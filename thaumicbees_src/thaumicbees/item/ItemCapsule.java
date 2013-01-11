package thaumicbees.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.recipes.RecipeManagers;
import thaumicbees.main.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;

public class ItemCapsule extends Item
{
	public enum Type
	{
		MAGIC("Magic Capsule", 2000, 0),
		;
		public String name; 
		public int capacity;
		public int iconIdx;
		
		private Type(String n, int c, int idx)
		{
			this.name = n;
			this.capacity = c;
			this.iconIdx = idx;
		}
	}
	
	public enum Liquid
	{  // Incidentally, Item meta is the liquid ID.
		EMPTY("", "Empty",  16),
		// Vanilla
		WATER("water", "Water", 17),
		LAVA("lava", "Lava", 18),
		// Forestry
		BIOMASS("biomass", "Biomass", 19),
		BIOFUEL("biofuel", "Biofuel", 20),
		// Buildcraft
		OIL("Oil", "Oil", 21),
		FUEL("Fuel", "Fuel", 22),
		// More Forestry
		SEEDOIL("seedoil", "Seed Oil", 23),
		HONEY("honey", "Honey", 24),
		JUICE("juice", "Juice", 25),
		CRUSHEDICE("ice", "Crushed Ice", 26),
		MILK("milk", "Milk", 27),
		// ExtraBees liquids
		ACID("acid", "Acid", 28),
		POISON("poison", "Poison", 29),
		LIQUIDNITROGEN("liquidNitrogen", "Liquid Nitrogen", 30),
		DNA("liquidDNA", "Liquid DNA", 31),
		// Railcraft
		CREOSOTEOIL("Creosote Oil", "Creosote Oil", 32),
		STEAM("Steam", "Steam", 33),
		;
		public String liquidID;
		public String displayName;
		public int iconIdx;
		public boolean available = false;
		
		private Liquid(String l, String display, int idx)
		{
			this.liquidID = l;
			this.displayName = display;
			this.iconIdx = idx;
		}
	}

	private Type capsuleType;
	
	public ItemCapsule(Type type, int itemId)
	{
		super(itemId);
		this.capsuleType = type;
		this.setTextureFile(CommonProxy.TCBEES_LIQUIDS_IMAGE);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setHasSubtypes(true);
	}

	@Override
	public String getItemDisplayName(ItemStack itemStack)
	{
		return Liquid.values()[itemStack.getItemDamage()].displayName + " " + this.capsuleType.name;
	}

	public ItemStack getCapsuleForLiquid(Liquid l)
	{
		return new ItemStack(this, 1, l.ordinal());
	}
	
	public void setUpLiquids()
	{
		ItemStack empty = new ItemStack(this, 1, 0);
		ItemStack filled;
		LiquidStack liquid = null;
		
		for (Liquid l : Liquid.values())
		{
			switch (l)
			{
				case EMPTY:
					liquid = null;
					break;
				case WATER:
					liquid = new LiquidStack(Block.waterStill, this.capsuleType.capacity);
					break;
				case LAVA:
					liquid = new LiquidStack(Block.lavaStill, this.capsuleType.capacity);
					break;
				default:
					liquid = LiquidDictionary.getLiquid(l.liquidID, this.capsuleType.capacity);
					break;
			}

			if (liquid != null)
			{
				filled = new ItemStack(this, 1, l.ordinal());
				LiquidContainerRegistry.registerLiquid(new LiquidContainerData(liquid, filled, empty));
				
				// Register with Squeezer/Bottler
				RecipeManagers.bottlerManager.addRecipe(5, liquid, empty, filled);
				RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[] {filled} , liquid,
						ItemManager.wax.getStackForType(ItemWax.WaxType.MAGIC), 20);
				l.available = true;
			}
		}
		// Empty will be set to unavailable. Obviously, it always is.
		Liquid.EMPTY.available = true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs tab, List itemList)
	{
		for (Liquid l : Liquid.values())
		{
			if (l.available)
			{
				itemList.add(new ItemStack(this, 1, l.ordinal()));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getIconFromDamageForRenderPass(int metadata, int pass)
	{
		int index = 0;
		if (pass == 0)
		{
			index = Liquid.values()[metadata].iconIdx;
		}
		else
		{
			index = capsuleType.iconIdx;
		}
		return index;
	}

	@Override
	public int getRenderPasses(int metadata)
	{
		return 2;
	}
	
/*	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, true);
		
		if (mop != null && mop.typeOfHit == EnumMovingObjectType.TILE)
		{
			int i = mop.blockX;
			int j = mop.blockY;
			int k = mop.blockZ;
			int targetedId = world.getBlockId(i, j, k);
			int targetedMeta = world.getBlockMetadata(i, j, k);

			// Check whether there is valid container for the liquid.
			LiquidContainerData container = LiquidHelper.getEmptyContainer(itemStack, new LiquidStack(targetedId, 2, targetedMeta));
			if (container != null)
			{
				// Search for a slot to stow a filled container in player's
				// inventory
				int slot = getMatchingItemSlot(player, container.filled);
				if (slot >= 0)
				{
					if (player.inventory.getStackInSlot(slot) == null)
					{
						player.inventory.setInventorySlotContents(slot, container.filled.copy());
					}
					else
					{
						player.inventory.getStackInSlot(slot).stackSize++;
					}
					
					// Remove consumed empty container
					itemStack.stackSize--;
		
					// Notify player that his inventory has changed.
					Proxies.net.inventoryChangeNotify(player);
				}
			}
		}

		return itemStack;
	}

	private int getMatchingItemSlot(EntityPlayer player, ItemStack itemStack)
	{
		int matchingSlot = -1;
		int emptySlot = -1;
		
		ItemStack[] inventory = player.inventory.mainInventory;
		for (int i = 0; i < inventory.length; ++i)
		{
			// Check to see if this is an empty slot.
			if (inventory[i] == null)
			{
				// Set our empty Slot counter if not already set.
				if (emptySlot == -1)
				{
					emptySlot = i;
				}
			}
			else
			{
				// See if contents of slot matches our target itemStack.
				
			}
		}
		
		if (matchingSlot == -1)
		{
			matchingSlot = emptySlot;
		}
		
		return matchingSlot;
	}*/

}
