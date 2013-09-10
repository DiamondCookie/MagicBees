package magicbees.main.utils.compat;

import magicbees.item.types.ResourceType;
import magicbees.main.CommonProxy;
import magicbees.main.Config;
import magicbees.main.MagicBees;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;

public class ThaumcraftHelper
{
	public enum MiscResource
	{
		ALUMENTUM,
		NITOR,
		THAUMIUM,
		QUICKSILVER,
		MAGIC_TALLOW,
		ZOMBIE_BRAIN,
		AMBER,
		ENCHANTED_FABRIC,
		FLUX_FILTER,
		KNOWLEDGE_FRAGMENT,
		MIRRORED_GLASS,
		;
	}
	
	public enum NuggetType
	{
		IRON,
		COPPER,
		TIN,
		SILVER,
		LEAD,
		QUICKSILVER,
		THAUMIUM,							_7,_8,_9,_10,_11,_12,_13,_14,_15,
		NATIVE_IRON,
		NATIVE_COPPER,
		NATIVE_TIN,
		NATIVE_SILVER,
		NATIVE_LEAD,						_21,_22,_23,_24,_25,_26,_27,_28,_29,_30,
		NATIVE_GOLD,
		;
	}
	
	public enum ShardType
	{
		AIR,
		FIRE,
		WATER,
		EARTH,
		ORDER,
		CHAOS,
		;
	}
	
	public enum Entity
	{
		BRAINY_ZOMBIE("entBrainyZombie", "EntityBrainyZombie"),
		GIANT_BRAINY_ZOMBIE("entGiantBrainyZombie", "EntityGiantBrainyZombie"),
		WISP("entWisp", "EntityWisp"),
		FIREBAT("entFirebat", "EntityFireBat"),
		;
		
		private static String packageName = "thaumcraft.common.entities.monster.";
		
		public String entityID;
		private String className;
		
		private Entity(String id, String clazz)
		{
			this.entityID = id;
			this.className = clazz;
		}
		
		public String getClassName()
		{
			return packageName + this.className;
		}
	}
	
	public enum BlockPlant
	{
		GREATWOOD_SAPLING,
		SILVERWOOD_SAPLING,
		SHIMMERLEAF,
		CINDERPEARL;
	}
	
	public enum TreeType
	{
		GREATWOOD,
		SILVERWOOD,
		;
	}
	
	private static boolean isThaumcraftPresent = false;
	public static final String Name = "Thaumcraft_";
	
	public static boolean isActive()
	{
		return isThaumcraftPresent;
	}
	
	public static void preInit()
	{
		if (cpw.mods.fml.common.Loader.isModLoaded(Name))
		{
			isThaumcraftPresent = true;
			//registerResearchXML();
		}
		else
		{
			// Switch off TC-dependant items.
			ResourceType.LORE_FRAGMENT.setHidden();
			ResourceType.TC_DUST_AIR.setHidden();
			ResourceType.TC_DUST_CHAOS.setHidden();
			ResourceType.TC_DUST_EARTH.setHidden();
			ResourceType.TC_DUST_FIRE.setHidden();
			ResourceType.TC_DUST_ORDER.setHidden();
			ResourceType.TC_DUST_WATER.setHidden();
		}
	}
	
	public static void init()
	{
		if (isActive())
		{
			getBlocks();
			getItems();
			
			//addItemsToBackpack();
		}
	}
	
	public static void postInit()
	{
		if (isActive())
		{
			setupResearch();
		}
	}
	
	public static void getBlocks()
	{
		Config.tcPlant = GameRegistry.findBlock(Name, "blockCustomPlant");
		Config.tcCandle = GameRegistry.findBlock(Name, "blockCandle");
		Config.tcCrystal = GameRegistry.findBlock(Name, "blockCrystal");
		Config.tcMarker = GameRegistry.findBlock(Name, "blockMarker");
		Config.tcJar = GameRegistry.findBlock(Name, "blockJar");
		Config.tcLog = GameRegistry.findBlock(Name, "blockMagicalLog");
		Config.tcLeaf = GameRegistry.findBlock(Name, "blockMagicalLeaves");
		Config.tcWarded = GameRegistry.findBlock(Name, "blockWarded");
	}
	
	public static void getItems()
	{
		Config.tcFilledJar = GameRegistry.findItem(Name, "BlockJarFilledItem");
		Config.tcMiscResource = GameRegistry.findItem(Name, "ItemResource");
		//Config.tcEssentiaBottle = GameRegistry.findItem(Name, "BlockJarFilledItem");
		Config.tcShard = GameRegistry.findItem(Name, "ItemShard");
		Config.tcGolem = GameRegistry.findItem(Name, "ItemGolemPlacer");
		//Config.tcWispEssence = ItemApi.getItem("itemWispEssence", 0).getItem();
		Config.tcNuggets = GameRegistry.findItem(Name, "ItemNugget");
		Config.tcShard = GameRegistry.findItem(Name, "ItemShard");
		Config.tcNuggetChicken = GameRegistry.findItem(Name, "ItemNuggetChicken");
		Config.tcNuggetBeef = GameRegistry.findItem(Name, "itemNuggetBeef");
		Config.tcNuggetPork = GameRegistry.findItem(Name, "itemNuggetPork");
	}

	public static void addItemsToBackpack()
	{
		if (MagicBees.getConfig().AddThaumcraftItemsToBackpacks)
		{
			// Add all shards and Thaumium to miner's backpack
			String ids = Config.tcShard.itemID + ":" + -1 + ";"
					+ Config.tcMiscResource.itemID + ":" + ThaumcraftHelper.MiscResource.THAUMIUM.ordinal();
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "miner@" + ids);
			
			// All resources go into Thaumaturge's backpack
			ids = Config.tcMiscResource.itemID + ":" + -1 + ";"
					+ Config.tcShard.itemID + ":" + -1 + ";"
					+ Config.tcFilledJar.itemID + ":" + -1 + ";"
					+ Config.tcCrystal.blockID + ":" + -1 + ";"
					+ Config.tcJar.blockID + ":" + -1 + ";"
					+ Config.tcGolem.itemID + ":" + -1 + ";";
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "thaumaturge@" + ids);
			
			// Add some plants & saplings to Forester's
			ids = Config.tcPlant.blockID + ":" + "-1" + ";"
					+ Config.tcLeaf.blockID + ":" + -1 + ";"
					+ Config.tcLog.blockID + ":" + "-1";
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "forester@" + ids);
			
			// Add Wisp & brains to Hunter's
			ids = /*Config.tcWispEssence.itemID + ":" + -1 + ";"
					+*/ Config.tcMiscResource.itemID + ":" + ThaumcraftHelper.MiscResource.ZOMBIE_BRAIN.ordinal();
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "hunter@" + ids);
			
			// Add Marker, warded stone, candle to builder
			ids = Config.tcCandle.blockID + ":" + -1 + ";"
					+ Config.tcMarker.blockID + ":" + -1 + ";"
					+ Config.tcWarded.blockID + ":" + -1;
			FMLInterModComms.sendMessage("Forestry", "add-backpack-items", "builder@" + ids);
		}
	}
	
	public static void setupResearch()
	{
		ResearchCategories.registerCategory("MAGICBEES", new ResourceLocation("magicbees", CommonProxy.ITEM_TEXTURE + "beeInfusion.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png"));
		
		ResearchItem startNode = new ResearchItem("MAGICBEES_ROOT", "MAGICBEES", new AspectList(), 0, 0, 0,
			Config.miscResources.getStackForType(ResourceType.RESEARCH_BEEINFUSION))
			.setAutoUnlock()
			.setPages(new ResearchPage[] { new ResearchPage("mb.researchPage.MBRoot.1") })
			.registerResearchItem();
	}
}
