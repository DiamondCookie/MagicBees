package thaumicbees.item.types;

import net.minecraft.item.Item;
import thaumicbees.main.utils.LocalizationManager;

public enum NuggetType
{
	IRON(5),
	COPPER(6),
	TIN(7),
	SILVER(8),
	LEAD(9),
	DIAMOND(10),
	EMERALD(11),
	;
	
	private Item targetIngot;
	public int iconIdx;
	private boolean active; 
	
	private NuggetType(int idx)
	{
		this.iconIdx = idx;
		this.active = true;
	}
	
	public void setIngotItem(Item target)
	{
		this.targetIngot = target;
	}
	
	public Item getIngotItem()
	{
		return this.targetIngot;
	}
	
	public void setActive(boolean b)
	{
		this.active = b;
	}
	
	public boolean isActive()
	{
		return this.active;
	}

	public String getName()
	{
		return LocalizationManager.getLocalizedString("tb.nugget." + this.toString().toLowerCase());
	}
}
