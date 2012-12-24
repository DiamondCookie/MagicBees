package thaumicbees.main;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMagicalComb extends Item
{

	private String name;
	private int colour[];
	
	public ItemMagicalComb(String combName, int itemID, int colour1, int colour2)
	{
		super(itemID);
		this.name = combName;
		this.colour = new int[2];
		this.colour[0] = colour1;
		this.colour[1] = colour2;
	}

	@Override
	public int getRenderPasses(int metadata)
	{
		return 2;
	}

	@Override
	public String getTextureFile()
	{
		return ThaumicBees.FORESTRY_GFX_ITEMS;
	}

	@Override
	public int getIconIndex(ItemStack stack, int pass)
	{
		return 12 * 16 + 6 + pass;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int pass)
	{
		
		return (pass < 2) ? this.colour[pass] : 0xFFFFFF;
	}

}
