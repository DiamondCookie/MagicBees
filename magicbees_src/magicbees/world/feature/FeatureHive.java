package magicbees.world.feature;

import java.util.Random;

import magicbees.block.types.HiveType;
import magicbees.main.Config;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.FMLLog;
import forestry.api.core.GlobalManager;

public class FeatureHive
{
	private static FeatureOreVein redstoneGen;
	private static FeatureOreVein netherQuartzGen;
	private static FeatureOreVein glowstoneGen;
	private static FeatureOreVein endStoneGen;
	
	private static boolean logSpawns = true;
	
	public static void initialize()
	{
		redstoneGen = new FeatureOreVein(Block.oreRedstone.blockID, Block.stone);
		netherQuartzGen = new FeatureOreVein(Block.oreNetherQuartz.blockID, Block.netherrack);
		glowstoneGen = new FeatureOreVein(Block.glowStone.blockID, Block.stone);
		endStoneGen = new FeatureOreVein(Block.whiteStone.blockID, Block.stone);
	}
	
	public static void generateHives(World world, Random random, int chunkX, int chunkZ, boolean initialGen)
	{
		for (HiveType type : HiveType.values())
		{
			type.generateHive(world, random, chunkX, chunkZ, initialGen);
		}
	}
	
	public static void generateHiveCurious(World world, Random random, int chunkX, int chunkZ, boolean initialGen)
	{
		// 1 per gen
		int coordX = random.nextInt(16) + chunkX * 16;
		int coordZ = random.nextInt(16) + chunkZ * 16;
		int coordY = world.getHeight() - 1;
		boolean doSpawn = false;
		
		do
		{
			--coordY;
			if (world.isAirBlock(coordX, coordY - 1, coordZ) && world.isAirBlock(coordX, coordY, coordZ) &&
					GlobalManager.leafBlockIds.contains(world.getBlockId(coordX, coordY + 1, coordZ))  )
			{
				doSpawn = true;
			}
		}
		while (!doSpawn && coordY > 20);
		
		if (doSpawn)
		{
			world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.CURIOUS.ordinal(), 3);
			if (logSpawns) 	FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Curious", coordX, coordZ, coordY);
		}
	}
	
	public static void generateHiveUnusual(World world, Random random, int chunkX, int chunkZ, boolean initialGen)
	{
		// 1 per gen
		int coordX = random.nextInt(16) + chunkX * 16;
		int coordZ = random.nextInt(16) + chunkZ * 16;
		int coordY = world.provider.getAverageGroundLevel() - 10;
		
		// For sanity
		if (coordY <= 0)
		{
			coordY = 0;
		}
		
		boolean doSpawn = false;
		do
		{
			++coordY;
			
			if (world.isAirBlock(coordX, coordY + 1, coordZ) && world.isAirBlock(coordX, coordY, coordZ) &&
					GlobalManager.dirtBlockIds.contains(world.getBlockId(coordX, coordY - 1, coordZ))  )
			{
				doSpawn = true;
			}
		}
		while (!doSpawn && coordY < world.getHeight());
		
		if (doSpawn)
		{
			world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.UNUSUAL.ordinal(), 3);
			if (logSpawns) FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Unusual", coordX, coordZ, coordY);
		}
	}
	
	public static void generateHiveResonant(World world, Random random, int chunkX, int chunkZ, boolean initialGen)
	{
		// 1 per gen
		int coordX = random.nextInt(16) + chunkX * 16;
		int coordZ = random.nextInt(16) + chunkZ * 16;
		int coordY = world.provider.getAverageGroundLevel() - 10;
		
		// For sanity
		if (coordY <= 0)
		{
			coordY = 0;
		}
		
		boolean doSpawn = false;
		do
		{
			++coordY;
			
			if (world.isAirBlock(coordX, coordY + 1, coordZ) &&
					(world.isAirBlock(coordX, coordY, coordZ) || world.getBlockId(coordX, coordY, coordZ) == Block.grass.blockID ||
					world.getBlockId(coordX, coordY, coordZ) == Block.snow.blockID) &&
					(GlobalManager.dirtBlockIds.contains(world.getBlockId(coordX, coordY - 1, coordZ)) ||
					 GlobalManager.sandBlockIds.contains(world.getBlockId(coordX, coordY - 1, coordZ))) )
			{
				doSpawn = true;
			}
		}
		while (!doSpawn && coordY < world.getHeight());
		
		if (doSpawn)
		{
			world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.RESONANT.ordinal(), 3);
			if (logSpawns) FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Resonant", coordX, coordZ, coordY);
		}
	}
	
	public static void generateHiveDeep(World world, Random random, int chunkX, int chunkZ, boolean initialGen)
	{
		// 1 or 2 per gen
		int count = random.nextInt(1) + 1;

		for (int i = 0; i < count; ++i)
		{
			int coordX = random.nextInt(16) + chunkX * 16;
			int coordZ = random.nextInt(16) + chunkZ * 16;
			int coordY = random.nextInt(16) + 17;
			boolean doSpawn = false;
			
			do
			{
				--coordY;
				int blockId = world.getBlockId(coordX, coordY, coordZ);
				if (blockId == Block.stone.blockID)
				{
					doSpawn = true;
				}
			}
			while (!doSpawn && coordY > 4);
			
			if (doSpawn)
			{
				world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.DEEP.ordinal(), 3);
				if (logSpawns) FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Deep", coordX, coordZ, coordY);
				

				redstoneGen.generateVein(world, random, coordX + 1, coordY, coordZ, 2);
				redstoneGen.generateVein(world, random, coordX - 1, coordY, coordZ, 2);
				redstoneGen.generateVein(world, random, coordX, coordY + 1, coordZ, 2);
				redstoneGen.generateVein(world, random, coordX, coordY - 1, coordZ, 2);
				redstoneGen.generateVein(world, random, coordX, coordY, coordZ + 1, 2);
				redstoneGen.generateVein(world, random, coordX, coordY, coordZ - 1, 2);
			}
		}
	}
	
	public static void generateHiveInfernal(World world, Random random, int chunkX, int chunkZ, boolean initialGen)
	{
		if (world.provider.dimensionId == -1)
		{
			// 2 or 3 per gen
			int count = random.nextInt(3) + 1;
			for (int i = 0; i < count; ++i)
			{
				int coordX = random.nextInt(16) + chunkX * 16;
				int coordZ = random.nextInt(16) + chunkZ * 16;
				int coordY = random.nextInt(60 / count) + i * (120 / count) - 5;
				boolean doSpawn = false;
				
				do
				{
					++coordY;
					
					if (!world.isAirBlock(coordX, coordY, coordZ) && world.getBlockId(coordX, coordY, coordZ) == Block.netherrack.blockID)
					{
						int surroundCount = 0;
						if (world.getBlockId(coordX+1, coordY, coordZ) == Block.netherrack.blockID)
						{
							++surroundCount;
						}
						if (world.getBlockId(coordX-1, coordY, coordZ) == Block.netherrack.blockID)
						{
							++surroundCount;
						}
						if (world.getBlockId(coordX, coordY+1, coordZ) == Block.netherrack.blockID)
						{
							++surroundCount;
						}
						if (world.getBlockId(coordX, coordY-1, coordZ) == Block.netherrack.blockID)
						{
							++surroundCount;
						}
						if (world.getBlockId(coordX, coordY, coordZ+1) == Block.netherrack.blockID)
						{
							++surroundCount;
						}
						if (world.getBlockId(coordX, coordY, coordZ-1) == Block.netherrack.blockID)
						{
							++surroundCount;
						}
						
						doSpawn = surroundCount >= 5;
					}
				}
				while (!doSpawn && coordY > 4 && coordY < 126);
				
				if (doSpawn)
				{
					world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.INFERNAL.ordinal(), 3);
					if (logSpawns) FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Infernal", coordX, coordZ, coordY);
	
					netherQuartzGen.generateVein(world, random, coordX + 1, coordY, coordZ, 4);
					netherQuartzGen.generateVein(world, random, coordX - 1, coordY, coordZ, 4);
					netherQuartzGen.generateVein(world, random, coordX, coordY + 1, coordZ, 4);
					netherQuartzGen.generateVein(world, random, coordX, coordY - 1, coordZ, 4);
					netherQuartzGen.generateVein(world, random, coordX, coordY, coordZ + 1, 4);
					netherQuartzGen.generateVein(world, random, coordX, coordY, coordZ - 1, 4);
				}
			}
		}
		else
		{
			// 1 per gen

			int coordX = random.nextInt(16) + chunkX * 16;
			int coordZ = random.nextInt(16) + chunkZ * 16;
			int coordY = random.nextInt(16) + 17;
			boolean doSpawn = false;
			
			do
			{
				--coordY;
				
				if (!world.isAirBlock(coordX, coordY, coordZ) && world.getBlockId(coordX, coordY, coordZ) == Block.stone.blockID)
				{
					int surroundCount = 0;
					if (world.getBlockId(coordX+1, coordY, coordZ) == Block.stone.blockID)
					{
						++surroundCount;
					}
					if (world.getBlockId(coordX-1, coordY, coordZ) == Block.stone.blockID)
					{
						++surroundCount;
					}
					if (world.getBlockId(coordX, coordY+1, coordZ) == Block.stone.blockID)
					{
						++surroundCount;
					}
					if (world.getBlockId(coordX, coordY-1, coordZ) == Block.stone.blockID)
					{
						++surroundCount;
					}
					if (world.getBlockId(coordX, coordY, coordZ+1) == Block.stone.blockID)
					{
						++surroundCount;
					}
					if (world.getBlockId(coordX, coordY, coordZ-1) == Block.stone.blockID)
					{
						++surroundCount;
					}
					
					doSpawn = surroundCount >= 5;
				}
			}
			while (!doSpawn && coordY > 4);
			
			if (doSpawn)
			{
				world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.INFERNAL.ordinal(), 3);
				if (logSpawns) FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Infernal", coordX, coordZ, coordY);

				glowstoneGen.generateVein(world, random, coordX + 1, coordY, coordZ, 2);
				glowstoneGen.generateVein(world, random, coordX - 1, coordY, coordZ, 2);
				if (world.getBlockId(coordX, coordY + 1, coordZ) == Block.stone.blockID)
				{
					world.setBlock(coordX, coordY + 1, coordZ, Block.glowStone.blockID, 0, 3);
				}
				if (world.getBlockId(coordX, coordY - 1, coordZ) == Block.stone.blockID)
				{
					world.setBlock(coordX, coordY - 1, coordZ, Block.glowStone.blockID, 0, 3);
				}
				glowstoneGen.generateVein(world, random, coordX, coordY, coordZ + 1, 2);
				glowstoneGen.generateVein(world, random, coordX, coordY, coordZ - 1, 2);
			}
		}
	}
	
	public static void generateHiveOblivion(World world, Random random, int chunkX, int chunkZ, boolean initialGen)
	{
		if (world.provider.dimensionId == 1)
		{
			// 1 per gen
			boolean doSpawn = false;
			int coordX = 0;
			int coordZ = 0;
			int coordY = 0;
			
			coordX = random.nextInt(16) + chunkX * 16;
			coordZ = random.nextInt(16) + chunkZ * 16;
			coordY = 8;
			
			do
			{
				++coordY;
				
				if (world.isAirBlock(coordX, coordY - 2, coordZ) &&
						world.getBlockId(coordX, coordY - 1, coordZ) == Block.whiteStone.blockID &&
						world.getBlockId(coordX, coordY, coordZ) == Block.whiteStone.blockID)
				{
					doSpawn = true;
				}
			}
			while (!doSpawn && coordY < 90);
			
			if (doSpawn)
			{
				world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.OBLIVION.ordinal(), 3);
				if (logSpawns) FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Oblivion", coordX, coordZ, coordY);
				
				int obsidianSpikeHeight = random.nextInt(8) + 2;
				
				for (int i = 1; i < obsidianSpikeHeight && coordY - i > 0; ++i)
				{
					world.setBlock(coordX, coordY - i, coordZ, Block.obsidian.blockID, 0, 2);
				}
			}
		}
		else
		{
			// 1 per gen

			int coordX = random.nextInt(16) + chunkX * 16;
			int coordZ = random.nextInt(16) + chunkZ * 16;
			int coordY = random.nextInt(16) + 9;
			boolean doSpawn = false;
			
			do
			{
				--coordY;
				
				if (!world.isAirBlock(coordX, coordY, coordZ) && world.getBlockId(coordX, coordY, coordZ) == Block.stone.blockID)
				{
					int surroundCount = 0;
					if (world.getBlockId(coordX+1, coordY, coordZ) == Block.stone.blockID)
					{
						++surroundCount;
					}
					if (world.getBlockId(coordX-1, coordY, coordZ) == Block.stone.blockID)
					{
						++surroundCount;
					}
					if (world.getBlockId(coordX, coordY+1, coordZ) == Block.stone.blockID)
					{
						++surroundCount;
					}
					if (world.getBlockId(coordX, coordY-1, coordZ) == Block.stone.blockID)
					{
						++surroundCount;
					}
					if (world.getBlockId(coordX, coordY, coordZ+1) == Block.stone.blockID)
					{
						++surroundCount;
					}
					if (world.getBlockId(coordX, coordY, coordZ-1) == Block.stone.blockID)
					{
						++surroundCount;
					}
					
					doSpawn = surroundCount >= 5;
				}
			}
			while (!doSpawn && coordY > 5);
			
			if (doSpawn)
			{
				world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.OBLIVION.ordinal(), 3);
				if (logSpawns) FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Oblivion", coordX, coordZ, coordY);

				endStoneGen.generateVein(world, random, coordX + 1, coordY, coordZ, 3);
				endStoneGen.generateVein(world, random, coordX - 1, coordY, coordZ, 3);
				endStoneGen.generateVein(world, random, coordX, coordY + 1, coordZ, 2);
				endStoneGen.generateVein(world, random, coordX, coordY - 1, coordZ, 2);
				endStoneGen.generateVein(world, random, coordX, coordY, coordZ + 1, 3);
				endStoneGen.generateVein(world, random, coordX, coordY, coordZ - 1, 3);
			}
		}
	}
}
