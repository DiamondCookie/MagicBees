package magicbees.main.utils;

import magicbees.item.types.ResourceType;
import magicbees.main.Config;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TabMagicBees extends CreativeTabs
{
	public static TabMagicBees tabMagicBees = new TabMagicBees();

	public TabMagicBees()
	{
		super(getNextID(), "magicBees");
	}

	public Item getTabIconItem()
	{
		return Config.miscResources.getStackForType(ResourceType.RESEARCH_BEEINFUSION).getItem();
	}
}