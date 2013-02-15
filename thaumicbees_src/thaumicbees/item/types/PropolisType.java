package thaumicbees.item.types;

import thaumicbees.main.utils.LocalizationManager;
import cpw.mods.fml.common.registry.LanguageRegistry;

public enum PropolisType
{
	STARK("dull", 0xBBBBBB),
	AIR("air", 0xA19E10),
	FIRE("fire", 0x95132F),
	WATER("water", 0x1054A1),
	EARTH("earth", 0x00a000),
	INFUSED("magic", 0xaa32fc),
	;
	
	private PropolisType(String pName, int overlayColour)
	{
		this.name = pName;
		this.colour = overlayColour;
	}
	
	private String name;
	public int colour;
	
	public String getName()
	{
		return LocalizationManager.getLocalizedString("tb.propolis." + this.name);
	}
}
