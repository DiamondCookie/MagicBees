package magicbees.world;

import java.util.LinkedList;
import java.util.Random;

import magicbees.main.MagicBees;
import magicbees.main.utils.ChunkCoords;
import magicbees.world.feature.FeatureHive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.ChunkDataEvent;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class WorldHandler implements IWorldGenerator
{
	public WorldTicker ticker;
	
	public WorldHandler()
	{
		this.ticker = new WorldTicker(this);
		TickRegistry.registerTickHandler(this.ticker, Side.SERVER);
		MinecraftForge.EVENT_BUS.register(this);
		
		FeatureHive.initialize();
	}
	
	@ForgeSubscribe
	public void chunkSaveEventHandler(ChunkDataEvent.Save event)
	{
		NBTTagCompound tag = new NBTTagCompound();
		if (MagicBees.getConfig().DoHiveRetrogen)
		{
			tag.setBoolean("hiveRetrogen", true);
		}
		
		event.getData().setTag("MagicBees", tag);
	}
	
	@ForgeSubscribe
	public void chunkLoadEventHandler(ChunkDataEvent.Load event)
	{
		boolean doRetrogen = false;
		
		NBTTagCompound tag = (NBTTagCompound)event.getData().getTag("MagicBees");
		if (tag == null)
		{
			doRetrogen = true;
		}
		
		if (doRetrogen)
		{
			ChunkCoords coords = new ChunkCoords(event.getChunk());
			ticker.queueChunkCoords(coords);
		}
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		generateWorld(world, random, chunkX, chunkZ, true);
	}
	
	public void generateWorld(World world, Random random, int chunkX, int chunkZ, boolean initialGen)
	{
		boolean modified = false;

		if (world.provider.terrainType != WorldType.FLAT)
		{
			if (initialGen || MagicBees.getConfig().DoHiveRetrogen)
			{
				FeatureHive.generateHives(world, random, chunkX, chunkZ, initialGen);
				modified = true;
			}
		}
		
		if (!initialGen && modified)
		{
			world.getChunkFromChunkCoords(chunkX, chunkZ).setChunkModified();
		}
	}

}
