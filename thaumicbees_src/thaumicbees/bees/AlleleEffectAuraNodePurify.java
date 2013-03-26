package thaumicbees.bees;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.AuraNode;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumicbees.main.Config;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IEffectData;

public class AlleleEffectAuraNodePurify extends AlleleEffect
{
	private int effectTimeout;
	private int nodeRange;
	
	public AlleleEffectAuraNodePurify(String id, boolean dominant, int timeout, int range)
	{
		super(id, dominant);
		this.effectTimeout = timeout;
		this.nodeRange = range;
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
	public IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing)
	{
		int timeout = storedData.getInteger(0);
		if (timeout >= this.effectTimeout)
		{
			World world = housing.getWorld();
			int x = housing.getXCoord();
			int y = housing.getYCoord();
			int z = housing.getZCoord();
			
			int nodeId = ThaumcraftApi.getClosestAuraWithinRange(world, x, y, z, this.nodeRange);
			if (nodeId >= 0)
			{
				AuraNode node = ThaumcraftApi.getNodeCopy(nodeId);
				if (node != null)
				{
					int aspectCount = node.flux.tags.size();
					if (aspectCount > 0)
					{
						EnumTag selectedTag = node.flux.getAspectsSorted()[world.rand.nextInt(node.flux.tags.size())];
						if (selectedTag != null && housing.addProduct(new ItemStack(Config.solidFlux, 1, selectedTag.id), true))
						{
							node.flux.remove(selectedTag, 1);
							ThaumcraftApi.queueNodeChanges(node.key, 0, 0, false, new ObjectTags().remove(selectedTag, 1), 0, 0, 0);
						}
					}
				}
			}
			storedData.setInteger(0, 0);
		}
		else
		{
			storedData.setInteger(0, timeout + 1);
		}
		
		return storedData;
	}

}
