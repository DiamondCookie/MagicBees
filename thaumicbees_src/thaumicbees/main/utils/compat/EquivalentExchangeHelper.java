package thaumicbees.main.utils.compat;

import net.minecraft.item.Item;
import thaumicbees.main.Config;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class EquivalentExchangeHelper
{
	private static boolean isEquivalentExchangePresent = false;
	
	public static boolean isActive()
	{
		return isEquivalentExchangePresent;
	}
	
	public static void init()
	{
		if (Loader.isModLoaded("EE3"))
		{
			isEquivalentExchangePresent = true;
		}
	}
	
	public static void getBlocks()
	{
		if (isActive())
		{
			
		}
	}
	
	public static void getItems()
	{
		if (isActive())
		{
			try
			{
				Class itemClass = Class.forName("com.pahimar.ee3.item.ModItems");
				Config.eeMinuimShard = (Item)(itemClass.getField("miniumShard").get(null));
			}
			catch (Exception e)
			{
				FMLLog.info("Could not get Equivalent Exchange items.");
			}
		}
	}
}
