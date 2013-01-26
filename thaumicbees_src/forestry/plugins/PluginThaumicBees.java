package forestry.plugins;

import java.util.Random;

import net.minecraft.command.ICommand;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import forestry.api.core.IOreDictionaryHandler;
import forestry.api.core.IPacketHandler;
import forestry.api.core.IPickupHandler;
import forestry.api.core.IPlugin;
import forestry.api.core.IResupplyHandler;
import forestry.api.core.ISaveEventHandler;
import thaumicbees.bees.ThaumicBeesPlugin;

public class PluginThaumicBees implements IPlugin
{

	@Override
	public boolean isAvailable()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void preInit()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doInit()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postInit()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDescription()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generateSurface(World world, Random rand, int chunkX, int chunkZ)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public IGuiHandler getGuiHandler()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPacketHandler getPacketHandler()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPickupHandler getPickupHandler()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResupplyHandler getResupplyHandler()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISaveEventHandler getSaveEventHandler()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOreDictionaryHandler getDictionaryHandler()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICommand[] getConsoleCommands()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
