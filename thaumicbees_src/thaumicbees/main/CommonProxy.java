package thaumicbees.main;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class CommonProxy
{
	public static String TCBEES_GFX = "/gfx/thaumicbees/";
	public static String TCBEES_RESEARCH = "/research/thaumicbees/";
	public static String TCBEES_ITEMS = TCBEES_GFX + "items/";
	
	public static String FORESTRY_GFX_ITEMS;
	public static String FORESTRY_GFX_BEEEFFECTS;
	
	public static String TCBEES_ITEMSPNG = TCBEES_ITEMS + "items.png";

	public CommonProxy() { }
	
	public void preloadTextures()
	{
		// Does nothing here.
	}
	
	public void drawBeeEffects(World world, double xPos, double yPos, double zPos, int colour, int rangeX, int rangeY, int rangeZ)
	{
		// Does nothing in common.
	}
	
	public Minecraft getClientInstance()
	{
		return FMLClientHandler.instance().getClient();
	}

}
