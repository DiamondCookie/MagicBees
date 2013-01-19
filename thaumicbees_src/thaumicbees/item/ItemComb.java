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
		OCCULT("Occult Comb", 0x6e1c6d, 0xff8fff, true),
		OTHERWORLDLY("Otherworldy Comb", 0x000056, 0x765cc1, true),
		PAPERY("Papery Comb", 0x503900, 0xbd9a30, true),
		STARK("Stark Comb", 0xB0B0BC, 0x6e6e79, false),
		AIRY("Airy Comb", 0xffff7e, 0x717600, false),
		FIREY("Firey Comb", 0xff3C01, 0x740002, false),
		WATERY("Watery Comb", 0x0090ff, 0x00308c, false),
		EARTHY("Earthy Comb", 0x00a000, 0x005100, false),
		INFUSED("Infused Comb", 0xaa32fc, 0x7A489E, false),
		INTELLECT("Memory Comb", 0x618fff, 0xb0092e9, false),
		SKULKING("Furtive Comb", 0x545454, 0xcda6cd, true),
		;
		
		private CombType(String pName, int colourA, int colourB, boolean show)
		{
			this.name = pName;
			this.combColour = new int[2];
			this.combColour[0] = colourA;
			this.combColour[1] = colourB;
			
			this.showInList = show;
		}
		
		public final String name;
		public int[] combColour;
		public boolean showInList;
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
		int passes = 2;
		return passes;
	}

	@Override
	public int getIconIndex(ItemStack stack, int pass)
	{
		int idx = 107;
		// 107, 108 are the base forestry honeycomb textures.
		if (pass >= 1)
		{
			idx = 108;
		}
		return idx;
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
		String result = "";
		int meta = stack.getItemDamage();
		if (meta >= 0 && meta < CombType.values().length)
		{
			result = CombType.values()[meta].name;
		}
		return result;
	}

}
