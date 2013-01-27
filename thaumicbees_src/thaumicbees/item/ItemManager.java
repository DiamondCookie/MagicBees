package thaumicbees.item;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import thaumcraft.api.EnumTag;
import thaumicbees.item.types.CapsuleType;
import thaumicbees.item.types.CombType;
import thaumicbees.item.types.DropType;
import thaumicbees.item.types.HiveFrameType;
import thaumicbees.item.types.PropolisType;
import thaumicbees.item.types.ResourceType;
import thaumicbees.item.types.WaxType;
import thaumicbees.main.ThaumicBees;
import thaumicbees.storage.BackpackDefinition;
import thaumicbees.thaumcraft.TCMiscResource;
import thaumicbees.thaumcraft.TCShardType;
import thaumicbees.thaumcraft.ThaumcraftCompat;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import forestry.api.apiculture.BeeManager;
import forestry.api.core.BlockInterface;
import forestry.api.core.ItemInterface;
import forestry.api.recipes.*;
import forestry.api.storage.BackpackManager;
import forestry.api.storage.EnumBackpackType;

public class ItemManager
{
	public static ItemComb combs;
	public static ItemWax wax;
	public static ItemPropolis propolis;
	public static ItemDrop drops;
	public static ItemPollen pollen;
	public static ItemSolidFlux solidFlux;
	public static ItemMiscResources miscResources;
	
	//----- Liquid Capsules --------------------
	public static ItemCapsule magicCapsule;
	
	//----- Apiary Frames ----------------------
	public static ItemMagicHiveFrame hiveFrameMagic;
	public static ItemMagicHiveFrame hiveFrameResilient;
	public static ItemMagicHiveFrame hiveFrameGentle;
	public static ItemMagicHiveFrame hiveFrameMetabolic;
	public static ItemMagicHiveFrame hiveFrameNecrotic;

	//----- Thaumcraft Items -------------------
	public static Item tcFilledJar;
	public static Item tcMiscResource;
	public static Item tcEssentiaBottle;
	public static Item tcShard;
	public static Item tcGolem;
	public static Item tcWispEssence;
	
	//----- Backpacks --------------------------
	public static Item thaumaturgeBackpackT1;
	public static Item thaumaturgeBackpackT2;
	
	public static BackpackDefinition thaumaturgeBackpackDef;
	
	public ItemManager() { }
	
	public static void setupItems(Configuration configFile)
	{
		int itemIDBase = 26090;
		
		Property p = configFile.getItem("combs", itemIDBase++);
		ItemManager.combs= new ItemComb(p.getInt());
		ItemManager.wax = new ItemWax(configFile.getItem("wax", itemIDBase++).getInt());
		ItemManager.propolis = new ItemPropolis(configFile.getItem("propolis", itemIDBase++).getInt());
		ItemManager.drops = new ItemDrop(configFile.getItem("drop", itemIDBase++).getInt());
		ItemManager.miscResources = new ItemMiscResources(configFile.getItem("miscResources", itemIDBase++).getInt());
		
		try
		{
			// 0x8700C6 = purpleish.
			BackpackDefinition def = new BackpackDefinition("thaumaturge", "Thaumaturge's Backpack", 0x8700C6);
			ItemManager.thaumaturgeBackpackT1 = 
					BackpackManager.backpackInterface.addBackpack(configFile.getItem("thaumaturgePack1", itemIDBase++).getInt(),
					def, EnumBackpackType.T1);
			ItemManager.thaumaturgeBackpackT2 = 
					BackpackManager.backpackInterface.addBackpack(configFile.getItem("thaumaturgePack2", itemIDBase++).getInt(),
					def, EnumBackpackType.T2);
			// Add additional items from configs to backpack.
			if (ThaumicBees.getInstanceConfig().ThaumaturgeExtraItems.length() > 0)
			{
				FMLLog.info("Attempting to add extra items to Thaumaturge's backpack! If you get an error, check your ThaumicBees.conf.");
				FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "thaumaturge@" + ThaumicBees.getInstanceConfig().ThaumaturgeExtraItems);
			}
		}
		catch (Exception e)
		{
			FMLLog.severe("ThaumicBees encountered an error during loading! Please ensure that Forestry did not fail to load.");
			e.printStackTrace();
		}
		
		
		ItemManager.magicCapsule = new ItemCapsule(CapsuleType.MAGIC, configFile.getItem("magicCapsule", itemIDBase++).getInt());
		
		ItemManager.pollen = new ItemPollen(configFile.getItem("pollen", itemIDBase++).getInt());
		ItemManager.solidFlux = new ItemSolidFlux(configFile.getItem("fluxCrystal", itemIDBase++).getInt());
		
		ItemManager.hiveFrameMagic = new ItemMagicHiveFrame(configFile.getItem("frameMagic", itemIDBase++).getInt(), HiveFrameType.MAGIC);
		ItemManager.hiveFrameResilient = new ItemMagicHiveFrame(configFile.getItem("frameResilient", itemIDBase++).getInt(), HiveFrameType.RESILIENT);
		ItemManager.hiveFrameGentle = new ItemMagicHiveFrame(configFile.getItem("frameGentle", itemIDBase++).getInt(), HiveFrameType.GENTLE);
		ItemManager.hiveFrameMetabolic = new ItemMagicHiveFrame(configFile.getItem("frameMetabolic", itemIDBase++).getInt(), HiveFrameType.METABOLIC);
		ItemManager.hiveFrameNecrotic = new ItemMagicHiveFrame(configFile.getItem("frameNecrotic", itemIDBase++).getInt(), HiveFrameType.NECROTIC);
		// Future frames, so they all are clumped together.
		itemIDBase++;
		itemIDBase++;
		itemIDBase++;
		itemIDBase++;
		itemIDBase++;
		itemIDBase++;
		itemIDBase++;
		itemIDBase++;
		itemIDBase++;
		
		// New items here.
		
	}
	
	public static void setupCrafting()
	{
		ItemStack inputStack; // Variable to hold forestry items
		
		// Essentia bottles
		ItemStack output = new ItemStack(tcEssentiaBottle);
		output.stackSize = 8;
		GameRegistry.addRecipe(output, new Object[]
			{
				" C ", "GPG", "PGP",
				'G', ItemManager.wax,
				'C', Item.clay,
				'P', Block.thinGlass
			});
		
		output = new ItemStack(tcEssentiaBottle);
		output.stackSize = 4;
		GameRegistry.addRecipe(output, new Object[] {
			" W ", "W W", " W ",
			'W', ItemManager.wax
		});
		
		// Magic capsules
		output = new ItemStack(magicCapsule); output.stackSize = 4;
		GameRegistry.addRecipe(output, new Object[] {
			"WWW",
			'W', ItemManager.wax.getStackForType(WaxType.MAGIC)
		});
		
		output = new ItemStack(tcMiscResource, 1, TCMiscResource.KNOWLEDGE_FRAGMENT.ordinal());
		GameRegistry.addRecipe(output, new Object[] {
			"FF", "FF",
			'F', ItemManager.miscResources.getStackForType(ResourceType.LORE_FRAGMENT)
		});
		
		// T1 Thaumaturge's backpack
		GameRegistry.addRecipe(new ItemStack(ItemManager.thaumaturgeBackpackT1), new Object[] {
			"SWS", "NCN", "SWS",
			'S', Item.silk,
			'W', Block.cloth,
			'N', Item.goldNugget,
			'C', Block.chest
		});
		
		// Concentrated Fertilizer -> Forestry fertilizer
		inputStack = ItemManager.miscResources.getStackForType(ResourceType.EXTENDED_FERTILIZER);
		output = ItemInterface.getItem("fertilizerCompound");
		output.stackSize = 6;
		GameRegistry.addRecipe(output, new Object[] {
			" S ", " F ", " S ",
			'F', inputStack,
			'S', Block.sand
		});
		GameRegistry.addRecipe(output, new Object[] {
			"   ", "SFS", "   ",
			'F', inputStack,
			'S', Block.sand
		});
		
		output = output.copy();
		output.stackSize = 12;
		GameRegistry.addRecipe(output, new Object[] {
			"aaa", "aFa", "aaa",
			'F', inputStack,
			'a', ItemInterface.getItem("ash")
		});
		
		// "bottling" Intellect drops
		GameRegistry.addRecipe(new ItemStack(Item.expBottle), new Object[] {
			"DDD", "DBD", "DDD",
			'D', ItemManager.drops.getStackForType(DropType.INTELLECT),
			'B', Item.glassBottle
		});
		
		// "bottling" Crystal aspects.
		for (EnumTag tag : EnumTag.values())
		{
			if (tag != EnumTag.UNKNOWN)
			{
				output = new ItemStack(ItemManager.tcEssentiaBottle, 1, tag.id + 1);
				GameRegistry.addRecipe(output, new Object[] {
						"ccc", "cxc", "ccc",
						'c', new ItemStack(ItemManager.solidFlux, 1, tag.id),
						'x', ItemManager.tcEssentiaBottle
				});
			}
		}
		
		// 20 is the 'average' time to centrifuge a comb.
		RecipeManagers.centrifugeManager.addRecipe(20, ItemManager.combs.getStackForType(CombType.OCCULT),
				new ItemStack[] {ItemManager.wax.getStackForType(WaxType.MAGIC), ItemInterface.getItem("honeyDrop") },
				new int[] { 100, 60 });
		RecipeManagers.centrifugeManager.addRecipe(20, ItemManager.combs.getStackForType(CombType.OTHERWORLDLY),
				new ItemStack[] {ItemInterface.getItem("beeswax"), ItemManager.wax.getStackForType(WaxType.MAGIC), ItemInterface.getItem("honeyDrop") },
				new int[] { 50, 20, 100 });
		RecipeManagers.centrifugeManager.addRecipe(20, ItemManager.combs.getStackForType(CombType.PAPERY),
				new ItemStack[] {ItemInterface.getItem("beeswax"), ItemManager.wax.getStackForType(WaxType.MAGIC), new ItemStack(Item.paper) },
				new int[] { 80, 20, 5 });
		RecipeManagers.centrifugeManager.addRecipe(25, ItemManager.combs.getStackForType(CombType.STARK),
				new ItemStack[] {ItemInterface.getItem("beeswax"), ItemManager.wax.getStackForType(WaxType.MAGIC), ItemManager.propolis.getStackForType(PropolisType.STARK) },
				new int[] { 10, 95, 15 });
		RecipeManagers.centrifugeManager.addRecipe(25, ItemManager.combs.getStackForType(CombType.AIRY),
				new ItemStack[] {ItemManager.wax.getStackForType(WaxType.MAGIC), ItemManager.propolis.getStackForType(PropolisType.AIR) },
				new int[] { 100, 65 });
		RecipeManagers.centrifugeManager.addRecipe(25, ItemManager.combs.getStackForType(CombType.FIREY),
				new ItemStack[] {ItemManager.wax.getStackForType(WaxType.MAGIC), ItemManager.propolis.getStackForType(PropolisType.FIRE) },
				new int[] { 100, 65 });
		RecipeManagers.centrifugeManager.addRecipe(25, ItemManager.combs.getStackForType(CombType.WATERY),
				new ItemStack[] {ItemManager.wax.getStackForType(WaxType.MAGIC), ItemManager.propolis.getStackForType(PropolisType.WATER)},
				new int[] { 100, 65 });
		RecipeManagers.centrifugeManager.addRecipe(25, ItemManager.combs.getStackForType(CombType.EARTHY),
				new ItemStack[] {ItemManager.wax.getStackForType(WaxType.MAGIC), ItemManager.propolis.getStackForType(PropolisType.EARTH) },
				new int[] { 100, 65 });
		RecipeManagers.centrifugeManager.addRecipe(25, ItemManager.combs.getStackForType(CombType.INFUSED),
				new ItemStack[] {ItemManager.wax.getStackForType(WaxType.MAGIC), ItemManager.wax.getStackForType(WaxType.MAGIC), ItemManager.propolis.getStackForType(PropolisType.INFUSED) },
				new int[] { 100, 50, 65 });
		RecipeManagers.centrifugeManager.addRecipe(20, ItemManager.combs.getStackForType(CombType.INTELLECT),
				new ItemStack[] { ItemManager.wax.getStackForType(WaxType.MAGIC), ItemInterface.getItem("honeydew"), ItemManager.drops.getStackForType(DropType.INTELLECT) },
				new int[] { 90, 40, 2 });
		RecipeManagers.centrifugeManager.addRecipe(20, ItemManager.combs.getStackForType(CombType.SKULKING),
				new ItemStack[] {ItemInterface.getItem("beeswax"), ItemInterface.getItem("propolis"), ItemInterface.getItem("honeydew") },
				new int[] { 90, 20, 35 });
		
		RecipeManagers.centrifugeManager.addRecipe(8, ItemManager.propolis.getStackForType(PropolisType.STARK),
				new ItemStack[] {new ItemStack(tcShard, 1, TCShardType.DULL.ordinal())},
				new int[] { 13 });
		RecipeManagers.centrifugeManager.addRecipe(8, ItemManager.propolis.getStackForType(PropolisType.AIR),
				new ItemStack[] {new ItemStack(tcShard, 1, TCShardType.AIR.ordinal())},
				new int[] { 10 });
		RecipeManagers.centrifugeManager.addRecipe(8, ItemManager.propolis.getStackForType(PropolisType.FIRE),
				new ItemStack[] {new ItemStack(tcShard, 1, TCShardType.FIRE.ordinal())},
				new int[] { 10 });
		RecipeManagers.centrifugeManager.addRecipe(8, ItemManager.propolis.getStackForType(PropolisType.WATER),
				new ItemStack[] {new ItemStack(tcShard, 1, TCShardType.WATER.ordinal())},
				new int[] { 10 });
		RecipeManagers.centrifugeManager.addRecipe(8, ItemManager.propolis.getStackForType(PropolisType.EARTH),
				new ItemStack[] {new ItemStack(tcShard, 1, TCShardType.EARTH.ordinal())},
				new int[] { 10 });
		RecipeManagers.centrifugeManager.addRecipe(8, ItemManager.propolis.getStackForType(PropolisType.INFUSED),
				new ItemStack[] {new ItemStack(tcShard, 1, TCShardType.MAGIC.ordinal())},
				new int[] { 10 });
		
		// Squeezer recipes
		RecipeManagers.squeezerManager.addRecipe(20, new ItemStack[] {ItemManager.propolis.getStackForType(PropolisType.FIRE) },
				new LiquidStack(Block.lavaStill, 250),
				new ItemStack(tcShard, 1, TCShardType.FIRE.ordinal()), 18);
		RecipeManagers.squeezerManager.addRecipe(20, new ItemStack[] {ItemManager.propolis.getStackForType(PropolisType.WATER) },
				new LiquidStack(Block.waterStill, 500),
				new ItemStack(tcShard, 1, TCShardType.WATER.ordinal()), 18);
		
		// Carpenter recipes
		inputStack = ItemInterface.getItem("craftingMaterial");
		inputStack.setItemDamage(3); // Set to Silk Mesh
		output = new ItemStack(thaumaturgeBackpackT2);
		RecipeManagers.carpenterManager.addRecipe(200, new LiquidStack(Block.waterStill.blockID, 1000), null, output, new Object[] {
			"WXW", "WTW", "WWW",
			'X', Item.diamond,
			'W', inputStack,
			'T', new ItemStack(ItemManager.thaumaturgeBackpackT1)
		});
		
		output = BlockInterface.getBlock("candle");
		output.stackSize = 24;
		RecipeManagers.carpenterManager.addRecipe(30, new LiquidStack(Block.waterStill, 600), null, output, new Object[] {
			" S ", "WWW", "WWW",
			'W', ItemManager.wax,
			'S', Item.silk
		});
		
		output = BlockInterface.getBlock("candle");
		output.stackSize = 6;
		inputStack = ItemInterface.getItem("craftingMaterial");
		inputStack.setItemDamage(2); // Set to Silk Wisp
		RecipeManagers.carpenterManager.addRecipe(30, new LiquidStack(Block.waterStill, 600), null, output, new Object[] {
			"WSW",
			'W', ItemManager.wax,
			'S', inputStack
		});
		
		output = ItemManager.miscResources.getStackForType(ResourceType.AROMATIC_LUMP, 2);
		RecipeManagers.carpenterManager.addRecipe(30, new LiquidStack(ItemInterface.getItem("liquidHoney").itemID, 1000), null, output, new Object[] {
			" P ", "JDJ", " P ",
			'P', ItemInterface.getItem("pollen"),
			'J', ItemInterface.getItem("royalJelly"),
			'D', ItemManager.drops.getStackForType(DropType.ENCHANTED)
		});
		
		RecipeManagers.carpenterManager.addRecipe(30, new LiquidStack(ItemInterface.getItem("liquidHoney").itemID, 1000), null, output, new Object[] {
			" J ", "PDP", " J ",
			'P', ItemInterface.getItem("pollen"),
			'J', ItemInterface.getItem("royalJelly"),
			'D', ItemManager.drops.getStackForType(DropType.ENCHANTED)
		});
		// Make Aromatic Lumps a swarmer inducer. Chance is /1000.
		BeeManager.inducers.put(output, 80);

	}
	
	public static void postInit()
	{
		// Forestry liquids aren't registered until PostInit, that's why we wait until now to register them.
		/*ItemStack empty = new ItemStack(this, 1, 0);
		ItemStack filled;
		LiquidStack liquid = null;
		
		for (Liquid l : Liquid.values())
		{
			switch (l)
			{
				case EMPTY:
					liquid = null;
					break;
				case WATER:
					liquid = new LiquidStack(Block.waterStill, this.capsuleType.capacity);
					break;
				case LAVA:
					liquid = new LiquidStack(Block.lavaStill, this.capsuleType.capacity);
					break;
				default:
					liquid = LiquidDictionary.getLiquid(l.liquidID, this.capsuleType.capacity);
					break;
			}

			if (liquid != null)
			{
				filled = new ItemStack(this, 1, l.ordinal());
				LiquidContainerRegistry.registerLiquid(new LiquidContainerData(liquid, filled, empty));
				
				// Register with Squeezer/Bottler
				RecipeManagers.bottlerManager.addRecipe(5, liquid, empty, filled);
				RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[] {filled} , liquid,
						ItemManager.wax.getStackForType(WaxType.MAGIC), 20);
				l.available = true;
			}
		}
		// Empty will be set to unavailable. Obviously, it always is.
		Liquid.EMPTY.available = true;*/
	}

}
