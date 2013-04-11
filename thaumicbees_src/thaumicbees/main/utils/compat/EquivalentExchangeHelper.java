package thaumicbees.main.utils.compat;

import net.minecraft.item.Item;
import cpw.mods.fml.common.FMLLog;
import thaumicbees.main.Config;

public class EquivalentExchangeHelper
{
	private static boolean isEquivalentExchangePresent = false;
	
	public static boolean isActive()
	{
		return isEquivalentExchangePresent;
	}
	
	public static void init()
	{
		if (cpw.mods.fml.common.Loader.isModLoaded("Thaumcraft"))
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
