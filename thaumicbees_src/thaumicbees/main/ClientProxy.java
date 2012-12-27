package thaumicbees.main;

import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{

	public ClientProxy() { }
	
	@Override
	public void preloadTextures()
	{
		// Preload the TCBees items file
		MinecraftForgeClient.preloadTexture(TCBEES_ITEMS);
	}

}
