package thaumicbees.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumicbees.client.gui.UIScreens;
import thaumicbees.main.CommonProxy;
import thaumicbees.main.ThaumicBees;
import thaumicbees.main.utils.TabThaumicBees;
import thaumicbees.tileentity.TileEntityEffectJar;

public class BlockEffectJar extends BlockContainer
{
	public BlockEffectJar(int id)
	{
		super(id, Material.glass);
		// Copy glass's block icon for the breaking & running textures.
		this.blockIcon = Block.glass.getIcon(0, 0);
		this.setCreativeTab(TabThaumicBees.tabThaumicBees);
		this.setUnlocalizedName("tb.effectJar");
		this.setBlockBounds(0.25f, 0f, 0.25f, 0.75f, 0.81f, 0.74f);
		this.setHardness(0.1f);
		this.setResistance(1.5f);
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

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return CommonProxy.RenderIdEffectJar;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta)
	{
		super.breakBlock(world, x, y, z, id, meta);
		this.dropInventory(world, x, y, z);
	}

	private void dropInventory(World world, int x, int y, int z)
	{
		TileEntity e = world.getBlockTileEntity(x, y, z);
		
		if (e != null && e instanceof TileEntityEffectJar)
		{
			TileEntityEffectJar jar = (TileEntityEffectJar)e;
			
			// 0 is the only droppable stack.
			ItemStack stack = jar.getStackInSlot(0);
			
			if (stack != null && stack.stackSize > 0)
			{
				float pX = world.rand.nextFloat() * 0.8f + 0.1f;
				float pY = world.rand.nextFloat() * 0.8f + 0.1f;
				float pZ = world.rand.nextFloat() * 0.8f + 0.1f;
				
				EntityItem entityItem = new EntityItem(world, x + pX, y + pY, z + pZ, stack.copy());
				
				entityItem.motionX = world.rand.nextGaussian() * 0.05f;
				entityItem.motionY = world.rand.nextGaussian() * 0.05f + 0.2f;
				entityItem.motionZ = world.rand.nextGaussian() * 0.05f;
				
				world.spawnEntityInWorld(entityItem);
				stack.stackSize = 0;
			}
		}
	}
}
