package thaumicbees.main.utils;

import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumicbees.bees.Allele;
import thaumicbees.bees.BeeGenomeManager;
import thaumicbees.bees.BeeSpecies;
import thaumicbees.item.ItemCapsule;
import thaumicbees.item.types.CombType;
import thaumicbees.item.types.DropType;
import thaumicbees.item.types.HiveFrameType;
import thaumicbees.item.types.LiquidType;
import thaumicbees.item.types.PlankType;
import thaumicbees.item.types.PropolisType;
import thaumicbees.item.types.ResourceType;
import thaumicbees.item.types.WaxType;
import thaumicbees.main.Config;
import thaumicbees.utils.compat.ShapelessBeeInfusionCraftingRecipe;
import thaumicbees.utils.compat.ThaumcraftHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.core.BlockInterface;
import forestry.api.core.ItemInterface;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;

public class CraftingManager
{
	public static void setupCrafting()
	{
		ItemStack input;
		ItemStack output;
		
		// Slabs
		for (int i = 0; i < PlankType.values().length; ++i)
		{
			input = new ItemStack(Config.planksWood, 1, i);
			output = new ItemStack(Config.slabWoodHalf, 6, i);
			GameRegistry.addRecipe(output, new Object[] {
				"PPP",
				'P', input
			});
		}

		// Essentia bottles
		output = new ItemStack(Config.tcEssentiaBottle);
		output.stackSize = 8;
		GameRegistry.addRecipe(output, new Object[] {
				" C ", "GPG", "PGP",
				'G', Config.wax,
				'C', Item.clay,
				'P', Block.thinGlass
		});

		output = new ItemStack(Config.tcEssentiaBottle);
		output.stackSize = 4;
		GameRegistry.addRecipe(output, new Object[] {
				" W ", "W W", " W ",
				'W', Config.wax
		});

		// Magic capsules
		output = new ItemStack(Config.magicCapsule); output.stackSize = 4;
		GameRegistry.addRecipe(output, new Object[] {
				"WWW",
				'W', Config.wax.getStackForType(WaxType.MAGIC)
		});

		output = new ItemStack(Config.tcMiscResource, 1, ThaumcraftHelper.MiscResource.KNOWLEDGE_FRAGMENT.ordinal());
		GameRegistry.addRecipe(output, new Object[] {
				"FF", "FF",
				'F', Config.miscResources.getStackForType(ResourceType.LORE_FRAGMENT)
		});

		// T1 Thaumaturge's backpack
		GameRegistry.addRecipe(new ItemStack(Config.thaumaturgeBackpackT1), new Object[] {
			"SWS", "NCN", "SWS",
			'S', Item.silk,
			'W', Block.cloth,
			'N', new ItemStack(Config.tcMiscResource.itemID, 1, ThaumcraftHelper.MiscResource.AMBER.ordinal()),
			'C', Block.chest
		});

		// Concentrated Fertilizer -> Forestry fertilizer
		input = Config.miscResources.getStackForType(ResourceType.EXTENDED_FERTILIZER);
		output = ItemInterface.getItem("fertilizerCompound");
		output.stackSize = 6;
		GameRegistry.addRecipe(output, new Object[] {
				" S ", " F ", " S ",
				'F', input,
				'S', Block.sand
		});
		GameRegistry.addRecipe(output, new Object[] {
				"   ", "SFS", "   ",
				'F', input,
				'S', Block.sand
		});

		output = output.copy();
		output.stackSize = 12;
		GameRegistry.addRecipe(output, new Object[] {
				"aaa", "aFa", "aaa",
				'F', input,
				'a', ItemInterface.getItem("ash")
		});
			
		// "bottling" Intellect drops
		GameRegistry.addRecipe(new ItemStack(Item.expBottle), new Object[] {
			"DDD", "DBD", "DDD",
			'D', Config.drops.getStackForType(DropType.INTELLECT),
			'B', Item.glassBottle
		});

		// "bottling" Crystal aspects.
		for (EnumTag tag : EnumTag.values())
		{
			if (tag != EnumTag.UNKNOWN)
			{
				output = new ItemStack(Config.tcEssentiaBottle, 1, tag.id + 1);
				GameRegistry.addRecipe(output, new Object[] {
						"ccc", "cxc", "ccc",
						'c', new ItemStack(Config.solidFlux, 1, tag.id),
						'x', Config.tcEssentiaBottle
				});
			}
		}

		// 20 is the 'average' time to centrifuge a comb.
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.OCCULT),
				new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), ItemInterface.getItem("honeyDrop") },
				new int[] { 100, 60 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.OTHERWORLDLY),
				new ItemStack[] {ItemInterface.getItem("beeswax"), Config.wax.getStackForType(WaxType.MAGIC), ItemInterface.getItem("honeyDrop") },
				new int[] { 50, 20, 100 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.PAPERY),
				new ItemStack[] {ItemInterface.getItem("beeswax"), Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Item.paper) },
				new int[] { 80, 20, 5 });
		RecipeManagers.centrifugeManager.addRecipe(25, Config.combs.getStackForType(CombType.STARK),
				new ItemStack[] {ItemInterface.getItem("beeswax"), Config.wax.getStackForType(WaxType.MAGIC), Config.propolis.getStackForType(PropolisType.STARK) },
				new int[] { 10, 95, 15 });
		RecipeManagers.centrifugeManager.addRecipe(25, Config.combs.getStackForType(CombType.AIRY),
				new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), Config.propolis.getStackForType(PropolisType.AIR) },
				new int[] { 100, 65 });
		RecipeManagers.centrifugeManager.addRecipe(25, Config.combs.getStackForType(CombType.FIREY),
				new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), Config.propolis.getStackForType(PropolisType.FIRE) },
				new int[] { 100, 65 });
		RecipeManagers.centrifugeManager.addRecipe(25, Config.combs.getStackForType(CombType.WATERY),
				new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), Config.propolis.getStackForType(PropolisType.WATER)},
				new int[] { 100, 65 });
		RecipeManagers.centrifugeManager.addRecipe(25, Config.combs.getStackForType(CombType.EARTHY),
				new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), Config.propolis.getStackForType(PropolisType.EARTH) },
				new int[] { 100, 65 });
		RecipeManagers.centrifugeManager.addRecipe(25, Config.combs.getStackForType(CombType.INFUSED),
				new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), Config.wax.getStackForType(WaxType.MAGIC), Config.propolis.getStackForType(PropolisType.INFUSED) },
				new int[] { 100, 50, 65 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.INTELLECT),
				new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), ItemInterface.getItem("honeydew"), Config.drops.getStackForType(DropType.INTELLECT) },
				new int[] { 90, 40, 10 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.SKULKING),
				new ItemStack[] {ItemInterface.getItem("beeswax"), ItemInterface.getItem("propolis"), ItemInterface.getItem("honeydew") },
				new int[] { 90, 20, 35 });

		RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.STARK),
				new ItemStack[] {new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.DULL.ordinal())},
				new int[] { 13 });
		RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.AIR),
				new ItemStack[] {new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.AIR.ordinal())},
				new int[] { 10 });
		RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.FIRE),
				new ItemStack[] {new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.FIRE.ordinal())},
				new int[] { 10 });
		RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.WATER),
				new ItemStack[] {new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.WATER.ordinal())},
				new int[] { 10 });
		RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.EARTH),
				new ItemStack[] {new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.EARTH.ordinal())},
				new int[] { 10 });
		RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.INFUSED),
				new ItemStack[] {new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.MAGIC.ordinal())},
				new int[] { 10 });

		// Squeezer recipes
		RecipeManagers.squeezerManager.addRecipe(20, new ItemStack[] {Config.propolis.getStackForType(PropolisType.FIRE) },
				new LiquidStack(Block.lavaStill, 250),
				new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.FIRE.ordinal()), 18);
		RecipeManagers.squeezerManager.addRecipe(20, new ItemStack[] {Config.propolis.getStackForType(PropolisType.WATER) },
				new LiquidStack(Block.waterStill, 500),
				new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.WATER.ordinal()), 18);

		// Carpenter recipes
		input = ItemInterface.getItem("craftingMaterial");
		input.setItemDamage(3); // Set to Silk Mesh
		output = new ItemStack(Config.thaumaturgeBackpackT2);
		RecipeManagers.carpenterManager.addRecipe(200, new LiquidStack(Block.waterStill.blockID, 1000), null, output, new Object[] {
			"WXW", "WTW", "WWW",
			'X', Item.diamond,
			'W', input,
			'T', new ItemStack(Config.thaumaturgeBackpackT1)
		});

		output = BlockInterface.getBlock("candle");
		output.stackSize = 24;
		RecipeManagers.carpenterManager.addRecipe(30, new LiquidStack(Block.waterStill, 600), null, output, new Object[] {
			" S ", "WWW", "WWW",
			'W', Config.wax,
			'S', Item.silk
		});

		output = BlockInterface.getBlock("candle");
		output.stackSize = 6;
		input = ItemInterface.getItem("craftingMaterial");
		input.setItemDamage(2); // Set to Silk Wisp
		RecipeManagers.carpenterManager.addRecipe(30, new LiquidStack(Block.waterStill, 600), null, output, new Object[] {
			"WSW",
			'W', Config.wax,
			'S', input
		});

		output = Config.miscResources.getStackForType(ResourceType.AROMATIC_LUMP, 2);
		RecipeManagers.carpenterManager.addRecipe(30, new LiquidStack(ItemInterface.getItem("liquidHoney").itemID, 1000), null, output, new Object[] {
			" P ", "JDJ", " P ",
			'P', ItemInterface.getItem("pollen"),
			'J', ItemInterface.getItem("royalJelly"),
			'D', Config.drops.getStackForType(DropType.ENCHANTED)
		});

		RecipeManagers.carpenterManager.addRecipe(30, new LiquidStack(ItemInterface.getItem("liquidHoney").itemID, 1000), null, output, new Object[] {
			" J ", "PDP", " J ",
			'P', ItemInterface.getItem("pollen"),
			'J', ItemInterface.getItem("royalJelly"),
			'D', Config.drops.getStackForType(DropType.ENCHANTED)
		});
		
		output = new ItemStack(Config.planksWood, 6, 0);
		RecipeManagers.carpenterManager.addRecipe(8, new LiquidStack(Block.waterStill, 500), null, output, new Object[] {
			"B",
			'B', new ItemStack(Config.tcLog, 1, 0)
		});
		
		output = new ItemStack(Config.planksWood, 8, 1);
		RecipeManagers.carpenterManager.addRecipe(8, new LiquidStack(Block.waterStill, 500), null, output, new Object[] {
			"B",
			'B', new ItemStack(Config.tcLog, 1, 1)
		});
		
		// Make Aromatic Lumps a swarmer inducer. Chance is /1000.
		BeeManager.inducers.put(output, 80);
		
		output = Config.miscResources.getStackForType(ResourceType.EXTENDED_FERTILIZER);
		ItemStack inputA = ItemInterface.getItem("apatite");
		ObjectTags tags = (new ObjectTags()).add(EnumTag.CROP, 12);
		output.stackSize = 2;
		ThaumcraftApi.addShapelessInfusionCraftingRecipe("FERTILIZER", "FERTILIZER", 5, tags, output, new Object[] {
				inputA
		});

		output = new ItemStack(Config.hiveFrameMagic);
		inputA = ItemInterface.getItem("frameUntreated");
		tags = new ObjectTags().add(EnumTag.WOOD, 4).add(EnumTag.INSECT, 8);
		ThaumcraftApi.addShapelessInfusionCraftingRecipe("HIVEFRAME", "FRAMEMAGIC", 50, tags, output, new Object[] {
				inputA
		});
		
		output = new ItemStack(Config.hiveFrameResilient);
		tags = new ObjectTags().add(EnumTag.WOOD, 12).add(EnumTag.INSECT, 8).add(EnumTag.EXCHANGE, 12);
		ThaumcraftApi.addInfusionCraftingRecipe("HIVEFRAME2", "FRAMERESILIENT", 50, tags, output, new Object[] {
				" i ", "ifi", " i ",
				'f', Config.hiveFrameMagic,
				'i', Item.ingotIron
		});
		
		output = new ItemStack(Config.hiveFrameGentle);
		tags = new ObjectTags().add(EnumTag.WOOD, 4).add(EnumTag.INSECT, 8).add(EnumTag.HEAL, 12);
		ThaumcraftApi.addInfusionCraftingRecipe("HIVEFRAMEGENTLE", "FRAMEGENTLE", 50, tags, output, new Object[] {
				"www", "wFw", "www",
				'F', inputA,
				'w', ItemInterface.getItem("beeswax")
				
		});
		
		output = new ItemStack(Config.hiveFrameMetabolic);
		tags = new ObjectTags().add(EnumTag.WOOD, 4).add(EnumTag.INSECT, 8).add(EnumTag.LIFE, 8).add(EnumTag.MOTION, 8)
				.add(EnumTag.EXCHANGE, 8);
		ThaumcraftApi.addShapelessInfusionCraftingRecipe("HIVEFRAMEMETA", "FRAMEMETABOLIC", 50, tags, output, new Object[] {
				Item.magmaCream,
				inputA
		});
		
		output = new ItemStack(Config.hiveFrameNecrotic);
		tags = new ObjectTags().add(EnumTag.WOOD, 4).add(EnumTag.INSECT, 8).add(EnumTag.EXCHANGE, 12).add(EnumTag.DEATH, 16)
				.add(EnumTag.POISON, 4);
		ThaumcraftApi.addInfusionCraftingRecipe("HIVEFRAMENECRO", "FRAMENECROTIC", 50, tags, output, new Object[] {
				" S ", "SxS", " S ",
				'S', Item.rottenFlesh,
				'x', inputA
		});
		
		output = new ItemStack(Config.hiveFrameTemporal);
		tags = new ObjectTags().add(EnumTag.WOOD, 4).add(EnumTag.INSECT, 8).add(EnumTag.TIME, 2);
		ThaumcraftApi.addInfusionCraftingRecipe("HIVEFRAMETIME", "FRAMETIME", 50, tags, output, new Object[] {
			"tSt", "SFS", "tSt",
			't', Item.stick,
			'S', Block.sand,
			'F', inputA
		});

		ItemStack drone = BeeGenomeManager.getBeeNBTForSpecies(BeeSpecies.STARK, EnumBeeType.DRONE);
		ItemStack princess = BeeGenomeManager.getBeeNBTForSpecies(BeeSpecies.STARK, EnumBeeType.PRINCESS);
		
		String researchKey = "BEEINFUSION";
		
		tags = (new ObjectTags()).add(EnumTag.WIND, 40).add(EnumTag.MOTION, 24);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION1", BeeSpecies.AIR.getBeeItem(EnumBeeType.DRONE),
				new ItemStack[] { drone, new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.AIR.ordinal())},
				100, tags, BeeSpecies.STARK, EnumBeeChromosome.SPECIES);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION2", BeeSpecies.AIR.getBeeItem(EnumBeeType.PRINCESS),
				new ItemStack[] { princess , new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.AIR.ordinal()) },
				100, tags, BeeSpecies.STARK, EnumBeeChromosome.SPECIES);
		
		tags = (new ObjectTags()).add(EnumTag.FIRE, 40).add( EnumTag.POWER, 24);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION3", BeeSpecies.FIRE.getBeeItem(EnumBeeType.DRONE), new Object[] 
				{ drone, new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.FIRE.ordinal()) },
				100, tags, BeeSpecies.STARK, EnumBeeChromosome.SPECIES);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION4", BeeSpecies.FIRE.getBeeItem(EnumBeeType.PRINCESS), new Object[] 
				{ princess, new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.FIRE.ordinal()) },
				100, tags, BeeSpecies.STARK, EnumBeeChromosome.SPECIES); 
		
		tags = (new ObjectTags()).add(EnumTag.WATER, 40).add( EnumTag.COLD, 24); 
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION5", BeeSpecies.WATER.getBeeItem(EnumBeeType.DRONE), new Object[] 
				{ drone, new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.WATER.ordinal()) },
				100, tags, BeeSpecies.STARK, EnumBeeChromosome.SPECIES); 
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION6", BeeSpecies.WATER.getBeeItem(EnumBeeType.PRINCESS), new Object[] 
				{ princess, new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.WATER.ordinal()) },
				100, tags, BeeSpecies.STARK, EnumBeeChromosome.SPECIES); 
		
		tags = (new ObjectTags()).add(EnumTag.EARTH, 40).add( EnumTag.ROCK, 24);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION7", BeeSpecies.EARTH.getBeeItem(EnumBeeType.DRONE), new Object[] 
				{ drone, new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.EARTH.ordinal()) },
				100, tags, BeeSpecies.STARK, EnumBeeChromosome.SPECIES); 
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION8", BeeSpecies.EARTH.getBeeItem(EnumBeeType.PRINCESS), new Object[] 
				{ princess, new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.EARTH.ordinal()) },
				100, tags, BeeSpecies.STARK, EnumBeeChromosome.SPECIES);
		
		tags = new ObjectTags().add(EnumTag.MAGIC, 40).add(EnumTag.FLUX, 24);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION9", BeeSpecies.INFUSED.getBeeItem(EnumBeeType.DRONE), new Object[]
				{ drone, new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.MAGIC.ordinal()) },
				100, tags, BeeSpecies.STARK, EnumBeeChromosome.SPECIES);
		ShapelessBeeInfusionCraftingRecipe.createNewRecipe(researchKey, "BEEINFUSION0", BeeSpecies.INFUSED.getBeeItem(EnumBeeType.PRINCESS), new Object[]
				{ princess, new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.MAGIC.ordinal()) },
				100, tags, BeeSpecies.STARK, EnumBeeChromosome.SPECIES);
		
		registerLiquidContainer(Config.magicCapsule);
	}

	private static void registerLiquidContainer(ItemCapsule baseCapsule)
	{
		ItemStack empty = new ItemStack(baseCapsule, 1, 0);
		ItemStack filled;
		LiquidStack liquid = null;

		for (LiquidType liquidType : LiquidType.values())
		{
			switch (liquidType)
			{
				case EMPTY:
					liquid = null;
					break;
				case WATER:
					liquid = new LiquidStack(Block.waterStill, baseCapsule.getType().capacity);
					break;
				case LAVA:
					liquid = new LiquidStack(Block.lavaStill, baseCapsule.getType().capacity);
					break;
				default:
					liquid = LiquidDictionary.getLiquid(liquidType.liquidID, baseCapsule.getType().capacity);
					break;
			}

			if (liquid != null)
			{
				filled = new ItemStack(baseCapsule, 1, liquidType.ordinal());
				LiquidContainerRegistry.registerLiquid(new LiquidContainerData(liquid, filled, empty));

				// Register with Squeezer/Bottler
				RecipeManagers.bottlerManager.addRecipe(5, liquid, empty, filled);
				RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[] {filled} , liquid,
						Config.wax.getStackForType(WaxType.MAGIC), 20);
				liquidType.available = true;
			}
		}
		// Empty will be set to unavailable. Obviously, it always is.
		LiquidType.EMPTY.available = true;
	}
}