package thaumicbees.main;

import java.io.File;

import thaumicbees.block.BlockMagicApiary;
import thaumicbees.block.BlockPlanks;
import thaumicbees.block.BlockWoodSlab;
import thaumicbees.block.TileEntityMagicApiary;
import thaumicbees.item.ItemCapsule;
import thaumicbees.item.ItemComb;
import thaumicbees.item.ItemDrop;
import thaumicbees.item.ItemMagicHiveFrame;
import thaumicbees.item.ItemMiscResources;
import thaumicbees.item.ItemPollen;
import thaumicbees.item.ItemPropolis;
import thaumicbees.item.ItemSolidFlux;
import thaumicbees.item.ItemWax;
import thaumicbees.item.types.CapsuleType;
import thaumicbees.item.types.HiveFrameType;
import thaumicbees.item.types.PlankType;
import thaumicbees.item.types.ResourceType;
import thaumicbees.main.utils.CompatabilityManager;
import thaumicbees.main.utils.LocalizationManager;
import thaumicbees.main.utils.compat.ForestryHelper;
import thaumicbees.main.utils.compat.ThaumcraftHelper;
import thaumicbees.storage.BackpackDefinition;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import forestry.api.apiculture.BeeManager;
import forestry.api.storage.BackpackManager;
import forestry.api.storage.EnumBackpackType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemMultiTextureTile;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;

/**
 * A class to hold some data related to mod state & functions.
 * @author MysteriousAges
 *
 */
public class Config
{
	public boolean DrawParticleEffects;	
	public boolean BeeInfusionsAdded;
	public boolean ExtraBeesInstalled;
	public boolean AddThaumcraftItemsToBackpacks;
	public String ThaumaturgeExtraItems;
	public int capsuleStackSizeMax;

	public static BlockPlanks planksWood;
	public static BlockWoodSlab slabWoodHalf;
	public static BlockWoodSlab slabWoodFull;
	public static BlockMagicApiary magicApiary;
	
	public static ItemComb combs;
	public static ItemWax wax;
	public static ItemPropolis propolis;
	public static ItemDrop drops;
	public static ItemPollen pollen;
	public static ItemSolidFlux solidFlux;
	public static ItemMiscResources miscResources;
	public static ItemFood jellyBaby;
	
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
		ThaumcraftHelper.getThaumcraftBlocks();
		ForestryHelper.getForestryBlocks();
		
		int blockIdBase = 1750;
		
		planksWood = new BlockPlanks(tbConfig.getBlock("planksTC", blockIdBase++).getInt());

		Item item = new ItemMultiTextureTile(planksWood.blockID - 256, planksWood, PlankType.getAllNames())
			.setItemName(planksWood.getBlockName()).setTextureFile(CommonProxy.TCBEES_ITEMS_IMAGE);
        Item.itemsList[planksWood.blockID] = item;
        
        OreDictionary.registerOre("plankWood", new ItemStack(planksWood, 1, -1));
        
        slabWoodFull = new BlockWoodSlab(tbConfig.getBlock("slabFull", blockIdBase++).getInt(), true);
        slabWoodHalf = new BlockWoodSlab(tbConfig.getBlock("slabHalf", blockIdBase++).getInt(), false);
        
        item = new ItemSlab(slabWoodHalf.blockID - 256, slabWoodHalf, slabWoodFull, false)
    		.setItemName(slabWoodHalf.getBlockName()).setTextureFile(CommonProxy.TCBEES_ITEMS_IMAGE);
	    Item.itemsList[slabWoodHalf.blockID] = item;
	    item = new ItemSlab(slabWoodFull.blockID - 256, slabWoodHalf, slabWoodFull, true)
			.setItemName(slabWoodFull.getBlockName()).setTextureFile(CommonProxy.TCBEES_ITEMS_IMAGE);
	    Item.itemsList[slabWoodFull.blockID] = item;
	    
	    OreDictionary.registerOre("slabWood", new ItemStack(slabWoodHalf, 1, -1));

        blockIdBase++; // Stair
        
		magicApiary = new BlockMagicApiary(tbConfig.getBlock("magicApiary", blockIdBase++).getInt());
		GameRegistry.registerBlock(magicApiary, "tb.magicApiary");
	}
	
	public void registerTileEntities()
	{
		TileEntity.addMapping(TileEntityMagicApiary.class, "MagicApiary");
	}
	
	public void setupItems()
	{
		ThaumcraftHelper.getThaumcraftItems();
		ForestryHelper.getForestryItems();
		
		int itemIDBase = 26090;
		
		Property p = tbConfig.getItem("combs", itemIDBase++);
		combs= new ItemComb(p.getInt());
		OreDictionary.registerOre("beeComb", new ItemStack(combs, 1, -1));
		
		wax = new ItemWax(tbConfig.getItem("wax", itemIDBase++).getInt());
		propolis = new ItemPropolis(tbConfig.getItem("propolis", itemIDBase++).getInt());
		drops = new ItemDrop(tbConfig.getItem("drop", itemIDBase++).getInt());
		miscResources = new ItemMiscResources(tbConfig.getItem("miscResources", itemIDBase++).getInt());
		

		// Make Aromatic Lumps a swarmer inducer. Chance is /1000.
		BeeManager.inducers.put(miscResources.getStackForType(ResourceType.AROMATIC_LUMP), 80);
		
		try
		{
			// 0x8700C6 = purpleish.
			String backpackName = LocalizationManager.getLocalizedString("tb.backpack.thaumaturge");
			BackpackDefinition def = new BackpackDefinition("thaumaturge", backpackName, 0x8700C6);
			thaumaturgeBackpackT1 = 
					BackpackManager.backpackInterface.addBackpack(tbConfig.getItem("thaumaturgePack1", itemIDBase++).getInt(),
					def, EnumBackpackType.T1);
			thaumaturgeBackpackT2 = 
					BackpackManager.backpackInterface.addBackpack(tbConfig.getItem("thaumaturgePack2", itemIDBase++).getInt(),
					def, EnumBackpackType.T2);
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
			throw new RuntimeException("Could not register backpacks via Forestry. Check your FML Client log and see if Forestry crashed silently.", e);
		}
		
		
		magicCapsule = new ItemCapsule(CapsuleType.MAGIC, tbConfig.getItem("magicCapsule", itemIDBase++).getInt(), this.capsuleStackSizeMax);
		
		pollen = new ItemPollen(tbConfig.getItem("pollen", itemIDBase++).getInt());
		solidFlux = new ItemSolidFlux(tbConfig.getItem("fluxCrystal", itemIDBase++).getInt());
		
		hiveFrameMagic = new ItemMagicHiveFrame(tbConfig.getItem("frameMagic", itemIDBase++).getInt(), HiveFrameType.MAGIC);
		hiveFrameResilient = new ItemMagicHiveFrame(tbConfig.getItem("frameResilient", itemIDBase++).getInt(), HiveFrameType.RESILIENT);
		hiveFrameGentle = new ItemMagicHiveFrame(tbConfig.getItem("frameGentle", itemIDBase++).getInt(), HiveFrameType.GENTLE);
		hiveFrameMetabolic = new ItemMagicHiveFrame(tbConfig.getItem("frameMetabolic", itemIDBase++).getInt(), HiveFrameType.METABOLIC);
		hiveFrameNecrotic = new ItemMagicHiveFrame(tbConfig.getItem("frameNecrotic", itemIDBase++).getInt(), HiveFrameType.NECROTIC);
		hiveFrameTemporal = new ItemMagicHiveFrame(tbConfig.getItem("frameTemporal", itemIDBase++).getInt(), HiveFrameType.TEMPORAL);
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
		
		// Jelly babies for Dr. Whoniversary.
		jellyBaby = new ItemFood(tbConfig.getItem("jellyBabies", itemIDBase++).getInt(), 1, false).setAlwaysEdible()
				.setPotionEffect(Potion.moveSpeed.id, 5, 1, 1f);
		jellyBaby.setTextureFile(CommonProxy.TCBEES_ITEMS_IMAGE).setIconIndex(19).setItemName("jellyBabies");
		
		voidCapsule = new ItemCapsule(CapsuleType.VOID, tbConfig.getItem("voidCapsule", itemIDBase++).getInt(), this.capsuleStackSizeMax);
	}
	
	private void doMiscConfig()
	{
		Property p;
		
		p = tbConfig.get("general", "backpack.thaumaturge.additionalItems", "");
		p.comment = "Add additional items to the Thaumaturge's Backpack." +
				"\n Format is the same as Forestry's: id:meta;id;id:meta (etc)";
		this.ThaumaturgeExtraItems = p.value;
		
		p = tbConfig.get("general", "backpack.forestry.addThaumcraftItems", true);
		p.comment = "Set to true if you want ThaumicBees to add several Thaumcraft blocks & items to Forestry backpacks." +
				"\n Set to false to disable.";
		this.AddThaumcraftItemsToBackpacks = p.getBoolean(true);
		
		p = tbConfig.get("general", "capsuleStackSize", 64);
		p.comment = "Allows you to edit the stack size of the capsules in ThaumicBees if using GregTech, \n" +
				"or the reduced capsule size in Forestry & Railcraft. Default: 64";
		this.capsuleStackSizeMax = p.getInt();
	}

}
