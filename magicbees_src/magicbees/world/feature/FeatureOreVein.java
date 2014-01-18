package magicbees.world.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeDirection;

public class FeatureOreVein
{
	private Block veinBlock;
	private int veinBlockMeta;
	private Block replacesBlock;
	
	public FeatureOreVein(Block block, Block replacesBlock)
	{
		this(block, 0, replacesBlock);
	}
	
	public FeatureOreVein(Block block, int meta, Block replacesBlock)
	{
		this.veinBlock = block;
		this.veinBlockMeta = meta;
		this.replacesBlock = replacesBlock;
	}
	
	public void generateVein(World world, Random random, int startX, int startY, int startZ, int maxSpawnCount)
	{
		int currentX = startX;
		int currentY = startY;
		int currentZ = startZ;
		int spawnAttempts = 0;
		int spawnCount = 0;
		
		while (spawnCount < maxSpawnCount && spawnAttempts < maxSpawnCount * 2)
		{
			++spawnAttempts;
			
			if (!world.isAirBlock(currentX, currentY, currentZ) && canBlockReplaceAt(world, currentX, currentY, currentZ, replacesBlock))
			{
				world.setBlock(currentX, currentY, currentZ, this.veinBlock.blockID, this.veinBlockMeta, 2);
				++spawnCount;
			}

			switch (random.nextInt(6))
			{
			case 0:
				++currentY;
				break;
			case 1:
				--currentY;
				break;
			case 2:
				++currentZ;
				break;
			case 3:
				--currentZ;
				break;
			case 4:
				++currentX;
				break;
			case 5:
				--currentX;
				break;
			}
		}
	}
	
	private static boolean canBlockReplaceAt(World world, int x, int y, int z, Block block)
	{
		return Block.blocksList[world.getBlockId(x, y, z)] != null &&
				Block.blocksList[world.getBlockId(x, y, z)].isGenMineableReplaceable(world, x, y, z, block.blockID);
	}
}
