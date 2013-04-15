package thaumicbees.main.utils.compat;

import net.minecraft.item.Item;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.ItemInterface;
import thaumicbees.main.Config;

public class ForestryHelper
{	
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
		
	}
	
	public static void getItems()
	{
		Config.fBeeComb = Item.itemsList[ItemInterface.getItem("beeComb").itemID];
		Config.fPollen = Item.itemsList[ItemInterface.getItem("pollen").itemID];
		Config.fCraftingResource = Item.itemsList[ItemInterface.getItem("craftingMaterial").itemID];
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
