package thaumicbees.item;

import java.util.List;

import thaumicbees.item.ItemPollen.PollenType;
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
		OCCULT("Occult Comb", 0x6e1c6d, 0xff8fff),
		OTHERWORLDLY("Otherworldy Comb", 0x000056, 0x765cc1),
		PAPERY("Papery Comb", 0x503900, 0xbd9a30),
		STARK("Stark Comb", 0xB0B0BC, 0x6e6e79),
		AIRY("Airy Comb", 0xffff7e, 0x717600),
		FIREY("Firey Comb", 0xff3C01, 0x740002),
		WATERY("Watery Comb", 0x0090ff, 0x00308c),
		EARTHY("Earthy Comb", 0x00a000, 0x005100),
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
		super.getSubItems(id, tabs, list);
		for (CombType type : CombType.values())
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

	@Override
	public int getIconIndex(ItemStack stack, int pass)
	{
		// 107, 108 are the base forestry honeycomb textures.
		return (pass >= 1) ? 108 : 107;
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
