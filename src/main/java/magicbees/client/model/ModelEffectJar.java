package magicbees.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import magicbees.main.CommonProxy;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ModelEffectJar
{
	public static final ResourceLocation objFile = new ResourceLocation(CommonProxy.DOMAIN, CommonProxy.MODEL + "effectJar.obj");
	public static final ResourceLocation textureLocation = new ResourceLocation(CommonProxy.DOMAIN, CommonProxy.MODEL + "jarTexture.png");
	
	public static final ModelEffectJar model = new ModelEffectJar();
	
	private IModelCustom effectJarObj;
	
	public ModelEffectJar()
	{
		this.effectJarObj = AdvancedModelLoader.loadModel(objFile);
	}
	
	public void render()
	{
		this.effectJarObj.renderPart("jarLid");
		
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		this.effectJarObj.renderPart("jarBase");
		GL11.glPopAttrib();
		
	}
}
