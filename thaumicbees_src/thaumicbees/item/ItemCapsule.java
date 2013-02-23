package thaumicbees.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import forestry.api.recipes.RecipeManagers;
import thaumicbees.item.types.CapsuleType;
import thaumicbees.item.types.LiquidType;
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
	private CapsuleType capsuleType;
	
	public ItemCapsule(CapsuleType type, int itemId, int maxStackSize)
	{
		super(itemId);
		this.capsuleType = type;
		this.setTextureFile(CommonProxy.TCBEES_LIQUIDS_IMAGE);
		this.setCreativeTab(Tabs.tabApiculture);
		this.setHasSubtypes(true);
		this.setMaxStackSize(maxStackSize);
	}
	
	public CapsuleType getType()
	{
		return this.capsuleType;
	}

	@Override
	public String getItemDisplayName(ItemStack itemStack)
	{
		return LiquidType.values()[itemStack.getItemDamage()].getDisplayName() + " " + this.capsuleType.getName();
	}

	public ItemStack getCapsuleForLiquid(LiquidType l)
	{
		return new ItemStack(this, 1, l.ordinal());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs tab, List itemList)
	{
		for (LiquidType l : LiquidType.values())
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
			index = LiquidType.values()[metadata].iconIdx;
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
}
