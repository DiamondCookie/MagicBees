package magicbees.block;

import java.util.ArrayList;
import java.util.List;


import magicbees.block.types.HiveType;
import magicbees.block.types.PlankType;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import forestry.core.config.ForestryBlock;

public class BlockHive extends Block
{	
	public BlockHive(int id)
	{
		super(id, new MaterialHive());
		this.setLightValue(0.8f);
		setHardness(1f);
		setCreativeTab(Tabs.tabApiculture);
		setUnlocalizedName("hive");
	}

	@Override
	public boolean canDragonDestroy(World world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public int damageDropped(int meta) 
	{
		return meta;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (meta < 0 || meta > HiveType.values().length)
		{
			meta = 0;
		}
		return HiveType.getHiveFromMeta(meta).getLightValue();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int id, CreativeTabs tab, List itemsList)
	{
		for (HiveType type : HiveType.values())
		{
			if (type.show)
			{
				itemsList.add(new ItemStack(this, 1, type.ordinal()));
			}
		}
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
	{		
		return HiveType.getHiveFromMeta(metadata).getDrops(world, x, y, z, fortune);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		HiveType.registerIcons(register);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta)
	{
		if (meta < 0 || meta > HiveType.values().length)
		{
			meta = 0;
		}
		return HiveType.getHiveFromMeta(meta).getIconForSide(side);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		int id = world.getBlockId(x, y, z);

	    if (id == 0)
	    {
	        return null;
	    }
	
	    Item item = Item.itemsList[id];
	    if (item == null)
	    {
	        return null;
	    }
	
	    return new ItemStack(id, 1, getDamageValue(world, x, y, z) & 7);
	}
}
