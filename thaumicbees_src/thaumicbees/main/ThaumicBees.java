package thaumicbees.main;

import thaumicbees.gui.GUIHandler;
import thaumicbees.main.utils.CompatabilityManager;
import thaumicbees.main.utils.CraftingManager;
import thaumicbees.main.utils.LocalizationManager;
import thaumicbees.main.utils.VersionInfo;
import thaumicbees.main.utils.compat.EquivalentExchangeHelper;
import thaumicbees.main.utils.compat.ExtraBeesHelper;
import thaumicbees.main.utils.compat.ThaumcraftHelper;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(
		modid=VersionInfo.ModName,
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

	public GUIHandler guiHandler;
	private String configsPath;
	private Config modConfig;

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		LocalizationManager.setupLocalizationInfo();
		
		this.configsPath = event.getModConfigurationDirectory().getAbsolutePath();
		this.modConfig = new Config(event.getSuggestedConfigurationFile());
		
		// Compatibility Helpers setup time.
		ThaumcraftHelper.init();
		ExtraBeesHelper.init();
		EquivalentExchangeHelper.init();
	}

	@Mod.Init
	public void init(FMLInitializationEvent event)
	{		
		this.modConfig.setupBlocks();
		this.modConfig.registerTileEntities();
		this.modConfig.setupItems();
		
		CompatabilityManager.setupBackpacks();
	}

	@Mod.PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		this.guiHandler = new GUIHandler();
		NetworkRegistry.instance().registerGuiHandler(this, this.guiHandler);
		
		thaumicbees.bees.Allele.setupAdditionalAlleles();
		thaumicbees.bees.BeeSpecies.setupBeeSpecies();
		thaumicbees.bees.BeeMutation.setupMutations();
		
		this.modConfig.saveConfigs();
		
		ThaumcraftHelper.setupItemAspects();
		
		// Vanilla, Forestry & Thaumcraft crafting setup
		CraftingManager.setupCrafting();
		
		ThaumcraftHelper.setupResearch();
		
		VersionInfo.doVersionCheck();
	}
	
	public static Config getConfig()
	{
		return object.modConfig;
	}
}
