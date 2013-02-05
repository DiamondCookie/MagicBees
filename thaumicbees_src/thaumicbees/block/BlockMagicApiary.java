package thaumicbees.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumicbees.main.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
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
		this.blockIndexInTexture = 48;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5)
	{
		return super.getBlockTexture(par1iBlockAccess, par2, par3, par4, par5);
	}
}
