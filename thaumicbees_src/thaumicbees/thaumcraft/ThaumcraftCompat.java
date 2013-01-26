package thaumicbees.thaumcraft;

import java.io.File;
import java.util.ArrayList;

import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchList;
import thaumicbees.bees.genetics.Allele;
import thaumicbees.bees.genetics.BeeGenomeManager;
import thaumicbees.block.BlockManager;
import thaumicbees.item.ItemComb;
import thaumicbees.item.ItemManager;
import thaumicbees.item.ItemMiscResources;
import thaumicbees.item.types.CombType;
import thaumicbees.item.types.DropType;
import thaumicbees.item.types.LiquidType;
import thaumicbees.item.types.ResourceType;
import thaumicbees.item.types.WaxType;
import thaumicbees.main.CommonProxy;
import thaumicbees.main.ThaumicBees;
import thaumicbees.storage.BackpackDefinition;

import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.core.BlockInterface;
import forestry.api.core.ItemInterface;
import forestry.api.storage.BackpackManager;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Property;
import net.minecraftforge.common.Configuration;

public class ThaumcraftCompat
{
	
	public static void parseThaumcraftConfig(String configPath)
	{
		Configuration tcConfig = new Configuration(new File(configPath + "/Thaumcraft.cfg"));
		Property p;
		
		tcConfig.load();
		
		// Load blocks out of the Thaumcraft config to get their IDs;
		p = tcConfig.getBlock("BlockCustomPlant", 0);
		BlockManager.tcPlant = Block.blocksList[p.getInt()];
		p = tcConfig.getBlock("BlockCandle", 0);
		BlockManager.tcCandle = Block.blocksList[ p.getInt()];
		p = tcConfig.getBlock("BlockCrystal", 0);
		BlockManager.tcCrystal = Block.blocksList[p.getInt()];
		p = tcConfig.getBlock("BlockMarker", 0);
		BlockManager.tcMarker = Block.blocksList[p.getInt()];
		p = tcConfig.getBlock("BlockJar", 0);
		BlockManager.tcJar = Block.blocksList[p.getInt()];
		p = tcConfig.getBlock("BlockMagicalLeaves", 0);
		BlockManager.tcLeaf = Block.blocksList[p.getInt()];
		p = tcConfig.getBlock("BlockMagicalLog", 0);
		BlockManager.tcLog = Block.blocksList[p.getInt()];
		p = tcConfig.getBlock("BlockSecure", 0);
		BlockManager.tcWarded = Block.blocksList[p.getInt()];
		
		// Load items out of the Thaumcraft config to get their IDs.
		p = tcConfig.getItem("ItemEssence", 0);
		ItemManager.tcEssentiaBottle = Item.itemsList[p.getInt() + 256];
		p = tcConfig.getItem("ItemResource", 0);
		ItemManager.tcMiscResource = Item.itemsList[p.getInt() + 256];
		p = tcConfig.getItem("ItemShard", 0);
		ItemManager.tcShard = Item.itemsList[p.getInt() + 256];
		p = tcConfig.getItem("BlockJarFilledItem", 0);
		ItemManager.tcFilledJar = Item.itemsList[p.getInt() + 256];
		p = tcConfig.getItem("ItemGolemPlacer", 0);
		ItemManager.tcGolem = Item.itemsList[p.getInt() + 256];
		p = tcConfig.getItem("ItemEssence", 0);
		ItemManager.tcWispEssence = Item.itemsList[p.getInt() + 256];
	}
	
	public static void setupBackpacks()
	{
		if (ThaumicBees.getInstanceConfig().AddThaumcraftItemsToBackpacks)
		{
			// Add all shards and Thaumium to miner's backpack
			String ids = ItemManager.tcShard.itemID + ":" + -1 + ";"
					+ ItemManager.tcMiscResource.itemID + ":" + TCMiscResource.THAUMIUM.ordinal();
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "miner@" + ids);
			
			// All resources go into Thaumaturge's backpack
			ids = ItemManager.tcMiscResource.itemID + ":" + -1 + ";"
					+ ItemManager.tcShard.itemID + ":" + -1 + ";"
					+ ItemManager.tcFilledJar.itemID + ":" + -1 + ";"
					+ BlockManager.tcCrystal.blockID + ":" + -1 + ";"
					+ BlockManager.tcJar.blockID + ":" + -1 + ";"
					+ ItemManager.tcGolem.itemID + ":" + -1;
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "thaumaturge@" + ids);
			
			// Add some plants & saplings to Forester's
			ids = BlockManager.tcPlant.blockID + ":" + "-1" + ";"
					+ BlockManager.tcLeaf.blockID + ":" + -1 + ";"
					+ BlockManager.tcLog.blockID + ":" + "-1";
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "forester@" + ids);
			
			// Add Wisp & brains to Hunter's
			ids = ItemManager.tcWispEssence.itemID + ":" + -1 + ";"
					+ ItemManager.tcMiscResource.itemID + ":" + TCMiscResource.ZOMBIE_BRAIN.ordinal();
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "hunter@" + ids);
			
			// Add Marker, warded stone, candle to builder
			ids = BlockManager.tcCandle.blockID + ":" + -1 + ";"
					+ BlockManager.tcMarker.blockID + ":" + -1 + ";"
					+ BlockManager.tcWarded.blockID + ":" + -1;
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "builder@" + ids);
		}
	}
	
	public static void setupItemAspects()
	{
		ObjectTags tags;
		ItemStack itemStack;
		
		tags = new ObjectTags().add(EnumTag.MAGIC, 2);
		ThaumcraftApi.registerObjectTag(ItemManager.wax.itemID, WaxType.MAGIC.ordinal(), tags);
		
		tags = new ObjectTags().add(EnumTag.MAGIC, 1);
		ThaumcraftApi.registerObjectTag(ItemManager.drops.itemID, DropType.ENCHANTED.ordinal(), tags);
		tags = new ObjectTags().add(EnumTag.KNOWLEDGE, 1);
		ThaumcraftApi.registerObjectTag(ItemManager.drops.itemID, DropType.INTELLECT.ordinal(), tags);
		
		tags = new ObjectTags().add(EnumTag.KNOWLEDGE, 3);
		ThaumcraftApi.registerObjectTag(ItemManager.miscResources.itemID, ResourceType.LORE_FRAGMENT.ordinal(), tags);
		
		tags = new ObjectTags().add(EnumTag.MAGIC, 1).add(EnumTag.INSECT, 2).add(EnumTag.CONTROL, 1);
		ThaumcraftApi.registerObjectTag(ItemManager.combs.itemID, CombType.STARK.ordinal(), tags);
		ThaumcraftApi.registerObjectTag(ItemManager.combs.itemID, CombType.AIRY.ordinal(), tags);
		ThaumcraftApi.registerObjectTag(ItemManager.combs.itemID, CombType.FIREY.ordinal(), tags);
		ThaumcraftApi.registerObjectTag(ItemManager.combs.itemID, CombType.WATERY.ordinal(), tags);
		ThaumcraftApi.registerObjectTag(ItemManager.combs.itemID, CombType.EARTHY.ordinal(), tags);
		ThaumcraftApi.registerObjectTag(ItemManager.combs.itemID, CombType.INFUSED.ordinal(), tags);
		
		// Tagging capsules.
		tags = new ObjectTags().add(EnumTag.MAGIC, 2);
		ThaumcraftApi.registerComplexObjectTag(ItemManager.magicCapsule.itemID, -1, tags);

		tags = new ObjectTags().add(EnumTag.MAGIC, 2).add(EnumTag.VOID, 2);
		ThaumcraftApi.registerObjectTag(ItemManager.magicCapsule.itemID, 0, tags);
		tags = new ObjectTags().add(EnumTag.MAGIC, 2).add(EnumTag.WATER, 2);
		ThaumcraftApi.registerObjectTag(ItemManager.magicCapsule.itemID, LiquidType.WATER.ordinal(), tags);
		tags = new ObjectTags().add(EnumTag.MAGIC, 2).add(EnumTag.FIRE, 2);
		ThaumcraftApi.registerObjectTag(ItemManager.magicCapsule.itemID, LiquidType.LAVA.ordinal(), tags);
		
		for (EnumTag tag : EnumTag.values())
		{
			if (tag == EnumTag.UNKNOWN)
			{
				continue;
			}
			tags = new ObjectTags().add(tag, 1);
			ThaumcraftApi.registerObjectTag(ItemManager.solidFlux.itemID, tag.id, tags);
		}
		
		// Tag some Forestry stuff.
		// Pulsating propolis
		itemStack = ItemInterface.getItem("propolis");
		tags = new ObjectTags().add(EnumTag.ELDRITCH, 1).add(EnumTag.CONTROL, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, 2	, tags);
		itemStack = ItemInterface.getItem("craftingMaterial");
		// 0: pulsatingDust
		// 1: "pulsatingMesh";
		tags = new ObjectTags().add(EnumTag.ELDRITCH, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, 1, tags);
		// 2: "silkWisp";
		// 3: "wovenSilk";
		// 4: "dissipationCharge";
		// 5: "iceShard";
		tags = new ObjectTags().add(EnumTag.COLD, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, 5, tags);
		
		itemStack = ItemInterface.getItem("apatite");
		tags = new ObjectTags().add(EnumTag.CRYSTAL, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("impregnatedCasing");
		tags = new ObjectTags().add(EnumTag.WOOD, 58).add(EnumTag.PLANT, 8);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("stickImpregnated");
		tags = new ObjectTags().add(EnumTag.WOOD, 8).add(EnumTag.PLANT, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("peat");
		tags = new ObjectTags().add(EnumTag.POWER, 2).add(EnumTag.PLANT, 1).add(EnumTag.EARTH, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("ash");
		tags = new ObjectTags().add(EnumTag.DESTRUCTION, 1).add(EnumTag.EXCHANGE, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("honeyDrop");
		tags = new ObjectTags().add(EnumTag.EXCHANGE, 2).add(EnumTag.LIFE, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("beeswax");
		tags = new ObjectTags().add(EnumTag.INSECT, 1).add(EnumTag.CONTROL, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		itemStack = ItemInterface.getItem("refractoryWax");
		tags = new ObjectTags().add(EnumTag.FIRE, 3);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		itemStack = ItemInterface.getItem("phosphor");
		tags = new ObjectTags().add(EnumTag.FIRE, 2).add(EnumTag.DESTRUCTION, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);

		itemStack = ItemInterface.getItem("waxCapsule");
		tags = new ObjectTags().add(EnumTag.CONTROL, 1).add(EnumTag.VOID, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		itemStack = ItemInterface.getItem("waxCapsuleWater");
		tags = new ObjectTags().add(EnumTag.CONTROL, 1).add(EnumTag.VOID, 1).add(EnumTag.WATER, 4);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("refractoryEmpty");
		tags = new ObjectTags().add(EnumTag.FIRE, 1).add(EnumTag.CONTROL, 1).add(EnumTag.VOID, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		itemStack = ItemInterface.getItem("refractoryWater");
		tags = new ObjectTags().add(EnumTag.FIRE, 1).add(EnumTag.VOID, 1).add(EnumTag.WATER, 4);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		itemStack = ItemInterface.getItem("refractoryLava");
		tags = new ObjectTags().add(EnumTag.VOID, 1).add(EnumTag.FIRE, 7).add(EnumTag.WATER, 1).add(EnumTag.ROCK, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);

		itemStack = ItemInterface.getItem("canEmpty");
		tags = new ObjectTags().add(EnumTag.METAL, 1).add(EnumTag.VOID, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		itemStack = ItemInterface.getItem("canWater");
		tags = new ObjectTags().add(EnumTag.METAL, 1).add(EnumTag.VOID, 1).add(EnumTag.WATER, 4);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		itemStack = ItemInterface.getItem("canLava");
		tags = new ObjectTags().add(EnumTag.METAL, 1).add(EnumTag.VOID, 1).add(EnumTag.FIRE, 6).add(EnumTag.WATER, 1).add(EnumTag.ROCK, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("beeComb");
		tags = new ObjectTags().add(EnumTag.INSECT, 2).add(EnumTag.TRAP, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags); // ALL combs plox.
		ThaumcraftApi.registerObjectTag(ItemManager.combs.itemID, -1, tags);
		
		itemStack = ItemManager.combs.getStackForType(CombType.OCCULT);
		tags = new ObjectTags().add(EnumTag.INSECT, 2).add(EnumTag.TRAP, 2).add(EnumTag.MAGIC, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		itemStack = ItemManager.combs.getStackForType(CombType.STARK);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("ambrosia");
		tags = new ObjectTags().add(EnumTag.LIFE, 2).add(EnumTag.HEAL, 4);
		
		// Forestry's wood		
		itemStack = BlockInterface.getBlock("log1");		
		tags = new ObjectTags().add(EnumTag.WOOD, 8);
		ThaumcraftApi.registerComplexObjectTag(itemStack.itemID, -1, tags);
		itemStack = BlockInterface.getBlock("log2"); // Same tags
		ThaumcraftApi.registerComplexObjectTag(itemStack.itemID, -1, tags);
		itemStack = BlockInterface.getBlock("log3"); // Same tags.
		ThaumcraftApi.registerComplexObjectTag(itemStack.itemID, -1, tags);
		itemStack = BlockInterface.getBlock("log4"); // Still same tags.
		ThaumcraftApi.registerComplexObjectTag(itemStack.itemID, -1, tags);
		
		itemStack = ItemInterface.getItem("sapling");
		tags = new ObjectTags().add(EnumTag.PLANT, 4).add(EnumTag.WOOD, 2);
		ThaumcraftApi.registerComplexObjectTag(itemStack.itemID, -1, tags);
		
		// BEES!
		itemStack = ItemInterface.getItem("beeDroneGE");
		tags = new ObjectTags().add(EnumTag.INSECT, 1).add(EnumTag.FLIGHT, 2);
		ThaumcraftApi.registerComplexObjectTag(itemStack.itemID, -1, tags); // All drones
		
		tags = new ObjectTags(itemStack.itemID, -1).add(EnumTag.VALUABLE, 4); // Get drone tags & add valuable
		itemStack = ItemInterface.getItem("beePrincessGE");
		ThaumcraftApi.registerComplexObjectTag(itemStack.itemID, -1, tags); // All princesses.
		
		tags = new ObjectTags().add(EnumTag.VALUABLE, 8).add(EnumTag.INSECT, 2).add(EnumTag.FLIGHT, 4);
		itemStack = ItemInterface.getItem("beeQueenGE");
		ThaumcraftApi.registerComplexObjectTag(itemStack.itemID, -1, tags); //All queens.
	}
	
	public static void setupResearch()
	{
		ObjectTags tags = new ObjectTags().add(EnumTag.WOOD, 2).add(EnumTag.INSECT, 2);
		ResearchItem startNode = new ResearchItem("TBSTARTNODE", tags, 10, 0, Block.sapling)
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.INSECT, 9).add(EnumTag.MAGIC, 2).add(EnumTag.FLUX, 2);
		ResearchItem starkHint = new ResearchItem("STARKHINT", tags, 5, 2, ItemInterface.getItem("beeQueenGE"))
			.setParents(startNode)
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.INSECT, 15).add(EnumTag.MAGIC, 5).add(EnumTag.WATER, 5).add(EnumTag.EARTH, 5)
				.add(EnumTag.WIND, 5).add(EnumTag.FIRE, 5);
		ResearchItem beeInfusion = new ResearchItem("BEEINFUSION", tags, 5, 3, ItemManager.miscResources.getStackForType(ResourceType.RESEARCH_BeeInfusion))
			.setParents(starkHint, ResearchList.getResearch("UTFT")).setHidden()
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.CROP, 20).add(EnumTag.EARTH, 10).add(EnumTag.WATER, 2);
		ResearchItem fertilizer = new ResearchItem("FERTILIZER", tags, 16, 0, ItemInterface.getItem("apatite"))
			.setParents(startNode)
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.WOOD, 10).add(EnumTag.TOOL, 5).add(EnumTag.INSECT, 15).add(EnumTag.BEAST, 2)
				.add(EnumTag.MAGIC, 5);
		ResearchItem  magicFrame = new ResearchItem("HIVEFRAME", tags, 10, -2, ItemManager.hiveFrameMagic)
			.setParents(startNode)
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.TOOL, 5).add(EnumTag.INSECT, 15).add(EnumTag.ARMOR, 5).add(EnumTag.EXCHANGE, 6)
				.add(EnumTag.MAGIC, 8);
		ResearchItem  magicFrame2 = new ResearchItem("HIVEFRAME2", tags, 8, -2, ItemManager.hiveFrameResilient)
			.setParents(magicFrame).setHidden()
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.TOOL, 5).add(EnumTag.INSECT, 15).add(EnumTag.LIFE, 5).add(EnumTag.EXCHANGE, 6)
				.add(EnumTag.HEAL, 2).add(EnumTag.FLOWER, 6);
		ResearchItem  gentleFrame = new ResearchItem("HIVEFRAMEGENTLE", tags, 12, -2, ItemManager.hiveFrameGentle)
			.setParents(magicFrame).setHidden()
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.TOOL, 5).add(EnumTag.INSECT, 15).add(EnumTag.LIFE, 12).add(EnumTag.EXCHANGE, 6)
				.add(EnumTag.MAGIC, 8).add(EnumTag.MOTION, 5);
		ResearchItem  metabolicFrame = new ResearchItem("HIVEFRAMEMETA", tags, 9, -3, ItemManager.hiveFrameMetabolic)
			.setParents(magicFrame).setHidden()
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.TOOL, 5).add(EnumTag.INSECT, 15).add(EnumTag.DEATH, 15).add(EnumTag.EXCHANGE, 6)
				.add(EnumTag.MAGIC, 8).add(EnumTag.POISON, 6);
		ResearchItem  necroticFrame = new ResearchItem("HIVEFRAMENECRO", tags, 11, -3, ItemManager.hiveFrameNecrotic)
			.setParents(magicFrame).setHidden()
			.registerResearchItem();

		/*ItemStack iconStack = BlockInterface.getBlock("soil");
		tags = new ObjectTags().add(EnumTag.EARTH, 10).add(EnumTag.WOOD, 4);
		ResearchItem humus = new ResearchItem("HUMUS", tags, 9, -1, iconStack)
			.setParents(startNode)
			.registerResearchItem();
		
		iconStack = BlockInterface.getBlock("soil");
		iconStack.setItemDamage(1);
		tags = new ObjectTags().add(EnumTag.EARTH, 10).add(EnumTag.WATER, 4).add(EnumTag.CROP, 4);
		ResearchItem bogEarth = new ResearchItem("BOGEARTH", tags, 9, -1, iconStack)
			.setParents(startNode)
			.registerResearchItem();*/
	}
	
	public static void setupCrafting()
	{
		ItemStack output = ItemManager.miscResources.getStackForType(ResourceType.EXTENDED_FERTILIZER);
		ItemStack inputA = ItemInterface.getItem("apatite");
		ObjectTags tags = (new ObjectTags()).add(EnumTag.CROP, 12);
		output.stackSize = 2;
		ThaumcraftApi.addShapelessInfusionCraftingRecipe("FERTILIZER", "FERTILIZER", 5, tags, output, new Object[] {
				inputA
		});

		output = new ItemStack(ItemManager.hiveFrameMagic);
		inputA = ItemInterface.getItem("frameUntreated");
		tags = new ObjectTags().add(EnumTag.WOOD, 4).add(EnumTag.INSECT, 8);
		ThaumcraftApi.addShapelessInfusionCraftingRecipe("HIVEFRAME", "FRAMEMAGIC", 50, tags, output, new Object[] {
				inputA
		});
		
		output = new ItemStack(ItemManager.hiveFrameResilient);
		tags = new ObjectTags().add(EnumTag.WOOD, 12).add(EnumTag.INSECT, 8).add(EnumTag.EXCHANGE, 12);
		ThaumcraftApi.addInfusionCraftingRecipe("HIVEFRAME2", "FRAMERESILIENT", 50, tags, output, new Object[] {
				" i ", "ifi", " i ",
				'f', ItemManager.hiveFrameMagic,
				'i', Item.ingotIron
		});
		
		output = new ItemStack(ItemManager.hiveFrameGentle);
		tags = new ObjectTags().add(EnumTag.WOOD, 4).add(EnumTag.INSECT, 8).add(EnumTag.HEAL, 12);
		ThaumcraftApi.addInfusionCraftingRecipe("HIVEFRAMEGENTLE", "FRAMEGENTLE", 50, tags, output, new Object[] {
				"www", "wFw", "www",
				'F', inputA,
				'w', ItemInterface.getItem("beeswax")
				
		});
		
		output = new ItemStack(ItemManager.hiveFrameMetabolic);
		tags = new ObjectTags().add(EnumTag.WOOD, 4).add(EnumTag.INSECT, 8).add(EnumTag.LIFE, 8).add(EnumTag.FLESH, 8)
				.add(EnumTag.EXCHANGE, 8);
		ThaumcraftApi.addShapelessInfusionCraftingRecipe("HIVEFRAMEMETA", "FRAMEMETABOLIC", 50, tags, output, new Object[] {
				Item.magmaCream,
				inputA
		});
		
		output = new ItemStack(ItemManager.hiveFrameNecrotic);
		tags = new ObjectTags().add(EnumTag.WOOD, 4).add(EnumTag.INSECT, 8).add(EnumTag.EXCHANGE, 12).add(EnumTag.DEATH, 10)
				.add(EnumTag.POISON, 5);
		ThaumcraftApi.addInfusionCraftingRecipe("HIVEFRAMENECRO", "FRAMENECROTIC", 50, tags, output, new Object[] {
				" S ", "SxS", " S ",
				'S', Item.rottenFlesh,
				'x', inputA
		});
	}

	public static void setupBeeInfusions(World world)
	{
		ItemStack drone = BeeGenomeManager.getBeeNBTForSpecies(Allele.Stark, EnumBeeType.DRONE);
		ItemStack princess = BeeGenomeManager.getBeeNBTForSpecies(Allele.Stark, EnumBeeType.PRINCESS);
		ItemStack airDrone = Allele.Air.getBeeItem(world, EnumBeeType.DRONE);
		ItemStack airPrincess = Allele.Air.getBeeItem(world, EnumBeeType.PRINCESS);
		ItemStack waterDrone = Allele.Water.getBeeItem(world, EnumBeeType.DRONE);
		ItemStack waterPrincess = Allele.Water.getBeeItem(world, EnumBeeType.PRINCESS);
		ItemStack earthDrone = Allele.Earth.getBeeItem(world, EnumBeeType.DRONE);
		ItemStack earthPrincess = Allele.Earth.getBeeItem(world, EnumBeeType.PRINCESS);
		ItemStack fireDrone = Allele.Fire.getBeeItem(world, EnumBeeType.DRONE);
		ItemStack firePrincess = Allele.Fire.getBeeItem(world, EnumBeeType.PRINCESS);
		ItemStack magicDrone = Allele.Infused.getBeeItem(world, EnumBeeType.DRONE);
		ItemStack magicPrincess = Allele.Infused.getBeeItem(world, EnumBeeType.PRINCESS);
		
		String researchKey = "BEEINFUSION";
		
		ObjectTags tags = (new ObjectTags()).add(EnumTag.WIND, 40).add(EnumTag.MOTION, 20);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION1", airDrone,
				new ItemStack[] { drone, new ItemStack(ItemManager.tcShard, 1, TCShardType.AIR.ordinal())},
				100, tags, Allele.Stark, EnumBeeChromosome.SPECIES);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION2", airPrincess,
				new ItemStack[] { princess , new ItemStack(ItemManager.tcShard, 1, TCShardType.AIR.ordinal()) },
				100, tags, Allele.Stark, EnumBeeChromosome.SPECIES);
		
		tags = (new ObjectTags()).add(EnumTag.FIRE, 40).add( EnumTag.POWER, 20);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION3", fireDrone, new Object[] 
				{ drone, new ItemStack(ItemManager.tcShard, 1, TCShardType.FIRE.ordinal()) },
				100, tags, Allele.Stark, EnumBeeChromosome.SPECIES);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION4", firePrincess, new Object[] 
				{ princess, new ItemStack(ItemManager.tcShard, 1, TCShardType.FIRE.ordinal()) },
				100, tags, Allele.Stark, EnumBeeChromosome.SPECIES); 
		
		tags = (new ObjectTags()).add(EnumTag.WATER, 40).add( EnumTag.COLD, 20); 
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION5", waterDrone, new Object[] 
				{ drone, new ItemStack(ItemManager.tcShard, 1, TCShardType.WATER.ordinal()) },
				100, tags, Allele.Stark, EnumBeeChromosome.SPECIES); 
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION6", waterPrincess, new Object[] 
				{ princess, new ItemStack(ItemManager.tcShard, 1, TCShardType.WATER.ordinal()) },
				100, tags, Allele.Stark, EnumBeeChromosome.SPECIES); 
		
		tags = (new ObjectTags()).add(EnumTag.EARTH, 40).add( EnumTag.ROCK, 20);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION7", earthDrone, new Object[] 
				{ drone, new ItemStack(ItemManager.tcShard, 1, TCShardType.EARTH.ordinal()) },
				100, tags, Allele.Stark, EnumBeeChromosome.SPECIES); 
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION8", earthPrincess, new Object[] 
				{ princess, new ItemStack(ItemManager.tcShard, 1, TCShardType.EARTH.ordinal()) },
				100, tags, Allele.Stark, EnumBeeChromosome.SPECIES);
		
		tags = new ObjectTags().add(EnumTag.MAGIC, 40).add(EnumTag.FLUX, 20);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION9", magicDrone, new Object[]
				{ drone, new ItemStack(ItemManager.tcShard, 1, TCShardType.MAGIC.ordinal()) },
				100, tags, Allele.Stark, EnumBeeChromosome.SPECIES);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION0", magicPrincess, new Object[]
				{ princess, new ItemStack(ItemManager.tcShard, 1, TCShardType.MAGIC.ordinal()) },
				100, tags, Allele.Stark, EnumBeeChromosome.SPECIES);
	}

	public static void registerResearch()
	{
		ThaumcraftApi.registerResearchXML(CommonProxy.TCBEES_RESEARCH + "research.xml");
	}
}
