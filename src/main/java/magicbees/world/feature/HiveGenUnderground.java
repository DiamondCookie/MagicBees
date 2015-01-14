package magicbees.world.feature;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import forestry.api.apiculture.hives.IHiveGen;

import magicbees.main.utils.BlockUtil;

public class HiveGenUnderground implements IHiveGen
{
	protected final int minLevel;
	protected final int range;
	protected final Block replace;
	private final int surroundCount;

	public HiveGenUnderground(int minLevel, int range, int surroundCount) {
		this(minLevel, range, Blocks.stone, surroundCount);
	}

	public HiveGenUnderground(int minLevel, int range, Block replace, int surroundCount)
	{
		this.minLevel = minLevel;
		this.range = range;
		this.replace = replace;
		this.surroundCount = surroundCount;
	}

	@Override
	public int getYForHive(World world, int x, int z)
	{
		return minLevel + world.rand.nextInt(range);
	}

	@Override
	public boolean isValidLocation(World world, int x, int y, int z)
	{
		return BlockUtil.getSurroundCount(world, x, y, z, replace) >= surroundCount;
	}

	@Override
	public boolean canReplace(World world, int x, int y, int z)
	{
		return !world.isAirBlock(x, y, z) && BlockUtil.canBlockReplaceAt(world, x, y, z, replace);
	}
}
