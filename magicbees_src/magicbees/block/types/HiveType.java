package magicbees.block.types;

import java.util.ArrayList;
import java.util.Random;

import magicbees.bees.BeeGenomeManager;
import magicbees.bees.BeeSpecies;
import magicbees.bees.HiveDrop;
import magicbees.item.types.CombType;
import magicbees.main.Config;
import magicbees.main.utils.VersionInfo;
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
	CURIOUS("curious", 12, true,
			new BiomeDictionary.Type[] {Type.FOREST, Type.HILLS, Type.MOUNTAIN},
			new int[] {80, 30, 15}),
	UNUSUAL("unusual", 12, true,
			new BiomeDictionary.Type[] {Type.PLAINS, Type.JUNGLE, Type.HILLS},
			new int[] {90, 60, 20}),
	RESONANT("resonant", 12, true,
			new BiomeDictionary.Type[] {Type.MAGICAL, Type.HILLS},
			new int[] {95, 50}),
	DEEP("deep", 4, true,
			new BiomeDictionary.Type[] {Type.HILLS, Type.MOUNTAIN, Type.MUSHROOM},
			new int[] {40, 60, 50}),
	INFERNAL("infernal", 15, true,
			new BiomeDictionary.Type[] {Type.NETHER, Type.MAGICAL},
			new int[] {98, 10}),
	OBLIVION("oblivion", 7, true,
			new BiomeDictionary.Type[] {Type.END, Type.MAGICAL},
			new int[] {98, 6}),
	;
	
	private static String[] nameList;
	
	private String name;
	public boolean show;
	private int lightLevel;
	private ArrayList<IHiveDrop> drops;
	private BiomeDictionary.Type[] validBiomes;
	private int[] rarity;
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
	
	public static void initHiveDrops()
	{
		ItemStack[] combs = new ItemStack[] {Config.combs.getStackForType(CombType.MUNDANE)};
		HiveDrop valiantDrop = new HiveDrop(BeeGenomeManager.addRainResist(BeeGenomeManager.getTemplateForestryValiant()), combs, 5);
		
		CURIOUS.drops.add(new HiveDrop(BeeSpecies.MYSTICAL.getGenome(), combs, 80));
		CURIOUS.drops.add(new HiveDrop(BeeGenomeManager.addRainResist(BeeSpecies.MYSTICAL.getGenome()), combs, 15));
		CURIOUS.drops.add(valiantDrop);
		
		UNUSUAL.drops.add(new HiveDrop(BeeSpecies.UNUSUAL.getGenome(), combs, 80));
		UNUSUAL.drops.add(new HiveDrop(BeeGenomeManager.addRainResist(BeeSpecies.UNUSUAL.getGenome()), combs, 15));
		UNUSUAL.drops.add(valiantDrop);
		
		RESONANT.drops.add(new HiveDrop(BeeSpecies.ATTUNED.getGenome(), combs, 80));
		RESONANT.drops.add(new HiveDrop(BeeGenomeManager.addRainResist(BeeSpecies.ATTUNED.getGenome()), combs, 20));
		RESONANT.drops.add(valiantDrop);
		
		DEEP.drops.add(new HiveDrop(BeeSpecies.SORCEROUS.getGenome(), combs, 80));
		DEEP.drops.add(new HiveDrop(BeeGenomeManager.addRainResist(BeeSpecies.SORCEROUS.getGenome()), combs, 20));
		DEEP.drops.add(valiantDrop);
		
		combs = new ItemStack[] {new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.SIMMERING.ordinal()), new ItemStack(Item.lightStoneDust, 6)};
		
		INFERNAL.drops.add(new HiveDrop(BeeGenomeManager.getTemplateForestrySinister(), combs, 80));
		INFERNAL.drops.add(new HiveDrop(BeeSpecies.ELDRITCH.getGenome(), combs, 10));
		INFERNAL.drops.add(new HiveDrop(BeeGenomeManager.getTemplateForestrySteadfast(), combs, 5));
		
		combs = new ItemStack[] {new ItemStack(Config.fBeeComb, 3, ForestryHelper.Comb.MYSTERIOUS.ordinal()), new ItemStack(Item.enderPearl, 2)};
		
		OBLIVION.drops.add(new HiveDrop(BeeGenomeManager.getTemplateForestryEnder(), combs, 80));
		OBLIVION.drops.add(new HiveDrop(BeeGenomeManager.getTemplateForestrySteadfast(), combs, 5));
	}
	
	private HiveType(String hiveName, int light, boolean visible, BiomeDictionary.Type[] biomes, int[] rarity)
	{
		this.name = hiveName;
		this.lightLevel = light;
		this.show = visible;
		this.drops = new ArrayList<IHiveDrop>();
		this.validBiomes = biomes;
		this.rarity = rarity;
		
		assert this.validBiomes.length == this.rarity.length;
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerIcons(IconRegister register)
	{
		for (HiveType type : HiveType.values())
		{
			type.icons = new Icon[2];

			type.icons[0] = register.registerIcon(VersionInfo.ModName.toLowerCase() + ":beehive." + type.ordinal() + ".top");
			type.icons[1] = register.registerIcon(VersionInfo.ModName.toLowerCase() + ":beehive." + type.ordinal() + ".side");
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
				hiveDrops.addAll(drop.getAdditional(world, x, y, z, fortune));
				break;
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
		BiomeDictionary.Type[] biomeType = BiomeDictionary.getTypesForBiome(world.getBiomeGenForCoordsBody(chunkX, chunkZ));
		
		int maxRarity = 0;
		for (int i = 0; i < biomeType.length; ++i)
		{
			int rarity = this.getRarityForBiome(biomeType[i]);
			if (rarity > maxRarity)
			{
				maxRarity = rarity;
			}
		}
		
		if (world.rand.nextInt(100) < maxRarity)
		{
			switch (this)
			{
			default:
			case CURIOUS:
				FeatureHive.generateHiveCurious(world, random, chunkX, chunkZ, initialGen);
				break;
			case UNUSUAL:
				FeatureHive.generateHiveUnusual(world, random, chunkX, chunkZ, initialGen);
				break;
			case RESONANT:
				FeatureHive.generateHiveResonant(world, random, chunkX, chunkZ, initialGen);
				break;
			case DEEP:
				FeatureHive.generateHiveDeep(world, random, chunkX, chunkZ, initialGen);
				break;
			case INFERNAL:
				FeatureHive.generateHiveInfernal(world, random, chunkX, chunkZ, initialGen);
				break;
			case OBLIVION:
				FeatureHive.generateHiveOblivion(world, random, chunkX, chunkZ, initialGen);
				break;
			}
		}
	}
	
	public int getRarityForBiome(BiomeDictionary.Type biomeType)
	{
		int rarity = 0;
		for (int i = 0; i < this.validBiomes.length; ++i)
		{
			if (this.validBiomes[i].equals(biomeType))
			{
				rarity = this.rarity[i];
				break;
			}
		}
		return rarity;
	}
}
