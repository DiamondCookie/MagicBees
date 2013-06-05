package magicbees.item.types;

import magicbees.main.utils.LocalizationManager;
import cpw.mods.fml.common.registry.LanguageRegistry;

public enum ResourceType
{
	LORE_FRAGMENT("fragment", true),
	AROMATIC_LUMP("lump", true),
	EXTENDED_FERTILIZER("fertilizer", true),
	SKULL_CHIP("skullChip", true),
	SKULL_FRAGMENT("skullFragment", true),
	DRAGON_DUST("dragonDust", true),
	DRAGON_CHUNK("dragonChunk", true),
	ESSENCE_FALSE_LIFE("essenceFalseLife", true),
	
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
	
	public void setHidden()
	{
		this.showInList = false;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getLocalizedName()
	{
		return LocalizationManager.getLocalizedString("resource." + this.name);
	}
}
