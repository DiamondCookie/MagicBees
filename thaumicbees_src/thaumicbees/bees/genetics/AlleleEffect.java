package thaumicbees.bees.genetics;

import thaumicbees.main.ThaumicBees;
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
	public abstract String getIdentifier();

	@Override
	public abstract IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing);

	@Override
	public IEffectData doFX(IBeeGenome genome, IEffectData storedData, IBeeHousing housing)
	{
		int[] area = genome.getTerritory();
		float mod = housing.getTerritoryModifier(genome);
		// Check to make sure territory is at least 1 with housing modifications
		area[0] = Math.min((int)(area[0] * mod), 1);
		area[1] = Math.min((int)(area[1] * mod), 1);
		area[2] = Math.min((int)(area[2] * mod), 1);
		
		ThaumicBees.proxy.drawBeeEffects(housing.getWorld(), housing.getXCoord(), housing.getYCoord(), housing.getZCoord(),
				genome.getPrimaryAsBee().getPrimaryColor(), area[0], area[1], area[2]);
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

}
