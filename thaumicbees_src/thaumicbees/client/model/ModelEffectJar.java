package thaumicbees.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import thaumicbees.main.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelEffectJar
{
	public static final String objFile = CommonProxy.MODEL_PATH + "effectJar.obj";
	public static final String textureFile = CommonProxy.MODEL_TEXTURE_PATH + "jarTexture.png";
	
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
