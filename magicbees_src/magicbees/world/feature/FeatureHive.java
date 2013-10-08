package magicbees.world.feature;

import java.util.Random;
import java.lang.Math;

import magicbees.block.types.HiveType;
import magicbees.main.Config;
import magicbees.main.MagicBees;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
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
			if(random.nextFloat() < type.getBiomeSpawnChance(world.getBiomeGenForCoordsBody(chunkX * 16, chunkZ * 16)))
			{
				for (int i = 0; i < 5; ++i)
				{
					int coordX = chunkX * 16 + random.nextInt(16);
					int coordZ = chunkZ * 16 + random.nextInt(16);
					if (type.generateHive(world, random, coordX, coordZ, initialGen))
					{
						break;
					}
				}
			}
		}
	}
	
	public static boolean generateHiveCurious(World world, Random random, int coordX, int coordZ, boolean initialGen)
	{
		int heightMapY = getHeightMapY(world, coordX, coordZ);
		
		if(!isLeaves(world, coordX, heightMapY - 1, coordZ))
		{
			return false;
		}
		
		int undersideY = seekGround(world, coordX, heightMapY, coordZ);
		
		// Find lowest branch
		while(!isLeaves(world, coordX, undersideY + 1, coordZ) && (undersideY < heightMapY))
		{
			undersideY++;
		}
		
		int coordY = 0;
		for(int i = 5; i > 0; i--)
		{
			// Attempt to spawn the hive on a random tree branch height
			if((i > 1) && ((heightMapY - undersideY) > 0))
			{
				coordY = random.nextInt(heightMapY - undersideY) + undersideY;
				while(!isLeaves(world, coordX, coordY + 1, coordZ) && (coordY < heightMapY))
				{
					coordY++;
				}
			}
			
			// Default to lowest branch
			else
			{
				coordY = undersideY;
			}
			
			if( isSoft(world, coordX, coordY - 1, coordZ) &&
				isSoft(world, coordX, coordY, coordZ) &&
				isLeaves(world, coordX, coordY + 1, coordZ) )
			{
				world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.CURIOUS.ordinal(), 3);
				if (logSpawns) 	FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Curious", coordX, coordZ, coordY);
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean generateHiveUnusual(World world, Random random, int coordX, int coordZ, boolean initialGen)
	{
		int coordY = findSurface(world, coordX, coordZ);
		
		if( world.isAirBlock(coordX, coordY + 1, coordZ) &&
			isSoft(world, coordX, coordY, coordZ) &&
			GlobalManager.dirtBlockIds.contains(world.getBlockId(coordX, coordY - 1, coordZ)) )
		{
			world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.UNUSUAL.ordinal(), 3);
			if (logSpawns) FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Unusual", coordX, coordZ, coordY);
			return true;
		}
		
		return false;
	}
	
	public static boolean generateHiveResonant(World world, Random random, int coordX, int coordZ, boolean initialGen)
	{
		int coordY = findSurface(world, coordX, coordZ);
		
		if( world.isAirBlock(coordX, coordY + 1, coordZ) &&
			isSoft(world, coordX, coordY, coordZ) &&
			(GlobalManager.dirtBlockIds.contains(world.getBlockId(coordX, coordY - 1, coordZ)) || GlobalManager.sandBlockIds.contains(world.getBlockId(coordX, coordY - 1, coordZ))) )
		{
			world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.RESONANT.ordinal(), 3);
			if (logSpawns) FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Resonant", coordX, coordZ, coordY);
			return true;
		}
		
		return false;
	}
	
	public static boolean generateHiveDeep(World world, Random random, int coordX, int coordZ, boolean initialGen)
	{

		BiomeGenBase biome = world.getBiomeGenForCoords(coordX, coordZ);
		int coordY = 10 + random.nextInt(10);
		
		if( world.getBlockId(coordX, coordY, coordZ) == Block.stone.blockID &&
			(getSurroundCount(world, coordX, coordY, coordZ, Block.stone) >= 6) )
		{
			world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.DEEP.ordinal(), 3);
			if (logSpawns) FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Deep", coordX, coordZ, coordY);
			
			redstoneGen.generateVein(world, random, coordX + 1, coordY, coordZ, 2);
			redstoneGen.generateVein(world, random, coordX - 1, coordY, coordZ, 2);
			redstoneGen.generateVein(world, random, coordX, coordY + 1, coordZ, 2);
			redstoneGen.generateVein(world, random, coordX, coordY - 1, coordZ, 2);
			redstoneGen.generateVein(world, random, coordX, coordY, coordZ + 1, 2);
			redstoneGen.generateVein(world, random, coordX, coordY, coordZ - 1, 2);
			
			return true;
		}
		
		return false;
	}
	
	public static boolean generateHiveInfernal(World world, Random random, int coordX, int coordZ, boolean initialGen)
	{
		if (!world.provider.isSurfaceWorld())
		{
			/*int chop = random.nextInt(2) + 1;
			int coordY = random.nextInt(60 / chop) + random.nextInt(chop) * (120 / chop) - 5;*/
			
			int coordY = random.nextInt(64) + (int)Math.round(Math.pow(((random.nextDouble() * 2) - 1), 3) * 32 + 32);
			
			if( world.getBlockId(coordX, coordY, coordZ) == Block.netherrack.blockID &&
				(getSurroundCount(world, coordX, coordY, coordZ, Block.netherrack) >= 5) )
			{
				world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.INFERNAL.ordinal(), 3);
				if (logSpawns) FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Infernal", coordX, coordZ, coordY);

				netherQuartzGen.generateVein(world, random, coordX + 1, coordY, coordZ, 4);
				netherQuartzGen.generateVein(world, random, coordX - 1, coordY, coordZ, 4);
				netherQuartzGen.generateVein(world, random, coordX, coordY + 1, coordZ, 4);
				netherQuartzGen.generateVein(world, random, coordX, coordY - 1, coordZ, 4);
				netherQuartzGen.generateVein(world, random, coordX, coordY, coordZ + 1, 4);
				netherQuartzGen.generateVein(world, random, coordX, coordY, coordZ - 1, 4);
				
				return true;
			}
		}
		else
		{
			int coordY = random.nextInt(13) + 5;
			
			if( world.getBlockId(coordX, coordY, coordZ) == Block.stone.blockID &&
				(getSurroundCount(world, coordX, coordY, coordZ, Block.stone) >= 6) )
			{
				world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.INFERNAL.ordinal(), 3);
				if (logSpawns) FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Infernal", coordX, coordZ, coordY);

				glowstoneGen.generateVein(world, random, coordX + 1, coordY, coordZ, 2);
				glowstoneGen.generateVein(world, random, coordX - 1, coordY, coordZ, 2);
				glowstoneGen.generateVein(world, random, coordX, coordY + 1, coordZ, 1);
				glowstoneGen.generateVein(world, random, coordX, coordY - 1, coordZ, 1);
				glowstoneGen.generateVein(world, random, coordX, coordY, coordZ + 1, 2);
				glowstoneGen.generateVein(world, random, coordX, coordY, coordZ - 1, 2);
				
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean generateHiveOblivion(World world, Random random, int coordX, int coordZ, boolean initialGen)
	{
		if (!world.provider.isSurfaceWorld())
		{
			int heightMapY = getHeightMapY(world, coordX, coordZ);
			
			if(heightMapY == 0)
			{
				return false;
			}
			
			int coordY = 0;
			
			while((world.getBlockId(coordX, coordY + 1, coordZ) != Block.whiteStone.blockID) && (coordY < heightMapY))
			{
				coordY++;
			}
			
			coordY += 2;
			
			if( world.isAirBlock(coordX, coordY - 2, coordZ) &&
				world.getBlockId(coordX, coordY - 1, coordZ) == Block.whiteStone.blockID &&
				world.getBlockId(coordX, coordY, coordZ) == Block.whiteStone.blockID )
			{
				world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.OBLIVION.ordinal(), 3);
				if (logSpawns) FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Oblivion", coordX, coordZ, coordY);
				
				int obsidianSpikeHeight = random.nextInt(8) + 2;
				
				for (int i = 1; i <= obsidianSpikeHeight && coordY - i > 0; ++i)
				{
					world.setBlock(coordX, coordY - i, coordZ, Block.obsidian.blockID, 0, 2);
				}
				
				return true;
			}
		}
		else
		{
			int coordY = random.nextInt(5) + 5;
			
			if( world.getBlockId(coordX, coordY, coordZ) == Block.stone.blockID &&
				(getSurroundCount(world, coordX, coordY, coordZ, Block.stone) >= 6) )
			{
				world.setBlock(coordX, coordY, coordZ, Config.hive.blockID, HiveType.OBLIVION.ordinal(), 3);
				if (logSpawns) FMLLog.info("Spawning %s hive at: X %d,  Z %d, Y %d", "Oblivion", coordX, coordZ, coordY);

				endStoneGen.generateVein(world, random, coordX + 1, coordY, coordZ, 3);
				endStoneGen.generateVein(world, random, coordX - 1, coordY, coordZ, 3);
				endStoneGen.generateVein(world, random, coordX, coordY + 1, coordZ, 2);
				endStoneGen.generateVein(world, random, coordX, coordY - 1, coordZ, 2);
				endStoneGen.generateVein(world, random, coordX, coordY, coordZ + 1, 3);
				endStoneGen.generateVein(world, random, coordX, coordY, coordZ - 1, 3);
				
				return true;
			}
		}
		
		return false;
	}
	
	// Get surface y from chunk height map (derived from canBlockSeeTheSky)
	public static int getHeightMapY(World world, int x, int z)
	{
		return world.getChunkFromChunkCoords(x >> 4, z >> 4).heightMap[(z & 15) << 4 | (x & 15)];
	}
	
	// Seek down through transparent blocks
	public static int seekGround(World world, int x, int y, int z)
	{
		while((isSoft(world, x, y - 1, z) || isLeaves(world, x, y - 1, z)) && (y > 0)) //(!Block.opaqueCubeLookup[world.getBlockId(x, y - 1, z)] || GlobalManager.leafBlockIds.contains(world.getBlockId(x, y - 1, z)))
		{
			y--;
		}
		return y;
	}
	
	public static int findSurface(World world, int x, int z)
	{
		return seekGround(world, x, getHeightMapY(world, x, z), z);
	}
	
	public static boolean isLeaves(World world, int x, int y, int z)
	{
		Block block = Block.blocksList[world.getBlockId(x, y, z)];
		if(block == null)
		{
			return false;
		}
		
		// Apparently Thaumcraft and MF:R leaves don't play nice
		return ( GlobalManager.leafBlockIds.contains(block.blockID) ||
			BlockLeavesBase.class.isAssignableFrom(block.getClass()) ||
			block.blockMaterial == Material.leaves );
	}
	
	public static boolean isSoft(World world, int x, int y, int z)
	{
		return (!world.getBlockMaterial(x, y, z).isSolid() && !world.getBlockMaterial(x, y, z).isLiquid());
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
