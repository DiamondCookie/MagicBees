package thaumicbees.main.utils;

import thaumicbees.block.TileEntityEffectJar;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;

public class EffectJarHousing implements IBeeHousing
{
	TileEntityEffectJar jarEntity;
	
	public EffectJarHousing(TileEntityEffectJar entity)
	{
		this.jarEntity = entity;
	}

	@Override
	public float getTerritoryModifier(IBeeGenome genome)
	{
		return 1;
	}

	@Override
	public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate)
	{
		return 0.5f;
	}

	@Override
	public int getXCoord()
	{
		return this.jarEntity.xCoord;
	}

	@Override
	public int getYCoord()
	{
		return this.jarEntity.yCoord;
	}

	@Override
	public int getZCoord()
	{
		return this.jarEntity.zCoord;
	}

	@Override
	public float getMutationModifier(IBeeGenome genome, IBeeGenome mate)
	{
		return 0;
	}

	@Override
	public float getProductionModifier(IBeeGenome genome)
	{
		return 0;
	}

	@Override
	public float getFloweringModifier(IBeeGenome genome)
	{
		return 0;
	}

	@Override
	public boolean isSealed()
	{
		return false;
	}

	@Override
	public boolean isSelfLighted()
	{
		return false;
	}

	@Override
	public boolean isSunlightSimulated()
	{
		return false;
	}

	@Override
	public boolean isHellish()
	{
		return false;
	}

	@Override
	public void onQueenChange(ItemStack queen) { }

	@Override
	public void wearOutEquipment(int amount) { }

	@Override
	public void onQueenDeath(IBee queen) { }

	@Override
	public void onPostQueenDeath(IBee queen) { }

	@Override
	public ItemStack getQueen()
	{
		return null;
	}

	@Override
	public ItemStack getDrone()
	{
		return null;
	}

	@Override
	public void setQueen(ItemStack itemstack) { }

	@Override
	public void setDrone(ItemStack itemstack) { }

	@Override
	public int getBiomeId()
	{
		return this.jarEntity.worldObj.getBiomeGenForCoords(getXCoord(), getYCoord()).biomeID;
	}

	@Override
	public EnumTemperature getTemperature()
	{
		return EnumTemperature.NORMAL;
	}

	@Override
	public EnumHumidity getHumidity()
	{
		return EnumHumidity.NORMAL;
	}

	@Override
	public World getWorld()
	{
		return this.jarEntity.worldObj;
	}

	@Override
	public String getOwnerName()
	{
		return this.jarEntity.getOwner();
	}

	@Override
	public void setErrorState(int state) { }

	@Override
	public int getErrorOrdinal()
	{
		return 0;
	}

	@Override
	public boolean canBreed()
	{
		return false;
	}

	@Override
	public boolean addProduct(ItemStack product, boolean all)
	{
		return true;
	}

}
