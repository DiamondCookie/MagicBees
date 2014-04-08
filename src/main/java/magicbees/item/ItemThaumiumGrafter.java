package magicbees.item;

import magicbees.main.CommonProxy;
import magicbees.main.Config;
import magicbees.main.utils.compat.ThaumcraftHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import thaumcraft.api.IRepairableExtended;
import thaumcraft.api.ThaumcraftApi;
import forestry.api.arboriculture.IToolGrafter;

import java.util.HashSet;
import java.util.Set;

public class ItemThaumiumGrafter extends Item implements IRepairableExtended, IToolGrafter
{
	public ItemThaumiumGrafter()
	{
		super();
		this.setMaxStackSize(1);
		this.setMaxDamage(15);
		this.setCreativeTab(forestry.api.core.Tabs.tabArboriculture);
		this.setUnlocalizedName(this.iconString = CommonProxy.DOMAIN + ":thaumiumGrafter");
	}

	@Override
	public float getSaplingModifier(ItemStack stack, World world, EntityPlayer player, int x, int y, int z)
	{
		return 100f;
	}

	@Override
	public Set<String> getToolClasses(ItemStack itemStack) {
		HashSet<String> classes = new HashSet<String>(1);
		classes.add("grafter");
		return classes;
	}

	@Override
	public int getHarvestLevel(ItemStack itemStack, String toolClass) {
		return toolClass.equals("grafter") ? 3 : 0;
	}
	
	@Override
	public float func_150893_a(ItemStack itemStack, Block block)
	{
		return 1f;
	}

	@Override
	public float getDigSpeed(ItemStack itemStack, Block block, int metadata)
	{
		return ForgeHooks.isToolEffective(itemStack, block, metadata) ? 4.8f : func_150893_a(itemStack, block);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, int x, int y, int z,
	                                EntityLivingBase entityLiving) {
		int damage = 1;
		if (block == Config.tcLeaf)
		{
			int meta = world.getBlockMetadata(x, y, z) & 1;
			if (meta == 0 || meta == 1)
			{
				this.dropItem(world, x, y, z, new ItemStack(Config.tcPlant, 1, meta));
			}
		}
		itemstack.damageItem(damage, entityLiving);
		return true;
	}
	
	private void dropItem(World world, int x, int y, int z, ItemStack item)
	{
        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops"))
        {
            float f = 0.7F;
            double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double)x + d0, (double)y + d1, (double)z + d2, item);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
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
        return ThaumcraftApi.toolMatThaumium.customCraftingMaterial == par2ItemStack.getItem() ? true : super.getIsRepairable
		        (par1ItemStack, par2ItemStack);
    }

	@Override
	public boolean doRepair(ItemStack stack, EntityPlayer player, int enchantLevel)
	{
		boolean flag = false;
		if (stack.getItemDamage() > 0)
		{
			flag = true;
			player.addExhaustion(0.6f * (enchantLevel * enchantLevel));
		}
		return flag;
	}
}
