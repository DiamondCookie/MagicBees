package magicbees.main.utils;

import net.minecraft.world.chunk.Chunk;

public class ChunkCoords
{
	public int dimension;
	public int xCoord;
	public int zCoord;
	
	public ChunkCoords(Chunk chunk)
	{
		this.dimension = chunk.worldObj.provider.dimensionId;
		this.xCoord = chunk.xPosition;
		this.zCoord = chunk.zPosition;
	}
}
