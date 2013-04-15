package thaumicbees.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.api.EnumTag;
import thaumicbees.item.types.NuggetType;
import thaumicbees.item.types.PollenType;
import thaumicbees.item.types.ResourceType;
import thaumicbees.main.CommonProxy;
import thaumicbees.main.utils.VersionInfo;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemNugget extends Item
{
	@SideOnly(Side.CLIENT)
	private Icon[] icons;
	
	public ItemNugget(int itemID)
	{
		super(itemID);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setHasSubtypes(true);
		this.setUnlocalizedName("beeNugget");
	}
	
	public ItemStack getStackForType(NuggetType type)
	{
		return new ItemStack(this, 1, type.ordinal());
	}
	
	public ItemStack getStackForType(NuggetType type, int count)
	{
		return new ItemStack(this, count, type.ordinal());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs tab, List list)
	{
		for (NuggetType type : NuggetType.values())
		{
			if (type.isActive())
			{
				list.add(new ItemStack(this.itemID, 1, type.ordinal()));
			}
		}
	}
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
    	this.icons = new Icon[NuggetType.values().length];
    	for (int i = 0; i < NuggetType.values().length; i++)
    	{
    		this.icons[i] = par1IconRegister.registerIcon(VersionInfo.ModName.toLowerCase() + ":nugget" 
    				+ NuggetType.values()[i].name().substring(0, 1) 
    				+ NuggetType.values()[i].name().substring(1).toLowerCase());
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
		return NuggetType.values()[stack.getItemDamage()].getName();
	}
}
