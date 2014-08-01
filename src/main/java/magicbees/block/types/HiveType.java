package magicbees.block.types;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.IHiveDrop;
import magicbees.bees.BeeGenomeManager;
import magicbees.bees.BeeSpecies;
import magicbees.bees.HiveDrop;
import magicbees.item.types.CombType;
import magicbees.main.CommonProxy;
import magicbees.main.Config;
import magicbees.main.utils.compat.ForestryHelper;
import magicbees.world.feature.FeatureHive;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.ArrayList;
import java.util.Random;

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
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	
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

		CURIOUS.validBiomes.add(Type.FOREST);
		CURIOUS.validBiomes.add(Type.JUNGLE);
		CURIOUS.validBiomes.add(Type.HILLS);
		CURIOUS.drops.add(new HiveDrop(BeeSpecies.MYSTICAL.getGenome(), combs, 80).setIgnoblePercentage(0.7f));
		CURIOUS.drops.add(new HiveDrop(BeeGenomeManager.addRainResist(BeeSpecies.MYSTICAL.getGenome()), combs, 15));
		CURIOUS.drops.add(valiantDrop);
		
		UNUSUAL.validBiomes.add(Type.PLAINS);
		UNUSUAL.validBiomes.add(Type.MOUNTAIN);
		UNUSUAL.validBiomes.add(Type.HILLS);
		UNUSUAL.drops.add(new HiveDrop(BeeSpecies.UNUSUAL.getGenome(), combs, 80).setIgnoblePercentage(0.7f));
		UNUSUAL.drops.add(new HiveDrop(BeeGenomeManager.addRainResist(BeeSpecies.UNUSUAL.getGenome()), combs, 15));
		UNUSUAL.drops.add(valiantDrop);

		RESONANT.validBiomes.add(Type.SANDY);
		RESONANT.validBiomes.add(Type.MAGICAL);
		RESONANT.drops.add(new HiveDrop(BeeSpecies.SORCEROUS.getGenome(), combs, 80).setIgnoblePercentage(0.7f));
		RESONANT.drops.add(new HiveDrop(BeeGenomeManager.addRainResist(BeeSpecies.SORCEROUS.getGenome()), combs, 20));
		RESONANT.drops.add(valiantDrop);
		
		DEEP.validBiomes.add(Type.HILLS);
		DEEP.validBiomes.add(Type.MOUNTAIN);
		DEEP.validBiomes.add(Type.MAGICAL);
		DEEP.drops.add(new HiveDrop(BeeSpecies.ATTUNED.getGenome(), combs, 80).setIgnoblePercentage(0.65f));
		DEEP.drops.add(new HiveDrop(BeeGenomeManager.addRainResist(BeeSpecies.ATTUNED.getGenome()), combs, 20));
		DEEP.drops.add(valiantDrop);
		
		combs = new ItemStack[] {Config.combs.getStackForType(CombType.MOLTEN), new ItemStack(Items.glowstone_dust, 6)};
		
		INFERNAL.validBiomes.add(Type.NETHER);
		INFERNAL.validBiomes.add(Type.MAGICAL);
		INFERNAL.drops.add(new HiveDrop(BeeSpecies.INFERNAL.getGenome(), combs, 80).setIgnoblePercentage(0.5f));
		INFERNAL.drops.add(new HiveDrop(ForestryHelper.getTemplateForestryForSpecies("Steadfast"), combs, 3));
		
		combs = new ItemStack[] {Config.combs.getStackForType(CombType.FORGOTTEN), new ItemStack(Items.ender_pearl, 1)};
		
		OBLIVION.validBiomes.add(Type.END);
		OBLIVION.validBiomes.add(Type.MAGICAL);
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
	public static void registerIcons(IIconRegister register)
	{
		for (HiveType type : HiveType.values())
		{
			type.icons = new IIcon[2];

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
	public IIcon getIconForSide(int side)
	{
		IIcon i = this.icons[0];
		
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
	
	public void generateHive(World world, Random random, int chunkX, int chunkZ, boolean initialGen)
	{
		if (spawnsInBiome(world.getBiomeGenForCoordsBody(chunkX * 16, chunkZ * 16)))
		{
			switch (this)
			{
			case CURIOUS:
				for (int i = 0; i < 3; ++i)
				{
					int coordX = chunkX * 16 + random.nextInt(16);
					int coordZ = chunkZ * 16 + random.nextInt(16);
					if (FeatureHive.generateHiveCurious(world, random, coordX, coordZ, initialGen))
					{
						break;
					}
				}
				break;
			case UNUSUAL:
				for (int i = 0; i < 3; ++i)
				{
					int coordX = chunkX * 16 + random.nextInt(16);
					int coordZ = chunkZ * 16 + random.nextInt(16);
					if (FeatureHive.generateHiveUnusual(world, random, coordX, coordZ, initialGen))
					{
						break;
					}
				}
				break;
			case RESONANT:
				for (int i = 0; i < 3; ++i)
				{
					int coordX = chunkX * 16 + random.nextInt(16);
					int coordZ = chunkZ * 16 + random.nextInt(16);
					if (FeatureHive.generateHiveResonant(world, random, coordX, coordZ, initialGen))
					{
						break;
					}
				}
				break;
			case DEEP:
				if (chunkX % 2 == 0 && chunkZ % 2 == 0)
				{
					for (int i = 0; i < 1; ++i)
					{
						int coordX = chunkX * 16 + random.nextInt(16);
						int coordZ = chunkZ * 16 + random.nextInt(16);
						if (FeatureHive.generateHiveDeep(world, random, coordX, coordZ, initialGen))
						{
							break;
						}
					}
				}
				break;
			case INFERNAL:
				for (int i = 0; i < 2; ++i)
				{
					int coordX = chunkX * 16 + random.nextInt(16);
					int coordZ = chunkZ * 16 + random.nextInt(16);
					if (FeatureHive.generateHiveInfernal(world, random, coordX, coordZ, initialGen))
					{
						break;
					}
				}
				break;
			case OBLIVION:
				for (int i = 0; i < 3; ++i)
				{
					int coordX = chunkX * 16 + random.nextInt(16);
					int coordZ = chunkZ * 16 + random.nextInt(16);
					if (FeatureHive.generateHiveOblivion(world, random, coordX, coordZ, initialGen))
					{
						break;
					}
				}
				break;
			}
		}
	}
	
	public boolean spawnsInBiome(BiomeGenBase biomeGen)
	{
		boolean found = false;
		BiomeDictionary.Type[] types = BiomeDictionary.getTypesForBiome(biomeGen);
		for (int i = 0; i < types.length; ++i)
		{
			if (this.validBiomes.contains(types[i]))
			{
				found = true;
				break;
			}
		}
		
		return found;
	}
}
