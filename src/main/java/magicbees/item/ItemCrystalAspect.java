package magicbees.item;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import magicbees.main.CommonProxy;
import magicbees.main.utils.LocalizationManager;
import magicbees.main.utils.TabMagicBees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemCrystalAspect extends Item
{
	public ItemCrystalAspect()
	{
		super();
		this.setCreativeTab(TabMagicBees.tabMagicBees);
		this.setHasSubtypes(true);
		this.setUnlocalizedName("crystalAspect");
		GameRegistry.registerItem(this, "crystalAspect");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
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
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(CommonProxy.DOMAIN + ":crystalAspect");
    }

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		return 0;
		//return EnumTag.get(stack.getItemDamage()).color;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return LocalizationManager.getLocalizedString("resource.crystalAspect");
		//return String.format(LocalizationManager.getLocalizedString("resource.crystalAspect"), EnumTag.get(stack.getItemDamage()).name);
	}
}
