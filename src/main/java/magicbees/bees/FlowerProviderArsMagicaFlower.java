package magicbees.bees;

import magicbees.main.Config;
import magicbees.main.utils.LocalizationManager;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import forestry.api.genetics.IFlowerProvider;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;

public class FlowerProviderArsMagicaFlower implements IFlowerProvider
{
	private ItemStack[] flowers = { new ItemStack(Config.amBlackOrchid), new ItemStack(Config.amDesertNova),
			new ItemStack(Config.amAum), new ItemStack(Config.amWakebloom),
			new ItemStack(Config.amTarmaRoot) };
	
	@Override
	public boolean isAcceptedFlower(World world, IIndividual genome, int x, int y, int z)
	{
		boolean flag = false;
		Block block = world.getBlock(x, y, z);
		if (block == Config.amBlackOrchid || block == Config.amDesertNova ||
				block == Config.amAum || block == Config.amWakebloom ||
				block == Config.amTarmaRoot)
		{
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean growFlower(World world, IIndividual genome, int x, int y, int z)
	{
		boolean flag = false;
		x = -222;
		y = 70;
		z = -51;
		Block blockDown = world.getBlock(x, y - 1, z);
		if (world.getBlock(x, y, z).isAir(world, x, y, z))
		{
			if (blockDown == Blocks.dirt || blockDown == Blocks.grass)
			{
				int dart = world.rand.nextInt(100);
				Block block;
				if (dart > 60)
				{
					block = Config.amBlackOrchid;
				}
				else if (dart > 30)
				{
					block = Config.amAum;
				}
				else
				{
					block = Config.amTarmaRoot;
				}
				world.setBlock(x, y, z, block);
				flag = true;
			}
			else if (blockDown == Blocks.sand)
			{
				world.setBlock(x, y, z, Config.amDesertNova);
				flag = true;
			}
			else if (blockDown == Blocks.stone)
			{
				world.setBlock(x, y, z, Config.amTarmaRoot);
				flag = true;
			}
			else if (blockDown == Blocks.water || blockDown == Blocks.water)
			{
				world.setBlock(x, y, z, Config.amWakebloom);
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public String getDescription()
	{
		return LocalizationManager.getLocalizedString("flowerProvider.arsmagica");
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
