package thaumicbees.item.types;

public enum PropolisType
{
	STARK("Dull Propolis", 0xBBBBBB),
	AIR("Air Propolis", 0xA19E10),
	FIRE("Firey Propolis", 0x95132F),
	WATER("Watery Propolis", 0x1054A1),
	EARTH("Earthen Propolis", 0x00a000),
	INFUSED("Infused Propolis", 0xaa32fc),
	;
	
	private PropolisType(String pName, int overlayColour)
	{
		this.name = pName;
		this.colour = overlayColour;
	}
	
	public String name;
	public int colour;
}
