package magicbees.bees;

import java.util.List;


import magicbees.main.MagicBees;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IEffectData;

public class AlleleEffectCure extends AlleleEffect
{

	public AlleleEffectCure(String id, boolean isDominant)
	{
		super(id, isDominant);
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
	public IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing)
	{
		int count = storedData.getInteger(0);
		
		if (count == 200)
		{
			List<Entity> entityList = this.getEntitiesWithinRange(genome, housing);
			
			for (Entity e : entityList)
			{
				if (e instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer)e;
					player.curePotionEffects(new ItemStack(Item.bucketMilk));
				}
			}
			storedData.setInteger(0, 0);
		}
		else
		{
			storedData.setInteger(0, count + 1);
		}
		
		return storedData;
	}

}
