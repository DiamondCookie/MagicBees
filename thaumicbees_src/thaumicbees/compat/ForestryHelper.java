package thaumicbees.compat;

import net.minecraft.item.Item;
import forestry.api.core.ItemInterface;
import thaumicbees.main.Config;

public class ForestryHelper
{
	public static void getForestryBlocks()
	{
		
	}
	
	public static void getForestryItems()
	{
		Config.fBeeComb = Item.itemsList[ItemInterface.getItem("beeComb").itemID];
		Config.fPollen = Item.itemsList[ItemInterface.getItem("pollen").itemID];
		Config.fCraftingResource = Item.itemsList[ItemInterface.getItem("craftingMaterial").itemID];
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
		LAPIS
	}
}
