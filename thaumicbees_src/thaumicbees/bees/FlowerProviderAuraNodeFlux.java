package thaumicbees.bees;

import cpw.mods.fml.common.registry.LanguageRegistry;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumicbees.main.utils.LocalizationManager;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IFlowerProvider;
import forestry.api.genetics.IPollinatable;

public class FlowerProviderAuraNodeFlux extends FlowerProviderAuraNode
{
	@Override
	public ItemStack[] affectProducts(World world, IBeeGenome genome, int x, int y, int z, ItemStack[] products)
	{
		if (world.rand.nextInt(20) < 1)
		{
			EnumTag tag;
			do
			{
				tag = EnumTag.values()[world.rand.nextInt(EnumTag.values().length)];
			}
			while (tag == EnumTag.UNKNOWN || tag == EnumTag.WEATHER);
			
			int nodeId = ThaumcraftApi.getClosestAuraWithinRange(world, x, y, z, this.nodeRange);
			if (nodeId != -1)
			{
				int quantity = world.rand.nextInt(2) + 1;
				ThaumcraftApi.queueNodeChanges(nodeId, 0, 0, false, new ObjectTags().add(tag, quantity), 0f, 0f, 0f);
			}
		}
		
		return products;
	}

	@Override
	public String getDescription()
	{
		return LocalizationManager.getLocalizedString("tb.bees.flower.nodeFlux");
	}
}
