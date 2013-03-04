package thaumicbees.item;

import thaumcraft.api.IVisRepairable;
import thaumcraft.api.ThaumcraftApi;
import thaumicbees.main.CommonProxy;
import thaumicbees.main.Config;
import thaumicbees.main.utils.compat.ThaumcraftHelper;
import forestry.api.arboriculture.IToolGrafter;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class ItemThaumiumGrafter extends Item implements IVisRepairable, IToolGrafter
{

	public ItemThaumiumGrafter(int id)
	{
		super(id);
		this.setCreativeTab(forestry.api.core.Tabs.tabArboriculture);
		this.setMaxDamage(25);
		this.setTextureFile(CommonProxy.TCBEES_ITEMS_IMAGE);
		this.setIconIndex(21);
		this.setItemName("tb.thaumiumGrafter");
	}

	@Override
	public float getSaplingModifier(ItemStack stack, World world, int x, int y, int z)
	{
		return 100f;
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
	public boolean onBlockDestroyed(ItemStack itemstack, World world, int id, int x, int y, int z, EntityLiving entityliving) {
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
