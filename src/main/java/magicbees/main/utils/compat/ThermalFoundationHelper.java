package magicbees.main.utils.compat;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import magicbees.main.Config;

public class ThermalFoundationHelper
{
	public static final String Name = "ThermalFoundation";
	private static boolean isThermalFoundationPresent = false;

	public static boolean isActive()
	{
		return isThermalFoundationPresent;
	}

	public static void preInit()
	{
		if (Loader.isModLoaded(Name) && Config.ThermalExpansionActive)
		{
			isThermalFoundationPresent = true;
		}
	}

	public static void init()
	{
		if (isActive())
		{

		}
	}

	public static void postInit()
	{
		if (isActive())
		{
			getBlocks();
			getItems();
		}
	}


	public static void getBlocks()
	{
		Config.teEnderiumBlock = GameRegistry.findItemStack(Name, "blockEnderium", 1);
		Config.teElectrumBlock = GameRegistry.findItemStack(Name, "blockElectrum", 1);
		Config.teInvarBlock = GameRegistry.findItemStack(Name, "blockInvar", 1);
		Config.teNickelBlock = GameRegistry.findItemStack(Name, "blockNickel", 1);
		Config.tePlatinumBlock = GameRegistry.findItemStack(Name, "blockPlatinum", 1);
		Config.teBronzeBlock = GameRegistry.findItemStack(Name, "blockBronze", 1);
	}

	public static void getItems()
	{
		Config.teEnderiumNugget = GameRegistry.findItemStack(Name, "nuggetEnderium", 1);
		Config.teInvarNugget = GameRegistry.findItemStack(Name, "nuggetInvar", 1);
		Config.teElectrumNugget = GameRegistry.findItemStack(Name, "nuggetElectrum", 1);
		Config.teNickelNugget = GameRegistry.findItemStack(Name, "nuggetNickel", 1);
		Config.tePlatinumNugget = GameRegistry.findItemStack(Name, "nuggetPlatinum", 1);
		Config.teDustCryotheum = GameRegistry.findItemStack(Name, "dustCryotheum", 1);
		Config.teDustBlizz = GameRegistry.findItemStack(Name, "dustBlizz", 1);
		Config.teDustPyrotheum = GameRegistry.findItemStack(Name, "dustPyrotheum", 1);
		Config.teDustSulfur = GameRegistry.findItemStack(Name, "dustSulfur", 1);
		Config.teDustPlatinum = GameRegistry.findItemStack(Name, "dustPlatinum", 1);
	}
}
