package thaumicbees.block;

import java.util.List;
import java.util.Random;

import thaumicbees.item.types.PlankType;
import thaumicbees.main.CommonProxy;
import thaumicbees.main.Config;
import thaumicbees.main.ThaumicBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class BlockWoodSlab extends BlockHalfSlab
{
	
	public BlockWoodSlab(int id, boolean doubleSlab)
	{
		super(id, doubleSlab, Material.wood);
		this.setHardness(2.5f);
		this.setResistance(6.0f);
		this.setBlockName("tb.slab.wood");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setTextureFile(CommonProxy.TCBEES_ITEMS_IMAGE);
		this.setLightOpacity(doubleSlab ? 255 : 0);
		if (ThaumicBees.getConfig().AreMagicPlanksFlammable)
		{
			Block.setBurnProperties(id, 5, 20);
		}
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		int tx = 256;
		if (PlankType.getType(meta & 7) != null)
		{
			tx = PlankType.getType(meta & 7).textureIdx;
		}		
		return tx;
	}

    public int idDropped(int id, Random rand, int par3)
    {
        return Config.slabWoodHalf.blockID;
    }

    protected ItemStack createStackedBlock(int meta)
    {
        return new ItemStack(Config.slabWoodHalf.blockID, 2, meta & 7);
    }

	@Override
	public String getFullSlabName(int meta)
	{
		return this.getBlockName() + "." + PlankType.getType(meta).name;
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
