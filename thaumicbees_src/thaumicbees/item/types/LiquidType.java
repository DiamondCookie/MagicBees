package thaumicbees.item.types;

import thaumicbees.main.utils.LocalizationManager;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraftforge.liquids.LiquidDictionary;

public enum LiquidType
{
	// Incidentally, Item meta is the liquid ID.
	
	// Args are: forgeLiquidID, Display Name, Icon idx
	EMPTY("", 16),
	
	// Vanilla
	WATER("water", 17),
	LAVA("lava", 18),
	
	// Forestry
	BIOMASS("biomass", 19),
	BIOFUEL("biofuel", 20),
	
	// Buildcraft
	OIL("Oil", 21),
	FUEL("Fuel", 22),
	
	// More Forestry
	SEEDOIL("seedoil", 23),
	HONEY("honey", 24),
	JUICE("juice", 25),
	CRUSHEDICE("ice", 26),
	MILK("milk", 27),
	
	// ExtraBees liquids
	ACID("acid", 28),
	POISON("poison", 29),
	LIQUIDNITROGEN("liquidNitrogen", 30),
	DNA("liquidDNA", 31),
	
	// Railcraft
	CREOSOTEOIL("Creosote Oil", 32),
	STEAM("Steam", 33),
	;
	public String liquidID;
	public int iconIdx;
	public boolean available = false;
	
	private LiquidType(String l, int idx)
	{
		this.liquidID = l;
		this.iconIdx = idx;
	}

	public String getDisplayName()
	{
		return LocalizationManager.getLocalizedString("tb.liquid." + liquidID);
	}
}
