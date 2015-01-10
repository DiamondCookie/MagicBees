package magicbees.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import magicbees.block.types.PlankType;
import magicbees.main.CommonProxy;
import magicbees.main.Config;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockPlanks extends Block
{
	@SideOnly(Side.CLIENT)
	private IIcon[] IIcons;
	
	public BlockPlanks()
	{
		super(Material.wood);
		this.setHardness(2.5f);
		this.setResistance(6.0f);
		this.setCreativeTab(CreativeTabs.tabBlock);
		if (Config.AreMagicPlanksFlammable)
		{
			Blocks.fire.setFireInfo(this, 5, 20);
		}
	}

	@Override
	public int damageDropped(int meta)
	{
		return meta;
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
		return this.IIcons[Math.max(0, Math.min(meta & 7, IIcons.length - 1))];
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IIconRegister)
	{
		this.IIcons = new IIcon[PlankType.values().length];
		for (PlankType t : PlankType.values())
		{
			this.IIcons[t.ordinal()] = par1IIconRegister.registerIcon(CommonProxy.DOMAIN + ":" + t.name);
		}
	}
}
