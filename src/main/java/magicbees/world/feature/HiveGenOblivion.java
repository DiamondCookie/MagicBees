package magicbees.world.feature;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import forestry.api.apiculture.hives.IHiveGen;

import magicbees.main.utils.BlockUtil;

public class HiveGenOblivion implements IHiveGen {

	@Override
	public int getYForHive(World world, int x, int z) {

		int surface = world.getHeightValue(x, z);
		if (surface == 0)
		{
			return -1;
		}

		int y = 10;

		// get to the end stone
		while (!Block.isEqualTo(world.getBlock(x, y - 1, z), Blocks.end_stone) && y < surface)
		{
			y += 8;
		}

		// hive must be embedded one deep into the stone
		while (!Block.isEqualTo(world.getBlock(x, y - 2, z), Blocks.air) && y > -1)
		{
			y--;
		}

		return y;
	}

	@Override
	public boolean isValidLocation(World world, int x, int y, int z) {

		if (BlockUtil.getSurroundCount(world, x, y, z, Blocks.end_stone) != 6)
		{
			return false;
		}

		for (int i = 2; i < 4; i++)
		{
			if (!world.isAirBlock(x, y - i, z))
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean canReplace(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		return Block.isEqualTo(block, Blocks.end_stone);
	}
}
