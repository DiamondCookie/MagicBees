package magicbees.world;

import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Mod;

import magicbees.main.Config;
import magicbees.main.utils.ChunkCoords;
import magicbees.world.feature.FeatureHive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ChunkDataEvent;

public class WorldGeneratorHandler implements IWorldGenerator
{
	public WorldTicker ticker;
	
	public WorldGeneratorHandler()
	{
		this.ticker = new WorldTicker(this);
		FMLCommonHandler.instance().bus().register(ticker);
		MinecraftForge.EVENT_BUS.register(this);
		
		FeatureHive.initialize();
	}
	
	@Mod.EventHandler
	public void chunkSaveEventHandler(ChunkDataEvent.Save event)
	{
		NBTTagCompound tag = new NBTTagCompound();
		if (Config.doHiveRetrogen)
		{
			tag.setBoolean("hiveRetrogen", true);
		}
		
		event.getData().setTag("MagicBees", tag);
	}
	
	@Mod.EventHandler
	public void chunkLoadEventHandler(ChunkDataEvent.Load event)
	{
		boolean doRetrogen = false;
		
		NBTTagCompound tag = (NBTTagCompound)event.getData().getTag("MagicBees");
		if (tag == null)
		{
			doRetrogen = true;
		}
		else if (Config.doHiveRetrogen)
		{
			if (!tag.hasKey("hiveRetrogen") || Config.forceHiveRegen)
			{
				doRetrogen = true;
			}
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
			if (initialGen || Config.doHiveRetrogen)
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