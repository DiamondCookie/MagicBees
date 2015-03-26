package magicbees.bees;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import net.minecraftforge.common.BiomeDictionary;

import forestry.api.apiculture.hives.HiveManager;
import forestry.api.apiculture.hives.IHiveDescription;
import forestry.api.apiculture.hives.IHiveGen;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;

import magicbees.block.types.HiveType;
import magicbees.main.Config;
import magicbees.main.utils.BlockUtil;
import magicbees.main.utils.LogHelper;
import magicbees.world.feature.FeatureOreVein;
import magicbees.world.feature.HiveGenNether;
import magicbees.world.feature.HiveGenOblivion;
import magicbees.world.feature.HiveGenUnderground;

public enum HiveDescription implements IHiveDescription {
	CURIOUS(HiveType.CURIOUS, 3.0f, HiveManager.genHelper.tree()),
	UNUSUAL(HiveType.UNUSUAL, 1.0f, HiveManager.genHelper.ground(Blocks.dirt, Blocks.grass)),
	RESONANT(HiveType.RESONANT, 0.5f, HiveManager.genHelper.ground(Blocks.sand, Blocks.sandstone)),
	DEEP(HiveType.DEEP, 2.0f, new HiveGenUnderground(10, 10, 6)) {
		@Override
		public void postGen(World world, int x, int y, int z)
		{
			super.postGen(world, x, y, z);
			Random random = world.rand;
			FeatureOreVein.redstoneGen.generateVein(world, random, x + 1, y, z, 2);
			FeatureOreVein.redstoneGen.generateVein(world, random, x - 1, y, z, 2);
			FeatureOreVein.redstoneGen.generateVein(world, random, x, y + 1, z, 2);
			FeatureOreVein.redstoneGen.generateVein(world, random, x, y - 1, z, 2);
			FeatureOreVein.redstoneGen.generateVein(world, random, x, y, z + 1, 2);
			FeatureOreVein.redstoneGen.generateVein(world, random, x, y, z - 1, 2);
		}
	},
	INFERNAL(HiveType.INFERNAL, 50.0f, new HiveGenNether(0, 175, 6)) {
		@Override
		public void postGen(World world, int x, int y, int z)
		{
			super.postGen(world, x, y, z);
			Random random = world.rand;
			FeatureOreVein.netherQuartzGen.generateVein(world, random, x + 1, y, z, 4);
			FeatureOreVein.netherQuartzGen.generateVein(world, random, x - 1, y, z, 4);
			FeatureOreVein.netherQuartzGen.generateVein(world, random, x, y + 1, z, 4);
			FeatureOreVein.netherQuartzGen.generateVein(world, random, x, y - 1, z, 4);
			FeatureOreVein.netherQuartzGen.generateVein(world, random, x, y, z + 1, 4);
			FeatureOreVein.netherQuartzGen.generateVein(world, random, x, y, z - 1, 4);
		}
	},
	INFERNAL_OVERWORLD(HiveType.INFERNAL, 1.0f, new HiveGenUnderground(5, 13, 6)) {
		@Override
		public void postGen(World world, int x, int y, int z)
		{
			super.postGen(world, x, y, z);
			Random random = world.rand;
			FeatureOreVein.glowstoneGen.generateVein(world, random, x + 1, y, z, world.rand.nextInt(4) + 1);
			FeatureOreVein.glowstoneGen.generateVein(world, random, x - 1, y, z, world.rand.nextInt(4) + 1);

			if (BlockUtil.canBlockReplaceAt(world, x, y + 1, z, Blocks.stone))
			{
				world.setBlock(x, y + 1, z, Blocks.glowstone, 0, 3);
			}

			if (BlockUtil.canBlockReplaceAt(world, x, y - 1, z, Blocks.stone))
			{
				world.setBlock(x, y - 1, z, Blocks.glowstone, 0, 3);
			}

			FeatureOreVein.glowstoneGen.generateVein(world, random, x, y, z + 1, world.rand.nextInt(4) + 1);
			FeatureOreVein.glowstoneGen.generateVein(world, random, x, y, z - 1, world.rand.nextInt(4) + 1);
		}
	},
	OBLIVION(HiveType.OBLIVION, 20.0f, new HiveGenOblivion()) {
		@Override
		public void postGen(World world, int x, int y, int z)
		{
			super.postGen(world, x, y, z);
			int obsidianSpikeHeight = world.rand.nextInt(8) + 3;

			for (int i = 1; i < obsidianSpikeHeight && y - i > 0; ++i)
			{
				world.setBlock(x, y - i, z, Blocks.obsidian, 0, 2);
			}
		}
	},
	OBLIVION_OVERWORLD(HiveType.OBLIVION, 1.0f, new HiveGenUnderground(5, 5, 5)) {
		@Override
		public void postGen(World world, int x, int y, int z)
		{
			super.postGen(world, x, y, z);
			Random random = world.rand;

			FeatureOreVein.endStoneGen.generateVein(world, random, x + 1, y, z, world.rand.nextInt(6) + 1);
			FeatureOreVein.endStoneGen.generateVein(world, random, x - 1, y, z, world.rand.nextInt(6) + 1);
			FeatureOreVein.endStoneGen.generateVein(world, random, x, y + 1, z, world.rand.nextInt(3) + 1);
			FeatureOreVein.endStoneGen.generateVein(world, random, x, y - 1, z, world.rand.nextInt(3) + 1);
			FeatureOreVein.endStoneGen.generateVein(world, random, x, y, z + 1, world.rand.nextInt(6) + 1);
			FeatureOreVein.endStoneGen.generateVein(world, random, x, y, z - 1, world.rand.nextInt(6) + 1);
		}
	};

	private static boolean logSpawns = Config.logHiveSpawns;

	private final HiveType hiveType;
	private final float genChance;
	private final List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
	private final IHiveGen hiveGen;

	private HiveDescription(HiveType hiveType, float genChance, IHiveGen hiveGen)
	{
		this.hiveType = hiveType;
		this.genChance = genChance;
		this.hiveGen = hiveGen;
	}

	public static void initHiveData()
	{
		CURIOUS.biomes.add(BiomeDictionary.Type.FOREST);
		CURIOUS.biomes.add(BiomeDictionary.Type.JUNGLE);
		CURIOUS.biomes.add(BiomeDictionary.Type.HILLS);

		UNUSUAL.biomes.add(BiomeDictionary.Type.PLAINS);
		UNUSUAL.biomes.add(BiomeDictionary.Type.MOUNTAIN);
		UNUSUAL.biomes.add(BiomeDictionary.Type.HILLS);

		RESONANT.biomes.add(BiomeDictionary.Type.SANDY);
		RESONANT.biomes.add(BiomeDictionary.Type.MAGICAL);

		DEEP.biomes.add(BiomeDictionary.Type.HILLS);
		DEEP.biomes.add(BiomeDictionary.Type.MOUNTAIN);
		DEEP.biomes.add(BiomeDictionary.Type.MAGICAL);

		INFERNAL.biomes.add(BiomeDictionary.Type.NETHER);
		INFERNAL_OVERWORLD.biomes.add(BiomeDictionary.Type.MAGICAL);

		OBLIVION.biomes.add(BiomeDictionary.Type.END);
		OBLIVION_OVERWORLD.biomes.add(BiomeDictionary.Type.MAGICAL);
	}

	@Override
	public IHiveGen getHiveGen()
	{
		return hiveGen;
	}

	@Override
	public Block getBlock()
	{
		return Config.hive;
	}

	@Override
	public int getMeta()
	{
		return hiveType.ordinal();
	}

	@Override
	public boolean isGoodBiome(BiomeGenBase biome)
	{
		BiomeDictionary.Type[] types = BiomeDictionary.getTypesForBiome(biome);
		for (BiomeDictionary.Type type : types)
		{
			if (biomes.contains(type))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isGoodHumidity(EnumHumidity humidity)
	{
		return hiveType.getOccupant().canWorkInHumidity(humidity);
	}

	@Override
	public boolean isGoodTemperature(EnumTemperature temperature)
	{
		return hiveType.getOccupant().canWorkInTemperature(temperature);
	}

	@Override
	public float getGenChance()
	{
		return genChance;
	}

	@Override
	public void postGen(World world, int x, int y, int z)
	{
		if (logSpawns)
		{
			LogHelper.info("Spawned " + this.toString().toLowerCase(Locale.ENGLISH) + " hive at: X " + x + ", Y: " + y + ", Z: " + z);
		}
	}
}
