package thaumicbees.main.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

import thaumcraft.api.EnumTag;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchList;
import thaumicbees.bees.Allele;
import thaumicbees.bees.BeeGenomeManager;
import thaumicbees.item.ItemComb;
import thaumicbees.item.ItemMiscResources;
import thaumicbees.item.types.CombType;
import thaumicbees.item.types.DropType;
import thaumicbees.item.types.LiquidType;
import thaumicbees.item.types.PlankType;
import thaumicbees.item.types.PollenType;
import thaumicbees.item.types.PropolisType;
import thaumicbees.item.types.ResourceType;
import thaumicbees.item.types.WaxType;
import thaumicbees.main.CommonProxy;
import thaumicbees.main.Config;
import thaumicbees.main.ThaumicBees;
import thaumicbees.main.utils.compat.ExtraBeesHelper;
import thaumicbees.main.utils.compat.ForestryHelper;
import thaumicbees.main.utils.compat.ThaumcraftHelper;
import thaumicbees.main.utils.compat.ForestryHelper.CircuitBoard;
import thaumicbees.main.utils.compat.ForestryHelper.Comb;
import thaumicbees.main.utils.compat.ForestryHelper.CraftingMaterial;
import thaumicbees.main.utils.compat.ForestryHelper.Pollen;
import thaumicbees.main.utils.compat.ForestryHelper.Propolis;
import thaumicbees.main.utils.compat.ForestryHelper.Tube;
import thaumicbees.main.utils.compat.ThaumcraftHelper.MiscResource;
import thaumicbees.storage.BackpackDefinition;

import cpw.mods.fml.common.FMLLog;
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
import net.minecraftforge.oredict.OreDictionary;

public class CompatabilityManager
{
	public static ItemStack getExtraBeeItem(String field)
	{
		ItemStack value = null;
		try
		{
			Class src = Class.forName("binnie.extrabees.core.ExtraBeeItem");
			Field f = src.getDeclaredField(field);
			
			Item i = (Item) f.get(null);
			value = new ItemStack(i);
		}
		catch (Exception e) { }
		return value;
	}
	
	public static void setupBackpacks()
	{
		if (ThaumicBees.getConfig().AddThaumcraftItemsToBackpacks)
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
		setupItemAspectsThaumicBees();
		setupItemAspectsForestry();
		if (ThaumicBees.getConfig().ExtraBeesInstalled)
		{
			setupItemAspectsExtraBees();
		}
	}
	
	private static void setupItemAspectsThaumicBees()
	{
		ObjectTags tags;
		ItemStack itemStack;
		
		tags = new ObjectTags().add(EnumTag.WOOD, 2);
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
		ThaumcraftApi.registerObjectTag(Config.combs.itemID, CombType.STARK.ordinal(), tags);
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
		ThaumcraftApi.registerComplexObjectTag(Config.magicCapsule.itemID, LiquidType.EMPTY.ordinal(), tags);

		tags = new ObjectTags(Config.magicCapsule.itemID, LiquidType.EMPTY.ordinal())
			.add(EnumTag.WATER, 8);
		ThaumcraftApi.registerObjectTag(Config.magicCapsule.itemID, LiquidType.WATER.ordinal(), tags);
		tags = new ObjectTags(Config.magicCapsule.itemID, LiquidType.EMPTY.ordinal())
			.add(EnumTag.FIRE, 12).add(EnumTag.ROCK, 4);
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
				.add(EnumTag.CONTROL, 2).add(EnumTag.WOOD, 5);
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
		tags = new ObjectTags().add(EnumTag.WOOD, 56).add(EnumTag.EXCHANGE, 8);
		ThaumcraftApi.registerObjectTag(itemStack.itemID, itemStack.getItemDamage(), tags);
		
		itemStack = ItemInterface.getItem("stickImpregnated");
		tags = new ObjectTags().add(EnumTag.WOOD, 6).add(EnumTag.EXCHANGE, 2);
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
		//ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Tube.COPPER.ordinal(), tags);
		tags = new ObjectTags(ItemInterface.getItem("ingotTin").itemID, 0).add(EnumTag.MECHANISM, 1).add(EnumTag.POWER, 1);
		//ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Tube.TIN.ordinal(), tags);
		tags = new ObjectTags(ItemInterface.getItem("ingotBronze").itemID, 0).add(EnumTag.MECHANISM, 1).add(EnumTag.POWER, 1);
		//ThaumcraftApi.registerObjectTag(itemStack.itemID, ForestryHelper.Tube.BRONZE.ordinal(), tags);
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
			itemStack = getExtraBeeItem("comb");
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
			
			itemStack = getExtraBeeItem("propolis");
			tags = new ObjectTags().add(EnumTag.CONTROL, 1);
			ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags);
			
			itemStack = getExtraBeeItem("honeyDrop");
			tags = new ObjectTags().add(EnumTag.EXCHANGE, 2).add(EnumTag.LIFE, 1);
			ThaumcraftApi.registerObjectTag(itemStack.itemID, -1, tags);
		}
		catch (NullPointerException e) { }
	}
	
	private static void setupItemAspectsOreDict()
	{
		
	}
	
	public static void setupBuildCraftFacades()
	{
		FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", ThaumicBees.getConfig().planksWood.blockID + "@" + PlankType.GREATWOOD.ordinal());
		FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", ThaumicBees.getConfig().planksWood.blockID + "@" + PlankType.SILVERWOOD.ordinal());
	}
	
	public static void setupResearch()
	{
		ObjectTags tags;
		
		tags = new ObjectTags().add(EnumTag.WOOD, 10).add(EnumTag.PLANT, 10).add(EnumTag.INSECT, 10);
		ResearchItem startNode = new ResearchItem("TBSTARTNODE", tags, 10, 1, Config.miscResources.getStackForType(ResourceType.RESEARCH_StartNode))
			.setParents(ResearchList.getResearch("UTFT"))
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.INSECT, 9).add(EnumTag.MAGIC, 4).add(EnumTag.FLUX, 4);
		ResearchItem starkHint = new ResearchItem("STARKHINT", tags, 5, 2, ItemInterface.getItem("beeQueenGE"))
			.setParents(startNode)
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.INSECT, 15).add(EnumTag.MAGIC, 5).add(EnumTag.WATER, 5).add(EnumTag.EARTH, 5)
				.add(EnumTag.WIND, 5).add(EnumTag.FIRE, 5);
		ResearchItem beeInfusion = new ResearchItem("BEEINFUSION", tags, 5, 3, Config.miscResources.getStackForType(ResourceType.RESEARCH_BeeInfusion))
			.setParents(starkHint).setHidden()
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.CROP, 20).add(EnumTag.EARTH, 20).add(EnumTag.WATER, 5);
		ResearchItem fertilizer = new ResearchItem("FERTILIZER", tags, 16, 0, ItemInterface.getItem("apatite"))
			.setParents(startNode)
			.registerResearchItem();
		
		tags = new ObjectTags().add(EnumTag.TOOL, 6).add(EnumTag.INSECT, 15).add(EnumTag.BEAST, 2)
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

	public static void registerResearch()
	{
		ThaumcraftApi.registerResearchXML(CommonProxy.TCBEES_RESEARCH + "research.xml");
	}
}
