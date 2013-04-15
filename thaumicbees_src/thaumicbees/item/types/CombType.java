package thaumicbees.item.types;

import thaumicbees.main.utils.LocalizationManager;
import cpw.mods.fml.common.registry.LanguageRegistry;

public enum CombType
{
	OCCULT("occult", 0xff8fff, 0x6e1c6d, true),
	OTHERWORLDLY("otherworldly",0x765cc1,  0x000056, true),
	PAPERY("paper", 0xbd9a30, 0x503900, true),
	STARK("stark", 0x6e6e79, 0xB0B0BC, false),
	AIRY("air", 0x717600, 0xffff7e, false),
	FIREY("fire", 0x740002, 0xff3C01, false),
	WATERY("water", 0x00308c, 0x0090ff, false),
	EARTHY("earth", 0x005100, 0x00a000, false),
	INFUSED("magic", 0x7A489E, 0xaa32fc, false),
	INTELLECT("aware", 0xb0092e9, 0x618fff, false),
	SKULKING("furtive", 0xcda6cd, 0x545454, true),
	SOUL("soul", 0x8A4500, 0x3C1E00, true),
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