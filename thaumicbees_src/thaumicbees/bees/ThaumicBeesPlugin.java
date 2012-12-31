package thaumicbees.bees;

import cpw.mods.fml.common.network.IGuiHandler;
import forestry.api.apiculture.*;
import forestry.api.core.*;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.IClassification.EnumClassLevel;

import java.lang.reflect.Field;
import java.util.Random;

import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumicbees.bees.genetics.Allele;
import thaumicbees.bees.genetics.AlleleEffectCure;
import thaumicbees.bees.genetics.AlleleEffectPotion;
import thaumicbees.bees.genetics.AlleleFlower;
import thaumicbees.bees.genetics.BeeGenomeManager;
import thaumicbees.bees.genetics.BeeMutation;
import thaumicbees.bees.genetics.BeeSpecies;
import thaumicbees.item.ItemManager;
import thaumicbees.item.ItemComb.CombType;
import thaumicbees.item.ItemMiscResources.ResourceType;
import thaumicbees.main.ThaumicBees;

import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class ThaumicBeesPlugin implements IPlugin
{

	private static int defaultBodyColour = 0xFF6E0D;
	private static boolean hideSpecies = true;

	public boolean isAvailable()
	{
		return true;
	}

	public void preInit()
	{
	}

	public void doInit()
	{
		try
		{
			Field f = Class.forName("forestry.core.config.Config").getField("enableParticleFX");
			ThaumicBees.object.configFlags.DrawParticleEffects = f.getBoolean(null);
			
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
		setupBeeSpecies();
		setupBeeMutations();
	}

	private void setupBeeSpecies()
	{
		IBreedingManager breedingMgr = BeeManager.breedingManager;
		
		
		Allele.flowerBookshelf = new AlleleFlower("flowerBookshelf", new FlowerProviderBookshelf(), true);
		Allele.flowerThaumcraft = new AlleleFlower("flowerThaumcraftPlant", new FlowerProviderThaumcraftFlower(), false);
		
		Allele.cleansingEffect = new AlleleEffectCure("effectCurative", false);
		Allele.digSpeed = new AlleleEffectPotion("effectDigSpeed", "Mining", Potion.digSpeed, 7, false);
		Allele.moveSpeed = new AlleleEffectPotion("effectMoveSpeed", "Swiftness", Potion.moveSpeed, 5, false);
		
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
		
		Allele.Mysterious = new BeeSpecies("Mysterious", "<indecipherable scribblings>|Apinomicon",
				"mysticus", occult, 0,
				0x762bc2, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, true);
		Allele.Mysterious.addProduct(ItemManager.combs.getStackForType(CombType.OCCULT), 25);
		Allele.Mysterious.setGenome(BeeGenomeManager.getTemplateMysterious());
		occult.addMemberSpecies(Allele.Mysterious);
		breedingMgr.registerBeeTemplate(Allele.Mysterious.getGenome());
		
		Allele.Arcane = new BeeSpecies("Arcane", "The pinnacle of magical bees. It is likely these lead to more powerful mutations...|Apinomicon",
				"arcanus", occult, 0,
				0xd242df, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, true);
		Allele.Arcane.addProduct(ItemManager.combs.getStackForType(CombType.OCCULT), 30);
		Allele.Arcane.setGenome(BeeGenomeManager.getTemplateArcane());
		occult.addMemberSpecies(Allele.Arcane);
		breedingMgr.registerBeeTemplate(Allele.Arcane.getGenome());
		
		IClassification otherworldly = AlleleManager.alleleRegistry.createAndRegisterClassification(EnumClassLevel.GENUS, "coeleste", "Coeleste");
		otherworldly.setParent(familyBee);
		
		Allele.Charmed = new BeeSpecies("Charmed", "<indecipherable scribblings>|Apinomicon",
				"larvatus", otherworldly, 0,
				0x48EEEC, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, true);
		Allele.Charmed.addProduct(ItemManager.combs.getStackForType(CombType.OTHERWORLDLY), 20);
		Allele.Charmed.setGenome(BeeGenomeManager.getTemplateCharmed());
		otherworldly.addMemberSpecies(Allele.Charmed);
		breedingMgr.registerBeeTemplate(Allele.Charmed.getGenome());
		
		Allele.Enchanted = new BeeSpecies("Enchanted", "<indecipherable scribblings>|Apinomicon",
				"cantatus", otherworldly, 0,
				0x18e726, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, true);
		Allele.Enchanted.addProduct(ItemManager.combs.getStackForType(CombType.OTHERWORLDLY), 30);
		Allele.Enchanted.setGenome(BeeGenomeManager.getTemplateEnchanted());
		otherworldly.addMemberSpecies(Allele.Enchanted);
		breedingMgr.registerBeeTemplate(Allele.Enchanted.getGenome());
		
		Allele.Supernatural = new BeeSpecies("Supernatural", "<indecipherable scribblings>|Apinomicon",
				"coeleste", otherworldly, 0,
				0x005614, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, true);
		Allele.Supernatural.addProduct(ItemManager.combs.getStackForType(CombType.OTHERWORLDLY), 40);
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
		Allele.Scholarly.addSpecialty(ItemManager.miscResources.getStackForType(ResourceType.KNOWLEDGE_FRAGMENT), 2);
		Allele.Scholarly.setGenome(BeeGenomeManager.getTemplateScholarly());
		learned.addMemberSpecies(Allele.Scholarly);
		breedingMgr.registerBeeTemplate(Allele.Scholarly.getGenome());

		Allele.Savant = new BeeSpecies("Savant", "lim(x^(i / pi)/ log(e * 7 - ln(32/x^-pi))). Solve for honey.|Note found on Yorae's desk",
				"philologus", learned, 0,
				0x6E1C6D, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.ARID,
				true, hideSpecies, true, false);
		Allele.Savant.addProduct(ItemManager.combs.getStackForType(CombType.PAPERY), 40);
		Allele.Savant.addSpecialty(ItemManager.miscResources.getStackForType(ResourceType.KNOWLEDGE_FRAGMENT), 5);
		Allele.Savant.setGenome(BeeGenomeManager.getTemplateSavant());
		learned.addMemberSpecies(Allele.Savant);
		breedingMgr.registerBeeTemplate(Allele.Savant.getGenome());
		
		IClassification stark = AlleleManager.alleleRegistry.createAndRegisterClassification(EnumClassLevel.GENUS, "torridus", "Torridus");
		stark.setParent(familyBee);
		
		Allele.Stark = new BeeSpecies("Stark", "\"These are unusually attracted to shards. This warrents further investigation.\"|Azanor, Thaumaturge",
				"torridae", stark, 0,
				0xCCCCCC, 0x999999, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, false);
		Allele.Stark.addProduct(ItemManager.combs.getStackForType(CombType.STARK), 5);
		Allele.Stark.setGenome(BeeGenomeManager.getTemplateStark());
		breedingMgr.registerBeeTemplate(Allele.Stark.getGenome());

		IClassification magical = AlleleManager.alleleRegistry.createAndRegisterClassification(EnumClassLevel.GENUS, "", "");
		magical.setParent(familyBee);
		
		Allele.Air = new BeeSpecies("Aura", "They move like the wind itself, making observers feel like slackers.|Apinomicon",
				"ventosa", magical, 0,
				0xD9D636, 0xA19E10, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, true);
		Allele.Air.addProduct(ItemManager.combs.getStackForType(CombType.AIRY), 20);
		Allele.Air.addSpecialty(ItemManager.quicksilver, 10);
		Allele.Air.setGenome(BeeGenomeManager.getTemplateAir());
		breedingMgr.registerBeeTemplate(Allele.Air.getGenome());
		
		Allele.Fire = new BeeSpecies("Ignis", "Caution: Contents of hive extremely hot.|Warning label on Azanor's apiary",
				"praefervidus", magical, 0,
				0xE50B0B, 0x95132F, EnumTemperature.HOT, EnumHumidity.ARID,
				true, hideSpecies, true, true);
		Allele.Fire.addProduct(ItemManager.combs.getStackForType(CombType.FIREY), 20);
		Allele.Fire.addSpecialty(new ItemStack(Item.blazePowder), 8);
		Allele.Fire.setGenome(BeeGenomeManager.getTemplateFire());
		breedingMgr.registerBeeTemplate(Allele.Fire.getGenome());
		
		Allele.Water = new BeeSpecies("Aqua", "The purity of their produce is unparalleled.|Apinomicon",
				"umidus", magical, 0,
				0x36CFD9, 0x1054A1, EnumTemperature.NORMAL, EnumHumidity.DAMP,
				true, hideSpecies, true, true);
		Allele.Water.addProduct(ItemManager.combs.getStackForType(CombType.WATERY), 20);
		Allele.Water.addSpecialty(new ItemStack(Block.ice), 8);
		Allele.Water.setGenome(BeeGenomeManager.getTemplateWater());
		breedingMgr.registerBeeTemplate(Allele.Water.getGenome());
		
		Allele.Earth = new BeeSpecies("Solum", "<indecipherable scribblings>|Apinomicon",
				"sordida", magical, 0,
				0x005100, 0x00a000, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, true);
		Allele.Earth.addProduct(ItemManager.combs.getStackForType(CombType.EARTHY), 30);
		Allele.Earth.addSpecialty(ItemManager.amber, 10);
		Allele.Earth.setGenome(BeeGenomeManager.getTemplateEarth());
		breedingMgr.registerBeeTemplate(Allele.Earth.getGenome());
	}

	private void setupBeeMutations()
	{
		// Mutations are registered with the BreedingManager in their constructor.
		
		BeeMutation.Esoteric = new BeeMutation(Allele.getBaseSpecies("Imperial"), Allele.getBaseSpecies("Demonic"), Allele.Esoteric.getGenome(), 10, false);
		BeeMutation.Esoteric1 = new BeeMutation(Allele.getBaseSpecies("Heroic"), Allele.getBaseSpecies("Demonic"), Allele.Esoteric.getGenome(), 25, false);
		BeeMutation.Mysterious = new BeeMutation(Allele.Esoteric, Allele.getBaseSpecies("Ended"), Allele.Mysterious.getGenome(), 10, false);
		BeeMutation.Arcane = new BeeMutation(Allele.Esoteric, Allele.Mysterious, Allele.Arcane.getGenome(), 8, false);
		BeeMutation.Charmed = new BeeMutation(Allele.getBaseSpecies("Cultivated"), Allele.getBaseSpecies("Valiant"), Allele.Charmed.getGenome(), 20, false);
		BeeMutation.Enchanted = new BeeMutation(Allele.Charmed, Allele.getBaseSpecies("Valiant"), Allele.Enchanted.getGenome(), 8, false);
		BeeMutation.Supernatural = new BeeMutation(Allele.Charmed, Allele.Enchanted, Allele.Supernatural.getGenome(), 8, false);
		BeeMutation.Pupil = new BeeMutation(Allele.Arcane, Allele.Enchanted, Allele.Pupil.getGenome(), 10, false);
		BeeMutation.Scholarly = new BeeMutation(Allele.Pupil, Allele.Arcane, Allele.Scholarly.getGenome(), 8, false);
		BeeMutation.Savant = new BeeMutation(Allele.Pupil, Allele.Scholarly, Allele.Savant.getGenome(), 6, false);
		BeeMutation.Stark = new BeeMutation(Allele.Arcane, Allele.Supernatural, Allele.Stark.getGenome(), 8, false);
	}

	public static void setupBeeInfusions(World world)
	{
		try
		{
			ItemStack starkDrone = Allele.Stark.getBeeItem(world, EnumBeeType.DRONE);
			ItemStack starkPrincess = Allele.Stark.getBeeItem(world, EnumBeeType.PRINCESS);
			ItemStack airDrone = Allele.Air.getBeeItem(world, EnumBeeType.DRONE);
			ItemStack airPrincess = Allele.Air.getBeeItem(world, EnumBeeType.PRINCESS);
			ItemStack waterDrone = Allele.Water.getBeeItem(world, EnumBeeType.DRONE);
			ItemStack waterPrincess = Allele.Water.getBeeItem(world, EnumBeeType.PRINCESS);
			ItemStack earthDrone = Allele.Earth.getBeeItem(world, EnumBeeType.DRONE);
			ItemStack earthPrincess = Allele.Earth.getBeeItem(world, EnumBeeType.PRINCESS);
			ItemStack fireDrone = Allele.Fire.getBeeItem(world, EnumBeeType.DRONE);
			ItemStack firePrincess = Allele.Fire.getBeeItem(world, EnumBeeType.PRINCESS);
			
			Item shard = (Item) Class.forName("thaumcraft.common.Config").getDeclaredField("itemShard").get(null);
			
			ObjectTags tags = (new ObjectTags()).add(EnumTag.WIND, 40).add(EnumTag.MOTION, 20);
			ThaumcraftApi.addShapelessInfusionCraftingRecipe("UTFT", 100, tags, airDrone, new Object[]
					{ starkDrone, new ItemStack(shard, 1, 0) });
			ThaumcraftApi.addShapelessInfusionCraftingRecipe("UTFT", 100, tags, airPrincess, new Object[] 
					{ starkPrincess, new ItemStack(shard, 1, 0) });
			
			tags = (new ObjectTags()).add(EnumTag.FIRE, 40).add( EnumTag.POWER, 20);
			ThaumcraftApi.addShapelessInfusionCraftingRecipe("UTFT", 100, tags, fireDrone, new Object[] 
					{ starkDrone, new ItemStack(shard, 1, 1) });
			ThaumcraftApi.addShapelessInfusionCraftingRecipe("UTFT", 100, tags, firePrincess, new Object[] 
					{ starkDrone, new ItemStack(shard, 1, 1) }); 
			
			tags = (new ObjectTags()).add(EnumTag.WATER, 40).add( EnumTag.COLD, 20); 
			ThaumcraftApi.addShapelessInfusionCraftingRecipe("UTFT", 100, tags, waterDrone, new Object[] 
					{ starkDrone, new ItemStack(shard, 1, 2) }); 
			ThaumcraftApi.addShapelessInfusionCraftingRecipe("UTFT", 100, tags, waterPrincess, new Object[] 
					{ starkDrone, new ItemStack(shard, 1, 2) }); 
			
			tags = (new ObjectTags()).add(EnumTag.EARTH, 40).add( EnumTag.ROCK, 20);
			ThaumcraftApi.addShapelessInfusionCraftingRecipe("UTFT", 100, tags, earthDrone, new Object[] 
					{ starkDrone, new ItemStack(shard, 1, 3) }); 
			ThaumcraftApi.addShapelessInfusionCraftingRecipe("UTFT", 100, tags, earthPrincess, new Object[] 
					{ starkDrone, new ItemStack(shard, 1, 3) });
		}
		catch (Exception ex)
		{
			System.out.println("Reflection failed. Could not get Thaumcraft shard. ):");
		}
	}
	
	public String getDescription()
	{
		return "ThaumicBees";
	}

	public void generateSurface(World world, Random random, int i, int j)
	{
	}

	public IGuiHandler getGuiHandler()
	{
		return null;
	}

	public IPacketHandler getPacketHandler()
	{
		return null;
	}

	public IPickupHandler getPickupHandler()
	{
		return null;
	}

	public IResupplyHandler getResupplyHandler()
	{
		return null;
	}

	public ISaveEventHandler getSaveEventHandler()
	{
		return null;
	}

	public IOreDictionaryHandler getDictionaryHandler()
	{
		return null;
	}

	public ICommand[] getConsoleCommands()
	{
		return null;
	}

}
