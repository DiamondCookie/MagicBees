package thaumicbees.main;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class CommonProxy
{
	public static final String TCBEES_GFX = "/gfx/thaumicbees/";
	public static final String TCBEES_RESEARCH = "/research/thaumicbees/";
	public static final String TCBEES_LOCDIR = "/lang/thaumicbees/";
	public static final String TCBEES_ITEM_GFX = TCBEES_GFX + "items/";
	
	public static String FORESTRY_GFX_ITEMS;
	public static String FORESTRY_GFX_BEEEFFECTS;
	
	public static final String TCBEES_ITEMS_IMAGE = TCBEES_ITEM_GFX + "items.png";
	public static final String TCBEES_LIQUIDS_IMAGE = TCBEES_ITEM_GFX + "liquids.png";

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
