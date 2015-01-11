package magicbees.tileentity;

import com.mojang.authlib.GameProfile;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.ErrorStateRegistry;
import forestry.api.core.IErrorState;
import forestry.api.genetics.IIndividual;

/**
 * Singleton object to simulate enough of a bee house to fire the bee effects. =D
 *
 * @author MysteriousAges
 */
public class EffectJarHousing implements IBeeHousing
{
	private static EffectJarHousing instance = new EffectJarHousing();

	TileEntityEffectJar jarEntity;

	public static EffectJarHousing getFor(TileEntityEffectJar entity)
	{
		instance.jarEntity = entity;
		return instance;
	}

	@Override
	public float getTerritoryModifier(IBeeGenome genome, float currentModifier)
	{
		return 0.9f;
	}

	@Override
	public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier)
	{
		return 0f;
	}

	@Override
	public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier)
	{
		return 0f;
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
		return true;
	}

	@Override
	public boolean isSelfLighted()
	{
		return true;
	}

	@Override
	public boolean isSunlightSimulated()
	{
		return true;
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
	public void wearOutEquipment(int amount)
	{
	}

	@Override
	public void onQueenDeath(IBee queen)
	{
	}

	@Override
	public void onPostQueenDeath(IBee queen)
	{
	}

	@Override
	public ItemStack getQueen()
	{
		return this.jarEntity.getStackInSlot(TileEntityEffectJar.QUEEN_SLOT);
	}

	@Override
	public ItemStack getDrone()
	{
		return null;
	}

	@Override
	public void setQueen(ItemStack itemStack)
	{
		this.jarEntity.setInventorySlotContents(TileEntityEffectJar.QUEEN_SLOT, itemStack);
	}

	@Override
	public void setDrone(ItemStack itemstack)
	{
	}

	@Override
	public int getBiomeId()
	{
		return getBiome().biomeID;
	}

	@Override
	public BiomeGenBase getBiome() {
		return this.jarEntity.getWorldObj().getBiomeGenForCoords(getXCoord(), getYCoord());
	}

	@Override
	public EnumTemperature getTemperature()
	{
		EnumTemperature temp = EnumTemperature.NORMAL;
		ItemStack stack = this.jarEntity.getStackInSlot(TileEntityEffectJar.QUEEN_SLOT);
		if (stack != null)
		{
			IBee queen = magicbees.bees.BeeManager.beeRoot.getMember(stack);
			temp = queen.getGenome().getPrimary().getTemperature();
		}
		return temp;
	}

	@Override
	public EnumHumidity getHumidity()
	{
		EnumHumidity humid = EnumHumidity.NORMAL;
		ItemStack stack = this.jarEntity.getStackInSlot(TileEntityEffectJar.QUEEN_SLOT);
		if (stack != null)
		{
			IBee queen = magicbees.bees.BeeManager.beeRoot.getMember(stack);
			humid = queen.getGenome().getPrimary().getHumidity();
		}
		return humid;
	}

	@Override
	public World getWorld()
	{
		return this.jarEntity.getWorldObj();
	}

	@Override
	public GameProfile getOwnerName()
	{
		return this.jarEntity.getOwner();
	}

	@Override
	public void setErrorState(int state)
	{
	}

	@Override
	public void setErrorState(IErrorState enumErrorCode)
	{
	}

	@Override
	public int getErrorOrdinal()
	{
		return getErrorState().getID();
	}

	@Override
	public IErrorState getErrorState()
	{
		return ErrorStateRegistry.getErrorState("ok");
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

	@Override
	public boolean onPollenRetrieved(IBee queen, IIndividual pollen, boolean isHandled)
	{
		return false;
	}

	@Override
	public boolean onEggLaid(IBee queen)
	{
		return false;
	}

	@Override
	public float getGeneticDecay(IBeeGenome genome, float currentModifier)
	{
		return 0f;
	}

}
