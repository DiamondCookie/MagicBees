package thaumicbees.item.types;

import thaumicbees.main.utils.LocalizationManager;
import cpw.mods.fml.common.registry.LanguageRegistry;

public enum PollenType
{
	UNUSUAL("unusual", 0xD8417B, 0xA03059),
	PHASED("phased", 0x4974B4, 0x456BA5),
	;
	
	private PollenType(String pName, int colourA, int colourB)
	{
		this.name = pName;
		this.colour[0] = colourA;
		this.colour[1] = colourB;
	}
	
	private String name;
	public int[] colour = new int[2];
	
	public String getName()
	{
		return LocalizationManager.getLocalizedString("tb.pollen." + this.name);
	}
}