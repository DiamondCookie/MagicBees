package magicbees.block.types;

public enum PlankType
{
	GREATWOOD("greatwood", 48),
	SILVERWOOD("silverwood", 49),
	;
	
	public String name;
	public int textureIdx;
	private static String[] nameList;
	
	private PlankType(String n, int idx)
	{
		this.name = n;
		this.textureIdx = idx;
	}
	
	public static PlankType getType(int meta)
	{
		PlankType type = null;
		
		if (meta >= 0 && meta <= values().length)
		{
			type = values()[meta];
		}
		
		return type;
	}
	
	public static String[] getAllNames()
	{
		return (nameList == null) ? nameList = generateNames() : nameList;
	}
	
	private static String[] generateNames()
	{
		String[] names = new String[values().length];
		for (int i = 0; i < names.length; ++i)
		{
			names[i] = values()[i].name;
		}
		return names;
	}
}
