package thaumicbees.item.types;

import thaumicbees.main.utils.LocalizationManager;
import cpw.mods.fml.common.registry.LanguageRegistry;

public enum WaxType
{
	MAGIC("magic", false, 0xd242df),
	;
	
	private WaxType(String n, boolean sp, int c)
	{
		this.name = n;
		this.sparkly = sp;
		this.colour = c;
	}
	
	private String name;
	public boolean sparkly;
	public int colour;
	
	public String getName()
	{
		return LocalizationManager.getLocalizedString("tb.wax." + this.name);
	}
}