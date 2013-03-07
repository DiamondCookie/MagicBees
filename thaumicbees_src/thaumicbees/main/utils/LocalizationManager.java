package thaumicbees.main.utils;

import net.minecraft.util.StringTranslate;
import thaumicbees.main.CommonProxy;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class LocalizationManager
{
	private enum Locale
	{
		en_US("en_US"),
		;
		public String locale;
		
		private Locale(String loc)
		{
			this.locale = loc;
		}
	}
	
	public static void setupLocalizationInfo()
	{
		for (Locale l : Locale.values())
		{
			LanguageRegistry.instance().loadLocalization(CommonProxy.TCBEES_LOCDIR + l.locale + ".xml", l.locale, true);
		}
	}

	public static String getLocalizedString(String key)
	{
		String result = LanguageRegistry.instance().getStringLocalization(key);
		if (result.isEmpty())
		{
			result = LanguageRegistry.instance().getStringLocalization(key, "en_US");
		}
		return result;
	}
}
