package magicbees.main.utils.compat;

import magicbees.bees.BeeManager;
import magicbees.main.Config;
import magicbees.main.utils.BlockInterface;
import magicbees.main.utils.ItemInterface;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.IAllele;

public class ForestryHelper
{
	public enum BlockResource
	{
		APATITE,
		COPPER,
		TIN,;
	}

	public enum CraftingMaterial
	{
		PULSATING_DUST, // unused
		PULSATING_MESH,
		SILK_WISP,
		WOVEN_SILK,
		DISSIPATION_CHARGE,
		ICE_SHARD,
		SCENTED_PANELING,;
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
		MOSSY,;
	}

	public enum Propolis
	{
		NORMAL,
		STICKY, // Unused.
		PULSATING,
		SILKY,;
	}

	public enum Pollen
	{
		NORMAL,
		CRYSTALLINE,;
	}

	public enum CircuitBoard
	{
		BASIC,
		ENHANCED,
		REFINED,
		INTRICATE,;
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
		LAPIS,;
	}

	public static final String Name = "Forestry";

	public static void preInit()
	{
	}

	public static void init()
	{
		getBlocks();
		getItems();
	}

	public static void postInit()
	{
	}

	private static void getBlocks()
	{
		try
		{
			Class c = Class.forName("forestry.core.config.ForestryBlock");
			Config.fAlvearyBlock = BlockInterface.getBlock("alveary");
			Config.fHiveBlock = BlockInterface.getBlock("beehives");
		}
		catch (Exception e)
		{

		}
	}

	private static void getItems()
	{
		Config.fBeeComb = ItemInterface.getItem("beeCombs");
		Config.fPollen = ItemInterface.getItem("pollen");
		Config.fCraftingResource = ItemInterface.getItem("craftingMaterial");
		Config.fHoneyDrop = ItemInterface.getItem("honeyDrop");
		Config.fHoneydew = ItemInterface.getItem("honeydew");
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
