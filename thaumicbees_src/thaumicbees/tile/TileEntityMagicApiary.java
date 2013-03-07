package thaumicbees.tile;

import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityMagicApiary extends TileEntity implements IInventory, IBeeHousing
{

	@Override
	public int getSizeInventory()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int var1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInvName()
	{
		return "thaumicbees.magicApiary";
	}

	@Override
	public int getInventoryStackLimit()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1)
	{
		return true;
	}

	@Override
	public void openChest() { }

	@Override
	public void closeChest() { }

	@Override
	public float getTerritoryModifier(IBeeGenome genome)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMutationModifier(IBeeGenome genome, IBeeGenome mate)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getProductionModifier(IBeeGenome genome)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFloweringModifier(IBeeGenome genome)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isSealed()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSelfLighted()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSunlightSimulated()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHellish()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onQueenChange(ItemStack queen)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void wearOutEquipment(int amount)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onQueenDeath(IBee queen)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostQueenDeath(IBee queen)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getXCoord()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getYCoord()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getZCoord()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getQueen()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getDrone()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setQueen(ItemStack itemstack)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDrone(ItemStack itemstack)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBiomeId()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EnumTemperature getTemperature()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnumHumidity getHumidity()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public World getWorld()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOwnerName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setErrorState(int state)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getErrorOrdinal()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canBreed()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addProduct(ItemStack product, boolean all)
	{
		// TODO Auto-generated method stub
		return false;
	}
}
