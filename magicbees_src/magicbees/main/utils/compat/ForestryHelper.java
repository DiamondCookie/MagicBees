package magicbees.main.utils.compat;

import java.lang.reflect.Method;

import magicbees.bees.BeeManager;
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
	
	public static void preInit() {}
	
	public static void init()
	{
		getBlocks();
		getItems();
	}
	
	public static void postInit() {}
	
	private static void getBlocks()
	{
		try
		{
			Class c = Class.forName("forestry.core.config.ForestryBlock");
			Config.fAlvearyBlock = (Block)(c.getField("alveary").get(null));
			Config.fHiveBlock = (Block)(c.getField("beehives").get(null));
		}
		catch (Exception e)
		{
			
		}
	}
	
	private static void getItems()
	{
		Config.fBeeComb = ItemInterface.getItem("beeComb").getItem();
		Config.fPollen = ItemInterface.getItem("pollen").getItem();
		Config.fCraftingResource = ItemInterface.getItem("craftingMaterial").getItem();
		Config.fHoneyDrop = ItemInterface.getItem("honeyDrop").getItem();
		Config.fHoneydew = ItemInterface.getItem("honeydew").getItem();
	}

	public static IAllele[] getTemplateForestryForSpecies(String speciesName)
	{
		return BeeManager.beeRoot.getTemplate("forestry.species" + speciesName);
	}
	
	public static EnumTemperature getEnumTemperatureFromValue(float rawTemp)
	{
		return EnumTemperature.getFromValue(rawTemp);
	}
	
	public static EnumHumidity getEnumHumidityFromValue(float rawHumidity)
	{
		return EnumHumidity.getFromValue(rawHumidity);
	}
}
