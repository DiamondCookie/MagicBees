package magicbees.bees;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IEffectData;

public class AlleleEffectPotion extends AlleleEffect
{
	private int potionId;
	private int duration;
	
	public AlleleEffectPotion(String name, Potion potionApplied, int effectDuration, boolean isDominant)
	{
		super(name, isDominant, 200);
		this.potionId = potionApplied.id;
		this.duration = 20 * effectDuration;
	}

	@Override
	public IEffectData validateStorage(IEffectData storedData)
	{
		if (storedData == null)
		{
			storedData = new EffectData(1, 0, 0);
		}
		return storedData;
	}

	@Override
	public IEffectData doEffectThrottled(IBeeGenome genome, IEffectData storedData, IBeeHousing housing)
	{
		List<Entity> entityList = this.getEntitiesWithinRange(genome, housing);
		
		for (Entity e : entityList)
		{
			if (e instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)e;
				player.addPotionEffect(new PotionEffect(this.potionId, this.duration, 0));
			}
		}
		storedData.setInteger(0, 0);
		
		return storedData;
	}
}
