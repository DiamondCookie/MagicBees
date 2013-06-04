package magicbees.main;

import magicbees.bees.BeeManager;
import magicbees.client.gui.GUIHandler;
import magicbees.main.utils.CompatabilityManager;
import magicbees.main.utils.CraftingManager;
import magicbees.main.utils.LocalizationManager;
import magicbees.main.utils.VersionInfo;
import magicbees.main.utils.compat.ArsMagicaHelper;
import magicbees.main.utils.compat.EquivalentExchangeHelper;
import magicbees.main.utils.compat.ExtraBeesHelper;
import magicbees.main.utils.compat.ThaumcraftHelper;
import magicbees.world.WorldGeneratorHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(
		modid=VersionInfo.ModName,
		name="Magic Bees",
		useMetadata=true,
		acceptedMinecraftVersions=VersionInfo.MCVersion,
		version=VersionInfo.Version + " (build " + VersionInfo.Build + ")",
		dependencies=VersionInfo.Depends
)
@NetworkMod(serverSideRequired=false, clientSideRequired=true)
public class MagicBees
{

	@Mod.Instance(VersionInfo.ModName)
	public static MagicBees object;
	
	@SidedProxy(serverSide="magicbees.main.CommonProxy", clientSide="magicbees.main.ClientProxy")
	public static CommonProxy proxy;

	public GUIHandler guiHandler;
	private String configsPath;
	private Config modConfig;
	private WorldGeneratorHandler worldHandler;

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
		ArsMagicaHelper.init();
	}

	@Mod.Init
	public void init(FMLInitializationEvent event)
	{		
		this.modConfig.setupBlocks();
		this.modConfig.setupItems();
		
		CompatabilityManager.setupBackpacks();
		
		GameRegistry.registerWorldGenerator(worldHandler = new WorldGeneratorHandler());
	}

	@Mod.PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		this.guiHandler = new GUIHandler();
		NetworkRegistry.instance().registerGuiHandler(this, this.guiHandler);
		
		proxy.registerRenderers();

		BeeManager.ititializeBees();
		
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
