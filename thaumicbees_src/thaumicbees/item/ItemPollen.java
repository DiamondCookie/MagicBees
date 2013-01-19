package thaumicbees.item;

import java.util.List;

import thaumicbees.item.ItemComb.CombType;
import thaumicbees.main.CommonProxy;
import thaumicbees.main.ThaumicBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemPollen extends Item
{
	public enum PollenType
	{
		UNUSUAL("Unusual Pollen", 0xA03059, 0xD8417B),
		;
		
		private PollenType(String pName, int colourA, int colourB)
		{
			this.name = pName;
			this.combColour[0] = colourA;
			this.combColour[1] = colourB;
		}
		
		public final String name;
		public int[] combColour = new int[2];
	}
	
	public ItemPollen(int itemID)
	{
		super(itemID);
		this.setTextureFile(CommonProxy.FORESTRY_GFX_ITEMS);
		this.setCreativeTab(CreativeTabs.tabMaterials);
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
		super.getSubItems(id, tabs, list);
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

	@Override
	public int getIconIndex(ItemStack stack, int pass)
	{
		int idx = 45;
		// 45, 46 are the base forestry pollen textures.
		if (pass >= 1)
		{
			idx = 46;
		}
		return idx;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		int meta = Math.max(0, Math.min(PollenType.values().length - 1, stack.getItemDamage()));
		int colour = PollenType.values()[meta].combColour[0];
		
		if (pass >= 1)
		{
			colour = PollenType.values()[meta].combColour[1];
		}
		
		return colour;
	}

	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		String result = "";
		int meta = stack.getItemDamage();
		if (meta >= 0 && meta < PollenType.values().length)
		{
			result = PollenType.values()[meta].name;
		}
		return result;
	}

}
