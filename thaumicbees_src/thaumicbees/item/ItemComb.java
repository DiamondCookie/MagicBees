package thaumicbees.item;

import java.util.List;

import thaumicbees.item.types.CombType;
import thaumicbees.main.CommonProxy;
import thaumicbees.main.ThaumicBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemComb extends Item
{
	public ItemComb(int itemID)
	{
		super(itemID);
		this.setTextureFile(CommonProxy.FORESTRY_GFX_ITEMS);
		this.setCreativeTab(Tabs.tabApiculture);
		this.setHasSubtypes(true);
	}
	
	public ItemStack getStackForType(CombType type)
	{
		return new ItemStack(this, 1, type.ordinal());
	}	
	
	public ItemStack getStackForType(CombType type, int count)
	{
		return new ItemStack(this, count, type.ordinal());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tabs, List list)
	{
		for (CombType type : CombType.values())
		{
			if (type.showInList)
			{
				list.add(this.getStackForType(type));
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
	public int getRenderPasses(int meta)
	{
		int passes = 2;
		return passes;
	}

	@Override
	public int getIconIndex(ItemStack stack, int pass)
	{
		int idx = 107;
		// 107, 108 are the base forestry honeycomb textures.
		if (pass >= 1)
		{
			idx = 108;
		}
		return idx;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		int meta = Math.max(0, Math.min(CombType.values().length - 1, stack.getItemDamage()));
		int colour = CombType.values()[meta].combColour[0];
		
		if (pass >= 1)
		{
			colour = CombType.values()[meta].combColour[1];
		}
		
		return colour;
	}

	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		return CombType.values()[stack.getItemDamage()].getName();
	}

}
