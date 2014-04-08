package magicbees.main.utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import magicbees.item.types.ResourceType;
import magicbees.main.Config;
import magicbees.main.MagicBees;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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