package thaumicbees.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumicbees.main.CommonProxy;
import thaumicbees.block.TileEntityMagicApiary;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;

public class BlockMagicApiary extends Block
{
	public BlockMagicApiary(int par1)
	{
		super(par1, Material.wood);
		this.setTextureFile(CommonProxy.TCBEES_ITEMS_IMAGE);
		this.setBlockName("Magic Apiary");
		this.blockIndexInTexture = 50;
		this.setHardness(2f);
		this.setResistance(5f);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess worldAccess, int x, int y, int z, int side)
	{
		int texture = 50;
		if (side == 0)
		{
			texture = 51;
		}
		else if (side == 1)
		{
			TileEntityMagicApiary tileInfo = (TileEntityMagicApiary)worldAccess.getBlockTileEntity(x, y, z);
			if (tileInfo != null && tileInfo.isMaster())
			{
				texture = 52;
			}
			else
			{
				texture = 51;
			}
		}
		return texture;
	}
	
    public int getBlockTextureFromSideAndMetadata(int side, int meta)
    {
		int texture = 50;
		if (side == 0 || side == 1)
		{
			texture = 51;
		}
		return texture;
    }

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity)
	{
		super.onBlockPlacedBy(world, x, y, z, entity);
		
		// Validate block arrangement
	}
}
