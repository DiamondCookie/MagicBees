package magicbees.main.utils.compat;

import java.util.ArrayList;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import magicbees.item.types.PollenType;
import magicbees.item.types.PropolisType;
import magicbees.item.types.ResourceType;
import magicbees.item.types.WaxType;
import magicbees.main.CommonProxy;
import magicbees.main.Config;
import magicbees.main.utils.BlockInterface;
import magicbees.main.utils.ItemInterface;
import magicbees.main.utils.LocalizationManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import forestry.core.config.ForestryBlock;
import forestry.core.config.ForestryItem;

public class ThaumcraftHelper {
	public enum MiscResource {
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

	public enum NuggetType {
		IRON,
		COPPER,
		TIN,
		SILVER,
		LEAD,
		QUICKSILVER,
		THAUMIUM,
		_7, _8, _9, _10, _11, _12, _13, _14, _15,
		NATIVE_IRON,
		NATIVE_COPPER,
		NATIVE_TIN,
		NATIVE_SILVER,
		NATIVE_LEAD,
		_21, _22, _23, _24, _25, _26, _27, _28, _29, _30,
		NATIVE_GOLD,
		;
	}

	public enum ShardType {
		AIR,
		FIRE,
		WATER,
		EARTH,
		ORDER,
		CHAOS,
		BALANCED,
		;
	}

	public enum MetalDeviceType {
		CRUCIBLE,
		ALEMBIC,
		VIS_CHARGE_RELAY,
		ADVANCED_ALCHEMICAL_CONSTRUCT,
		_4,
		ITEM_GRATE,
		_6,
		ARCANE_LAMP,
		LAMP_OF_GROWTH,
		ALCHEMICAL_CONSTRUCT,
		THAUMATORIUM,
		_11,
		MNEMONIC_MATRIX,
		LAMP_OF_FERTILITY,
		VIS_RELAY,
		;
	}
	
	public enum WoodenDeviceType {
		BELLOWS,
		EAR,
		PRESSURE_PLATE,
		BORE_BASE,
		BORE,
		PLANKS_GREATWOOD,
		PLANKS_SILVERWOOD,
		BANNER,
		;
	}

	public enum Entity {
		BRAINY_ZOMBIE("entBrainyZombie", "EntityBrainyZombie"),
		GIANT_BRAINY_ZOMBIE("entGiantBrainyZombie", "EntityGiantBrainyZombie"),
		WISP("entWisp", "EntityWisp"),
		FIREBAT("entFirebat", "EntityFireBat"),
		;

		private static String packageName = "thaumcraft.common.entities.monster.";

		public String entityID;
		private String className;

		private Entity(String id, String clazz) {
			this.entityID = id;
			this.className = clazz;
		}

		public String getClassName() {
			return packageName + this.className;
		}
	}

	public enum BlockPlant {
		GREATWOOD_SAPLING,
		SILVERWOOD_SAPLING,
		SHIMMERLEAF,
		CINDERPEARL,
		PURIFYING_PLANT,
		;
	}

	public enum TreeType {
		GREATWOOD,
		SILVERWOOD,
		;
	}

	public static final String Name = "Thaumcraft";
	private static boolean isThaumcraftPresent = false;

	public static boolean isActive() {
		return isThaumcraftPresent;
	}

	public static void preInit() {
		if (Loader.isModLoaded(Name) && Config.thaumcraftActive) {
			isThaumcraftPresent = true;
		} else {
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

	public static void init() {
		// if (isActive()) { }
	}

	public static void postInit() {
		if (isActive()) {
			// Apparently the Game Registry isn't populated until now. ):
			getBlocks();
			getItems();

			aspectTime = new Aspect("tempus", 0xB68CFF, new Aspect[] {
					Aspect.VOID, Aspect.ORDER }, new ResourceLocation(
					CommonProxy.DOMAIN, CommonProxy.TEXTURE
							+ "aspects/tempus.png"), 1);

			// addItemsToBackpack();
			setupItemAspects();
			setupCrafting();
			setupResearch();
		}
	}

	private static Object aspectTime;

	private static void getBlocks() {
		Config.tcPlant = BlockInterface.getBlock(Name, "blockCustomPlant");
		Config.tcCandle = BlockInterface.getBlock(Name, "blockCandle");
		Config.tcCrystal = BlockInterface.getBlock(Name, "blockCrystal");
		Config.tcMarker = BlockInterface.getBlock(Name, "blockMarker");
		Config.tcJar = BlockInterface.getBlock(Name, "blockJar");
		Config.tcLog = BlockInterface.getBlock(Name, "blockMagicalLog");
		Config.tcLeaf = BlockInterface.getBlock(Name, "blockMagicalLeaves");
		Config.tcWarded = BlockInterface.getBlock(Name, "blockWarded");
		Config.tcWooden = BlockInterface.getBlock(Name, "blockWoodenDevice");
		Config.tcMetal = BlockInterface.getBlock(Name, "blockMetalDevice");
	}

	private static void getItems() {
		Config.tcFilledJar = ItemInterface.getItem(Name, "BlockJarFilledItem");
		Config.tcMiscResource = ItemInterface.getItem(Name, "ItemResource");
		// Config.tcEssentiaBottle = ItemInterface.getItem(Name,
		// "BlockJarFilledItem");
		Config.tcShard = ItemInterface.getItem(Name, "ItemShard");
		Config.tcGolem = ItemInterface.getItem(Name, "ItemGolemPlacer");
		// Config.tcWispEssence = ItemApi.getItem("itemWispEssence",
		// 0).getItem();
		Config.tcNuggets = ItemInterface.getItem(Name, "ItemNugget");
		Config.tcShard = ItemInterface.getItem(Name, "ItemShard");
		Config.tcNuggetChicken = ItemInterface.getItem(Name,
				"ItemNuggetChicken");
		Config.tcNuggetBeef = ItemInterface.getItem(Name, "ItemNuggetBeef");
		Config.tcNuggetPork = ItemInterface.getItem(Name, "ItemNuggetPork");
	}

	private static void setupCrafting() {
		ItemStack input, output;

		input = Config.miscResources.getStackForType(ResourceType.TC_DUST_AIR);
		output = new ItemStack(Config.tcShard, 1, ShardType.AIR.ordinal());
		GameRegistry.addShapelessRecipe(output, input, input, input, input);

		input = Config.miscResources
				.getStackForType(ResourceType.TC_DUST_WATER);
		output = new ItemStack(Config.tcShard, 1, ShardType.WATER.ordinal());
		GameRegistry.addShapelessRecipe(output, input, input, input, input);

		input = Config.miscResources.getStackForType(ResourceType.TC_DUST_FIRE);
		output = new ItemStack(Config.tcShard, 1, ShardType.FIRE.ordinal());
		GameRegistry.addShapelessRecipe(output, input, input, input, input);

		input = Config.miscResources
				.getStackForType(ResourceType.TC_DUST_EARTH);
		output = new ItemStack(Config.tcShard, 1, ShardType.EARTH.ordinal());
		GameRegistry.addShapelessRecipe(output, input, input, input, input);

		input = Config.miscResources
				.getStackForType(ResourceType.TC_DUST_ORDER);
		output = new ItemStack(Config.tcShard, 1, ShardType.ORDER.ordinal());
		GameRegistry.addShapelessRecipe(output, input, input, input, input);

		input = Config.miscResources
				.getStackForType(ResourceType.TC_DUST_CHAOS);
		output = new ItemStack(Config.tcShard, 1, ShardType.CHAOS.ordinal());
		GameRegistry.addShapelessRecipe(output, input, input, input, input);

		thaumScoop = ThaumcraftApi.addArcaneCraftingRecipe("MB_Scoop",
				new ItemStack(Config.thaumiumScoop),
				new AspectList().add(Aspect.ORDER, 2), new Object[] {
						"sWs",
						"sTs",
						" T ",
						's',
						Items.stick,
						'W',
						Blocks.wool,
						'T',
						new ItemStack(Config.tcMiscResource, 1,
								MiscResource.THAUMIUM.ordinal()) });

		thaumGrafter = ThaumcraftApi.addArcaneCraftingRecipe("MB_Grafter",
				new ItemStack(Config.thaumiumGrafter),
				new AspectList().add(Aspect.ORDER, 5), new Object[] {
						"  T",
						" s ",
						"s  ",
						's',
						Items.stick,
						'T',
						new ItemStack(Config.tcMiscResource, 1,
								MiscResource.THAUMIUM.ordinal()) });

		frameMagic = ThaumcraftApi.addArcaneCraftingRecipe(
				"MB_FrameMagic",
				new ItemStack(Config.hiveFrameMagic),
				new AspectList().add(Aspect.ORDER, 5).add(Aspect.AIR, 2)
						.add(Aspect.EARTH, 2), new Object[] {
						"sss",
						"sCs",
						"sss",
						's',
						Items.stick,
						'C',
						new ItemStack(Config.tcMiscResource, 1,
								MiscResource.ENCHANTED_FABRIC.ordinal()) });

		essenceLife = ThaumcraftApi.addCrucibleRecipe("MB_EssenceLife",
				Config.miscResources
						.getStackForType(ResourceType.ESSENCE_FALSE_LIFE),
				new ItemStack(Blocks.red_flower),
				new AspectList().add(Aspect.EXCHANGE, 4).add(Aspect.PLANT, 4));

		essenceDeath = ThaumcraftApi.addCrucibleRecipe("MB_EssenceDeath",
				Config.miscResources
						.getStackForType(ResourceType.ESSENCE_SHALLOW_GRAVE),
				new ItemStack(Items.rotten_flesh),
				new AspectList().add(Aspect.DEATH, 4).add(Aspect.ENTROPY, 4));

		essenceArmor = ThaumcraftApi
				.addCrucibleRecipe(
						"MB_EssenceArmor",
						Config.miscResources
								.getStackForType(ResourceType.ESSENCE_EVERLASTING_DURABILITY),
						new ItemStack(Items.leather),
						new AspectList().add(Aspect.ARMOR, 4).add(Aspect.MAGIC,
								2));

		output = Config.miscResources
				.getStackForType(ResourceType.ESSENCE_FICKLE_PERMANENCE);
		essenceUnstableA = ThaumcraftApi
				.addCrucibleRecipe(
						"MB_EssenceUnstable",
						output,
						Config.propolis.getStackForType(PropolisType.UNSTABLE),
						new AspectList().add(Aspect.ENTROPY, 2).add(
								Aspect.EXCHANGE, 1));

		essenceUnstableB = ThaumcraftApi
				.addCrucibleRecipe(
						"MB_EssenceUnstable",
						output,
						new ItemStack(Blocks.netherrack),
						new AspectList().add(Aspect.ENTROPY, 8).add(
								Aspect.EXCHANGE, 5));

		essenceTime = ThaumcraftApi.addCrucibleRecipe("MB_EssenceTime",
				Config.miscResources
						.getStackForType(ResourceType.ESSENCE_LOST_TIME),
				new ItemStack(Items.clock),
				new AspectList().add(Aspect.ORDER, 10).add(Aspect.VOID, 10)
						.add(Aspect.TRAP, 4));

		input = new ItemStack(Items.ender_pearl);
		singularityA = ThaumcraftApi.addInfusionCraftingRecipe(
				"MB_DimensionalSingularity", Config.miscResources
						.getStackForType(ResourceType.DIMENSIONAL_SINGULARITY,
								3), 5, new AspectList()
						.add(Aspect.ELDRITCH, 10).add(Aspect.EXCHANGE, 20),
				new ItemStack(Blocks.gold_block), new ItemStack[] { input,
						input });

		ItemStack in2 = new ItemStack(Items.diamond);
		singularityB = ThaumcraftApi.addInfusionCraftingRecipe(
				"MB_DimensionalSingularity",
				Config.miscResources.getStackForType(
						ResourceType.DIMENSIONAL_SINGULARITY, 3),
				6,
				new AspectList().add(Aspect.ELDRITCH, 10)
						.add(Aspect.EXCHANGE, 20).add(Aspect.VOID, 15),
				Config.propolis.getStackForType(PropolisType.UNSTABLE),
				new ItemStack[] { input, input, in2 });

		essenceOblivion = ThaumcraftApi
				.addShapelessArcaneCraftingRecipe(
						"MB_EssenceOblivion",
						Config.miscResources
								.getStackForType(ResourceType.ESSENCE_SCORNFUL_OBLIVION),
						new AspectList().add(Aspect.ENTROPY, 25)
								.add(Aspect.AIR, 40).add(Aspect.ORDER, 15),
						new Object[] {
								Config.miscResources
										.getStackForType(ResourceType.DIMENSIONAL_SINGULARITY),
								Blocks.dragon_egg, });
	}

	private static Object frameMagic;
	private static Object thaumScoop;
	private static Object thaumGrafter;
	private static Object singularityA;
	private static Object singularityB;
	private static Object essenceLife;
	private static Object essenceDeath;
	private static Object essenceArmor;
	private static Object essenceUnstableA;
	private static Object essenceUnstableB;
	private static Object essenceTime;
	private static Object essenceOblivion;

	private static void setupResearch() {

		ArrayList<Object> list;
		ItemStack input;
		IRecipe recipe;
		String category = "MAGICBEES";
		ResearchCategories.registerCategory(category, new ResourceLocation(
				CommonProxy.DOMAIN, CommonProxy.ITEM_TEXTURE + "beeInfusion.png"),
				new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png"));

		ResearchItem rootNodeR = new ResearchItem("MB_Root", category,
				new AspectList(),
				0, 0, 0,
				Config.miscResources.getStackForType(ResourceType.RESEARCH_BEEINFUSION))
		.setRound()
		.setStub()
		.setAutoUnlock()
		.setPages(getResearchPage("MB_Root.1"), getResearchPage("MB_Root.2")).registerResearchItem();

		list = new ArrayList<Object>(4);
		list.add(Config.miscResources.getStackForType(ResourceType.LORE_FRAGMENT));
		list.add(Config.miscResources.getStackForType(ResourceType.LORE_FRAGMENT));
		list.add(Config.miscResources.getStackForType(ResourceType.LORE_FRAGMENT));
		list.add(Config.miscResources.getStackForType(ResourceType.LORE_FRAGMENT));
		recipe = new ShapelessRecipes(new ItemStack(Config.tcMiscResource, 1,
				MiscResource.KNOWLEDGE_FRAGMENT.ordinal()), list);

		ResearchItem researchFragment = new ResearchItem("MB_LoreFragment", category,
				new AspectList(),
				0, -3, 0,
				Config.miscResources.getStackForType(ResourceType.LORE_FRAGMENT))
		.setPages(getResearchPage("MB_LoreFragment.1"), new ResearchPage(recipe)).setParents("MB_Root")
		.setStub().setAutoUnlock().setRound().registerResearchItem();

		ResearchItem thaumiumScoopR = new ResearchItem("MB_Scoop", category,
				new AspectList().add(Aspect.TOOL, 1).add(Aspect.MAGIC, 1).add(Aspect.AIR, 1),
				-2, -3, 1,
				new ItemStack(Config.thaumiumScoop))
		.setPages(getResearchPage("MB_Scoop.1"), new ResearchPage((IArcaneRecipe) thaumScoop))
		.setParentsHidden("THAUMIUM").registerResearchItem();

		ResearchItem thaumiumGrafterR = new ResearchItem("MB_Grafter", category,
				new AspectList().add(Aspect.TOOL, 2).add(Aspect.TREE, 1).add(Aspect.GREED, 1),
				-2, -1, 4,
				new ItemStack(Config.thaumiumGrafter))
		.setPages(getResearchPage("MB_Grafter.1"), new ResearchPage((IArcaneRecipe) thaumGrafter))
		.setParents("MB_Scoop").registerResearchItem();

		ResearchItem frameMagicR = new ResearchItem("MB_FrameMagic", category,
				new AspectList().add(Aspect.TOOL, 2).add(Aspect.ARMOR, 1),
				-2, 1, 2,
				new ItemStack(Config.hiveFrameMagic))
		.setPages(getResearchPage("MB_FrameMagic"), new ResearchPage((IArcaneRecipe) frameMagic))
		.registerResearchItem();

		input = new ItemStack(Config.hiveFrameMagic);
		list = new ArrayList<Object>(2);
		list.add(Config.miscResources.getStackForType(ResourceType.ESSENCE_FALSE_LIFE));
		list.add(input);
		recipe = new ShapelessRecipes(new ItemStack(Config.hiveFrameGentle), list);

		ResearchItem essenceFalseLifeR = new ResearchItem("MB_EssenceLife", category,
				new AspectList().add(Aspect.LIFE, 2).add(Aspect.MAGIC, 1),
				2, -1, 2,
				Config.miscResources.getStackForType(ResourceType.ESSENCE_FALSE_LIFE))
		.setPages(getResearchPage("MB_EssenceLife.1"),
				new ResearchPage((CrucibleRecipe) essenceLife),
				new ResearchPage(recipe)).setParentsHidden("CRUCIBLE")
		.registerResearchItem();

		list = new ArrayList<Object>(2);
		list.add(Config.miscResources.getStackForType(ResourceType.ESSENCE_EVERLASTING_DURABILITY));
		list.add(input);
		recipe = new ShapelessRecipes(new ItemStack(Config.hiveFrameResilient), list);

		ResearchItem essenceDurabilityR = new ResearchItem("MB_EssenceArmor", category,
				new AspectList().add(Aspect.ARMOR, 1).add(Aspect.MAGIC, 1),
				5, 0, 3,
				Config.miscResources.getStackForType(ResourceType.ESSENCE_EVERLASTING_DURABILITY))
			.setPages(getResearchPage("MB_EssenceArmor.1"),
					new ResearchPage((CrucibleRecipe) essenceArmor),
					new ResearchPage(recipe)).setParents("MB_EssenceLife")
			.registerResearchItem();

		list = new ArrayList<Object>(2);
		list.add(Config.miscResources .getStackForType(ResourceType.ESSENCE_FICKLE_PERMANENCE));
		list.add(input);
		recipe = new ShapelessRecipes(new ItemStack(Config.hiveFrameMetabolic), list);

		ResearchItem essenceUnstableR = new ResearchItem( "MB_EssenceUnstable", category,
				new AspectList().add(Aspect.ENTROPY, 2).add(Aspect.ORDER, 1),
				3, 1, 4,
				Config.miscResources.getStackForType(ResourceType.ESSENCE_FICKLE_PERMANENCE))
			.setPages(getResearchPage("MB_EssenceUnstable.1"),
					new ResearchPage((CrucibleRecipe) essenceUnstableA),
					new ResearchPage((CrucibleRecipe) essenceUnstableB),
					new ResearchPage(recipe)).setParents("MB_EssenceLife")
			.setConcealed().registerResearchItem();

		list = new ArrayList<Object>(2);
		list.add(Config.miscResources .getStackForType(ResourceType.ESSENCE_SHALLOW_GRAVE));
		list.add(input);
		recipe = new ShapelessRecipes(new ItemStack(Config.hiveFrameNecrotic), list);

		ResearchItem essenceShallowGraveR = new ResearchItem("MB_EssenceDeath", category,
				new AspectList().add(Aspect.DEATH, 2).add(Aspect.MAGIC, 1),
				2, 3, 2,
				Config.miscResources .getStackForType(ResourceType.ESSENCE_SHALLOW_GRAVE))
			.setPages(getResearchPage("MB_EssenceDeath.1"),
					new ResearchPage((CrucibleRecipe) essenceDeath),
					new ResearchPage(recipe))
			.setParents("MB_EssenceUnstable").setConcealed()
			.registerResearchItem();

		list = new ArrayList<Object>(2);
		list.add(Config.miscResources.getStackForType(ResourceType.ESSENCE_LOST_TIME));
		list.add(input);
		recipe = new ShapelessRecipes(new ItemStack(Config.hiveFrameTemporal), list);

		ResearchItem essenceTimeR = new ResearchItem("MB_EssenceTime", category,
				new AspectList().add((Aspect) aspectTime, 2).add(Aspect.MAGIC, 1),
				0, 2, 5,
				Config.miscResources.getStackForType(ResourceType.ESSENCE_LOST_TIME))
		.setPages(getResearchPage("MB_EssenceTime.1"),
					new ResearchPage((CrucibleRecipe) essenceTime),
					new ResearchPage(recipe))
		.setParents("MB_EssenceUnstable").setConcealed()
		.registerResearchItem();

		ResearchItem singularityR = new ResearchItem("MB_DimensionalSingularity", category,
				new AspectList().add(Aspect.ELDRITCH, 2).add((Aspect) aspectTime, 1).add(Aspect.VOID, 1),
				-1, 4, 5,
				Config.miscResources.getStackForType(ResourceType.DIMENSIONAL_SINGULARITY))
		.setPages(getResearchPage("MB_DimensionalSingularity.1"),
				new ResearchPage((InfusionRecipe) singularityA),
				new ResearchPage((InfusionRecipe) singularityB))
		.setParents("MB_FrameMagic", "MB_EssenceTime", "MB_EssenceDeath").setConcealed().setSpecial()
		.registerResearchItem();

		list = new ArrayList<Object>(2);
		list.add(Config.miscResources.getStackForType(ResourceType.ESSENCE_SCORNFUL_OBLIVION));
		list.add(ItemInterface.getItemStack("frameProven"));
		recipe = new ShapelessRecipes(new ItemStack(Config.hiveFrameOblivion), list);

		ResearchItem essenceOblivionR = new ResearchItem("MB_EssenceOblivion", category,
				new AspectList().add(Aspect.VOID, 2).add(Aspect.HUNGER, 1).add((Aspect) aspectTime, 1),
				-3, 3, 5,
				Config.miscResources.getStackForType(ResourceType.ESSENCE_SCORNFUL_OBLIVION))
		.setPages(getResearchPage("MB_EssenceOblivion.1"),
				new ResearchPage((IArcaneRecipe) essenceOblivion),
				new ResearchPage(recipe))
		.setParents("MB_DimensionalSingularity").setConcealed()
		.registerResearchItem();
	}

	private static ResearchPage getResearchPage(String ident) {
		return new ResearchPage(LocalizationManager.getLocalizedString("tc.research_page." + ident));
	}

	private static void setupItemAspects() {
		ItemStack item;
		AspectList list;

		ThaumcraftApi.registerObjectTag(new ItemStack(Items.clock), new AspectList(new ItemStack(Items.clock)).add((Aspect) aspectTime, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(Items.repeater), new AspectList(new ItemStack(Items.repeater)).add((Aspect) aspectTime, 2));

		list = new AspectList(new ItemStack(Blocks.log));
		for (int i = 1; i <= 8; i++) {
			item = ItemInterface.getItemStack("log" + i);
			if (item != null) {
				ThaumcraftApi.registerObjectTag(item, list);
			}
		}

		list = new AspectList(new ItemStack(Blocks.planks));
		for (int i = 1; i <= 2; i++) {
			item = ItemInterface.getItemStack("planks" + i);
			if (item != null) {
				ThaumcraftApi.registerObjectTag(item, list);
			}
		}

		list = new AspectList().add(Aspect.ORDER, 5).add(Aspect.ARMOR, 2).add(Aspect.BEAST, 1);
		item = new ItemStack(Config.fHiveBlock);
		ThaumcraftApi.registerObjectTag(item, list);
		ThaumcraftApi.registerObjectTag(new ItemStack(Config.hive), list);

		list = new AspectList().add(Aspect.LIGHT, 1);
		item = ForestryBlock.candle.getItemStack();
		ThaumcraftApi.registerObjectTag(item, new int[] { item.getItemDamage() }, list);

		list = new AspectList().add(Aspect.DARKNESS, 1);
		item = ForestryBlock.stump.getItemStack();
		ThaumcraftApi.registerObjectTag(item, new int[] { item.getItemDamage() }, list);

		list = new AspectList(new ItemStack(Blocks.glass)).add(Aspect.SENSES, 1);
		item = ForestryBlock.glass.getItemStack();
		ThaumcraftApi.registerObjectTag(item, list);

		list = new AspectList().add(Aspect.EARTH, 2).add(Aspect.WATER, 2);
		item = ForestryBlock.soil.getItemStack();
		ThaumcraftApi.registerObjectTag(item, new int[] { 1 }, list);

		list = new AspectList(new ItemStack(Blocks.leaves));
		item = ForestryBlock.leaves.getItemStack();
		ThaumcraftApi.registerObjectTag(item, new int[] { item.getItemDamage() }, list);

		list = new AspectList(new ItemStack(Blocks.sapling));
		item = ForestryBlock.saplingGE.getItemStack();
		ThaumcraftApi.registerObjectTag(item, list);

		list = new AspectList()/* .add(Aspect.SEED, 1) */.add(Aspect.PLANT, 1);
		item = ForestryItem.pollenFertile.getItemStack();
		ThaumcraftApi.registerObjectTag(item, list);

		list = new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.EARTH, 2);
		item = ForestryItem.apatite.getItemStack();
		ThaumcraftApi.registerObjectTag(new ItemStack(item.getItem(), 1,ForestryHelper.BlockResource.APATITE.ordinal()), list.copy());
		item = ForestryItem.apatite.getItemStack();
		ThaumcraftApi.registerObjectTag(new ItemStack(item.getItem(), 1, item.getItemDamage()), list.copy().add(Aspect.CRYSTAL, 2));

		list = new AspectList().add(Aspect.MOTION, 2).add(Aspect.FLIGHT, 1);
		item = ItemInterface.getItemStack("beeDroneGE");
		ThaumcraftApi.registerObjectTag(item, list);
		list = list.copy().add(Aspect.GREED, 2).add(Aspect.EXCHANGE, 1);
		item = ItemInterface.getItemStack("beePrincessGE");
		ThaumcraftApi.registerObjectTag(item, list);
		item = ItemInterface.getItemStack("beeQueenGE");
		ThaumcraftApi.registerObjectTag(item, list);
		list = new AspectList().add(Aspect.LIFE, 2).add(Aspect.EXCHANGE, 5);
		item = ItemInterface.getItemStack("beeLarvaeGE");
		ThaumcraftApi.registerObjectTag(item, list);

		item = ItemInterface.getItemStack("scoop");
		list = new AspectList(new ItemStack(item.getItem(), 1, 0)).add(Aspect.TOOL, 2);
		ThaumcraftApi.registerComplexObjectTag(item, list);

		item = ItemInterface.getItemStack("grafter");
		list = new AspectList(new ItemStack(item.getItem(), 1, item.getItemDamage())).add(Aspect.TOOL, 2);
		ThaumcraftApi.registerComplexObjectTag(item, list);

		item = ItemInterface.getItemStack("grafterProven");
		list = list.copy().add(Aspect.TOOL, 2).add(Aspect.EXCHANGE, 3);
		ThaumcraftApi.registerObjectTag(new ItemStack(item.getItem(), 1, item.getItemDamage()), list);

		list = new AspectList().add(Aspect.TRAP, 2);
		ThaumcraftApi.registerObjectTag("beeComb", list);

		list = new AspectList().add(Aspect.SLIME, 1);
		item = ItemInterface.getItemStack("propolis");
		ThaumcraftApi.registerObjectTag(new ItemStack(item.getItem(), 1, ForestryHelper.Propolis.NORMAL.ordinal()), list);
		ThaumcraftApi.registerObjectTag(new ItemStack(item.getItem(), 1, ForestryHelper.Propolis.SILKY.ordinal()), list.copy().add(Aspect.SLIME, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(item.getItem(), 1, ForestryHelper.Propolis.PULSATING.ordinal()), list.copy().add(Aspect.ELDRITCH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(Config.propolis, 1, PropolisType.UNSTABLE.ordinal()), list.copy().add(Aspect.ENTROPY, 1).add(Aspect.EXCHANGE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(Config.propolis, 1, PropolisType.AIR.ordinal()), list.copy().add(Aspect.AIR, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(Config.propolis, 1, PropolisType.WATER.ordinal()), list.copy().add(Aspect.WATER, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(Config.propolis, 1, PropolisType.FIRE.ordinal()), list.copy().add(Aspect.FIRE, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(Config.propolis, 1, PropolisType.EARTH.ordinal()), list.copy().add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(Config.propolis, 1, PropolisType.ORDER.ordinal()), list.copy().add(Aspect.ORDER, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(Config.propolis, 1, PropolisType.CHAOS.ordinal()), list.copy().add(Aspect.ENTROPY, 2));

		list = new AspectList().add(Aspect.PLANT, 2);
		item = ItemInterface.getItemStack("pollen");
		ThaumcraftApi.registerObjectTag(new ItemStack(item.getItem(), 1, ForestryHelper.Pollen.NORMAL.ordinal()), list);
		ThaumcraftApi.registerObjectTag(new ItemStack(item.getItem(), 1, ForestryHelper.Pollen.CRYSTALLINE.ordinal()), list.copy());
		ThaumcraftApi.registerObjectTag(new ItemStack(Config.pollen, 1, PollenType.UNUSUAL.ordinal()), list.copy().add(Aspect.MAGIC, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(Config.pollen, 1, PollenType.PHASED.ordinal()), list.copy().add((Aspect) aspectTime, 2));

		list = new AspectList().add(Aspect.ORDER, 1);
		item = ItemInterface.getItemStack("beeswax");
		ThaumcraftApi.registerObjectTag(item, new int[] { item.getItemDamage() }, list.copy().add(Aspect.ORDER, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(Config.wax, 1, WaxType.MAGIC.ordinal()), list.copy().add(Aspect.MAGIC, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(Config.wax, 1, WaxType.SOUL.ordinal()), list.copy().add(Aspect.SOUL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(Config.wax, 1, WaxType.AMNESIC.ordinal()), list.copy().add(Aspect.MIND, 2));

		list = new AspectList().add(Aspect.EXCHANGE, 2).add(Aspect.LIFE, 2);
		item = ItemInterface.getItemStack("honeyDrop");
		ThaumcraftApi.registerObjectTag(item, new int[] { item.getItemDamage() }, list);

		list = new AspectList().add(Aspect.LIFE, 2).add(Aspect.ENERGY, 2);
		item = ItemInterface.getItemStack("honeydew");
		ThaumcraftApi.registerObjectTag(item, new int[] { item.getItemDamage() }, list);

		list = new AspectList().add(Aspect.GREED, 1).add(Aspect.LIFE, 4);
		item = ItemInterface.getItemStack("royalJelly");
		ThaumcraftApi.registerObjectTag(item, new int[] { item.getItemDamage() }, list);

		list = new AspectList().add(Aspect.FIRE, 1).add(Aspect.TRAP, 2);
		item = ItemInterface.getItemStack("phosphor");
		ThaumcraftApi.registerObjectTag(item, new int[] { item.getItemDamage() }, list);

		item = Config.miscResources.getStackForType(ResourceType.ESSENCE_FALSE_LIFE);
		ThaumcraftApi.registerObjectTag(item, new int[] { item.getItemDamage() }, new AspectList().add(Aspect.LIFE, 6));

		item = Config.miscResources.getStackForType(ResourceType.ESSENCE_SHALLOW_GRAVE);
		ThaumcraftApi.registerObjectTag(item, new int[] { item.getItemDamage() }, new AspectList().add(Aspect.DEATH, 6));

		item = Config.miscResources.getStackForType(ResourceType.ESSENCE_EVERLASTING_DURABILITY);
		ThaumcraftApi.registerObjectTag(item, new int[] { item.getItemDamage() }, new AspectList().add(Aspect.ARMOR, 6));

		item = Config.miscResources.getStackForType(ResourceType.ESSENCE_LOST_TIME);
		ThaumcraftApi.registerObjectTag(item, new int[] { item.getItemDamage() }, new AspectList().add(Aspect.VOID, 6).add((Aspect) aspectTime, 8));
	}
}
