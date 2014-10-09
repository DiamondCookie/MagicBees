package magicbees.main;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.apiculture.BeeManager;
import forestry.api.storage.BackpackManager;
import forestry.api.storage.EnumBackpackType;
import forestry.api.storage.IBackpackDefinition;
import magicbees.block.BlockEffectJar;
import magicbees.block.BlockHive;
import magicbees.block.BlockPlanks;
import magicbees.block.BlockWoodSlab;
import magicbees.block.types.HiveType;
import magicbees.block.types.PlankType;
import magicbees.item.*;
import magicbees.item.types.*;
import magicbees.main.utils.LocalizationManager;
import magicbees.main.utils.LogHelper;
import magicbees.main.utils.VersionInfo;
import magicbees.main.utils.compat.ThaumcraftHelper;
import magicbees.storage.BackpackDefinition;
import magicbees.tileentity.TileEntityEffectJar;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * A class to hold some data related to mod state & functions.
 * @author MysteriousAges
 *
 */
public class Config
{
	public boolean	DrawParticleEffects;	
	public boolean	BeeInfusionsAdded;
    public static boolean  ThaumaturgeBackpackActive;
	public boolean	AddThaumcraftItemsToBackpacks;
	public boolean	DisableUpdateNotification;
	public boolean	AreMagicPlanksFlammable;
	public boolean	UseImpregnatedStickInTools;
	public boolean	MoonDialShowsPhaseInText;
	public boolean	DoSpecialHiveGen;
	public String	ThaumaturgeExtraItems;
	public int		CapsuleStackSizeMax;
	public boolean	DoHiveRetrogen;
	public boolean	ForceHiveRegen;
    public static boolean  LogHiveSpawns;
    public static double ThaumcraftSaplingDroprate;

    public static boolean ArsMagicaActive;
    public static boolean BloodMagicActive;
    public static boolean EquivalentExchangeActive;
    public static boolean ExtraBeesActive;
    public static boolean RedstoneArsenalActive;
    public static boolean ThaumcraftActive;
    public static boolean ThermalExpansionActive;
	
	public float	MagnetBaseRange;
	public float	MagnetLevelMultiplier;
	public int		MagnetMaxLevel;
	
	public boolean	ForestryDebugEnabled;

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
	public static ItemMysteriousMagnet magnet;
	
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
	public static Block fHiveBlock;
	public static Block fAlvearyBlock;
	//----- Forestry Items -------------------------------------
	public static Item fBeeComb;
	public static Item fHoneydew;
	public static Item fHoneyDrop;
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
	//public static Item tcEssentiaBottle;
	public static Item tcShard;
	public static Item tcGolem;
	//public static Item tcWispEssence;
	public static Item tcNuggets;
	public static Item tcNuggetChicken;
	public static Item tcNuggetBeef;
	public static Item tcNuggetPork;
	//----- Equivalent Exchange Blocks -------------------------
	//----- Equivalent Exchange Items --------------------------
	public static Item eeMinuimShard;
	//----- Ars Magica Blocks ----------------------------------
	public static Block amResourceBlock;
	public static Block amBlackOrchid;
	public static Block amDesertNova;
	public static Block amAum;
	public static Block amWakebloom;
	public static Block amTarmaRoot;
	//----- Ars Magica Items -----------------------------------
	public static Item amItemResource;
	public static Item amEssence;
	//----Redstone Arsenal Items---
	public static ItemStack rsaFluxBlock;
	public static ItemStack rsaFluxNugget;	
	//---ThermalExpansion Items ---
	public static ItemStack teEnderiumBlock;
	public static ItemStack teElectrumBlock;
	public static ItemStack teInvarBlock;
	public static ItemStack teNickelBlock;
	public static ItemStack tePlatinumBlock;
	public static ItemStack teBronzeBlock;	
	public static ItemStack teEnderiumNugget;
	public static ItemStack teInvarNugget;
	public static ItemStack teElectrumNugget;
	public static ItemStack teNickelNugget;
	public static ItemStack tePlatinumNugget;
	public static ItemStack teDustCryotheum;
	public static ItemStack teDustBlizz;
	public static ItemStack teDustPyrotheum;
	public static ItemStack teDustSulfur;
	public static ItemStack teDustPlatinum;
	public static FluidStack teFluidGlowstone;
	public static FluidStack teFluidCoal;
	public static FluidStack teFluidRedstone;
	public static FluidStack teFluidEnder;
    //--- Blood Magic Blocks ---
    public static Block bmBloodStoneBrick;
    //--- Blood Magic Items ---
    public static ItemStack bmIncendium;
    public static ItemStack bmMagicales;
    public static ItemStack bmSanctus;
    public static ItemStack bmAether;
    public static ItemStack bmCrepitous;
    public static ItemStack bmCrystallos;
    public static ItemStack bmTerrae;
    public static ItemStack bmAquasalus;
    public static ItemStack bmTennebrae;


	
	

	//----- Config State info ----------------------------------
	public static Configuration configuration;
	
	public Config(File configFile)
	{
		this.configuration = new Configuration(configFile);
		this.configuration.load();
		this.doMiscConfig();
		
		this.ForestryDebugEnabled = (new File("./config/forestry/DEBUG.ON")).exists();
        this.configuration.save();
	}

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(VersionInfo.ModName))
        {
            if (configuration.hasChanged())
            {
                this.configuration.save();
            }
        }
    }

	public void saveConfigs()
	{
		this.configuration.save();
	}

	public void setupBlocks()
	{
		{
			if (ThaumcraftHelper.isActive())
			{
                /**
                planksWood = new BlockPlanks();
				planksWood.setBlockName("planks");

				//Item.itemsList[planksWood.blockID] = null;
		        //Item.itemsList[planksWood.blockID] = new ItemMultiTexture(planksWood.blockID - 256, planksWood,
				//	PlankType.getAllNames());

				//FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", MagicBees.getConfig().planksWood
				//	.blockID + "@" + PlankType.GREATWOOD.ordinal());
				//FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", MagicBees.getConfig().planksWood
				//	.blockID + "@" + PlankType.SILVERWOOD.ordinal());

		        slabWoodFull = new BlockWoodSlab(true);
		        slabWoodHalf = new BlockWoodSlab(false);

		        slabWoodFull.setBlockName("planks");
		        slabWoodHalf.setBlockName("planks");

			    /*Item.itemsList[slabWoodHalf.blockID] = null;
			    Item.itemsList[slabWoodHalf.blockID] = new ItemSlab(slabWoodHalf.blockID - 256, slabWoodHalf, slabWoodFull, false);
			    Item.itemsList[slabWoodFull.blockID] = null;
			    Item.itemsList[slabWoodFull.blockID] = new ItemSlab(slabWoodFull.blockID - 256, slabWoodHalf,
			    slabWoodFull, true);*/

		        //OreDictionary.registerOre("plankWood", new ItemStack(planksWood, 1, -1));
			    //OreDictionary.registerOre("slabWood", new ItemStack(slabWoodHalf, 1, -1));

			}
		}

		effectJar = new BlockEffectJar();
		GameRegistry.registerBlock(effectJar, "effectJar");
		GameRegistry.registerTileEntity(TileEntityEffectJar.class, TileEntityEffectJar.tileEntityName);

		hive = new BlockHive();
		GameRegistry.registerBlock(hive, ItemMagicHive.class, "hive");

		for (HiveType t : HiveType.values())
		{
			hive.setHarvestLevel("scoop", 0, t.ordinal() );
		}

		LogHelper.info("Replacing stupid-block with 'Here,  have some delicious textures' ItemBlock. This is 100%% normal.");
		/*Item.itemsList[hive.blockID] = null;
		Item.itemsList[hive.blockID] = new ItemMultiTextureTile(hive.blockID - 256, hive, HiveType.getAllNames());*/
	}
	
	public void setupItems()
	{		
		combs = new ItemComb();
		wax = new ItemWax();
		propolis = new ItemPropolis();
		drops = new ItemDrop();
		miscResources = new ItemMiscResources();
		
		// Make Aromatic Lumps a swarmer inducer. Chance is /1000.
		BeeManager.inducers.put(miscResources.getStackForType(ResourceType.AROMATIC_LUMP), 95);
		
		{
			if (ThaumcraftHelper.isActive() && ThaumaturgeBackpackActive)
			{
				try
				{
					// 0x8700C6 = purpleish.
					String backpackName = LocalizationManager.getLocalizedString("backpack.thaumaturge");
					BackpackDefinition def = new BackpackDefinition("thaumaturge", backpackName, 0x8700C6);
					thaumaturgeBackpackT1 = BackpackManager.backpackInterface.addBackpack(def, EnumBackpackType.T1);
					thaumaturgeBackpackT1.setUnlocalizedName("backpack.thaumaturgeT1");
                    GameRegistry.registerItem(thaumaturgeBackpackT1, "backpack.thaumaturgeT1");
					thaumaturgeBackpackT2 = BackpackManager.backpackInterface.addBackpack(def, EnumBackpackType.T2);
					thaumaturgeBackpackT2.setUnlocalizedName("backpack.thaumaturgeT2");
                    GameRegistry.registerItem(thaumaturgeBackpackT2, "backpack.thaumaturgeT2");
					// Add additional items from configs to backpack.
					if (MagicBees.getConfig().ThaumaturgeExtraItems.length() > 0)
					{
						FMLLog.info("Attempting to add extra items to Thaumaturge's backpack. If you get an error, check your MagicBees.conf.");
						FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "thaumaturge@" + MagicBees.getConfig().ThaumaturgeExtraItems);
					}
				}
				catch (Exception e)
				{
					FMLLog.severe("MagicBees encountered a problem during loading!");
					FMLLog.severe("Could not register backpacks via Forestry. Check your FML Client log and see if Forestry crashed silently.");
				}
			}
		}

		magicCapsule = new ItemCapsule(CapsuleType.MAGIC, this.CapsuleStackSizeMax);
		pollen = new ItemPollen();
		
		{
			if (ThaumcraftHelper.isActive())
			{
				solidFlux = new ItemCrystalAspect();
			}
		}
		
		hiveFrameMagic = new ItemMagicHiveFrame(HiveFrameType.MAGIC);
		hiveFrameResilient = new ItemMagicHiveFrame(HiveFrameType.RESILIENT);
		hiveFrameGentle = new ItemMagicHiveFrame(HiveFrameType.GENTLE);
		hiveFrameMetabolic = new ItemMagicHiveFrame(HiveFrameType.METABOLIC);
		hiveFrameNecrotic = new ItemMagicHiveFrame(HiveFrameType.NECROTIC);
		hiveFrameTemporal = new ItemMagicHiveFrame(HiveFrameType.TEMPORAL);
		hiveFrameOblivion = new ItemMagicHiveFrame(HiveFrameType.OBLIVION);
		
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(hiveFrameOblivion), 1, 1, 18));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(new ItemStack(hiveFrameOblivion), 1, 3, 23));

		jellyBaby = new ItemFood(1, false).setAlwaysEdible()
				.setPotionEffect(Potion.moveSpeed.id, 5, 1, 1f);
		jellyBaby.setUnlocalizedName(CommonProxy.DOMAIN + ":jellyBabies").setTextureName(CommonProxy.DOMAIN + ":jellyBabies");
		GameRegistry.registerItem(jellyBaby, "jellyBabies");
		
		
		voidCapsule = new ItemCapsule(CapsuleType.VOID, this.CapsuleStackSizeMax);

		{
			if (ThaumcraftHelper.isActive())
			{
				try {
					// Reflecting avoids the need to directly include the Thaumcraft API in the jar. BAM!
					Constructor ctor1 = Class.forName("magicbees.item.ItemThaumiumScoop").getConstructor();
					thaumiumScoop = new ItemThaumiumScoop();
					GameRegistry.registerItem(thaumiumScoop, thaumiumScoop.getUnlocalizedName(), CommonProxy.DOMAIN);
					
					Constructor ctor2 = Class.forName("magicbees.item.ItemThaumiumGrafter").getConstructor();
					thaumiumGrafter = new ItemThaumiumGrafter();
					GameRegistry.registerItem(thaumiumGrafter, thaumiumGrafter.getUnlocalizedName(), CommonProxy.DOMAIN);
				} catch (Exception e) { } 
			}
		}
		
		moonDial = new ItemMoonDial();
		
		nuggets = new ItemNugget();
		
		magnet = new ItemMysteriousMagnet();
		magnet.setBaseRange(MagnetBaseRange);
		magnet.setLevelMultiplier(MagnetLevelMultiplier);
		magnet.setMaximumLevel(MagnetMaxLevel);
        GameRegistry.registerItem(magnet, "magnet", CommonProxy.DOMAIN);
		
		for (int level = 0; level <= 8; level++)
		{
			OreDictionary.registerOre("mb.magnet.level" + level, new ItemStack(magnet, 1, level * 2));
			OreDictionary.registerOre("mb.magnet.level" + level, new ItemStack(magnet, 1, level * 2 + 1));
		}


		OreDictionary.registerOre("beeComb", new ItemStack(combs, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("waxMagical", wax.getStackForType(WaxType.MAGIC));
		OreDictionary.registerOre("waxMagical", wax.getStackForType(WaxType.AMNESIC));
		OreDictionary.registerOre("nuggetIron", nuggets.getStackForType(NuggetType.IRON));
		OreDictionary.registerOre("nuggetCopper", nuggets.getStackForType(NuggetType.COPPER));
		OreDictionary.registerOre("nuggetTin", nuggets.getStackForType(NuggetType.TIN));
		OreDictionary.registerOre("nuggetSilver", nuggets.getStackForType(NuggetType.SILVER));
		OreDictionary.registerOre("nuggetLead", nuggets.getStackForType(NuggetType.LEAD));
		OreDictionary.registerOre("shardDiamond", nuggets.getStackForType(NuggetType.DIAMOND));
		OreDictionary.registerOre("shardEmerald", nuggets.getStackForType(NuggetType.EMERALD));
		OreDictionary.registerOre("shardApatite", nuggets.getStackForType(NuggetType.APATITE));


        String item;
        for (NuggetType type: NuggetType.values())
        {
            LogHelper.info("Found nugget of type " + type.toString());
            item = type.toString().toLowerCase();
            item = Character.toString(item.charAt(0)).toUpperCase() + item.substring(1);
            if (OreDictionary.getOres("ingot" + item).size() <= 0)
            {
                if (OreDictionary.getOres("shard" + item).size() <= 0)
                {
                    LogHelper.info("Disabled nugget " + type.toString());
                    type.setInactive();
                }
            }

        }

        GameRegistry.registerItem(nuggets, "beeNugget");
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

        p = configuration.get("general", "backpack.thaumaturge.active", true);
        p.comment = "Set to false to disable the Thaumaturge backpack";
        this.ThaumaturgeBackpackActive = p.getBoolean(true);

		p = configuration.get("general", "backpack.thaumaturge.additionalItems", "");
		p.comment = "Add additional items to the Thaumaturge's Backpack." +
				"\n Format is the same as Forestry's: id:meta;id;id:meta (etc)";
		this.ThaumaturgeExtraItems = p.getString();
		
		p = configuration.get("general", "backpack.forestry.addThaumcraftItems", true);
		p.comment = "Set to true if you want MagicBees to add several Thaumcraft blocks & items to Forestry backpacks." +
				"\n Set to false to disable.";
		this.AddThaumcraftItemsToBackpacks = p.getBoolean(true);
		
		p = configuration.get("general", "capsuleStackSize", 64);
		p.comment = "Allows you to edit the stack size of the capsules in MagicBees if using GregTech, \n" +
				"or the reduced capsule size in Forestry & Railcraft. Default: 64";
		this.CapsuleStackSizeMax = p.getInt();
		
		p = configuration.get("general", "disableVersionNotification", false);
		p.comment = "Set to true to stop ThaumicBees from notifying you when new updates are available. (Does not supress critical updates)";
		this.DisableUpdateNotification = p.getBoolean(false);
		
		p = configuration.get("general", "areMagicPlanksFlammable", false);
		p.comment = "Set to true to allow Greatwood & Silverwood planks to burn in a fire.";
		this.AreMagicPlanksFlammable = p.getBoolean(false);
		
		p = configuration.get("general", "useImpregnatedStickInTools", false);
		p.comment = "Set to true to make Thaumium Grafter & Scoop require impregnated sticks in the recipe.";
		this.UseImpregnatedStickInTools = p.getBoolean(false);

        p = configuration.get("general", "thaumCraftSaplingDroprate", 0.1, "The chance for thaumcraft saplings using the thaumium grafter", 0.0, 1.0);
        this.ThaumcraftSaplingDroprate = p.getDouble(0.1);
		
		p = configuration.get("general", "moonDialShowText", false);
		p.comment = "set to true to show the current moon phase in mouse-over text.";
		this.MoonDialShowsPhaseInText = p.getBoolean(false);
		
		p = configuration.get("general", "doSpecialHiveGen", true);
		p.comment = "Set to false if you hate fun and do not want special hives generating in Magic biomes.";
		this.DoSpecialHiveGen = p.getBoolean(true);
		
		p = configuration.get("Retrogen", "doHiveRetrogen", false);
		p.comment = "Set to true to enable retroactive worldgen of Magic Bees hives.";
		this.DoHiveRetrogen = p.getBoolean(false);
		
		p = configuration.get("Retrogen", "forceHiveRegen", false);
		p.comment = "Set to true to force a regeneration of Magic Bees hives. Will set config option to false after parsed. (Implies doHiveRetrogen=true)";
		this.ForceHiveRegen = p.getBoolean(false);
		
		if (this.ForceHiveRegen)
		{
			FMLLog.info("Magic Bees will aggressively regenerate hives in all chunks for this game instance. Config option set to false.");
			p.set(false);
			this.DoHiveRetrogen = true;
		}
		else if (this.DoHiveRetrogen)
		{
			FMLLog.info("Magic Bees will attempt to regenerate hives in chunks that were generated before the mod was added.");
		}

		p = configuration.get("general", "magnetRangeBase", 3.0);
		p.comment = "Base range (in blocks) of the Mysterious Magnet";
		this.MagnetBaseRange = (float)p.getDouble(3.0);
		
		p = configuration.get("general", "magnetRangeMultiplier", 0.75);
		p.comment = "Range multiplier per level of the Mysterious Magnet. Total range = base range + level * multiplier";
		this.MagnetLevelMultiplier = (float)p.getDouble(0.75);
		
		p = configuration.get("general", "magnetMaximumLevel", 8);
		p.comment = "Maximum level of the magnets.";
		this.MagnetMaxLevel = p.getInt();

        //Modules
        p = configuration.get("modules", "ArsMagica", true);
        this.ArsMagicaActive = p.getBoolean();

        p = configuration.get("modules", "BloodMagic", true);
        this.BloodMagicActive = p.getBoolean();

        p = configuration.get("modules", "EquivalentExchange", true);
        this.EquivalentExchangeActive = p.getBoolean();

        p = configuration.get("modules", "ExtraBees", true);
        this.ExtraBeesActive = p.getBoolean();

        p = configuration.get("modules", "RedstoneArsenal", true);
        this.RedstoneArsenalActive = p.getBoolean();

        p = configuration.get("modules", "Thaumcraft", true);
        this.ThaumcraftActive = p.getBoolean();

        p = configuration.get("modules", "ThermalExpansion", true);
        this.ThermalExpansionActive = p.getBoolean();

        // Debug
        p = configuration.get("debug", "logHiveSpawns", false);
        p.comment = "Enable to see exact locations of MagicBees hive spawns.";
        this.LogHiveSpawns = p.getBoolean();
	}

}
