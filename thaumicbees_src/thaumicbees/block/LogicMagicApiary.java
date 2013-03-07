package thaumicbees.block;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.mysteriousages.utils.GenericInventory;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeekeepingLogic;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import thaumicbees.main.utils.compat.ForestryHelper;

public class LogicMagicApiary implements IBeeHousing
{
	private TileEntityMagicApiary parent;
	private String ownerName;
	private BiomeGenBase biome;
	private IBeekeepingLogic beeLogic;
	private GenericInventory tileInventory;
	private ArrayList<ItemStack> inventoryOverflow;
	
	private static final int SLOT_ID_QUEEN = 0;
	private static final int SLOT_ID_DRONE = 1;
	private static final int SLOT_ID_FRAME = 2;
	private static final int SLOT_LENGTH_FRAME = 2;
	private static final int SLOT_ID_PRODUCE = 4;
	private static final int SLOT_LENGTH_PRODUCE = 6;
	
	public LogicMagicApiary(TileEntityMagicApiary master)
	{
		this.parent = master;
		this.beeLogic = BeeManager.breedingManager.createBeekeepingLogic(this);
		this.tileInventory = new GenericInventory("MagicApiary", 11);
	}
	
	public TileEntityMagicApiary getParent()
	{
		return parent;
	}
	
	public void setOwner(EntityPlayer player)
	{
		this.ownerName = player.username;
	}
	
	public String getOwner()
	{
		return this.ownerName;
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		NBTTagCompound beeTag = new NBTTagCompound();
		this.beeLogic.writeToNBT(beeTag);
		tag.setTag("BeeLogic", beeTag);
		tag.setString("Owner", this.ownerName);
		NBTTagCompound inventory = new NBTTagCompound();
		this.tileInventory.writeToNBT(inventory);
		tag.setTag("Inventory", inventory);
		
		if (this.inventoryOverflow.size() > 0)
		{
			NBTTagList overflow = new NBTTagList();
			for (int i = 0; i < this.inventoryOverflow.size(); ++i)
			{
				NBTTagCompound itemTag = new NBTTagCompound();
				this.inventoryOverflow.get(i).writeToNBT(itemTag);
				overflow.appendTag(itemTag);
			}
		}
		
	}
	
	public void readFromNBT(NBTTagCompound tag)
	{
		
	}

	@Override
	public float getTerritoryModifier(IBeeGenome genome)
	{
		return 1f;
	}

	@Override
	public float getMutationModifier(IBeeGenome genome, IBeeGenome mate)
	{
		return 1f;
	}

	@Override
	public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate)
	{
		return 1f;
	}

	@Override
	public float getProductionModifier(IBeeGenome genome)
	{
		return 2f;
	}

	@Override
	public float getFloweringModifier(IBeeGenome genome)
	{
		return 1f;
	}

	@Override
	public boolean isSealed()
	{
		return false;
	}

	@Override
	public boolean isSelfLighted()
	{
		return false;
	}

	@Override
	public boolean isSunlightSimulated()
	{
		return false;
	}

	@Override
	public boolean isHellish()
	{
		return false;
	}

	@Override
	public void onQueenChange(ItemStack queen)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void wearOutEquipment(int amount)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onQueenDeath(IBee queen)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onPostQueenDeath(IBee queen)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getXCoord()
	{
		return parent.xCoord;
	}

	@Override
	public int getYCoord()
	{
		return parent.yCoord;
	}

	@Override
	public int getZCoord()
	{
		return parent.zCoord;
	}

	@Override
	public ItemStack getQueen()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getDrone()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setQueen(ItemStack itemstack)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setDrone(ItemStack itemstack)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getBiomeId()
	{
		return this.biome.biomeID;
	}

	@Override
	public EnumTemperature getTemperature()
	{
		return ForestryHelper.getEnumTemperatureFromValue(this.biome.temperature);
	}

	@Override
	public EnumHumidity getHumidity()
	{
		return ForestryHelper.getEnumHumidityFromValue(this.biome.rainfall);
	}

	@Override
	public World getWorld()
	{
		return this.getWorld();
	}

	@Override
	public String getOwnerName()
	{
		return parent.getOwner();
	}

	@Override
	public void setErrorState(int state)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getErrorOrdinal()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canBreed()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addProduct(ItemStack product, boolean all)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
