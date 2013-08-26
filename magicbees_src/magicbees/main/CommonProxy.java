package magicbees.main;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;

public class CommonProxy
{
	public static final String DOMAIN = "magicbees";
	public static final String TEXTURE = "textures/";
	public static final String GUI_TEXTURE = TEXTURE + "gui/";
	public static final String MODEL = "model/";
	public static final String LANGS = "lang/";
	
	@Deprecated
	public static final String RESOURCE_PATH = "/assets/magicbees/";
	@Deprecated
	public static final String TEXTURE_PATH = RESOURCE_PATH + "textures/";
	@Deprecated
	public static final String MODEL_PATH = RESOURCE_PATH + "model/";
	@Deprecated
	public static final String TCBEES_RESEARCH = RESOURCE_PATH + "research/";
	@Deprecated
	public static final String TCBEES_LOCDIR = RESOURCE_PATH + "lang/";
	
	public static String FORESTRY_GFX_ITEMS;
	public static String FORESTRY_GFX_BEEEFFECTS;
	
	@Deprecated
	public static final String GUI_PATH = TEXTURE_PATH + "gui/";
	
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
