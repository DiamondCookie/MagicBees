package thaumicbees.bees;

import cpw.mods.fml.common.network.IGuiHandler;
import forestry.api.apiculture.*;
import forestry.api.core.*;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.IClassification.EnumClassLevel;

import java.lang.reflect.Field;
import java.util.Random;

import thaumcraft.api.EnumNodeType;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumicbees.bees.genetics.Allele;
import thaumicbees.bees.genetics.AlleleEffectAuraNodeGrow;
import thaumicbees.bees.genetics.AlleleEffectCure;
import thaumicbees.bees.genetics.AlleleEffectPotion;
import thaumicbees.bees.genetics.AlleleEffectSpawnMob;
import thaumicbees.bees.genetics.AlleleEffectSpawnWisp;
import thaumicbees.bees.genetics.AlleleFlower;
import thaumicbees.bees.genetics.AlleleInteger;
import thaumicbees.bees.genetics.BeeGenomeManager;
import thaumicbees.bees.genetics.BeeMutation;
import thaumicbees.bees.genetics.BeeSpecies;
import thaumicbees.item.ItemComb;
import thaumicbees.item.ItemManager;
import thaumicbees.item.types.CombType;
import thaumicbees.item.types.DropType;
import thaumicbees.item.types.PollenType;
import thaumicbees.item.types.ResourceType;
import thaumicbees.main.ThaumicBees;
import thaumicbees.thaumcraft.TCEntity;
import thaumicbees.thaumcraft.TCMiscResource;
import thaumicbees.thaumcraft.TCShardType;
import thaumicbees.thaumcraft.ThaumcraftCompat;

import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class TBBeeManager
{
	private static int defaultBodyColour = 0xFF6E0D;
	private static int malevolentBodyColour = 0xe15236;
	private static boolean hideSpecies = true;

	public void doInit()
	{
		try
		{
			Field f = Class.forName("forestry.core.config.Config").getField("enableParticleFX");
			ThaumicBees.getInstanceConfig().DrawParticleEffects = f.getBoolean(null);
			
			f = Class.forName("forestry.core.config.Defaults").getField("TEXTURE_PARTICLES_BEE");
			thaumicbees.main.CommonProxy.FORESTRY_GFX_BEEEFFECTS = (String)f.get(null);
		}
		catch (Exception e)
		{
			// Could not get Forestry config value.
		}
	}

	public void postInit()
	{
		setupAdditionalAlleles();
		setupBeeSpecies();
		setupBeeMutations();
	}
	
	private void setupAdditionalAlleles()
	{
		Allele.fertilityHighDominant = new AlleleInteger("fertilityHighDominant", 3, true);
		
		Allele.flowerBookshelf = new AlleleFlower("flowerBookshelf", new FlowerProviderBookshelf(), true);
		Allele.flowerThaumcraft = new AlleleFlower("flowerThaumcraftPlant", new FlowerProviderThaumcraftFlower(), false);
		Allele.flowerAuraNode = new AlleleFlower("flowerAuraNode", new FlowerProviderAuraNode(), true);
		Allele.flowerNodePurify = new AlleleFlower("flowerAuraNodePurify", new FlowerProviderAuraNodePurify(), false);
		Allele.flowerNodeFluxify = new AlleleFlower("flowerAuraNodeFlux", new FlowerProviderAuraNodeFlux(), false);
		
		Allele.cleansingEffect = new AlleleEffectCure("effectCurative", false);
		Allele.digSpeed = new AlleleEffectPotion("effectDigSpeed", "Mining", Potion.digSpeed, 7, false);
		Allele.moveSpeed = new AlleleEffectPotion("effectMoveSpeed", "Swiftness", Potion.moveSpeed, 5, false);
		
		Allele.nodeGen = new AlleleEffectAuraNodeGrow("effectNodeGeneration", "Nodeify", false, 800);

		Allele.spawnBrainyZombie = new AlleleEffectSpawnMob("effectBrainy", false, "Brainy", TCEntity.BRAINY_ZOMBIE.entityID);
		Allele.spawnBrainyZombie.setAggrosPlayerOnSpawn().setThrottle(800).setSpawnsOnPlayerNear(null).setMaxMobsInSpawnZone(2);
		
		Allele.spawnBats = new AlleleEffectSpawnMob("effectBatty", false, "Batty", TCEntity.FIREBAT.entityID);
		Allele.spawnBats.setThrottle(300).setSpawnsOnPlayerNear("Bat");
		
		Allele.spawnWisp = new AlleleEffectSpawnWisp("effectWispy", false, "Wispy", TCEntity.WISP.entityID);
		Allele.spawnWisp.setThrottle(1800);
		
		Allele.spawnGhast = new AlleleEffectSpawnMob("Ghast", false, "Ghastly", "Ghast");
		Allele.spawnGhast.setThrottle(2000).setChanceToSpawn(20).setMaxMobsInSpawnZone(1);
	}

	private void setupBeeSpecies()
	{
		IBreedingManager breedingMgr = BeeManager.breedingManager;
		ItemStack forestryItem;
		
		IClassification familyBee = AlleleManager.alleleRegistry.getClassification("family.apidae");
		IClassification occult = AlleleManager.alleleRegistry.createAndRegisterClassification(EnumClassLevel.GENUS, "occult", "Arcanus");
		occult.setParent(familyBee);

		Allele.Esoteric = new BeeSpecies("Esoteric", "An unusual crossbreed which seems to have magical properties.|Apinomicon",
				"secretiore", occult, 0,
				0x001099, 0xcc763c, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, true);
		Allele.Esoteric.addProduct(ItemManager.combs.getStackForType(CombType.OCCULT), 20);
		Allele.Esoteric.setGenome(BeeGenomeManager.getTemplateEsoteric());
		occult.addMemberSpecies(Allele.Esoteric);
		breedingMgr.registerBeeTemplate(Allele.Esoteric.getGenome());
		
		Allele.Mysterious = new BeeSpecies("Mysterious", "These bees have been to the end of the world and back, and their power has grown.|Apinomicon",
				"mysticus", occult, 0,
				0x762bc2, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, true);
		Allele.Mysterious.addProduct(ItemManager.combs.getStackForType(CombType.OCCULT), 25);
		Allele.Mysterious.setGenome(BeeGenomeManager.getTemplateMysterious());
		occult.addMemberSpecies(Allele.Mysterious);
		breedingMgr.registerBeeTemplate(Allele.Mysterious.getGenome());
		
		Allele.Arcane = new BeeSpecies("Arcane", "\"Their produce is charged with magic.\"|Azanor, Master Thaumaturge",
				"arcanus", occult, 0,
				0xd242df, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, true);
		Allele.Arcane.addProduct(ItemManager.combs.getStackForType(CombType.OCCULT), 30);
		Allele.Arcane.addSpecialty(ItemManager.drops.getStackForType(DropType.ENCHANTED, 1), 9);
		Allele.Arcane.setGenome(BeeGenomeManager.getTemplateArcane());
		occult.addMemberSpecies(Allele.Arcane);
		breedingMgr.registerBeeTemplate(Allele.Arcane.getGenome());
		
		IClassification otherworldly = AlleleManager.alleleRegistry.createAndRegisterClassification(EnumClassLevel.GENUS, "coeleste", "Coeleste");
		otherworldly.setParent(familyBee);
		
		Allele.Charmed = new BeeSpecies("Charmed", "Your first experiments in Thaumaturgical Apiculture have yielded fruit. Buzzing fruit.|Apinomicon",
				"larvatus", otherworldly, 0,
				0x48EEEC, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, true);
		Allele.Charmed.addProduct(ItemManager.combs.getStackForType(CombType.OTHERWORLDLY), 20);
		Allele.Charmed.setGenome(BeeGenomeManager.getTemplateCharmed());
		otherworldly.addMemberSpecies(Allele.Charmed);
		breedingMgr.registerBeeTemplate(Allele.Charmed.getGenome());
		
		Allele.Enchanted = new BeeSpecies("Enchanted", "Successive generations of Charmed bees have reinforced their connection to the unknown.|Apinomicon",
				"cantatus", otherworldly, 0,
				0x18e726, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, true);
		Allele.Enchanted.addProduct(ItemManager.combs.getStackForType(CombType.OTHERWORLDLY), 30);
		Allele.Enchanted.setGenome(BeeGenomeManager.getTemplateEnchanted());
		otherworldly.addMemberSpecies(Allele.Enchanted);
		breedingMgr.registerBeeTemplate(Allele.Enchanted.getGenome());
		
		Allele.Supernatural = new BeeSpecies("Supernatural", "These bees walk the line between this world and the unseen.|Apinomicon",
				"coeleste", otherworldly, 0,
				0x005614, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, true);
		Allele.Supernatural.addProduct(ItemManager.combs.getStackForType(CombType.OTHERWORLDLY), 40);
		Allele.Supernatural.addSpecialty(ItemManager.pollen.getStackForType(PollenType.UNUSUAL), 8);
		Allele.Supernatural.setGenome(BeeGenomeManager.getTemplateSupernatural());
		otherworldly.addMemberSpecies(Allele.Supernatural);
		breedingMgr.registerBeeTemplate(Allele.Supernatural.getGenome());
		
		IClassification learned = AlleleManager.alleleRegistry.createAndRegisterClassification(EnumClassLevel.GENUS, "docto", "Docto");
		learned.setParent(familyBee);

		Allele.Pupil = new BeeSpecies("Pupil", "\"What does that bee want with my paper?!|Yorae, Librarian",
				"disciplina", learned, 0,
				0xFFFF00, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.ARID,
				false, hideSpecies, true, true);
		Allele.Pupil.addProduct(ItemManager.combs.getStackForType(CombType.PAPERY), 20);
		Allele.Pupil.setGenome(BeeGenomeManager.getTemplatePupil());
		learned.addMemberSpecies(Allele.Pupil);
		breedingMgr.registerBeeTemplate(Allele.Pupil.getGenome());

		Allele.Scholarly = new BeeSpecies("Scholarly", "\"I can't be sure, but I think they might be smarter than me...\"|Yorae, Librarian",
				"studiosis", learned, 0,
				0x6E0000, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.ARID,
				false, hideSpecies, true, false);
		Allele.Scholarly.addProduct(ItemManager.combs.getStackForType(CombType.PAPERY), 25);
		Allele.Scholarly.addSpecialty(ItemManager.miscResources.getStackForType(ResourceType.LORE_FRAGMENT), 2);
		Allele.Scholarly.setGenome(BeeGenomeManager.getTemplateScholarly());
		learned.addMemberSpecies(Allele.Scholarly);
		breedingMgr.registerBeeTemplate(Allele.Scholarly.getGenome());

		Allele.Savant = new BeeSpecies("Savant", "lim(x^(i / pi)/ log(e * 7 - ln(32/x^-pi))). Solve for honey.|Note found on Yorae's desk",
				"philologus", learned, 0,
				0x6E1C6D, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.ARID,
				true, hideSpecies, true, false);
		Allele.Savant.addProduct(ItemManager.combs.getStackForType(CombType.PAPERY), 40);
		Allele.Savant.addSpecialty(ItemManager.miscResources.getStackForType(ResourceType.LORE_FRAGMENT), 5);
		Allele.Savant.setGenome(BeeGenomeManager.getTemplateSavant());
		learned.addMemberSpecies(Allele.Savant);
		breedingMgr.registerBeeTemplate(Allele.Savant.getGenome());
		
		IClassification stark = AlleleManager.alleleRegistry.createAndRegisterClassification(EnumClassLevel.GENUS, "torridus", "Torridus");
		stark.setParent(familyBee);
		
		Allele.Stark = new BeeSpecies("Stark", "\"These are unusually attracted to shards. This warrents further investigation.\"|Azanor, Master Thaumaturge",
				"torridae", stark, 0,
				0xCCCCCC, 0x999999, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, false);
		Allele.Stark.addProduct(ItemManager.combs.getStackForType(CombType.STARK), 10);
		Allele.Stark.setGenome(BeeGenomeManager.getTemplateStark());
		breedingMgr.registerBeeTemplate(Allele.Stark.getGenome());

		IClassification magical = AlleleManager.alleleRegistry.createAndRegisterClassification(EnumClassLevel.GENUS, "thaumis", "Thaumis");
		magical.setParent(familyBee);
		
		Allele.Air = new BeeSpecies("Aura", "\"They work so fast it's breathtaking.\"|Sengir, Mad Apiarist",
				"ventosa", magical, 0,
				0xD9D636, 0xA19E10, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, true);
		Allele.Air.addProduct(ItemManager.combs.getStackForType(CombType.AIRY), 9);
		Allele.Air.addSpecialty(new ItemStack(ItemManager.tcMiscResource, 1, TCMiscResource.QUICKSILVER.ordinal()), 2);
		Allele.Air.setGenome(BeeGenomeManager.getTemplateAir());
		breedingMgr.registerBeeTemplate(Allele.Air.getGenome());
		
		Allele.Fire = new BeeSpecies("Ignis", "Caution: Contents of hive extremely hot.|Warning label on Azanor's apiary",
				"praefervidus", magical, 0,
				0xE50B0B, 0x95132F, EnumTemperature.HOT, EnumHumidity.ARID,
				true, hideSpecies, true, true);
		Allele.Fire.addProduct(ItemManager.combs.getStackForType(CombType.FIREY), 15);
		Allele.Fire.addSpecialty(new ItemStack(Item.blazePowder), 8);
		Allele.Fire.setGenome(BeeGenomeManager.getTemplateFire());
		breedingMgr.registerBeeTemplate(Allele.Fire.getGenome());
		
		// Grab the Crystalline Pollen
		(forestryItem = ItemInterface.getItem("pollen")).setItemDamage(1);
		
		Allele.Water = new BeeSpecies("Aqua", "\"I tried to breed them once, but that was a wash.\"|MysteriousAges, Apprentice Thaumaturge",
				"umidus", magical, 0,
				0x36CFD9, 0x1054A1, EnumTemperature.NORMAL, EnumHumidity.DAMP,
				true, hideSpecies, true, true);
		Allele.Water.addProduct(ItemManager.combs.getStackForType(CombType.WATERY), 20);
		Allele.Water.addSpecialty(new ItemStack(Block.ice), 1).addSpecialty(forestryItem, 5);
		Allele.Water.setGenome(BeeGenomeManager.getTemplateWater());
		breedingMgr.registerBeeTemplate(Allele.Water.getGenome());
		
		Allele.Earth = new BeeSpecies("Solum", "\"You're really gonna dig these bees, but watch out - they bore quite easily.\"|MysteriousAges, Apprentice Comedian",
				"sordida", magical, 0,
				0x005100, 0x00a000, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, true);
		Allele.Earth.addProduct(ItemManager.combs.getStackForType(CombType.EARTHY), 30);
		Allele.Earth.addSpecialty(new ItemStack(ItemManager.tcMiscResource, 1, TCMiscResource.AMBER.ordinal()), 6);
		Allele.Earth.setGenome(BeeGenomeManager.getTemplateEarth());
		breedingMgr.registerBeeTemplate(Allele.Earth.getGenome());
		
		Allele.Infused = new BeeSpecies("Praecantatio", "Beekeeping is magic!|Apinomicon, Preface",
				"azanorius", magical, 0,
				0xaa32fc, 0x7A489E, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, true);
		Allele.Infused.addProduct(ItemManager.combs.getStackForType(CombType.INFUSED), 20);
		Allele.Infused.setGenome(BeeGenomeManager.getTemplateInfused());
		breedingMgr.registerBeeTemplate(Allele.Infused.getGenome());
		
		IClassification aware = AlleleManager.alleleRegistry.createAndRegisterClassification(EnumClassLevel.GENUS, "patibilis", "Patibilis");
		aware.setParent(familyBee);
		
		Allele.Aware = new BeeSpecies("Aware", "\"They can see into your soul!\"|Florastar, Expert Beekeeper",
				"sensibilis", aware, 0,
				0xb0092e9, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, false);
		Allele.Aware.addProduct(ItemManager.combs.getStackForType(CombType.INTELLECT), 18);
		Allele.Aware.setGenome(BeeGenomeManager.getTemplateAware());
		breedingMgr.registerBeeTemplate(Allele.Aware.getGenome());
		
		Allele.Vis = new BeeSpecies("Vis", "\"They can feel changes in the aura, but are not yet able to affect it.\"|Azanor, research notes",
				"arcanus saecula", aware, 0,
				0x004c99, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, false);
		Allele.Vis.addProduct(ItemManager.combs.getStackForType(CombType.INTELLECT), 25);
		Allele.Vis.setGenome(BeeGenomeManager.getTemplateVis());
		breedingMgr.registerBeeTemplate(Allele.Vis.getGenome());
		
		Allele.Pure = new BeeSpecies("Pure", "\"It's like a bee janitor!\"|MysteriousAges, Thaumaturge",
				"arcanus puritatem", aware, 0,
				0xb0092e9, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, false);
		Allele.Pure.addProduct(ItemManager.combs.getStackForType(CombType.INTELLECT), 20);
		Allele.Pure.setGenome(BeeGenomeManager.getTemplatePure());
		breedingMgr.registerBeeTemplate(Allele.Pure.getGenome());
		
		Allele.Flux = new BeeSpecies("Flux", "\"I thought they would help clean up, but it only makes things worse!\"|Kreicus, Apprentice Thaumaturge",
				"arcanus labe", aware, 0,
				0x004c99, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, false);
		Allele.Flux.addProduct(ItemManager.combs.getStackForType(CombType.INTELLECT), 20);
		Allele.Flux.setGenome(BeeGenomeManager.getTemplateFlux());
		breedingMgr.registerBeeTemplate(Allele.Flux.getGenome());
		
		Allele.Node = new BeeSpecies("Node", "Having undergone a freak mutation, these bees now attract magic to them.|Apinomicon",
				"conficiens", aware, 0,
				0xFFF266, 0xFF8CE9, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, false);
		Allele.Node.addProduct(ItemManager.combs.getStackForType(CombType.INTELLECT), 20);
		Allele.Node.setGenome(BeeGenomeManager.getTemplateNode());
		breedingMgr.registerBeeTemplate(Allele.Node.getGenome());
		
		IClassification malevolent = AlleleManager.alleleRegistry.createAndRegisterClassification(EnumClassLevel.GENUS, "malevolens", "Malevolens");
		malevolent.setParent(familyBee);
		
		Allele.Skulking = new BeeSpecies("Skulking", "These bees have become xenophobic and bad-tempered. Use caution.|Apinomicon",
				"malevolens", malevolent, 0,
				0x524827, malevolentBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, true);
		Allele.Skulking.addProduct(ItemManager.combs.getStackForType(CombType.SKULKING), 10);
		Allele.Skulking.setGenome(BeeGenomeManager.getTemplateSkulking());
		breedingMgr.registerBeeTemplate(Allele.Skulking.getGenome());
		
		Allele.Brainy = new BeeSpecies("Brainy", "Their combs may be fetid and foul-smelling, but their intelligence is well-developed.|Apinomicon",
				"cerebrum", malevolent, 0,
				0x83FF70, malevolentBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, true);
		Allele.Brainy.addProduct(ItemManager.combs.getStackForType(CombType.SKULKING), 10).addProduct(new ItemStack(Item.rottenFlesh), 6);
		Allele.Brainy.addSpecialty(new ItemStack(ItemManager.tcMiscResource,  1, TCMiscResource.ZOMBIE_BRAIN.ordinal()), 2);
		Allele.Brainy.setGenome(BeeGenomeManager.getTemplateBrainy());
		breedingMgr.registerBeeTemplate(Allele.Brainy.getGenome());
		
		// Grab the Silky Comb.
		(forestryItem = ItemInterface.getItem("beeComb")).setItemDamage(6);
		
		Allele.Gossamer = new BeeSpecies("Gossamer", "As they work, they seem to fade out from light for brief moments.|Apinomicon",
				"perlucidus", malevolent, 0,
				0x183f66, malevolentBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, true);
		Allele.Gossamer.addProduct(forestryItem, 15);
		Allele.Gossamer.setGenome(BeeGenomeManager.getTemplateGossamer());
		breedingMgr.registerBeeTemplate(Allele.Gossamer.getGenome());
		
		Allele.Wispy = new BeeSpecies("Wispy", "Their language is garbled and unintelligible. It is probable they are speaking to unnatural beings.|Apinomicon",
				"umbrabilis", malevolent, 0,
				0x9cb8d5, malevolentBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, false);
		Allele.Wispy.addProduct(forestryItem, 22);
		// Get silk wisp
		(forestryItem = ItemInterface.getItem("craftingMaterial")).setItemDamage(2);
		Allele.Wispy.addSpecialty(forestryItem, 4);
		Allele.Wispy.setGenome(BeeGenomeManager.getTemplateWispy());
		breedingMgr.registerBeeTemplate(Allele.Wispy.getGenome());
		
		Allele.Batty = new BeeSpecies("Batty", "They tend to attract bats to their hives through means unknown.|Apinomicon",
				"chiroptera", malevolent, 0,
				0x27350d, malevolentBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, true);
		Allele.Batty.addProduct(ItemManager.combs.getStackForType(CombType.SKULKING), 10);
		Allele.Batty.addSpecialty(new ItemStack(Item.gunpowder), 4);
		Allele.Batty.setGenome(BeeGenomeManager.getTemplateBatty());
		breedingMgr.registerBeeTemplate(Allele.Batty.getGenome());
		
		Allele.Ghastly = new BeeSpecies("Ghastly", "\"*sigh*... Really, Myst?\"|Taveria",
				"pallens", malevolent, 0,
				0xccccee, 0xbf877c, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, false);
		Allele.Ghastly.addProduct(ItemManager.combs.getStackForType(CombType.SKULKING), 8);
		Allele.Ghastly.addSpecialty(new ItemStack(Item.ghastTear), 2);
		Allele.Ghastly.setGenome(BeeGenomeManager.getTemplateGhastly());
		breedingMgr.registerBeeTemplate(Allele.Ghastly.getGenome());
	}

	private void setupBeeMutations()
	{
		// Mutations are registered with the BreedingManager in their constructor.
		
		BeeMutation.Esoteric = new BeeMutation(Allele.getBaseSpecies("Imperial"), Allele.getBaseSpecies("Demonic"), Allele.Esoteric, 10);
		BeeMutation.Esoteric1 = new BeeMutation(Allele.getBaseSpecies("Heroic"), Allele.getBaseSpecies("Demonic"), Allele.Esoteric, 25);
		BeeMutation.Mysterious = new BeeMutation(Allele.Esoteric, Allele.getBaseSpecies("Ended"), Allele.Mysterious, 10);
		BeeMutation.Arcane = new BeeMutation(Allele.Esoteric, Allele.Mysterious, Allele.Arcane, 8)
			.setMoonPhaseBonus(MoonPhase.WANING_CRESCENT, MoonPhase.WAXING_CRESCENT, 1.5f);
		BeeMutation.Charmed = new BeeMutation(Allele.getBaseSpecies("Diligent"), Allele.getBaseSpecies("Valiant"), Allele.Charmed, 20);
		BeeMutation.Enchanted = new BeeMutation(Allele.Charmed, Allele.getBaseSpecies("Valiant"), Allele.Enchanted, 8);
		BeeMutation.Supernatural = new BeeMutation(Allele.Charmed, Allele.Enchanted, Allele.Supernatural, 10)
			.setMoonPhaseBonus(MoonPhase.WAXING_GIBBOUS, MoonPhase.WANING_GIBBOUS, 1.5f);
		BeeMutation.Pupil = new BeeMutation(Allele.Arcane, Allele.Enchanted, Allele.Pupil, 10);
		BeeMutation.Scholarly = new BeeMutation(Allele.Pupil, Allele.Arcane, Allele.Scholarly, 8);
		BeeMutation.Savant = new BeeMutation(Allele.Pupil, Allele.Scholarly, Allele.Savant, 6);
		BeeMutation.Stark = new BeeMutation(Allele.Arcane, Allele.Supernatural, Allele.Stark, 8);
		
		BeeMutation.Aware = new BeeMutation(Allele.getBaseSpecies("Demonic"), Allele.getBaseSpecies("Edenic"), Allele.Aware, 12);
		BeeMutation.Vis = new BeeMutation(Allele.Aware, Allele.Arcane, Allele.Vis, 7)
		.setAuraNodeRequired(40);
		BeeMutation.Vis1 = new BeeMutation(Allele.Aware, Allele.Stark, Allele.Vis, 12)
		.setAuraNodeRequired(80);
		
		BeeMutation.Pure = new BeeMutation(Allele.Vis, Allele.getBaseSpecies("Edenic"), Allele.Pure, 5)
			.setAuraNodeTypeRequired(5, EnumNodeType.PURE).setMoonPhaseBonus(MoonPhase.NEW, MoonPhase.NEW, 1.6f);
		BeeMutation.Flux = new BeeMutation(Allele.Vis, Allele.getBaseSpecies("Edenic"), Allele.Flux, 5)
			.setAuraNodeTypeRequired(30, EnumNodeType.UNSTABLE).setMoonPhaseBonus(MoonPhase.FULL, MoonPhase.FULL, 1.6f);
				
		BeeMutation.Node = new BeeMutation(Allele.Vis, Allele.Vis, Allele.Node, 2)
			.setAuraNodeRequired(4).setMoonPhaseRestricted(MoonPhase.WANING_HALF, MoonPhase.WANING_HALF);
		BeeMutation.Node1 = new BeeMutation(Allele.Vis, Allele.Vis, Allele.Node, 2)
			.setAuraNodeRequired(4).setMoonPhaseRestricted(MoonPhase.WAXING_HALF, MoonPhase.WAXING_HALF);
		
		// Now we get into a little bit of branching...
		if (ThaumicBees.getInstanceConfig().ExtraBeesInstalled)
		{
			BeeMutation.Skulking = new BeeMutation(Allele.Mysterious, Allele.getExtraSpecies("desolate"), Allele.Skulking, 10);
			BeeMutation.Brainy = new BeeMutation(Allele.Skulking, Allele.getExtraSpecies("rotten"), Allele.Brainy, 12);
			BeeMutation.Gossamer = new BeeMutation(Allele.Skulking, Allele.getExtraSpecies("ancient"), Allele.Gossamer, 10);
			BeeMutation.Batty = new BeeMutation(Allele.Skulking, Allele.getExtraSpecies("rock"), Allele.Batty, 14);
			BeeMutation.Ghastly = new BeeMutation(Allele.Skulking, Allele.getExtraSpecies("creeper"), Allele.Ghastly, 13);
		}
		else
		{
			BeeMutation.Skulking = new BeeMutation(Allele.Mysterious, Allele.getBaseSpecies("Modest"), Allele.Skulking, 10);
			BeeMutation.Brainy = new BeeMutation(Allele.Skulking, Allele.getBaseSpecies("Sinister"), Allele.Brainy, 10);
			BeeMutation.Gossamer = new BeeMutation(Allele.Skulking, Allele.Supernatural, Allele.Gossamer, 10);
			BeeMutation.Batty = new BeeMutation(Allele.Skulking, Allele.getBaseSpecies("Frugal"), Allele.Batty, 11);
			BeeMutation.Ghastly = new BeeMutation(Allele.Skulking, Allele.getBaseSpecies("Austere"), Allele.Ghastly, 13);
		}
		BeeMutation.Gossamer.setMoonPhaseRestricted(MoonPhase.FULL, MoonPhase.WANING_CRESCENT);
		BeeMutation.Wispy = new BeeMutation(Allele.Gossamer, Allele.getBaseSpecies("Cultivated"), Allele.Wispy, 8);
		
	}
}
