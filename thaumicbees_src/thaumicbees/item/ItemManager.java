package thaumicbees.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import forestry.api.recipes.*;

public class ItemManager
{
	public static ItemComb magicalComb;
	public static ItemMagicalWax magicalWax;
	
	public ItemManager() { }
	
	public static void setupItems(Configuration configFile)
	{
		int itemIDBase = 26090;
		
		Property p = configFile.getItem("combs", itemIDBase++);
		ItemManager.magicalComb= new ItemComb(p.getInt());
		ItemManager.magicalWax = new ItemMagicalWax(configFile.getItem("wax", itemIDBase++).getInt(), 0xffff00, false);
	}
	
	public static void setupCrafting()
	{
		// 20 is the 'average' time to centrifuge a comb.
		RecipeManagers.centrifugeManager.addRecipe(25, new ItemStack(ItemManager.magicalComb), new ItemStack(ItemManager.magicalWax));
	}

}
