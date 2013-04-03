package thaumicbees.block;

import thaumicbees.main.utils.EffectJarHousing;
import forestry.api.apiculture.IBeeHousing;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityEffectJar extends TileEntity
{
	private String ownerName;
	private EffectJarHousing housingLogic;
	
	public TileEntityEffectJar()
	{
		super();
	}
	
	public void setOwner(EntityPlayer player)
	{
		this.ownerName = player.username;
	}
	
	public String getOwner()
	{
		return this.ownerName;
	}
}
