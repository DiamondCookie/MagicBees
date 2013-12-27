package magicbees.main.utils.compat;

import magicbees.main.Config;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Loader;

public class ArsMagicaHelper
{
	public enum ResourceType
	{
		VINTEUM_DUST,
		ARCANE_COMPOUND,
		ARCANE_ASH,
		PURIFIED_VINTEUM,
		CHIMERITE,
		BLUE_TOPAZ,
		SUNSTONE,
		MOONSTONE,
	}
	
	public enum EssenceType
	{
		ARCANE,
		EARTH,
		AIR,
		FIRE,
		WATER,
		PLANT,
		ICE,
		LIGHTNING,
		LIFE,
		ENDER,
		PURE,
		HIGH,
		BASE,
	}
	
	private static boolean isArsMagicaPresent = false;
	public static final String Name = "arsmagica2";
	
	public static boolean isActive()
	{
		return isArsMagicaPresent;
	}
	
	public static void preInit()
	{
		if (Loader.isModLoaded(Name))
		{
			isArsMagicaPresent = true;
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
	
	private static void getBlocks()
	{
		try
		{
			Class clazz = Class.forName("am2.blocks.BlocksCommonProxy");
			Config.amResourceBlock = (Block)(clazz.getField("AMOres").get(null));
			Config.amBlackOrchid = (Block)(clazz.getField("blueOrchid").get(null));
			Config.amDesertNova = (Block)(clazz.getField("desertNova").get(null));
			Config.amAum = (Block)(clazz.getField("aum").get(null));
			Config.amWakebloom = (Block)(clazz.getField("wakebloom").get(null));
			Config.amTarmaRoot = (Block)(clazz.getField("tarmaRoot").get(null));
		}
		catch (Exception e)
		{
			
		}
	}
	
	private static void getItems()
	{
		try
		{
			Class clazz = Class.forName("am2.items.ItemsCommonProxy");
			Config.amItemResource = (Item)(clazz.getField("itemOre").get(null));
			Config.amEssence = (Item)(clazz.getField("essence").get(null));
		}
		catch (Exception e)
		{
			
		}
	}
}
