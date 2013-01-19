package thaumicbees.compat;

import java.io.File;
import java.util.ArrayList;

import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumicbees.block.BlockManager;
import thaumicbees.item.ItemComb;
import thaumicbees.item.ItemManager;
import thaumicbees.item.ItemMiscResources;
import thaumicbees.item.ItemCapsule.Liquid;
import thaumicbees.item.ItemComb.CombType;
import thaumicbees.item.ItemDrop.DropType;
import thaumicbees.item.ItemWax.WaxType;
import thaumicbees.storage.BackpackDefinition;

import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.core.BlockInterface;
import forestry.api.core.ItemInterface;
import forestry.api.storage.BackpackManager;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
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
	
	public static void setupResearch()
	{
		// TODO: Get the path correct.
		//ThaumcraftApi.registerResearchXML(CommonProxy.TCBEES_RESEARCH + "/research.xml");
	}
	
	public static void setupAspects()
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
		ThaumcraftApi.registerObjectTag(ItemManager.miscResources.itemID, ItemMiscResources.ResourceType.LORE_FRAGMENT.ordinal(), tags);
		
		tags = new ObjectTags().add(EnumTag.MAGIC, 1).add(EnumTag.INSECT, 2).add(EnumTag.CONTROL, 1);
		ThaumcraftApi.registerObjectTag(ItemManager.combs.itemID, ItemComb.CombType.STARK.ordinal(), tags);
		ThaumcraftApi.registerObjectTag(ItemManager.combs.itemID, ItemComb.CombType.AIRY.ordinal(), tags);
		ThaumcraftApi.registerObjectTag(ItemManager.combs.itemID, ItemComb.CombType.FIREY.ordinal(), tags);
		ThaumcraftApi.registerObjectTag(ItemManager.combs.itemID, ItemComb.CombType.WATERY.ordinal(), tags);
		ThaumcraftApi.registerObjectTag(ItemManager.combs.itemID, ItemComb.CombType.EARTHY.ordinal(), tags);
		ThaumcraftApi.registerObjectTag(ItemManager.combs.itemID, ItemComb.CombType.INFUSED.ordinal(), tags);
		
		// Tagging capsules.
		tags = new ObjectTags().add(EnumTag.MAGIC, 2);
		ThaumcraftApi.registerComplexObjectTag(ItemManager.magicCapsule.itemID, -1, tags);

		tags = new ObjectTags().add(EnumTag.MAGIC, 2).add(EnumTag.VOID, 2);
		ThaumcraftApi.registerObjectTag(ItemManager.magicCapsule.itemID, 0, tags);
		tags = new ObjectTags().add(EnumTag.MAGIC, 2).add(EnumTag.WATER, 2);
		ThaumcraftApi.registerObjectTag(ItemManager.magicCapsule.itemID, Liquid.WATER.ordinal(), tags);
		tags = new ObjectTags().add(EnumTag.MAGIC, 2).add(EnumTag.FIRE, 2);
		ThaumcraftApi.registerObjectTag(ItemManager.magicCapsule.itemID, Liquid.LAVA.ordinal(), tags);
		
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
}
