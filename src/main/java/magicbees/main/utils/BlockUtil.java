package magicbees.main.utils;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BlockUtil {

	public static int getSurroundCount(World world, int x, int y, int z, Block blockType)
	{
		int surroundCount = 0;

		if (canBlockReplaceAt(world, x + 1, y, z, blockType))
		{
			++surroundCount;
		}

		if (canBlockReplaceAt(world, x - 1, y, z, blockType))
		{
			++surroundCount;
		}

		if (canBlockReplaceAt(world, x, y + 1, z, blockType))
		{
			++surroundCount;
		}

		if (canBlockReplaceAt(world, x, y - 1, z, blockType))
		{
			++surroundCount;
		}

		if (canBlockReplaceAt(world, x, y, z + 1, blockType))
		{
			++surroundCount;
		}

		if (canBlockReplaceAt(world, x, y, z - 1, blockType))
		{
			++surroundCount;
		}

		return surroundCount;
	}

	public static boolean canBlockReplaceAt(World world, int x, int y, int z, Block target)
	{
		// don't cause new chunks to load
		if (!world.blockExists(x, y, z))
		{
			return true;
		}
		Block block = world.getBlock(x, y, z);
		return block.isReplaceableOreGen(world, x, y, z, target);
	}
}
