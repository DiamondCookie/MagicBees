package magicbees.main.utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import magicbees.item.types.ResourceType;
import magicbees.main.ThaumicBees;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabThaumicBees extends CreativeTabs
{
	public static TabThaumicBees tabThaumicBees = new TabThaumicBees();

	public TabThaumicBees()
	{
		super(getNextID(), "thaumicBees");
	}

    public ItemStack getIconItemStack()
    {
        return ThaumicBees.getConfig().miscResources.getStackForType(ResourceType.RESEARCH_BEEINFUSION);
    }
}