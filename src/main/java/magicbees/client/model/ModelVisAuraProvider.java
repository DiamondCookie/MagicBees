package magicbees.client.model;

import org.lwjgl.opengl.GL11;

import magicbees.main.CommonProxy;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelVisAuraProvider {
	public static final ResourceLocation objFile = new ResourceLocation(CommonProxy.DOMAIN, CommonProxy.MODEL + "visAuraConverter.obj");
	public static final ResourceLocation textureLocation = new ResourceLocation(CommonProxy.DOMAIN, CommonProxy.MODEL + "visAuraProvider.png");
	
	public static final ModelVisAuraProvider model = new ModelVisAuraProvider();
	
	private IModelCustom visAuraProviderObj;
	
	public ModelVisAuraProvider() {
		this.visAuraProviderObj = AdvancedModelLoader.loadModel(objFile);
	}

	public void render(long tick) {
		GL11.glPushMatrix();
		GL11.glRotated(Math.sin(tick * Math.PI * 2 / 80) * 0.7, 0, 1, 0);
		GL11.glRotated(Math.sin((tick + 25) * Math.PI * 2 / 150) * 0.75, 0.5, 0, 0.5);
		GL11.glTranslated(Math.sin(tick * Math.PI * 2 / 1000) * 0.025, 0, Math.sin((tick + 30) * Math.PI * 2 / 650) * 0.03);
		float red = (float)(Math.sin(tick * Math.PI * 2 / 130) * 0.2 + 0.8);
		float green = (float)(Math.cos((tick + 2) * Math.PI * 2 / 140) * 0.2 + 0.8);
		float blue = (float)(Math.sin((tick + 5) * Math.PI * 2 / 99) * 0.2 + 0.8);
		GL11.glColor3f(red, green, blue);
		this.visAuraProviderObj.renderPart("centre");
		GL11.glPopMatrix();
		
		GL11.glColor3f(1f, 1f, 1f);
		this.visAuraProviderObj.renderPart("frame");
	}
}
