package magicbees.bees;

import magicbees.main.utils.LocalizationManager;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aura.AuraNode;
import forestry.api.genetics.IFlowerProvider;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;

public class FlowerProviderAuraNode implements IFlowerProvider
{
	protected int nodeRange = 70;
	
	@Override
	public boolean isAcceptedFlower(World world, IIndividual genome, int x, int y, int z)
	{
		return this.isNodeWithinRange(world, x, y, z);
	}

	@Override
	public boolean isAcceptedPollinatable(World world, IPollinatable pollinatable)
	{
		return pollinatable.getPlantType().size() > 0;
	}

	@Override
	public boolean growFlower(World world, IIndividual genome, int x, int y, int z)
	{
		return true;
	}

	@Override
	public String getDescription()
	{
		return LocalizationManager.getLocalizedString("tb.bees.flower.node");
	}

	@Override
	public ItemStack[] affectProducts(World world, IIndividual genome, int x, int y, int z, ItemStack[] products)
	{
		return products;
	}

	@Override
	public ItemStack[] getItemStacks()
	{
		return null;
	}
	
	protected boolean isNodeWithinRange(World w, int x, int y, int z)
	{
		boolean nodeNearby = false;		
		if (ThaumcraftApi.getClosestAuraWithinRange(w, x, y, z, this.nodeRange) >= 0)
		{
			nodeNearby = true;
		}
		return nodeNearby;
	}
	
	protected AuraNode getNodeWithinRange(World w, int x, int y, int z)
	{
		AuraNode node = null;
		
		int nodeId = ThaumcraftApi.getClosestAuraWithinRange(w, x, y, z, this.nodeRange);
		if (nodeId >= 0)
		{
			node = ThaumcraftApi.getNodeCopy(nodeId);
		}
		
		return node;
	}

	protected ItemStack[] addItemToProducts(ItemStack[] products, ItemStack itemStack)
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
