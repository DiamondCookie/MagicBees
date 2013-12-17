package magicbees.world.feature;

import java.util.Random;

import magicbees.block.types.HiveType;
import magicbees.main.Config;
import magicbees.main.MagicBees;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import cpw.mods.fml.common.FMLLog;
import forestry.api.core.GlobalManager;

public class FeatureHive
{
	private static FeatureOreVein redstoneGen;
	private static FeatureOreVein netherQuartzGen;
	private static FeatureOreVein glowstoneGen;
	private static FeatureOreVein endStoneGen;
	
	private static boolean logSpawns = false;
	
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
	
	public static boolean generateHiveCurious(World world, Random random, int coordX, int coordZ, boolean initialGen)
	{
		int coordY = getHeight(world, random, coordX, coordZ);
		boolean doSpawn = false;
		
		Block b = Block.blocksList[world.getBlockId(coordX, coordY + 1, coordZ)];
		if (world.isAirBlock(coordX, coordY - 1, coordZ) && world.isAirBlock(coordX, coordY, coordZ) &&
				b != null && b.isLeaves(world, coordX, coordY + 1, coordZ))
		{
			world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.CURIOUS.ordinal(), 3);
			if (logSpawns) 	FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Curious", coordX, coordZ, coordY);
			doSpawn = true;
		}
		
		return doSpawn;
	}
	
	public static boolean generateHiveUnusual(World world, Random random, int coordX, int coordZ, boolean initialGen)
	{
		int coordY = getHeight(world, random, coordX, coordZ);
		
		boolean doSpawn = false;
		if (world.isAirBlock(coordX, coordY + 1, coordZ) && world.isAirBlock(coordX, coordY, coordZ) &&
				GlobalManager.dirtBlockIds.contains(world.getBlockId(coordX, coordY - 1, coordZ))  )
		{
			doSpawn = true;
		}
		
		if (doSpawn)
		{
			world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.UNUSUAL.ordinal(), 3);
			if (logSpawns) FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Unusual", coordX, coordZ, coordY);
		}
		
		return doSpawn;
	}
	
	public static boolean generateHiveResonant(World world, Random random, int coordX, int coordZ, boolean initialGen)
	{
		int coordY = getHeight(world, random, coordX, coordZ);
		boolean doSpawn = false;
		if (world.isAirBlock(coordX, coordY + 1, coordZ) &&
				(world.isAirBlock(coordX, coordY, coordZ) || world.getBlockId(coordX, coordY, coordZ) == Block.tallGrass.blockID) &&
				(GlobalManager.dirtBlockIds.contains(world.getBlockId(coordX, coordY - 1, coordZ)) ||
				 GlobalManager.sandBlockIds.contains(world.getBlockId(coordX, coordY - 1, coordZ))) )
		{
			doSpawn = true;
		}
		
		if (doSpawn)
		{
			world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.RESONANT.ordinal(), 3);
			if (logSpawns) FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Resonant", coordX, coordZ, coordY);
		}
		
		return doSpawn;
	}
	
	public static boolean generateHiveDeep(World world, Random random, int coordX, int coordZ, boolean initialGen)
	{

		BiomeGenBase biome = world.getBiomeGenForCoords(coordX, coordZ);
		int coordY = 10 + random.nextInt(10);

		boolean doSpawn = false;
		
		if (random.nextInt(100) < 40 && 
				!world.isAirBlock(coordX, coordY, coordZ) &&
				world.getBlockId(coordX, coordY, coordZ) == Block.stone.blockID)
		{			
			doSpawn = getSurroundCount(world, coordX, coordY, coordZ, Block.stone) >= 6;
		}
		
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
		
		return doSpawn;
	}
	
	public static boolean generateHiveInfernal(World world, Random random, int coordX, int coordZ, boolean initialGen)
	{
		boolean doSpawn = false;
		if (!world.provider.isSurfaceWorld())
		{
			int chop = random.nextInt(2) + 1;
			int coordY = random.nextInt(60 / chop) + random.nextInt(chop) * (120 / chop) - 5;
			
			if (!world.isAirBlock(coordX, coordY, coordZ) && world.getBlockId(coordX, coordY, coordZ) == Block.netherrack.blockID)
			{				
				doSpawn = getSurroundCount(world, coordX, coordY, coordZ, Block.netherrack) >= 5;
			}
			
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
		else if (MagicBees.getConfig().DoSpecialHiveGen)
		{
			int coordY = random.nextInt(13) + 5;
			
			if (!world.isAirBlock(coordX, coordY, coordZ) && world.getBlockId(coordX, coordY, coordZ) == Block.stone.blockID)
			{
				
				doSpawn = getSurroundCount(world, coordX, coordY, coordZ, Block.stone) >= 6;
			}
			
			if (doSpawn)
			{
				world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.INFERNAL.ordinal(), 3);
				if (logSpawns) FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Infernal", coordX, coordZ, coordY);

				glowstoneGen.generateVein(world, random, coordX + 1, coordY, coordZ, 2);
				glowstoneGen.generateVein(world, random, coordX - 1, coordY, coordZ, 2);
				if (world.getBlockId(coordX, coordY + 1, coordZ) == Block.stone.blockID ||
						world.getBlockId(coordX, coordY + 1, coordZ) == Block.netherrack.blockID)
				{
					world.setBlock(coordX, coordY + 1, coordZ, Block.glowStone.blockID, 0, 3);
				}
				if (world.getBlockId(coordX, coordY - 1, coordZ) == Block.stone.blockID ||
						world.getBlockId(coordX, coordY + 1, coordZ) == Block.netherrack.blockID)
				{
					world.setBlock(coordX, coordY - 1, coordZ, Block.glowStone.blockID, 0, 3);
				}
				glowstoneGen.generateVein(world, random, coordX, coordY, coordZ + 1, 2);
				glowstoneGen.generateVein(world, random, coordX, coordY, coordZ - 1, 2);
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
					world.getBlockId(coordX, coordY - 1, coordZ) == Block.whiteStone.blockID &&
					world.getBlockId(coordX, coordY, coordZ) == Block.whiteStone.blockID)
			{
				doSpawn = true;
			}
			
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
		else if (MagicBees.getConfig().DoSpecialHiveGen)
		{
			// 1 per gen
			int coordY = random.nextInt(5) + 5;
			
			do
			{
				--coordY;
				
				if (!world.isAirBlock(coordX, coordY, coordZ) && world.getBlockId(coordX, coordY, coordZ) == Block.stone.blockID ||
						world.getBlockId(coordX, coordY + 1, coordZ) == Block.whiteStone.blockID)
				{					
					doSpawn = getSurroundCount(world, coordX, coordY, coordZ, Block.whiteStone) >= 5 ||
							getSurroundCount(world, coordX, coordY, coordZ, Block.stone) >= 5;
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
		return doSpawn;
	}
	
	private static int getHeight(World world, Random random, int coordX, int coordZ)
	{
		BiomeGenBase biome = world.getBiomeGenForCoords(coordX, coordZ);
		int min = (int)(biome.minHeight * 64 + 62);
		int max = (int)(biome.maxHeight * 64);
		// sanity
		min = (min > 1) ? min : 1;
		max = (max > 10) ? max : 10;
		return min + random.nextInt(max);
	}
	
	private static int getSurroundCount(World world, int x, int y, int z, Block blockType)
	{
		int surroundCount = 0;
		
		if (world.getBlockId(x+1, y, z) == blockType.blockID)
		{
			++surroundCount;
		}
		if (world.getBlockId(x-1, y, z) == blockType.blockID)
		{
			++surroundCount;
		}
		if (world.getBlockId(x, y+1, z) == blockType.blockID)
		{
			++surroundCount;
		}
		if (world.getBlockId(x, y-1, z) == blockType.blockID)
		{
			++surroundCount;
		}
		if (world.getBlockId(x, y, z+1) == blockType.blockID)
		{
			++surroundCount;
		}
		if (world.getBlockId(x, y, z-1) == blockType.blockID)
		{
			++surroundCount;
		}
		
		return surroundCount;
	}
}
