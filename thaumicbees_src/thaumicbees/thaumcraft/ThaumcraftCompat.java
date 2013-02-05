package thaumicbees.thaumcraft;

import java.io.File;
import java.util.ArrayList;

import thaumcraft.api.EnumTag;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchList;
import thaumicbees.bees.BeeGenomeManager;
import thaumicbees.bees.genetics.Allele;
import thaumicbees.item.ItemComb;
import thaumicbees.item.ItemMiscResources;
import thaumicbees.item.types.CombType;
import thaumicbees.item.types.DropType;
import thaumicbees.item.types.LiquidType;
import thaumicbees.item.types.ResourceType;
import thaumicbees.item.types.WaxType;
import thaumicbees.main.CommonProxy;
import thaumicbees.main.Config;
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
	public static void getThaumcraftBlocks()
	{
		Config.tcPlant = Block.blocksList[ItemApi.getItem("blockCustomPlant", 0).itemID];
		Config.tcCandle = Block.blocksList[ItemApi.getItem("blockCandle", 0).itemID];
		Config.tcCrystal = Block.blocksList[ItemApi.getItem("blockCrystal", 0).itemID];
		Config.tcMarker = Block.blocksList[ItemApi.getItem("blockMarker", 0).itemID];
		Config.tcJar = Block.blocksList[ItemApi.getItem("blockJar", 0).itemID];
		Config.tcLog = Block.blocksList[ItemApi.getItem("blockMagicalLog", 0).itemID];
		Config.tcLeaf = Block.blocksList[ItemApi.getItem("blockMagicalLeaves", 0).itemID];
		Config.tcWarded = Block.blocksList[ItemApi.getItem("blockWarded", 0).itemID];
	}
	
	public static void getThaumcraftItems()
	{
		Config.tcFilledJar = Item.itemsList[ItemApi.getItem("itemJarFilled", 0).itemID];
		Config.tcMiscResource = Item.itemsList[ItemApi.getItem("itemResource", 0).itemID];
		Config.tcEssentiaBottle = Item.itemsList[ItemApi.getItem("itemEssence", 0).itemID];
		Config.tcShard = Item.itemsList[ItemApi.getItem("itemShard", 0).itemID];
		Config.tcGolem = Item.itemsList[ItemApi.getItem("itemGolemPlacer", 0).itemID];
		Config.tcWispEssence = Item.itemsList[ItemApi.getItem("itemWispEssence", 0).itemID];
	}
	
	public static void setupBackpacks()
	{
		if (ThaumicBees.getInstanceConfig().AddThaumcraftItemsToBackpacks)
		{
			// Add all shards and Thaumium to miner's backpack
			String ids = Config.tcShard.itemID + ":" + -1 + ";"
					+ Config.tcMiscResource.itemID + ":" + TCMiscResource.THAUMIUM.ordinal();
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "miner@" + ids);
			
			// All resources go into Thaumaturge's backpack
			ids = Config.tcMiscResource.itemID + ":" + -1 + ";"
					+ Config.tcShard.itemID + ":" + -1 + ";"
					+ Config.tcFilledJar.itemID + ":" + -1 + ";"
					+ Config.tcCrystal.blockID + ":" + -1 + ";"
					+ Config.tcJar.blockID + ":" + -1 + ";"
					+ Config.tcGolem.itemID + ":" + -1 + ";"
					+ Config.solidFlux.itemID + ":" + -1;
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "thaumaturge@" + ids);
			
			// Add some plants & saplings to Forester's
			ids = Config.tcPlant.blockID + ":" + "-1" + ";"
					+ Config.tcLeaf.blockID + ":" + -1 + ";"
					+ Config.tcLog.blockID + ":" + "-1";
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "forester@" + ids);
			
			// Add Wisp & brains to Hunter's
			ids = Config.tcWispEssence.itemID + ":" + -1 + ";"
					+ Config.tcMiscResource.itemID + ":" + TCMiscResource.ZOMBIE_BRAIN.ordinal();
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "hunter@" + ids);
			
			// Add Marker, warded stone, candle to builder
			ids = Config.tcCandle.blockID + ":" + -1 + ";"
					+ Config.tcMarker.blockID + ":" + -1 + ";"
					+ Config.tcWarded.blockID + ":" + -1;
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "builder@" + ids);
		}
	}
	
	public static void setupItemAspects()
	{
		ObjectTags tags;
		ItemStack itemStack;
		
		tags = new ObjectTags().add(EnumTag.MAGIC, 2);
		ThaumcraftApi.registerObjectTag(Config.wax.itemID, WaxType.MAGIC.ordinal(), tags);
		
		tags = new ObjectTags().add(EnumTag.MAGIC, 1);
		ThaumcraftApi.registerObjectTag(Config.drops.itemID, DropType.ENCHANTED.ordinal(), tags);
		tags = new ObjectTags().add(EnumTag.KNOWLEDGE, 1);
		ThaumcraftApi.registerObjectTag(Config.drops.itemID, DropType.INTELLECT.ordinal(), tags);
		
		tags = new ObjectTags().add(EnumTag.KNOWLEDGE, 3);
		ThaumcraftApi.registerObjectTag(Config.miscResources.itemID, ResourceType.LORE_FRAGMENT.ordinal(), tags);
		
		tags = new ObjectTags().add(EnumTag.MAGIC, 1).add(EnumTag.INSECT, 2).add(EnumTag.CONTROL, 1);
		ThaumcraftApi.registerObjectTag(Config.combs.itemID, CombType.STARK.ordinal(), tags);
		ThaumcraftApi.registerObjectTag(Config.combs.itemID, CombType.AIRY.ordinal(), tags);
		ThaumcraftApi.registerObjectTag(Config.combs.itemID, CombType.FIREY.ordinal(), tags);
		ThaumcraftApi.registerObjectTag(Config.combs.itemID, CombType.WATERY.ordinal(), tags);
		ThaumcraftApi.registerObjectTag(Config.combs.itemID, CombType.EARTHY.ordinal(), tags);
		ThaumcraftApi.registerObjectTag(Config.combs.itemID, CombType.INFUSED.ordinal(), tags);
		
		// Tagging capsules.
		tags = new ObjectTags().add(EnumTag.MAGIC, 2);
		ThaumcraftApi.registerObjectTag(Config.magicCapsule.itemID, -1, tags);

		tags = new ObjectTags().add(EnumTag.MAGIC, 2).add(EnumTag.VOID, 2);
		ThaumcraftApi.registerObjectTag(Config.magicCapsule.itemID, 0, tags);
		tags = new ObjectTags().add(EnumTag.MAGIC, 2).add(EnumTag.WATER, 2);
		ThaumcraftApi.registerObjectTag(Config.magicCapsule.itemID, LiquidType.WATER.ordinal(), tags);
		tags = new ObjectTags().add(EnumTag.MAGIC, 2).add(EnumTag.FIRE, 2);
		ThaumcraftApi.registerObjectTag(Config.magicCapsule.itemID, LiquidType.LAVA.ordinal(), tags);
		
		for (EnumTag tag : EnumTag.values())
		{
			if (tag == EnumTag.UNKNOWN)
			{
				continue;
			}
			tags = new ObjectTags().add(tag, 1);
			ThaumcraftApi.registerObjectTag(Config.solidFlux.itemID, tag.id, tags);
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
		ThaumcraftApi.registerObjectTag(Config.combs.itemID, -1, tags);
		
		itemStack = Config.combs.getStackForType(CombType.OCCULT);
		tags = new ObjectTags().add(EnumTag.INSECT, 2).add(EnumTag.TRAP, 2).add(EnumTag.MAGIC, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		itemStack = Config.combs.getStackForType(CombType.STARK);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("ambrosia");
		tags = new ObjectTags().add(EnumTag.LIFE, 2).add(EnumTag.HEAL, 4);
		
		// Forestry's wood		
		itemStack = BlockInterface.getBlock("log1");		
		tags = new ObjectTags().add(EnumTag.WOOD, 8);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags);
		itemStack = BlockInterface.getBlock("log2"); // Same tags
		ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags);
		itemStack = BlockInterface.getBlock("log3"); // Same tags.
		ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags);
		itemStack = BlockInterface.getBlock("log4"); // Still same tags.
		ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags);
		
		itemStack = ItemInterface.getItem("sapling");
		tags = new ObjectTags().add(EnumTag.PLANT, 4).add(EnumTag.WOOD, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags);
		
		// BEES!
		itemStack = ItemInterface.getItem("beeDroneGE");
		tags = new ObjectTags().add(EnumTag.INSECT, 1).add(EnumTag.FLIGHT, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags); // All drones
		
		tags = new ObjectTags(itemStack.itemID, -1).add(EnumTag.VALUABLE, 4); // Get drone tags & add valuable
		itemStack = ItemInterface.getItem("beePrincessGE");
		ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags); // All princesses.
		
		tags = new ObjectTags().add(EnumTag.VALUABLE, 8).add(EnumTag.INSECT, 2).add(EnumTag.FLIGHT, 4);
		itemStack = ItemInterface.getItem("beeQueenGE");
		ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags); //All queens.
	}
	
	public static void setupResearch()
	{
		ObjectTags tags;
		
		ResearchItem startNode = new ResearchItem("TBSTARTNODE", null, 10, 0, Config.miscResources.getStackForType(ResourceType.RESEARCH_StartNode))
			.setAutoUnlock()
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.INSECT, 9).add(EnumTag.MAGIC, 4).add(EnumTag.FLUX, 4);
		ResearchItem starkHint = new ResearchItem("STARKHINT", tags, 5, 2, ItemInterface.getItem("beeQueenGE"))
			.setParents(startNode)
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.INSECT, 15).add(EnumTag.MAGIC, 5).add(EnumTag.WATER, 5).add(EnumTag.EARTH, 5)
				.add(EnumTag.WIND, 5).add(EnumTag.FIRE, 5);
		ResearchItem beeInfusion = new ResearchItem("BEEINFUSION", tags, 5, 3, Config.miscResources.getStackForType(ResourceType.RESEARCH_BeeInfusion))
			.setParents(starkHint, ResearchList.getResearch("UTFT")).setHidden()
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.CROP, 20).add(EnumTag.EARTH, 20).add(EnumTag.WATER, 5);
		ResearchItem fertilizer = new ResearchItem("FERTILIZER", tags, 16, 0, ItemInterface.getItem("apatite"))
			.setParents(startNode)
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.WOOD, 10).add(EnumTag.TOOL, 6).add(EnumTag.INSECT, 15).add(EnumTag.BEAST, 2)
				.add(EnumTag.MAGIC, 5);
		ResearchItem  magicFrame = new ResearchItem("HIVEFRAME", tags, 10, -2, Config.hiveFrameMagic)
			.setParents(startNode)
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.TOOL, 5).add(EnumTag.INSECT, 10).add(EnumTag.ARMOR, 5).add(EnumTag.EXCHANGE, 6)
				.add(EnumTag.MAGIC, 8);
		ResearchItem  magicFrame2 = new ResearchItem("HIVEFRAME2", tags, 8, -2, Config.hiveFrameResilient)
			.setParents(magicFrame).setHidden()
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.TOOL, 4).add(EnumTag.INSECT, 10).add(EnumTag.LIFE, 5).add(EnumTag.EXCHANGE, 6)
				.add(EnumTag.HEAL, 2).add(EnumTag.FLOWER, 6);
		ResearchItem  gentleFrame = new ResearchItem("HIVEFRAMEGENTLE", tags, 12, -2, Config.hiveFrameGentle)
			.setParents(magicFrame).setHidden()
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.TOOL, 4).add(EnumTag.INSECT, 10).add(EnumTag.LIFE, 6).add(EnumTag.EXCHANGE, 8)
				.add(EnumTag.MAGIC, 8).add(EnumTag.MOTION, 4).add(EnumTag.FLESH, 4);
		ResearchItem  metabolicFrame = new ResearchItem("HIVEFRAMEMETA", tags, 9, -3, Config.hiveFrameMetabolic)
			.setParents(magicFrame).setHidden()
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.TOOL, 5).add(EnumTag.INSECT, 10).add(EnumTag.DEATH, 15).add(EnumTag.EXCHANGE, 6)
				.add(EnumTag.MAGIC, 8).add(EnumTag.POISON, 6);
		ResearchItem  necroticFrame = new ResearchItem("HIVEFRAMENECRO", tags, 11, -3, Config.hiveFrameNecrotic)
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

	public static void registerResearch()
	{
		ThaumcraftApi.registerResearchXML(CommonProxy.TCBEES_RESEARCH + "research.xml");
	}
}
