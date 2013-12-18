package magicbees.item.types;

import cpw.mods.fml.common.registry.LanguageRegistry;
import magicbees.main.utils.LocalizationManager;
import net.minecraft.util.Icon;
import net.minecraftforge.liquids.LiquidDictionary;

public enum FluidType
{
	// Incidentally, Item meta is the liquid ID.
	
	// Args are: forgeLiquidID, Display Name, Icon idx
	EMPTY(""),
	
	// Vanilla
	WATER("water"),
	LAVA("lava"),
	
	// Forestry
	BIOMASS("biomass"),
	ETHANOL("bioethanol"),
	
	// Buildcraft
	OIL("oil"),
	FUEL("fuel"),
	
	// More Forestry
	SEEDOIL("seedoil"),
	HONEY("honey"),
	JUICE("juice"),
	CRUSHEDICE("ice"),
	MILK("milk"),
	
	// ExtraBees liquids
	ACID("acid"),
	POISON("poison"),
	LIQUIDNITROGEN("liquidnitrogen"),
	DNA("liquiddna"),
	
	// Railcraft
	CREOSOTEOIL("creosote"),
	STEAM("steam"),
	;
	public String liquidID;
	public int iconIdx;
	public boolean available = false;
	public Icon liquidIcon;
	
	private FluidType(String l)
	{
		this.liquidID = l;
	}

	public String getDisplayName()
	{
		return LocalizationManager.getLocalizedString("liquid." + liquidID);
	}
}
