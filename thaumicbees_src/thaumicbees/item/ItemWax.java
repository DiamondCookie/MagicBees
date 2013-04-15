package thaumicbees.item;

import java.util.List;

import thaumicbees.item.types.ResourceType;
import thaumicbees.item.types.WaxType;
import thaumicbees.main.CommonProxy;
import thaumicbees.main.ThaumicBees;
import thaumicbees.main.utils.VersionInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemWax extends Item
{
	@SideOnly(Side.CLIENT)
	private Icon secondaryIcon;
	
	public ItemWax(int itemID)
	{
		super(itemID);
		this.setCreativeTab(Tabs.tabApiculture);
		this.setHasSubtypes(true);
	}
	
	public ItemStack getStackForType(WaxType type)
	{
		return new ItemStack(this, 1, type.ordinal());
	}	
	
	public ItemStack getStackForType(WaxType type, int count)
	{
		return new ItemStack(this, count, type.ordinal());
	}
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
    	this.itemIcon = par1IconRegister.registerIcon(VersionInfo.ModName.toLowerCase() + ":wax.0");
    	this.secondaryIcon = par1IconRegister.registerIcon(VersionInfo.ModName.toLowerCase() + ":wax.1");
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta)
	{
		return (WaxType.values()[meta].sparkly) ? this.secondaryIcon : this.itemIcon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tabs, List list)
	{
		for (WaxType type : WaxType.values())
		{
			list.add(this.getStackForType(type));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		int colour = 0xffffff;
		int meta = stack.getItemDamage();
		if (meta >= 0 && meta < WaxType.values().length)
		{
			colour = WaxType.values()[meta].colour;
		}
		return colour;
	}

	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		return WaxType.values()[stack.getItemDamage()].getName();
	}
}
