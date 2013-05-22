package thaumicbees.bees;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumicbees.main.Config;
import thaumicbees.main.utils.LocalizationManager;
import forestry.api.genetics.IFlowerProvider;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;

public class FlowerProviderArsMagicaFlower implements IFlowerProvider
{
	private ItemStack[] flowers = {new ItemStack(Config.amBlackOrchid), new ItemStack(Config.amDesertNova) };
	
	@Override
	public boolean isAcceptedFlower(World world, IIndividual genome, int x, int y, int z)
	{
		boolean flag = false;
		int id = world.getBlockId(x, y, z);
		if (id == Config.amBlackOrchid.blockID || id == Config.amDesertNova.blockID)
		{
			flag = true;
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
				world.setBlock(x, y, z, Config.amBlackOrchid.blockID);
				flag = true;
			}
			else if (blockDown == Block.sand.blockID)
			{
				world.setBlock(x, y, z, Config.amDesertNova.blockID);
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public String getDescription()
	{
		return LocalizationManager.getLocalizedString("tb.bees.flower.arsmagica");
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
