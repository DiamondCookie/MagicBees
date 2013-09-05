package magicbees.item;

import java.util.List;

import magicbees.main.utils.LocalizationManager;
import magicbees.main.utils.TabMagicBees;
import magicbees.main.utils.VersionInfo;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCrystalAspect extends Item
{
	public ItemCrystalAspect(int itemId)
	{
		super(itemId);
		this.setCreativeTab(TabMagicBees.tabMagicBees);
		this.setHasSubtypes(true);
		this.setUnlocalizedName("crystalAspect");
		GameRegistry.registerItem(this, "crystalAspect");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs tab, List list)
	{
		/*for (EnumTag tag : EnumTag.values())
		{
			if (tag == EnumTag.UNKNOWN)
			{
				continue;
			}
			list.add(new ItemStack(this.itemID, 1, tag.id));
		}*/
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
		return 0;
		//return EnumTag.get(stack.getItemDamage()).color;
	}

	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		return LocalizationManager.getLocalizedString("resource.crystalAspect");
		//return String.format(LocalizationManager.getLocalizedString("resource.crystalAspect"), EnumTag.get(stack.getItemDamage()).name);
	}
}
