package thaumicbees.item;

import java.util.List;

import thaumicbees.item.types.PropolisType;
import thaumicbees.main.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemPropolis extends Item
{
	public ItemPropolis(int id)
	{
		super(id);
		this.setTextureFile(CommonProxy.FORESTRY_GFX_ITEMS);
		this.setCreativeTab(Tabs.tabApiculture);
		this.setIconIndex(98);
		this.setHasSubtypes(true);
	}
	
	public ItemStack getStackForType(PropolisType type)
	{
		return new ItemStack(this, 1, type.ordinal());
	}	
	
	public ItemStack getStackForType(PropolisType type, int count)
	{
		return new ItemStack(this, count, type.ordinal());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tabs, List list)
	{
		for (PropolisType type : PropolisType.values())
		{
			list.add(this.getStackForType(type));
		}
	}
	
	@Override
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		int colour = 0xffffff;
		int meta = stack.getItemDamage();
		if (meta >= 0 && meta < PropolisType.values().length)
		{
			colour = PropolisType.values()[meta].colour;
		}
		return colour;
	}

	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		String result = "";
		int meta = stack.getItemDamage();
		if (meta >= 0 && meta < PropolisType.values().length)
		{
			result = PropolisType.values()[meta].name;
		}
		return result;
	}

}
