package magicbees.main.utils.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.client.FMLClientHandler;

import magicbees.main.utils.ChunkCoords;
import magicbees.main.utils.LogHelper;
import magicbees.main.utils.net.NetworkEventHandler.EventType;
import magicbees.tileentity.AuraCharges;
import magicbees.tileentity.ITileEntityAuraCharged;

public class EventAuraChargeUpdate extends EventCoords {

	private int flags;

	public EventAuraChargeUpdate(ChunkCoords position, AuraCharges auraCharges) {
		super(EventType.AURA_CHARGE_UPDATE, position);
		flags = auraCharges.writeToFlags();
	}

	public EventAuraChargeUpdate(DataInputStream byteStream) {
		super(EventType.FLAGS_UPDATE, byteStream);

		this.readDataFromInputStream(byteStream);
	}

	@Override
	protected void writeDataToOutputStream(DataOutputStream byteStream) {
		super.writeDataToOutputStream(byteStream);

		try {
			byteStream.writeInt(flags);
		}
		catch (IOException e) {
			LogHelper.error("Could not write EventAuraChargeUpdate data to stream.");
			e.printStackTrace();
		}
	}

	@Override
	protected void readDataFromInputStream(DataInputStream byteStream) {
		super.readDataFromInputStream(byteStream);

		try {
			this.flags = byteStream.readInt();
		}
		catch (IOException e) {
			LogHelper.error("Could not read EventAuraChargeUpdate data from stream.");
			e.printStackTrace();
		}
	}

	@Override
	public void process(EntityPlayerMP player) {
		TileEntity tile = FMLClientHandler.instance().getClient().theWorld.getTileEntity(getCoords().x, getCoords().y, getCoords().z);

		if (tile != null && tile instanceof ITileEntityAuraCharged) {
			AuraCharges auraCharges = ((ITileEntityAuraCharged) tile).getAuraCharges();
			auraCharges.readFromFlags(flags);
		}
	}

}
