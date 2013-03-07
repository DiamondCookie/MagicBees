package thaumicbees.item.types;

public enum DropType
{
	ENCHANTED("Enchanting Drop", 0x6e1c6d, 0xff8fff),
	INTELLECT("Intellect Drop", 0x25914D, 0x18E072),
	;
	
	private DropType(String pName, int colourA, int colourB)
	{
		this.name = pName;
		this.combColour[0] = colourA;
		this.combColour[1] = colourB;
	}
	
	public final String name;
	public int[] combColour = new int[2];
}