package thaumicbees.item;

import thaumcraft.api.IVisRepairable;
import thaumcraft.api.ThaumcraftApi;
import thaumicbees.main.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class ItemThaumiumScoop extends Item implements IVisRepairable
{
	public ItemThaumiumScoop(int itemID)
	{
		super(itemID);
		this.setMaxStackSize(1);
		this.setMaxDamage(30);
		this.setIconIndex(20);
		this.setTextureFile(CommonProxy.TCBEES_ITEMS_IMAGE);
		this.setItemName("tb.thaumiumScoop");
		this.setCreativeTab(forestry.api.core.Tabs.tabApiculture);
	}

	@Override
	public float getStrVsBlock(ItemStack itemStack, Block block)
	{
		return 1f;
	}

	@Override
	public float getStrVsBlock(ItemStack itemStack, Block block, int metadata)
	{
		return ForgeHooks.isToolEffective(itemStack, block, metadata) ? 4f : getStrVsBlock(itemStack, block);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, int i, int j, int k, int l, EntityLiving entityliving) {
		itemstack.damageItem(1, entityliving);
		return true;
	}
	
    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return ThaumcraftApi.toolMatThaumium.getEnchantability();
    }

    /**
     * Return the name for this tool's material.
     */
    public String getToolMaterialName()
    {
        return ThaumcraftApi.toolMatThaumium.toString();
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return ThaumcraftApi.toolMatThaumium.getToolCraftingMaterial() == par2ItemStack.itemID ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

	@Override
	public void doRepair(ItemStack stack, Entity e)
	{
		if (stack.getItemDamage() > 0)
		{
			if (ThaumcraftApi.decreaseClosestAura(e.worldObj, e.posX, e.posY, e.posZ, 1, true))
			{
				stack.damageItem(-1, (EntityLiving)e);
			}
		}
	}
}
