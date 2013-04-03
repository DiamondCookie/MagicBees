package thaumicbees.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;

public class BlockEffectJar extends BlockContainer
{
	public BlockEffectJar(int id)
	{
		super(id, Material.glass);
	}

	@Override
	public TileEntity createNewTileEntity(World w)
	{
		return new TileEntityEffectJar(w);
	}

}
