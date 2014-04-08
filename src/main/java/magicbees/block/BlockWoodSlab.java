package magicbees.block;

import java.util.List;
import java.util.Random;

import magicbees.block.types.PlankType;
import magicbees.main.CommonProxy;
import magicbees.main.Config;
import magicbees.main.MagicBees;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWoodSlab extends BlockSlab
{
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	
	public BlockWoodSlab(boolean doubleSlab)
	{
		super(doubleSlab, Material.wood);
		this.setHardness(2.5f);
		this.setResistance(6.0f);
		this.setBlockName("tb.slab.wood");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setLightOpacity(doubleSlab ? 255 : 0);
		if (MagicBees.getConfig().AreMagicPlanksFlammable)
		{
			Blocks.fire.setFireInfo(this, 5, 20);
		}
	}

	@Override
    public Item getItemDropped(int id, Random rand, int par3)
    {
        return Item.getItemFromBlock(Config.slabWoodHalf);
    }

    protected ItemStack createStackedBlock(int meta)
    {
        return new ItemStack(Config.planksWood, 1, meta & 7);
    }

	@Override
	public String func_150002_b(int meta)
	{
		return this.getUnlocalizedName() + "." + PlankType.getType(meta).name;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List itemsList)
	{
		for (PlankType type : PlankType.values())
		{
			itemsList.add(new ItemStack(this, 1, type.ordinal()));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return this.icons[Math.max(0, Math.min(meta & 7, icons.length-1))];
	}
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
		this.icons = new IIcon[PlankType.values().length];
    	for (PlankType t : PlankType.values())
    	{
    		this.icons[t.ordinal()] = par1IconRegister.registerIcon(CommonProxy.DOMAIN + ":" + t.name);
    	}
    }

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);

	    if (block.isAir(world, x, y, z))
	    {
	        return null;
	    }
	
	    Item item = Item.getItemFromBlock(block);
	    if (item == null)
	    {
	        return null;
	    }
	
	    return new ItemStack(item, 1, getDamageValue(world, x, y, z) & 7);
	}
}
