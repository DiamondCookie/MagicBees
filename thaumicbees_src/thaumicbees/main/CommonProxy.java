package thaumicbees.main;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class CommonProxy
{
	public static final String TCBEES_GFX = "/mods/thaumicbees/textures/";
	public static final String TCBEES_RESEARCH = "/research/thaumicbees/";
	public static final String TCBEES_LOCDIR = "/lang/thaumicbees/";
	
	public static String FORESTRY_GFX_ITEMS;
	public static String FORESTRY_GFX_BEEEFFECTS;
	
	public static final String TCBEES_GUI_PATH = TCBEES_GFX + "gui/";

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
