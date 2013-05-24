package magicbees.main;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import magicbees.block.BlockEffectJar;
import magicbees.block.BlockHive;
import magicbees.block.BlockPlanks;
import magicbees.block.BlockWoodSlab;
import magicbees.block.types.HiveType;
import magicbees.block.types.PlankType;
import magicbees.item.ItemCapsule;
import magicbees.item.ItemComb;
import magicbees.item.ItemCrystalAspect;
import magicbees.item.ItemDrop;
import magicbees.item.ItemMagicHiveFrame;
import magicbees.item.ItemMiscResources;
import magicbees.item.ItemMoonDial;
import magicbees.item.ItemNugget;
import magicbees.item.ItemPollen;
import magicbees.item.ItemPropolis;
import magicbees.item.ItemWax;
import magicbees.item.types.CapsuleType;
import magicbees.item.types.HiveFrameType;
import magicbees.item.types.NuggetType;
import magicbees.item.types.ResourceType;
import magicbees.main.utils.LocalizationManager;
import magicbees.main.utils.compat.ArsMagicaHelper;
import magicbees.main.utils.compat.EquivalentExchangeHelper;
import magicbees.main.utils.compat.ForestryHelper;
import magicbees.main.utils.compat.ThaumcraftHelper;
import magicbees.storage.BackpackDefinition;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemMultiTextureTile;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.apiculture.BeeManager;
import forestry.api.storage.BackpackManager;
import forestry.api.storage.EnumBackpackType;

/**
 * A class to hold some data related to mod state & functions.
 * @author MysteriousAges
 *
 */
public class Config
{
	public boolean DrawParticleEffects;	
	public boolean BeeInfusionsAdded;
	public boolean AddThaumcraftItemsToBackpacks;
	public boolean DisableUpdateCheck;
	public boolean AreMagicPlanksFlammable;
	public boolean UseImpregnatedStickInTools;
	public boolean MoonDialShowsPhaseInText;
	public String ThaumaturgeExtraItems;
	public int CapsuleStackSizeMax;

	public static BlockPlanks planksWood;
	public static BlockWoodSlab slabWoodHalf;
	public static BlockWoodSlab slabWoodFull;
	public static BlockEffectJar effectJar;
	public static BlockHive hive;
	
	public static ItemComb combs;
	public static ItemWax wax;
	public static ItemPropolis propolis;
	public static ItemDrop drops;
	public static ItemPollen pollen;
	public static ItemCrystalAspect solidFlux;
	public static ItemMiscResources miscResources;
	public static ItemFood jellyBaby;
	public static Item thaumiumScoop;
	public static Item thaumiumGrafter;
	public static ItemNugget nuggets;
	public static ItemMoonDial moonDial;
	
	//----- Liquid Capsules --------------------
	public static ItemCapsule magicCapsule;
	public static ItemCapsule voidCapsule;
	
	//----- Apiary Frames ----------------------
	public static ItemMagicHiveFrame hiveFrameMagic;
	public static ItemMagicHiveFrame hiveFrameResilient;
	public static ItemMagicHiveFrame hiveFrameGentle;
	public static ItemMagicHiveFrame hiveFrameMetabolic;
	public static ItemMagicHiveFrame hiveFrameNecrotic;
	public static ItemMagicHiveFrame hiveFrameTemporal;
	public static ItemMagicHiveFrame hiveFrameOblivion;
	
	//----- Backpacks ------------------------------------------
	public static Item thaumaturgeBackpackT1;
	public static Item thaumaturgeBackpackT2;
	public static BackpackDefinition thaumaturgeBackpackDef;

	//----- Forestry Blocks ------------------------------------
	//----- Forestry Items -------------------------------------
	public static Item fBeeComb;
	public static Item fPollen;
	public static Item fCraftingResource;
	//----- Thaumcraft Blocks ----------------------------------
	public static Block tcPlant;
	public static Block tcCandle;
	public static Block tcCrystal;
	public static Block tcMarker;
	public static Block tcJar;
	public static Block tcLog;
	public static Block tcLeaf;
	public static Block tcWarded;
	//----- Thaumcraft Items -----------------------------------
	public static Item tcFilledJar;
	public static Item tcMiscResource;
	public static Item tcEssentiaBottle;
	public static Item tcShard;
	public static Item tcGolem;
	public static Item tcWispEssence;
	public static Item tcNuggets;
	public static Item tcNuggetChicken;
	public static Item tcNuggetBeef;
	public static Item tcNuggetPork;
	//----- Equivalent Exchange Blocks -------------------------
	//----- Equivalent Exchange Items --------------------------
	public static Item eeMinuimShard;
	//----- Ars Magica Blocks ----------------------------------
	public static Block amBlackOrchid;
	public static Block amDesertNova;
	//----- Ars Magica Items -----------------------------------
	public static Item amVinteumDust;
	public static Item amArcaneCompound;
	public static Item amEssenceArcane;
	public static Item amEssenceEarth;
	public static Item amEssenceWater;
	public static Item amEssenceFire;
	public static Item amEssenceAir;
	public static Item amEssenceMagma;
	public static Item amEssencePlant;
	public static Item amEssenceIce;
	public static Item amEssenceLightning;
	

	//----- Config State info ----------------------------------
	private Configuration tbConfig;
	
	public Config(File configFile)
	{
		this.tbConfig = new Configuration(configFile);
		
		this.tbConfig.load();
		this.doMiscConfig();
	}
	
	public void saveConfigs()
	{
		this.tbConfig.save();
	}

	public void setupBlocks()
	{
		ThaumcraftHelper.getBlocks();
		ForestryHelper.getBlocks();
		EquivalentExchangeHelper.getBlocks();
		ArsMagicaHelper.getBlocks();
		
		int blockIdBase = 1750;
		
		{
			int plankId = tbConfig.getBlock("planksTC", blockIdBase++).getInt();
			int slabFullId = tbConfig.getBlock("slabFull", blockIdBase++).getInt();
			int slabHalfId = tbConfig.getBlock("slabHalf", blockIdBase++).getInt();
			
			if (ThaumcraftHelper.isActive())
			{
				planksWood = new BlockPlanks(plankId);
				planksWood.setUnlocalizedName("tb.planks");

				Item.itemsList[planksWood.blockID] = null;
		        Item.itemsList[planksWood.blockID] = new ItemMultiTextureTile(planksWood.blockID - 256, planksWood, PlankType.getAllNames());

		        OreDictionary.registerOre("plankWood", new ItemStack(planksWood, 1, -1));
				FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", ThaumicBees.getConfig().planksWood.blockID + "@" + PlankType.GREATWOOD.ordinal());
				FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", ThaumicBees.getConfig().planksWood.blockID + "@" + PlankType.SILVERWOOD.ordinal());
		        
		        slabWoodFull = new BlockWoodSlab(slabFullId, true);
		        slabWoodHalf = new BlockWoodSlab(slabHalfId, false);
		        
		        slabWoodFull.setUnlocalizedName("tb.planks");
		        slabWoodHalf.setUnlocalizedName("tb.planks");

			    Item.itemsList[slabWoodHalf.blockID] = null;
			    Item.itemsList[slabWoodHalf.blockID] = new ItemSlab(slabWoodHalf.blockID - 256, slabWoodHalf, slabWoodFull, false);
			    Item.itemsList[slabWoodFull.blockID] = null;
			    Item.itemsList[slabWoodFull.blockID] = new ItemSlab(slabWoodFull.blockID - 256, slabWoodHalf, slabWoodFull, true);
			    
			    OreDictionary.registerOre("slabWood", new ItemStack(slabWoodHalf, 1, -1));
			}
		}
		
		effectJar = new BlockEffectJar(tbConfig.getBlock("effectJar", blockIdBase++).getInt());
		GameRegistry.registerBlock(effectJar, "tb.effectJar");
		
		hive = new BlockHive(tbConfig.getBlock("hives", blockIdBase++).getInt());
		GameRegistry.registerBlock(hive, "tb.hive");
		
		Item.itemsList[hive.blockID] = null;
		Item.itemsList[hive.blockID] = new ItemMultiTextureTile(hive.blockID - 256, hive, HiveType.getAllNames());
	}
	
	public void setupItems()
	{
		ThaumcraftHelper.getItems();
		ForestryHelper.getItems();
		EquivalentExchangeHelper.getItems();
		ArsMagicaHelper.getItems();
		
		int itemIDBase = 26090;

		combs= new ItemComb(tbConfig.getItem("combs", itemIDBase++).getInt());
		wax = new ItemWax(tbConfig.getItem("wax", itemIDBase++).getInt());
		propolis = new ItemPropolis(tbConfig.getItem("propolis", itemIDBase++).getInt());
		drops = new ItemDrop(tbConfig.getItem("drop", itemIDBase++).getInt());
		miscResources = new ItemMiscResources(tbConfig.getItem("miscResources", itemIDBase++).getInt());
		
		// Make Aromatic Lumps a swarmer inducer. Chance is /1000.
		BeeManager.inducers.put(miscResources.getStackForType(ResourceType.AROMATIC_LUMP), 80);
		
		{
			int tier1 = tbConfig.getItem("thaumaturgePack1", itemIDBase++).getInt();
			int tier2 = tbConfig.getItem("thaumaturgePack2", itemIDBase++).getInt();
			
			if (ThaumcraftHelper.isActive())
			{
				try
				{
					// 0x8700C6 = purpleish.
					String backpackName = LocalizationManager.getLocalizedString("tb.backpack.thaumaturge");
					BackpackDefinition def = new BackpackDefinition("thaumaturge", backpackName, 0x8700C6);
					thaumaturgeBackpackT1 = BackpackManager.backpackInterface.addBackpack(tier1, def, EnumBackpackType.T1);					
					thaumaturgeBackpackT2 = BackpackManager.backpackInterface.addBackpack(tier2, def, EnumBackpackType.T2);
					// Add additional items from configs to backpack.
					if (ThaumicBees.getConfig().ThaumaturgeExtraItems.length() > 0)
					{
						FMLLog.info("Attempting to add extra items to Thaumaturge's backpack! If you get an error, check your ThaumicBees.conf.");
						FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "thaumaturge@" + ThaumicBees.getConfig().ThaumaturgeExtraItems);
					}
				}
				catch (Exception e)
				{
					FMLLog.severe("ThaumicBees encountered a problem during loading!");
					FMLLog.severe("Could not register backpacks via Forestry. Check your FML Client log and see if Forestry crashed silently.");
				}
			}
		}
		
		magicCapsule = new ItemCapsule(CapsuleType.MAGIC, tbConfig.getItem("magicCapsule", itemIDBase++).getInt(), this.CapsuleStackSizeMax);
		pollen = new ItemPollen(tbConfig.getItem("pollen", itemIDBase++).getInt());
		
		{
			int crystalId = tbConfig.getItem("fluxCrystal", itemIDBase++).getInt();
			if (ThaumcraftHelper.isActive())
			{
				solidFlux = new ItemCrystalAspect(crystalId);
			}
		}
		
		hiveFrameMagic = new ItemMagicHiveFrame(tbConfig.getItem("frameMagic", itemIDBase++).getInt(), HiveFrameType.MAGIC);
		hiveFrameResilient = new ItemMagicHiveFrame(tbConfig.getItem("frameResilient", itemIDBase++).getInt(), HiveFrameType.RESILIENT);
		hiveFrameGentle = new ItemMagicHiveFrame(tbConfig.getItem("frameGentle", itemIDBase++).getInt(), HiveFrameType.GENTLE);
		hiveFrameMetabolic = new ItemMagicHiveFrame(tbConfig.getItem("frameMetabolic", itemIDBase++).getInt(), HiveFrameType.METABOLIC);
		hiveFrameNecrotic = new ItemMagicHiveFrame(tbConfig.getItem("frameNecrotic", itemIDBase++).getInt(), HiveFrameType.NECROTIC);
		hiveFrameTemporal = new ItemMagicHiveFrame(tbConfig.getItem("frameTemporal", itemIDBase++).getInt(), HiveFrameType.TEMPORAL);
		hiveFrameOblivion = new ItemMagicHiveFrame(tbConfig.getItem("frameOblivion", itemIDBase++).getInt(), HiveFrameType.OBLIVION);
		// Future frames, so they all are clumped together.
		itemIDBase++;
		itemIDBase++;
		itemIDBase++;
		itemIDBase++;
		itemIDBase++;
		itemIDBase++;
		itemIDBase++;
		itemIDBase++;
		
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(hiveFrameOblivion), 1, 1, 18));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(new ItemStack(hiveFrameOblivion), 1, 3, 23));

		jellyBaby = new ItemFood(tbConfig.getItem("jellyBabies", itemIDBase++).getInt(), 1, false).setAlwaysEdible()
				.setPotionEffect(Potion.moveSpeed.id, 5, 1, 1f);
		jellyBaby.setUnlocalizedName("thaumicbees:jellyBabies");
		GameRegistry.registerItem(jellyBaby, "thaumicbees:jellyBabies");
		
		
		voidCapsule = new ItemCapsule(CapsuleType.VOID, tbConfig.getItem("voidCapsule", itemIDBase++).getInt(), this.CapsuleStackSizeMax);

		{
			int scoopID = tbConfig.getItem("thaumiumScoop", itemIDBase++).getInt();
			int grafterID = tbConfig.getItem("thaumiumGrafter", itemIDBase++).getInt();
			if (ThaumcraftHelper.isActive())
			{
				try {
					// Reflecting avoids the need to directly include the Thaumcraft API in the jar. BAM!
					Constructor ctor1 = Class.forName("thaumicbees.item.ItemThaumiumScoop").getConstructor(int.class);
					thaumiumScoop = (Item)ctor1.newInstance(scoopID);
					MinecraftForge.setToolClass(thaumiumScoop, "scoop", 3);
					GameRegistry.registerItem(thaumiumScoop, thaumiumScoop.getUnlocalizedName());
					
					Constructor ctor2 = Class.forName("thaumicbees.item.ItemThaumiumGrafter").getConstructor(int.class);
					thaumiumGrafter = (Item)ctor2.newInstance(grafterID);
					MinecraftForge.setToolClass(thaumiumGrafter, "grafter", 3);
					GameRegistry.registerItem(thaumiumGrafter, thaumiumGrafter.getUnlocalizedName());
				} catch (Exception e) { } 
			}
		}
		
		moonDial = new ItemMoonDial(tbConfig.getItem("moonDial", itemIDBase++).getInt());
		
		// Other tools I might need in the future
		itemIDBase++;
		
		nuggets = new ItemNugget(tbConfig.getItem("beeNuggets", itemIDBase++).getInt());
		

		OreDictionary.registerOre("beeComb", new ItemStack(combs, 1, -1));
		OreDictionary.registerOre("nuggetIron", nuggets.getStackForType(NuggetType.IRON));
		OreDictionary.registerOre("nuggetCopper", nuggets.getStackForType(NuggetType.COPPER));
		OreDictionary.registerOre("nuggetTin", nuggets.getStackForType(NuggetType.TIN));
		OreDictionary.registerOre("nuggetSilver", nuggets.getStackForType(NuggetType.SILVER));
		OreDictionary.registerOre("nuggetLead", nuggets.getStackForType(NuggetType.LEAD));
		OreDictionary.registerOre("shardDiamond", nuggets.getStackForType(NuggetType.DIAMOND));
		OreDictionary.registerOre("shardEmerald", nuggets.getStackForType(NuggetType.EMERALD));
	}
	
	private void doMiscConfig()
	{
		Property p;
		
		// Pull config from Forestry via reflection
		Field f;
		try
		{
			f = Class.forName("forestry.core.config.Config").getField("enableParticleFX");
			this.DrawParticleEffects = f.getBoolean(null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		p = tbConfig.get("general", "backpack.thaumaturge.additionalItems", "");
		p.comment = "Add additional items to the Thaumaturge's Backpack." +
				"\n Format is the same as Forestry's: id:meta;id;id:meta (etc)";
		this.ThaumaturgeExtraItems = p.getString();
		
		p = tbConfig.get("general", "backpack.forestry.addThaumcraftItems", true);
		p.comment = "Set to true if you want ThaumicBees to add several Thaumcraft blocks & items to Forestry backpacks." +
				"\n Set to false to disable.";
		this.AddThaumcraftItemsToBackpacks = p.getBoolean(true);
		
		p = tbConfig.get("general", "capsuleStackSize", 64);
		p.comment = "Allows you to edit the stack size of the capsules in ThaumicBees if using GregTech, \n" +
				"or the reduced capsule size in Forestry & Railcraft. Default: 64";
		this.CapsuleStackSizeMax = p.getInt();
		
		p = tbConfig.get("general", "disableVersionCheck", false);
		p.comment = "Set to true to stop ThaumicBees from checking for updates.";
		this.DisableUpdateCheck = p.getBoolean(false);
		
		p = tbConfig.get("general", "areMagicPlanksFlammable", false);
		p.comment = "Set to true to allow Greatwood & Silverwood planks to burn in a fire.";
		this.AreMagicPlanksFlammable = p.getBoolean(false);
		
		p = tbConfig.get("general", "useImpregnatedStickInTools", false);
		p.comment = "Set to true to make Thaumium Grafter & Scoop require impregnated sticks in the recipe.";
		this.UseImpregnatedStickInTools = p.getBoolean(false);
		
		p = tbConfig.get("general", "moonDialShowText", false);
		p.comment = "set to true to show the current moon phase in mouse-over text.";
		this.MoonDialShowsPhaseInText = p.getBoolean(false);
	}

}
