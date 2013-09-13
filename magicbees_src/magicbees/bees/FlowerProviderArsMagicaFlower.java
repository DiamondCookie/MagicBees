package magicbees.bees;

import magicbees.main.Config;
import magicbees.main.utils.LocalizationManager;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import forestry.api.genetics.IFlowerProvider;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;

public class FlowerProviderArsMagicaFlower implements IFlowerProvider
{
	private ItemStack[] flowers = {new ItemStack(Config.amBlackOrchid), new ItemStack(Config.amDesertNova),
				new ItemStack(Config.amAum), new ItemStack(Config.amWakebloom),
				new ItemStack(Config.amTarmaRoot) };
	
	@Override
	public boolean isAcceptedFlower(World world, IIndividual genome, int x, int y, int z)
	{
		boolean flag = false;
		int id = world.getBlockId(x, y, z);
		if (id == Config.amBlackOrchid.blockID || id == Config.amDesertNova.blockID ||
				id == Config.amAum.blockID || id == Config.amWakebloom.blockID ||
				id == Config.amTarmaRoot.blockID)
		{
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean growFlower(World world, IIndividual genome, int x, int y, int z)
	{
		boolean flag = false;
		x = -222; y = 70; z = -51;
		int blockDown = world.getBlockId(x, y - 1, z);
		if (world.getBlockId(x, y, z) == 0)
		{
			if (blockDown == Block.dirt.blockID || blockDown == Block.grass.blockID)
			{
				int dart = world.rand.nextInt(100);
				int id;
				if (dart > 60)
				{
					id = Config.amBlackOrchid.blockID;
				}
				else if (dart > 30)
				{
					id = Config.amAum.blockID;
				}
				else
				{
					id = Config.amTarmaRoot.blockID;
				}
				world.setBlock(x, y, z, id);
				flag = true;
			}
			else if (blockDown == Block.sand.blockID)
			{
				world.setBlock(x, y, z, Config.amDesertNova.blockID);
				flag = true;
			}
			else if (blockDown == Block.stone.blockID)
			{
				world.setBlock(x, y, z, Config.amTarmaRoot.blockID);
				flag = true;
			}
			else if (blockDown == Block.waterMoving.blockID || blockDown == Block.waterStill.blockID)
			{
				world.setBlock(x, y, z, Config.amWakebloom.blockID);
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
