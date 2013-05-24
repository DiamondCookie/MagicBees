package magicbees.item.types;

import magicbees.main.utils.LocalizationManager;
import cpw.mods.fml.common.registry.LanguageRegistry;

public enum DropType
{
	ENCHANTED("enchanting", 0x6e1c6d, 0xff8fff),
	INTELLECT("intellect", 0x25914D, 0x18E072),
	;
	
	private DropType(String pName, int colourA, int colourB)
	{
		this.name = pName;
		this.combColour[0] = colourA;
		this.combColour[1] = colourB;
	}
	
	private final String name;
	public int[] combColour = new int[2];
	
	public String getName()
	{
		return LocalizationManager.getLocalizedString("tb.drop." + this.name);
	}
}