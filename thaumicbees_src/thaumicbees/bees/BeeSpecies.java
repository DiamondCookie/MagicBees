package thaumicbees.bees;

import forestry.api.apiculture.*;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.*;
import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.world.World;

public class BeeSpecies implements IAlleleBeeSpecies
{

	public static BeeSpecies Esoteric;
	public static BeeSpecies Mysterious;
	public static BeeSpecies Arcane;
	public static BeeSpecies Charmed;
	public static BeeSpecies Enchanted;
	public static BeeSpecies Supernatural;
	public static BeeSpecies Pupil;
	public static BeeSpecies Scholarly;
	public static BeeSpecies Savant;
	public static BeeSpecies Stark;
	public static BeeSpecies Air;
	public static BeeSpecies Water;
	public static BeeSpecies Earth;
	public static BeeSpecies Fire;
	private String universalID;
	private String name;
	private String descripton;
	private String binomial;
	private String authority;
	private int bodyType;
	private int primaryColour;
	private int secondaryColour;
	private EnumTemperature temperature;
	private EnumHumidity humidity;
	private boolean hasEffect;
	private boolean isSecret;
	private boolean isCounted;
	private boolean isDominant;
	private IBranch branch;
	private HashMap products;
	private HashMap specialty;
	private IAllele genomeTemplate[];

	public static IAlleleBeeSpecies getBaseSpecies(String name)
	{
		return (IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele((new StringBuilder()).append("forestry.species").append(name).toString());
	}

	public BeeSpecies(String speciesName, String speciesDescription, String genusName, IBranch parentBranch, int firstColour, int secondColour, EnumTemperature preferredTemp, EnumHumidity preferredHumidity, boolean hasGlowEffect, boolean isSpeciesDominant)
	{
		this(speciesName, speciesDescription, genusName, parentBranch, 0, firstColour, secondColour, preferredTemp, preferredHumidity, hasGlowEffect, true, true, isSpeciesDominant);
	}

	public BeeSpecies(String speciesName, String speciesDescription, String genusName, IBranch parentBranch, int body, int firstColour, int secondColour, EnumTemperature preferredTemp, EnumHumidity preferredHumidity, boolean hasGlowEffect, boolean isSpeciesSecret, boolean isSpeciesCounted, boolean isSpeciesDominant)
	{
		universalID = (new StringBuilder()).append("thaumicbees.species").append(speciesName).toString();
		name = speciesName;
		descripton = speciesDescription;
		binomial = genusName;
		authority = "MysteriousAges";
		bodyType = body;
		primaryColour = firstColour;
		secondaryColour = secondColour;
		temperature = preferredTemp;
		humidity = preferredHumidity;
		hasEffect = hasGlowEffect;
		isSecret = isSpeciesSecret;
		isCounted = isSpeciesCounted;
		isDominant = isSpeciesDominant;
		products = new HashMap();
		specialty = new HashMap();
		genomeTemplate = BeeManager.breedingManager.getDefaultBeeTemplate();
		genomeTemplate[EnumBeeChromosome.SPECIES.ordinal()] = this;
		AlleleManager.alleleRegistry.registerAllele(this);
	}

	public BeeSpecies setGene(EnumBeeChromosome chromosome, IAllele allele)
	{
		genomeTemplate[chromosome.ordinal()] = allele;
		return this;
	}

	public BeeSpecies setGenome(IAllele genome[])
	{
		genomeTemplate = genome;
		return this;
	}

	public IAllele[] getGenome()
	{
		return genomeTemplate;
	}

	public BeeSpecies addProduct(ItemStack produce, int percentChance)
	{
		products.put(produce, Integer.valueOf(percentChance));
		return this;
	}

	public BeeSpecies addSpecialty(ItemStack produce, int percentChance)
	{
		specialty.put(produce, Integer.valueOf(percentChance));
		return this;
	}

	public ItemStack getBeeItem(World world, EnumBeeType beeType)
	{
		return BeeManager.beeInterface.getBeeStack(BeeManager.beeInterface.getBee(world, BeeManager.beeInterface.templateAsGenome(genomeTemplate)), beeType);
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return descripton;
	}

	public int getBodyType()
	{
		return bodyType;
	}

	public int getPrimaryColor()
	{
		return primaryColour;
	}

	public int getSecondaryColor()
	{
		return secondaryColour;
	}

	public EnumTemperature getTemperature()
	{
		return temperature;
	}

	public EnumHumidity getHumidity()
	{
		return humidity;
	}

	public boolean hasEffect()
	{
		return hasEffect;
	}

	public boolean isSecret()
	{
		return isSecret;
	}

	public boolean isCounted()
	{
		return isCounted;
	}

	public String getBinomial()
	{
		return binomial;
	}

	public String getAuthority()
	{
		return authority;
	}

	public IBranch getBranch()
	{
		return branch;
	}

	public String getUID()
	{
		return universalID;
	}

	public boolean isDominant()
	{
		return isDominant;
	}

	public HashMap getProducts()
	{
		return products;
	}

	public HashMap getSpecialty()
	{
		return specialty;
	}

	public boolean isJubilant(World world, int biomeid, int x, int i, int j)
	{
		return true;
	}

	/**
	 * @deprecated Method getAchievement is deprecated
	 */

	public Achievement getAchievement()
	{
		return null;
	}
}
