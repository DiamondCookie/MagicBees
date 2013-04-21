package thaumicbees.tileentity;

import forestry.api.apiculture.IBeeHousing;
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
	
	private ItemStack droneSlot;
	private ItemStack queenSlot;
	
	private int lifeTicksRemaining;
	private static final int lifeTicks = 135;
	
	public TileEntityEffectJar()
	{
		super();
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
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		return null;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		
	}

	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack)
	{
		return false;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() { }
}
