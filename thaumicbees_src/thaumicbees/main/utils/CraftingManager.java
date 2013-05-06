package thaumicbees.main.utils;

import java.util.ArrayList;

import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumicbees.bees.Allele;
import thaumicbees.bees.BeeGenomeManager;
import thaumicbees.bees.BeeSpecies;
import thaumicbees.item.ItemCapsule;
import thaumicbees.item.ItemWax;
import thaumicbees.item.types.CombType;
import thaumicbees.item.types.DropType;
import thaumicbees.item.types.HiveFrameType;
import thaumicbees.item.types.LiquidType;
import thaumicbees.item.types.NuggetType;
import thaumicbees.item.types.PlankType;
import thaumicbees.item.types.PollenType;
import thaumicbees.item.types.PropolisType;
import thaumicbees.item.types.ResourceType;
import thaumicbees.item.types.WaxType;
import thaumicbees.main.Config;
import thaumicbees.main.ThaumicBees;
import thaumicbees.main.utils.compat.ArsMagicaHelper;
import thaumicbees.main.utils.compat.ForestryHelper;
import thaumicbees.main.utils.compat.ShapelessBeeInfusionCraftingRecipe;
import thaumicbees.main.utils.compat.ThaumcraftHelper;
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
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CraftingManager
{
	public static void setupCrafting()
	{
		// Broken up into seperate sections to make things a bit easier to find.
		setupVanillaCrafting();
		setupCentrifugeRecipes();
		setupSqueezerRecipes();
		setupCarpenterRecipes();
		
		if (ThaumcraftHelper.isActive())
		{
			ThaumcraftHelper.setupThaumcraftCrafting();
		}
		else
		{
			setupThaumcraftAlternativeCrafting();
		}

		registerLiquidContainer(Config.magicCapsule);
		registerLiquidContainer(Config.voidCapsule);
	}
	
	private static void setupVanillaCrafting()
	{
		ItemStack input;
		ItemStack output;

		// Magic capsules
		output = new ItemStack(Config.magicCapsule); output.stackSize = 4;
		GameRegistry.addRecipe(output, new Object[] {
				"WWW",
				'W', Config.wax.getStackForType(WaxType.MAGIC)
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
		
		GameRegistry.addRecipe(new ItemStack(Block.slowSand, 0, 4), new Object[] {
			"SwS", "wDw", "SwS",
			'S', Block.sand,
			'D', Block.dirt,
			'w', Config.wax.getStackForType(WaxType.SOUL)
		});
		GameRegistry.addRecipe(new ItemStack(Block.slowSand, 0, 4), new Object[] {
			"wSw", "SDS", "wSw",
			'S', Block.sand,
			'D', Block.dirt,
			'w', Config.wax.getStackForType(WaxType.SOUL)
		});
		
		GameRegistry.addRecipe(new ItemStack(Config.hiveFrameTemporal), new Object[] {
			"sPs", "PfP", "sPs",
			's', Block.sand,
			'P', Config.pollen.getStackForType(PollenType.PHASED),
			'f', Config.hiveFrameMagic
		});
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Config.effectJar), new Object[] {
			"GSG", "QPQ", "GGG",
			'G', Block.glass,
			'S', "slabWood",
			'P', Config.pollen.getStackForType(PollenType.UNUSUAL),
			'Q', Item.netherQuartz
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Config.moonDial), new Object[] {
			"DqD", "qrq", "DqD",
			'r', Item.redstone,
			'q', Item.netherQuartz,
			'D', "dyeGreen"
		}));

		if (OreDictionary.getOres("ingotCopper").size() <= 0)
		{
			NuggetType.COPPER.setActive(false);
		}
		if (OreDictionary.getOres("ingotTin").size() <= 0)
		{
			NuggetType.TIN.setActive(false);
		}
		if (OreDictionary.getOres("ingotSilver").size() <= 0)
		{
			NuggetType.SILVER.setActive(false);
		}
		if (OreDictionary.getOres("ingotLead").size() <= 0)
		{
			NuggetType.LEAD.setActive(false);
		}

		if (NuggetType.COPPER.isActive())
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(OreDictionary.getOres("ingotCopper").get(0), new Object[] {
				"xxx", "xxx", "xxx",
				'x', "nuggetCopper"
			}));
		}
		if (NuggetType.TIN.isActive())
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(OreDictionary.getOres("ingotTin").get(0), new Object[] {
				"xxx", "xxx", "xxx",
				'x', "nuggetTin"
			}));
		}
		if (NuggetType.SILVER.isActive())
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(OreDictionary.getOres("ingotSilver").get(0), new Object[] {
				"xxx", "xxx", "xxx",
				'x', "nuggetSilver"
			}));
		}
		if (NuggetType.LEAD.isActive())
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(OreDictionary.getOres("ingotLead").get(0), new Object[] {
				"xxx", "xxx", "xxx",
				'x', "nuggetLead"
			}));
		}
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.ingotIron), new Object[] {
			"xxx", "xxx", "xxx",
			'x', "nuggetIron"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.diamond), new Object[] {
			"xxx", "xxx", "xxx",
			'x', "shardDiamond"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.emerald), new Object[] {
			"xxx", "xxx", "xxx",
			'x', "shardEmerald"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemInterface.getItem("apatite"), new Object[] {
			"xxx", "xxx", "xxx",
			'x', Config.nuggets.getStackForType(NuggetType.APATITE)
		}));

		if (ThaumcraftHelper.isActive())
		{
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
		}
		
		if (ArsMagicaHelper.isActive())
		{
			input = ItemInterface.getItem("apatite");
			output = Config.miscResources.getStackForType(ResourceType.EXTENDED_FERTILIZER, 4);
			GameRegistry.addShapelessRecipe(output, new Object[] {
					new ItemStack(Config.amEssenceEarth), new ItemStack(Config.amEssencePlant),
					input, input
			});
		}
	}
	
	private static void setupCentrifugeRecipes()
	{
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
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.INTELLECT),
				new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), ItemInterface.getItem("honeydew"), Config.drops.getStackForType(DropType.INTELLECT) },
				new int[] { 90, 40, 10 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.SKULKING),
				new ItemStack[] {ItemInterface.getItem("beeswax"), ItemInterface.getItem("propolis"), ItemInterface.getItem("honeydew") },
				new int[] { 90, 20, 35 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.SOUL),
				new ItemStack[] {Config.wax.getStackForType(WaxType.SOUL), ItemInterface.getItem("honeydew") },
				new int[] { 95, 26 });
		
		if (ThaumcraftHelper.isActive())
		{
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
			
			RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.STARK),
					new ItemStack[] {new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.DULL.ordinal())},
					new int[] { 10 });
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
		}
		
		if (ArsMagicaHelper.isActive())
		{
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.ESSENCE), 
					new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Config.amVinteumDust), new ItemStack(Config.amVinteumDust) },
					new int[] { 85, 10, 2 } );
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.POTENT),
					new ItemStack[] {ItemInterface.getItem("beeswax"), ItemInterface.getItem("refractoryWax"), ItemInterface.getItem("honeydew") },
					new int[] { 50, 50, 65 } );
		}
	}
	
	private static void setupSqueezerRecipes()
	{
		// Squeezer recipes
		if (ThaumcraftHelper.isActive())
		{
			RecipeManagers.squeezerManager.addRecipe(20, new ItemStack[] {Config.propolis.getStackForType(PropolisType.FIRE) },
					new LiquidStack(Block.lavaStill, 250),
					new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.FIRE.ordinal()), 8);
			RecipeManagers.squeezerManager.addRecipe(20, new ItemStack[] {Config.propolis.getStackForType(PropolisType.WATER) },
					new LiquidStack(Block.waterStill, 500),
					new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.WATER.ordinal()), 8);
		}
	}

	private static void setupCarpenterRecipes()
	{
		ItemStack input;
		ItemStack output;

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
		input.setItemDamage(ForestryHelper.CraftingMaterial.SILK_WISP.ordinal()); // Set to Silk Wisp
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
		
		if (ThaumcraftHelper.isActive())
		{
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
			output = new ItemStack(Config.planksWood, 4, 0);
			RecipeManagers.carpenterManager.addRecipe(8, new LiquidStack(Block.waterStill, 500), null, output, new Object[] {
				"B",
				'B', new ItemStack(Config.tcLog, 1, 0)
			});
			
			output = new ItemStack(Config.planksWood, 6, 1);
			RecipeManagers.carpenterManager.addRecipe(8, new LiquidStack(Block.waterStill, 500), null, output, new Object[] {
				"B",
				'B', new ItemStack(Config.tcLog, 1, 1)
			});
		}
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
	
	private static void setupThaumcraftAlternativeCrafting()
	{
		ItemStack input, output;

		output = new ItemStack(Config.hiveFrameMagic);
		input = ItemInterface.getItem("frameUntreated");
		GameRegistry.addRecipe(output, new Object[] {
			"www", "wfw", "www",
			'w', Config.wax.getStackForType(WaxType.MAGIC),
			'f', input
		});
		
		output = new ItemStack(Config.hiveFrameResilient);
		GameRegistry.addRecipe(output, new Object[] {
				"wiw", "ifi", "wiw",
				'f', Config.hiveFrameMagic,
				'i', Item.ingotIron,
				'w', Config.wax.getStackForType(WaxType.MAGIC)
		});
		
		output = new ItemStack(Config.hiveFrameGentle);
		GameRegistry.addRecipe(output, new Object[] {
				"WwW", "wFw", "WwW",
				'F', input,
				'w', ItemInterface.getItem("beeswax"),
				'W', Config.wax.getStackForType(WaxType.MAGIC)
		});
		
		output = new ItemStack(Config.hiveFrameMetabolic);
		GameRegistry.addShapelessRecipe(output, new Object[] {
				Item.magmaCream,
				input,
				Config.wax.getStackForType(WaxType.MAGIC),
				Config.wax.getStackForType(WaxType.MAGIC),
				Config.wax.getStackForType(WaxType.MAGIC),
				Config.wax.getStackForType(WaxType.MAGIC),
		});
		
		output = new ItemStack(Config.hiveFrameNecrotic);
		GameRegistry.addRecipe(output, new Object[] {
				"wSw", "SFS", "wSw",
				'S', Item.rottenFlesh,
				'F', input,
				'w', Config.wax.getStackForType(WaxType.MAGIC)
		});
		
		output = new ItemStack(Config.hiveFrameTemporal);
		GameRegistry.addRecipe(output, new Object[] {
				"wSt", "wFw", "tSw",
				't', Item.stick,
				'S', Block.sand,
				'F', Config.hiveFrameMagic,
				'w', Config.wax.getStackForType(WaxType.MAGIC)
		});
		GameRegistry.addRecipe(output, new Object[] {
				"tSw", "wFw", "wSt",
				't', Item.stick,
				'S', Block.sand,
				'F', Config.hiveFrameMagic,
				'w', Config.wax.getStackForType(WaxType.MAGIC)
		});
		
		output = Config.voidCapsule.getCapsuleForLiquid(LiquidType.EMPTY);
		output.stackSize = 4;
		GameRegistry.addRecipe(output, new Object[] {
			"wDw", "GPG", "TwT",
			'G', Block.glass,
			'D', Item.diamond,
			'P', Item.eyeOfEnder,
			'T', Item.ingotGold,
			'w', Config.wax.getStackForType(WaxType.MAGIC)
		});
	}
}