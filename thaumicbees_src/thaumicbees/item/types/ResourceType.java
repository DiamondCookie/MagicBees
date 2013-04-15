package thaumicbees.item.types;

import thaumicbees.main.utils.LocalizationManager;
import cpw.mods.fml.common.registry.LanguageRegistry;

public enum ResourceType
{
	LORE_FRAGMENT("fragment", true),
	AROMATIC_LUMP("lump", true),
	EXTENDED_FERTILIZER("fertilizer", true),
	
	// Dummy items for Thaumanomicon research icons.
	//   These won't ever actually exist in-game, and so 
	RESEARCH_STARTNODE("startNode", false),
	RESEARCH_BEEINFUSION("beeInfusion", false),
	;
	
	private ResourceType(String n, boolean show)
	{
		this.name = n;
		this.showInList = show;
	}
	private String name;
	public boolean showInList;
	
	public String getName()
	{
		return this.name;
	}
	
	public String getLocalizedName()
	{
		return LocalizationManager.getLocalizedString("tb.resource." + this.name);
	}
}
