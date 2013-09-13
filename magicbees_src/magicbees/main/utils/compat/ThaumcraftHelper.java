package magicbees.main.utils.compat;

import magicbees.item.types.PollenType;
import magicbees.item.types.PropolisType;
import magicbees.item.types.ResourceType;
import magicbees.item.types.WaxType;
import magicbees.main.CommonProxy;
import magicbees.main.Config;
import magicbees.main.MagicBees;
import magicbees.main.utils.LocalizationManager;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;

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
		QUICKSILVER,
		THAUMIUM,							_7,_8,_9,_10,_11,_12,_13,_14,_15,
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
		ORDER,
		CHAOS,
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
	
	public static final String Name = "Thaumcraft";
	private static boolean isThaumcraftPresent = false;
	
	public static boolean isActive()
	{
		return isThaumcraftPresent;
	}
	
	public static void preInit()
	{
		if (cpw.mods.fml.common.Loader.isModLoaded(Name))
		{
			isThaumcraftPresent = true;
		}
		else
		{
			// Switch off TC-dependant items.
			ResourceType.LORE_FRAGMENT.setHidden();
			ResourceType.TC_DUST_AIR.setHidden();
			ResourceType.TC_DUST_CHAOS.setHidden();
			ResourceType.TC_DUST_EARTH.setHidden();
			ResourceType.TC_DUST_FIRE.setHidden();
			ResourceType.TC_DUST_ORDER.setHidden();
			ResourceType.TC_DUST_WATER.setHidden();
		}
	}
	
	public static void init()
	{
		//if (isActive()) { }
	}
	
	public static void postInit()
	{
		if (isActive())
		{
			// Apparently the Game Registry isn't populated until now. ):
			getBlocks();
			getItems();
			
			//addItemsToBackpack();
			setupItemAspects();
			setupCrafting();
			setupResearch();
		}
	}
	
	private static void getBlocks()
	{
		Config.tcPlant = GameRegistry.findBlock(Name, "blockCustomPlant");
		Config.tcCandle = GameRegistry.findBlock(Name, "blockCandle");
		Config.tcCrystal = GameRegistry.findBlock(Name, "blockCrystal");
		Config.tcMarker = GameRegistry.findBlock(Name, "blockMarker");
		Config.tcJar = GameRegistry.findBlock(Name, "blockJar");
		Config.tcLog = GameRegistry.findBlock(Name, "blockMagicalLog");
		Config.tcLeaf = GameRegistry.findBlock(Name, "blockMagicalLeaves");
		Config.tcWarded = GameRegistry.findBlock(Name, "blockWarded");
	}
	
	private static void getItems()
	{
		Config.tcFilledJar = GameRegistry.findItem(Name, "BlockJarFilledItem");
		Config.tcMiscResource = GameRegistry.findItem(Name, "ItemResource");
		//Config.tcEssentiaBottle = GameRegistry.findItem(Name, "BlockJarFilledItem");
		Config.tcShard = GameRegistry.findItem(Name, "ItemShard");
		Config.tcGolem = GameRegistry.findItem(Name, "ItemGolemPlacer");
		//Config.tcWispEssence = ItemApi.getItem("itemWispEssence", 0).getItem();
		Config.tcNuggets = GameRegistry.findItem(Name, "ItemNugget");
		Config.tcShard = GameRegistry.findItem(Name, "ItemShard");
		Config.tcNuggetChicken = GameRegistry.findItem(Name, "ItemNuggetChicken");
		Config.tcNuggetBeef = GameRegistry.findItem(Name, "ItemNuggetBeef");
		Config.tcNuggetPork = GameRegistry.findItem(Name, "ItemNuggetPork");
	}

	private static void addItemsToBackpack()
	{
		if (MagicBees.getConfig().AddThaumcraftItemsToBackpacks)
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
			ids = /*Config.tcWispEssence.itemID + ":" + -1 + ";"
					+*/ Config.tcMiscResource.itemID + ":" + ThaumcraftHelper.MiscResource.ZOMBIE_BRAIN.ordinal();
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "hunter@" + ids);
			
			// Add Marker, warded stone, candle to builder
			ids = Config.tcCandle.blockID + ":" + -1 + ";"
					+ Config.tcMarker.blockID + ":" + -1 + ";"
					+ Config.tcWarded.blockID + ":" + -1;
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "builder@" + ids);
		}
	}
	
	private static IArcaneRecipe thaumScoop;
	private static IArcaneRecipe thaumGrafter;
	
	private static void setupCrafting()
	{
		ItemStack input, output;

		input = Config.miscResources.getStackForType(ResourceType.TC_DUST_AIR);
		output = new ItemStack(Config.tcShard, 1, ShardType.AIR.ordinal());
		GameRegistry.addShapelessRecipe(output, input, input, input, input);
		
		input = Config.miscResources.getStackForType(ResourceType.TC_DUST_WATER);
		output = new ItemStack(Config.tcShard, 1, ShardType.WATER.ordinal());
		GameRegistry.addShapelessRecipe(output, input, input, input, input);

		input = Config.miscResources.getStackForType(ResourceType.TC_DUST_FIRE);
		output = new ItemStack(Config.tcShard, 1, ShardType.FIRE.ordinal());
		GameRegistry.addShapelessRecipe(output, input, input, input, input);

		input = Config.miscResources.getStackForType(ResourceType.TC_DUST_EARTH);
		output = new ItemStack(Config.tcShard, 1, ShardType.EARTH.ordinal());
		GameRegistry.addShapelessRecipe(output, input, input, input, input);

		input = Config.miscResources.getStackForType(ResourceType.TC_DUST_ORDER);
		output = new ItemStack(Config.tcShard, 1, ShardType.ORDER.ordinal());
		GameRegistry.addShapelessRecipe(output, input, input, input, input);

		input = Config.miscResources.getStackForType(ResourceType.TC_DUST_CHAOS);
		output = new ItemStack(Config.tcShard, 1, ShardType.CHAOS.ordinal());
		GameRegistry.addShapelessRecipe(output, input, input, input, input);
		
		thaumScoop = ThaumcraftApi.addArcaneCraftingRecipe("MB_Scoop", new ItemStack(Config.thaumiumScoop),
			new AspectList().add(Aspect.ORDER, 2), new Object[] {
			"sWs", "sTs", " T ",
			's', Item.stick,
			'W', Block.cloth,
			'T', new ItemStack(Config.tcMiscResource, 1, MiscResource.THAUMIUM.ordinal())
		});
		
		thaumGrafter = ThaumcraftApi.addArcaneCraftingRecipe("MB_Grafter", new ItemStack(Config.thaumiumGrafter),
			new AspectList().add(Aspect.ORDER, 5), new Object[] {
			"  T", " s ", "s  ",
			's', Item.stick,
			'T', new ItemStack(Config.tcMiscResource, 1, MiscResource.THAUMIUM.ordinal())
		});
		
	}
	
	private static void setupResearch()
	{
		ResearchCategories.registerCategory("MAGICBEES", new ResourceLocation("magicbees", CommonProxy.ITEM_TEXTURE + "beeInfusion.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png"));
		
		ResearchItem startNode = new ResearchItem("MB_Root", "MAGICBEES", new AspectList(),
				0, 0, 0,
			Config.miscResources.getStackForType(ResourceType.RESEARCH_BEEINFUSION))
			.setAutoUnlock()
			.setPages(new ResearchPage[] { new ResearchPage(getResearchPage("MB_Root.1")) })
			.registerResearchItem();
		
		ResearchItem thaumiumScoop = new ResearchItem("MB_Scoop", "MAGICBEES", new AspectList().add(Aspect.TOOL, 1),
				2, -2, 1,
				new ItemStack(Config.thaumiumScoop))
			.setPages(new ResearchPage[] { new ResearchPage(getResearchPage("MB_Scoop.1")), new ResearchPage(thaumScoop) })
			.setParents("MB_Root")
			.setParentsHidden("THAUMIUM")
			.registerResearchItem();
		
		ResearchItem thaumiumGrafter = new ResearchItem("MB_Grafter", "MAGICBEES", new AspectList().add(Aspect.TOOL, 2).add(Aspect.TREE, 1).add(Aspect.GREED, 1),
			4, -3, 4,
			new ItemStack(Config.thaumiumGrafter))
			.setPages(new ResearchPage[] { new ResearchPage(getResearchPage("MB_Grafter.1")), new ResearchPage(thaumGrafter) })
			.setParents("MB_Scoop")
			.registerResearchItem();
	}
	
	private static String getResearchPage(String ident)
	{
		// Helps get around all the stupid XML Doctype restrictions.
		return LocalizationManager.getLocalizedString("tc.research_page." + ident)
				.replaceAll("##lineBreak##", "<BR>")
				.replaceAll("##horizontalRule##", "<LINE>")
				.replaceAll("##img##", "<IMG>")
				.replaceAll("##/img##", "</IMG>");
	}
	
	private static void setupItemAspects()
	{
		ItemStack item;
		AspectList list;
		
		list = new AspectList(Block.wood.blockID, 0);
		for (int i = 1; i <= 8; i++)
		{
			item = forestry.api.core.BlockInterface.getBlock("log" + i);
			if (item != null)
			{
				ThaumcraftApi.registerObjectTag(item.itemID, -1, list);
			}
		}
		
		list = new AspectList(Block.planks.blockID, 0);
		for (int i = 1; i <= 2; i++)
		{
			item = forestry.api.core.BlockInterface.getBlock("planks" + i);
			if (item != null)
			{
				ThaumcraftApi.registerObjectTag(item.itemID, -1, list);
			}
		}
		
		list = new AspectList().add(Aspect.ORDER, 5).add(Aspect.ARMOR, 2).add(Aspect.BEAST, 1);
		item = new ItemStack(Config.fHiveBlock);
		ThaumcraftApi.registerObjectTag(item.itemID, -1, list);
		ThaumcraftApi.registerObjectTag(Config.hive.blockID, -1, list);
		
		list = new AspectList().add(Aspect.LIGHT, 1);
		item = forestry.api.core.BlockInterface.getBlock("candle");
		ThaumcraftApi.registerObjectTag(item.itemID, item.getItemDamage(), list);
		
		list = new AspectList().add(Aspect.DARKNESS, 1);
		item = forestry.api.core.BlockInterface.getBlock("stump");
		ThaumcraftApi.registerObjectTag(item.itemID, item.getItemDamage(), list);
		
		list = new AspectList(Block.glass.blockID, 0).add(Aspect.SENSES, 1);
		item = forestry.api.core.BlockInterface.getBlock("glass");
		ThaumcraftApi.registerObjectTag(item.itemID, -1, list);
		
		list = new AspectList().add(Aspect.EARTH, 2).add(Aspect.WATER, 2);
		item = forestry.api.core.BlockInterface.getBlock("soil");
		ThaumcraftApi.registerObjectTag(item.itemID, 1, list);
				
		list = new AspectList(Block.leaves.blockID, 0);
		item = forestry.api.core.BlockInterface.getBlock("leaves");
		ThaumcraftApi.registerObjectTag(item.itemID, item.getItemDamage(), list);
		
		list = new AspectList(Block.sapling.blockID, 0);
		item = forestry.api.core.BlockInterface.getBlock("saplingGE");
		ThaumcraftApi.registerObjectTag(item.itemID, -1, list);
		
		list = new AspectList().add(Aspect.SEED, 1).add(Aspect.PLANT, 1);
		item = forestry.api.core.ItemInterface.getItem("pollenFertile");
		ThaumcraftApi.registerObjectTag(item.itemID, -1, list);
		
		list = new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.EARTH, 2);
		item = forestry.api.core.BlockInterface.getBlock("resources");
		ThaumcraftApi.registerObjectTag(item.itemID, ForestryHelper.BlockResource.APATITE.ordinal(), list.copy().add(Aspect.STONE, 2));
		item = forestry.api.core.ItemInterface.getItem("apatite");
		ThaumcraftApi.registerObjectTag(item.itemID, item.getItemDamage(), list.copy().add(Aspect.CRYSTAL, 2));
		
		
		list = new AspectList().add(Aspect.MOTION, 2).add(Aspect.FLIGHT, 1);
		item = forestry.api.core.ItemInterface.getItem("beeDroneGE");
		ThaumcraftApi.registerObjectTag(item.itemID, -1, list);
		list = list.copy().add(Aspect.GREED, 2).add(Aspect.EXCHANGE, 1);
		item = forestry.api.core.ItemInterface.getItem("beePrincessGE");
		ThaumcraftApi.registerObjectTag(item.itemID, -1, list);
		item = forestry.api.core.ItemInterface.getItem("beeQueenGE");
		ThaumcraftApi.registerObjectTag(item.itemID, -1, list);
		list = new AspectList().add(Aspect.LIFE, 2).add(Aspect.EXCHANGE, 5);
		item = forestry.api.core.ItemInterface.getItem("beeLarvaeGE");
		ThaumcraftApi.registerObjectTag(item.itemID, -1, list);
		
		item = forestry.api.core.ItemInterface.getItem("scoop");
		list = new AspectList(item.itemID, 0).add(Aspect.TOOL, 2);
		ThaumcraftApi.registerComplexObjectTag(item.itemID, 0, list);
		
		item = forestry.api.core.ItemInterface.getItem("grafter");
		list = new AspectList(item.itemID, item.getItemDamage()).add(Aspect.TOOL, 2);
		ThaumcraftApi.registerComplexObjectTag(item.itemID, 0, list);
		
		item = forestry.api.core.ItemInterface.getItem("grafterProven");
		list = list.copy().add(Aspect.TOOL, 2).add(Aspect.EXCHANGE, 3);
		ThaumcraftApi.registerObjectTag(item.itemID, item.getItemDamage(), list);
		
		list = new AspectList().add(Aspect.TRAP, 2);
		ThaumcraftApi.registerObjectTag("beeComb", list);
		
		list = new AspectList().add(Aspect.SLIME, 1);
		item = forestry.api.core.ItemInterface.getItem("propolis");
		ThaumcraftApi.registerObjectTag(item.itemID, ForestryHelper.Propolis.NORMAL.ordinal(), list);
		ThaumcraftApi.registerObjectTag(item.itemID, ForestryHelper.Propolis.SILKY.ordinal(), list.copy().add(Aspect.SLIME, 1));
		ThaumcraftApi.registerObjectTag(item.itemID, ForestryHelper.Propolis.PULSATING.ordinal(), list.copy().add(Aspect.ELDRITCH, 1));
		ThaumcraftApi.registerObjectTag(Config.propolis.itemID, PropolisType.AIR.ordinal(), list.copy().add(Aspect.AIR, 1));
		ThaumcraftApi.registerObjectTag(Config.propolis.itemID, PropolisType.WATER.ordinal(), list.copy().add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(Config.propolis.itemID, PropolisType.FIRE.ordinal(), list.copy().add(Aspect.FIRE, 1));
		ThaumcraftApi.registerObjectTag(Config.propolis.itemID, PropolisType.EARTH.ordinal(), list.copy().add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(Config.propolis.itemID, PropolisType.ORDER.ordinal(), list.copy().add(Aspect.ORDER, 1));
		ThaumcraftApi.registerObjectTag(Config.propolis.itemID, PropolisType.CHAOS.ordinal(), list.copy().add(Aspect.ENTROPY, 1));
		
		list = new AspectList().add(Aspect.PLANT, 2);
		item = forestry.api.core.ItemInterface.getItem("pollen");
		ThaumcraftApi.registerObjectTag(item.itemID, ForestryHelper.Pollen.NORMAL.ordinal(), list);
		ThaumcraftApi.registerObjectTag(item.itemID, ForestryHelper.Pollen.CRYSTALLINE.ordinal(), list.copy().add(Aspect.ICE, 1));
		ThaumcraftApi.registerObjectTag(Config.pollen.itemID, PollenType.UNUSUAL.ordinal(), list.copy().add(Aspect.MAGIC, 1));
		ThaumcraftApi.registerObjectTag(Config.pollen.itemID, PollenType.PHASED.ordinal(), list.copy().add(Aspect.ELDRITCH, 1));
		
		list = new AspectList().add(Aspect.ORDER, 1);
		item = forestry.api.core.ItemInterface.getItem("beeswax");
		ThaumcraftApi.registerObjectTag(item.itemID, item.getItemDamage(), list);
		ThaumcraftApi.registerObjectTag(Config.wax.itemID, WaxType.MAGIC.ordinal(), list.copy().add(Aspect.MAGIC, 1));
		ThaumcraftApi.registerObjectTag(Config.wax.itemID, WaxType.SOUL.ordinal(), list.copy().add(Aspect.SOUL, 1));
		ThaumcraftApi.registerObjectTag(Config.wax.itemID, WaxType.AMNESIC.ordinal(), list.copy().add(Aspect.MIND, 1));
		
		list = new AspectList().add(Aspect.EXCHANGE, 2).add(Aspect.LIFE, 2);
		item = forestry.api.core.ItemInterface.getItem("honeyDrop");
		ThaumcraftApi.registerObjectTag(item.itemID, item.getItemDamage(), list);
		
		list = new AspectList().add(Aspect.LIFE, 2).add(Aspect.ENERGY, 2);
		item = forestry.api.core.ItemInterface.getItem("honeydew");
		ThaumcraftApi.registerObjectTag(item.itemID, item.getItemDamage(), list);
		
		list = new AspectList().add(Aspect.GREED, 1).add(Aspect.LIFE, 4);
		item = forestry.api.core.ItemInterface.getItem("royalJelly");
		ThaumcraftApi.registerObjectTag(item.itemID, item.getItemDamage(), list);
		
		list = new AspectList().add(Aspect.FIRE, 1).add(Aspect.TRAP, 2);
		item = forestry.api.core.ItemInterface.getItem("phosphor");
		ThaumcraftApi.registerObjectTag(item.itemID, item.getItemDamage(), list);
	}
}
