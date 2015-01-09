package magicbees.bees;

import java.util.List;

import magicbees.item.ItemArmorApiarist;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IEffectData;

public class AlleleEffectPotion extends AlleleEffect
{
	private int potionId;
	private int duration;
	private boolean isMalicious;
	
	public AlleleEffectPotion(String name, Potion potionApplied, int effectDuration, boolean isDominant)
	{
		super(name, isDominant, 200);
		this.potionId = potionApplied.id;
		this.duration = 20 * effectDuration;
		this.isMalicious = false;
	}
	
	public AlleleEffectPotion setMalicious()
	{
		this.isMalicious = true;
		
		return this;
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
				if (this.isMalicious)
				{
					int armorPieces = ItemArmorApiarist.getNumberPiecesWorn(player);
					int finalDuration = this.duration / 4 * (4 - armorPieces);
					if (finalDuration > 0)
					{
						player.addPotionEffect(new PotionEffect(this.potionId, finalDuration, 0));
					}
				}
				else
				{
					player.addPotionEffect(new PotionEffect(this.potionId, this.duration, 0));
				}
			}
		}
		storedData.setInteger(0, 0);
		
		return storedData;
	}
}
