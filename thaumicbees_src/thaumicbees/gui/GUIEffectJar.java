package thaumicbees.gui;

import org.lwjgl.opengl.GL11;

import thaumicbees.main.CommonProxy;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public class GUIEffectJar extends GuiContainer
{
	public static final String BACKGROUND_FILE = "jarScreen.png";
	
	private static final int WIDTH = 176;
	private static final int HEIGHT = 156;
	
	private static final int SLOT_JAR_X = 80;
	private static final int SLOT_JAR_Y = 22;
	private static final int SLOT_INVENTORY_X = 8;
	private static final int SLOT_INVENTORY_Y = 74;
	private static final int BAR_DEST_X = 117;
	private static final int BAR_DEST_Y = 10;
	
	private static final int BAR_SRC_X = 176;
	private static final int BAR_SRC_Y = 0;
	
	private static final int BAR_WIDTH = 10;
	private static final int BAR_HEIGHT = 40;
	
	public GUIEffectJar(ContainerEffectJar container)
	{
		super(container);
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		fontRenderer.drawString("Inve", 9, 63, 0);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		int texture = this.mc.renderEngine.getTexture(CommonProxy.TCBEES_GUI_PATH + BACKGROUND_FILE);
		
		GL11.glColor4f(1f, 1f, 1f, 0.2f);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_SRC_ALPHA);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
		
		GL11.glColor4f(1f, 1f, 1f, 1f);
		
		float percent = 0.0f;
		int value = BAR_HEIGHT - (int)(percent * BAR_HEIGHT);
		this.drawTexturedModalRect(this.guiLeft + BAR_DEST_X, this.guiTop + value + BAR_DEST_Y, BAR_SRC_X, BAR_SRC_Y, BAR_WIDTH, BAR_HEIGHT - value);
	}

}
