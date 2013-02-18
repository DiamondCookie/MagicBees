package thaumicbees.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumicbees.item.types.PlankType;
import thaumicbees.main.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWood;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class BlockPlanks extends Block
{
	public BlockPlanks(int id)
	{
		super(id, Material.wood);
		this.setHardness(2.5f);
		this.setResistance(6.0f);
		this.setBlockName("tb.planks");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setTextureFile(CommonProxy.TCBEES_ITEMS_IMAGE);
		Block.setBurnProperties(id, 5, 20);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		return PlankType.getType(meta).textureIdx;
	}

	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int id, CreativeTabs tab, List itemsList)
	{
		for (PlankType type : PlankType.values())
		{
			itemsList.add(new ItemStack(this, 1, type.ordinal()));
		}
	}
}
