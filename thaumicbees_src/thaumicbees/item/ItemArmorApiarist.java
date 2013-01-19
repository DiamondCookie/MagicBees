package thaumicbees.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import forestry.api.apiculture.IArmorApiarist;

public class ItemArmorApiarist extends Item implements IArmorApiarist
{

	public ItemArmorApiarist(int par1)
	{
		super(par1);
	}
	
	public static int getNumberPiecesWorn(EntityPlayer player)
	{
		int count = 0;

		if (wearsHelmet(player)) {
			count++;
		}
		if (wearsChest(player)) {
			count++;
		}
		if (wearsLegs(player)) {
			count++;
		}
		if (wearsBoots(player)) {
			count++;
		}

		return count;
	}
	
	public static boolean wearsHelmet(EntityPlayer player)
	{
		ItemStack armorItem = player.inventory.armorInventory[3];
		return armorItem != null && armorItem.getItem() instanceof IArmorApiarist;
	}

	public static boolean wearsChest(EntityPlayer player)
	{
		ItemStack armorItem = player.inventory.armorInventory[2];
		return armorItem != null && armorItem.getItem() instanceof IArmorApiarist;
	}

	public static boolean wearsLegs(EntityPlayer player)
	{
		ItemStack armorItem = player.inventory.armorInventory[1];
		return armorItem != null && armorItem.getItem() instanceof IArmorApiarist;
	}

	public static boolean wearsBoots(EntityPlayer player)
	{
		ItemStack armorItem = player.inventory.armorInventory[0];
		return armorItem != null && armorItem.getItem() instanceof IArmorApiarist;
	}
}
