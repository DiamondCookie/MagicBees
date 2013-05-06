package net.mysteriousages.utils.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * 
 * @author MysteriousAges
 *
 */
public class SlotCustomItems extends Slot
{
	private List<ItemStack> items;
	
	public SlotCustomItems(IInventory inventory, int slotIndex, int xPos, int yPos)
	{
		this(inventory, slotIndex, xPos, yPos, new ItemStack[0]);
	}
	
	public SlotCustomItems(IInventory inventory, int slotIndex, int xPos, int yPos, ItemStack... items)
	{
		super(inventory, slotIndex, xPos, yPos);
		this.items = new ArrayList<ItemStack>();
		
		for (ItemStack i : items)
		{
			this.items.add(i);
		}
	}
	
	/**
	 * 
	 * @return List of valid ItemStacks
	 */
	public List<ItemStack> getValidItems()
	{
		return this.items;
	}
	
	public void addValidItem(ItemStack newItem)
	{
		boolean contains = false;
		for (int i = 0; i < this.items.size(); ++i)
		{
			ItemStack s = this.items.get(i);
			if (s.itemID == newItem.itemID)
			{
				if (s.getItemDamage() == OreDictionary.WILDCARD_VALUE)
				{
					contains = true;
					break;
				}
				else if (s.getItemDamage() == newItem.getItemDamage())
				{
					contains = true;
					break;
				}
			}
		}
		if (!contains)
		{
			this.items.add(newItem);
		}
	}
	
	/**
	 * 
	 * @param removeItem ItemStack to remove.
	 */
	public void removeValidItem(ItemStack removeItem)
	{
		for (int i = 0; i < this.items.size(); ++i)
		{
			ItemStack s = this.items.get(i);
			if (s.itemID == removeItem.itemID)
			{
				if (s.getItemDamage() == OreDictionary.WILDCARD_VALUE)
				{
					this.items.remove(i);
					break;
				}
				else if (s.getItemDamage() == removeItem.getItemDamage())
				{
					this.items.remove(i);
					break;
				}
			}
		}
	}

	@Override
	public boolean isItemValid(ItemStack itemStack)
	{
		boolean flag = false;
		for (int i = 0; i < this.items.size(); ++i)
		{
			if (this.items.get(i).itemID == itemStack.itemID)
			{
				if (this.items.get(i).getItemDamage() == itemStack.getItemDamage())
				{
					flag = true;
					break;
				}
				else
				{
					flag = this.items.get(i).getItemDamage() ==OreDictionary.WILDCARD_VALUE;
					break;
				}
			}
		}
		return flag;
	}

}
