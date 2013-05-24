package magicbees.main.utils.compat;

import java.lang.reflect.Field;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ExtraBeesHelper
{
	private static boolean isEBPresent = false;
	
	public static boolean isActive()
	{
		return isEBPresent;
	}
	
	public static void init()
	{
		if (cpw.mods.fml.common.Loader.isModLoaded("ExtraBees"))
		{
			isEBPresent = true;
		}
	}
	
	public static ItemStack getExtraBeeItem(String field)
	{
		ItemStack value = null;
		try
		{
			Class src = Class.forName("binnie.extrabees.core.ExtraBeeItem");
			Field f = src.getDeclaredField(field);
			
			Item i = (Item) f.get(null);
			value = new ItemStack(i);
		}
		catch (Exception e) { }
		return value;
	}

	public enum CombType
	{
		BARREN,
		ROTTEN,
		BONE,
		OIL,
		COAL,
		FUEL,
		WATER,
		MILK,
		FRUIT,
		SEED,
		ALCOHOL,
		STONE,
		REDSTONE,
		RESIN,
		IC2,
		IRON,
		GOLD,
		COPPER,
		TIN,
		SILVER,
		BRONZE,
		URANIUM,
		CLAY,
		OLD,
		FUNGAL,
		CREOSOTE,
		LATEX,
		ACIDIC,
		VENOMOUS,
		SLIME,
		BLAZE,
		COFFEE,
		GLACIAL,
		MINT,
		CITRUS,
		PEAT,
		SHADOW,
		LEAD,
		BRASS,
		ELECTRUM,
		ZINC,
		TITANIUM,
		TUNGSTEN,
		STEEL,
		IRIDIUM,
		PLATINUM,
		LAPIS,
		SODALITE,
		PYRITE,
		BAUXITE,
		CINNABAR,
		SPHALERITE,
		EMERALD,
		RUBY,
		SAPPHIRE,
		OLIVINE,
		DIAMOND,
		RED,
		YELLOW,
		BLUE,
		GREEN,
		BLACK,
		WHITE,
		BROWN,
		ORANGE,
		CYAN,
		PURPLE,
		GRAY,
		LIGHTBLUE,
		PINK,
		LIMEGREEN,
		MAGENTA,
		LIGHTGRAY,		
		NICKEL,
		INVAR,
		GLOWSTONE,
		SALTPETER,
		PULP,
		MULCH,
		COMPOST,
		SAWDUST,
		;
	}
}
