package thaumicbees.item.types;

public enum CombType
{
	OCCULT("Occult Comb", 0x6e1c6d, 0xff8fff, true),
	OTHERWORLDLY("Otherworldy Comb", 0x000056, 0x765cc1, true),
	PAPERY("Papery Comb", 0x503900, 0xbd9a30, true),
	STARK("Stark Comb", 0xB0B0BC, 0x6e6e79, false),
	AIRY("Airy Comb", 0xffff7e, 0x717600, false),
	FIREY("Firey Comb", 0xff3C01, 0x740002, false),
	WATERY("Watery Comb", 0x0090ff, 0x00308c, false),
	EARTHY("Earthy Comb", 0x00a000, 0x005100, false),
	INFUSED("Infused Comb", 0xaa32fc, 0x7A489E, false),
	INTELLECT("Memory Comb", 0x618fff, 0xb0092e9, false),
	SKULKING("Furtive Comb", 0x545454, 0xcda6cd, true),
	;
	
	private CombType(String pName, int colourA, int colourB, boolean show)
	{
		this.name = pName;
		this.combColour = new int[2];
		this.combColour[0] = colourA;
		this.combColour[1] = colourB;
		
		this.showInList = show;
	}
	
	public final String name;
	public int[] combColour;
	public boolean showInList;
}