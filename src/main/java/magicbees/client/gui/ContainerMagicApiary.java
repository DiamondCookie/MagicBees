package magicbees.client.gui;

import forestry.api.apiculture.IHiveFrame;
import forestry.plugins.PluginApiculture;
import magicbees.main.utils.ItemInterface;
import magicbees.tileentity.TileEntityMagicApiary;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMagicApiary extends ContainerMB {

    public TileEntityMagicApiary apiary;
    public int maxSlot = 0;

    // Constants
    private static final int SLOT_QUEEN = 0;
    private static final int SLOT_DRONE = 1;
    private static final int SLOT_FRAME_START = 2;
    private static final int SLOT_INVENTORY_START = 5;
    private static final int SLOT_FRAME_COUNT = 3;
    private static final int SLOT_INVENTORY_COUNT = 7;

    public ContainerMagicApiary(InventoryPlayer inventoryPlayer, TileEntityMagicApiary thaumicApiary){
        this.apiary = thaumicApiary;

        // Queen/Princess slot
        addSlotToContainer(new SlotCustomItems(this.apiary, 0, 29, 39, ItemInterface.getItemStack("Forestry", "beeQueenGE", 1), ItemInterface.getItemStack("Forestry", "beePrincessGE", 1)));
        // Drone slot
        addSlotToContainer(new SlotCustomItems(this.apiary, 1, 29, 65, ItemInterface.getItemStack("Forestry", "beeDroneGE", 64)));

        int currentSlot = 1;

        // Frame slots
        for (int x = 0; x < 3; x++){
            currentSlot++;
            addSlotToContainer(new SlotFrame(thaumicApiary, currentSlot, 66, 23 + x * 29));
            //LogHelper.info("[1] CURRENTSLOT: " + currentSlot);
        }

        for (int x = 0; x < 3; x++){
            currentSlot++;
            addSlotToContainer(new Slot(thaumicApiary, currentSlot, 116, 26 + x * 26));
            //LogHelper.info("[2] CURRENTSLOT: " + currentSlot);
        }

        int j = 0;
        for (int y = 0; y < 2; y++){
            for (int x = 0; x < 2; x++){
                currentSlot++;
                addSlotToContainer(new Slot(thaumicApiary, currentSlot, 95 + x * 42, 39 + j * 26));
                //LogHelper.info("[3] CURRENTSLOT: " + currentSlot);
            }
            j++;
        }

        addPlayerInventory(inventoryPlayer, 0, 109);

        maxSlot = currentSlot;

    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex){
        Slot itemSlot = this.getSlot(slotIndex);
        boolean clearSlot = false;

        if (itemSlot != null && itemSlot.getHasStack()) {
            ItemStack srcStack = itemSlot.getStack();

            if (slotIndex <= maxSlot && srcStack != null){
                clearSlot = this.mergeItemStack(srcStack, maxSlot + 1, maxSlot + 36, false);
            }else{
                if (slotIndex > maxSlot && srcStack != null){
                    if (PluginApiculture.beeInterface.isMember(srcStack)){
                            if (!PluginApiculture.beeInterface.isDrone(srcStack)){
                                if (this.getSlot(SLOT_QUEEN).getHasStack() == false) {
                                    clearSlot = this.mergeItemStack(srcStack, SLOT_QUEEN, SLOT_QUEEN, false);
                                }
                            }else{
                                if (this.getSlot(SLOT_DRONE).isItemValid(srcStack)){
                                    clearSlot = this.mergeItemStack(srcStack, SLOT_DRONE, SLOT_DRONE, false);
                                }
                            }
                    }else if(srcStack.getItem() instanceof IHiveFrame){
                        clearSlot = this.mergeItemStack(srcStack, SLOT_FRAME_START, SLOT_FRAME_START + SLOT_FRAME_COUNT, false);
                    }
                }
            }


        }

        if (clearSlot){
            itemSlot.putStack(null);
        }

        itemSlot.onSlotChanged();
        player.inventory.markDirty();

        return null;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return apiary.isUseableByPlayer(entityPlayer);
    }


    @Override
    public void updateProgressBar(int i, int j) {
        apiary.getGUINetworkData(i, j);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (Object crafter : crafters) {
            apiary.sendGUINetworkData(this, (ICrafting) crafter);
        }
    }
}
