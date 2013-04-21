package thaumicbees.block;

import thaumicbees.gui.UIScreens;
import thaumicbees.main.ThaumicBees;
import thaumicbees.main.utils.TabThaumicBees;
import thaumicbees.tileentity.TileEntityEffectJar;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;

public class BlockEffectJar extends BlockContainer
{
	public BlockEffectJar(int id)
	{
		super(id, Material.glass);
		this.setCreativeTab(TabThaumicBees.tabThaumicBees);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		boolean activate = false;
		
		if (!player.isSneaking())
		{
			player.openGui(ThaumicBees.object, UIScreens.EFFECT_JAR.ordinal(), world, x, y, z);
			activate = true;
		}
		
		return activate;
	}

	@Override
	public TileEntity createNewTileEntity(World w)
	{
		return new TileEntityEffectJar();
	}

}
