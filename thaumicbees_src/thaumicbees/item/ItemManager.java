package thaumicbees.item;

import cpw.mods.fml.common.registry.GameRegistry;
import thaumicbees.item.ItemComb.CombType;
import thaumicbees.item.ItemDrop.DropType;
import thaumicbees.item.ItemMiscResources.ResourceType;
import thaumicbees.item.ItemPropolis.PropolisType;
import thaumicbees.item.ItemWax.WaxType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import net.minecraftforge.liquids.LiquidStack;
import forestry.api.apiculture.BeeManager;
import forestry.api.core.BlockInterface;
import forestry.api.core.ItemInterface;
import forestry.api.recipes.*;

public class ItemManager
{
	public static ItemComb combs;
	public static ItemWax wax;
	public static ItemPropolis propolis;
	public static ItemDrop drops;
	public static ItemMiscResources miscResources;
	
	//----- Liquid Capsules --------------------
	public static ItemCapsule magicCapsule;
	public static ItemCapsule magicCapsuleWater;
	public static ItemCapsule magicCapsuleLava;
	public static ItemCapsule magicCapsuleBiomass;
	public static ItemCapsule magicCapsuleBiofuel;
	public static ItemCapsule magicCapsuleOil;
	public static ItemCapsule magicCapsuleFuel;
	public static ItemCapsule magicCapsuleSeedOil;
	public static ItemCapsule magicCapsuleHoney;
	public static ItemCapsule magicCapsuleJuice;
	public static ItemCapsule magicCapsuleIce;

	//----- Thaumcraft Items -------------------
	public static ItemStack quicksilver;
	public static ItemStack amber;
	public static ItemStack fragment;
	public static ItemStack airShard;
	public static ItemStack fireShard;
	public static ItemStack waterShard;
	public static ItemStack earthShard;
	public static ItemStack magicShard;
	public static ItemStack dullShard;
	public static ItemStack essentiaBottle;
	
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
	}
	
	public static void setupCrafting()
	{
		ItemStack output = ItemManager.essentiaBottle.copy();
		output.stackSize = 8;
		GameRegistry.addRecipe(output, new Object[]
				{
					" C ", "GPG", "PGP",
					'G', ItemManager.wax,
					'C', Item.clay,
					'P', Block.thinGlass
				});
		output = ItemManager.essentiaBottle.copy();
		output.stackSize = 4;
		GameRegistry.addRecipe(output, new Object[]
			{
				" W ", "W W", " W ",
				'W', ItemManager.wax
			});
		
		output = ItemManager.fragment.copy();
		GameRegistry.addShapelessRecipe(output, new Object[] 
				{ 
				ItemManager.miscResources.getStackForType(ResourceType.KNOWLEDGE_FRAGMENT),
				ItemManager.miscResources.getStackForType(ResourceType.KNOWLEDGE_FRAGMENT),
				ItemManager.miscResources.getStackForType(ResourceType.KNOWLEDGE_FRAGMENT),
				ItemManager.miscResources.getStackForType(ResourceType.KNOWLEDGE_FRAGMENT)
				});
		
		// 20 is the 'average' time to centrifuge a comb.
		RecipeManagers.centrifugeManager.addRecipe(20, ItemManager.combs.getStackForType(CombType.OCCULT),
				new ItemStack[] {ItemManager.wax.getStackForType(WaxType.MAGIC), ItemInterface.getItem("honeyDrop") },
				new int[] { 100, 60 });
		RecipeManagers.centrifugeManager.addRecipe(20, ItemManager.combs.getStackForType(CombType.OTHERWORLDLY),
				new ItemStack[] {ItemManager.wax.getStackForType(WaxType.MAGIC), ItemInterface.getItem("honeyDrop") },
				new int[] { 60, 100 });
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
		RecipeManagers.centrifugeManager.addRecipe(25, ItemManager.propolis.getStackForType(PropolisType.STARK),
				new ItemStack[] {ItemManager.dullShard},
				new int[] { 20 });
		RecipeManagers.centrifugeManager.addRecipe(25, ItemManager.propolis.getStackForType(PropolisType.AIR),
				new ItemStack[] {ItemManager.airShard},
				new int[] { 18 });
		RecipeManagers.centrifugeManager.addRecipe(25, ItemManager.propolis.getStackForType(PropolisType.FIRE),
				new ItemStack[] {ItemManager.fireShard},
				new int[] { 18 });
		RecipeManagers.centrifugeManager.addRecipe(25, ItemManager.propolis.getStackForType(PropolisType.WATER),
				new ItemStack[] {ItemManager.waterShard},
				new int[] { 18 });
		RecipeManagers.centrifugeManager.addRecipe(25, ItemManager.propolis.getStackForType(PropolisType.EARTH),
				new ItemStack[] {ItemManager.earthShard},
				new int[] { 18 });
		
		// Squeezer recipes
		RecipeManagers.squeezerManager.addRecipe(20, new ItemStack[] {ItemManager.propolis.getStackForType(PropolisType.FIRE) },
				new LiquidStack(Block.lavaStill, 250), ItemManager.fireShard, 15);
		RecipeManagers.squeezerManager.addRecipe(20, new ItemStack[] {ItemManager.propolis.getStackForType(PropolisType.WATER) },
				new LiquidStack(Block.waterStill, 1000), ItemManager.waterShard, 25);
		
		// Carpenter recipes
		output = BlockInterface.getBlock("candle");
		output.stackSize = 24;
		RecipeManagers.carpenterManager.addRecipe(30, new LiquidStack(Block.waterStill, 600), null, output, new Object[] {
			" S ", "WWW", "WWW",
			'W', ItemManager.wax,
			'S', Item.silk });
		output = BlockInterface.getBlock("candle");
		output.stackSize = 6;
		ItemStack inputStack = ItemInterface.getItem("craftingMaterial");
		inputStack.setItemDamage(2); // Set to Silk Wisp
		RecipeManagers.carpenterManager.addRecipe(30, new LiquidStack(Block.waterStill, 600), null, output, new Object[] {
			"WSW",
			'W', ItemManager.wax,
			'S', inputStack });
		
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

}
