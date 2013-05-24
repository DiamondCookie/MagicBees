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

	private int effectTimeout;
	
	AlleleEffectAuraNodeCharge(String id, boolean dominant, int timeout)
	{
		super(id, dominant);
		this.effectTimeout = timeout;
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
		int timeout = storedData.getInteger(0);
		if (timeout >= this.effectTimeout)
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
			
			ThaumcraftApi.increaseLowestAura(world, x, y, z, 1);
		}
		else
		{
			storedData.setInteger(0, timeout + 1);
		}
		return storedData;
	}

}
