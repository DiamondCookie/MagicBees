package magicbees.bees;

import net.minecraft.world.World;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IEffectData;

public class AlleleEffectAuraNodeCharge extends AlleleEffect
{
	
	AlleleEffectAuraNodeCharge(String id, boolean dominant, int timeout)
	{
		super(id, dominant, timeout);
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
		World world = housing.getWorld();
		int x = housing.getXCoord();
		int y = housing.getYCoord();
		int z = housing.getZCoord();
		
		EnumTag tag;
		do
		{
			tag = EnumTag.values()[world.rand.nextInt(EnumTag.values().length)];
		}
		while (tag == EnumTag.UNKNOWN || tag == EnumTag.WEATHER);
		
		if (world.rand.nextBoolean())
		{
			ThaumcraftApi.increaseLowestAura(world, x, y, z, 1);
		}
		storedData.setInteger(0, 0);
			
		return storedData;
	}

}
