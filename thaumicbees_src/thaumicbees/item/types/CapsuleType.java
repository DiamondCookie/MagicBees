package thaumicbees.item.types;

import thaumicbees.main.utils.LocalizationManager;
import cpw.mods.fml.common.registry.LanguageRegistry;

public enum CapsuleType
{
	MAGIC("magic", 2000, 0),
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
		return LocalizationManager.getLocalizedString("tb.capsule." + name);
	}
}