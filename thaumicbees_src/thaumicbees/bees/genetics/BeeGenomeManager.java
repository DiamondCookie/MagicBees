package thaumicbees.bees.genetics;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;

/**
 * Simply a class to hold all the functions to manage species' default genomes.
 * @author MysteriousAges
 *
 */
public class BeeGenomeManager
{
	// Basic genome for All thaumic bees.
	private static IAllele[] getTemplateBaseThaumic()
	{
		IAllele[] genome = new IAllele[EnumBeeChromosome.values().length];

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.Esoteric;
		genome[EnumBeeChromosome.SPEED.ordinal()] =  AlleleManager.getAllele("speedSlowest");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = AlleleManager.getAllele("lifespanShorter");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = AlleleManager.getAllele("fertilityNormal");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = AlleleManager.getAllele("toleranceNone");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = AlleleManager.getAllele("boolFalse");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = AlleleManager.getAllele("toleranceNone");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = AlleleManager.getAllele("boolFalse");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = AlleleManager.getAllele("boolFalse");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = AlleleManager.getAllele("flowersVanilla");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = AlleleManager.getAllele("floweringSlowest");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = AlleleManager.getAllele("territoryDefault");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = AlleleManager.getAllele("effectNone");

		return genome;
	}
	
	// Basic genome for Arcane branch bees.
	private static IAllele[] getTemplateBaseArcane()
	{
		IAllele[] genome = getTemplateBaseThaumic();
		
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = AlleleManager.getAllele("toleranceBoth1");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = AlleleManager.getAllele("floweringSlow");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = AlleleManager.getAllele("fertilityHigh");
		
		return genome;
	}

	// Specialization genome for Esoteric bees.
	public static IAllele[] getTemplateEsoteric()
	{
		IAllele[] genome = getTemplateBaseArcane();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.Esoteric;
		
		return genome;
	}

	public static IAllele[] getTemplateMysterious()
	{
		IAllele[] genome = getTemplateBaseArcane();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.Mysterious;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = AlleleManager.getAllele("toleranceBoth2");
		
		return genome;
	}

	public static IAllele[] getTemplateArcane()
	{
		IAllele[] genome = getTemplateBaseArcane();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.Arcane;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = AlleleManager.getAllele("toleranceBoth2");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = AlleleManager.getAllele("fertilityNormal");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = AlleleManager.getAllele("floweringAverage");
		
		return genome;
	}

	private static IAllele[] getTemplateBaseSupernatural()
	{
		IAllele[] genome = getTemplateBaseThaumic();
		
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = AlleleManager.getAllele("boolTrue");
		genome[EnumBeeChromosome.SPEED.ordinal()] = AlleleManager.getAllele("speedNorm");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = AlleleManager.getAllele("floweringSlowest");
		
		return genome;
	}

	public static IAllele[] getTemplateCharmed()
	{
		IAllele[] genome = getTemplateBaseSupernatural();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.Charmed;
		
		return genome;
	}

	public static IAllele[] getTemplateEnchanted()
	{
		IAllele[] genome = getTemplateBaseSupernatural();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.Enchanted;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = AlleleManager.getAllele("toleranceBoth1");
		
		return genome;
	}

	public static IAllele[] getTemplateSupernatural()
	{
		IAllele[] genome = getTemplateBaseSupernatural();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.Supernatural;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = AlleleManager.getAllele("toleranceBoth2");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = AlleleManager.getAllele("toleranceBoth1");
		
		return genome;
	}
	
	private static IAllele[] getTemplateBaseScholarly()
	{
		IAllele[] genome = getTemplateBaseThaumic();

		genome[EnumBeeChromosome.SPEED.ordinal()] = AlleleManager.getAllele("speedSlow");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = AlleleManager.getAllele("lifespanElongated");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = AlleleManager.getAllele("boolTrue");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = AlleleFlower.alleleFlowerBookshelf;
		
		return genome;
	}

	public static IAllele[] getTemplatePupil()
	{
		IAllele[] genome = getTemplateBaseScholarly();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.Pupil;
		genome[EnumBeeChromosome.SPEED.ordinal()] = AlleleManager.getAllele("speedSlower");
		
		return genome;
	}

	public static IAllele[] getTemplateScholarly()
	{
		IAllele[] genome = getTemplateBaseScholarly();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.Scholarly;
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = AlleleManager.getAllele("fertilityLow");
		
		return genome;
	}

	public static IAllele[] getTemplateSavant()
	{
		IAllele[] genome = getTemplateBaseScholarly();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.Savant;
		genome[EnumBeeChromosome.SPEED.ordinal()] = AlleleManager.getAllele("speedNorm");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = AlleleManager.getAllele("fertilityLow");
		
		return genome;
	}
	
	private static IAllele[] getTemplateBaseElemental()
	{
		IAllele[] genome = getTemplateBaseThaumic();

		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = AlleleFlower.alleleFlowerThaumcraft;
		
		return genome;
	}

	public static IAllele[] getTemplateStark()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.Stark;
		
		return genome;
	}

	public static IAllele[] getTemplateAir()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.Air;
		genome[EnumBeeChromosome.SPEED.ordinal()] = AlleleManager.getAllele("speedFastest");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = AlleleManager.getAllele("lifespanShortened");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = AlleleManager.getAllele("territoryLargest");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = AlleleManager.getAllele("boolTrue");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = AlleleManager.getAllele("floweringFastest");
		
		return genome;
	}

	public static IAllele[] getTemplateFire()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.Fire;
		genome[EnumBeeChromosome.SPEED.ordinal()] = AlleleManager.getAllele("speedFaster");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = AlleleManager.getAllele("lifespanNormal");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = AlleleManager.getAllele("toleranceDown3");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = AlleleManager.getAllele("toleranceUp2");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = AlleleManager.getAllele("boolTrue");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = AlleleManager.getAllele("effectIgnition");
		
		return genome;
	}

	public static IAllele[] getTemplateWater()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.Water;
		genome[EnumBeeChromosome.SPEED.ordinal()] = AlleleManager.getAllele("speedFast");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = AlleleManager.getAllele("lifespanLong");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = AlleleManager.getAllele("toleranceDown2");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = AlleleManager.getAllele("boolTrue");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = AlleleManager.getAllele("territoryLarge");
		
		return genome;
	}

	public static IAllele[] getTemplateEarth()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.Earth;
		genome[EnumBeeChromosome.SPEED.ordinal()] =  AlleleManager.getAllele("speedSlow");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = AlleleManager.getAllele("lifespanLonger");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = AlleleManager.getAllele("toleranceDown1");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = AlleleManager.getAllele("boolTrue");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = AlleleManager.getAllele("boolTrue");
		
		return genome;
	}
	
}
