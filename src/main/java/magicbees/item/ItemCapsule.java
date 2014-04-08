package magicbees.item;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import magicbees.item.types.CapsuleType;
import magicbees.item.types.FluidType;
import magicbees.main.CommonProxy;
import magicbees.main.utils.TabMagicBees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemCapsule extends Item
{
	private CapsuleType capsuleType;
	
	public ItemCapsule(CapsuleType type, int maxStackSize)
	{
		super();
		this.capsuleType = type;
		this.setCreativeTab(TabMagicBees.tabMagicBees);
		this.setHasSubtypes(true);
		this.setMaxStackSize(maxStackSize);
		this.setUnlocalizedName("capsule." + type.toString().toLowerCase());
		GameRegistry.registerItem(this, "capsule." + type.toString().toLowerCase());
	}
	
	public CapsuleType getType()
	{
		return this.capsuleType;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack)
	{
		return String.format(this.capsuleType.getLocalizedName(), FluidType.values()[itemStack.getItemDamage()].getDisplayName());
	}

	public ItemStack getCapsuleForLiquid(FluidType l)
	{
		return new ItemStack(this, 1, l.ordinal());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List itemList)
	{
		for (FluidType l : FluidType.values())
		{
			if (l.available)
			{
				itemList.add(new ItemStack(this, 1, l.ordinal()));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
    	this.itemIcon = par1IconRegister.registerIcon(CommonProxy.DOMAIN + ":capsule" + this.capsuleType.getName().substring(0, 1).toUpperCase() 
    			+ this.capsuleType.getName().substring(1));
    	for (FluidType t : FluidType.values())
    	{
    		if (t != FluidType.EMPTY && t.liquidIcon == null)
    		{
    			t.liquidIcon = par1IconRegister.registerIcon(CommonProxy.DOMAIN + ":liquids/" + t.liquidID.toLowerCase());
    		}
    	}
    }

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int metadata, int pass)
	{
		IIcon i = this.itemIcon;
		if (metadata != 0 && pass == 0)
		{
			i = FluidType.values()[Math.max(0, Math.min(metadata, FluidType.values().length - 1))].liquidIcon;
		}
		return i;
	}

	@Override
	public int getRenderPasses(int metadata)
	{
		return (metadata == 0) ? 1 : 2;
	}
}
