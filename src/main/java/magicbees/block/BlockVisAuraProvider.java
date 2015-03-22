package magicbees.block;

import magicbees.main.utils.TabMagicBees;
import magicbees.tileentity.TileEntityVisAuraProvider;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockVisAuraProvider extends BlockContainer {

	public BlockVisAuraProvider() {
		super(Material.glass);
		this.setCreativeTab(TabMagicBees.tabMagicBees);
		this.setBlockName("visAuraProvider");
		this.setHardness(0.5f);
		this.setResistance(1.5f);
		this.setBlockBounds(0.25f, 0f, 0.25f, 0.75f, 0.5f, 0.75f);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityVisAuraProvider();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = Blocks.glass.getIcon(0, 0);
	}

}
