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

    public GuiThaumicApiary(InventoryPlayer inventoryPlayer, TileEntityThaumicApiary thaumicApiary) {
        super(new ContainerThaumicApiary(inventoryPlayer, thaumicApiary));

        this.xSize = WIDTH;
        this.ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y) {
        GL11.glColor4f(1, 1, 1, 1);
        this.mc.getTextureManager().bindTexture(BACKGROUND_LOCATION);

        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

    }
}
