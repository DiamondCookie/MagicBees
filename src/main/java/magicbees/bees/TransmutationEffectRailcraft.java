package magicbees.bees;

import java.util.Locale;

import cpw.mods.fml.common.registry.GameRegistry;
import magicbees.api.bees.ITransmutationEffectLogic;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.oredict.OreDictionary;

public class TransmutationEffectRailcraft implements ITransmutationEffectLogic
{
	@Override
	public boolean tryTransmutation(World world, BiomeGenBase biome, ItemStack sourceBlock, int x, int y, int z)
	{
		boolean flag = false;
		if (OreDictionary.itemMatches(new ItemStack(Blocks.stone), sourceBlock, false) ||
				OreDictionary.itemMatches(new ItemStack(Blocks.cobblestone), sourceBlock, false))
		{
			flag = trySpawnQuarriedStone(world, biome, sourceBlock, x, y, z) ||
					trySpawnAbyssalStone(world, biome, sourceBlock, x, y, z);
		}
		return flag;
	}
	
	private boolean trySpawnQuarriedStone(World world, BiomeGenBase biome, ItemStack sourceBlock, int x, int y, int z)
	{
		boolean flag = false;
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.FOREST) &&
				!BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SNOWY))
		{
			ItemStack item = GameRegistry.findItemStack("Railcraft", "cube.stone.quarried", 1);
			if (item != null)
			{
				world.setBlock(x, y, z, Block.getBlockFromItem(item.getItem()), item.getItemDamage(), 2);
				flag = true;
			}
		}
		return flag;
	}

	private boolean trySpawnAbyssalStone(World world, BiomeGenBase biome, ItemStack sourceBlock, int x, int y, int z)
	{
		boolean flag = false;
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.WATER) &&
				!biome.biomeName.toLowerCase(Locale.ENGLISH).contains("river"))
		{
			ItemStack item = GameRegistry.findItemStack("Railcraft", "cube.stone.abyssal", 1);
			if (item != null)
			{
				world.setBlock(x, y, z, Block.getBlockFromItem(item.getItem()), item.getItemDamage(), 2);
				flag = true;
			}
		}
		return flag;
	}
}
