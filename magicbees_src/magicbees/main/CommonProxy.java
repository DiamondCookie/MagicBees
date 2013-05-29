package magicbees.main;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import magicbees.tileentity.TileEntityEffectJar;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class CommonProxy
{
	public static final String TEXTURE_PATH = "/mods/magicbees/textures/";
	public static final String MODEL_PATH = "/mods/magicbees/model/";
	public static final String TCBEES_RESEARCH = "/research/magicbees/";
	public static final String TCBEES_LOCDIR = "/lang/magicbees/";
	
	public static String FORESTRY_GFX_ITEMS;
	public static String FORESTRY_GFX_BEEEFFECTS;
	
	public static final String GUI_PATH = TEXTURE_PATH + "gui/";
	public static final String MODEL_TEXTURE_PATH = TEXTURE_PATH + "model/";
	
	public static int RenderIdEffectJar;

	public CommonProxy() { }
	
	public void drawBeeEffects(World world, double xPos, double yPos, double zPos, int colour, int rangeX, int rangeY, int rangeZ)
	{
		// Does nothing in common.
	}
	
	public Minecraft getClientInstance()
	{
		return FMLClientHandler.instance().getClient();
	}
	
	public void registerRenderers() { }

}
