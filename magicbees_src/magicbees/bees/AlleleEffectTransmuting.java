package magicbees.bees;

import java.util.Locale;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.oredict.OreDictionary;
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

		// Check if == stone
		boolean doTransmute = false;
		Block block = Block.blocksList[world.getBlockId(xCoord, yCoord, zCoord)];
		if (block == null || block == Block.stone || block == Block.cobblestone)
		{
			doTransmute = true;
		}
		else if (block != null)
		{
			ItemStack cobbleItem = new ItemStack(Block.cobblestone);
			ItemStack stoneItem = new ItemStack(Block.stone);
			ItemStack testItem = new ItemStack(block);
			doTransmute = OreDictionary.itemMatches(cobbleItem, testItem, false) || OreDictionary.itemMatches(stoneItem, cobbleItem, false); 
		}
	
		// Transmute to something.
		if (doTransmute)
		{
			BiomeGenBase biome = world.getBiomeGenForCoords(xCoord, zCoord);
			// Gogo shortcutting logic operators!
			boolean tryThis = trySpawnAbyssalStone(world, biome, xCoord, yCoord, zCoord) ||
				trySpawnQuarriedStone(world, biome, xCoord, yCoord, zCoord) || 
				trySpawnSandstone(world, biome, xCoord, yCoord, zCoord);
		}
		storedData.setInteger(0, 0);

		return storedData;
	}
	
	private boolean trySpawnQuarriedStone(World world, BiomeGenBase biome, int x, int y, int z)
	{
		boolean flag = false;
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.FOREST) && 
    		!BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.FROZEN))
		{
			ItemStack item = GameRegistry.findItemStack("Railcraft", "stone.abyssal", 1);
			if (item != null)
			{
				world.setBlock(x, y, z, item.itemID, item.getItemDamage(), 2);
				flag = true;
			}
	    }
		return flag;
	}

	private boolean trySpawnAbyssalStone(World world, BiomeGenBase biome, int x, int y, int z)
	{
		boolean flag = false;
	    if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.WATER) &&
	    		biome.biomeName.toLowerCase(Locale.ENGLISH).contains("river"))
		{
			ItemStack item = GameRegistry.findItemStack("Railcraft", "stone.quarried", 1);
			if (item != null)
			{
				world.setBlock(x, y, z, item.itemID, item.getItemDamage(), 2);
				flag = true;
			}
	    }
	    return flag;
	}
	
	private boolean trySpawnSandstone(World world, BiomeGenBase biome, int x, int y, int z)
	{
		boolean flag = false;
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.DESERT))
		{
			world.setBlock(x, y, z, Block.sandStone.blockID);
			flag = true;
		}
		return flag;
	}

}
