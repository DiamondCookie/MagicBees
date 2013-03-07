package thaumicbees.item.types;

public enum PollenType
{
	UNUSUAL("Unusual Pollen", 0xA03059, 0xD8417B),
	;
	
	private PollenType(String pName, int colourA, int colourB)
	{
		this.name = pName;
		this.combColour[0] = colourA;
		this.combColour[1] = colourB;
	}
	
	public final String name;
	public int[] combColour = new int[2];
}