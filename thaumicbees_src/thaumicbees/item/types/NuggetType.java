package thaumicbees.item.types;

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
	
	public int iconIdx;
	
	private NuggetType(int idx)
	{
		this.iconIdx = idx;
	}	

	public String getName()
	{
		return LocalizationManager.getLocalizedString("tb.nugget." + this.toString().toLowerCase());
	}
}
