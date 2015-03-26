package magicbees.main.utils.compat;

import java.util.Locale;

import magicbees.bees.BeeManager;
import magicbees.bees.HiveDescription;
import magicbees.block.types.HiveType;
import magicbees.main.Config;
import magicbees.main.utils.BlockInterface;
import magicbees.main.utils.ItemInterface;
import magicbees.main.utils.VersionInfo;
import forestry.api.apiculture.hives.HiveManager;
import forestry.api.apiculture.hives.IHiveRegistry;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.EnumTolerance;
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
	
	public enum ApicultureBlock {
		APIARY,
		;
	}

	public static final String Name = "Forestry";

	public static void preInit()
	{
		IHiveRegistry hiveRegistry = HiveManager.hiveRegistry;

		HiveDescription.initHiveData();

		registerHive(hiveRegistry, HiveDescription.CURIOUS);
		registerHive(hiveRegistry, HiveDescription.UNUSUAL);
		registerHive(hiveRegistry, HiveDescription.RESONANT);
		registerHive(hiveRegistry, HiveDescription.DEEP);
		registerHive(hiveRegistry, HiveDescription.INFERNAL);
		registerHive(hiveRegistry, HiveDescription.OBLIVION);

		if (Config.doSpecialHiveGen)
		{
			registerHive(hiveRegistry, HiveDescription.INFERNAL_OVERWORLD);
			registerHive(hiveRegistry, HiveDescription.OBLIVION_OVERWORLD);
		}
	}

	private static void registerHive(IHiveRegistry hiveRegistry, HiveDescription hiveDescription) {
		String hiveName = VersionInfo.ModName + ":" + hiveDescription.toString().toLowerCase(Locale.ENGLISH);
		hiveRegistry.registerHive(hiveName, hiveDescription);
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
			Config.fApicultureBlock = BlockInterface.getBlock("apiculture");
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

	public static boolean isHumidityWithinTolerance(EnumHumidity humid, EnumHumidity beeHumidityBase, EnumTolerance value) {
		// TODO Auto-generated method stub
		return false;
	}
}
