package magicbees.bees;

import java.util.List;


import magicbees.main.MagicBees;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
		super(id, isDominant, 200);
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
				player.curePotionEffects(new ItemStack(Items.milk_bucket));
			}
		}
		storedData.setInteger(0, 0);
		
		return storedData;
	}

}
