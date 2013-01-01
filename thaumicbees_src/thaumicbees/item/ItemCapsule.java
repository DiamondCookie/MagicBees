package thaumicbees.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumicbees.main.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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
	
	public enum Liquids
	{  // Incidentally, Item meta is the liquid ID.
		EMPTY("", 16),
		WATER("water", 17),
		LAVA("lava", 18),
		BIOMASS("biomass", 19),
		BIOFUEL("biofuel", 20),
		OIL("oil", 21),
		FUEL("fuel", 22),
		SEEDOIL("seedoil", 23),
		HONEY("honey", 24),
		JUICE("juice", 25),
		MILK("milk", 26),
		;
		public String liquidId;
		public int iconIdx;
		
		private Liquids(String l, int idx)
		{
			this.liquidId = l;
			this.iconIdx = idx;
		}
	}
	
	private Type capsuleType;
	
	public ItemCapsule(Type type, int itemId)
	{
		super(itemId);
		this.capsuleType = type;
		this.setTextureFile(CommonProxy.TCBEES_LIQUIDS_IMAGE);
		this.setCreativeTab(CreativeTabs.tabMaterials);
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
		if (pass == 1)
		{
			index = Liquids.values()[metadata].iconIdx;
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

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		return itemStack;
	}
	
	

}
