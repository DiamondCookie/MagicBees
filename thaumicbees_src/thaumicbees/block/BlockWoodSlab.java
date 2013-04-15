package thaumicbees.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import thaumicbees.item.types.PlankType;
import thaumicbees.main.Config;
import thaumicbees.main.ThaumicBees;
import thaumicbees.main.utils.VersionInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWoodSlab extends BlockHalfSlab
{
	@SideOnly(Side.CLIENT)
	private Icon[] icons = new Icon[PlankType.values().length];
	
	public BlockWoodSlab(int id, boolean doubleSlab)
	{
		super(id, doubleSlab, Material.wood);
		this.setHardness(2.5f);
		this.setResistance(6.0f);
		this.setUnlocalizedName("tb.slab.wood");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setLightOpacity(doubleSlab ? 255 : 0);
		if (ThaumicBees.getConfig().AreMagicPlanksFlammable)
		{
			Block.setBurnProperties(id, 5, 20);
		}
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
		return this.getUnlocalizedName() + "." + PlankType.getType(meta).name;
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

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int side, int meta)
	{
		return this.icons[Math.max(0, Math.min(meta & 7, icons.length-1))];
	}
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
    	for (PlankType t : PlankType.values())
    	{
    		this.icons[t.ordinal()] = par1IconRegister.registerIcon(VersionInfo.ModName + ":" + t.name);
    	}
    }
}
