package magicbees.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerMB extends Container
{
    protected final int PLAYER_INVENTORY_ROWS = 3;
    protected final int PLAYER_INVENTORY_COLUMNS = 9;

    public void addPlayerInventory(InventoryPlayer inventoryPlayer, int xStart, int yStart)
    {
        for (int x = 0; x < PLAYER_INVENTORY_COLUMNS; x++){
            addSlotToContainer(new Slot(inventoryPlayer, x, xStart + 8 + 18 * x, yStart + 4 + PLAYER_INVENTORY_ROWS * 18));
        }

        for (int y = 0; y < PLAYER_INVENTORY_ROWS; y++){
            for (int x = 0; x < PLAYER_INVENTORY_COLUMNS; x++){
                addSlotToContainer(new Slot(inventoryPlayer, x + y * 9 + 9, xStart + 8 + 18 * x, yStart + y * 18));
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return true;
    }
}
