package thaumicbees.main;

import java.io.File;

import thaumcraft.api.ThaumcraftApi;
import thaumicbees.item.ItemManager;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Property;
import net.minecraftforge.common.Configuration;

public class ThaumcraftCompat
{

	public static int itemResourceID;
	public static final int itemResourceQuicksilverMeta = 3;
	public static final int itemResourceAmberMeta = 6;
	public static final int itemResourceFragmentMeta = 9;
	
	public static int itemShardID;
	public static final int itemShardAirMeta = 0;
	public static final int itemShardFireMeta = 1;
	public static final int itemShardWaterMeta = 2;
	public static final int itemShardEarthMeta = 3;
	public static final int itemShardMagicMeta = 4;
	public static final int itemShardDullMeta = 5;
	
	public static int itemEssentiaBottle;
	
	public static void init()
	{
		Configuration tcConfig = new Configuration(new File("./config/Thaumcraft.cfg"));
		Property p;
		
		tcConfig.load();
		
		// Load items out of the Thaumcraft config to get their IDs.
		p = tcConfig.getItem("ItemEssence", 25005);
		itemEssentiaBottle = p.getInt() + 256;
		p = tcConfig.getItem("ItemResource", 25007);
		itemResourceID = p.getInt() + 256;
		p = tcConfig.getItem("ItemShard", 25008);
		itemShardID = p.getInt() + 256;
	}
	
	public static void setupResearch()
	{
		//ThaumcraftApi.registerResearchXML(CommonProxy.TCBEES_RESEARCH + "/research.xml");
	}

	public static void setupCrafting()
	{
		GameRegistry.addRecipe(new ItemStack(itemEssentiaBottle, 8, 0), new Object[]
			{
				" C ", "G G", " G ",
				Character.valueOf('G'), ItemManager.magicalWax,
				Character.valueOf('C'), Item.clay
			});
	}
}
