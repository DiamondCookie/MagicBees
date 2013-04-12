package thaumicbees.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.api.EnumTag;
import thaumicbees.item.types.NuggetType;
import thaumicbees.item.types.PollenType;
import thaumicbees.item.types.ResourceType;
import thaumicbees.main.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemNugget extends Item
{
	public ItemNugget(int itemID)
	{
		super(itemID);
		this.setTextureFile(CommonProxy.TCBEES_ITEMS_IMAGE);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setHasSubtypes(true);
	}
	
	public ItemStack getStackForType(NuggetType type)
	{
		return new ItemStack(this, 1, type.ordinal());
	}
	
	public ItemStack getStackForType(NuggetType type, int count)
	{
		return new ItemStack(this, count, type.ordinal());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs tab, List list)
	{
		for (NuggetType type : NuggetType.values())
		{
			if (type.isActive())
			{
				list.add(new ItemStack(this.itemID, 1, type.ordinal()));
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getIconFromDamage(int meta)
	{
		int iconIdx = 0;
		if (meta >= 0 && meta < NuggetType.values().length)
		{
			iconIdx = NuggetType.values()[meta].iconIdx;
		}
		return iconIdx;
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		return NuggetType.values()[stack.getItemDamage()].getName();
	}
}
