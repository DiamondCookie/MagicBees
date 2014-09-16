package magicbees.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import magicbees.client.gui.UIScreens;
import magicbees.main.MagicBees;
import magicbees.main.utils.TabMagicBees;
import magicbees.tileentity.TileEntityThaumicApiary;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockThaumicApiary extends BlockContainer
{
    public BlockThaumicApiary()
    {
        super(Material.wood);
        this.setCreativeTab(TabMagicBees.tabMagicBees);
        this.setBlockName("thaumicApiary");
        this.setHardness(1f);
        this.setResistance(1.5f);
        this.setHarvestLevel("axe", 0);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9 )
    {
        boolean activate = false;

        if (!player.isSneaking())
        {
            player.openGui(MagicBees.object, UIScreens.THAUMIC_APIARY.ordinal(), world, x, y, z);
            activate = true;
        }

        return activate;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {

    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityThaumicApiary();
    }

}
