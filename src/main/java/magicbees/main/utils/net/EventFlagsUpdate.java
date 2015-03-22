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
import magicbees.tileentity.ITileEntityFlags;

public class EventFlagsUpdate extends EventCoords {

	private int[] flags;
	
	public EventFlagsUpdate(ChunkCoords position, int[] flagValues) {
		super(EventType.FLAGS_UPDATE, position);
		flags = flagValues;
	}
	
	public EventFlagsUpdate(DataInputStream byteStream) {
		super(EventType.FLAGS_UPDATE, byteStream);
		
		this.readDataFromInputStream(byteStream);
	}
	
	public int[] getFlags() {
		return flags;
	}

	@Override
	protected void writeDataToOutputStream(DataOutputStream byteStream) {
		super.writeDataToOutputStream(byteStream);
		
		try {
			byteStream.writeByte(flags.length);
			for (int index = 0; index < flags.length; ++index) {
				byteStream.writeInt(flags[index]);
			}
		}
		catch (IOException e) {
			LogHelper.error("Could not write EventFlagsUpdate data to stream.");
			e.printStackTrace();
		}
	}

	@Override
	protected void readDataFromInputStream(DataInputStream byteStream) {
		super.readDataFromInputStream(byteStream);
		
		try {
			byte length = byteStream.readByte();

			this.flags = new int[length];
			for (int index = 0; index < length; ++index) {
				flags[index] = byteStream.readInt();
			}
		}
		catch (IOException e) {
			LogHelper.error("Could not read EventFlagsUpdate data from stream.");
			e.printStackTrace();
		}
	}
	
	@Override
	public void process(EntityPlayerMP player) {
		TileEntity tile = FMLClientHandler.instance().getClient().theWorld.getTileEntity(getCoords().x, getCoords().y, getCoords().z);
			
		if (tile != null && tile instanceof ITileEntityFlags) {
			((ITileEntityFlags) tile).setFlags(this.flags);
		}
	}

}
