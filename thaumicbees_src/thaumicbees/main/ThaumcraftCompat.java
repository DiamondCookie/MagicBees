package thaumicbees.main;

import java.io.File;

import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumicbees.block.BlockManager;
import thaumicbees.item.ItemManager;
import thaumicbees.item.ItemWax.WaxType;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Property;
import net.minecraftforge.common.Configuration;

public class ThaumcraftCompat
{
	private static final int blockMetaSilverleaf = 2;
	private static final int blockMetaCinderpearl = 3;

	private static int itemResourceID;
	private static final int itemResourceQuicksilverMeta = 3;
	private static final int itemResourceAmberMeta = 6;
	private static final int itemResourceFragmentMeta = 9;
	
	private static int itemShardID;
	private static final int itemShardAirMeta = 0;
	private static final int itemShardFireMeta = 1;
	private static final int itemShardWaterMeta = 2;
	private static final int itemShardEarthMeta = 3;
	private static final int itemShardMagicMeta = 4;
	private static final int itemShardDullMeta = 5;
	
	private static int itemEssentiaBottle;
	
	public static void init()
	{
		Configuration tcConfig = new Configuration(new File("./config/Thaumcraft.cfg"));
		Property p;
		
		tcConfig.load();
		
		// Load blocks out of the Thaumcraft config to get their IDs;
		p = tcConfig.getBlock("BlockCustomPlant", 2403);
		BlockManager.ThaumcraftPlant = Block.blocksList[p.getInt()];
		
		// Load items out of the Thaumcraft config to get their IDs.
		p = tcConfig.getItem("ItemEssence", 25005);
		itemEssentiaBottle = p.getInt() + 256;
		ItemManager.essentiaBottle = new ItemStack(itemEssentiaBottle, 1, 0);
		p = tcConfig.getItem("ItemResource", 25007);
		itemResourceID = p.getInt() + 256;
		ItemManager.quicksilver = new ItemStack(itemResourceID, 1, itemResourceQuicksilverMeta);
		ItemManager.amber = new ItemStack(itemResourceID, 1, itemResourceAmberMeta);
		ItemManager.fragment = new ItemStack(itemResourceID, 1, itemResourceFragmentMeta);
		p = tcConfig.getItem("ItemShard", 25008);
		itemShardID = p.getInt() + 256;
		ItemManager.airShard = new ItemStack(itemShardID, 1, itemShardAirMeta);
		ItemManager.fireShard = new ItemStack(itemShardID, 1, itemShardFireMeta);
		ItemManager.waterShard = new ItemStack(itemShardID, 1, itemShardWaterMeta);
		ItemManager.earthShard = new ItemStack(itemShardID, 1, itemShardEarthMeta);
		ItemManager.magicShard = new ItemStack(itemShardID, 1, itemShardMagicMeta);
		ItemManager.dullShard = new ItemStack(itemShardID, 1, itemShardDullMeta);
	}
	
	public static void setupResearch()
	{
		//ThaumcraftApi.registerResearchXML(CommonProxy.TCBEES_RESEARCH + "/research.xml");
	}
	
	public static void setupAspects()
	{
		ObjectTags tags;
		
		tags = new ObjectTags().add(EnumTag.MAGIC, 2);
		ThaumcraftApi.registerObjectTag(ItemManager.wax.shiftedIndex, WaxType.MAGIC.ordinal(), tags);
	}
}
