package thaumicbees.bees;

import thaumcraft.api.AuraNode;
import thaumcraft.api.ThaumcraftApi;
import thaumicbees.main.Config;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IFlowerProvider;
import forestry.api.genetics.IPollinatable;

public class FlowerProviderAuraNode implements IFlowerProvider
{
	protected int nodeRange = 70;
	
	@Override
	public boolean isAcceptedFlower(World world, IBeeGenome genome, int x, int y, int z)
	{
		return this.isNodeWithinRange(world, x, y, z);
	}

	@Override
	public boolean isAcceptedPollinatable(World world, IPollinatable pollinatable)
	{
		return true;
	}

	@Override
	public boolean growFlower(World world, IBeeGenome genome, int x, int y, int z)
	{
		boolean flag = false;
		int blockDown = world.getBlockId(x, y - 1, z);
		if (world.isAirBlock(x, y, z) && (blockDown == Block.dirt.blockID || blockDown == Block.grass.blockID))
		{
			int rand = world.rand.nextInt(10);
			world.setBlockAndMetadataWithNotify(x, y, z, Config.tcPlant.blockID, (rand >= 5) ? 2 : 3);
			System.out.println(x + ", " + y + ", " + ", " + z + " set to flower.");
			flag = true;
		}
		return flag;
	}

	@Override
	public String getDescription()
	{
		return "Node";
	}

	@Override
	public ItemStack[] affectProducts(World world, IBeeGenome genome, int x, int y, int z, ItemStack[] products)
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
