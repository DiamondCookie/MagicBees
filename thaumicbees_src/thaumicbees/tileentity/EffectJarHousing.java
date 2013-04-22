package thaumicbees.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import forestry.api.apiculture.BeeManager;
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
	public float getTerritoryModifier(IBeeGenome genome, float currentModifier)
	{
		return 0f;
	}

	@Override
	public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier)
	{
		return 0f;
	}

	@Override
	public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier)
	{
		return 0.5f;
	}

	@Override
	public float getProductionModifier(IBeeGenome genome, float currentModifier)
	{
		return 0f;
	}

	@Override
	public float getFloweringModifier(IBeeGenome genome, float currentModifier)
	{
		return 0f;
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
	public void onQueenChange(ItemStack queen)
	{
		
	}

	@Override
	public void wearOutEquipment(int amount) { }

	@Override
	public void onQueenDeath(IBee queen)
	{
		
	}

	@Override
	public void onPostQueenDeath(IBee queen) { }

	@Override
	public ItemStack getQueen()
	{
		return this.jarEntity.queenSlot;
	}

	@Override
	public ItemStack getDrone()
	{
		return null;
	}

	@Override
	public void setQueen(ItemStack itemStack)
	{
		this.jarEntity.queenSlot = itemStack;
	}

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
		EnumTemperature temp = EnumTemperature.NORMAL;
		ItemStack stack = this.jarEntity.queenSlot;
		if (stack != null)
		{
			IBee queen = BeeManager.beeInterface.getBee(stack);
			temp = queen.getGenome().getPrimary().getTemperature();
		}
		return temp;
	}

	@Override
	public EnumHumidity getHumidity()
	{
		EnumHumidity humid = EnumHumidity.NORMAL;
		ItemStack stack = this.jarEntity.queenSlot;
		if (stack != null)
		{
			IBee queen = BeeManager.beeInterface.getBee(stack);
			humid = queen.getGenome().getPrimary().getHumidity();
		}
		return humid;
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
