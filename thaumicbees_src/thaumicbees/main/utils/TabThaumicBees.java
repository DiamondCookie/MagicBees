package thaumicbees.main.utils;

import thaumicbees.item.types.ResourceType;
import thaumicbees.main.ThaumicBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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