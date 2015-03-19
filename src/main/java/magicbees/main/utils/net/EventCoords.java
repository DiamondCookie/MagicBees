package magicbees.main.utils.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import magicbees.main.utils.ChunkCoords;
import magicbees.main.utils.LogHelper;

public class EventCoords extends EventBase {
	
	private ChunkCoords coords;
	
	public EventCoords(NetworkEventHandler.EventType type, ChunkCoords position) {
		super(type);
		this.coords = position;
	}
	
	public EventCoords(NetworkEventHandler.EventType type, DataInputStream byteStream) {
		super(type);
	}
	
	public ChunkCoords getCoords() {
		return this.coords;
	}
	
	@Override
	protected void writeDataToOutputStream(DataOutputStream byteStream) {
		super.writeDataToOutputStream(byteStream);
		
		try {
			byteStream.writeInt(coords.dimension);
			byteStream.writeInt(coords.x);
			byteStream.writeInt(coords.y);
			byteStream.writeInt(coords.z);
		}
		catch (IOException e) {
			LogHelper.error("Could not write EventCoords data to stream.");
			e.printStackTrace();
		}
	}
	
	@Override
	protected void readDataFromInputStream(DataInputStream byteStream) {
		super.readDataFromInputStream(byteStream);
		
		try {
			int dim = byteStream.readInt();
			int x = byteStream.readInt();
			int y = byteStream.readInt();
			int z = byteStream.readInt();
			
			this.coords = new ChunkCoords(dim, x, y, z);
		}
		catch (IOException e) {
			LogHelper.error("Could not read EventCoords data from stream.");
			e.printStackTrace();
		}
	}
}
