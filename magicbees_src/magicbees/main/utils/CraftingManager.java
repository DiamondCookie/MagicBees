package magicbees.main.utils;

import magicbees.block.types.PlankType;
import magicbees.item.ItemCapsule;
import magicbees.item.types.CombType;
import magicbees.item.types.DropType;
import magicbees.item.types.FluidType;
import magicbees.item.types.NuggetType;
import magicbees.item.types.PollenType;
import magicbees.item.types.PropolisType;
import magicbees.item.types.ResourceType;
import magicbees.item.types.WaxType;
import magicbees.main.Config;
import magicbees.main.utils.compat.ArsMagicaHelper;
import magicbees.main.utils.compat.ForestryHelper;
import magicbees.main.utils.compat.ThaumcraftHelper;
import magicbees.main.utils.compat.ThermalExpansionHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.core.BlockInterface;
import forestry.api.core.ItemInterface;
import forestry.api.recipes.RecipeManagers;

public class CraftingManager
{
	public static void registerLiquidContainers()
	{
		registerLiquidContainer(Config.magicCapsule);
		registerLiquidContainer(Config.voidCapsule);
	}
	
	public static void setupCrafting()
	{
		// Broken up into seperate sections to make things a bit easier to find.
		setupVanillaCrafting();
		setupCentrifugeRecipes();
		setupCarpenterRecipes();
	}
	
	private static void setupVanillaCrafting()
	{
		ItemStack input;
		ItemStack output;

		// Magic capsules
		output = new ItemStack(Config.magicCapsule); output.stackSize = 4;
		GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[] {
				"WWW",
				'W', "waxMagical"
		}));

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
		
		GameRegistry.addRecipe(new ItemStack(Block.slowSand, 4), new Object[] {
			"SwS", "wDw", "SwS",
			'S', Block.sand,
			'D', Block.dirt,
			'w', Config.wax.getStackForType(WaxType.SOUL)
		});
		GameRegistry.addRecipe(new ItemStack(Block.slowSand, 4), new Object[] {
			"wSw", "SDS", "wSw",
			'S', Block.sand,
			'D', Block.dirt,
			'w', Config.wax.getStackForType(WaxType.SOUL)
		});

		output = new ItemStack(Config.hiveFrameMagic);
		input = ItemInterface.getItem("frameUntreated");
		GameRegistry.addRecipe(output, new Object[] {
			"www", "wfw", "www",
			'w', Config.wax.getStackForType(WaxType.MAGIC),
			'f', input
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
		
		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.SKULL_FRAGMENT), new Object[] {
			"xxx", "xxx", "xxx",
			'x', Config.miscResources.getStackForType(ResourceType.SKULL_CHIP)
		});
		
		GameRegistry.addRecipe(new ItemStack(Item.skull, 1, 1), new Object[] {
			"xxx", "xxx",
			'x', Config.miscResources.getStackForType(ResourceType.SKULL_FRAGMENT)
		});
		
		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.DRAGON_CHUNK), new Object[] {
			"xxx", "xxx",
			'x', Config.miscResources.getStackForType(ResourceType.DRAGON_DUST)
		});
		
		GameRegistry.addRecipe(new ItemStack(Block.dragonEgg, 1), new Object[] {
			"ccc", "cec", "ccc",
			'c', Config.miscResources.getStackForType(ResourceType.DRAGON_CHUNK),
			'e', Config.miscResources.getStackForType(ResourceType.ESSENCE_FALSE_LIFE)
		});

		GameRegistry.addRecipe(new ShapedOreRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_EVERLASTING_DURABILITY), new Object[] {
			"gwg", "wiw", "gwg",
			'g', Block.glass,
			'w', "waxMagical",
			'i', Block.blockIron
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_EVERLASTING_DURABILITY), new Object[] {
			"wgw", "gig", "wgw",
			'g', Block.glass,
			'w', "waxMagical",
			'i', Block.blockIron
		}));
		
		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_FALSE_LIFE), new Object[] {
			"gwg", "wfw", "gwg",
			'g', Block.glass,
			'w', Config.wax.getStackForType(WaxType.SOUL),
			'f', Block.plantRed
		});
		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_FALSE_LIFE), new Object[] {
			"wgw", "gfg", "wgw",
			'g', Block.glass,
			'w', Config.wax.getStackForType(WaxType.SOUL),
			'f', Block.plantRed
		});
		
		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_SHALLOW_GRAVE), new Object[] {
			"gwg", "wfw", "gwg", 
			'g', Block.glass,
			'w', Config.wax.getStackForType(WaxType.SOUL),
			'f', Item.rottenFlesh
		});
		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_SHALLOW_GRAVE), new Object[] {
			"wgw", "gfg", "wgw", 
			'g', Block.glass,
			'w', Config.wax.getStackForType(WaxType.SOUL),
			'f', Item.rottenFlesh
		});

		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_LOST_TIME), new Object[] {
			"wgw", "gcg", "wgw",
			'g', Block.glass,
			'w', Config.wax.getStackForType(WaxType.SOUL),
			'c', Item.pocketSundial
		});
		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_LOST_TIME), new Object[] {
			"gwg", "wcw", "gwg",
			'g', Block.glass,
			'w', Config.wax.getStackForType(WaxType.SOUL),
			'c', Item.pocketSundial
		});
		
		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_FICKLE_PERMANENCE), new Object[] {
			"wew", "gcg", "wew",
			'w', Config.wax.getStackForType(WaxType.SOUL),
			'c', Item.magmaCream,
			'e', Item.egg,
			'g', Block.glowStone
		});
		
		GameRegistry.addRecipe(Config.miscResources.getStackForType(ResourceType.ESSENCE_SCORNFUL_OBLIVION), new Object[] {
			"gst", "sEs", "tsg",
			'g', Config.miscResources.getStackForType(ResourceType.ESSENCE_SHALLOW_GRAVE),
			't', Config.miscResources.getStackForType(ResourceType.ESSENCE_LOST_TIME),
			's', new ItemStack(Item.skull, 1, 1),
			'E', Block.dragonEgg,
		});
		
		// IF YOU UPDATE THESE, CHANGE THE RECIPES IN THAUMCRAFT HELPER, YOU IDIOT.
		input = new ItemStack(Config.hiveFrameMagic);
		GameRegistry.addShapelessRecipe(new ItemStack(Config.hiveFrameResilient), new Object[] {
			Config.miscResources.getStackForType(ResourceType.ESSENCE_EVERLASTING_DURABILITY),
			input
		});
		
		GameRegistry.addShapelessRecipe(new ItemStack(Config.hiveFrameGentle), new Object[] {
			Config.miscResources.getStackForType(ResourceType.ESSENCE_FALSE_LIFE),
			input
		});
		
		GameRegistry.addShapelessRecipe(new ItemStack(Config.hiveFrameNecrotic), new Object[] {
			Config.miscResources.getStackForType(ResourceType.ESSENCE_SHALLOW_GRAVE),
			input
		});
		
		GameRegistry.addShapelessRecipe(new ItemStack(Config.hiveFrameMetabolic), new Object[] {
			Config.miscResources.getStackForType(ResourceType.ESSENCE_FICKLE_PERMANENCE),
			input
		});
		
		GameRegistry.addShapelessRecipe(new ItemStack(Config.hiveFrameTemporal), new Object[] {
			Config.miscResources.getStackForType(ResourceType.ESSENCE_LOST_TIME),
			input
		});
		
		GameRegistry.addShapelessRecipe(new ItemStack(Config.hiveFrameOblivion), new Object[] {
			Config.miscResources.getStackForType(ResourceType.ESSENCE_SCORNFUL_OBLIVION),
			ItemInterface.getItem("frameProven")
		});
		// </idiot>

		if (OreDictionary.getOres("ingotCopper").size() <= 0)
		{
			NuggetType.COPPER.setInactive();
		}
		else
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(OreDictionary.getOres("ingotCopper").get(0), new Object[] {
				"xxx", "xxx", "xxx",
				'x', "nuggetCopper"
			}));
		}
		if (OreDictionary.getOres("ingotTin").size() <= 0)
		{
			NuggetType.TIN.setInactive();
		}
		else
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(OreDictionary.getOres("ingotTin").get(0), new Object[] {
				"xxx", "xxx", "xxx",
				'x', "nuggetTin"
			}));
		}
		if (OreDictionary.getOres("ingotSilver").size() <= 0)
		{
			NuggetType.SILVER.setInactive();
		}
		else
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(OreDictionary.getOres("ingotSilver").get(0), new Object[] {
				"xxx", "xxx", "xxx",
				'x', "nuggetSilver"
			}));
		}
		if (OreDictionary.getOres("ingotLead").size() <= 0)
		{
			NuggetType.LEAD.setInactive();
		}
		else
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
		
		output = Config.miscResources.getStackForType(ResourceType.DIMENSIONAL_SINGULARITY);
		GameRegistry.addRecipe(output, new Object[] {
			" G ", "QEQ", " W ",
			'E', Item.eyeOfEnder,
			'Q', Block.blockNetherQuartz,
			'W', Block.whiteStone,
			'G', Block.blockGold
		});
		
		output = Config.voidCapsule.getCapsuleForLiquid(FluidType.EMPTY);
		output.stackSize = 4;
		GameRegistry.addRecipe(output, new Object[] {
			"T T", "GFG", "T T",
			'G', Block.thinGlass,
			'F', Config.miscResources.getStackForType(ResourceType.DIMENSIONAL_SINGULARITY),
			'T', Item.goldNugget
		});
		
		output = new ItemStack(Config.magnet);
		GameRegistry.addRecipe(output, new Object[] {
			" i ", "cSc", " d ",
			'i', Item.ingotIron,
			'c', Item.compass,
			'd', Item.diamond,
			'S', Config.miscResources.getStackForType(ResourceType.DIMENSIONAL_SINGULARITY)
		});
		
		for (int level = 1; level <= 8; level++)
		{
			output = new ItemStack(Config.magnet, 1, level * 2);
			GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[] {
					" d ", "mSm", " B ",
					'd', Item.diamond,
					'm', "mb.magnet.level" + (level - 1),
					'B', Block.blockRedstone,
					'S', Config.miscResources.getStackForType(ResourceType.DIMENSIONAL_SINGULARITY)
			}));
		}
		

		if (ThaumcraftHelper.isActive())
		{
			// Slabs
			/*for (int i = 0; i < PlankType.values().length; ++i)
			{
				input = new ItemStack(Config.planksWood, 1, i);
				output = new ItemStack(Config.slabWoodHalf, 6, i);
				GameRegistry.addRecipe(output, new Object[] {
					"PPP",
					'P', input
				});
			}*/

			// Essentia bottles
			/*output = new ItemStack(Config.tcEssentiaBottle);
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
			});*/
			
			input = Config.miscResources.getStackForType(ResourceType.LORE_FRAGMENT);
			output = new ItemStack(Config.tcMiscResource, 1, ThaumcraftHelper.MiscResource.KNOWLEDGE_FRAGMENT.ordinal());
			GameRegistry.addShapelessRecipe(output, new Object[] {
					input, input, input, input
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
			/*for (EnumTag tag : EnumTag.values())
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
			}*/
		}
		
		if (ArsMagicaHelper.isActive())
		{
			input = ItemInterface.getItem("apatite");
			output = Config.miscResources.getStackForType(ResourceType.EXTENDED_FERTILIZER, 4);
			GameRegistry.addShapelessRecipe(output, new Object[] {
					new ItemStack(Config.amEssence, 1, ArsMagicaHelper.EssenceType.EARTH.ordinal()),
					input, input
			});
			GameRegistry.addShapelessRecipe(output, new Object[] {
					new ItemStack(Config.amEssence, 1, ArsMagicaHelper.EssenceType.PLANT.ordinal()),
					input, input
			});
		}
	}
	
	private static void setupCentrifugeRecipes()
	{
		ItemStack beeswax = ItemInterface.getItem("beeswax");
		ItemStack propolis = ItemInterface.getItem("propolis");
		propolis.setItemDamage(ForestryHelper.Propolis.PULSATING.ordinal());
		
		// 20 is the 'average' time to centrifuge a comb.
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.MUNDANE),
				new ItemStack[] {beeswax, ItemInterface.getItem("honeyDrop"), Config.wax.getStackForType(WaxType.MAGIC)},
				new int[] {90, 60, 10});
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.MOLTEN),
				new ItemStack[] {ItemInterface.getItem("refractoryWax"), ItemInterface.getItem("honeyDrop")},
				new int[] {86, 8});
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.FORGOTTEN), 
				new ItemStack[] {Config.wax.getStackForType(WaxType.AMNESIC), propolis, ItemInterface.getItem("honeyDrop")},
				new int[] {50, 50, 22});
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.OCCULT),
				new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), ItemInterface.getItem("honeyDrop") },
				new int[] { 100, 60 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.OTHERWORLDLY),
				new ItemStack[] {beeswax, Config.wax.getStackForType(WaxType.MAGIC), ItemInterface.getItem("honeyDrop") },
				new int[] { 50, 20, 100 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.PAPERY),
				new ItemStack[] {ItemInterface.getItem("beeswax"), Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Item.paper) },
				new int[] { 80, 20, 5 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.INTELLECT),
				new ItemStack[] { Config.wax.getStackForType(WaxType.MAGIC), ItemInterface.getItem("honeydew"), Config.drops.getStackForType(DropType.INTELLECT) },
				new int[] { 90, 40, 10 });
		propolis.setItemDamage(ForestryHelper.Propolis.NORMAL.ordinal());
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.FURTIVE),
				new ItemStack[] {ItemInterface.getItem("beeswax"), propolis, ItemInterface.getItem("honeydew") },
				new int[] { 90, 20, 35 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.SOUL),
				new ItemStack[] {Config.wax.getStackForType(WaxType.SOUL), ItemInterface.getItem("honeydew") },
				new int[] { 95, 26 });
		RecipeManagers.centrifugeManager.addRecipe(20,  Config.combs.getStackForType(CombType.TEMPORAL),
				new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), Config.pollen.getStackForType(PollenType.PHASED), new ItemStack(Config.fHoneydew, 1)},
				new int[] {100, 5, 60});
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TRANSMUTED), 
				new ItemStack[] {ItemInterface.getItem("beeswax"), Config.wax.getStackForType(WaxType.MAGIC), Config.propolis.getStackForType(PropolisType.UNSTABLE)}, 
				new int[] {80, 80, 15});
		
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.AIRY),
				new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Item.feather) },
				new int[] { 100, 60 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.FIREY),
				new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Item.blazePowder) },
				new int[] { 100, 60 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.WATERY),
				new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Item.dyePowder) },
				new int[] { 100, 60 });
		RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.EARTHY),
				new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Item.clay) },
				new int[] { 100, 60 });

		if (ThaumcraftHelper.isActive())
		{
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TC_AIR),
					new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Item.feather), Config.propolis.getStackForType(PropolisType.AIR) },
					new int[] { 100, 60, 80 });
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TC_FIRE),
					new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Item.blazePowder), Config.propolis.getStackForType(PropolisType.FIRE) },
					new int[] { 100, 60, 80 });
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TC_WATER),
					new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Item.dyePowder), Config.propolis.getStackForType(PropolisType.WATER) },
					new int[] { 100, 60, 80 });
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TC_EARTH),
					new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Item.clay), Config.propolis.getStackForType(PropolisType.EARTH) },
					new int[] { 100, 60, 80 });
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TC_ORDER),
					new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), Config.propolis.getStackForType(PropolisType.ORDER) },
					new int[] { 100, 80 });
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TC_CHAOS),
					new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), Config.propolis.getStackForType(PropolisType.CHAOS) },
					new int[] { 100, 80 });

			RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.AIR),
					new ItemStack[] {propolis, Config.miscResources.getStackForType(ResourceType.TC_DUST_AIR) },
					new int[] { 100, 65 } );
			RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.FIRE),
					new ItemStack[] {propolis, Config.miscResources.getStackForType(ResourceType.TC_DUST_FIRE) },
					new int[] { 100, 65 } );
			RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.WATER),
					new ItemStack[] {propolis, Config.miscResources.getStackForType(ResourceType.TC_DUST_WATER) },
					new int[] { 100, 65 } );
			RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.EARTH),
					new ItemStack[] {propolis, Config.miscResources.getStackForType(ResourceType.TC_DUST_EARTH) },
					new int[] { 100, 65 } );
			RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.ORDER),
					new ItemStack[] {propolis, Config.miscResources.getStackForType(ResourceType.TC_DUST_ORDER) },
					new int[] { 100, 65 } );
			RecipeManagers.centrifugeManager.addRecipe(8, Config.propolis.getStackForType(PropolisType.CHAOS),
					new ItemStack[] {propolis, Config.miscResources.getStackForType(ResourceType.TC_DUST_CHAOS) },
					new int[] { 100, 65 } );
		}
		
		if (ArsMagicaHelper.isActive())
		{
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.AM_ESSENCE), 
					new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), new ItemStack(Config.amItemResource), new ItemStack(Config.amItemResource) },
					new int[] { 85, 10, 2 } );
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.AM_POTENT),
					new ItemStack[] {ItemInterface.getItem("beeswax"), ItemInterface.getItem("refractoryWax"), ItemInterface.getItem("honeydew") },
					new int[] { 50, 50, 65 } );
		}
		if (ThermalExpansionHelper.isActive())
		{
			
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TE_DESTABILIZED), 
					new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), Config.drops.getStackForType(DropType.DESTABILIZED)},
					new int[] {50, 22});
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TE_CARBON), 
					new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), Config.drops.getStackForType(DropType.CARBON)},
					new int[] {50, 22});
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TE_LUX), 
					new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), Config.drops.getStackForType(DropType.LUX)},
					new int[] {50, 22});
			RecipeManagers.centrifugeManager.addRecipe(20, Config.combs.getStackForType(CombType.TE_ENDEARING), 
					new ItemStack[] {Config.wax.getStackForType(WaxType.MAGIC), Config.drops.getStackForType(DropType.ENDEARING)},
					new int[] {50, 22});
			
		}
			
	}

	private static void setupCarpenterRecipes()
	{
		ItemStack input;
		ItemStack output;

		output = BlockInterface.getBlock("candle");
		output.stackSize = 24;
		RecipeManagers.carpenterManager.addRecipe(30, new FluidStack(FluidRegistry.WATER, 600), null, output, new Object[] {
			" S ", "WWW", "WWW",
			'W', Config.wax,
			'S', Item.silk
		});

		output = BlockInterface.getBlock("candle");
		output.stackSize = 6;
		input = ItemInterface.getItem("craftingMaterial");
		input.setItemDamage(ForestryHelper.CraftingMaterial.SILK_WISP.ordinal()); // Set to Silk Wisp
		RecipeManagers.carpenterManager.addRecipe(30, new FluidStack(FluidRegistry.WATER, 600), null, output, new Object[] {
			"WSW",
			'W', Config.wax,
			'S', input
		});

		output = Config.miscResources.getStackForType(ResourceType.AROMATIC_LUMP, 2);
		RecipeManagers.carpenterManager.addRecipe(30, FluidRegistry.getFluidStack("honey", 1000), null, output, new Object[] {
			" P ", "JDJ", " P ",
			'P', ItemInterface.getItem("pollen"),
			'J', ItemInterface.getItem("royalJelly"),
			'D', Config.drops.getStackForType(DropType.ENCHANTED)
		});

		RecipeManagers.carpenterManager.addRecipe(30, FluidRegistry.getFluidStack("honey", 1000), null, output, new Object[] {
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
			RecipeManagers.carpenterManager.addRecipe(200, new FluidStack(FluidRegistry.WATER, 1000), null, output, new Object[] {
				"WXW", "WTW", "WWW",
				'X', Item.diamond,
				'W', input,
				'T', new ItemStack(Config.thaumaturgeBackpackT1)
			});
		}
	}

	private static void registerLiquidContainer(ItemCapsule baseCapsule)
	{
		ItemStack empty = new ItemStack(baseCapsule, 1, 0);
		ItemStack filled;
		FluidStack liquid = null;

		for (FluidType fluidType : FluidType.values())
		{
			switch (fluidType)
			{
				case EMPTY:
					liquid = null;
					break;
				case WATER:
					liquid = new FluidStack(FluidRegistry.WATER, baseCapsule.getType().capacity);
					break;
				case LAVA:
					liquid = new FluidStack(FluidRegistry.LAVA, baseCapsule.getType().capacity);
					break;
				default:
					liquid = FluidRegistry.getFluidStack(fluidType.liquidID, baseCapsule.getType().capacity);
					break;
			}

			if (liquid != null)
			{
				filled = new ItemStack(baseCapsule, 1, fluidType.ordinal());
				FluidContainerRegistry.registerFluidContainer(liquid, filled, empty);

				// Register with Squeezer/Bottler
				RecipeManagers.bottlerManager.addRecipe(5, liquid, empty, filled);
				RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[] {filled} , liquid,
						Config.wax.getStackForType(WaxType.MAGIC), 20);
				fluidType.available = true;
			}
		}
		// Empty will be set to unavailable. Obviously, it always is.
		FluidType.EMPTY.available = true;
	}
}