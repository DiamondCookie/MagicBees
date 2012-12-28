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

public class ItemDrop extends Item
{
	public enum DropType
	{
		ENCHANTED("Enchanting Drop", 0x6e1c6d, 0xff8fff),
		;
		
		private DropType(String pName, int colourA, int colourB)
		{
			this.name = pName;
			this.combColour[0] = colourA;
			this.combColour[1] = colourB;
		}
		
		public final String name;
		public int[] combColour = new int[2];
	}
	
	public ItemDrop(int itemID)
	{
		super(itemID);
		this.setTextureFile(CommonProxy.FORESTRY_GFX_ITEMS);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setHasSubtypes(true);
	}
	
	public ItemStack getStackForType(DropType type)
	{
		return new ItemStack(this, 1, type.ordinal());
	}	
	
	public ItemStack getStackForType(DropType type, int count)
	{
		return new ItemStack(this, count, type.ordinal());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tabs, List list)
	{
		super.getSubItems(id, tabs, list);
		for (DropType type : DropType.values())
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
		// 109, 110 are the base forestry drop textures.
		return 109 + pass;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		int colour = 0xffffff;
		int meta = stack.getItemDamage();
		if (meta >= 0 && meta < DropType.values().length)
		{
			colour = DropType.values()[meta].combColour[pass % 2];
		}
		return colour;
	}

	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		String result = "";
		int meta = stack.getItemDamage();
		if (meta >= 0 && meta < DropType.values().length)
		{
			result = DropType.values()[meta].name;
		}
		return result;
	}

}
