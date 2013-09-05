package magicbees.bees;


import magicbees.main.utils.compat.ExtraBeesHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.core.ItemInterface;
import forestry.api.genetics.IAllele;

/**
 * Simply a class to hold all the functions to manage species' default genomes.
 * @author MysteriousAges
 *
 */
public class BeeGenomeManager
{
	// Basic genome for All thaumic bees.
	private static IAllele[] getTemplateModBase()
	{
		IAllele[] genome = new IAllele[EnumBeeChromosome.values().length];

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.MYSTICAL;
		genome[EnumBeeChromosome.SPEED.ordinal()] =  Allele.getBaseAllele("speedSlowest");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanShorter");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityNormal");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceNone");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolFalse");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceNone");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolFalse");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolFalse");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.getBaseAllele("flowersVanilla");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringSlowest");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryDefault");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectNone");

		return genome;
	}

	public static IAllele[] addRainResist(IAllele[] genome)
	{		
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		
		return genome;
	}
	
	public static IAllele[] getTemplateMystical()
	{
		IAllele[] genome = getTemplateModBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.MYSTICAL;
		
		return genome;
	}
	
	public static IAllele[] getTemplateSorcerous()
	{
		IAllele[] genome = getTemplateModBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.SORCEROUS;
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityHigh");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceDown2");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceUp1");
		
		return genome;
	}
	
	public static IAllele[] getTemplateUnusual()
	{
		IAllele[] genome = getTemplateModBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.UNUSUAL;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth2");
		
		return genome;
	}
	
	public static IAllele[] getTemplateAttuned()
	{
		IAllele[] genome = getTemplateModBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.ATTUNED;
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityHigh");
		
		return genome;
	}
	
	public static IAllele[] getTemplateEldritch()
	{
		IAllele[] genome = getTemplateModBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.ELDRITCH;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedSlower");
		
		return genome;
	}
	
	// Basic genome for Arcane branch bees.
	private static IAllele[] getTemplateBaseArcane()
	{
		IAllele[] genome = getTemplateModBase();
		
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringSlow");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityHigh");
		
		return genome;
	}

	// Specialization genome for Esoteric bees.
	public static IAllele[] getTemplateEsoteric()
	{
		IAllele[] genome = getTemplateBaseArcane();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.ESOTERIC;
		
		return genome;
	}

	public static IAllele[] getTemplateMysterious()
	{
		IAllele[] genome = getTemplateBaseArcane();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.MYSTERIOUS;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth2");
		
		return genome;
	}

	public static IAllele[] getTemplateArcane()
	{
		IAllele[] genome = getTemplateBaseArcane();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.ARCANE;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth2");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityNormal");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringAverage");
		
		return genome;
	}

	private static IAllele[] getTemplateBaseSupernatural()
	{
		IAllele[] genome = getTemplateModBase();
		
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedNorm");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringSlowest");
		
		return genome;
	}

	public static IAllele[] getTemplateCharmed()
	{
		IAllele[] genome = getTemplateBaseSupernatural();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.CHARMED;
		
		return genome;
	}

	public static IAllele[] getTemplateEnchanted()
	{
		IAllele[] genome = getTemplateBaseSupernatural();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.ENCHANTED;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		
		return genome;
	}

	public static IAllele[] getTemplateSupernatural()
	{
		IAllele[] genome = getTemplateBaseSupernatural();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.SUPERNATURAL;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth2");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		
		return genome;
	}
	
	public static IAllele[] getTemplateEthereal()
	{
		IAllele[] genome = getTemplateModBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.ETHEREAL;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedNorm");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanShortened");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringAverage");
		
		return genome;
	}
	
	private static IAllele[] getTemplateBaseElemental()
	{
		IAllele[] genome = getTemplateModBase();
		
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedNorm");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanNormal");
		
		return genome;
	}
	
	public static IAllele[] getTemplateWatery()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.WATERY;
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceUp1");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceDown2");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.getBaseAllele("flowersSnow");
		
		return genome;
	}
	
	public static IAllele[] getTemplateFirey()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.FIREY;
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceDown1");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceUp2");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.getBaseAllele("flowersCacti");
		
		return genome;
	}
	
	public static IAllele[] getTemplateEarthy()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.EARTHY;
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanLong");
		
		return genome;
	}
	
	public static IAllele[] getTemplateWindy()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.WINDY;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth2");
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedFaster");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringFaster");
		
		return genome;
	}
	
	private static IAllele[] getTemplateBaseScholarly()
	{
		IAllele[] genome = getTemplateModBase();

		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedSlow");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanElongated");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceUp2");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = AlleleFlower.flowerBookshelf;
		
		return genome;
	}

	public static IAllele[] getTemplatePupil()
	{
		IAllele[] genome = getTemplateBaseScholarly();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.PUPIL;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedSlower");
		
		return genome;
	}

	public static IAllele[] getTemplateScholarly()
	{
		IAllele[] genome = getTemplateBaseScholarly();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.SCHOLARLY;
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityLow");
		
		return genome;
	}

	public static IAllele[] getTemplateSavant()
	{
		IAllele[] genome = getTemplateBaseScholarly();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.SAVANT;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedNorm");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityLow");
		
		return genome;
	}
	
	private static IAllele[] getTemplateBaseSoul()
	{
		IAllele[] genome = getTemplateModBase();
		
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		
		return genome;
	}
	
	public static IAllele[] getTemplateAware()
	{
		IAllele[] genome = getTemplateBaseSoul();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.AWARE;
		
		return genome;
	}
	
	public static IAllele[] getTemplateSpirit()
	{
		IAllele[] genome = getTemplateBaseSoul();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.SPIRIT;
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanShortened");
		
		return genome;
	}
	
	public static IAllele[] getTemplateSoul()
	{
		IAllele[] genome = getTemplateBaseSoul();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.SOUL;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceDown2");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanNormal");
		
		return genome;
	}
	
	private static IAllele[] getTemplateBaseMalevolent()
	{
		IAllele[] genome = getTemplateModBase();
		
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedFast");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringFaster");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityNormal");
		
		return genome;
	}
	
	public static IAllele[] getTemplateSkulking()
	{
		IAllele[] genome = getTemplateBaseMalevolent();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.SKULKING;
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		
		return genome;
	}
	
	public static IAllele[] getTemplateGhastly()
	{
		IAllele[] genome = getTemplateBaseMalevolent();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.GHASTLY;
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityLow");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.spawnGhast;
		
		return genome;
	}
	
	public static IAllele[] getTemplateSpidery()
	{
		IAllele[] genome = getTemplateBaseMalevolent();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.SPIDERY;
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLarger");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.spawnSpider;
		
		return genome;
	}
	
	public static IAllele[] getTemplateSmouldering()
	{
		IAllele[] genome = getTemplateBaseMalevolent();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.SMOULDERING;
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.spawnBlaze;
		
		return genome;
	}
	
	private static IAllele[] getTemplateBaseTemporal()
	{
		IAllele[] genome = getTemplateModBase();
		
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityNormal");
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedNorm");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		
		return genome;
	}
	
	public static IAllele[] getTemplateTimely()
	{
		IAllele[] genome = getTemplateBaseTemporal();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TIMELY;
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.effectSlowSpeed;
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanElongated");
		
		return genome;
	}

	public static IAllele[] getTemplateLordly()
	{
		IAllele[] genome = getTemplateBaseTemporal();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.LORDLY;
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.effectSlowSpeed;
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanLong");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectDrunkard");
		
		return genome;
	}

	public static IAllele[] getTemplateDoctoral()
	{
		IAllele[] genome = getTemplateBaseTemporal();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.DOCTORAL;
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.effectSlowSpeed;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth3");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLarge");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanLongest");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectHeroic");
		
		return genome;
	}
	
	private static IAllele[] getTemplateAbominableBase()
	{
		IAllele[] genome = getTemplateModBase();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.INFERNAL;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceDown2");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.getBaseAllele("flowersNether");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectAggressive");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanShort");
		
		return genome;
	}
	
	public static IAllele[] getTemplateInfernal()
	{
		IAllele[] genome = getTemplateAbominableBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.INFERNAL;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedSlow");
		
		return genome;
	}
	
	public static IAllele[] getTemplateHateful()
	{
		IAllele[] genome = getTemplateAbominableBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.HATEFUL;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedSlow");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanElongated");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLarger");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectMisanthrope");
		
		return genome;
	}
	
	public static IAllele[] getTemplateSpiteful()
	{
		IAllele[] genome = getTemplateAbominableBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.SPITEFUL;
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanLong");
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedNorm");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLarger");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectMisanthrope");
		
		return genome;
	}
	
	public static IAllele[] getTemplateWithering()
	{
		IAllele[] genome = getTemplateAbominableBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.WITHERING;
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLargest");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityLow");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.effectWithering;
		
		return genome;
	}
	
	private static IAllele[] getTemplateBaseExtrinsic()
	{
		IAllele[] genome = getTemplateModBase();
		
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceUp2");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectAggressive");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.getBaseAllele("flowersEnd");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		
		return genome;
	}
	
	public static IAllele[] getTemplateOblivion()
	{
		IAllele[] genome = getTemplateBaseExtrinsic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.OBLIVION;
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityMaximum");
		
		return genome;
	}
	
	public static IAllele[] getTemplateNameless()
	{
		IAllele[] genome = getTemplateBaseExtrinsic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.NAMELESS;
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityHigh");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		
		return genome;
	}
	
	public static IAllele[] getTemplateAbandoned()
	{
		IAllele[] genome = getTemplateBaseExtrinsic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.ABANDONED;
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanElongated");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityNormal");
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedSlow");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectRepulsion");
		
		return genome;
	}
	
	public static IAllele[] getTemplateForlorn()
	{
		IAllele[] genome = getTemplateBaseExtrinsic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.FORLORN;
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityLow");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanLongest");
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedSlow");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectRepulsion");
		
		return genome;
	}
	
	public static IAllele[] getTemplateDraconic()
	{
		IAllele[] genome = getTemplateBaseExtrinsic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.DRACONIC;
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityLow");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanLongest");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectMisanthrope");
		
		return genome;
	}
	
	private static IAllele[] getTemplateBaseMetallic()
	{
		IAllele[] genome = getTemplateModBase();
		
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedSlowest");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityLow");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		
		return genome;
	}
	
	public static IAllele[] getTemplateIron()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.IRON;
		
		return genome;
	}
	
	public static IAllele[] getTemplateGold()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.GOLD;
		
		return genome;
	}
	
	public static IAllele[] getTemplateCopper()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.COPPER;
		
		return genome;
	}
	
	public static IAllele[] getTemplateTin()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TIN;
		
		return genome;
	}
	
	public static IAllele[] getTemplateSilver()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.SILVER;
		
		return genome;
	}
	
	public static IAllele[] getTemplateLead()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.LEAD;
		
		return genome;
	}
	
	public static IAllele[] getTemplateAluminum()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.ALUMINUM;
		
		return genome;
	}
	
	public static IAllele[] getTemplateArdite()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.ARDITE;
		
		return genome;
	}
	
	public static IAllele[] getTemplateCobalt()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.COBALT;
		
		return genome;
	}
	
	public static IAllele[] getTemplateManyullyn()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.MANYULLYN;
		
		return genome;
	}
	
	public static IAllele[] getTemplateDiamond()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.DIAMOND;
		
		return genome;
	}
	
	public static IAllele[] getTemplateEmerald()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.EMERALD;
		
		return genome;
	}
	
	public static IAllele[] getTemplateApatite()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.APATITE;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedNorm");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanNormal");
		
		return genome;
	}
	
	public static IAllele[] getTemplateMutableBase()
	{
		IAllele[] genome = getTemplateModBase();
		
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanShortest");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceUp1");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceDown2");
		
		return genome;
	}
	
	public static IAllele[] getTemplateMutable()
	{
		IAllele[] genome = getTemplateMutableBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.MUTABLE;
		
		return genome;
	}
	
	public static IAllele[] getTemplateTransmuting()
	{
		IAllele[] genome = getTemplateMutableBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TRANSMUTING;
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.effectTransmuting;
		
		return genome;
	}
	
	public static IAllele[] getTemplateCrumbling()
	{
		IAllele[] genome = getTemplateMutableBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.CRUMBLING;
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.effectCrumbling;
		
		return genome;
	}
	
	private static IAllele[] getTemplateTCBase()
	{
		IAllele[] genome = getTemplateModBase();

		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = AlleleFlower.flowerThaumcraft;
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityLow");
		
		return genome;
	}

	public static IAllele[] getTemplateTCStark()
	{
		IAllele[] genome = getTemplateTCBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TC_STARK;
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityNormal");
		
		return genome;
	}

	public static IAllele[] getTemplateTCAir()
	{
		IAllele[] genome = getTemplateTCBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TC_AIR;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedFastest");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanShortened");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLargest");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringMaximum");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.effectMoveSpeed;
		
		return genome;
	}

	public static IAllele[] getTemplateTCFire()
	{
		IAllele[] genome = getTemplateTCBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TC_FIRE;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedFaster");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanNormal");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceDown3");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceUp2");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectIgnition");
		
		return genome;
	}

	public static IAllele[] getTemplateTCWater()
	{
		IAllele[] genome = getTemplateTCBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TC_WATER;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedFast");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanLong");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceDown2");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLarge");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.effectCleansing;
		
		return genome;
	}

	public static IAllele[] getTemplateTCEarth()
	{
		IAllele[] genome = getTemplateTCBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TC_EARTH;
		genome[EnumBeeChromosome.SPEED.ordinal()] =  Allele.getBaseAllele("speedSlow");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanLonger");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceDown1");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringFastest");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.effectDigSpeed;
		
		return genome;
	}
	
	public static IAllele[] getTemplateTCMagic()
	{
		IAllele[] genome = getTemplateTCBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TC_MAGIC;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedNorm");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanNormal");
		
		return genome;
	}
	
	private static IAllele[] getTemplateTCBaseVis()
	{
		IAllele[] genome = getTemplateTCBase();

		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityLow");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		
		return genome;
	}
	
	public static IAllele[] getTemplateTCVis()
	{
		IAllele[] genome = getTemplateTCBaseVis();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TC_VIS;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedSlow");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringSlower");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.flowerAuraNode;

		return genome;
	}
	
	public static IAllele[] getTemplateTCFlux()
	{
		IAllele[] genome = getTemplateTCBaseVis();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TC_FLUX;
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceNone");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceNone");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.flowerAuraNode;
		//genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.effectNodeFlux;
		
		return genome;
	}
	
	public static IAllele[] getTemplateTCAttract()
	{
		IAllele[] genome = getTemplateTCBaseVis();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TC_ATTRACT;
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.flowerAuraNode;
		//genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.effectNodeAttract;
		
		return genome;
	}
	
	public static IAllele[] getTemplateTCPure()
	{
		IAllele[] genome = getTemplateTCBaseVis();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TC_PURE;
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceNone");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceNone");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringAverage");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.flowerAuraNode;
		//genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.effectNodePurify;
		
		return genome;
	}
	
	public static IAllele[] getTemplateTCRejuvinating()
	{
		IAllele[] genome = getTemplateTCBaseVis();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TC_REJUVENATING;
		//genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.effectNodeCharge;
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.flowerAuraNode;
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceNone");
		
		return genome;
	}
	
	public static IAllele[] getTemplateTCBrainy()
	{
		IAllele[] genome = getTemplateBaseMalevolent();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TC_BRAINY;
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.spawnBrainyZombie;
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityLow");
		
		return genome;
	}
	
	public static IAllele[] getTemplateTCWispy()
	{
		IAllele[] genome = getTemplateBaseMalevolent();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TC_WISPY;
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.spawnWisp;
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityLow");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.flowerThaumcraft;
		
		return genome;
	}
	
	public static IAllele[] getTemplateTCBatty()
	{
		IAllele[] genome = getTemplateBaseMalevolent();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TC_BATTY;
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.spawnBats;
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLarge");
		
		return genome;
	}
	
	public static IAllele[] getTemplateTCChicken()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TC_CHICKEN;
		
		return genome;
	}
	
	public static IAllele[] getTemplateTCBeef()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TC_BEEF;
		
		return genome;
	}
	
	public static IAllele[] getTemplateTCPork()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.TC_PORK;
		
		return genome;
	}
	
	private static IAllele[] getTemplateEEBase()
	{
		IAllele[] genome = getTemplateModBase();
		
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLarge");
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedSlow");
		
		return genome;
	}
	
	public static IAllele[] getTemplateEEMinium()
	{
		IAllele[] genome = getTemplateEEBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.EE_MINIUM;
		
		return genome;
	}
	
	private static IAllele[] getTemplateAMBase()
	{
		IAllele[] genome = getTemplateModBase();
		
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.flowerArsMagica;
		
		return genome;
	}

	public static IAllele[] getTemplateAMEssence()
	{
		IAllele[] genome = getTemplateAMBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.AM_ESSENCE;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedNorm");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityHigh");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringSlow");
		
		return genome;
	}

	public static IAllele[] getTemplateAMQuintessence()
	{
		IAllele[] genome = getTemplateAMEssence();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.AM_QUINTESSENCE;
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanElongated");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityHigh");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringAverage");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectMisanthrope");
		
		return genome;
	}

	public static IAllele[] getTemplateAMEarth()
	{
		IAllele[] genome = getTemplateAMBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.AM_EARTH;
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringFaster");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanLongest");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityHigh");
		
		return genome;
	}

	public static IAllele[] getTemplateAMAir()
	{
		IAllele[] genome = getTemplateAMBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.AM_AIR;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedFast");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLargest");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth2");
		
		return genome;
	}

	public static IAllele[] getTemplateAMFire()
	{
		IAllele[] genome = getTemplateAMBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.AM_FIRE;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedNorm");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringFast");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectIgnition");
		
		return genome;
	}

	public static IAllele[] getTemplateAMWater()
	{
		IAllele[] genome = getTemplateAMBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.AM_WATER;
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceDown1");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanElongated");
		
		return genome;
	}

	public static IAllele[] getTemplateAMLightning()
	{
		IAllele[] genome = getTemplateAMBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.AM_LIGHTNING;
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanShortest");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLargest");
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.speedBlinding;
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		
		if (ExtraBeesHelper.isActive())
		{
			genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getAllele("extrabees.effect.lightning");
		}
		
		return genome;
	}

	public static IAllele[] getTemplateAMPlant()
	{
		IAllele[] genome = getTemplateAMBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.AM_PLANT;
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringFastest");
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedNorm");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		
		return genome;
	}

	public static IAllele[] getTemplateAMIce()
	{
		IAllele[] genome = getTemplateAMBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.AM_ICE;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceUp2");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceDown2");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedSlowest");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanLongest");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectGlacial");
		
		return genome;
	}

	public static IAllele[] getTemplateAMMagma()
	{
		IAllele[] genome = getTemplateAMBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.AM_MAGMA;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceDown2");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanElongated");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectIgnition");
		
		return genome;
	}

	public static IAllele[] getTemplateAMArcane()
	{
		IAllele[] genome = getTemplateAMBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.AM_ARCANE;
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityLow");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth2");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		
		return genome;
	}
	
	public static IAllele[] getTemplateAMVortex()
	{
		IAllele[] genome = getTemplateAMBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.AM_VORTEX;
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanLonger");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityHigh");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLarger");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.spawnManaDrainer;
		
		return genome;
	}
	
	public static IAllele[] getTemplateAMWight()
	{
		IAllele[] genome = getTemplateAMBase();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.AM_WIGHT;
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedFast");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.spawnWispOrHecate;
		
		return genome;
	}

	public static ItemStack getBeeNBTForSpecies(BeeSpecies species, EnumBeeType beeType)
	{
		ItemStack taggedBee;
		switch (beeType)
		{
			case PRINCESS:
				taggedBee = ItemInterface.getItem("beePrincessGE");
				break;
			case QUEEN:
				taggedBee = ItemInterface.getItem("beeQueenGE");
				break;
			case DRONE:
			default:
				taggedBee = ItemInterface.getItem("beeDroneGE");
				break;
		}
		
		NBTTagCompound tags = new NBTTagCompound();
		
		addGeneToCompound(EnumBeeChromosome.SPECIES, species, tags);
		
		taggedBee.setTagCompound(tags);
		
		return taggedBee;
	}
	
	private static void addGeneToCompound(EnumBeeChromosome gene, IAllele allele, NBTTagCompound compound)
	{
		NBTTagCompound geneRoot = new NBTTagCompound();
		compound.setTag("Genome", geneRoot);
		NBTTagList chromosomes = new NBTTagList();
		geneRoot.setTag("Chromosomes", chromosomes);
		
		NBTTagCompound selectedGene = new NBTTagCompound();
		chromosomes.appendTag(selectedGene);
		
		selectedGene.setByte("Slot", (byte)gene.ordinal());
		selectedGene.setString("UID0", allele.getUID());
		selectedGene.setString("UID1", allele.getUID());		
	}
}
