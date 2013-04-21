package thaumicbees.item;

import java.util.List;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import thaumcraft.api.EnumTag;
import thaumicbees.main.CommonProxy;
import thaumicbees.main.ThaumicBees;
import thaumicbees.main.utils.LocalizationManager;
import thaumicbees.main.utils.TabThaumicBees;
import thaumicbees.main.utils.VersionInfo;
import thaumicbees.main.utils.compat.ForestryHelper;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCrystalAspect extends Item
{
	public ItemCrystalAspect(int itemId)
	{
		super(itemId);
		this.setCreativeTab(TabThaumicBees.tabThaumicBees);
		this.setHasSubtypes(true);
		this.setUnlocalizedName("crystalAspect");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs tab, List list)
	{
		for (EnumTag tag : EnumTag.values())
		{
			if (tag == EnumTag.UNKNOWN)
			{
				continue;
			}
			list.add(new ItemStack(this.itemID, 1, tag.id));
		}
	}
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(VersionInfo.ModName.toLowerCase() + ":crystalAspect");
    }

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		return EnumTag.get(stack.getItemDamage()).color;
	}

	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		return String.format(LocalizationManager.getLocalizedString("tb.resource.crystalAspect"), EnumTag.get(stack.getItemDamage()).name);
	}
}
