package magicbees.bees;

import magicbees.main.Config;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aura.AuraNode;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IEffectData;

public class AlleleEffectAuraNodePurify extends AlleleEffect
{
	private int nodeRange;
	
	public AlleleEffectAuraNodePurify(String id, boolean dominant, int timeout, int range)
	{
		super(id, dominant, timeout);
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
	public IEffectData doEffectThrottled(IBeeGenome genome, IEffectData storedData, IBeeHousing housing)
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
		
		return storedData;
	}

}
