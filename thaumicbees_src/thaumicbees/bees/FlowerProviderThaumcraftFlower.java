package thaumicbees.bees;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumicbees.main.Config;
import thaumicbees.main.utils.LocalizationManager;
import thaumicbees.main.utils.compat.ThaumcraftHelper;
import forestry.api.genetics.IFlowerProvider;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;

public class FlowerProviderThaumcraftFlower implements IFlowerProvider
{
	private ItemStack[] flowers = {new ItemStack(Config.tcPlant, 1, 2), new ItemStack(Config.tcPlant, 1, 3) };
			
	@Override
	public boolean isAcceptedFlower(World world, IIndividual genome, int x, int y, int z)
	{
		boolean flag = false;
		if (world.getBlockId(x, y, z) == Config.tcPlant.blockID)
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
		int blockDown = world.getBlockId(x, y - 1, z);
		if (world.getBlockId(x, y, z) == 0)
		{
			if (blockDown == Block.dirt.blockID || blockDown == Block.grass.blockID)
			{
				world.setBlockMetadataWithNotify(x, y, z, Config.tcPlant.blockID, ThaumcraftHelper.BlockPlant.SHIMMERLEAF.ordinal());
				flag = true;
			}
			else if (blockDown == Block.sand.blockID)
			{
				world.setBlockMetadataWithNotify(x, y, z, Config.tcPlant.blockID, ThaumcraftHelper.BlockPlant.CINDERPEARL.ordinal());
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public String getDescription()
	{
		return LocalizationManager.getLocalizedString("tb.bees.flower.magic");
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
