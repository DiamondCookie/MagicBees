package magicbees.tileentity;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import magicbees.bees.AuraChargeType;

public class AuraCharges {
	private static final String auraChargesTagName = "AuraCharges";

	private final Map<AuraChargeType, Long> charges = new HashMap<AuraChargeType, Long>(AuraChargeType.values().length);

	public void start(AuraChargeType auraChargeType, World worldObj) {
		charges.put(auraChargeType, worldObj.getTotalWorldTime());
	}

	public boolean isActive(AuraChargeType auraChargeType) {
		return charges.keySet().contains(auraChargeType);
	}

	public boolean isExpired(AuraChargeType auraChargeType, World worldObj) {
		Long chargeStart = charges.get(auraChargeType);
		return chargeStart == null || chargeStart + auraChargeType.duration <= worldObj.getTotalWorldTime();
	}

	public void stop(AuraChargeType AuraChargeType) {
		charges.remove(AuraChargeType);
	}

	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		NBTTagCompound auraChargeTags = new NBTTagCompound();
		for (Map.Entry<AuraChargeType, Long> chargeEntry : charges.entrySet()) {
			String chargeName = chargeEntry.getKey().toString();
			Long chargeStart = chargeEntry.getValue();
			auraChargeTags.setLong(chargeName, chargeStart);
		}
		nbtTagCompound.setTag(auraChargesTagName, auraChargeTags);
	}

	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		NBTTagCompound auraChargeTags = nbtTagCompound.getCompoundTag(auraChargesTagName);
		for (AuraChargeType auraCharge : AuraChargeType.values()) {
			String chargeName = auraCharge.toString();
			Long chargeStart = auraChargeTags.getLong(chargeName);
			if (chargeStart > 0) {
				charges.put(auraCharge, chargeStart);
			}
		}
	}

	public int writeToFlags() {
		int flags = 0;
		for (AuraChargeType auraChargeType : charges.keySet()) {
			flags |= auraChargeType.flag;
		}
		return flags;
	}

	public void readFromFlags(int flags) {
		charges.clear();
		for (AuraChargeType auraCharge : AuraChargeType.values()) {
			if ((flags & auraCharge.flag) > 0) {
				charges.put(auraCharge, 0L);
			}
		}
	}
}
