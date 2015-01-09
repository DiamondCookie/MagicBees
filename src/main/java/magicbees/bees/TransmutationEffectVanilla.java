package magicbees.bees;

import magicbees.api.bees.ITransmutationEffectLogic;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.oredict.OreDictionary;

public class TransmutationEffectVanilla implements ITransmutationEffectLogic
{
	@Override
	public boolean tryTransmutation(World world, BiomeGenBase biome, ItemStack sourceBlock, int x, int y, int z)
	{
		return trySpawnSandstone(world, biome, sourceBlock, x, y, z);
	}

	private boolean trySpawnSandstone(World world, BiomeGenBase biome, ItemStack sourceBlock, int x, int y, int z)
	{
		boolean flag = false;
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SANDY) &&
				OreDictionary.itemMatches(new ItemStack(Blocks.sand), sourceBlock, false))
		{
			world.setBlock(x, y, z, Blocks.sandstone);
			flag = true;
		}
		return flag;
	}
}
