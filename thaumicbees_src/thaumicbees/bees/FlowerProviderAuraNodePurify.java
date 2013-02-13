package thaumicbees.bees;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.AuraNode;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumicbees.main.Config;
import forestry.api.apiculture.IBeeGenome;

public class FlowerProviderAuraNodePurify extends FlowerProviderAuraNode
{
	@Override
	public boolean growFlower(World world, IBeeGenome genome, int x, int y, int z)
	{
		return true;
	}

	@Override
	public String getDescription()
	{
		return LanguageRegistry.instance().getStringLocalization("tb.bees.flower.nodePure");
	}

	@Override
	public ItemStack[] affectProducts(World world, IBeeGenome genome, int x, int y, int z, ItemStack[] products)
	{
		if (world.rand.nextInt(25) < 1)
		{
			AuraNode node = this.getNodeWithinRange(world, x, y, z);
			if (node != null && (1.0 * node.level) / node.baseLevel > 0.75)
			{
				int aspectCount = node.flux.tags.size();
				if (aspectCount > 0)
				{
					EnumTag selectedTag = node.flux.getAspectsSorted()[world.rand.nextInt(node.flux.tags.size())];
					if (selectedTag != null)
					{
						node.flux.remove(selectedTag, 1);
						ThaumcraftApi.queueNodeChanges(node.key, node.baseLevel / -16, 0, false, new ObjectTags().add(selectedTag, -1), 0, 0, 0);
						products = addItemToProducts(products, new ItemStack(Config.solidFlux, 1, selectedTag.id));
					}
				}
			}
		}
		return products;
	}
}
