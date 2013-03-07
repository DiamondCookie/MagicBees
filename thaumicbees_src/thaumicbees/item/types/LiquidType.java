package thaumicbees.item.types;

public enum LiquidType
{
	// Incidentally, Item meta is the liquid ID.
	
	// Args are: forgeLiquidID, Display Name, Icon idx
	EMPTY("", "Empty",  16),
	
	// Vanilla
	WATER("water", "Water", 17),
	LAVA("lava", "Lava", 18),
	
	// Forestry
	BIOMASS("biomass", "Biomass", 19),
	BIOFUEL("biofuel", "Biofuel", 20),
	
	// Buildcraft
	OIL("Oil", "Oil", 21),
	FUEL("Fuel", "Fuel", 22),
	
	// More Forestry
	SEEDOIL("seedoil", "Seed Oil", 23),
	HONEY("honey", "Honey", 24),
	JUICE("juice", "Juice", 25),
	CRUSHEDICE("ice", "Crushed Ice", 26),
	MILK("milk", "Milk", 27),
	
	// ExtraBees liquids
	ACID("acid", "Acid", 28),
	POISON("poison", "Poison", 29),
	LIQUIDNITROGEN("liquidNitrogen", "Liquid Nitrogen", 30),
	DNA("liquidDNA", "Liquid DNA", 31),
	
	// Railcraft
	CREOSOTEOIL("Creosote Oil", "Creosote Oil", 32),
	STEAM("Steam", "Steam", 33),
	;
	public String liquidID;
	public String displayName;
	public int iconIdx;
	public boolean available = false;
	
	private LiquidType(String l, String display, int idx)
	{
		this.liquidID = l;
		this.displayName = display;
		this.iconIdx = idx;
	}
}
