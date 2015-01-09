package magicbees.bees;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IEffectData;

public class AlleleEffectTransmuting extends AlleleEffect
{
	public AlleleEffectTransmuting(String id, boolean isDominant)
	{
		super(id, isDominant, 200);
	}

	@Override
	public IEffectData validateStorage(IEffectData storedData)
	{
		if (storedData == null || !(storedData instanceof magicbees.bees.EffectData))
		{
			storedData = new magicbees.bees.EffectData(1, 0, 0);
		}
		return storedData;
	}

	@Override
	protected IEffectData doEffectThrottled(IBeeGenome genome, IEffectData storedData, IBeeHousing housing)
	{
		World world = housing.getWorld();
		// Get random coords within territory
		int xCoord = (int)(housing.getTerritoryModifier(genome, 1f) * genome.getTerritory()[0]);
		xCoord = housing.getXCoord() + world.rand.nextInt(xCoord) - xCoord / 2;
		int yCoord = (int)(housing.getTerritoryModifier(genome, 1f) * genome.getTerritory()[1]);
		yCoord = housing.getYCoord() + world.rand.nextInt(yCoord) - yCoord / 2;
		int zCoord = (int)(housing.getTerritoryModifier(genome, 1f) * genome.getTerritory()[0]);
		zCoord = housing.getZCoord() + world.rand.nextInt(zCoord) - zCoord / 2;

		BiomeGenBase biome = world.getBiomeGenForCoords(xCoord, zCoord);
		TransmutationEffectController.instance.attemptTransmutations(world, biome,
				new ItemStack(world.getBlock(xCoord, yCoord, zCoord), 1, world.getBlockMetadata(xCoord, yCoord, zCoord)),
				xCoord, yCoord, zCoord);
		
		storedData.setInteger(0, 0);

		return storedData;
	}
}
