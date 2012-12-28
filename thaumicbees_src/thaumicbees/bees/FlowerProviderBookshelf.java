package thaumicbees.bees;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IFlowerProvider;
import forestry.api.genetics.IPollinatable;

public class FlowerProviderBookshelf implements IFlowerProvider
{

	public boolean isAcceptedFlower(World world, IBeeGenome genome, int x, int i, int j)
	{
		boolean flag = false;
		if (world.getBlockId(x, i, j) == Block.bookShelf.blockID)
		{
			flag = true;
		}
		return flag;
	}

	public boolean growFlower(World world, IBeeGenome genome, int x, int i, int j)
	{
		// Always return true so the system thinks we grew a flower.
		//  Bees can't grow bookshelves! Are you crazy?!
		return true;
	}

	public ItemStack[] affectProducts(World world, IBeeGenome genome, int x, int i, int j, ItemStack products[])
	{
		return products;
	}

	public ItemStack[] getItemStacks()
	{
		return new ItemStack[] { new ItemStack(Block.bookShelf) };
	}

	@Override
	public boolean isAcceptedPollinatable(World world, IPollinatable pollinatable)
	{
		// No idea what this is supposed to do.
		return false;
	}

	public String getDescription()
	{
		return "Books";
	}
}
