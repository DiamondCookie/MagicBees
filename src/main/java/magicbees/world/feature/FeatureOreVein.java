package magicbees.world.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import magicbees.main.utils.BlockUtil;

public class FeatureOreVein
{
	public static final FeatureOreVein redstoneGen;
	public static final FeatureOreVein netherQuartzGen;
	public static final FeatureOreVein glowstoneGen;
	public static final FeatureOreVein endStoneGen;

	static
	{
		redstoneGen = new FeatureOreVein(Blocks.redstone_ore, Blocks.stone);
		netherQuartzGen = new FeatureOreVein(Blocks.quartz_ore, Blocks.netherrack);
		glowstoneGen = new FeatureOreVein(Blocks.glowstone, Blocks.stone);
		endStoneGen = new FeatureOreVein(Blocks.end_stone, Blocks.stone);
	}

	private final Block veinBlock;
	private final int veinBlockMeta;
	private final Block replacesBlock;

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

			if (!world.isAirBlock(currentX, currentY, currentZ) && BlockUtil.canBlockReplaceAt(world, currentX, currentY, currentZ, replacesBlock))
			{
				world.setBlock(currentX, currentY, currentZ, this.veinBlock, this.veinBlockMeta, 2);
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
