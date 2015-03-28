package magicbees.tileentity;

import java.util.HashMap;
import java.util.Map;

import magicbees.api.bees.IAuraChargeType;
import magicbees.bees.AuraChargeType;
import magicbees.api.bees.IMagicApiaryAuraProvider;
import magicbees.main.CommonProxy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.visnet.VisNetHandler;

public class TileEntityVisAuraProvider extends TileEntity implements IMagicApiaryAuraProvider {

	public static final String tileEntityName = CommonProxy.DOMAIN + ".visAuraProvider";
	
	private static final int MAX_CHARGES = 10;
	private static final int VIS_PER_CHARGE = 8;

	private static class AuraCharge {
		int charges;
		int vis;

		public int[] toArray() {
			return new int[]{charges, vis};
		}

		public void fromArray(int[] array) {
			if (array.length == 2) {
				charges = array[0];
				vis = array[1];
			}
		}
	}

	private final Map<AuraChargeType, AuraCharge> currentCharges;

	public TileEntityVisAuraProvider() {
		super();

		AuraChargeType[] auraChargeTypes = AuraChargeType.values();
		currentCharges = new HashMap<AuraChargeType, AuraCharge>(auraChargeTypes.length);
		for (AuraChargeType auraChargeType : auraChargeTypes) {
			currentCharges.put(auraChargeType, new AuraCharge());
		}
	}
	
	@Override
	public void updateEntity() {
		
		long tick = worldObj.getTotalWorldTime();

		for (Map.Entry<AuraChargeType, AuraCharge> currentCharge : currentCharges.entrySet()) {
			AuraChargeType type = currentCharge.getKey();
			AuraCharge auraCharge = currentCharge.getValue();

			if (auraCharge.charges < MAX_CHARGES && (tick % type.tickRate) == 0) {
				auraCharge.vis += getVisFromNet(type.aspect);
				if (auraCharge.vis >= VIS_PER_CHARGE) {
					auraCharge.vis -= VIS_PER_CHARGE;
					auraCharge.charges++;
				}
			}
		}
	}
	
	private int getVisFromNet(Aspect aspect) {
		return VisNetHandler.drainVis(worldObj, xCoord, yCoord, zCoord, aspect, 1);
	}

	@Override
	public boolean getMutationCharge() {
		return getCharge(AuraChargeType.MUTATION);
	}

	@Override
	public boolean getDeathRateCharge() {
		return getCharge(AuraChargeType.DEATH);
	}

	@Override
	public boolean getProductionCharge() {
		return getCharge(AuraChargeType.PRODUCTION);
	}

	@Override
	public boolean getCharge(IAuraChargeType auraChargeType) {
		AuraCharge auraCharge = currentCharges.get(auraChargeType);
		if (auraCharge == null) {
			return false;
		}
		if (auraCharge.charges > 0) {
			auraCharge.charges--;
			return true;
		}

		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		for (Map.Entry<AuraChargeType, AuraCharge> charge : currentCharges.entrySet()) {
			String auraName = charge.getKey().toString();
			int[] array = tag.getIntArray(auraName);
			AuraCharge auraCharge = charge.getValue();
			auraCharge.fromArray(array);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		for (Map.Entry<AuraChargeType, AuraCharge> charge : currentCharges.entrySet()) {
			String auraName = charge.getKey().toString();
			AuraCharge auraCharge = charge.getValue();
			int[] array = auraCharge.toArray();
			tag.setIntArray(auraName, array);
		}
	}
}
