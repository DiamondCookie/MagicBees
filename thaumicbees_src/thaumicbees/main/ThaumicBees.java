package thaumicbees.main;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import thaumicbees.bees.TBBeeManager;
import thaumicbees.compat.CompatabilityManager;
import thaumicbees.main.utils.CraftingManager;
import thaumicbees.main.utils.VersionInfo;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import forestry.api.core.ItemInterface;

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
	private Config modConfig;
	private TBBeeManager plugin;

	public ThaumicBees()
	{
		this.plugin = new TBBeeManager();
	}

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		this.configsPath = event.getModConfigurationDirectory().getAbsolutePath();
		this.modConfig = new Config(event.getSuggestedConfigurationFile());
		
		CompatabilityManager.registerResearch();
	}

	@Mod.Init
	public void init(FMLInitializationEvent event)
	{
		// Check on ExtraBees & enable content. =D
		this.modConfig.ExtraBeesInstalled = cpw.mods.fml.common.Loader.isModLoaded("ExtraBees");
		
		this.proxy.preloadTextures();
		try
		{
			FMLLog.info("[ThaumicBees] Attempting to get Forestry's item graphics file...");
			// Grab Forestry graphics file path
			proxy.FORESTRY_GFX_ITEMS = ItemInterface.getItem("beeComb").getItem().getTextureFile();
		}
		catch (Exception e)
		{
			FMLLog.severe("ThaumicBees encountered a problem while loading!");
			throw new RuntimeException("Could not get the Forestry item texture file! Did Forestry load correctly?", e);
		}
		
		this.modConfig.setupBlocks();
		this.modConfig.setupItems();
		
		CompatabilityManager.setupBackpacks();
		
		plugin.doInit();
	}

	@Mod.PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		plugin.postInit();
		this.modConfig.saveConfigs();
		
		CompatabilityManager.setupItemAspects();
		
		// Vanilla, Forestry & Thaumcraft crafting setup
		CraftingManager.setupCrafting();
		
		// Forestry has init'd by this point.
		MinecraftForge.EVENT_BUS.register(this);
		
		
		CompatabilityManager.setupResearch();
	}
	
	public static Config getConfig()
	{
		return object.modConfig;
	}
}
