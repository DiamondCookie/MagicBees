package thaumicbees.item;

import java.util.List;

import thaumicbees.item.ItemComb.CombType;
import thaumicbees.item.ItemDrop.DropType;
import thaumicbees.item.ItemPollen.PollenType;
import thaumicbees.main.CommonProxy;
import thaumicbees.main.ThaumicBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemWax extends Item
{
	public enum WaxType
	{
		MAGIC("Magic Wax", false, 0xd242df),
		;
		
		private WaxType(String n, boolean sp, int c)
		{
			this.name = n;
			this.sparkly = sp;
			this.colour = c;
		}
		
		public String name;
		public boolean sparkly;
		public int colour;
	}
	
	public ItemWax(int itemID)
	{
		super(itemID);
		this.setTextureFile(CommonProxy.TCBEES_ITEMSPNG);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setHasSubtypes(true);
	}
	
	public ItemStack getStackForType(WaxType type)
	{
		return new ItemStack(this, 1, type.ordinal());
	}	
	
	public ItemStack getStackForType(WaxType type, int count)
	{
		return new ItemStack(this, count, type.ordinal());
	}

	@Override
	public int getIconIndex(ItemStack stack, int pass)
	{
		int idx = 1;
		int meta = stack.getItemDamage();
		if (meta >= 0 && meta < WaxType.values().length)
		{
			idx = (WaxType.values()[meta].sparkly) ? 0 : 1;
		}
		return idx;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tabs, List list)
	{
		super.getSubItems(id, tabs, list);
		for (WaxType type : WaxType.values())
		{
			list.add(this.getStackForType(type));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		int colour = 0xffffff;
		int meta = stack.getItemDamage();
		if (meta >= 0 && meta < WaxType.values().length)
		{
			colour = WaxType.values()[meta].colour;
		}
		return colour;
	}

	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		String result = "";
		int meta = stack.getItemDamage();
		if (meta >= 0 && meta < WaxType.values().length)
		{
			result = WaxType.values()[meta].name;
		}
		return result;
	}
}
