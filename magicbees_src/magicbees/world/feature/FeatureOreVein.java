package magicbees.world.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeDirection;

public class FeatureOreVein
{
	private int veinBlockId;
	private int veinBlockMeta;
	private int replacesBlockId;
	
	public FeatureOreVein(int blockId, Block replacesBlock)
	{
		this(blockId, 0, replacesBlock);
	}
	
	public FeatureOreVein(int blockId, int meta, Block replacesBlock)
	{
		this.veinBlockId = blockId;
		this.veinBlockMeta = meta;
		this.replacesBlockId = replacesBlock.blockID;
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
			
			if (!world.isAirBlock(currentX, currentY, currentZ) && world.getBlockId(currentX, currentY, currentZ) == replacesBlockId)
			{
				world.setBlock(currentX, currentY, currentZ, this.veinBlockId, this.veinBlockMeta, 2);
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
}
