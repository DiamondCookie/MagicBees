package thaumicbees.tileentity;

import forestry.api.apiculture.IBeeHousing;
import forestry.api.core.ItemInterface;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityEffectJar extends TileEntity implements IInventory
{
	private String ownerName;
	private EffectJarHousing housingLogic;
	
	private ItemStack[] droneSlots;
	protected ItemStack queenSlot;
	
	private int lifeTicksRemaining;
	private static final int lifeTicks = 135;
	
	public TileEntityEffectJar()
	{
		super();
		this.droneSlots = new ItemStack[1];
	}
	
	public void setOwner(EntityPlayer player)
	{
		this.ownerName = player.username;
	}
	
	public String getOwner()
	{
		return this.ownerName;
	}

	@Override
	public int getSizeInventory()
	{
		return this.droneSlots.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return droneSlots[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int count)
	{
		ItemStack value = null;
		if (this.droneSlots[slot] != null)
		{
			value = this.droneSlots[slot].copy();
			value.stackSize = Math.min(count, this.droneSlots[slot].stackSize);
			this.droneSlots[slot].stackSize -= value.stackSize;
			if (this.droneSlots[slot].stackSize == 0)
			{
				this.droneSlots[slot] = null;
			}
		}
		onInventoryChanged();
		return value;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack value = null;
		if (this.droneSlots[slot] != null)
		{
			value = this.droneSlots[slot];
			this.droneSlots[slot] = null;
		}
		onInventoryChanged();
		return value;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagRoot)
	{
		super.readFromNBT(tagRoot);
		
		if (tagRoot.hasKey("drones"))
		{
			this.droneSlots[0] = ItemStack.loadItemStackFromNBT((NBTTagCompound)tagRoot.getTag("drones"));
		}
		if (tagRoot.hasKey("queen"))
		{
			this.queenSlot = ItemStack.loadItemStackFromNBT((NBTTagCompound)tagRoot.getTag("queen"));
		}
		this.lifeTicksRemaining = tagRoot.getInteger("lifeTicksRemaining");
		
	}

	@Override
	public void writeToNBT(NBTTagCompound tagRoot)
	{
		super.writeToNBT(tagRoot);
		
		if (this.droneSlots[0] != null)
		{
			tagRoot.setTag("drones", this.droneSlots[0].writeToNBT(new NBTTagCompound()));
		}
		if (this.queenSlot != null)
		{
			tagRoot.setTag("queen", this.queenSlot.writeToNBT(new NBTTagCompound()));
		}
		tagRoot.setInteger("lifeTicksRemaining", this.lifeTicksRemaining);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack)
	{
		this.droneSlots[slot] = itemStack;
		if (this.droneSlots[slot] != null)
		{
			this.droneSlots[slot].stackSize = Math.min(this.droneSlots[slot].stackSize, this.droneSlots[slot].getItem().getItemStackLimit());
		}
		onInventoryChanged();
	}

	@Override
	public String getInvName()
	{
		return "container.effectJar";
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemStack)
	{
		return true;
	}

	@Override
	public void openChest() { }

	@Override
	public void closeChest() { }
}
