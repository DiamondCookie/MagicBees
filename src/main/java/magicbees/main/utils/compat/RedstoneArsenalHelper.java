package magicbees.main.utils.compat;

import cpw.mods.fml.common.registry.GameRegistry;
import magicbees.main.Config;
import net.minecraft.item.ItemStack;

public class RedstoneArsenalHelper
{
	public enum MiscResource
	{
		FLUXED_ELECTRUMBLOCK,		
		;
	}
	
	public enum NuggetType
	{
		ELECTRUMFLUX,		
		;
	}
	
	
		
	public static final String Name = "RedstoneArsenal";
	private static boolean isRedstoneArsenalPresent = false;
	
	public static boolean isActive()
	{
		return isRedstoneArsenalPresent;
	}
	
	public static void preInit()
	{
		if (cpw.mods.fml.common.Loader.isModLoaded(Name))
		{
			isRedstoneArsenalPresent = true;
		}
		else
		{
			// Switch off RSA-dependant items.
			
		}
	}
	
	public static void init()
	{
		//if (isActive()) { }
	}
	
	public static void postInit()
	{
		if (isActive())
		{
			// Apparently the Game Registry isn't populated until now. ):
			getBlocks();
			getItems();
			
			//addItemsToBackpack();			
			setupCrafting();
			
		}
	}
	
	
	
	private static void getBlocks()
	{
		Config.rsaFluxBlock = GameRegistry.findItemStack("RedstoneArsenal", "blockElectrumFlux", 1);			
	}
	
	private static void getItems()
	{
		Config.rsaFluxNugget = GameRegistry.findItemStack("RedstoneArsenal", "nuggetElectrumFlux", 1);		
	}
	
	
	private static void setupCrafting()
	{
		ItemStack input, output;
		
		
		};	
		
	}

