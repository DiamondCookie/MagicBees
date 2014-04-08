package magicbees.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import magicbees.item.types.NuggetType;
import magicbees.main.CommonProxy;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemNugget extends Item
{
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	
	public ItemNugget()
	{
		super();
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
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (NuggetType type : NuggetType.values())
		{
			if (type.isActive())
			{
				list.add(new ItemStack(item, 1, type.ordinal()));
			}
		}
	}
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
    	this.icons = new IIcon[NuggetType.values().length];
    	for (int i = 0; i < NuggetType.values().length; i++)
    	{
    		this.icons[i] = par1IconRegister.registerIcon(CommonProxy.DOMAIN + ":nugget" 
    				+ NuggetType.values()[i].name().substring(0, 1) 
    				+ NuggetType.values()[i].name().substring(1).toLowerCase());
    	}
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta)
	{
		return icons[meta];
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return NuggetType.values()[stack.getItemDamage()].getName();
	}
}
