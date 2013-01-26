package thaumicbees.bees.genetics;

import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;

public class Allele implements IAllele
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
	public static BeeSpecies Infused;
	
	public static BeeSpecies Aware;
	public static BeeSpecies Vis;
	public static BeeSpecies Pure;
	public static BeeSpecies Flux;
	
	public static BeeSpecies Skulking;
	public static BeeSpecies Brainy;
	public static BeeSpecies Gossamer;
	public static BeeSpecies Wispy;
	public static BeeSpecies Batty;
	public static BeeSpecies Ghastly;
	
	public static AlleleInteger fertilityHighDominant;
	
	public static AlleleFlower flowerBookshelf;
	public static AlleleFlower flowerThaumcraft;
	public static AlleleFlower flowerAuraNode;
	public static AlleleFlower flowerNodePurify;
	public static AlleleFlower flowerNodeFluxify;
	
	public static AlleleEffectCure cleansingEffect;
	public static AlleleEffectPotion digSpeed;
	public static AlleleEffectPotion moveSpeed;
	public static AlleleEffectSpawnMob spawnBrainyZombie;
	public static AlleleEffectSpawnMob spawnWisp;
	public static AlleleEffectSpawnMob spawnBats;
	public static AlleleEffectSpawnMob spawnGhast;
	
	private String uid;
	private boolean dominant;

	public static IAlleleBeeSpecies getBaseSpecies(String name)
	{
		return (IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele((new StringBuilder()).append("forestry.species").append(name).toString());
	}
	
	public static IAlleleBeeSpecies getExtraSpecies(String name)
	{
		return (IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele((new StringBuilder()).append("extrabees.species.").append(name).toString());
	}
	
	public static IAllele getBaseAllele(String name)
	{
		return AlleleManager.getAllele(name);
	}
	
	public Allele(String id, boolean isDominant)
	{
		this.uid = "thaumicbees." + id;
		this.dominant = isDominant;
		AlleleManager.alleleRegistry.registerAllele(this);
	}

	@Override
	public String getUID()
	{
		return this.uid;
	}

	@Override
	public boolean isDominant()
	{
		return this.dominant;
	}

}
