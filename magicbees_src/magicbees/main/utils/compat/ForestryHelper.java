package magicbees.main.utils.compat;

import java.lang.reflect.Method;

import magicbees.main.Config;
import net.minecraft.block.Block;
import cpw.mods.fml.common.FMLLog;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.ItemInterface;
import forestry.api.genetics.IAllele;

public class ForestryHelper
{
	public enum BlockResource
	{
		APATITE,
		COPPER,
		TIN,
		;
	}
	
	public enum CraftingMaterial
	{
		PULSATING_DUST, // unused
		PULSATING_MESH,
		SILK_WISP,
		WOVEN_SILK,
		DISSIPATION_CHARGE,
		ICE_SHARD,
		SCENTED_PANELING,
		;
	}
	
	public enum Comb
	{
		HONEY,
		COCOA,
		SIMMERING,
		STRINGY,
		FROZEN,
		DRIPPING,
		SILKY,
		PARCHED,
		MYSTERIOUS,
		IRRADIATED,
		POWDERY,
		REDDENED,
		DARKENED,
		OMEGA,
		WHEATEN,
		MOSSY,
		;
	}
	
	public enum Propolis
	{
		NORMAL,
		STICKY, // Unused.
		PULSATING,
		SILKY,
		;
	}
	
	public enum Pollen
	{
		NORMAL,
		CRYSTALLINE,
		;
	}
	
	public enum CircuitBoard
	{
		BASIC,
		ENHANCED,
		REFINED,
		INTRICATE,
		;
	}
	
	public enum Tube
	{
		COPPER,
		TIN,
		BRONZE,
		IRON,
		GOLD,
		DIAMOND,
		OBSIDIAN,
		BLAZE,
		RUBBER,
		EMERALD,
		APATITE,
		LAPIS,
		;
	}
	
	public static final String Name = "Forestry";
	
	public static void getBlocks()
	{
		try
		{
			Class c = Class.forName("forestry.core.config.ForestryBlock");
			Config.fAlvearyBlock = (Block)(c.getField("alveary").get(null));
		}
		catch (Exception e)
		{
			
		}
	}
	
	public static void getItems()
	{
		Config.fBeeComb = ItemInterface.getItem("beeComb").getItem();
		Config.fPollen = ItemInterface.getItem("pollen").getItem();
		Config.fCraftingResource = ItemInterface.getItem("craftingMaterial").getItem();
		Config.fHoneyDrop = ItemInterface.getItem("honeyDrop").getItem();
		Config.fHoneydew = ItemInterface.getItem("honeydew").getItem();
	}

	public static IAllele[] getTemplateForestryForSpecies(String speciesName)
	{
		IAllele[] template = null;
		try
		{
			//BeeTemplates.getValiantTemplate()
			Class c = Class.forName("forestry.apiculture.genetics.BeeTemplates");
			Method m = c.getMethod("get" + speciesName + "Template");
			template = (IAllele[])(m.invoke(null));
		}
		catch (Exception e)
		{
			FMLLog.severe("Could not get Forestry template for %s.", speciesName);
			e.printStackTrace();
		}
		return template;
	}
	
	public static EnumTemperature getEnumTemperatureFromValue(float rawTemp)
	{
		EnumTemperature value = EnumTemperature.ICY;
		
		if (rawTemp >= 2.0f)
		{
			value = EnumTemperature.HOT;
		}
		else if (rawTemp >= 1.2f)
		{
			value = EnumTemperature.WARM;
		}
		else if (rawTemp >= 0.2f)
		{
			value = EnumTemperature.NORMAL;
		}
		else if (rawTemp >= 0.05f)
		{
			value = EnumTemperature.COLD;
		}

		return value;
	}
	
	public static EnumHumidity getEnumHumidityFromValue(float rawHumidity)
	{
		EnumHumidity value = EnumHumidity.ARID;
		
		if (rawHumidity >= 0.9f)
		{
			value = EnumHumidity.DAMP;
		}
		else if (rawHumidity >= 0.3f)
		{
			value = EnumHumidity.NORMAL;
		}

		return value;
	}
}
