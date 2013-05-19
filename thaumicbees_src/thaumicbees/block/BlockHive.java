package thaumicbees.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import forestry.api.core.BlockInterface;

public class BlockHive extends Block
{
	public BlockHive(int id)
	{
		super(id, new MaterialHive());
	}

}
