package thaumicbees.bees;

import cpw.mods.fml.common.network.IGuiHandler;
import forestry.api.apiculture.*;
import forestry.api.core.*;
import forestry.api.genetics.AlleleManager;
import java.util.Random;

import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumicbees.item.ItemManager;

import net.minecraft.command.ICommand;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ThaumicBeesPlugin implements IPlugin
{

	private static int defaultBodyColour = 0xFF6E0D;
	private static boolean hideSpecies = false;

	public boolean isAvailable()
	{
		return true;
	}

	public void preInit()
	{
	}

	public void doInit()
	{
	}

	public void postInit()
	{
		setupBeeSpecies();
		setupBeeMutations();
	}

	private void setupBeeSpecies()
	{
		IBreedingManager breedingMgr = BeeManager.breedingManager;
		BeeBranch.Arcane = new BeeBranch("Arcane", "arcanis");
		BeeBranch.Supernatural = new BeeBranch("Supernatural", "coeleste");
		BeeBranch.Stark = new BeeBranch("Stark", "torridus", "Nothing of the mundane world remains in these bees.");
		BeeBranch.Elemental = new BeeBranch("Elemental", "praecantatio");
		BeeBranch.Scholarly = new BeeBranch("Scholarly", "scholasticus");
		
		BeeSpecies.Esoteric = new BeeSpecies("Esoteric", "", "secretiore", BeeBranch.Arcane, 0,
				0x001099, 0xcc763c, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, true);
		BeeSpecies.Esoteric.addProduct(new ItemStack(ItemManager.magicalComb), 5);
		BeeSpecies.Esoteric.setGene(EnumBeeChromosome.TEMPERATURE_TOLERANCE, AlleleManager.getAllele("toleranceBoth2"));
		breedingMgr.registerBeeTemplate(BeeSpecies.Esoteric.getGenome());
		
		BeeSpecies.Mysterious = new BeeSpecies("Mysterious", "", "mysticus", BeeBranch.Arcane, 0,
				0x762bc2, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, true);		
		BeeSpecies.Mysterious.setGene(EnumBeeChromosome.TEMPERATURE_TOLERANCE, AlleleManager.getAllele("toleranceBoth2"));
		BeeSpecies.Mysterious.setGene(EnumBeeChromosome.FERTILITY, AlleleManager.getAllele("fertilityNormal"));
		BeeSpecies.Mysterious.setGene(EnumBeeChromosome.FLOWERING, AlleleManager.getAllele("floweringMaximum"));
		breedingMgr.registerBeeTemplate(BeeSpecies.Mysterious.getGenome());
		
		BeeSpecies.Arcane = new BeeSpecies("Arcane", "", "arcanus", BeeBranch.Arcane, 0,
				0xd242df, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, true);
		BeeSpecies.Mysterious.setGene(EnumBeeChromosome.TEMPERATURE_TOLERANCE, AlleleManager.getAllele("toleranceBoth2"));
		BeeSpecies.Mysterious.setGene(EnumBeeChromosome.FLOWERING, AlleleManager.getAllele("floweringMaximum"));
		BeeSpecies.Arcane.setGene(EnumBeeChromosome.FERTILITY, AlleleManager.getAllele("fertilityLow"));
		breedingMgr.registerBeeTemplate(BeeSpecies.Arcane.getGenome());
		
		BeeSpecies.Charmed = new BeeSpecies("Charmed", "", "larvatus", BeeBranch.Supernatural, 0,
				0x48EEEC, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, true);
		BeeSpecies.Charmed.setGene(EnumBeeChromosome.NOCTURNAL, AlleleManager.getAllele("boolTrue"));
		BeeSpecies.Charmed.setGene(EnumBeeChromosome.SPEED, AlleleManager.getAllele("speedNorm"));
		BeeSpecies.Charmed.setGene(EnumBeeChromosome.FLOWERING, AlleleManager.getAllele("floweringSlowest"));
		breedingMgr.registerBeeTemplate(BeeSpecies.Charmed.getGenome());
		
		BeeSpecies.Enchanted = new BeeSpecies("Enchanted", "", "cantatus", BeeBranch.Supernatural, 0,
				0x18e726, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				false, hideSpecies, true, true);
		BeeSpecies.Charmed.setGene(EnumBeeChromosome.NOCTURNAL, AlleleManager.getAllele("boolTrue"));
		BeeSpecies.Charmed.setGene(EnumBeeChromosome.SPEED, AlleleManager.getAllele("speedNorm"));
		BeeSpecies.Charmed.setGene(EnumBeeChromosome.FLOWERING, AlleleManager.getAllele("floweringSlowest"));
		BeeSpecies.Charmed.setGene(EnumBeeChromosome.TEMPERATURE_TOLERANCE, AlleleManager.getAllele("toleranceBoth1"));
		breedingMgr.registerBeeTemplate(BeeSpecies.Enchanted.getGenome());
		
		BeeSpecies.Supernatural = new BeeSpecies("Supernatural", "", "coeleste", BeeBranch.Supernatural, 0,
				0x005614, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, true);
		BeeSpecies.Charmed.setGene(EnumBeeChromosome.NOCTURNAL, AlleleManager.getAllele("boolTrue"));
		BeeSpecies.Charmed.setGene(EnumBeeChromosome.SPEED, AlleleManager.getAllele("speedNorm"));
		BeeSpecies.Charmed.setGene(EnumBeeChromosome.FLOWERING, AlleleManager.getAllele("floweringSlowest"));
		BeeSpecies.Charmed.setGene(EnumBeeChromosome.TEMPERATURE_TOLERANCE, AlleleManager.getAllele("toleranceBoth2"));
		breedingMgr.registerBeeTemplate(BeeSpecies.Supernatural.getGenome());
		
		BeeSpecies.Stark = new BeeSpecies("Stark", "", "torridus", BeeBranch.Stark, 0,
				0xCCCCCC, 0x999999, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, false);
		breedingMgr.registerBeeTemplate(BeeSpecies.Stark.getGenome());
		
		BeeSpecies.Air = new BeeSpecies("Aura", "", "ventosa", BeeBranch.Elemental, 0,
				0xD9D636, 0xA19E10, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, true);
		BeeSpecies.Air.setGene(EnumBeeChromosome.SPEED, AlleleManager.getAllele("speedFastest"));
		BeeSpecies.Air.setGene(EnumBeeChromosome.LIFESPAN, AlleleManager.getAllele("lifespanShortened"));
		BeeSpecies.Air.setGene(EnumBeeChromosome.TERRITORY, AlleleManager.getAllele("territoryLargest"));
		BeeSpecies.Air.setGene(EnumBeeChromosome.TOLERANT_FLYER, AlleleManager.getAllele("boolTrue"));
		breedingMgr.registerBeeTemplate(BeeSpecies.Air.getGenome());
		
		BeeSpecies.Water = new BeeSpecies("Aqua", "", "umidus", BeeBranch.Elemental, 0,
				0x36CFD9, 0x1054A1, EnumTemperature.NORMAL, EnumHumidity.DAMP,
				true, hideSpecies, true, true);
		BeeSpecies.Water.setGene(EnumBeeChromosome.SPEED, AlleleManager.getAllele("speedFast"));
		BeeSpecies.Water.setGene(EnumBeeChromosome.LIFESPAN, AlleleManager.getAllele("lifespanElongated"));
		BeeSpecies.Water.setGene(EnumBeeChromosome.HUMIDITY_TOLERANCE, AlleleManager.getAllele("toleranceUp2"));
		BeeSpecies.Water.setGene(EnumBeeChromosome.TOLERANT_FLYER, AlleleManager.getAllele("boolTrue"));
		breedingMgr.registerBeeTemplate(BeeSpecies.Water.getGenome());
		
		BeeSpecies.Earth = new BeeSpecies("Solum", "", "sordida", BeeBranch.Elemental, 0,
				0xD9A136, 0x79450C, EnumTemperature.NORMAL, EnumHumidity.NORMAL,
				true, hideSpecies, true, true);
		BeeSpecies.Earth.setGene(EnumBeeChromosome.SPEED, AlleleManager.getAllele("speedFast"));
		BeeSpecies.Earth.setGene(EnumBeeChromosome.LIFESPAN, AlleleManager.getAllele("lifespanElongated"));
		BeeSpecies.Earth.setGene(EnumBeeChromosome.HUMIDITY_TOLERANCE, AlleleManager.getAllele("toleranceUp2"));
		BeeSpecies.Earth.setGene(EnumBeeChromosome.TOLERANT_FLYER, AlleleManager.getAllele("boolTrue"));
		breedingMgr.registerBeeTemplate(BeeSpecies.Earth.getGenome());
		
		BeeSpecies.Fire = new BeeSpecies("Ignis", "", "praefervidus", BeeBranch.Elemental, 0,
				0xE50B0B, 0x95132F, EnumTemperature.HELLISH, EnumHumidity.ARID,
				true, hideSpecies, true, true);
		BeeSpecies.Fire.setGene(EnumBeeChromosome.SPEED, AlleleManager.getAllele("speedFaster"));
		BeeSpecies.Fire.setGene(EnumBeeChromosome.LIFESPAN, AlleleManager.getAllele("lifespanNormal"));
		BeeSpecies.Fire.setGene(EnumBeeChromosome.TEMPERATURE_TOLERANCE, AlleleManager.getAllele("toleranceDown5"));
		BeeSpecies.Fire.setGene(EnumBeeChromosome.HUMIDITY_TOLERANCE, AlleleManager.getAllele("toleranceDown2"));
		BeeSpecies.Fire.setGene(EnumBeeChromosome.TOLERANT_FLYER, AlleleManager.getAllele("boolTrue"));
		breedingMgr.registerBeeTemplate(BeeSpecies.Fire.getGenome());
		
		BeeSpecies.Pupil = new BeeSpecies("Pupil", "", "disciplina", BeeBranch.Scholarly, 0,
				0xFFFF00, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.ARID,
				false, hideSpecies, true, true);
		BeeSpecies.Pupil.setGene(EnumBeeChromosome.SPEED, AlleleManager.getAllele("speedSlowest"));
		BeeSpecies.Pupil.setGene(EnumBeeChromosome.LIFESPAN, AlleleManager.getAllele("lifespanElongated"));
		BeeSpecies.Pupil.setGene(EnumBeeChromosome.CAVE_DWELLING, AlleleManager.getAllele("boolTrue"));
		breedingMgr.registerBeeTemplate(BeeSpecies.Pupil.getGenome());
		
		BeeSpecies.Scholarly = new BeeSpecies("Scholarly", "", "studiosis", BeeBranch.Scholarly, 0,
				0x6E0000, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.ARID,
				false, hideSpecies, true, false);
		BeeSpecies.Scholarly.setGene(EnumBeeChromosome.SPEED, AlleleManager.getAllele("speedSlower"));
		BeeSpecies.Pupil.setGene(EnumBeeChromosome.LIFESPAN, AlleleManager.getAllele("lifespanElongated"));
		BeeSpecies.Pupil.setGene(EnumBeeChromosome.CAVE_DWELLING, AlleleManager.getAllele("boolTrue"));
		breedingMgr.registerBeeTemplate(BeeSpecies.Scholarly.getGenome());
		
		BeeSpecies.Savant = new BeeSpecies("Savant", "", "philologus", BeeBranch.Scholarly, 0,
				0x6E1C6D, defaultBodyColour, EnumTemperature.NORMAL, EnumHumidity.ARID,
				true, hideSpecies, true, false);
		BeeSpecies.Pupil.setGene(EnumBeeChromosome.SPEED, AlleleManager.getAllele("speedNorm"));
		BeeSpecies.Pupil.setGene(EnumBeeChromosome.LIFESPAN, AlleleManager.getAllele("lifespanElongated"));
		BeeSpecies.Pupil.setGene(EnumBeeChromosome.CAVE_DWELLING, AlleleManager.getAllele("boolTrue"));
		breedingMgr.registerBeeTemplate(BeeSpecies.Savant.getGenome());
	}

	private void setupBeeMutations()
	{
		BeeMutation.Esoteric = new BeeMutation(BeeSpecies.getBaseSpecies("Imperial"), BeeSpecies.getBaseSpecies("Demonic"), BeeSpecies.Esoteric.getGenome(), 10, false);
		BeeMutation.Esoteric1 = new BeeMutation(BeeSpecies.getBaseSpecies("Heroic"), BeeSpecies.getBaseSpecies("Demonic"), BeeSpecies.Esoteric.getGenome(), 20, false);
		BeeMutation.Mysterious = new BeeMutation(BeeSpecies.Esoteric, BeeSpecies.getBaseSpecies("Ended"), BeeSpecies.Mysterious.getGenome(), 10, false);
		BeeMutation.Arcane = new BeeMutation(BeeSpecies.Esoteric, BeeSpecies.Mysterious, BeeSpecies.Arcane.getGenome(), 8, false);
		BeeMutation.Charmed = new BeeMutation(BeeSpecies.getBaseSpecies("Cultivated"), BeeSpecies.getBaseSpecies("Valiant"), BeeSpecies.Charmed.getGenome(), 20, false);
		BeeMutation.Enchanted = new BeeMutation(BeeSpecies.Charmed, BeeSpecies.getBaseSpecies("Valiant"), BeeSpecies.Enchanted.getGenome(), 8, false);
		BeeMutation.Supernatural = new BeeMutation(BeeSpecies.Charmed, BeeSpecies.Enchanted, BeeSpecies.Supernatural.getGenome(), 8, false);
		BeeMutation.Pupil = new BeeMutation(BeeSpecies.Arcane, BeeSpecies.Enchanted, BeeSpecies.Pupil.getGenome(), 10, false);
		BeeMutation.Scholarly = new BeeMutation(BeeSpecies.Pupil, BeeSpecies.Arcane, BeeSpecies.Scholarly.getGenome(), 8, false);
		BeeMutation.Savant = new BeeMutation(BeeSpecies.Pupil, BeeSpecies.Scholarly, BeeSpecies.Savant.getGenome(), 6, false);
		BeeMutation.Stark = new BeeMutation(BeeSpecies.Arcane, BeeSpecies.Supernatural, BeeSpecies.Stark.getGenome(), 8, false);
	}

	public static void setupBeeInfusions(World world)
	{
		try
		{
			ItemStack starkDrone = BeeSpecies.Stark.getBeeItem(world, EnumBeeType.DRONE);
			ItemStack starkPrincess = BeeSpecies.Stark.getBeeItem(world, EnumBeeType.PRINCESS);
			ItemStack airDrone = BeeSpecies.Air.getBeeItem(world, EnumBeeType.DRONE);
			ItemStack airPrincess = BeeSpecies.Air.getBeeItem(world, EnumBeeType.PRINCESS);
			ItemStack waterDrone = BeeSpecies.Water.getBeeItem(world, EnumBeeType.DRONE);
			ItemStack waterPrincess = BeeSpecies.Water.getBeeItem(world, EnumBeeType.PRINCESS);
			ItemStack earthDrone = BeeSpecies.Earth.getBeeItem(world, EnumBeeType.DRONE);
			ItemStack earthPrincess = BeeSpecies.Earth.getBeeItem(world, EnumBeeType.PRINCESS);
			ItemStack fireDrone = BeeSpecies.Fire.getBeeItem(world, EnumBeeType.DRONE);
			ItemStack firePrincess = BeeSpecies.Fire.getBeeItem(world, EnumBeeType.PRINCESS);
			
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
