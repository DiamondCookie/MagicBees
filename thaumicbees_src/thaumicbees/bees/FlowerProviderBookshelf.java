package thaumicbees.bees;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IFlowerProvider;

public class FlowerProviderBookshelf implements IFlowerProvider
{

	public boolean isAcceptedFlower(World world, IBeeGenome genome, int x, int i, int j)
	{
		return false;
	}

	public boolean growFlower(World world, IBeeGenome genome, int x, int i, int j)
	{
		return false;
	}

	public String getDescription()
	{
		return null;
	}

	public ItemStack[] affectProducts(World world, IBeeGenome genome, int x, int i, int j, ItemStack aum[])
	{
		return null;
	}

	public ItemStack[] getItemStacks()
	{
		return null;
	}
}
