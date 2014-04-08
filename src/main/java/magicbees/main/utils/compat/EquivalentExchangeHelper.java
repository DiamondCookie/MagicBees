package magicbees.main.utils.compat;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import magicbees.main.Config;
import net.minecraft.item.Item;

public class EquivalentExchangeHelper
{
	private static boolean isEquivalentExchangePresent = false;
	
	public static boolean isActive()
	{
		return isEquivalentExchangePresent;
	}
	
	public static void preInit()
	{
		if (Loader.isModLoaded("EE3"))
		{
			isEquivalentExchangePresent = true;
		}
	}
	
	public static void init()
	{
		if (isActive())
		{
			getBlocks();
			getItems();
		}
	}
	
	public static void postInit() {}
	
	private static void getBlocks() {}
	
	private static void getItems()
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
