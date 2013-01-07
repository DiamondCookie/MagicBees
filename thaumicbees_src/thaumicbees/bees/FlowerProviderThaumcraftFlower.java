package thaumicbees.bees;

import thaumicbees.block.BlockManager;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IFlowerProvider;
import forestry.api.genetics.IPollinatable;

public class FlowerProviderThaumcraftFlower implements IFlowerProvider
{
	private ItemStack[] flowers = {new ItemStack(BlockManager.tcPlant, 1, 2), new ItemStack(BlockManager.tcPlant, 1, 3) };
			
	@Override
	public boolean isAcceptedFlower(World world, IBeeGenome genome, int x, int y, int z)
	{
		boolean flag = false;
		if (world.getBlockId(x, y, z) == BlockManager.tcPlant.blockID)
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
	public boolean growFlower(World world, IBeeGenome genome, int x, int y, int z)
	{
		boolean flag = false;
		int blockDown = world.getBlockId(x, y - 1, z);
		if (world.isAirBlock(x, y, z) && (blockDown == Block.dirt.blockID || blockDown == Block.grass.blockID))
		{
			int rand = world.rand.nextInt(10);
			world.setBlockAndMetadataWithNotify(x, y, z, BlockManager.tcPlant.blockID, (rand >= 5) ? 2 : 3);
			System.out.println(x + ", " + y + ", " + ", " + z + " set to flower.");
			flag = true;
		}
		return flag;
	}

	@Override
	public String getDescription()
	{
		return "Magic Flowers";
	}

	@Override
	public ItemStack[] affectProducts(World world, IBeeGenome genome, int x, int y, int z, ItemStack[] products)
	{
		return products;
	}

	@Override
	public ItemStack[] getItemStacks()
	{
		return this.flowers;
	}

	//@Override
	public boolean isAcceptedPollinatable(World world, IPollinatable pollinatable)
	{
		return false;
	}

}
