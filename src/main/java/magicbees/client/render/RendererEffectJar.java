package magicbees.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import magicbees.client.model.ModelEffectJar;
import magicbees.tileentity.TileEntityEffectJar;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RendererEffectJar extends TileEntitySpecialRenderer implements IItemRenderer
{
	public static RendererEffectJar instance = new RendererEffectJar();

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick)
	{
		if (tileEntity instanceof TileEntityEffectJar)
		{
			this.doRender(x, y, z, 1f);
		}
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		switch (type)
		{
			default:
			case ENTITY:
				doRender(0, 0, 0, 1.8f);
				break;
			
			case EQUIPPED:
				doRender(0, 0, 0, 1.5f);
				break;
			
			case EQUIPPED_FIRST_PERSON:
				doRender(-.5, .5, 0.25, 1f);
				break;
			
			case INVENTORY:
				doRender(0.3, 0.25, 0.3, 1.5f);
				break;
		}
	}
	
	private void doRender(double x, double y, double z, float scale)
	{
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(x, y, z);
		
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(ModelEffectJar.textureLocation);
		
		ModelEffectJar.model.render();
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
