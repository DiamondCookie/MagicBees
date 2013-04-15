package thaumicbees.item;

import java.util.List;

import thaumicbees.item.types.CombType;
import thaumicbees.main.CommonProxy;
import thaumicbees.main.ThaumicBees;
import thaumicbees.main.utils.VersionInfo;
import thaumicbees.main.utils.compat.ForestryHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemComb extends Item
{
	public ItemComb(int itemID)
	{
		super(itemID);
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
		return 2;
	}
	
	@SideOnly(Side.CLIENT)
	private Icon secondIcon;
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(ForestryHelper.Name.toLowerCase() + ":beeCombs.0");
        this.secondIcon= par1IconRegister.registerIcon(ForestryHelper.Name.toLowerCase() + ":beeCombs.1");
    }

	@Override
	public Icon getIcon(ItemStack stack, int pass)
	{
		return (pass == 0) ? itemIcon : secondIcon;
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
