package thaumicbees.bees;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.core.ItemInterface;
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

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.ESOTERIC;
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
	
	// Basic genome for Arcane branch bees.
	private static IAllele[] getTemplateBaseArcane()
	{
		IAllele[] genome = getTemplateBaseThaumic();
		
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
		IAllele[] genome = getTemplateBaseThaumic();
		
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
	
	private static IAllele[] getTemplateBaseScholarly()
	{
		IAllele[] genome = getTemplateBaseThaumic();

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
	
	private static IAllele[] getTemplateBaseElemental()
	{
		IAllele[] genome = getTemplateBaseThaumic();

		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = AlleleFlower.flowerThaumcraft;
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityLow");
		
		return genome;
	}

	public static IAllele[] getTemplateStark()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.STARK;
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityNormal");
		
		return genome;
	}

	public static IAllele[] getTemplateAir()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.AIR;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedFastest");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanShortened");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLargest");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringMaximum");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.moveSpeed;
		
		return genome;
	}

	public static IAllele[] getTemplateFire()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.FIRE;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedFaster");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanNormal");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceDown3");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceUp2");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectIgnition");
		
		return genome;
	}

	public static IAllele[] getTemplateWater()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.WATER;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedFast");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanLong");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceDown2");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLarge");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.cleansingEffect;
		
		return genome;
	}

	public static IAllele[] getTemplateEarth()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.EARTH;
		genome[EnumBeeChromosome.SPEED.ordinal()] =  Allele.getBaseAllele("speedSlow");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanLonger");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceDown1");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringFastest");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.digSpeed;
		
		return genome;
	}
	
	public static IAllele[] getTemplateInfused()
	{
		IAllele[] genome = getTemplateBaseElemental();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.INFUSED;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedNorm");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanNormal");
		
		return genome;
	}
	
	private static IAllele[] getTemplateBaseVis()
	{
		IAllele[] genome = getTemplateBaseThaumic();

		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityLow");
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		
		return genome;
	}
	
	public static IAllele[] getTemplateAware()
	{
		IAllele[] genome = getTemplateBaseVis();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.AWARE;
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.flowerThaumcraft;
		
		return genome;
	}
	
	public static IAllele[] getTemplateVis()
	{
		IAllele[] genome = getTemplateBaseVis();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.VIS;
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedSlow");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringSlower");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.flowerAuraNode;

		return genome;
	}
	
	public static IAllele[] getTemplatePure()
	{
		IAllele[] genome = getTemplateBaseVis();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.PURE;
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceNone");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceNone");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringAverage");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.flowerAuraNode;
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.effectNodePurify;
		
		return genome;
	}
	
	public static IAllele[] getTemplateFlux()
	{
		IAllele[] genome = getTemplateBaseVis();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.FLUX;
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceNone");
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceNone");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.flowerAuraNode;
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.effectNodeFlux;
		
		return genome;
	}
	
	public static IAllele[] getTemplateNode()
	{
		IAllele[] genome = getTemplateBaseVis();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.NODE;
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringSlowest");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.flowerAuraNode;
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.effectNodeAttract;
		
		return genome;
	}
	
	public static IAllele[] getTemplateRejuvinating()
	{
		IAllele[] genome = getTemplateBaseVis();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.REJUVENATING;
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.effectNodeCharge;
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.flowerAuraNode;
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceNone");
		
		return genome;
	}
	
	private static IAllele[] getTemplateBaseMalevolent()
	{
		IAllele[] genome = getTemplateBaseThaumic();
		
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedFast");
		genome[EnumBeeChromosome.FLOWERING.ordinal()] = Allele.getBaseAllele("floweringFaster");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.fertilityHighDominant;
		
		return genome;
	}
	
	public static IAllele[] getTemplateSkulking()
	{
		IAllele[] genome = getTemplateBaseMalevolent();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.SKULKING;
		genome[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityNormal");
		
		return genome;
	}
	
	public static IAllele[] getTemplateBrainy()
	{
		IAllele[] genome = getTemplateBaseMalevolent();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.BRAINY;
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.spawnBrainyZombie;
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityLow");
		
		return genome;
	}
	
	public static IAllele[] getTemplateGossamer()
	{
		IAllele[] genome = getTemplateBaseMalevolent();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.GOSSAMER;
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.flowerThaumcraft;
		
		return genome;
	}
	
	public static IAllele[] getTemplateWispy()
	{
		IAllele[] genome = getTemplateBaseMalevolent();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.WISPY;
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.spawnWisp;
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.FERTILITY.ordinal()] = Allele.getBaseAllele("fertilityLow");
		genome[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = Allele.flowerThaumcraft;
		
		return genome;
	}
	
	public static IAllele[] getTemplateBatty()
	{
		IAllele[] genome = getTemplateBaseMalevolent();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.BATTY;
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.spawnBats;
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLarge");
		
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
	
	private static IAllele[] getTemplateBaseTemporal()
	{
		IAllele[] genome = getTemplateBaseThaumic();
		
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
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.slowSpeed;
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanElongated");
		
		return genome;
	}

	public static IAllele[] getTemplateLordly()
	{
		IAllele[] genome = getTemplateBaseTemporal();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.LORDLY;
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.slowSpeed;
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanLong");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectDrunkard");
		
		return genome;
	}

	public static IAllele[] getTemplateDoctoral()
	{
		IAllele[] genome = getTemplateBaseTemporal();

		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.DOCTORAL;
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.slowSpeed;
		genome[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth3");
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLarge");
		genome[EnumBeeChromosome.NOCTURNAL.ordinal()] = Allele.getBaseAllele("boolTrue");
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanLongest");
		genome[EnumBeeChromosome.EFFECT.ordinal()] = Allele.getBaseAllele("effectHeroic");
		
		return genome;
	}
	
	private static IAllele[] getTemplateBaseAlchemical()
	{
		IAllele[] genome = getTemplateBaseThaumic();
		
		genome[EnumBeeChromosome.TERRITORY.ordinal()] = Allele.getBaseAllele("territoryLarge");
		genome[EnumBeeChromosome.SPEED.ordinal()] = Allele.getBaseAllele("speedSlow");
		
		return genome;
	}
	
	public static IAllele[] getTemplateMinium()
	{
		IAllele[] genome = getTemplateBaseAlchemical();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.MINIUM;
		
		return genome;
	}
	
	private static IAllele[] getTemplateBaseSoul()
	{
		IAllele[] genome = getTemplateBaseThaumic();
		
		genome[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = Allele.getBaseAllele("toleranceBoth1");
		genome[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = Allele.getBaseAllele("boolTrue");
		
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
		genome[EnumBeeChromosome.LIFESPAN.ordinal()] = Allele.getBaseAllele("lifespanNormal");
		
		return genome;
	}
	
	private static IAllele[] getTemplateBaseMetallic()
	{
		IAllele[] genome = getTemplateBaseThaumic();
		
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
	
	public static IAllele[] getTemplateChicken()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.CHICKEN;
		
		return genome;
	}
	
	public static IAllele[] getTemplateBeef()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.BEEF;
		
		return genome;
	}
	
	public static IAllele[] getTemplatePork()
	{
		IAllele[] genome = getTemplateBaseMetallic();
		
		genome[EnumBeeChromosome.SPECIES.ordinal()] = BeeSpecies.PORK;
		
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
