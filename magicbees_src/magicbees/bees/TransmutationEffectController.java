package magicbees.bees;

import java.util.ArrayList;

import magicbees.api.MagicBeesAPI;
import magicbees.api.bees.ITransmutationEffectController;
import magicbees.api.bees.ITransmutationEffectLogic;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.FMLLog;

public class TransmutationEffectController implements ITransmutationEffectController
{
	private ArrayList<ITransmutationEffectLogic> logicObjects;
	static TransmutationEffectController instance;
	
	public TransmutationEffectController()
	{
		logicObjects = new ArrayList<ITransmutationEffectLogic>();
		logicObjects.add(new TransmutationEffectRailcraft());
		logicObjects.add(new TransmutationEffectVanilla());
		MagicBeesAPI.transmutationEffectController = instance = this;
	}
	
	public void attemptTransmutations(World world, BiomeGenBase biome, ItemStack sourceBlock, int x, int y, int z)
	{
		boolean done = false;
		int i = 0;
		ITransmutationEffectLogic logic;
		while (!done && i < logicObjects.size())
		{
			logic = logicObjects.get(i++);
			try
			{
				done = logic.tryTransmutation(world, biome, sourceBlock, x, y, z);
			}
			catch (Exception e)
			{
				done = false;
				FMLLog.warning("Magic Bees encountered an issue with an ITransmutationEffectLogic provider %s. Debug information follows.", logic.getClass().getName());
				FMLLog.info(e.getMessage());
			}
			
			if (done)
			{
				logicObjects.remove(i);
				logicObjects.add(logic);
				break;
			}
		}
	}

	@Override
	public void addEffectLogic(ITransmutationEffectLogic logic)
	{
		if (logic != null && !logicObjects.contains(logic))
		{
			logicObjects.add(logic);
		}
	}

}
