package thaumicbees.bees.genetics;

import java.util.List;

import thaumicbees.bees.EffectData;
import thaumicbees.main.ThaumicBees;

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
	public String getIdentifier()
	{
		return "Cleansing";
	}

	@Override
	public IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing)
	{
		int count = storedData.getInteger(0);
		
		if (count == 200)
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
			
			AxisAlignedBB bounds = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(min[0], min[1], min[2], max[0], max[1], max[2]);
			List<Entity> entityList = housing.getWorld().getEntitiesWithinAABB(EntityPlayer.class, bounds);
			
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
