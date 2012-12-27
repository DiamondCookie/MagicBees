package thaumicbees.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemPropolis extends Item
{
	public enum PropolisType
	{
		;
		
		private PropolisType(String pName, int overlayColour)
		{
			this.name = pName;
			this.colour = overlayColour;
		}
		
		public String name;
		public int colour;
	}

	public ItemPropolis(int id)
	{
		super(id);
	}
	
	@Override
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		int colour = 0xffffff;
		int meta = stack.getItemDamage();
		if (meta >= 0 && meta < PropolisType.values().length)
		{
			colour = PropolisType.values()[meta].colour;
		}
		return colour;
	}

}
