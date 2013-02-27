package net.mysteriousages.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Provides basic functionality for an owned inventory.
 * @author MysteriousAges
 *
 */
public class GenericOwnedInventory extends GenericInventory
{
	private String ownerName;
	private boolean isPublic;
	
	public GenericOwnedInventory(EntityPlayer player, String inventoryName, int slotCount)
	{
		super(inventoryName, slotCount);
		this.setInfo(player);		
	}

	public GenericOwnedInventory(EntityPlayer player, String inventoryName, int slotCount, int maxStackSize)
	{
		super(inventoryName, slotCount, maxStackSize);
		this.setInfo(player);
	}

	private void setInfo(EntityPlayer player)
	{
		this.ownerName = player.username;
	}
	
	public void setPubliclyAccessible(boolean flag)
	{
		this.isPublic = flag;
	}
	
	public String getOwnerName()
	{
		return this.ownerName;
	}
	
	public boolean isPubliclyAccessible()
	{
		return this.isPublic;
	}	

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.isPublic || this.ownerName.equalsIgnoreCase(player.username);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setString("Owner", this.ownerName);
		tag.setBoolean("PublicFlag", this.isPublic);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		this.ownerName = tag.getString("Owner");
		this.isPublic = tag.getBoolean("PublicFlag");
	}
	
	
}
