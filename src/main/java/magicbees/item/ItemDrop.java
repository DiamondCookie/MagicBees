package magicbees.item;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import magicbees.item.types.DropType;
import magicbees.main.utils.compat.ForestryHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import forestry.api.core.Tabs;

public class ItemDrop extends Item
{
	public ItemDrop()
	{
		super();
		this.setCreativeTab(Tabs.tabApiculture);
		this.setHasSubtypes(true);
		this.setUnlocalizedName("drop");
		GameRegistry.registerItem(this, "drop");
	}
	
	public ItemStack getStackForType(DropType type)
	{
		return new ItemStack(this, 1, type.ordinal());
	}
	
	public ItemStack getStackForType(DropType type, int count)
	{
		return new ItemStack(this, count, type.ordinal());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		for (DropType type : DropType.values())
		{
			list.add(this.getStackForType(type));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@Override
	public int getRenderPasses(int metadata)
	{
		return 2;
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon secondIcon;
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(ForestryHelper.Name.toLowerCase() + ":honeyDrop.0");
		this.secondIcon = par1IconRegister.registerIcon(ForestryHelper.Name.toLowerCase() + ":honeyDrop.1");
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return (pass == 0) ? this.itemIcon : this.secondIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		int meta = Math.max(0, Math.min(DropType.values().length - 1, stack.getItemDamage()));
		int colour = DropType.values()[meta].combColour[0];
		
		if (pass >= 1)
		{
			colour = DropType.values()[meta].combColour[1];
		}
		
		return colour;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return DropType.values()[stack.getItemDamage()].getName();
	}

}
