package magicbees.world;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import magicbees.main.utils.ChunkCoords;
import net.minecraft.world.World;

public class WorldTicker
{
	private WorldGeneratorHandler parent;
	private HashMap<Integer, LinkedList<ChunkCoords>> chunkRegenList;

	public WorldTicker(WorldGeneratorHandler p)
	{
		this.parent = p;
		this.chunkRegenList = new HashMap<Integer, LinkedList<ChunkCoords>>();
	}

	@SubscribeEvent
	public void onTick(TickEvent.WorldTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END)
		{
			World world = event.world;
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
