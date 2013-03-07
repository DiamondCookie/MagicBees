package thaumicbees.item.types;

import thaumicbees.main.utils.LocalizationManager;
import cpw.mods.fml.common.registry.LanguageRegistry;

public enum PollenType
{
	UNUSUAL("unusual",	0xA03059, 0xD8417B),
	PHASED("phased",	0x456BA5, 0x4974B4),
	;
	
	private PollenType(String pName, int colourA, int colourB)
	{
		this.name = pName;
		this.combColour[0] = colourA;
		this.combColour[1] = colourB;
	}
	
	private String name;
	public int[] combColour = new int[2];
	
	public String getName()
	{
		return LocalizationManager.getLocalizedString("tb.pollen." + this.name);
	}
}