package magicbees.bees;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IEffectData;

public class AlleleEffectCrumbling extends AlleleEffect
{
	private static HashMap<ItemStack, ItemStack> crumbleMap = new HashMap<ItemStack, ItemStack>();

	public static void addPairToMap(ItemStack source, ItemStack target)
	{
		boolean add = true;
		for (ItemStack i : crumbleMap.keySet())
		{
			if (OreDictionary.itemMatches(i, source, false))
			{
				add = false;
				break;
			}
		}
		if (add)
		{
			crumbleMap.put(source, target);
		}
	}
	
	public AlleleEffectCrumbling(String id, boolean isDominant)
	{
		super(id, isDominant, 600);
		
		addPairToMap(new ItemStack(Blocks.stone), new ItemStack(Blocks.cobblestone));
		addPairToMap(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.mossy_cobblestone));
		addPairToMap(new ItemStack(Blocks.stonebrick, 1, 0), new ItemStack(Blocks.stonebrick, 1, 2));
		addPairToMap(new ItemStack(Blocks.stonebrick, 1, 2), new ItemStack(Blocks.stonebrick, 1, 1));
		addPairToMap(new ItemStack(Blocks.cobblestone_wall), new ItemStack(Blocks.cobblestone_wall, 1, 1));
		addPairToMap(new ItemStack(Blocks.gravel), new ItemStack(Blocks.sand));
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
		int xRange = (int)(housing.getTerritoryModifier(genome, 1f) * genome.getTerritory()[0]);
		int yRange = (int)(housing.getTerritoryModifier(genome, 1f) * genome.getTerritory()[1]);
		int zRange = (int)(housing.getTerritoryModifier(genome, 1f) * genome.getTerritory()[2]);

		int xCoord = housing.getXCoord() + world.rand.nextInt(xRange) - xRange / 2;
		int yCoord = housing.getYCoord() + world.rand.nextInt(yRange) - yRange / 2;
		int zCoord = housing.getZCoord() + world.rand.nextInt(zRange) - zRange / 2;
		
		Block block = world.getBlock(xCoord, yCoord, zCoord);
		if (block != null)
		{
			ItemStack source = new ItemStack(block, 1, world.getBlockMetadata(xCoord, yCoord, zCoord));
			for (ItemStack key : crumbleMap.keySet())
			{
				if (OreDictionary.itemMatches(source, key, false))
				{
					ItemStack target = crumbleMap.get(key);
					world.setBlock(xCoord, yCoord, zCoord, Block.getBlockFromItem(target.getItem()),
							target.getItemDamage(), 2);
					
					break;
				}
			}
		}
		
		storedData.setInteger(0, 0);
		
		return storedData;
	}

}
