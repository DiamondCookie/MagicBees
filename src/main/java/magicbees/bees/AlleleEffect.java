package magicbees.bees;

import java.util.List;

import cpw.mods.fml.common.registry.LanguageRegistry;

import magicbees.main.MagicBees;
import magicbees.main.utils.LocalizationManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IEffectData;

public abstract class AlleleEffect extends Allele implements IAlleleBeeEffect
{
	protected int throttle;

	public AlleleEffect(String id, boolean isDominant, int timeout)
	{
		super("effect" + id, isDominant);
		this.throttle = timeout;
	}

	@Override
	public boolean isCombinable()
	{
		return false;
	}

	@Override
	public abstract IEffectData validateStorage(IEffectData storedData);

	@Override
	public final IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing)
	{
		int count = storedData.getInteger(0);
		if (count >= this.throttle)
		{
			storedData = this.doEffectThrottled(genome, storedData, housing);
		}
		else
		{
			storedData.setInteger(0, count + 1);
		}
		return storedData;
	}
	
	/**
	 * 
	 * @param genome
	 * @param storedData
	 * @param housing
	 * @return
	 */
	protected abstract IEffectData doEffectThrottled(IBeeGenome genome, IEffectData storedData, IBeeHousing housing);

	@Override
	public IEffectData doFX(IBeeGenome genome, IEffectData storedData, IBeeHousing housing)
	{
		return Allele.forestryBaseEffect.doFX(genome, storedData, housing);
	}
	
	protected List<Entity> getEntitiesWithinRange(IBeeGenome genome, IBeeHousing housing)
	{
		// Get the size of the affected area
		int[] area = genome.getTerritory();
		
		// Calculate offset
		int[] min = new int[3];
		int[] max = new int[3];
		min[0] = housing.getXCoord() - area[0] / 2;
		max[0] = housing.getXCoord() + area[0] / 2;
		
		min[1] = housing.getYCoord() - area[1] / 2;
		max[1] = housing.getYCoord() + area[1] / 2;
		
		min[2] = housing.getZCoord() - area[2] / 2;
		max[2] = housing.getZCoord() + area[2] / 2;
		
		AxisAlignedBB bounds = AxisAlignedBB.getAABBPool().getAABB(min[0], min[1], min[2], max[0], max[1], max[2]);
		return housing.getWorld().getEntitiesWithinAABB(EntityPlayer.class, bounds);
	}

}
