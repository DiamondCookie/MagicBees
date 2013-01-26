package thaumicbees.item.types;

public enum ResourceType
{
	LORE_FRAGMENT("Lore Fragment", 2, true),
	AROMATIC_LUMP("Aromatic Lump", 3, true),
	EXTENDED_FERTILIZER("Concentrated Compound", 4, true),
	
	// Dummy items for Thaumanomicon research icons.
	RESEARCH_BeeInfusion("Bee Infusion", 240, false),
	;
	
	private ResourceType(String n, int i, boolean show)
	{
		this.name = n;
		this.iconIdx = i;
		this.showInList = show;
	}
	public String name;
	public int iconIdx;
	public boolean showInList;
}
