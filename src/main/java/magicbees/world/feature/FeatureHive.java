package magicbees.world.feature;

import java.util.Random;

import magicbees.block.types.HiveType;
import magicbees.main.Config;
import magicbees.main.MagicBees;
import magicbees.main.utils.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class FeatureHive
{
	private static FeatureOreVein redstoneGen;
	private static FeatureOreVein netherQuartzGen;
	private static FeatureOreVein glowstoneGen;
	private static FeatureOreVein endStoneGen;

	private static boolean logSpawns = MagicBees.getConfig().LogHiveSpawns;

	public static void initialize()
	{
		redstoneGen = new FeatureOreVein(Blocks.redstone_ore, Blocks.stone);
		netherQuartzGen = new FeatureOreVein(Blocks.quartz_ore, Blocks.netherrack);
		glowstoneGen = new FeatureOreVein(Blocks.glowstone, Blocks.stone);
		endStoneGen = new FeatureOreVein(Blocks.end_stone, Blocks.stone);
	}

	public static void generateHives(World world, Random random, int chunkX, int chunkZ, boolean initialGen)
	{
		for (HiveType type : HiveType.values())
		{
			type.generateHive(world, random, chunkX, chunkZ, initialGen);
		}
	}

	public static boolean generateHiveCurious(World world, Random random, int coordX, int coordZ, boolean initialGen)
	{
		int coordY = getHeight(world, random, coordX, coordZ);
		boolean doSpawn = false;

		Block b = world.getBlock(coordX, coordY + 1, coordZ);
		if (world.isAirBlock(coordX, coordY - 1, coordZ) &&
				world.isAirBlock(coordX, coordY, coordZ) &&
				b != null && b.isLeaves(world, coordX, coordY + 1, coordZ))
		{
			world.setBlock(coordX, coordY, coordZ, Config.hive, HiveType.CURIOUS.ordinal(), 3);
			if (logSpawns)
				LogHelper.info("Spawning curious hive at: X " + coordX + ", Y: " + coordY + ", Z: " + coordZ);
			doSpawn = true;
		}

		return doSpawn;
	}

	public static boolean generateHiveUnusual(World world, Random random, int coordX, int coordZ, boolean initialGen)
	{
		int coordY = getHeight(world, random, coordX, coordZ);

		boolean doSpawn = false;
		if (world.isAirBlock(coordX, coordY + 1, coordZ) &&
				world.isAirBlock(coordX, coordY, coordZ) &&
				canBlockReplaceAt(world, coordX, coordY - 1, coordZ, Blocks.grass))
		{
			doSpawn = true;
		}

		if (doSpawn)
		{
			world.setBlock(coordX, coordY, coordZ, Config.hive, HiveType.UNUSUAL.ordinal(), 3);
			if (logSpawns)
				LogHelper.info("Spawning unusual hive at: X " + coordX + ", Y: " + coordY + ", Z: " + coordZ);
		}

		return doSpawn;
	}

	public static boolean generateHiveResonant(World world, Random random, int coordX, int coordZ, boolean initialGen)
	{
		int coordY = getHeight(world, random, coordX, coordZ);
		boolean doSpawn = false;
		if (world.rand.nextInt() < 40 && world.isAirBlock(coordX, coordY + 1, coordZ) &&
				(world.isAirBlock(coordX, coordY, coordZ) || canBlockReplaceAt(world, coordX, coordY, coordZ,
						Blocks.tallgrass)) &&
				(canBlockReplaceAt(world, coordX, coordY - 1, coordZ, Blocks.grass) ||
						canBlockReplaceAt(world, coordX, coordY - 1, coordZ, Blocks.sand)))
		{
			doSpawn = true;
		}

		if (doSpawn)
		{
			world.setBlock(coordX, coordY, coordZ, Config.hive, HiveType.RESONANT.ordinal(), 3);
			if (logSpawns)
				LogHelper.info("Spawning resonant hive at: X " + coordX + ", Y: " + coordY + ", Z: " + coordZ);
		}

		return doSpawn;
	}

	public static boolean generateHiveDeep(World world, Random random, int coordX, int coordZ, boolean initialGen)
	{

		BiomeGenBase biome = world.getBiomeGenForCoords(coordX, coordZ);
		int coordY = 10 + random.nextInt(10);

		boolean doSpawn = false;

		if (random.nextInt(100) < 40 &&
				!world.isAirBlock(coordX, coordY, coordZ) && canBlockReplaceAt(world, coordX, coordY, coordZ,
				Blocks.stone))
		{
			doSpawn = getSurroundCount(world, coordX, coordY, coordZ, Blocks.stone) >= 6;
		}

		if (doSpawn)
		{
			world.setBlock(coordX, coordY, coordZ, Config.hive, HiveType.DEEP.ordinal(), 3);
			if (logSpawns) LogHelper.info("Spawning deep hive at: X " + coordX + ", Y: " + coordY + ", Z: " + coordZ);


			redstoneGen.generateVein(world, random, coordX + 1, coordY, coordZ, 2);
			redstoneGen.generateVein(world, random, coordX - 1, coordY, coordZ, 2);
			redstoneGen.generateVein(world, random, coordX, coordY + 1, coordZ, 2);
			redstoneGen.generateVein(world, random, coordX, coordY - 1, coordZ, 2);
			redstoneGen.generateVein(world, random, coordX, coordY, coordZ + 1, 2);
			redstoneGen.generateVein(world, random, coordX, coordY, coordZ - 1, 2);
		}

		return doSpawn;
	}

	public static boolean generateHiveInfernal(World world, Random random, int coordX, int coordZ, boolean initialGen)
	{
		boolean doSpawn = false;
		if (!world.provider.isSurfaceWorld())
		{
			int chop = random.nextInt(2) + 1;
			int coordY = random.nextInt(60 / chop) + random.nextInt(chop) * (120 / chop) - 5;

			if (!world.isAirBlock(coordX, coordY, coordZ) && world.getBlock(coordX, coordY, coordZ) != null &&
					world.getBlock(coordX, coordY, coordZ).isReplaceableOreGen(world, coordX, coordY, coordZ,
							Blocks.netherrack))
			{
				doSpawn = getSurroundCount(world, coordX, coordY, coordZ, Blocks.netherrack) >= 5;
			}

			if (doSpawn)
			{
				world.setBlock(coordX, coordY, coordZ, Config.hive, HiveType.INFERNAL.ordinal(), 3);
				if (logSpawns)
					LogHelper.info("Spawning infernal hive at: X " + coordX + ", Y: " + coordY + ", Z: " + coordZ);

				netherQuartzGen.generateVein(world, random, coordX + 1, coordY, coordZ, 4);
				netherQuartzGen.generateVein(world, random, coordX - 1, coordY, coordZ, 4);
				netherQuartzGen.generateVein(world, random, coordX, coordY + 1, coordZ, 4);
				netherQuartzGen.generateVein(world, random, coordX, coordY - 1, coordZ, 4);
				netherQuartzGen.generateVein(world, random, coordX, coordY, coordZ + 1, 4);
				netherQuartzGen.generateVein(world, random, coordX, coordY, coordZ - 1, 4);
			}
		}
		else if (MagicBees.getConfig().DoSpecialHiveGen && world.rand.nextInt() < 11)
		{
			int coordY = random.nextInt(13) + 5;

			if (!world.isAirBlock(coordX, coordY, coordZ) &&
					canBlockReplaceAt(world, coordX, coordY, coordZ, Blocks.stone))
			{

				doSpawn = getSurroundCount(world, coordX, coordY, coordZ, Blocks.stone) >= 6;
			}

			if (doSpawn)
			{
				world.setBlock(coordX, coordY, coordZ, Config.hive, HiveType.INFERNAL.ordinal(), 3);
				if (logSpawns)
					LogHelper.info("Spawning infernal hive at: X " + coordX + ", Y: " + coordY + ", Z: " + coordZ);

				glowstoneGen.generateVein(world, random, coordX + 1, coordY, coordZ, world.rand.nextInt(4) + 1);
				glowstoneGen.generateVein(world, random, coordX - 1, coordY, coordZ, world.rand.nextInt(4) + 1);

				if (canBlockReplaceAt(world, coordX, coordY + 1, coordZ, Blocks.stone) ||
						canBlockReplaceAt(world, coordX, coordY + 1, coordZ, Blocks.netherrack))
				{
					world.setBlock(coordX, coordY + 1, coordZ, Blocks.glowstone, 0, 3);
				}

				if (canBlockReplaceAt(world, coordX, coordY - 1, coordZ, Blocks.stone) ||
						canBlockReplaceAt(world, coordX, coordY + 1, coordZ, Blocks.netherrack))
				{
					world.setBlock(coordX, coordY - 1, coordZ, Blocks.glowstone, 0, 3);
				}

				glowstoneGen.generateVein(world, random, coordX, coordY, coordZ + 1, world.rand.nextInt(4) + 1);
				glowstoneGen.generateVein(world, random, coordX, coordY, coordZ - 1, world.rand.nextInt(4) + 1);
			}
		}

		return doSpawn;
	}

	public static boolean generateHiveOblivion(World world, Random random, int coordX, int coordZ, boolean initialGen)
	{
		boolean doSpawn = false;
		if (!world.provider.isSurfaceWorld())
		{
			// 1 per gen
			int coordY = 10 + random.nextInt(25);

			if (world.isAirBlock(coordX, coordY - 2, coordZ) &&
					canBlockReplaceAt(world, coordX, coordY - 1, coordZ, Blocks.end_stone) &&
					canBlockReplaceAt(world, coordX, coordY, coordZ, Blocks.end_stone))
			{
				doSpawn = true;
			}

			if (doSpawn)
			{
				world.setBlock(coordX, coordY, coordZ, Config.hive, HiveType.OBLIVION.ordinal(), 3);
				if (logSpawns)
					LogHelper.info("Spawning oblivion hive at: X " + coordX + ", Y: " + coordY + ", Z: " + coordZ);

				int obsidianSpikeHeight = random.nextInt(8) + 2;

				for (int i = 1; i < obsidianSpikeHeight && coordY - i > 0; ++i)
				{
					world.setBlock(coordX, coordY - i, coordZ, Blocks.obsidian, 0, 2);
				}
			}
		}
		else if (MagicBees.getConfig().DoSpecialHiveGen && world.rand.nextInt() < 8)
		{
			// 1 per gen
			int coordY = random.nextInt(5) + 5;

			do
			{
				--coordY;

				if (!world.isAirBlock(coordX, coordY, coordZ) &&
						canBlockReplaceAt(world, coordX, coordY, coordZ, Blocks.stone) ||
						canBlockReplaceAt(world, coordX, coordY, coordZ, Blocks.end_stone))
				{
					doSpawn = getSurroundCount(world, coordX, coordY, coordZ, Blocks.end_stone) >= 5 ||
							getSurroundCount(world, coordX, coordY, coordZ, Blocks.stone) >= 5;
				}
			}
			while (!doSpawn && coordY > 5);

			if (doSpawn)
			{
				world.setBlock(coordX, coordY, coordZ, Config.hive, HiveType.OBLIVION.ordinal(), 3);
				if (logSpawns)
					LogHelper.info("Spawning oblivion hive at: X " + coordX + ", Y: " + coordY + ", Z: " + coordZ);

				endStoneGen.generateVein(world, random, coordX + 1, coordY, coordZ, world.rand.nextInt(6) + 1);
				endStoneGen.generateVein(world, random, coordX - 1, coordY, coordZ, world.rand.nextInt(6) + 1);
				endStoneGen.generateVein(world, random, coordX, coordY + 1, coordZ, world.rand.nextInt(3) + 1);
				endStoneGen.generateVein(world, random, coordX, coordY - 1, coordZ, world.rand.nextInt(3) + 1);
				endStoneGen.generateVein(world, random, coordX, coordY, coordZ + 1, world.rand.nextInt(6) + 1);
				endStoneGen.generateVein(world, random, coordX, coordY, coordZ - 1, world.rand.nextInt(6) + 1);
			}
		}
		return doSpawn;
	}

	private static int getHeight(World world, Random random, int coordX, int coordZ)
	{
		BiomeGenBase biome = world.getBiomeGenForCoords(coordX, coordZ);
		int min = (int)(biome.rootHeight/*minHeight*/ * 64 + 62);
		int max = (int)(biome.heightVariation/*maxHeight*/ * 64);
		// sanity
		min = (min > 1) ? min : 1;
		max = (max > 10) ? max : 10;
		return min + random.nextInt(max);
	}

	// Determines if surrounding material is "equivalent" to provided block using isGenMineableReplaceable.
	private static int getSurroundCount(World world, int x, int y, int z, Block blockType)
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

	private static boolean canBlockReplaceAt(World world, int x, int y, int z, Block block)
	{
		return world.getBlock(x, y, z) != null &&
				world.getBlock(x, y, z).isReplaceableOreGen(world, x, y, z, block);
	}
}
