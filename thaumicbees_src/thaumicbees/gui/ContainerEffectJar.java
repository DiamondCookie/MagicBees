package thaumicbees.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import thaumicbees.tileentity.TileEntityEffectJar;
import forestry.api.core.ItemInterface;

public class ContainerEffectJar extends Container
{
	private TileEntityEffectJar jar;
	
	public ContainerEffectJar(TileEntityEffectJar tileEntityEffectJar, EntityPlayer player)
	{
		this.jar = tileEntityEffectJar;
		SlotCustomItems slot = new SlotCustomItems(this.jar, 0, 80, 22);
		slot.addValidItem(ItemInterface.getItem("beeDroneGE"));
		this.addSlotToContainer(slot);

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9+9, 8 + j * 18, 74 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 132));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return this.jar.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		ItemStack value = null;
		Slot s = (Slot)this.inventorySlots.get(slot);
		
		if (s != null && s.getHasStack())
		{
			ItemStack source = s.getStack();
			value = source.copy();
			
			if (slot == 0)
			{
				if (!this.mergeItemStack(source, 1, 36, true))
				{
					return null;
				}
				
				s.onSlotChange(source, value);
			}
			else
			{
				if (((SlotCustomItems)(this.inventorySlots.get(0))).isItemValid(source))
				{
					if (this.mergeItemStack(source, 0, 1, false))
					{
						return null;
					}
					
					s.onSlotChange(source, value);
				}
			}
			
			if (source.stackSize == 0)
			{
				s.putStack(null);
			}
			else
			{
				s.onSlotChanged();
			}
			
			if (source.stackSize == value.stackSize)
			{
				value = null;
			}
			
			s.onPickupFromSlot(player, source);
		}
		
		return value;
	}

}
