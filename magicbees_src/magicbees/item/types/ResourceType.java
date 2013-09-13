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
	ESSENCE_SHALLOW_GRAVE("essenceShallowGrave", true),
	ESSENCE_LOST_TIME("essenceLostTime", true),
	ESSENCE_EVERLASTING_DURABILITY("essenceEverlastingDurability", true),
	ESSENCE_UNENDING_DISREGARD("essenceUnendingDisregard", true),
	z_1("", false),
	z_2("", false),
	z_3("", false),
	z_4("", false),
	z_5("", false),
	DIMENSIONAL_SINGULARITY("dimensionalSingularity", false),
	TC_DUST_AIR("TCairDust", true),
	TC_DUST_WATER("TCwaterDust", true),
	TC_DUST_FIRE("TCfireDust", true),
	TC_DUST_EARTH("TCearthDust", true),
	TC_DUST_ORDER("TCorderDust", true),
	TC_DUST_CHAOS("TCchaosDust", true),
	
	
	// Dummy items for Thaumanomicon research icons.
	//   These won't ever actually exist in-game, and so they can be moved around.
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
