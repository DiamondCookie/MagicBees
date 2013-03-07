package thaumicbees.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import thaumcraft.api.EnumTag;
import thaumicbees.main.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSolidFlux extends Item
{
	public ItemSolidFlux(int itemId)
	{
		super(itemId);
		this.setTextureFile(CommonProxy.TCBEES_ITEMS_IMAGE);
		this.setIconIndex(18);
		this.setCreativeTab(Tabs.tabApiculture);
		this.setHasSubtypes(true);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs tab, List list)
	{
		for (EnumTag tag : EnumTag.values())
		{
			if (tag == EnumTag.UNKNOWN)
			{
				continue;
			}
			
			list.add(new ItemStack(this.itemID, 1, tag.id));
		}
	}

	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		return "Crystalized " + EnumTag.get(stack.getItemDamage()).name + " Aspect";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		return EnumTag.get(stack.getItemDamage()).color;
	}

}
