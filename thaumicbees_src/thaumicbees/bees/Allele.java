package thaumicbees.bees;

import net.minecraft.potion.Potion;
import thaumicbees.main.utils.compat.ArsMagicaHelper;
import thaumicbees.main.utils.compat.ThaumcraftHelper;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IAlleleFlowers;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleEffect;

public class Allele implements IAllele
{
	public static AlleleInteger fertilityHighDominant;
	public static AlleleFloat speedBlinding;
	
	public static IAlleleFlowers flowerBookshelf;
	public static IAlleleFlowers flowerThaumcraft;
	public static IAlleleFlowers flowerArsMagica;
	public static IAlleleFlowers flowerAuraNode;
	@Deprecated
	public static IAlleleFlowers flowerNodePurify;
	@Deprecated
	public static IAlleleFlowers flowerNodeFluxify;
	
	public static IAlleleEffect cleansingEffect;
	public static IAlleleEffect digSpeed;
	public static IAlleleEffect moveSpeed;
	public static IAlleleEffect slowSpeed;
	
	public static IAlleleEffect spawnBrainyZombie;
	public static IAlleleEffect spawnWisp;
	public static IAlleleEffect spawnBats;
	public static IAlleleEffect spawnGhast;
	public static IAlleleEffect spawnSpider;
	public static IAlleleEffect spawnManaDrainer;
	public static IAlleleEffect spawnWispOrHecate;
	
	public static IAlleleEffect effectNodeAttract;
	public static IAlleleEffect effectNodePurify;
	public static IAlleleEffect effectNodeFlux;
	public static IAlleleEffect effectNodeCharge;
	
	public static void setupAdditionalAlleles()
	{
		Allele.fertilityHighDominant = new AlleleInteger("fertilityHighDominant", 3, true);
		Allele.speedBlinding = new AlleleFloat("speedBlinding", 2f, false);
		
		Allele.flowerBookshelf = new AlleleFlower("flowerBookshelf", new FlowerProviderBookshelf(), true);
		if (ThaumcraftHelper.isActive())
		{
			FlowerProviderAuraNode provider = new FlowerProviderAuraNode();
			Allele.flowerThaumcraft = new AlleleFlower("flowerThaumcraftPlant", new FlowerProviderThaumcraftFlower(), false);
			Allele.flowerAuraNode = new AlleleFlower("flowerAuraNode", provider, true);
			Allele.flowerNodePurify = new AlleleFlower("flowerAuraNodePurify", provider, false);
			Allele.flowerNodeFluxify = new AlleleFlower("flowerAuraNodeFlux", provider, false);

			Allele.spawnBrainyZombie = new AlleleEffectSpawnMob("effectBrainy", false, ThaumcraftHelper.Entity.BRAINY_ZOMBIE.entityID)
				.setAggrosPlayerOnSpawn().setThrottle(800).setSpawnsOnPlayerNear(null).setMaxMobsInSpawnZone(2);
	
			Allele.spawnBats = new AlleleEffectSpawnMob("effectBatty", false, ThaumcraftHelper.Entity.FIREBAT.entityID)
				.setThrottle(300).setSpawnsOnPlayerNear("Bat");
	
			Allele.spawnWisp = new AlleleEffectSpawnWisp("effectWispy", false, ThaumcraftHelper.Entity.WISP.entityID, "thaumcraft.wisplive")
				.setThrottle(1800).setChanceToSpawn(79);
		}
		else
		{
			Allele.flowerThaumcraft = Allele.flowerAuraNode = (IAlleleFlowers)Allele.getBaseAllele("flowersVanilla");
			Allele.flowerNodePurify = Allele.flowerNodeFluxify = (IAlleleFlowers)Allele.getBaseAllele("flowersVanilla");
			
			Allele.spawnBrainyZombie = Allele.spawnBats = Allele.spawnWisp = (IAlleleEffect)Allele.getBaseAllele("effectNone");
		}
		
		if (ArsMagicaHelper.isActive())
		{
			Allele.flowerArsMagica = new AlleleFlower("flowerArsMagicaPlant", new FlowerProviderArsMagicaFlower(), false);
			//String id, boolean isDominant, int throttle, String[] mobs, int[] chance
			Allele.spawnManaDrainer = new AlleleEffectSpawnMobWeighted("effectManaDrainer", true, 20,
					new String[] { "ArsMagica.MobManaCreeper", "ArsMagica.ManaVortex" },
					new int[] { 60, 2 });
			
			Allele.spawnWispOrHecate = new AlleleEffectSpawnMobWeighted("effectAMWisp", true, 20,
					new String[] { "ArsMagica.MobWisp", "ArsMagica.MobHecate" },
					new int[] { 40, 3 });
		}
		else
		{
			Allele.flowerArsMagica = (IAlleleFlowers)Allele.getBaseAllele("flowersVanilla");
			Allele.spawnManaDrainer = Allele.spawnWispOrHecate = (IAlleleEffect)Allele.getBaseAllele("effectNone");
		}

		Allele.effectNodeAttract = new AlleleEffectAuraNodeAttract("effectNodeGeneration", false, 400);
		Allele.effectNodePurify = new AlleleEffectAuraNodePurify("effectNodePurify", false, 600, 150);
		Allele.effectNodeFlux = new AlleleEffectAuraNodeFlux("effectNodeFlux", true, 300, 300);
		Allele.effectNodeCharge = new AlleleEffectAuraNodeCharge("effectNodeCharge", true, 1200);

		Allele.cleansingEffect = new AlleleEffectCure("effectCurative", false);
		Allele.digSpeed = new AlleleEffectPotion("effectDigSpeed", Potion.digSpeed, 15, false);
		Allele.moveSpeed = new AlleleEffectPotion("effectMoveSpeed", Potion.moveSpeed, 10, false);
		Allele.slowSpeed = new AlleleEffectPotion("effectSlowSpeed", Potion.moveSlowdown, 3, false);

		Allele.spawnGhast = new AlleleEffectSpawnMob("Ghast", false, "Ghast", "mob.ghast.moan")
			.setThrottle(2060).setChanceToSpawn(10).setMaxMobsInSpawnZone(1);
		
		Allele.spawnSpider = new AlleleEffectSpawnMob("Spider", false, "Spider", "mob.spider.step")
			.setThrottle(400).setChanceToSpawn(70).setMaxMobsInSpawnZone(4);
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
	
	public static IAllele getAllele(String name)
	{
		return AlleleManager.alleleRegistry.getAllele(name);
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
