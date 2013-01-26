package thaumicbees.bees;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.AuraNode;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumicbees.block.BlockManager;
import thaumicbees.item.ItemManager;
import forestry.api.apiculture.IBeeGenome;

public class FlowerProviderAuraNodePurify extends FlowerProviderAuraNode
{
	@Override
	public boolean growFlower(World world, IBeeGenome genome, int x, int y, int z)
	{
		return true;
	}

	@Override
	public String getDescription()
	{
		return "Node Purify";
	}

	@Override
	public ItemStack[] affectProducts(World world, IBeeGenome genome, int x, int y, int z, ItemStack[] products)
	{
		AuraNode node = this.getNodeWithinRange(world, x, y, z);
		if (node != null)
		{
			int aspectCount = node.flux.tags.size();
			if (aspectCount > 0)
			{
				EnumTag selectedTag = node.flux.getAspectsSorted()[world.rand.nextInt(node.flux.tags.size())];
				node.flux.remove(selectedTag, 1);
				ThaumcraftApi.queueNodeChanges(node.key, 0, 0, false, new ObjectTags().add(selectedTag, -1), 0, 0, 0);
				products = addItemToProducts(products, new ItemStack(ItemManager.solidFlux, 1, selectedTag.id));
			}
		}
		return products;
	}

	private ItemStack[] addItemToProducts(ItemStack[] products, ItemStack itemStack)
	{
		for (ItemStack stack : products)
		{
			if (stack.itemID == itemStack.itemID && stack.getItemDamage() == itemStack.getItemDamage())
			{
				if (stack.stackSize < stack.getItem().getItemStackLimit())
				{
					stack.stackSize += itemStack.stackSize;
					itemStack.stackSize = Math.max(stack.stackSize - stack.getItem().getItemStackLimit(), 0);
				}
			}
		}
			
		if (itemStack.stackSize > 0)
		{
			ItemStack[] newProducts = new ItemStack[products.length + 1];
			for (int i = 0; i < products.length; ++i)
			{
				newProducts[i] = products[i];
			}
			newProducts[products.length] = itemStack;
			products = newProducts;
		}
		
		return products;
	}
}
