package magicbees.main.utils.compat;

import java.util.ArrayList;

import magicbees.item.types.PollenType;
import magicbees.item.types.PropolisType;
import magicbees.item.types.ResourceType;
import magicbees.item.types.WaxType;
import magicbees.main.CommonProxy;
import magicbees.main.Config;
import magicbees.main.MagicBees;
import magicbees.main.utils.LocalizationManager;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.core.ItemInterface;

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

