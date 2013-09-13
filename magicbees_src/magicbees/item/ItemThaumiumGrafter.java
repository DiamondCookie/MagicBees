package magicbees.item;

import magicbees.main.Config;
import magicbees.main.utils.VersionInfo;
import magicbees.main.utils.compat.ThaumcraftHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.arboriculture.IToolGrafter;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IRepairableExtended;
import thaumcraft.api.ThaumcraftApi;

public class ItemThaumiumGrafter extends Item implements IRepairableExtended, IToolGrafter
{
	public ItemThaumiumGrafter(int id)
	{
		super(id);
		this.setMaxStackSize(1);
		this.setMaxDamage(15);
		this.setCreativeTab(forestry.api.core.Tabs.tabArboriculture);
		this.setUnlocalizedName(this.iconString = VersionInfo.ModName.toLowerCase() + ":thaumiumGrafter");
	}

	@Override
	public float getSaplingModifier(ItemStack stack, World world, EntityPlayer player, int x, int y, int z)
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
		return ForgeHooks.isToolEffective(itemStack, block, metadata) ? 4.8f : getStrVsBlock(itemStack, block);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, int id, int x, int y, int z, EntityLivingBase entityLiving) {
		int damage = 1;
		if (id == Config.tcLeaf.blockID)
		{
			Block block = Block.blocksList[id];
			int meta = world.getBlockMetadata(x, y, z) & 1;
			block.dropBlockAsItemWithChance(world, x, y, z, meta, 0, 0);
			if (meta == ThaumcraftHelper.TreeType.GREATWOOD.ordinal())
			{
				damage = 2;
			}
			else if (meta == ThaumcraftHelper.TreeType.SILVERWOOD.ordinal())
			{
				damage = 4;
			}
		}
		itemstack.damageItem(damage, entityLiving);
		
		if (entityLiving instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer)entityLiving;
		}
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
	public boolean doRepair(ItemStack stack, EntityPlayer player, int enchantLevel)
	{
		boolean flag = false;
		if (stack.getItemDamage() > 0)
		{
			flag = true;
			if (enchantLevel > 2)
			{
				player.setFire(enchantLevel * enchantLevel / 2);
			}
			else
			{
				player.addExhaustion(0.8f * (enchantLevel * enchantLevel));
			}
		}
		return flag;
	}
}
