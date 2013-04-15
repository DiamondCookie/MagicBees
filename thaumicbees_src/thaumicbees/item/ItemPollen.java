package thaumicbees.item;

import java.util.List;

import thaumicbees.item.types.PollenType;
import thaumicbees.main.CommonProxy;
import thaumicbees.main.ThaumicBees;
import thaumicbees.main.utils.compat.ForestryHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemPollen extends Item
{
	public ItemPollen(int itemID)
	{
		super(itemID);
		this.setCreativeTab(Tabs.tabApiculture);
		this.setHasSubtypes(true);
	}
	public ItemStack getStackForType(PollenType type)
	{
		return new ItemStack(this, 1, type.ordinal());
	}	
	
	public ItemStack getStackForType(PollenType type, int count)
	{
		return new ItemStack(this, count, type.ordinal());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tabs, List list)
	{
		for (PollenType type : PollenType.values())
		{
			list.add(this.getStackForType(type));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@Override
	public int getRenderPasses(int metadata)
	{
		return 2;
	}
	
	@SideOnly(Side.CLIENT)
	private Icon secondIcon;
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(ForestryHelper.Name.toLowerCase() + ":pollen.0");
        this.secondIcon= par1IconRegister.registerIcon(ForestryHelper.Name.toLowerCase() + ":pollen.1");
    }
    
	@Override
	public Icon getIcon(ItemStack stack, int pass)
	{
		return (pass == 0) ? this.itemIcon : this.secondIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		int meta = Math.max(0, Math.min(PollenType.values().length - 1, stack.getItemDamage()));
		int colour = PollenType.values()[meta].colour[0];
		
		if (pass >= 1)
		{
			colour = PollenType.values()[meta].colour[1];
		}
		
		return colour;
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		return PollenType.values()[stack.getItemDamage()].getName();
	}

}
