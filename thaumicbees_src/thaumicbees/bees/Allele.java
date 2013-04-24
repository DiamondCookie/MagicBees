package thaumicbees.bees;

import net.minecraft.potion.Potion;
import thaumicbees.main.utils.compat.ArsMagicaHelper;
import thaumicbees.main.utils.compat.ThaumcraftHelper;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IAlleleFlowers;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;

public class Allele implements IAllele
{
	public static AlleleInteger fertilityHighDominant;
	
	public static IAlleleFlowers flowerBookshelf;
	public static IAlleleFlowers flowerThaumcraft;
	public static IAlleleFlowers flowerArsMagica;
	public static IAlleleFlowers flowerAuraNode;
	@Deprecated
	public static IAlleleFlowers flowerNodePurify;
	@Deprecated
	public static IAlleleFlowers flowerNodeFluxify;
	
	public static AlleleEffectCure cleansingEffect;
	public static AlleleEffectPotion digSpeed;
	public static AlleleEffectPotion moveSpeed;
	public static AlleleEffectPotion slowSpeed;
	public static AlleleEffectSpawnMob spawnBrainyZombie;
	public static AlleleEffectSpawnMob spawnWisp;
	public static AlleleEffectSpawnMob spawnBats;
	public static AlleleEffectSpawnMob spawnGhast;
	public static AlleleEffectAuraNodeAttract effectNodeAttract;
	public static AlleleEffectAuraNodePurify effectNodePurify;
	public static AlleleEffectAuraNodeFlux effectNodeFlux;
	public static AlleleEffectAuraNodeCharge effectNodeCharge;
	
	public static void setupAdditionalAlleles()
	{
		Allele.fertilityHighDominant = new AlleleInteger("fertilityHighDominant", 3, true);
		
		Allele.flowerBookshelf = new AlleleFlower("flowerBookshelf", new FlowerProviderBookshelf(), true);
		if (ThaumcraftHelper.isActive())
		{
			FlowerProviderAuraNode provider = new FlowerProviderAuraNode();
			Allele.flowerThaumcraft = new AlleleFlower("flowerThaumcraftPlant", new FlowerProviderThaumcraftFlower(), false);
			Allele.flowerAuraNode = new AlleleFlower("flowerAuraNode", provider, true);
			Allele.flowerNodePurify = new AlleleFlower("flowerAuraNodePurify", provider, false);
			Allele.flowerNodeFluxify = new AlleleFlower("flowerAuraNodeFlux", provider, false);
		}
		else
		{
			Allele.flowerThaumcraft = (IAlleleFlowers)Allele.getBaseAllele("flowersVanilla");
			Allele.flowerAuraNode = (IAlleleFlowers)Allele.getBaseAllele("flowersVanilla");
			Allele.flowerNodePurify = (IAlleleFlowers)Allele.getBaseAllele("flowersVanilla");
			Allele.flowerNodeFluxify = (IAlleleFlowers)Allele.getBaseAllele("flowersVanilla");
		}
		
		if (ArsMagicaHelper.isActive())
		{
			Allele.flowerArsMagica = new AlleleFlower("flowerArsMagicaPlant", new FlowerProviderArsMagicaFlower(), false);
		}
		else
		{
			Allele.flowerArsMagica = (IAlleleFlowers)Allele.getBaseAllele("flowersVanilla");
		}

		Allele.effectNodeAttract = new AlleleEffectAuraNodeAttract("effectNodeGeneration", false, 400);
		Allele.effectNodePurify = new AlleleEffectAuraNodePurify("effectNodePurify", false, 600, 150);
		Allele.effectNodeFlux = new AlleleEffectAuraNodeFlux("effectNodeFlux", true, 300, 300);
		Allele.effectNodeCharge = new AlleleEffectAuraNodeCharge("effectNodeCharge", true, 1200);

		Allele.cleansingEffect = new AlleleEffectCure("effectCurative", false);
		Allele.digSpeed = new AlleleEffectPotion("effectDigSpeed", Potion.digSpeed, 15, false);
		Allele.moveSpeed = new AlleleEffectPotion("effectMoveSpeed", Potion.moveSpeed, 10, false);
		Allele.slowSpeed = new AlleleEffectPotion("effectSlowSpeed", Potion.moveSlowdown, 3, false);

		Allele.spawnBrainyZombie = new AlleleEffectSpawnMob("effectBrainy", false, ThaumcraftHelper.Entity.BRAINY_ZOMBIE.entityID);
		Allele.spawnBrainyZombie.setAggrosPlayerOnSpawn().setThrottle(800).setSpawnsOnPlayerNear(null).setMaxMobsInSpawnZone(2);

		Allele.spawnBats = new AlleleEffectSpawnMob("effectBatty", false, ThaumcraftHelper.Entity.FIREBAT.entityID);
		Allele.spawnBats.setThrottle(300).setSpawnsOnPlayerNear("Bat");

		Allele.spawnWisp = new AlleleEffectSpawnWisp("effectWispy", false, ThaumcraftHelper.Entity.WISP.entityID, "thaumcraft.wisplive");
		Allele.spawnWisp.setThrottle(1800).setChanceToSpawn(79);

		Allele.spawnGhast = new AlleleEffectSpawnMob("Ghast", false, "Ghast", "mob.ghast.moan");
		Allele.spawnGhast.setThrottle(2060).setChanceToSpawn(10).setMaxMobsInSpawnZone(1);
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
