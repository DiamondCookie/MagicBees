package thaumicbees.item;

import thaumcraft.api.IVisRepairable;
import thaumcraft.api.ThaumcraftApi;
import thaumicbees.main.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

public class ItemReinforcedScoop extends Item implements IVisRepairable
{
	public ItemReinforcedScoop(int itemID)
	{
		super(itemID);
		this.setMaxStackSize(1);
		this.setMaxDamage(20);
		this.setTextureFile(CommonProxy.TCBEES_ITEMS_IMAGE);
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
