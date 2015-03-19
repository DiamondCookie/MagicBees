package magicbees.main.utils;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ChunkCoords
{
	public final int dimension;
	public final int x;
	public final int y;
	public final int z;
	
	public ChunkCoords(TileEntity entity) {
		this(entity.getWorldObj(), entity.xCoord, entity.yCoord, entity.zCoord);
	}
	
	public ChunkCoords(Entity entity) {
		this(entity.worldObj, entity.chunkCoordX, entity.chunkCoordY, entity.chunkCoordZ);
	}

	public ChunkCoords(World world, int xCoord, int yCoord, int zCoord)
	{
		this(world.provider.dimensionId, xCoord, yCoord, zCoord);
	}
	
	public ChunkCoords(int dimId, int xCoord, int yCoord, int zCoord)
	{
		this.dimension = dimId;
		this.x = xCoord;
		this.y = yCoord;
		this.z = zCoord;
	}
	
}
