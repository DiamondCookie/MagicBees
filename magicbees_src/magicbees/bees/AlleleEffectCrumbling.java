package magicbees.bees;

import java.util.HashMap;

import net.minecraft.block.Block;
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
		
		addPairToMap(new ItemStack(Block.stone), new ItemStack(Block.cobblestone));
		addPairToMap(new ItemStack(Block.cobblestone), new ItemStack(Block.cobblestoneMossy));
		addPairToMap(new ItemStack(Block.stoneBrick, 1, 0), new ItemStack(Block.stoneBrick, 1, 2));
		addPairToMap(new ItemStack(Block.stoneBrick, 1, 2), new ItemStack(Block.stoneBrick, 1, 1));
		addPairToMap(new ItemStack(Block.cobblestoneWall), new ItemStack(Block.cobblestoneWall, 1, 1));
		addPairToMap(new ItemStack(Block.gravel), new ItemStack(Block.sand));
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
		
		boolean success = false;
		Block block = Block.blocksList[world.getBlockId(xCoord, yCoord, zCoord)];
		if (block != null)
		{
			ItemStack source = new ItemStack(block, 1, world.getBlockMetadata(xCoord, yCoord, zCoord));
			for (ItemStack key : crumbleMap.keySet())
			{
				if (OreDictionary.itemMatches(source, key, false))
				{
					ItemStack target = crumbleMap.get(key);
					world.setBlock(xCoord, yCoord, zCoord, target.itemID, target.getItemDamage(), 2); 
					
					break;
				}
			}
		}
		
		storedData.setInteger(0, 0);
		
		return storedData;
	}

}
