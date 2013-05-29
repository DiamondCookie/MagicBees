package magicbees.item.types;

import magicbees.main.utils.LocalizationManager;
import cpw.mods.fml.common.registry.LanguageRegistry;

public enum CapsuleType
{
	MAGIC("magic", 2000, 0),
	VOID("void", 8000, 1),
	;
	private String name; 
	public int capacity;
	public int iconIdx;
	
	private CapsuleType(String n, int c, int idx)
	{
		this.name = n;
		this.capacity = c;
		this.iconIdx = idx;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getLocalizedName()
	{
		return LocalizationManager.getLocalizedString("capsule." + name);
	}
}