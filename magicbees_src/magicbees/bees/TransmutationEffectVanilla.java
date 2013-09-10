package magicbees.bees;

import cpw.mods.fml.common.registry.GameRegistry;
import magicbees.api.bees.ITransmutationEffectLogic;
import net.minecraft.block.Block;
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
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.DESERT) &&
				OreDictionary.itemMatches(new ItemStack(Block.sand), sourceBlock, false))
		{
			world.setBlock(x, y, z, Block.sandStone.blockID);
			flag = true;
		}
		return flag;
	}
}
