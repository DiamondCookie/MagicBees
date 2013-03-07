package thaumicbees.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumicbees.main.CommonProxy;
import thaumicbees.tile.TileEntityMagicApiary;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;

public class BlockMagicApiary extends BlockContainer
{
	public BlockMagicApiary(int par1)
	{
		super(par1, Material.wood);
		this.setTextureFile(CommonProxy.TCBEES_ITEMS_IMAGE);
		this.setBlockName("Magic Apiary");
		this.blockIndexInTexture = 48;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess worldAccess, int x, int y, int z, int side)
	{
		int texture = 48;
		if (side == 0 || side == 1)
		{
			texture = 50;
		}
		return texture;
	}
	
    public int getBlockTextureFromSideAndMetadata(int side, int meta)
    {
		int texture = 48;
		if (side == 0 || side == 1)
		{
			texture = 50;
		}
		return texture;
    }

	@Override
	public TileEntity createNewTileEntity(World var1)
	{
		return new TileEntityMagicApiary();
	}
}
