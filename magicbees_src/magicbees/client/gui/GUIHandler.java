package magicbees.client.gui;

import magicbees.tileentity.TileEntityEffectJar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		Object value = null;
		
		if (ID == UIScreens.EFFECT_JAR.ordinal())
		{
			value = new ContainerEffectJar((TileEntityEffectJar)world.getBlockTileEntity(x, y, z), player);
		}
		
		return value;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		Object value = null;
		
		if (ID == UIScreens.EFFECT_JAR.ordinal())
		{
			value = new GUIEffectJar((TileEntityEffectJar)world.getBlockTileEntity(x, y, z), player);
		}
		
		return value;
	}

}
