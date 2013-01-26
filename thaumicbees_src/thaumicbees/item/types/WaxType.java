package thaumicbees.item.types;

public enum WaxType
{
	MAGIC("Magic Wax", false, 0xd242df),
	;
	
	private WaxType(String n, boolean sp, int c)
	{
		this.name = n;
		this.sparkly = sp;
		this.colour = c;
	}
	
	public String name;
	public boolean sparkly;
	public int colour;
}