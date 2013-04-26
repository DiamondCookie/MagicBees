package thaumicbees.main.utils.compat;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import thaumicbees.main.Config;
import cpw.mods.fml.common.Loader;

public class ArsMagicaHelper
{
	private static boolean isArsMagicaPresent = false;
	
	public static boolean isActive()
	{
		return isArsMagicaPresent;
	}
	
	public static void init()
	{
		if (Loader.isModLoaded("ArsMagica"))
		{
			isArsMagicaPresent = true;
		}
	}
	
	public static void getBlocks()
	{
		if (isActive())
		{
			try
			{
				Class clazz = Class.forName("mithion.arsmagica.blocks.ArsMagicaBlocksCommonProxy");
				Config.amBlackOrchid = (Block)(clazz.getField("blueOrchid").get(null));
				Config.amDesertNova = (Block)(clazz.getField("desertNova").get(null));
			}
			catch (Exception e)
			{
				
			}
		}
	}
	
	public static void getItems()
	{
		if (isActive())
		{
			try
			{
				Class clazz = Class.forName("mithion.arsmagica.items.ArsMagicaItemsCommonProxy");
				Config.amVinteumDust = (Item)(clazz.getField("vinteumDust").get(null));
				Config.amArcaneCompound = (Item)(clazz.getField("arcaneCompound").get(null));
				Config.amEssenceAir = (Item)(clazz.getField("airEssence").get(null));
				Config.amEssenceArcane = (Item)(clazz.getField("arcaneEssence").get(null));
				Config.amEssenceEarth = (Item)(clazz.getField("earthEssence").get(null));
				Config.amEssenceFire = (Item)(clazz.getField("fireEssence").get(null));
				Config.amEssenceIce = (Item)(clazz.getField("iceEssence").get(null));
				Config.amEssenceLightning = (Item)(clazz.getField("lightningEssence").get(null));
				Config.amEssenceMagma = (Item)(clazz.getField("magmaEssence").get(null));
				Config.amEssencePlant = (Item)(clazz.getField("plantEssence").get(null));
				Config.amEssenceWater = (Item)(clazz.getField("waterEssence").get(null));
			}
			catch (Exception e)
			{
				
			}
		}
	}
}
