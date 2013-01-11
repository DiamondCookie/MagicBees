package thaumicbees.item;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import thaumicbees.item.ItemComb.CombType;
import thaumicbees.item.ItemDrop.DropType;
import thaumicbees.item.ItemMiscResources.ResourceType;
import thaumicbees.item.ItemPropolis.PropolisType;
import thaumicbees.item.ItemWax.WaxType;
import thaumicbees.main.ThaumcraftCompat;
import thaumicbees.storage.BackpackDefinition;
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
import forestry.api.storage.BackpackManager;
import forestry.api.storage.EnumBackpackType;

public class ItemManager
{
	public static ItemComb combs;
	public static ItemWax wax;
	public static ItemPropolis propolis;
	public static ItemDrop drops;
	public static ItemMiscResources miscResources;
	
	//----- Liquid Capsules --------------------
	public static ItemCapsule magicCapsule;

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
		
		// 0x8700C6 = purpleish.
		BackpackDefinition def = new BackpackDefinition("thaumaturge", "Thaumaturge's Backpack", 0x8700C6);
		ItemManager.thaumaturgeBackpackT1 = 
				BackpackManager.backpackInterface.addBackpack(configFile.getItem("thaumaturgePack1", itemIDBase++).getInt(),
				def, EnumBackpackType.T1);
		ItemManager.thaumaturgeBackpackT2 = 
				BackpackManager.backpackInterface.addBackpack(configFile.getItem("thaumaturgePack2", itemIDBase++).getInt(),
				def, EnumBackpackType.T2);
		
		ItemManager.magicCapsule = new ItemCapsule(ItemCapsule.Type.MAGIC, configFile.getItem("magicCapsule", itemIDBase++).getInt());
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
		GameRegistry.addRecipe(output, new Object[]
			{
				" W ", "W W", " W ",
				'W', ItemManager.wax
			});
		
		// Magic capsules
		output = new ItemStack(magicCapsule); output.stackSize = 4;
		GameRegistry.addRecipe(output, new Object[]
				{
				"WWW",
				'W', ItemManager.wax.getStackForType(ItemWax.WaxType.MAGIC)
				});
		
		output = new ItemStack(tcMiscResource, 1, ThaumcraftCompat.TCMiscResource.KNOWLEDGE_FRAGMENT.ordinal());
		GameRegistry.addShapelessRecipe(output, new Object[] 
				{ 
				ItemManager.miscResources.getStackForType(ResourceType.LORE_FRAGMENT),
				ItemManager.miscResources.getStackForType(ResourceType.LORE_FRAGMENT),
				ItemManager.miscResources.getStackForType(ResourceType.LORE_FRAGMENT),
				ItemManager.miscResources.getStackForType(ResourceType.LORE_FRAGMENT)
				});
		
		// T1 Thaumaturge's backpack
		GameRegistry.addRecipe(new ItemStack(ItemManager.thaumaturgeBackpackT1), new Object[] {
			"SWS", "NCN", "SWS",
			'S', Item.silk,
			'W', Block.cloth,
			'N', Item.goldNugget,
			'C', Block.chest
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
				new ItemStack[] {new ItemStack(tcShard, 1, ThaumcraftCompat.TCShardType.DULL.ordinal())},
				new int[] { 20 });
		RecipeManagers.centrifugeManager.addRecipe(25, ItemManager.propolis.getStackForType(PropolisType.AIR),
				new ItemStack[] {new ItemStack(tcShard, 1, ThaumcraftCompat.TCShardType.AIR.ordinal())},
				new int[] { 18 });
		RecipeManagers.centrifugeManager.addRecipe(25, ItemManager.propolis.getStackForType(PropolisType.FIRE),
				new ItemStack[] {new ItemStack(tcShard, 1, ThaumcraftCompat.TCShardType.FIRE.ordinal())},
				new int[] { 18 });
		RecipeManagers.centrifugeManager.addRecipe(25, ItemManager.propolis.getStackForType(PropolisType.WATER),
				new ItemStack[] {new ItemStack(tcShard, 1, ThaumcraftCompat.TCShardType.WATER.ordinal())},
				new int[] { 18 });
		RecipeManagers.centrifugeManager.addRecipe(25, ItemManager.propolis.getStackForType(PropolisType.EARTH),
				new ItemStack[] {new ItemStack(tcShard, 1, ThaumcraftCompat.TCShardType.EARTH.ordinal())},
				new int[] { 18 });
		
		// Squeezer recipes
		RecipeManagers.squeezerManager.addRecipe(20, new ItemStack[] {ItemManager.propolis.getStackForType(PropolisType.FIRE) },
				new LiquidStack(Block.lavaStill, 250),
				new ItemStack(tcShard, 1, ThaumcraftCompat.TCShardType.FIRE.ordinal()), 15);
		RecipeManagers.squeezerManager.addRecipe(20, new ItemStack[] {ItemManager.propolis.getStackForType(PropolisType.WATER) },
				new LiquidStack(Block.waterStill, 500),
				new ItemStack(tcShard, 1, ThaumcraftCompat.TCShardType.WATER.ordinal()), 25);
		
		// Carpenter recipes
		inputStack = ItemInterface.getItem("craftingMaterial");
		inputStack.setItemDamage(3); // Set to Silk Mesh
		output = new ItemStack(thaumaturgeBackpackT2);
		RecipeManagers.carpenterManager.addRecipe(200, new LiquidStack(Block.waterStill.blockID, 1000), null, output, new Object[] {
			"WXW", "WTW", "WWW",
			'X', Item.diamond,
			'W', inputStack,
			'T', new ItemStack(ItemManager.thaumaturgeBackpackT1) });
		
		output = BlockInterface.getBlock("candle");
		output.stackSize = 24;
		RecipeManagers.carpenterManager.addRecipe(30, new LiquidStack(Block.waterStill, 600), null, output, new Object[] {
			" S ", "WWW", "WWW",
			'W', ItemManager.wax,
			'S', Item.silk });
		output = BlockInterface.getBlock("candle");
		output.stackSize = 6;
		inputStack = ItemInterface.getItem("craftingMaterial");
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
	
	public static void postInit()
	{
		// Forestry liquids aren't registered until PostInit, that's why we wait until now to register them.
		ItemManager.magicCapsule.setUpLiquids();
	}

}
