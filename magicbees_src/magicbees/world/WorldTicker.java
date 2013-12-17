package magicbees.world;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import magicbees.main.CommonProxy;
import magicbees.main.utils.ChunkCoords;
import net.minecraft.world.World;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class WorldTicker implements ITickHandler
{
	private WorldGeneratorHandler parent;
	private HashMap<Integer, LinkedList<ChunkCoords>> chunkRegenList;
	
	public WorldTicker(WorldGeneratorHandler p)
	{
		this.parent = p;
		this.chunkRegenList = new HashMap<Integer, LinkedList<ChunkCoords>>();
	}
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) { }

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		World world = (World)tickData[0];
		int dimensionID = world.provider.dimensionId;
		LinkedList<ChunkCoords> chunkList = chunkRegenList.get(dimensionID);
		
		if (chunkList != null && chunkList.peekFirst() != null)
		{
			ChunkCoords coords = chunkList.removeFirst();

			// This bit is from FML's GameRegistry.generateWorld where the seed is constructed.
	        long worldSeed = world.getSeed();
	        Random random = new Random(worldSeed);
	        long xSeed = random.nextLong() >> 2 + 1L;
	        long zSeed = random.nextLong() >> 2 + 1L;
	        random.setSeed((xSeed * coords.xCoord + zSeed * coords.zCoord) ^ worldSeed);
	        
	        parent.generateWorld(world, random, coords.xCoord, coords.zCoord, false);
		}
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.WORLD);
	}

	@Override
	public String getLabel()
	{
		return CommonProxy.DOMAIN + ".world";
	}
	
	public void queueChunkCoords(ChunkCoords coords)
	{
		if (!chunkRegenList.containsKey(coords.dimension))
		{
			chunkRegenList.put(coords.dimension, new LinkedList<ChunkCoords>());
		}
		chunkRegenList.get(coords.dimension).addLast(coords);
	}

	public LinkedList<ChunkCoords> getChunksToGen(int dimensionId)
	{
		return chunkRegenList.get(dimensionId);
	}

}
