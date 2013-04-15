package thaumicbees.bees;

import java.util.List;

import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import thaumicbees.main.ThaumicBees;
import thaumicbees.main.utils.LocalizationManager;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IEffectData;

public abstract class AlleleEffect extends Allele implements IAlleleBeeEffect
{

	public AlleleEffect(String id, boolean isDominant)
	{
		super(id, isDominant);
	}

	@Override
	public boolean isCombinable()
	{
		return false;
	}

	@Override
	public abstract IEffectData validateStorage(IEffectData storedData);

	@Override
	public String getIdentifier()
	{
		return LocalizationManager.getLocalizedString(getUID());
	}

	@Override
	public abstract IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing);

	@Override
	public IEffectData doFX(IBeeGenome genome, IEffectData storedData, IBeeHousing housing)
	{
		int[] area = genome.getTerritory();
		float mod = housing.getTerritoryModifier(genome, 1f);
		// Check to make sure territory is at least 1 with housing modifications
		area[0] = Math.max((int)(area[0] * mod), 1);
		area[1] = Math.max((int)(area[1] * mod), 1);
		area[2] = Math.max((int)(area[2] * mod), 1);
		
		ThaumicBees.proxy.drawBeeEffects(housing.getWorld(), housing.getXCoord(), housing.getYCoord(), housing.getZCoord(),
				genome.getPrimaryAsBee().getIconColour(0), area[0], area[1], area[2]);
		return storedData;
	}

	@Override
	public String getIconTextureFile()
	{
		return null;
	}

	@Override
	public int getIconIndex()
	{
		return -1;
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
