package magicbees.bees;

import magicbees.main.Config;
import magicbees.main.utils.LocalizationManager;
import magicbees.main.utils.compat.ThaumcraftHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import forestry.api.genetics.IFlowerProvider;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;

public class FlowerProviderThaumcraftFlower implements IFlowerProvider
{
	private ItemStack[] flowers = { new ItemStack(Config.tcPlant, 1, 2), new ItemStack(Config.tcPlant, 1, 3) };

	@Override
	public boolean isAcceptedFlower(World world, IIndividual genome, int x, int y, int z)
	{
		boolean flag = false;
		if (world.getBlock(x, y, z) == Config.tcPlant)
		{
			int meta = world.getBlockMetadata(x, y, z);
			if (meta == 2 || meta == 3)
			{
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public boolean growFlower(World world, IIndividual genome, int x, int y, int z)
	{
		boolean flag = false;
		Block blockDown = world.getBlock(x, y - 1, z);
		if (world.getBlock(x, y, z).isAir(world, x, y, z))
		{
			if (blockDown == Blocks.dirt || blockDown == Blocks.grass)
			{
				world.setBlock(x, y, z, Config.tcPlant, ThaumcraftHelper.BlockPlant.SHIMMERLEAF.ordinal(), 2);
				flag = true;
			}
			else if (blockDown == Blocks.sand)
			{
				world.setBlock(x, y, z, Config.tcPlant, ThaumcraftHelper.BlockPlant.CINDERPEARL.ordinal(), 2);
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public String getDescription()
	{
		return LocalizationManager.getLocalizedString("flowerProvider.magic");
	}

	@Override
	public ItemStack[] affectProducts(World world, IIndividual genome, int x, int y, int z, ItemStack[] products)
	{
		return products;
	}

	@Override
	public ItemStack[] getItemStacks()
	{
		return this.flowers;
	}

	@Override
	public boolean isAcceptedPollinatable(World world, IPollinatable pollinatable)
	{
		return pollinatable.getPlantType().size() > 1;
	}

}
