package thaumicbees.main;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.LanguageRegistry;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.core.ItemInterface;

import java.io.InputStream;
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
import thaumicbees.thaumcraft.ThaumcraftCompat;

@Mod(
		modid="ThaumicBees",
		useMetadata=true,
		acceptedMinecraftVersions=VersionInfo.MCVersion,
		version=VersionInfo.Version,
		dependencies=VersionInfo.Depends
)
public class ThaumicBees
{

	@Mod.Instance(value="ThaumicBees")
	public static ThaumicBees object;
	
	@SidedProxy(serverSide="thaumicbees.main.CommonProxy", clientSide="thaumicbees.main.ClientProxy")
	public static CommonProxy proxy;

	private String configsPath;
	private Configuration config;
	private ThaumicBeesConfiguration modConfig;
	private ThaumicBeesPlugin plugin;

	public ThaumicBees()
	{
		this.modConfig = new ThaumicBeesConfiguration();
		
		this.modConfig.ThaumcraftRecipesAdded = false;
		this.plugin = new ThaumicBeesPlugin();
	}

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		this.configsPath = event.getModConfigurationDirectory().getAbsolutePath();
		this.config = new Configuration(event.getSuggestedConfigurationFile());
		
		this.config.load();
		// Allow us to set up some extra stuff.
		this.modConfig.doMiscConfig(this.config);
		plugin.preInit();
		
		ThaumcraftCompat.registerResearch();
	}

	@Mod.Init
	public void init(FMLInitializationEvent event)
	{
		// Check on ExtraBees & enable content. =D
		this.modConfig.ExtraBeesInstalled = cpw.mods.fml.common.Loader.isModLoaded("ExtraBees");
		
		// Give ThaumcraftCompat a chance to set itself up.
		ThaumcraftCompat.parseThaumcraftConfig(configsPath);
		this.proxy.preloadTextures();
		
		// Grab Forestry graphics file path
		proxy.FORESTRY_GFX_ITEMS = ItemInterface.getItem("beeComb").getItem().getTextureFile();
		
		BlockManager.setupBlocks(this.config);
		ItemManager.setupItems(this.config);
		this.config.save();
		
		
		// Vanilla & Forestry mechanics setup
		ItemManager.setupCrafting();
		// Thaumcraft-specific crafting (Infusions, & Vis-needing)
		ThaumcraftCompat.setupCrafting();
		
		ThaumcraftCompat.setupBackpacks();
		ThaumcraftCompat.setupItemAspects();
		
		plugin.doInit();
	}

	@Mod.PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		// Item Manager has a couple tasks left to it at this point...
		ItemManager.postInit();
		
		// Forestry has init'd by this point.
		MinecraftForge.EVENT_BUS.register(this);
		
		plugin.postInit();
		
		ThaumcraftCompat.setupResearch();
	}

	@ForgeSubscribe
	public void worldLoad(net.minecraftforge.event.world.WorldEvent.Load event)
	{
		if (!this.modConfig.ThaumcraftRecipesAdded)
		{
			ThaumcraftCompat.setupBeeInfusions(event.world);
			this.modConfig.ThaumcraftRecipesAdded = true;
		}
	}
	
	public static ThaumicBeesConfiguration getInstanceConfig()
	{
		return object.modConfig;
	}
}
