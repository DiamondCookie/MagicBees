package thaumicbees.main;

import java.io.File;

import thaumicbees.block.BlockMagicApiary;
import thaumicbees.compat.CompatabilityManager;
import thaumicbees.compat.ThaumcraftHelper;
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
import thaumicbees.storage.BackpackDefinition;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.storage.BackpackManager;
import forestry.api.storage.EnumBackpackType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

	
	public static BlockMagicApiary magicApiary;
	
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
	
	//----- Backpacks ------------------------------------------
	public static Item thaumaturgeBackpackT1;
	public static Item thaumaturgeBackpackT2;
	public static BackpackDefinition thaumaturgeBackpackDef;

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
		
		int blockIdBase = 1750;
		
		magicApiary = new BlockMagicApiary(tbConfig.getBlock("magicApiary", blockIdBase).getInt());
		GameRegistry.registerBlock(magicApiary, "Magic Apiary");
	}
	
	public void setupItems()
	{
		ThaumcraftHelper.getThaumcraftItems();
		
		int itemIDBase = 26090;
		
		Property p = tbConfig.getItem("combs", itemIDBase++);
		combs= new ItemComb(p.getInt());
		OreDictionary.registerOre("beeComb", new ItemStack(combs, 1, -1));
		
		wax = new ItemWax(tbConfig.getItem("wax", itemIDBase++).getInt());
		propolis = new ItemPropolis(tbConfig.getItem("propolis", itemIDBase++).getInt());
		drops = new ItemDrop(tbConfig.getItem("drop", itemIDBase++).getInt());
		miscResources = new ItemMiscResources(tbConfig.getItem("miscResources", itemIDBase++).getInt());
		
		try
		{
			// 0x8700C6 = purpleish.
			BackpackDefinition def = new BackpackDefinition("thaumaturge", "Thaumaturge's Backpack", 0x8700C6);
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
		
		
		magicCapsule = new ItemCapsule(CapsuleType.MAGIC, tbConfig.getItem("magicCapsule", itemIDBase++).getInt());
		
		pollen = new ItemPollen(tbConfig.getItem("pollen", itemIDBase++).getInt());
		solidFlux = new ItemSolidFlux(tbConfig.getItem("fluxCrystal", itemIDBase++).getInt());
		
		hiveFrameMagic = new ItemMagicHiveFrame(tbConfig.getItem("frameMagic", itemIDBase++).getInt(), HiveFrameType.MAGIC);
		hiveFrameResilient = new ItemMagicHiveFrame(tbConfig.getItem("frameResilient", itemIDBase++).getInt(), HiveFrameType.RESILIENT);
		hiveFrameGentle = new ItemMagicHiveFrame(tbConfig.getItem("frameGentle", itemIDBase++).getInt(), HiveFrameType.GENTLE);
		hiveFrameMetabolic = new ItemMagicHiveFrame(tbConfig.getItem("frameMetabolic", itemIDBase++).getInt(), HiveFrameType.METABOLIC);
		hiveFrameNecrotic = new ItemMagicHiveFrame(tbConfig.getItem("frameNecrotic", itemIDBase++).getInt(), HiveFrameType.NECROTIC);
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
	}

}
