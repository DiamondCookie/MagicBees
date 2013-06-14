package magicbees.bees;

import java.lang.reflect.Method;

import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aura.AuraNode;
import net.minecraft.world.World;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IEffectData;

public class AlleleEffectAuraNodeAttract extends AlleleEffect
{
	
	public AlleleEffectAuraNodeAttract(String id, boolean isDominant, int timeout)
	{
		super(id, isDominant, timeout);
	}

	@Override
	public IEffectData validateStorage(IEffectData storedData)
	{
		if (storedData == null)
		{
			storedData = new EffectData(1, 0, 0);
		}
		return storedData;
	}

	@Override
	public IEffectData doEffectThrottled(IBeeGenome genome, IEffectData storedData, IBeeHousing housing)
	{
		World w = housing.getWorld();
		if (w.rand.nextInt(10) < 1)
		{
			float x = housing.getXCoord();
			float y = housing.getYCoord();
			float z = housing.getZCoord();
			int nodeId = ThaumcraftApi.getClosestAuraWithinRange(w, x, y, z, 1800);
			if (nodeId != -1)
			{
				AuraNode node = ThaumcraftApi.getNodeCopy(nodeId);
				if (node.level >= node.baseLevel * 4 / 5)
				{
					x = (float)(x - node.xPos);
					y = (float)(y - node.yPos);
					z = (float)(z - node.zPos);
					
					float distance = (float)Math.sqrt(x * x + y * y + z * z);
					x = x / distance;
					y = y / distance;
					z = z / distance;
					
					x *= 0.05f;
					y *= 0.05f;
					z *= 0.05f;
					
					ThaumcraftApi.queueNodeChanges(nodeId, -1, 0, false, null, x, y, z);
				}
			}
		}
		storedData.setInteger(0, 0);
		
		return storedData;
	}

}
