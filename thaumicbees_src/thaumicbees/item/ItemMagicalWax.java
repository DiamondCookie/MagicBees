package thaumicbees.item;

import thaumicbees.main.CommonProxy;
import thaumicbees.main.ThaumicBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMagicalWax extends Item
{
	
	private boolean sparkly;
	private int colour;
	
	public ItemMagicalWax(int itemID, int overlayColour, boolean hasSparklyIcon)
	{
		super(itemID);
		this.sparkly = hasSparklyIcon;
		this.colour = overlayColour;
		this.setTextureFile(CommonProxy.TCBEES_ITEMS);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public int getIconIndex(ItemStack stack, int pass)
	{
		return this.sparkly ? 0 : 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int pass)
	{
		
		return this.colour;
	}
}
