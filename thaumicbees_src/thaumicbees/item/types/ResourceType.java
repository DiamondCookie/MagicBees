package thaumicbees.item.types;

import thaumicbees.main.utils.LocalizationManager;
import cpw.mods.fml.common.registry.LanguageRegistry;

public enum ResourceType
{
	LORE_FRAGMENT("fragment", 2, true),
	AROMATIC_LUMP("lump", 3, true),
	EXTENDED_FERTILIZER("fertilizer", 4, true),
	
	// Dummy items for Thaumanomicon research icons.
	//   These won't ever actually exist in-game, and so 
	RESEARCH_StartNode("Start Node", 241, false),
	RESEARCH_BeeInfusion("Bee Infusion", 240, false),
	;
	
	private ResourceType(String n, int i, boolean show)
	{
		this.name = n;
		this.iconIdx = i;
		this.showInList = show;
	}
	private String name;
	public int iconIdx;
	public boolean showInList;
	
	public String getName()
	{
		return LocalizationManager.getLocalizedString("tb.resource." + this.name);
	}
}
