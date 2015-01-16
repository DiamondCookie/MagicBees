package magicbees.client.gui;

import forestry.api.apiculture.IHiveFrame;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotFrame extends SlotCustomItems {
    public SlotFrame(IInventory inventory, int slotIndex, int xPos, int yPos) {
        super(inventory, slotIndex, xPos, yPos);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack){
        if (itemStack == null){
            return false;
        }
        if (!inventory.isItemValidForSlot(getSlotIndex(), itemStack)){
            return false;
        }

        boolean flag = false;

        if(itemStack.getItem() instanceof IHiveFrame){
            flag = true;
        }

        return flag;
    }
}
