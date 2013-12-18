package magicbees.main;

import magicbees.bees.BeeManager;
import magicbees.bees.TransmutationEffectController;
import magicbees.client.gui.GUIHandler;
import magicbees.main.utils.CraftingManager;
import magicbees.main.utils.IMCManager;
import magicbees.main.utils.LocalizationManager;
import magicbees.main.utils.VersionInfo;
import magicbees.main.utils.compat.ArsMagicaHelper;
import magicbees.main.utils.compat.EquivalentExchangeHelper;
import magicbees.main.utils.compat.ExtraBeesHelper;
import magicbees.main.utils.compat.ForestryHelper;
import magicbees.main.utils.compat.ThaumcraftHelper;
import magicbees.world.WorldGeneratorHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
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
		version=VersionInfo.Version,
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

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		this.configsPath = event.getModConfigurationDirectory().getAbsolutePath();
		this.modConfig = new Config(event.getSuggestedConfigurationFile());
		
		// Compatibility Helpers setup time.
		ForestryHelper.preInit();
		ExtraBeesHelper.preInit();
		ThaumcraftHelper.preInit();
		EquivalentExchangeHelper.preInit();
		ArsMagicaHelper.preInit();
		
		this.modConfig.setupBlocks();
		this.modConfig.setupItems();
		
		LocalizationManager.setupLocalizationInfo();
		
		new TransmutationEffectController();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		ForestryHelper.init();
		ExtraBeesHelper.init();
		ThaumcraftHelper.init();
		EquivalentExchangeHelper.init();
		ArsMagicaHelper.init();
		
		GameRegistry.registerWorldGenerator(worldHandler = new WorldGeneratorHandler());		
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		ForestryHelper.postInit();
		ExtraBeesHelper.postInit();
		ThaumcraftHelper.postInit();
		EquivalentExchangeHelper.postInit();
		ArsMagicaHelper.postInit();
		
		this.guiHandler = new GUIHandler();
		NetworkRegistry.instance().registerGuiHandler(this, this.guiHandler);
		
		proxy.registerRenderers();

		BeeManager.ititializeBees();
		
		this.modConfig.saveConfigs();
		
		CraftingManager.setupCrafting();
		CraftingManager.registerLiquidContainers();
		
		VersionInfo.doVersionCheck();
	}
	
	@Mod.EventHandler
	public void handleIMCMessage(IMCEvent event)
	{
		IMCManager.handle(event);
	}
	
	public static Config getConfig()
	{
		return object.modConfig;
	}
}
