package magicbees.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import magicbees.main.CommonProxy;
import magicbees.tileentity.TileEntityThaumicApiary;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiThaumicApiary extends GuiContainer
{
    public static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(CommonProxy.DOMAIN, CommonProxy.GUI_TEXTURE + "apiary.png");

    private static final int WIDTH = 176;
    private static final int HEIGHT = 190;
    
    private static final int LIFEBAR_WIDTH = 2;
    private static final int LIFEBAR_HEIGHT = 46;
    
    private static final int LIFEBAR_DEST_X = 21;
    private static final int LIFEBAR_DEST_Y = 37;
    
    private static final int LIFEBAR_SRC_Y = 0;
    private static final int LIFEBAR_SRC_STAGE1_X = 193;
    private static final int LIFEBAR_SRC_STAGE2_X = 189;
    private static final int LIFEBAR_SRC_STAGE3_X = 185;
    private static final int LIFEBAR_SRC_STAGE4_X = 181;
    private static final int LIFEBAR_SRC_STAGE5_X = 177;
    
    private static final int WORKBOOST_WIDTH = 16;
    private static final int WORKBOOST_HEIGHT = 12;
    private static final int WORKBOOST_DEST_X = 37;
    private static final int WORKBOOST_DEST_Y = 18;
    private static final int WORKBOOST_SRC_X = 176;
    private static final int WORKBOOST_SRC_Y = 46;
    
    private static final int DEATHBOOST_WIDTH = 11;
    private static final int DEATHBOOST_HEIGHT = 13;
    private static final int DEATHBOOST_DEST_X = 25;
    private static final int DEATHBOOST_DEST_Y = 17;
    private static final int DEATHBOOST_SRC_X = 176;
    private static final int DEATHBOOST_SRC_Y = 58;
    
    private static final int MUTATIONBOOST_WIDTH = 9;
    private static final int MUTATIONBOOST_HEIGHT = 13;
    private static final int MUTATIONBOOST_DEST_X = 15;
    private static final int MUTATIONBOOST_DEST_Y = 17;
    private static final int MUTATIONBOOST_SRC_X = 176;
    private static final int MUTATIONBOOST_SRC_Y = 71;

    public GuiThaumicApiary(InventoryPlayer inventoryPlayer, TileEntityThaumicApiary thaumicApiary) {
        super(new ContainerThaumicApiary(inventoryPlayer, thaumicApiary));

        this.xSize = WIDTH;
        this.ySize = HEIGHT;
    }

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		fontRendererObj.drawString("Inventory", 9, 99, 0);
	}

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y) {
        GL11.glColor4f(1, 1, 1, 1);
        this.mc.getTextureManager().bindTexture(BACKGROUND_LOCATION);

        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
        TileEntityThaumicApiary apiary = ((ContainerThaumicApiary)this.inventorySlots).apiary;
        int value = LIFEBAR_HEIGHT - (apiary.getHealthScaled(100) * LIFEBAR_HEIGHT) / 100;
        this.drawTexturedModalRect(this.guiLeft + LIFEBAR_DEST_X, this.guiTop + LIFEBAR_DEST_Y,
        		LIFEBAR_SRC_STAGE1_X, LIFEBAR_SRC_Y, LIFEBAR_WIDTH, value);

        if (apiary.isWorkBoosted()) {
        	this.drawTexturedModalRect(this.guiLeft + WORKBOOST_DEST_X, this.guiTop + WORKBOOST_DEST_Y,
        			WORKBOOST_SRC_X, WORKBOOST_SRC_Y, WORKBOOST_WIDTH, WORKBOOST_HEIGHT);
        }
        if (apiary.isDeathRateBoosted()) {
        	this.drawTexturedModalRect(this.guiLeft + DEATHBOOST_DEST_X, this.guiTop + DEATHBOOST_DEST_Y,
        			DEATHBOOST_SRC_X, DEATHBOOST_SRC_Y, DEATHBOOST_WIDTH, DEATHBOOST_HEIGHT);
        }
        if (apiary.isMutationBoosted()) {
        	this.drawTexturedModalRect(this.guiLeft + MUTATIONBOOST_DEST_X, this.guiTop + MUTATIONBOOST_DEST_Y,
        			MUTATIONBOOST_SRC_X, MUTATIONBOOST_SRC_Y, MUTATIONBOOST_WIDTH, MUTATIONBOOST_HEIGHT);
        }
    }
}
