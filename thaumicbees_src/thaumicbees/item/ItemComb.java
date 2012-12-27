package thaumicbees.item;

import thaumicbees.main.CommonProxy;
import thaumicbees.main.ThaumicBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemComb extends Item
{
	public enum CombType
	{
		MAGICAL("Magical Comb", 0x6e1c6d, 0xff8fff),
		PAPERY("Papery Comb", 0xfff, 0xfff),
		STARK("Stark Comb", 0x505759, 0xaaaebd),
		;
		
		private CombType(String pName, int colourA, int colourB)
		{
			this.name = pName;
			this.combColour[0] = colourA;
			this.combColour[1] = colourB;
		}
		
		public final String name;
		public int[] combColour = new int[2];
	}
	
	public ItemComb(int itemID)
	{
		super(itemID);
		this.setTextureFile(CommonProxy.FORESTRY_GFX_ITEMS);
		this.setCreativeTab(CreativeTabs.tabMaterials);
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
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@Override
	public int getRenderPasses(int metadata)
	{
		return 2;
	}

	@Override
	public int getIconIndex(ItemStack stack, int pass)
	{
		// 107, 108 are the base forestry honeycomb textures.
		return 107 + pass;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		int colour = 0xffffff;
		int meta = stack.getItemDamage();
		if (meta >= 0 && meta < CombType.values().length)
		{
			colour = CombType.values()[meta].combColour[pass % 2];
		}
		return colour;
	}

	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		String result = "";
		int meta = stack.getItemDamage();
		if (meta >= 0 && meta < CombType.values().length)
		{
			result = CombType.values()[meta].name;
		}
		return result;
	}

}
