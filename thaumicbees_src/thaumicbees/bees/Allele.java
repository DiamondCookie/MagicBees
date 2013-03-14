package thaumicbees.bees;

import net.minecraft.potion.Potion;
import thaumicbees.main.utils.compat.ThaumcraftHelper;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;

public class Allele implements IAllele
{
	public static AlleleInteger fertilityHighDominant;
	
	public static AlleleFlower flowerBookshelf;
	public static AlleleFlower flowerThaumcraft;
	public static AlleleFlower flowerAuraNode;
	public static AlleleFlower flowerNodePurify;
	public static AlleleFlower flowerNodeFluxify;
	
	public static AlleleEffectCure cleansingEffect;
	public static AlleleEffectPotion digSpeed;
	public static AlleleEffectPotion moveSpeed;
	public static AlleleEffectPotion slowSpeed;
	public static AlleleEffectSpawnMob spawnBrainyZombie;
	public static AlleleEffectSpawnMob spawnWisp;
	public static AlleleEffectSpawnMob spawnBats;
	public static AlleleEffectSpawnMob spawnGhast;
	public static AlleleEffectAuraNodeGrow nodeGen;
	
	public static void setupAdditionalAlleles()
	{
		Allele.fertilityHighDominant = new AlleleInteger("fertilityHighDominant", 3, true);
		
		Allele.flowerBookshelf = new AlleleFlower("flowerBookshelf", new FlowerProviderBookshelf(), true);
		Allele.flowerThaumcraft = new AlleleFlower("flowerThaumcraftPlant", new FlowerProviderThaumcraftFlower(), false);
		Allele.flowerAuraNode = new AlleleFlower("flowerAuraNode", new FlowerProviderAuraNode(), true);
		Allele.flowerNodePurify = new AlleleFlower("flowerAuraNodePurify", new FlowerProviderAuraNodePurify(), false);
		Allele.flowerNodeFluxify = new AlleleFlower("flowerAuraNodeFlux", new FlowerProviderAuraNodeFlux(), false);
		
		Allele.cleansingEffect = new AlleleEffectCure("effectCurative", false);
		Allele.digSpeed = new AlleleEffectPotion("effectDigSpeed", Potion.digSpeed, 15, false);
		Allele.moveSpeed = new AlleleEffectPotion("effectMoveSpeed", Potion.moveSpeed, 10, false);
		Allele.slowSpeed = new AlleleEffectPotion("effectSlowSpeed", Potion.moveSlowdown, 3, false);
		
		Allele.nodeGen = new AlleleEffectAuraNodeGrow("effectNodeGeneration", false, 400);

		Allele.spawnBrainyZombie = new AlleleEffectSpawnMob("effectBrainy", false, ThaumcraftHelper.Entity.BRAINY_ZOMBIE.entityID);
		Allele.spawnBrainyZombie.setAggrosPlayerOnSpawn().setThrottle(800).setSpawnsOnPlayerNear(null).setMaxMobsInSpawnZone(2);
		
		Allele.spawnBats = new AlleleEffectSpawnMob("effectBatty", false, ThaumcraftHelper.Entity.FIREBAT.entityID);
		Allele.spawnBats.setThrottle(300).setSpawnsOnPlayerNear("Bat");
		
		Allele.spawnWisp = new AlleleEffectSpawnWisp("effectWispy", false, ThaumcraftHelper.Entity.WISP.entityID);
		Allele.spawnWisp.setThrottle(1800);
		
		Allele.spawnGhast = new AlleleEffectSpawnMob("Ghast", false, "Ghast");
		Allele.spawnGhast.setThrottle(2000).setChanceToSpawn(20).setMaxMobsInSpawnZone(1);
	}
	
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
		return AlleleManager.alleleRegistry.getAllele("forestry." + name);
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
