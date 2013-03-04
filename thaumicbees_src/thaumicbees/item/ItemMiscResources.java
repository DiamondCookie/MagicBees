package thaumicbees.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import thaumicbees.item.types.ResourceType;
import thaumicbees.main.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMiscResources extends Item
{
	public ItemMiscResources(int ID)
	{
		super(ID);
		this.setTextureFile(CommonProxy.TCBEES_ITEMS_IMAGE);
		this.setCreativeTab(Tabs.tabApiculture);
		this.setHasSubtypes(true);
	}
	
	public ItemStack getStackForType(ResourceType type)
	{
		return new ItemStack(this, 1, type.ordinal());
	}	
	
	public ItemStack getStackForType(ResourceType type, int count)
	{
		return new ItemStack(this, count, type.ordinal());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tabs, List list)
	{
		for (ResourceType type : ResourceType.values())
		{
			if (type.showInList)
			{
				list.add(this.getStackForType(type));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getIconFromDamage(int meta)
	{
		int iconIdx = 0;
		if (meta >= 0 && meta < ResourceType.values().length)
		{
			iconIdx = ResourceType.values()[meta].iconIdx;
		}
		return iconIdx;
	}

	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		return ResourceType.values()[stack.getItemDamage()].getName();
	}

}
