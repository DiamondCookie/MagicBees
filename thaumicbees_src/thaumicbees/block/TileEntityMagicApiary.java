package thaumicbees.block;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;

public class TileEntityMagicApiary extends TileEntity
{
	private String owner;
	private LogicMagicApiary logic;
	private int masterX;
	private int masterY;
	private int masterZ;
	
	public TileEntityMagicApiary(World w)
	{
		this.setWorldObj(w);
	}
	
	public void setValid(TileEntityMagicApiary masterEntity)
	{
		this.logic = masterEntity.logic;
		this.masterX = masterEntity.xCoord;
		this.masterY = masterEntity.yCoord;
		this.masterZ = masterEntity.zCoord;
	}
	
	public String getOwner()
	{
		return this.owner;
	}
	
	public boolean isValidStructure()
	{
		return false;
	}
	
	public boolean isMaster()
	{
		boolean flag = false;
		
		if (this.logic != null)
		{
			flag = this.logic.getParent() == this;
		}
		
		return flag;
	}
	
	public LogicMagicApiary getLogic()
	{
		return this.logic;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagRoot)
	{
		super.readFromNBT(tagRoot);
		this.owner = tagRoot.getString("Owner");
		if (tagRoot.hasKey("Logic"))
		{
			this.logic = new LogicMagicApiary(this);
			this.logic.readFromNBT(tagRoot.getCompoundTag("Logic"));
		}
		else
		{
			this.masterX = tagRoot.getInteger("MasterX");
			this.masterY = tagRoot.getInteger("MasterY");
			this.masterZ = tagRoot.getInteger("MasterZ");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagRoot)
	{
		super.writeToNBT(tagRoot);
		tagRoot.setString("Owner", this.owner);
		if (this.isMaster())
		{
			NBTTagCompound logicTag = new NBTTagCompound();
			this.logic.writeToNBT(logicTag);
			tagRoot.setTag("Logic", logicTag);
		}
		else
		{
			tagRoot.setInteger("MasterX", this.masterX);
			tagRoot.setInteger("MasterY", this.masterY);
			tagRoot.setInteger("MasterZ", this.masterZ);
		}
	}
}
