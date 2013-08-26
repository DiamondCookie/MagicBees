package magicbees.main.utils.compat;

import java.util.HashMap;

import magicbees.bees.BeeGenomeManager;
import magicbees.bees.BeeSpecies;
import magicbees.item.ItemThaumiumGrafter;
import magicbees.item.ItemThaumiumScoop;
import magicbees.item.types.CombType;
import magicbees.item.types.DropType;
import magicbees.item.types.HiveFrameType;
import magicbees.item.types.FluidType;
import magicbees.item.types.PollenType;
import magicbees.item.types.PropolisType;
import magicbees.item.types.ResourceType;
import magicbees.item.types.WaxType;
import magicbees.main.CommonProxy;
import magicbees.main.Config;
import magicbees.main.MagicBees;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchList;
import cpw.mods.fml.common.event.FMLInterModComms;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.core.BlockInterface;
import forestry.api.core.ItemInterface;

public class ThaumcraftHelper
{
	public enum MiscResource
	{
		ALUMENTUM,
		NITOR,
		THAUMIUM,
		QUICKSILVER,
		MAGIC_TALLOW,
		ZOMBIE_BRAIN,
		AMBER,
		ENCHANTED_FABRIC,
		FLUX_FILTER,
		KNOWLEDGE_FRAGMENT,
		MIRRORED_GLASS,
		;
	}
	
	public enum NuggetType
	{
		IRON,
		COPPER,
		TIN,
		SILVER,
		LEAD,
		QUICKSILVER,						_6,_7,_8,_9,_10,_11,_12,_13,_14,_15,
		NATIVE_IRON,
		NATIVE_COPPER,
		NATIVE_TIN,
		NATIVE_SILVER,
		NATIVE_LEAD,						_21,_22,_23,_24,_25,_26,_27,_28,_29,_30,
		NATIVE_GOLD,
		;
	}
	
	public enum ShardType
	{
		AIR,
		FIRE,
		WATER,
		EARTH,
		MAGIC,
		DULL,
		;
	}
	
	public enum Entity
	{
		BRAINY_ZOMBIE("entBrainyZombie", "EntityBrainyZombie"),
		GIANT_BRAINY_ZOMBIE("entGiantBrainyZombie", "EntityGiantBrainyZombie"),
		WISP("entWisp", "EntityWisp"),
		FIREBAT("entFirebat", "EntityFireBat"),
		;
		
		private static String packageName = "thaumcraft.common.entities.monster.";
		
		public String entityID;
		private String className;
		
		private Entity(String id, String clazz)
		{
			this.entityID = id;
			this.className = clazz;
		}
		
		public String getClassName()
		{
			return packageName + this.className;
		}
	}
	
	public enum BlockPlant
	{
		GREATWOOD_SAPLING,
		SILVERWOOD_SAPLING,
		SHIMMERLEAF,
		CINDERPEARL;
	}
	
	public enum TreeType
	{
		GREATWOOD,
		SILVERWOOD,
		;
	}
	
	private static boolean isThaumcraftPresent = false;
	public static final String Name = "Thaumcraft";
	
	public static boolean isActive()
	{
		return isThaumcraftPresent;
	}
	
	public static void init()
	{
		if (cpw.mods.fml.common.Loader.isModLoaded(Name))
		{
			isThaumcraftPresent = true;
			
			registerResearchXML();
		}
		else
		{
			// Switch off TC-dependant items.
			ResourceType.LORE_FRAGMENT.setHidden();
		}
	}
	
	public static void getBlocks()
	{
		if (isActive())
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
	}
	
	public static void getItems()
	{
		if (isActive())
		{
			Config.tcFilledJar = ItemApi.getItem("itemJarFilled", 0).getItem();
			Config.tcMiscResource = ItemApi.getItem("itemResource", 0).getItem();
			Config.tcEssentiaBottle = ItemApi.getItem("itemEssence", 0).getItem();
			Config.tcShard = ItemApi.getItem("itemShard", 0).getItem();
			Config.tcGolem = ItemApi.getItem("itemGolemPlacer", 0).getItem();
			Config.tcWispEssence = ItemApi.getItem("itemWispEssence", 0).getItem();
			Config.tcNuggets = ItemApi.getItem("itemNugget", 0).getItem();
			Config.tcNuggetChicken = ItemApi.getItem("itemNuggetChicken", 0).getItem();
			Config.tcNuggetBeef = ItemApi.getItem("itemNuggetBeef", 0).getItem();
			Config.tcNuggetPork = ItemApi.getItem("itemNuggetPork", 0).getItem();
		}
	}

	public static void addItemsToBackpack()
	{
		if (isActive() && MagicBees.getConfig().AddThaumcraftItemsToBackpacks)
		{
			// Add all shards and Thaumium to miner's backpack
			String ids = Config.tcShard.itemID + ":" + -1 + ";"
					+ Config.tcMiscResource.itemID + ":" + ThaumcraftHelper.MiscResource.THAUMIUM.ordinal();
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "miner@" + ids);
			
			// All resources go into Thaumaturge's backpack
			ids = Config.tcMiscResource.itemID + ":" + -1 + ";"
					+ Config.tcShard.itemID + ":" + -1 + ";"
					+ Config.tcFilledJar.itemID + ":" + -1 + ";"
					+ Config.tcCrystal.blockID + ":" + -1 + ";"
					+ Config.tcJar.blockID + ":" + -1 + ";"
					+ Config.tcGolem.itemID + ":" + -1 + ";";
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "thaumaturge@" + ids);
			
			// Add some plants & saplings to Forester's
			ids = Config.tcPlant.blockID + ":" + "-1" + ";"
					+ Config.tcLeaf.blockID + ":" + -1 + ";"
					+ Config.tcLog.blockID + ":" + "-1";
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "forester@" + ids);
			
			// Add Wisp & brains to Hunter's
			ids = Config.tcWispEssence.itemID + ":" + -1 + ";"
					+ Config.tcMiscResource.itemID + ":" + ThaumcraftHelper.MiscResource.ZOMBIE_BRAIN.ordinal();
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
		if (isActive())
		{
			setupItemAspectsThaumicBees();
			setupItemAspectsForestry();
			if (ExtraBeesHelper.isActive())
			{
				setupItemAspectsExtraBees();
			}
		}
	}
	
	private static void setupItemAspectsThaumicBees()
	{
		ObjectTags tags;
		ItemStack itemStack;
		
		tags = new ObjectTags().add(EnumTag.WOOD, 1);
		ThaumcraftApi.registerObjectTag(Config.planksWood.blockID, -1, tags);
		
		tags = new ObjectTags().add(EnumTag.MAGIC, 2);
		ThaumcraftApi.registerObjectTag(Config.wax.itemID, WaxType.MAGIC.ordinal(), tags);
		
		tags = new ObjectTags().add(EnumTag.MAGIC, 1);
		ThaumcraftApi.registerObjectTag(Config.drops.itemID, DropType.ENCHANTED.ordinal(), tags);
		tags = new ObjectTags().add(EnumTag.KNOWLEDGE, 1);
		ThaumcraftApi.registerObjectTag(Config.drops.itemID, DropType.INTELLECT.ordinal(), tags);
		
		tags = new ObjectTags().add(EnumTag.ELDRITCH, 2);
		ThaumcraftApi.registerObjectTag(Config.pollen.itemID, PollenType.UNUSUAL.ordinal(), tags);
		tags = new ObjectTags().add(EnumTag.TIME, 2);
		ThaumcraftApi.registerObjectTag(Config.pollen.itemID, PollenType.PHASED.ordinal(), tags);
		
		tags = new ObjectTags().add(EnumTag.CONTROL, 1);
		ThaumcraftApi.registerObjectTag(Config.propolis.itemID, -1, tags);
		tags = new ObjectTags(Config.propolis.itemID, -1).add(EnumTag.MAGIC, 1);
		ThaumcraftApi.registerObjectTag(Config.propolis.itemID, PropolisType.STARK.ordinal(), tags);
		tags = new ObjectTags(Config.propolis.itemID, -1).add(EnumTag.MAGIC, 1).add(EnumTag.WIND, 3);
		ThaumcraftApi.registerObjectTag(Config.propolis.itemID, PropolisType.AIR.ordinal(), tags);
		tags = new ObjectTags(Config.propolis.itemID, -1).add(EnumTag.MAGIC, 1).add(EnumTag.FIRE, 3);
		ThaumcraftApi.registerObjectTag(Config.propolis.itemID, PropolisType.FIRE.ordinal(), tags);
		tags = new ObjectTags(Config.propolis.itemID, -1).add(EnumTag.MAGIC, 1).add(EnumTag.WATER, 3);
		ThaumcraftApi.registerObjectTag(Config.propolis.itemID, PropolisType.WATER.ordinal(), tags);
		tags = new ObjectTags(Config.propolis.itemID, -1).add(EnumTag.MAGIC, 1).add(EnumTag.EARTH, 3);
		ThaumcraftApi.registerObjectTag(Config.propolis.itemID, PropolisType.EARTH.ordinal(), tags);
		tags = new ObjectTags(Config.propolis.itemID, -1).add(EnumTag.MAGIC, 4);
		ThaumcraftApi.registerObjectTag(Config.propolis.itemID, PropolisType.INFUSED.ordinal(), tags);
		
		tags = new ObjectTags().add(EnumTag.KNOWLEDGE, 3);
		ThaumcraftApi.registerObjectTag(Config.miscResources.itemID, ResourceType.LORE_FRAGMENT.ordinal(), tags);
		
		tags = new ObjectTags().add(EnumTag.INSECT, 2).add(EnumTag.TRAP, 2);
		ThaumcraftApi.registerObjectTag(Config.combs.itemID, -1, tags);
		tags = new ObjectTags(Config.combs.itemID, -1).add(EnumTag.MAGIC, 2);
		ThaumcraftApi.registerObjectTag(Config.combs.itemID, CombType.OCCULT.ordinal(), tags);
		ThaumcraftApi.registerObjectTag(Config.combs.itemID, CombType.OTHERWORLDLY.ordinal(), tags);
		ThaumcraftApi.registerObjectTag(Config.combs.itemID, CombType.HARMONIZING.ordinal(), tags);
		tags = new ObjectTags(Config.combs.itemID, -1).add(EnumTag.MAGIC, 4);
		ThaumcraftApi.registerObjectTag(Config.combs.itemID, CombType.INFUSED.ordinal(), tags);
		tags = new ObjectTags(Config.combs.itemID, -1).add(EnumTag.MAGIC, 2).add(EnumTag.MOTION, 2);
		ThaumcraftApi.registerObjectTag(Config.combs.itemID, CombType.AIRY.ordinal(), tags);
		tags = new ObjectTags(Config.combs.itemID, -1).add(EnumTag.MAGIC, 2).add(EnumTag.POWER, 2);
		ThaumcraftApi.registerObjectTag(Config.combs.itemID, CombType.FIREY.ordinal(), tags);
		tags = new ObjectTags(Config.combs.itemID, -1).add(EnumTag.MAGIC, 2).add(EnumTag.COLD, 2);
		ThaumcraftApi.registerObjectTag(Config.combs.itemID, CombType.WATERY.ordinal(), tags);
		tags = new ObjectTags(Config.combs.itemID, -1).add(EnumTag.MAGIC, 2).add(EnumTag.ROCK, 2);
		ThaumcraftApi.registerObjectTag(Config.combs.itemID, CombType.EARTHY.ordinal(), tags);
		
		// Tagging capsules.
		tags = new ObjectTags().add(EnumTag.VOID, 2).add(EnumTag.MAGIC, 2);
		ThaumcraftApi.registerComplexObjectTag(Config.magicCapsule.itemID, FluidType.EMPTY.ordinal(), tags);

		tags = new ObjectTags(Config.magicCapsule.itemID, FluidType.EMPTY.ordinal())
			.add(EnumTag.WATER, 8);
		ThaumcraftApi.registerObjectTag(Config.magicCapsule.itemID, FluidType.WATER.ordinal(), tags);
		tags = new ObjectTags(Config.magicCapsule.itemID, FluidType.EMPTY.ordinal())
			.add(EnumTag.FIRE, 12).add(EnumTag.ROCK, 4);
		ThaumcraftApi.registerObjectTag(Config.magicCapsule.itemID, FluidType.LAVA.ordinal(), tags);
		
		for (EnumTag tag : EnumTag.values())
		{
			if (tag == EnumTag.UNKNOWN)
			{
				continue;
			}
			tags = new ObjectTags().add(tag, 1);
			ThaumcraftApi.registerObjectTag(Config.solidFlux.itemID, tag.id, tags);
		}
	}
		
	private static void setupItemAspectsForestry()
	{
		ObjectTags tags;
		ItemStack itemStack;

		itemStack = BlockInterface.getBlock("candle");
		tags = new ObjectTags().add(EnumTag.LIGHT, 1).add(EnumTag.CONTROL, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = BlockInterface.getBlock("glass");
		tags = new ObjectTags(Block.glass.blockID, 0);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags);

		// Pulsating propolis
		itemStack = ItemInterface.getItem("propolis");
		tags = new ObjectTags().add(EnumTag.CONTROL, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags);
		tags = new ObjectTags(itemStack.itemID, -1).add(EnumTag.ELDRITCH, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Propolis.PULSATING.ordinal(), tags);
		
		itemStack = ItemInterface.getItem("pollen");
		tags = new ObjectTags().add(EnumTag.FLOWER, 1).add(EnumTag.PLANT, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Pollen.NORMAL.ordinal(), tags);
		tags = new ObjectTags(itemStack.itemID, ForestryHelper.Pollen.NORMAL.ordinal()).add(EnumTag.COLD, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Pollen.CRYSTALLINE.ordinal(), tags);
		
		itemStack = ItemInterface.getItem("craftingMaterial");		
		tags = new ObjectTags().add(EnumTag.ELDRITCH, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.CraftingMaterial.PULSATING_MESH.ordinal(), tags);
		tags = new ObjectTags().add(EnumTag.INSECT, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.CraftingMaterial.SILK_WISP.ordinal(), tags);
		tags = new ObjectTags().add(EnumTag.INSECT, 4).add(EnumTag.CLOTH, 5);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.CraftingMaterial.WOVEN_SILK.ordinal(), tags);
		tags = new ObjectTags().add(EnumTag.COLD, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.CraftingMaterial.ICE_SHARD.ordinal(), tags);
		tags = new ObjectTags().add(EnumTag.VALUABLE, 1).add(EnumTag.PLANT, 1).add(EnumTag.INSECT, 2)
				.add(EnumTag.CONTROL, 2).add(EnumTag.WOOD, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.CraftingMaterial.SCENTED_PANELING.ordinal(), tags);
		
		itemStack = ItemInterface.getItem("beealyzer");
		tags = new ObjectTags().add(EnumTag.VALUABLE, 3).add(EnumTag.MECHANISM, 3).add(EnumTag.METAL, 19)
				.add(EnumTag.POWER, 8).add(EnumTag.TOOL, 18);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("treealyzer");
		tags = new ObjectTags().add(EnumTag.POWER, 3).add(EnumTag.PURE, 3).add(EnumTag.LIFE, 6)
				.add(EnumTag.TOOL, 12).add(EnumTag.METAL, 19);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("apatite");
		tags = new ObjectTags().add(EnumTag.CRYSTAL, 2).add(EnumTag.POWER, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("impregnatedCasing");
		tags = new ObjectTags().add(EnumTag.WOOD, 28).add(EnumTag.EXCHANGE, 8);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("stickImpregnated");
		tags = new ObjectTags().add(EnumTag.WOOD, 3).add(EnumTag.EXCHANGE, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("peat");
		tags = new ObjectTags().add(EnumTag.POWER, 2).add(EnumTag.PLANT, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("ash");
		tags = new ObjectTags().add(EnumTag.DESTRUCTION, 1).add(EnumTag.EXCHANGE, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("honeyDrop");
		tags = new ObjectTags().add(EnumTag.EXCHANGE, 2).add(EnumTag.LIFE, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		itemStack = ItemInterface.getItem("honeydew");
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("royalJelly");
		tags = new ObjectTags().add(EnumTag.INSECT, 2).add(EnumTag.POWER, 1).add(EnumTag.VALUABLE, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("honeyPot");
		tags = new ObjectTags().add(EnumTag.LIFE, 2);
		ThaumcraftApi.registerComplexObjectTag(itemStack.itemID, itemStack.getItemDamage(),	tags);
		
		itemStack = ItemInterface.getItem("ambrosia");
		tags = new ObjectTags().add(EnumTag.HEAL, 4);
		ThaumcraftApi.registerComplexObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("beeswax");
		tags = new ObjectTags().add(EnumTag.CONTROL, 1);
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
		ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags);
		tags = new ObjectTags(itemStack.itemID, -1).add(EnumTag.FIRE, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Comb.SIMMERING.ordinal(), tags);
		tags = new ObjectTags(itemStack.itemID, -1).add(EnumTag.COLD, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Comb.FROZEN.ordinal(), tags);
		tags = new ObjectTags(itemStack.itemID, -1).add(EnumTag.CROP, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Comb.WHEATEN.ordinal(), tags);
		tags = new ObjectTags(itemStack.itemID, -1).add(EnumTag.FUNGUS, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Comb.MOSSY.ordinal(), tags);
		
		itemStack = ItemInterface.getItem("ambrosia");
		tags = new ObjectTags().add(EnumTag.LIFE, 2).add(EnumTag.HEAL, 4);
		
		// Forestry's wood		
		itemStack = BlockInterface.getBlock("log1");		
		tags = new ObjectTags().add(EnumTag.WOOD, 4);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags);
		itemStack = BlockInterface.getBlock("log2"); // Same tags
		ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags);
		itemStack = BlockInterface.getBlock("log3"); // Same tags.
		ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags);
		itemStack = BlockInterface.getBlock("log4"); // Still same tags.
		ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags);
		
		itemStack = ItemInterface.getItem("sapling");
		tags = new ObjectTags().add(EnumTag.PLANT, 4).add(EnumTag.WOOD, 1);
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
		
		itemStack = ItemInterface.getItem("circuitboards");
		tags = new ObjectTags().add(EnumTag.CRYSTAL, 2).add(EnumTag.METAL, 5).add(EnumTag.MECHANISM, 10)
				.add(EnumTag.POWER, 10);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.CircuitBoard.BASIC.ordinal(), tags);
		tags = new ObjectTags().add(EnumTag.MECHANISM, 10).add(EnumTag.POWER, 10).add(EnumTag.METAL, 14)
				.add(EnumTag.CONTROL, 2).add(EnumTag.EXCHANGE, 2);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.CircuitBoard.ENHANCED.ordinal(), tags);
		tags = new ObjectTags().add(EnumTag.MECHANISM, 10).add(EnumTag.POWER, 10).add(EnumTag.METAL, 14);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.CircuitBoard.REFINED.ordinal(), tags);
		tags = new ObjectTags().add(EnumTag.MECHANISM, 10).add(EnumTag.POWER, 10).add(EnumTag.METAL, 19)
				.add(EnumTag.VALUABLE, 10);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.CircuitBoard.INTRICATE.ordinal(), tags);
		
		itemStack = ItemInterface.getItem("tubes");
		tags = new ObjectTags(ItemInterface.getItem("ingotCopper").itemID, 0).add(EnumTag.MECHANISM, 1).add(EnumTag.POWER, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Tube.COPPER.ordinal(), tags);
		tags = new ObjectTags(ItemInterface.getItem("ingotTin").itemID, 0).add(EnumTag.MECHANISM, 1).add(EnumTag.POWER, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Tube.TIN.ordinal(), tags);
		tags = new ObjectTags(ItemInterface.getItem("ingotBronze").itemID, 0).add(EnumTag.MECHANISM, 1).add(EnumTag.POWER, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Tube.BRONZE.ordinal(), tags);
		tags = new ObjectTags(Item.ingotIron.itemID, 0).add(EnumTag.MECHANISM, 1).add(EnumTag.POWER, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Tube.IRON.ordinal(), tags);
		tags = new ObjectTags(Item.ingotGold.itemID, 0).add(EnumTag.MECHANISM, 1).add(EnumTag.POWER, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Tube.GOLD.ordinal(), tags);
		tags = new ObjectTags(Item.diamond.itemID, 0).add(EnumTag.MECHANISM, 1).add(EnumTag.POWER, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Tube.DIAMOND.ordinal(), tags);
		tags = new ObjectTags(Block.obsidian.blockID, 0).add(EnumTag.MECHANISM, 1).add(EnumTag.POWER, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Tube.OBSIDIAN.ordinal(), tags);
		tags = new ObjectTags(Item.blazePowder.itemID, 0).add(EnumTag.MECHANISM, 1).add(EnumTag.POWER, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Tube.BLAZE.ordinal(), tags);
		tags = new ObjectTags().add(EnumTag.MOTION, 2).add(EnumTag.CONTROL, 2).add(EnumTag.MECHANISM, 1).add(EnumTag.POWER, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Tube.RUBBER.ordinal(), tags);
		tags = new ObjectTags(Item.emerald.itemID, 0).add(EnumTag.MECHANISM, 1).add(EnumTag.POWER, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Tube.EMERALD.ordinal(), tags);
		tags = new ObjectTags(ItemInterface.getItem("apatite").itemID, 0).add(EnumTag.MECHANISM, 1).add(EnumTag.POWER, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Tube.APATITE.ordinal(), tags);
		tags = new ObjectTags(Item.dyePowder.itemID, 4).add(EnumTag.MECHANISM, 1).add(EnumTag.POWER, 1);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Tube.LAPIS.ordinal(), tags);
	}
	
	private static void setupItemAspectsExtraBees()
	{
		ItemStack itemStack;
		ObjectTags tags;
		
		try // One try block suffices. If we can't get one item, it's probably safe to assume we can't get any others.
		{
			itemStack = ExtraBeesHelper.getExtraBeeItem("comb");
			tags = new ObjectTags().add(EnumTag.INSECT, 2).add(EnumTag.TRAP, 2);
			ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags);
			tags = new ObjectTags(itemStack.itemID, -1).add(EnumTag.POWER, 2);
			ThaumcraftApi.registerObjectTag(itemStack.itemID, ExtraBeesHelper.CombType.OIL.ordinal(), tags);
			ThaumcraftApi.registerObjectTag(itemStack.itemID, ExtraBeesHelper.CombType.COAL.ordinal(), tags);
			ThaumcraftApi.registerObjectTag(itemStack.itemID, ExtraBeesHelper.CombType.FUEL.ordinal(), tags);
			ThaumcraftApi.registerObjectTag(itemStack.itemID, ExtraBeesHelper.CombType.ALCOHOL.ordinal(), tags);
			ThaumcraftApi.registerObjectTag(itemStack.itemID, ExtraBeesHelper.CombType.REDSTONE.ordinal(), tags);
			ThaumcraftApi.registerObjectTag(itemStack.itemID, ExtraBeesHelper.CombType.URANIUM.ordinal(), tags);
			tags = new ObjectTags(itemStack.itemID, -1).add(EnumTag.POISON, 2);
			ThaumcraftApi.registerObjectTag(itemStack.itemID, ExtraBeesHelper.CombType.VENOMOUS.ordinal(), tags);
			
			itemStack = ExtraBeesHelper.getExtraBeeItem("propolis");
			tags = new ObjectTags().add(EnumTag.CONTROL, 1);
			ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags);
			
			itemStack = ExtraBeesHelper.getExtraBeeItem("honeyDrop");
			tags = new ObjectTags().add(EnumTag.EXCHANGE, 2).add(EnumTag.LIFE, 1);
			ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags);
		}
		catch (NullPointerException e) { }
	}
	
	public static void setupResearch()
	{
		if (isActive())
		{
			ObjectTags tags;
			
			tags = new ObjectTags().add(EnumTag.WOOD, 10).add(EnumTag.PLANT, 10).add(EnumTag.INSECT, 10);
			ResearchItem startNode = new ResearchItem("BEESTARTNODE", tags, 10, 1, Config.miscResources.getStackForType(ResourceType.RESEARCH_STARTNODE))
				.setParents(ResearchList.getResearch("UTFT"))
				.registerResearchItem();
			
			tags = new ObjectTags().add(EnumTag.CROP, 15).add(EnumTag.EARTH, 15).add(EnumTag.WATER, 15);
			ResearchItem fertilizer = new ResearchItem("FERTILIZER", tags, 16, 0, ItemInterface.getItem("apatite"))
				.setParents(startNode)
				.registerResearchItem();
			
			tags = new ObjectTags().add(EnumTag.TOOL, 6).add(EnumTag.INSECT, 10).add(EnumTag.BEAST, 2)
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
					.add(EnumTag.FLOWER, 6);
			ResearchItem  gentleFrame = new ResearchItem("HIVEFRAMEGENTLE", tags, 12, -2, Config.hiveFrameGentle)
				.setParents(magicFrame).setHidden()
				.registerResearchItem();
			
			tags = new ObjectTags().add(EnumTag.TOOL, 4).add(EnumTag.INSECT, 10).add(EnumTag.LIFE, 6).add(EnumTag.EXCHANGE, 8)
					.add(EnumTag.MAGIC, 8).add(EnumTag.MOTION, 4);
			ResearchItem  metabolicFrame = new ResearchItem("HIVEFRAMEMETA", tags, 9, -3, Config.hiveFrameMetabolic)
				.setParents(magicFrame).setHidden()
				.registerResearchItem();
			
			tags = new ObjectTags().add(EnumTag.TOOL, 5).add(EnumTag.INSECT, 10).add(EnumTag.DEATH, 15).add(EnumTag.EXCHANGE, 6)
					.add(EnumTag.MAGIC, 8).add(EnumTag.POISON, 6);
			ResearchItem  necroticFrame = new ResearchItem("HIVEFRAMENECRO", tags, 11, -3, Config.hiveFrameNecrotic)
				.setParents(magicFrame).setHidden()
				.registerResearchItem();
			
			tags = new ObjectTags().add(EnumTag.TOOL, 5).add(EnumTag.INSECT, 10).add(EnumTag.TIME, 4);
			ResearchItem temporalFrame = new ResearchItem("HIVEFRAMETIME", tags, 9, -5, Config.hiveFrameTemporal)
				.setParents(magicFrame).setHidden()
				.registerResearchItem();
			
			tags = new ObjectTags().add(EnumTag.VOID, 15).add(EnumTag.CRYSTAL, 8).add(EnumTag.ELDRITCH, 8).add(EnumTag.EXCHANGE, 15);
			ResearchItem voidCapsule = new ResearchItem("VOIDCAPSULE", tags, 10, 9, Config.voidCapsule)
				.setParents(startNode, ResearchList.getResearch("PORTABLEHOLE")).setHidden()
				.registerResearchItem();
			
			tags = new ObjectTags().add(EnumTag.WOOD, 8).add(EnumTag.INSECT, 10).add(EnumTag.TOOL, 6).add(EnumTag.MAGIC, 6);
			ResearchItem scoop = new ResearchItem("THAUMIUMSCOOP", tags, 0, -3, Config.thaumiumScoop)
				.setParents(ResearchList.getResearch("THAUMIUM"))
				.registerResearchItem();
			
			tags = new ObjectTags().add(EnumTag.WOOD, 16).add(EnumTag.PLANT, 20).add(EnumTag.TOOL, 6).add(EnumTag.MAGIC, 6);
			ResearchItem grafter = new ResearchItem("THAUMIUMGRAFTER", tags, 0, -4, Config.thaumiumGrafter)
				.setParents(scoop)
				.registerResearchItem();
		}
	}

	private static void registerResearchXML()
	{
		if (isActive())
		{
			ThaumcraftApi.registerResearchXML(CommonProxy.TCBEES_RESEARCH + "research.xml");
		}
	}
	
	public static void setupThaumcraftCrafting()
	{
		ItemStack input;
		ItemStack output;
		
		if (MagicBees.getConfig().UseImpregnatedStickInTools)
		{
			input = ItemInterface.getItem("stickImpregnated");
		}
		else
		{
			input = new ItemStack(Item.stick);
		}
		
		ThaumcraftApi.addArcaneCraftingRecipe("THAUMIUMSCOOP", "THAUMIUMSCOOP", 20, new ItemStack(Config.thaumiumScoop), new Object[] {
			"sWs", "sTs", " T ",
			's', input,
			'W', Block.cloth,
			'T', new ItemStack(Config.tcMiscResource, 1, ThaumcraftHelper.MiscResource.THAUMIUM.ordinal())
		});
		
		ThaumcraftApi.addArcaneCraftingRecipe("THAUMIUMGRAFTER", "THAUMIUMGRAFTER", 160, new ItemStack(Config.thaumiumGrafter), new Object[] {
			"  T", " T ", "s  ",
			's', input,
			'T', new ItemStack(Config.tcMiscResource, 1, ThaumcraftHelper.MiscResource.THAUMIUM.ordinal())
		});
		
		output = Config.miscResources.getStackForType(ResourceType.EXTENDED_FERTILIZER);
		input = ItemInterface.getItem("apatite");
		ObjectTags tags = (new ObjectTags()).add(EnumTag.CROP, 12);
		output.stackSize = 2;
		ThaumcraftApi.addShapelessInfusionCraftingRecipe("FERTILIZER", "FERTILIZER", 5, tags, output, new Object[] {
				input
		});

		output = new ItemStack(Config.hiveFrameMagic);
		input = ItemInterface.getItem("frameUntreated");
		tags = new ObjectTags().add(EnumTag.WOOD, 4).add(EnumTag.MAGIC, 2);
		ThaumcraftApi.addShapelessInfusionCraftingRecipe("HIVEFRAME", "FRAMEMAGIC", 20, tags, output, new Object[] {
				input
		});
		
		output = new ItemStack(Config.hiveFrameResilient);
		tags = new ObjectTags().add(EnumTag.WOOD, 12).add(EnumTag.MAGIC, 2).add(EnumTag.EXCHANGE, 8);
		ThaumcraftApi.addInfusionCraftingRecipe("HIVEFRAME2", "FRAMERESILIENT", 35, tags, output, new Object[] {
				"ifi",
				'f', Config.hiveFrameMagic,
				'i', Item.ingotIron
		});
		
		output = new ItemStack(Config.hiveFrameGentle);
		tags = new ObjectTags().add(EnumTag.WOOD, 4).add(EnumTag.MAGIC, 2).add(EnumTag.LIFE, 8);
		ThaumcraftApi.addInfusionCraftingRecipe("HIVEFRAMEGENTLE", "FRAMEGENTLE", 35, tags, output, new Object[] {
				" w ", "wFw", " w ",
				'F', input,
				'w', ItemInterface.getItem("beeswax")
				
		});
		
		output = new ItemStack(Config.hiveFrameMetabolic);
		tags = new ObjectTags().add(EnumTag.WOOD, 6).add(EnumTag.MAGIC, 2).add(EnumTag.LIFE, 4).add(EnumTag.EXCHANGE, 8);
		ThaumcraftApi.addShapelessInfusionCraftingRecipe("HIVEFRAMEMETA", "FRAMEMETABOLIC", 50, tags, output, new Object[] {
				Item.magmaCream,
				input
		});
		
		output = new ItemStack(Config.hiveFrameNecrotic);
		tags = new ObjectTags().add(EnumTag.WOOD, 6).add(EnumTag.MAGIC, 2).add(EnumTag.EXCHANGE, 12).add(EnumTag.DEATH, 8);
		ThaumcraftApi.addInfusionCraftingRecipe("HIVEFRAMENECRO", "FRAMENECROTIC", 35, tags, output, new Object[] {
				"SxS",
				'S', Item.rottenFlesh,
				'x', input
		});
		
		output = new ItemStack(Config.hiveFrameTemporal);
		tags = new ObjectTags().add(EnumTag.WOOD, 6).add(EnumTag.MAGIC, 2).add(EnumTag.TIME, 1);
		ThaumcraftApi.addInfusionCraftingRecipe("HIVEFRAMETIME", "FRAMETIME", 50, tags, output, new Object[] {
			"tSt", "SFS", "tSt",
			't', Item.stick,
			'S', Block.sand,
			'F', input
		});
		
		output = Config.voidCapsule.getCapsuleForLiquid(FluidType.EMPTY);
		output.stackSize = 4;
		tags = new ObjectTags().add(EnumTag.VOID, 16).add(EnumTag.ELDRITCH, 2).add(EnumTag.EXCHANGE, 8);
		ThaumcraftApi.addInfusionCraftingRecipe("VOIDCAPSULE", "VOIDCAPSULE", 10, tags, output, new Object[] {
			" G ", "GPG", "TGT",
			'G', Block.thinGlass,
			'P', Item.enderPearl,
			'T', Item.ingotIron
		});

		ItemStack drone = BeeGenomeManager.getBeeNBTForSpecies(BeeSpecies.TC_STARK, EnumBeeType.DRONE);
		ItemStack princess = BeeGenomeManager.getBeeNBTForSpecies(BeeSpecies.TC_STARK, EnumBeeType.PRINCESS);
		
		String researchKey = "BEEINFUSION";
		
		tags = (new ObjectTags()).add(EnumTag.WIND, 40).add(EnumTag.MOTION, 24);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION1", BeeSpecies.TC_AIR.getBeeItem(EnumBeeType.DRONE),
				new ItemStack[] { drone, new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.AIR.ordinal())},
				100, tags, BeeSpecies.TC_STARK, EnumBeeChromosome.SPECIES);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION2", BeeSpecies.TC_AIR.getBeeItem(EnumBeeType.PRINCESS),
				new ItemStack[] { princess , new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.AIR.ordinal()) },
				100, tags, BeeSpecies.TC_STARK, EnumBeeChromosome.SPECIES);
		
		tags = (new ObjectTags()).add(EnumTag.FIRE, 40).add( EnumTag.POWER, 24);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION3", BeeSpecies.TC_FIRE.getBeeItem(EnumBeeType.DRONE), new Object[] 
				{ drone, new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.FIRE.ordinal()) },
				100, tags, BeeSpecies.TC_STARK, EnumBeeChromosome.SPECIES);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION4", BeeSpecies.TC_FIRE.getBeeItem(EnumBeeType.PRINCESS), new Object[] 
				{ princess, new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.FIRE.ordinal()) },
				100, tags, BeeSpecies.TC_STARK, EnumBeeChromosome.SPECIES); 
		
		tags = (new ObjectTags()).add(EnumTag.WATER, 40).add( EnumTag.COLD, 24); 
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION5", BeeSpecies.TC_WATER.getBeeItem(EnumBeeType.DRONE), new Object[] 
				{ drone, new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.WATER.ordinal()) },
				100, tags, BeeSpecies.TC_STARK, EnumBeeChromosome.SPECIES); 
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION6", BeeSpecies.TC_WATER.getBeeItem(EnumBeeType.PRINCESS), new Object[] 
				{ princess, new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.WATER.ordinal()) },
				100, tags, BeeSpecies.TC_STARK, EnumBeeChromosome.SPECIES); 
		
		tags = (new ObjectTags()).add(EnumTag.EARTH, 40).add( EnumTag.ROCK, 24);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION7", BeeSpecies.TC_EARTH.getBeeItem(EnumBeeType.DRONE), new Object[] 
				{ drone, new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.EARTH.ordinal()) },
				100, tags, BeeSpecies.TC_STARK, EnumBeeChromosome.SPECIES); 
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION8", BeeSpecies.TC_EARTH.getBeeItem(EnumBeeType.PRINCESS), new Object[] 
				{ princess, new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.EARTH.ordinal()) },
				100, tags, BeeSpecies.TC_STARK, EnumBeeChromosome.SPECIES);
		
		tags = new ObjectTags().add(EnumTag.MAGIC, 40).add(EnumTag.FLUX, 24);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION9", BeeSpecies.TC_MAGIC.getBeeItem(EnumBeeType.DRONE), new Object[]
				{ drone, new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.MAGIC.ordinal()) },
				100, tags, BeeSpecies.TC_STARK, EnumBeeChromosome.SPECIES);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION0", BeeSpecies.TC_MAGIC.getBeeItem(EnumBeeType.PRINCESS), new Object[]
				{ princess, new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.MAGIC.ordinal()) },
				100, tags, BeeSpecies.TC_STARK, EnumBeeChromosome.SPECIES);
	}
}
