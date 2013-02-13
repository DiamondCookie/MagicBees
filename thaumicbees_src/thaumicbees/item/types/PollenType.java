package thaumicbees.item.types;

import cpw.mods.fml.common.registry.LanguageRegistry;

public enum PollenType
{
	UNUSUAL("unusual", 0xA03059, 0xD8417B),
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
		return LanguageRegistry.instance().getStringLocalization("tb.pollen." + this.name);
	}
}