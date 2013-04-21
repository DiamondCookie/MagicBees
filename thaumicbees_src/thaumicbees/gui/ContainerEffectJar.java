package thaumicbees.gui;

import forestry.api.core.ItemInterface;
import thaumicbees.tileentity.TileEntityEffectJar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerEffectJar extends Container
{
	private TileEntityEffectJar jar;
	
	public ContainerEffectJar(TileEntityEffectJar tileEntityEffectJar, EntityPlayer player)
	{
		this.jar = tileEntityEffectJar;
		ItemStack drone = ItemInterface.getItem("beeDroneGE");
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

}
