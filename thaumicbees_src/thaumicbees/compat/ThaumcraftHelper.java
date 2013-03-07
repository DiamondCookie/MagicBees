package thaumicbees.compat;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import thaumcraft.api.ItemApi;
import thaumicbees.main.Config;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ThaumcraftHelper
{
	public static void getThaumcraftBlocks()
	{
		Config.tcPlant = Block.blocksList[ItemApi.getItem("blockCustomPlant", 0).itemID];
		Config.tcCandle = Block.blocksList[ItemApi.getItem("blockCandle", 0).itemID];
		Config.tcCrystal = Block.blocksList[ItemApi.getItem("blockCrystal", 0).itemID];
		Config.tcMarker = Block.blocksList[ItemApi.getItem("blockMarker", 0).itemID];
		Config.tcJar = Block.blocksList[ItemApi.getItem("blockJar", 0).itemID];
		Config.tcLog = Block.blocksList[ItemApi.getItem("blockMagicalLog", 0).itemID];
		Config.tcLeaf = Block.blocksList[ItemApi.getItem("blockMagicalLeaves", 0).itemID];
		Config.tcWarded = Block.blocksList[ItemApi.getItem("blockWarded", 0).itemID];
	}
	
	public static void getThaumcraftItems()
	{
		Config.tcFilledJar = Item.itemsList[ItemApi.getItem("itemJarFilled", 0).itemID];
		Config.tcMiscResource = Item.itemsList[ItemApi.getItem("itemResource", 0).itemID];
		Config.tcEssentiaBottle = Item.itemsList[ItemApi.getItem("itemEssence", 0).itemID];
		Config.tcShard = Item.itemsList[ItemApi.getItem("itemShard", 0).itemID];
		Config.tcGolem = Item.itemsList[ItemApi.getItem("itemGolemPlacer", 0).itemID];
		Config.tcWispEssence = Item.itemsList[ItemApi.getItem("itemWispEssence", 0).itemID];
		Config.tcNuggets = Item.itemsList[ItemApi.getItem("itemNugget", 0).itemID];
	}
	
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
		QUICKSILVER,						_6,_7,_8,_9,_10,_11,_12,_13,_14,_15,
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
		MAGIC,
		DULL,
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
		SILVERLEAF,
		CINDERPEARL;
	}
}
