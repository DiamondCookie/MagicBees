package thaumicbees.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import extrabiomes.utility.CreativeTab;
import thaumicbees.item.ItemPropolis.PropolisType;
import thaumicbees.item.ItemWax.WaxType;
import thaumicbees.main.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMiscResources extends Item
{
	public enum ResourceType
	{
		KNOWLEDGE_FRAGMENT("Lore Fragment", 2),
		;
		
		private ResourceType(String n, int i)
		{
			this.name = n;
			this.iconIdx = i;
		}
		public String name;
		public int iconIdx;
	}

	public ItemMiscResources(int ID)
	{
		super(ID);
		this.setTextureFile(CommonProxy.TCBEES_ITEMSPNG);
		this.setCreativeTab(CreativeTab.tabMaterials);
		this.setHasSubtypes(true);
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
		super.getSubItems(id, tabs, list);
		for (ResourceType type : ResourceType.values())
		{
			list.add(this.getStackForType(type));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getIconFromDamage(int meta)
	{
		int iconIdx = 0;
		if (meta >= 0 && meta < ResourceType.values().length)
		{
			iconIdx = ResourceType.values()[meta].iconIdx;
		}
		return iconIdx;
	}

	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		String result = "";
		int meta = stack.getItemDamage();
		if (meta >= 0 && meta < ResourceType.values().length)
		{
			result = ResourceType.values()[meta].name;
		}
		return result;
	}

}
