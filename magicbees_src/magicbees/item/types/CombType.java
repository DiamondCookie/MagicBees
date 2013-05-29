package magicbees.item.types;

import magicbees.main.utils.LocalizationManager;
import net.minecraft.item.ItemStack;

public enum CombType
{
	MUNDANE("mundane", new int[] {0xFFA060, 0xE5BE99}, true),
	OCCULT("occult", new int[] {0xff8fff, 0x6e1c6d}, true),
	OTHERWORLDLY("otherworldly", new int[] {0x765cc1, 0x3B3B7A}, true),
	PAPERY("paper", new int[] {0xbd9a30, 0x503900}, true),
	SOUL("soul", new int[] {0x8A4500, 0x3C1E00}, true),
	SKULKING("furtive", new int[] {0xcda6cd, 0x545454}, true),
	INTELLECT("aware", new int[] {0xb0092e9, 0x618fff}, false),
	
	TC_STARK("stark", new int[] {0x6e6e79, 0xB0B0BC}, false),
	TC_AIRY("air", new int[] {0x717600, 0xffff7e}, false),
	TC_FIREY("fire", new int[] {0x740002, 0xff3C01}, false),
	TC_WATERY("water", new int[] {0x00308c, 0x0090ff}, false),
	TC_EARTHY("earth", new int[] {0x005100, 0x00a000}, false),
	TC_INFUSED("magic", new int[] {0x7A489E, 0xaa32fc}, false),
	
	AM_ESSENCE("essence", new int[] {0x03E1DE, 0x015352}, false),
	AM_POTENT("potent", new int[] {0xBFA330, 0x41046F}, false),
	;
	
	private CombType(String pName, int[] colour, boolean show)
	{
		this.name = pName;
		this.combColour = new int[2];
		this.combColour = colour;
		
		this.showInList = show;
	}
	
	private String name;
	public int[] combColour;
	public boolean showInList;
	
	public String getName()
	{
		return LocalizationManager.getLocalizedString("comb." + this.name);
	}
}