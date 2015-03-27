package magicbees.client.render;

import magicbees.client.model.ModelVisAuraProvider;
import magicbees.tileentity.TileEntityVisAuraProvider;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class RendererVisAuraProvider extends TileEntitySpecialRenderer implements IItemRenderer {
	public static RendererVisAuraProvider instance = new RendererVisAuraProvider();

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float tick) {
		if (entity instanceof TileEntityVisAuraProvider) {
			this.doRender(x, y, z, 1f, entity.getWorldObj().getTotalWorldTime());
		}
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		doRender(0, 0, 0, 1f, 0);
	}
	
	private void doRender(double x, double y, double z, float scale, long tick) {
		GL11.glPushMatrix();
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(x, y, z);
		
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(ModelVisAuraProvider.textureLocation);
		ModelVisAuraProvider.model.render(tick);
		
		GL11.glPopMatrix();
	}

}
