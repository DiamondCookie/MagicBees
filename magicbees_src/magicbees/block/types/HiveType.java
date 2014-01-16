package magicbees.block.types;

import java.util.ArrayList;
import java.util.Random;

import magicbees.bees.BeeGenomeManager;
import magicbees.bees.BeeSpecies;
import magicbees.bees.HiveDrop;
import magicbees.item.types.CombType;
import magicbees.main.CommonProxy;
import magicbees.main.Config;
import magicbees.main.utils.compat.ForestryHelper;
import magicbees.world.feature.FeatureHive;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.IHiveDrop;

public enum HiveType
{
	CURIOUS("curious", 12, true),
	UNUSUAL("unusual", 12, true),
	RESONANT("resonant", 12, true),
	DEEP("deep", 4, false),
	INFERNAL("infernal", 15, false),
	OBLIVION("oblivion", 7, false),
	;
	
	private static String[] nameList;
	
	private String name;
	public boolean show;
	private int lightLevel;
	private ArrayList<IHiveDrop> drops;
	private ArrayList<BiomeDictionary.Type> validBiomes;
	private float[] spawnChance;
	@SideOnly(Side.CLIENT)
	private Icon[] icons;
	
	public static HiveType getHiveFromMeta(int meta)
	{
		HiveType type = CURIOUS;
		
		if (meta > 0 && meta < HiveType.values().length)
		{
			type = HiveType.values()[meta];
		}
		
		return type;
	}
	
	public static void initHiveData()
	{
		ItemStack[] combs = new ItemStack[] {Config.combs.getStackForType(CombType.MUNDANE)};
		HiveDrop valiantDrop = new HiveDrop(BeeGenomeManager.addRainResist(ForestryHelper.getTemplateForestryForSpecies("Valiant")), combs, 5);

		CURIOUS.addSpawnBiome(Type.FOREST, 0.015F);
		CURIOUS.addSpawnBiome(Type.JUNGLE, 0.01F);
		CURIOUS.addSpawnBiome(Type.HILLS, 0.07F);
		CURIOUS.drops.add(new HiveDrop(BeeSpecies.MYSTICAL.getGenome(), combs, 80).setIgnoblePercentage(0.7f));
		CURIOUS.drops.add(new HiveDrop(BeeGenomeManager.addRainResist(BeeSpecies.MYSTICAL.getGenome()), combs, 15));
		CURIOUS.drops.add(valiantDrop);
		
		UNUSUAL.addSpawnBiome(Type.PLAINS, 0.01F);
		UNUSUAL.addSpawnBiome(Type.MOUNTAIN, 0.02F);
		UNUSUAL.addSpawnBiome(Type.HILLS, 0.02F);
		UNUSUAL.drops.add(new HiveDrop(BeeSpecies.UNUSUAL.getGenome(), combs, 80).setIgnoblePercentage(0.7f));
		UNUSUAL.drops.add(new HiveDrop(BeeGenomeManager.addRainResist(BeeSpecies.UNUSUAL.getGenome()), combs, 15));
		UNUSUAL.drops.add(valiantDrop);

		RESONANT.addSpawnBiome(Type.DESERT, 0.01F);
		RESONANT.addSpawnBiome(Type.MAGICAL, 0.02F);
		RESONANT.drops.add(new HiveDrop(BeeSpecies.SORCEROUS.getGenome(), combs, 80).setIgnoblePercentage(0.7f));
		RESONANT.drops.add(new HiveDrop(BeeGenomeManager.addRainResist(BeeSpecies.SORCEROUS.getGenome()), combs, 20));
		RESONANT.drops.add(valiantDrop);
		
		DEEP.addSpawnBiome(Type.HILLS, 0.01F);
		DEEP.addSpawnBiome(Type.MOUNTAIN, 0.01F);
		DEEP.addSpawnBiome(Type.MAGICAL, 0.02F);
		DEEP.drops.add(new HiveDrop(BeeSpecies.ATTUNED.getGenome(), combs, 80).setIgnoblePercentage(0.65f));
		DEEP.drops.add(new HiveDrop(BeeGenomeManager.addRainResist(BeeSpecies.ATTUNED.getGenome()), combs, 20));
		DEEP.drops.add(valiantDrop);
		
		combs = new ItemStack[] {Config.combs.getStackForType(CombType.MOLTEN), new ItemStack(Item.glowstone, 6)};
		
		INFERNAL.addSpawnBiome(Type.NETHER, 0.04F);
		INFERNAL.addSpawnBiome(Type.MAGICAL, 0.007F);
		INFERNAL.drops.add(new HiveDrop(BeeSpecies.INFERNAL.getGenome(), combs, 80).setIgnoblePercentage(0.5f));
		INFERNAL.drops.add(new HiveDrop(ForestryHelper.getTemplateForestryForSpecies("Steadfast"), combs, 3));
		
		combs = new ItemStack[] {Config.combs.getStackForType(CombType.FORGOTTEN), new ItemStack(Item.enderPearl, 1)};
		
		OBLIVION.addSpawnBiome(Type.END, 0.05F);
		OBLIVION.addSpawnBiome(Type.MAGICAL, 0.005F);
		OBLIVION.drops.add(new HiveDrop(BeeSpecies.OBLIVION.getGenome(), combs, 80));
		OBLIVION.drops.add(new HiveDrop(ForestryHelper.getTemplateForestryForSpecies("Steadfast"), combs, 9));
	}
	
	private HiveType(String hiveName, int light, boolean visible)
	{
		this.name = hiveName;
		this.lightLevel = light;
		this.show = visible;
		this.drops = new ArrayList<IHiveDrop>();
		this.validBiomes = new ArrayList<BiomeDictionary.Type>();
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerIcons(IconRegister register)
	{
		for (HiveType type : HiveType.values())
		{
			type.icons = new Icon[2];

			type.icons[0] = register.registerIcon(CommonProxy.DOMAIN + ":beehive." + type.ordinal() + ".top");
			type.icons[1] = register.registerIcon(CommonProxy.DOMAIN + ":beehive." + type.ordinal() + ".side");
		}
	}
	
	private HiveType()
	{
		this.drops = new ArrayList<IHiveDrop>();
	}
	
	public void addDrop(IHiveDrop drop)
	{
		this.drops.add(drop);
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIconForSide(int side)
	{
		Icon i = this.icons[0];
		
		if (side != 0 && side != 1)
		{
			i = this.icons[1];
		}
		
		return i;
	}
	
	public int getLightValue()
	{
		return this.lightLevel;
	}

	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int fortune)
	{
		ArrayList<ItemStack> hiveDrops = new ArrayList<ItemStack>();
		int dart;
		
		// Get a princess.
		int throttle = 0;
		while (hiveDrops.size() <= 0 && throttle < 10)
		{
			++throttle;
			dart = world.rand.nextInt(100);
			for (IHiveDrop drop : drops)
			{
				if (dart <= drop.getChance(world, x, y, z))
				{
					hiveDrops.add(drop.getPrincess(world, x, y, z, fortune));
					break;
				}
			}
		}
		
		// Get a drone, maybe.
		dart = world.rand.nextInt(100);
		for (IHiveDrop drop : drops)
		{
			if (dart <= drop.getChance(world, x, y, z))
			{
				hiveDrops.addAll(drop.getDrones(world, x, y, z, fortune));
				break;
			}
		}
		
		// Get additional drops.
		dart = world.rand.nextInt(100);
		for (IHiveDrop drop : drops)
		{
			if (dart <= drop.getChance(world, x, y, z))
			{
				hiveDrops.addAll(drop.getAdditional(world, x, y, z, fortune));
			}
		}
		
		return hiveDrops;
	}
	
	public static String[] getAllNames()
	{
		return (nameList == null) ? nameList = generateNames() : nameList;
	}
	
	private static String[] generateNames()
	{
		String[] names = new String[values().length];
		for (int i = 0; i < names.length; ++i)
		{
			names[i] = values()[i].name;
		}
		return names;
	}
	
	private void addSpawnBiome(Type biome, float chance)
	{
		validBiomes.add(biome);
		spawnChance[biome.ordinal()] = chance;
	}
	
	public boolean generateHive(World world, Random random, int coordX, int coordZ, boolean initialGen)
	{
		switch (this)
		{
			case CURIOUS:
				return FeatureHive.generateHiveCurious(world, random, coordX, coordZ, initialGen);
				
			case UNUSUAL:
				return FeatureHive.generateHiveUnusual(world, random, coordX, coordZ, initialGen);
				
			case RESONANT:
				return FeatureHive.generateHiveResonant(world, random, coordX, coordZ, initialGen);
				
			case DEEP:
				return FeatureHive.generateHiveDeep(world, random, coordX, coordZ, initialGen);
				
			case INFERNAL:
				return FeatureHive.generateHiveInfernal(world, random, coordX, coordZ, initialGen);
				
			case OBLIVION:
				return FeatureHive.generateHiveOblivion(world, random, coordX, coordZ, initialGen);
		}
		return true;
	}
	
	public float getBiomeSpawnChance(BiomeGenBase biomeGen)
	{
		float chanceSum = 0;
		int validTypes = 0;
		
		BiomeDictionary.Type[] types = BiomeDictionary.getTypesForBiome(biomeGen);
		for (int i = 0; i < types.length; ++i)
		{
			if (validBiomes.contains(types[i]))
			{
				chanceSum += spawnChance[types[i].ordinal()];
				validTypes++;
			}
		}
		
		if(validTypes > 0)
		{
			return chanceSum / (float)validTypes;
		}
		else
		{
			return 0;
		}
	}
}
