package thaumicbees.main;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import thaumicbees.main.utils.CompatabilityManager;
import thaumicbees.main.utils.CraftingManager;
import thaumicbees.main.utils.LocalizationManager;
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
		name="Thaumic Bees",
		useMetadata=true,
		acceptedMinecraftVersions=VersionInfo.MCVersion,
		version=VersionInfo.Version + " - " + VersionInfo.Build,
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

	public ThaumicBees()
	{
	}

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		LocalizationManager.setupLocalizationInfo();
		
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
		this.modConfig.registerTileEntities();
		this.modConfig.setupItems();
		
		CompatabilityManager.setupBuildCraftFacades();
		CompatabilityManager.setupBackpacks();
	}

	@Mod.PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		this.setupBees();
		this.modConfig.saveConfigs();
		
		CompatabilityManager.setupItemAspects();
		
		// Vanilla, Forestry & Thaumcraft crafting setup
		CraftingManager.setupCrafting();
		
		// Forestry has init'd by this point.
		MinecraftForge.EVENT_BUS.register(this);
		
		CompatabilityManager.setupResearch();
		
		VersionInfo.doVersionCheck();
	}
	
	public static Config getConfig()
	{
		return object.modConfig;
	}
	
	private void setupBees()
	{
		thaumicbees.bees.Allele.setupAdditionalAlleles();
		thaumicbees.bees.BeeSpecies.setupBeeSpecies();
		thaumicbees.bees.BeeMutation.setupMutations();
	}
}
