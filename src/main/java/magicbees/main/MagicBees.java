package magicbees.main;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import magicbees.bees.BeeManager;
import magicbees.bees.TransmutationEffectController;
import magicbees.client.gui.GUIHandler;
import magicbees.main.utils.*;
import magicbees.main.utils.compat.*;
import magicbees.world.WorldGeneratorHandler;

@Mod(
		modid=VersionInfo.ModName,
		dependencies=VersionInfo.Depends,
        guiFactory=VersionInfo.GUI_FACTORY_CLASS
)
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
        FMLCommonHandler.instance().bus().register(modConfig);
		
		// Compatibility Helpers setup time.
		ModHelper.preInit();
			
		this.modConfig.setupBlocks();
		this.modConfig.setupItems();
		
		// LocalizationManager.setupLocalizationInfo();
		
		new TransmutationEffectController();
        LogHelper.info("Preinit completed");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
        ModHelper.init();

		worldHandler = new WorldGeneratorHandler();
		GameRegistry.registerWorldGenerator(worldHandler, 0);

        LogHelper.info("Init completed");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
        ModHelper.postInit();
		
		this.guiHandler = new GUIHandler();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, this.guiHandler);
		
		proxy.registerRenderers();

		BeeManager.ititializeBees();
		
		this.modConfig.saveConfigs();
		
		CraftingManager.setupCrafting();
		CraftingManager.registerLiquidContainers();
		
		VersionInfo.doVersionCheck();
        LogHelper.info("Postinit completed");
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
