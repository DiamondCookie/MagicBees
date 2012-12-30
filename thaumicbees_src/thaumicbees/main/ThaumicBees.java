package thaumicbees.main;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.core.ItemInterface;

import java.io.PrintStream;
import java.lang.reflect.Field;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import net.minecraftforge.event.EventBus;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;
import thaumcraft.api.*;
import thaumicbees.bees.ThaumicBeesPlugin;
import thaumicbees.bees.genetics.BeeSpecies;
import thaumicbees.block.BlockManager;
import thaumicbees.item.ItemComb;
import thaumicbees.item.ItemWax;
import thaumicbees.item.ItemManager;

@Mod(
		modid="ThaumicBees",
		useMetadata=true,
		acceptedMinecraftVersions="1.4.6",
		version="@VERSION@-@BUILD_NUMBER@",
		dependencies="required-after:Forestry@[1.6.4.2,);required-after:Thaumcraft@[3.0.1c,)"
)
public class ThaumicBees
{

	@Mod.Instance(value="ThaumicBees")
	public static ThaumicBees object;
	
	@SidedProxy(serverSide="thaumicbees.main.CommonProxy", clientSide="thaumicbees.main.ClientProxy")
	public static CommonProxy proxy;
	
	private boolean ThaumcraftRecipesAdded;
	private Configuration config;
	public ConfigFlags configFlags;

	public ThaumicBees()
	{
		ThaumcraftRecipesAdded = false;
		this.configFlags = new ConfigFlags();
	}

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		this.config = new Configuration(event.getSuggestedConfigurationFile());
	}

	@Mod.Init
	public void init(FMLInitializationEvent event)
	{
		// Give ThaumcraftCompat a chance to set itself up.
		ThaumcraftCompat.init();
		this.proxy.preloadTextures();
		
		// Grab Forestry graphics file path
		proxy.FORESTRY_GFX_ITEMS = ItemInterface.getItem("beeComb").getItem().getTextureFile();
		
		this.config.load();
		BlockManager.setupBlocks(this.config);
		ItemManager.setupItems(this.config);
		this.config.save();
		
		ItemManager.setupCrafting();
		
		ThaumcraftCompat.setupResearch();
		ThaumcraftCompat.setupAspects();
	}

	@Mod.PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@ForgeSubscribe
	public void worldLoad(net.minecraftforge.event.world.WorldEvent.Load event)
	{
		if (!ThaumcraftRecipesAdded)
		{
			ThaumicBeesPlugin.setupBeeInfusions(event.world);
			ThaumcraftRecipesAdded = true;
		}
	}
}
