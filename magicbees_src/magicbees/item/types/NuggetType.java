package magicbees.item.types;

import magicbees.main.utils.LocalizationManager;
import net.minecraft.item.Item;

public enum NuggetType
{
	IRON,
	COPPER,
	TIN,
	SILVER,
	LEAD,
	DIAMOND,
	EMERALD,
	APATITE,
	;
	
	private Item targetIngot;
	private boolean active; 
	
	private NuggetType()
	{
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
		return LocalizationManager.getLocalizedString("nugget." + this.toString().toLowerCase());
	}
}
