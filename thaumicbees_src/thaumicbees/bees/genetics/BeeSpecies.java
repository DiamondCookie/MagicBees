package thaumicbees.bees.genetics;

import forestry.api.apiculture.*;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.*;
import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.world.World;

public class BeeSpecies extends Allele implements IAlleleBeeSpecies
{
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
	private IClassification branch;
	private HashMap products;
	private HashMap specialty;
	private IAllele genomeTemplate[];

	public BeeSpecies(String speciesName, String speciesDescription, String genusName, IClassification classification, int firstColour, int secondColour, EnumTemperature preferredTemp, EnumHumidity preferredHumidity, boolean hasGlowEffect, boolean isSpeciesDominant)
	{
		this(speciesName, speciesDescription, genusName, classification, 0, firstColour, secondColour, preferredTemp, preferredHumidity, hasGlowEffect, true, true, isSpeciesDominant);
	}

	public BeeSpecies(String speciesName, String speciesDescription, String genusName, IClassification classification, int body, int firstColour, int secondColour, EnumTemperature preferredTemp, EnumHumidity preferredHumidity, boolean hasGlowEffect, boolean isSpeciesSecret, boolean isSpeciesCounted, boolean isSpeciesDominant)
	{
		super("species" + speciesName, isSpeciesDominant);
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
		products = new HashMap();
		specialty = new HashMap();
		this.branch = classification;
		this.genomeTemplate = new IAllele[EnumBeeChromosome.values().length];
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

	public IClassification getBranch()
	{
		return this.branch;
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
