package magicbees.tileentity;

import magicbees.api.bees.IMagicApiaryAuraProvider;
import magicbees.main.CommonProxy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.visnet.VisNetHandler;

public class TileEntityVisAuraProvider extends TileEntity implements IMagicApiaryAuraProvider {

	public static final String tileEntityName = CommonProxy.DOMAIN + ".visAuraProvider";
	
	private static final int MAX_CHARGES = 10;
	int currentMutationCharges;
	int currentDeathCharges;
	int currentProductionCharges;
	
	private static final int VIS_PER_CHARGE = 10;
	private static Aspect ASPECT_MUTATION = Aspect.WATER;
	private static Aspect ASPECT_DEATH = Aspect.ENTROPY;
	private static Aspect ASPECT_PRODUCTION = Aspect.AIR;
	int currentMutationVis;
	int currentDeathVis;
	int currentProductionVis;
	
	public TileEntityVisAuraProvider() {
		super();
	}
	
	@Override
	public void updateEntity() {
		
		long modTickRate = worldObj.getTotalWorldTime();
		if (currentProductionCharges < MAX_CHARGES && modTickRate % 3 == 0) {
			currentProductionVis += getVisFromNet(ASPECT_PRODUCTION);
			if (currentProductionVis >= VIS_PER_CHARGE) {
				currentProductionVis -= VIS_PER_CHARGE;
				++currentProductionCharges;
			}
		}
		if (currentDeathCharges < MAX_CHARGES && modTickRate % 5 == 0) {
			currentDeathVis += getVisFromNet(ASPECT_DEATH);
			if (currentDeathVis >= VIS_PER_CHARGE) {
				currentDeathVis -= VIS_PER_CHARGE;
				++currentDeathCharges;
			}
		}
		if (currentMutationCharges < MAX_CHARGES && modTickRate % 11 == 0) {
			currentMutationCharges += getVisFromNet(ASPECT_MUTATION);
			if (currentMutationCharges >= VIS_PER_CHARGE) {
				currentMutationCharges -= VIS_PER_CHARGE;
				++currentMutationCharges;
			}
		}
	}
	
	private int getVisFromNet(Aspect aspect) {
		return VisNetHandler.drainVis(worldObj, xCoord, yCoord, zCoord, aspect, 1);
	}

	@Override
	public boolean getMutationCharge() {
		if (this.currentMutationCharges > 0) {
			--this.currentMutationCharges;
			return true;
		}
		return false;
	}

	@Override
	public boolean getDeathRateCharge() {
		if (this.currentDeathCharges > 0) {
			--this.currentDeathCharges;
			return true;
		}
		return false;
	}

	@Override
	public boolean getProductionCharge() {
		if (this.currentProductionCharges > 0) {
			--this.currentProductionCharges;
			return true;
		}
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		
		this.currentMutationCharges = tag.getInteger("mutationCharges");
		this.currentMutationVis = tag.getInteger("mutationVis");
		this.currentDeathCharges = tag.getInteger("deathCharges");
		this.currentDeathVis = tag.getInteger("deathVis");
		this.currentProductionCharges = tag.getInteger("productionCharges");
		this.currentProductionVis = tag.getInteger("productionVis");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		tag.setInteger("mutationCharges", currentMutationCharges);
		tag.setInteger("mutationVis", currentMutationVis);
		tag.setInteger("deathCharges", currentDeathCharges);
		tag.setInteger("deathVis", currentDeathVis);
		tag.setInteger("productionCharges", currentProductionCharges);
		tag.setInteger("productionVis", currentProductionVis);
	}
}
