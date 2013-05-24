package magicbees.item;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import magicbees.item.types.ResourceType;
import magicbees.main.CommonProxy;
import magicbees.main.ThaumicBees;
import magicbees.main.utils.TabThaumicBees;
import magicbees.main.utils.VersionInfo;
import magicbees.main.utils.compat.ForestryHelper;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemMiscResources extends Item
{
	private Icon[] icons = new Icon[ResourceType.values().length];
	
	public ItemMiscResources(int ID)
	{
		super(ID);
		this.setCreativeTab(TabThaumicBees.tabThaumicBees);
		this.setHasSubtypes(true);
		this.setUnlocalizedName("tb.miscResources");
		GameRegistry.registerItem(this, "tb.miscResources");
	}
	
	public ItemStack getStackForType(ResourceType type)
	{
		return new ItemStack(this, 1, type.ordinal());
	}	
	
	public ItemStack getStackForType(ResourceType type, int count)
	{
		return new ItemStack(this, count, type.ordinal());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tabs, List list)
	{
		for (ResourceType type : ResourceType.values())
		{
			if (type.showInList)
			{
				list.add(this.getStackForType(type));
			}
		}
	}
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
    	for (int i = 0; i < ResourceType.values().length; i++)
    	{
    		this.icons[i] = par1IconRegister.registerIcon(VersionInfo.ModName.toLowerCase() + ":" + ResourceType.values()[i].getName());
    	}
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta)
	{
		return icons[meta];
	}

	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		return ResourceType.values()[stack.getItemDamage()].getLocalizedName();
	}

}
