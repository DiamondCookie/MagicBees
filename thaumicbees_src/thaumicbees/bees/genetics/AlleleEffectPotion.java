package thaumicbees.bees.genetics;

import net.minecraft.potion.Potion;
import thaumicbees.bees.EffectData;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IEffectData;

public class AlleleEffectPotion extends Allele implements IAlleleBeeEffect
{
	private int potionId;
	private int duration;
	private String beealyzerId;
	
	public AlleleEffectPotion(String name, String readableName, Potion potionApplied, int effectDuration, boolean isDominant)
	{
		super("effect" + name, isDominant);
		this.beealyzerId = readableName;
		this.potionId = potionApplied.id;
		this.duration = effectDuration;
	}

	@Override
	public boolean isCombinable()
	{
		return false;
	}

	@Override
	public IEffectData validateStorage(IEffectData storedData)
	{
		if (!(storedData instanceof EffectData))
		{
			storedData = new EffectData(1, 0, 0);
		}
		return storedData;
	}

	@Override
	public String getIdentifier()
	{
		return this.beealyzerId;
	}

	@Override
	public IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IEffectData doFX(IBeeGenome genome, IEffectData storedData, IBeeHousing housing)
	{
		// TODO Auto-generated method stub
		return null;
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
