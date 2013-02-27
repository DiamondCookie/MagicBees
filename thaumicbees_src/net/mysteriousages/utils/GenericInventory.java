package net.mysteriousages.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * Provides some common functionality for Inventory clusters. Performs bounds-checking on slot args.
 * 
 * @author MysteriousAges
 *
 */
public class GenericInventory implements IInventory
{
	private ItemStack[] inventory;
	private String name;
	private int stackLimit;
	
	public GenericInventory(String inventoryName, int slotCount)
	{
		this(inventoryName, slotCount, 64);
	}
	
	public GenericInventory(String inventoryName, int slotCount, int maxStackSize)
	{
		this.inventory = new ItemStack[slotCount];
		this.name = inventoryName;
		this.stackLimit = maxStackSize;
	}
	
	@Override
	public int getSizeInventory()
	{
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		ItemStack result = null;
		
		if (this.slotNumberValid(slot))
		{
			result = this.inventory[slot];
		}
		
		return result;
	}

	@Override
	public ItemStack decrStackSize(int slot, int count)
	{
		ItemStack result = null;
		
		if (this.slotNumberValid(slot))
		{
			if (this.inventory[slot] != null)
			{
				result = this.inventory[slot].splitStack(Math.min(count, this.inventory[slot].stackSize));
				if (this.inventory[slot].stackSize <= 0)
				{
					this.inventory[slot] = null;
				}
			}
		}
		
		return result;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return this.getStackInSlot(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack)
	{
		if (this.slotNumberValid(slot))
		{
			this.inventory[slot] = itemStack;
		}
	}

	@Override
	public String getInvName()
	{
		return this.name;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return this.stackLimit;
	}
	
	public ItemStack[] getInventory()
	{
		return this.inventory;
	}

	@Override
	public void onInventoryChanged() { }

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return true;
	}
	
	/**
	 * Accepts an ItemStack and attempts to merge it with a similar item stack or an empty slot.
	 * @param stack
	 * @return Any leftover items, or null if the merge completed successfully.
	 */
	public ItemStack attemptStackMerge(ItemStack stack)
	{
		for (int i = 0; i < this.inventory.length; ++i)
		{
			if (this.inventory[i] == null)
			{
				this.inventory[i] = stack;
				stack = null;
				break;
			}
			else if (this.inventory[i].isItemEqual(stack))
			{
				int freeSlots = stack.getItem().getItemStackLimit() - this.inventory[i].stackSize;
				
				this.inventory[i].stackSize += freeSlots;
				stack.stackSize -= freeSlots;
			}
			
			if (stack.stackSize <= 0)
			{
				stack = null;
				break;
			}
		}
		
		return stack;
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setString("Name", this.name);
		tag.setInteger("Size", this.inventory.length);
		NBTTagList itemList = new NBTTagList();
		
		for (int i = 0; i < this.inventory.length; ++i)
		{
			NBTTagCompound itemTag = new NBTTagCompound();
			itemTag.setByte("Slot", (byte)i);
			this.inventory[i].writeToNBT(itemTag);
			itemList.appendTag(itemTag);
		}
		
		tag.setTag("Items", itemList);
	}
	
	public void readFromNBT(NBTTagCompound tag)
	{
		this.name = tag.getString("Name");
		NBTTagList itemList = tag.getTagList("Items");
		int length = tag.getInteger("Size");
		this.inventory = new ItemStack[length];
		
		for (int i = 0; i < itemList.tagCount(); ++i)
		{
			NBTTagCompound item = (NBTTagCompound)itemList.tagAt(i);
			int slotId = item.getByte("Slot");
			if (slotId >= 0 && slotId < this.inventory.length)
			{
				this.inventory[slotId] = ItemStack.loadItemStackFromNBT(item);
			}
		}
	}

	/**
	 * Checks to see if the given slot number is valid with the inventory size
	 * @param slot
	 * @return True, if slot in [0, inventory size)
	 */
	protected boolean slotNumberValid(int slot)
	{
		return slot >= 0 && slot < this.inventory.length;
	}

	@Override
	public void openChest() { }

	@Override
	public void closeChest() { }
	
	
}
