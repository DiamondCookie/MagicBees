package thaumicbees.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.mysteriousages.utils.gui.SlotCustomItems;
import thaumicbees.tileentity.TileEntityEffectJar;
import forestry.api.core.ItemInterface;

public class ContainerEffectJar extends Container
{
	public TileEntityEffectJar jar;
	
	public int lastBeeHealth = 0;
	public int speciesColour = 0xffffff;
	
	public ContainerEffectJar(TileEntityEffectJar tileEntityEffectJar, EntityPlayer player)
	{
		this.jar = tileEntityEffectJar;
		this.addSlotToContainer(new SlotCustomItems(this.jar, 0, 80, 22, new ItemStack[] { ItemInterface.getItem("beeDroneGE") }));

		for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 74 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 132));
        }
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting crafting)
	{
		super.addCraftingToCrafters(crafting);
		
		crafting.sendProgressBarUpdate(this, 0, this.jar.currentBeeHealth);
		crafting.sendProgressBarUpdate(this, 1, this.jar.currentBeeColour);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		for (int i = 0; i < this.crafters.size(); ++i)
		{
			ICrafting crafting = (ICrafting)this.crafters.get(i);
			
			if (this.lastBeeHealth != this.jar.currentBeeHealth)
			{
				crafting.sendProgressBarUpdate(this, 0, this.jar.currentBeeHealth);
				this.lastBeeHealth = this.jar.currentBeeHealth;
			}
			if (this.speciesColour != this.jar.currentBeeColour)
			{
				crafting.sendProgressBarUpdate(this, 1, this.jar.currentBeeColour);
				this.speciesColour = this.jar.currentBeeColour;
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int craftingId, int value)
	{
		if (craftingId == 0)
		{
			this.jar.currentBeeHealth = value;
		}
		else if (craftingId == 1)
		{
			this.jar.currentBeeColour = value;
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
		Slot itemSlot = this.getSlot(slot);
		boolean clearSlot = false;
		
		if (itemSlot != null && itemSlot.getHasStack())
		{
			ItemStack srcStack = itemSlot.getStack();
			if (slot == 0 && srcStack != null)
			{
				clearSlot = this.mergeItemStack(srcStack, 1, 36 + 1, false);
			}
			else
			{
				if (this.getSlot(0).isItemValid(srcStack))
				{
					clearSlot = this.mergeItemStack(srcStack, 0, 1, false);
				}
			}
		}
		
		if (clearSlot)
		{
			itemSlot.putStack(null);
		}
		itemSlot.onSlotChanged();
		player.inventory.onInventoryChanged();
		
		return null;
	}

}
