package magicbees.world.feature;

import java.util.Random;

import magicbees.block.types.HiveType;
import magicbees.main.Config;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.feature.WorldGenMinable;
import forestry.api.core.GlobalManager;

public class FeatureHive
{
	private static WorldGenMinable redstoneGen;
	private static WorldGenMinable glowStoneGen;
	
	public static void initialize()
	{
		redstoneGen = new WorldGenMinable(Block.oreRedstone.blockID, 10);
		glowStoneGen = new WorldGenMinable(Block.glowStone.blockID, 16, Block.netherrack.blockID);
	}
	
	public static void generateHives(World world, Random random, int chunkX, int chunkZ, boolean initialGen)
	{
		if (world.provider.terrainType != WorldType.FLAT)
		{
			for (HiveType type : HiveType.values())
			{
				type.generateHive(world, random, chunkX, chunkZ, initialGen);
			}
		}
	}
	
	public static void generateHiveCurious(World world, Random random, int chunkX, int chunkZ, boolean initialGen)
	{
		// 1 per gen
		int coordX = random.nextInt(16) + chunkX;
		int coordZ = random.nextInt(16) + chunkZ;
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
		while (!doSpawn && coordY > 1);
		
		if (doSpawn)
		{
			world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.CURIOUS.ordinal(), 2);
		}
	}
	
	public static void generateHiveUnusual(World world, Random random, int chunkX, int chunkZ, boolean initialGen)
	{
		// 1 per gen
		int coordX = random.nextInt(16) + chunkX;
		int coordZ = random.nextInt(16) + chunkZ;
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
			world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.UNUSUAL.ordinal(), 2);
		}
	}
	
	public static void generateHiveResonant(World world, Random random, int chunkX, int chunkZ, boolean initialGen)
	{
		// 1 per gen
		int coordX = random.nextInt(16) + chunkX;
		int coordZ = random.nextInt(16) + chunkZ;
		int coordY = world.provider.getAverageGroundLevel() - 10;
		
	}
	
	public static void generateHiveDeep(World world, Random random, int chunkX, int chunkZ, boolean initialGen)
	{
		// 1 or 2 per gen
		int count = random.nextInt(1) + 1;

		for (int i = 0; i < count; ++i)
		{
			int coordX = random.nextInt(16) + chunkX;
			int coordZ = random.nextInt(16) + chunkZ;
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
				world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.DEEP.ordinal(), 2);
				
				// Spawn some redstone around it
				redstoneGen.generate(world, random, coordX, coordY, coordZ);
				
				for (int y = 0; y < 64; ++y)
				{
					for (int z = 0; z < 16; ++z)
					{
						for (int x = 0; x < 16; ++x)
						{
							if (world.getBlockId(x + chunkX, y, z + chunkX) == Block.stone.blockID ||
									world.getBlockId(x + chunkX, y, z + chunkX) == Block.dirt.blockID ||
									world.getBlockId(x + chunkX, y, z + chunkX) == Block.dirt.blockID)
							{
								world.setBlock(x + chunkX, y, z + chunkX, Block.glass.blockID, 0, 2);
							}
						}
					}
				}
			}
		}
	}
	
	public static void generateHiveInfernal(World world, Random random, int chunkX, int chunkZ, boolean initialGen)
	{
		// 2 or 3 per gen
		int count = random.nextInt(3) + 1;
		
		for (int i = 0; i < count; ++i)
		{
			int coordX = random.nextInt(16) + chunkX;
			int coordZ = random.nextInt(16) + chunkZ;
			int coordY = random.nextInt(60 / count) + i * (120 / count) + 5;
			boolean doSpawn = false;
			
			do
			{
				--coordY;
				int blockId = world.getBlockId(coordX, coordY, coordZ);
				if (world.isAirBlock(coordX, coordY, coordZ) || blockId == Block.netherrack.blockID)
				{
					doSpawn = true;
				}
			}
			while (!doSpawn && coordY > 4 && coordY < 126);
			
			if (doSpawn)
			{
				world.setBlock(coordX, coordY, chunkZ, Config.hive.blockID, HiveType.INFERNAL.ordinal(), 2);
				
				glowStoneGen.generate(world, random, coordX, coordY, coordZ);
			}
		}
	}
	
	public static void generateHiveOblivion(World world, Random random, int chunkX, int chunkZ, boolean initialGen)
	{
		// 1 per gen
		boolean doSpawn = false;
		int coordX = 0;
		int coordZ = 0;
		int coordY = 0;
		
		coordX = random.nextInt(16) + chunkX;
		coordZ = random.nextInt(16) + chunkZ;
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
			world.setBlock(coordX, coordY, chunkZ, Config.hive.blockID, HiveType.OBLIVION.ordinal(), 2);
			
			int obsidianSpikeHeight = random.nextInt(4) + 2;
			
			for (int i = 1; i < obsidianSpikeHeight && coordY - i > 0; ++i)
			{
				world.setBlock(coordX, coordY - i, chunkZ, Block.obsidian.blockID, 0, 2);
			}
		}
	}
}
