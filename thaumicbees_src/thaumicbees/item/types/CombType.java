package thaumicbees.item.types;

import thaumicbees.main.utils.LocalizationManager;
import cpw.mods.fml.common.registry.LanguageRegistry;

public enum CombType
{
	OCCULT("occult", 0x6e1c6d, 0xff8fff, true),
	OTHERWORLDLY("otherworldly", 0x000056, 0x765cc1, true),
	PAPERY("paper", 0x503900, 0xbd9a30, true),
	STARK("stark", 0xB0B0BC, 0x6e6e79, false),
	AIRY("air", 0xffff7e, 0x717600, false),
	FIREY("fire", 0xff3C01, 0x740002, false),
	WATERY("water", 0x0090ff, 0x00308c, false),
	EARTHY("earth", 0x00a000, 0x005100, false),
	INFUSED("magic", 0xaa32fc, 0x7A489E, false),
	INTELLECT("aware", 0x618fff, 0xb0092e9, false),
	SKULKING("furtive", 0x545454, 0xcda6cd, true),
	;
	
	private CombType(String pName, int colourA, int colourB, boolean show)
	{
		this.name = pName;
		this.combColour = new int[2];
		this.combColour[0] = colourA;
		this.combColour[1] = colourB;
		
		this.showInList = show;
	}
	
	private String name;
	public int[] combColour;
	public boolean showInList;
	
	public String getName()
	{
		return LocalizationManager.getLocalizedString("tb.comb." + this.name);
	}
}