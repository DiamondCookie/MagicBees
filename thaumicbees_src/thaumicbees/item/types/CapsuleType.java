package thaumicbees.item.types;

public enum CapsuleType
{
	MAGIC("Magic Capsule", 2000, 0),
	;
	public String name; 
	public int capacity;
	public int iconIdx;
	
	private CapsuleType(String n, int c, int idx)
	{
		this.name = n;
		this.capacity = c;
		this.iconIdx = idx;
	}
}