package magicbees.item;

import forestry.core.items.ItemForestryBlock;
import magicbees.block.types.HiveType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

/**
 * Created by Allen on 7/29/2014.
 */
public class ItemMagicHive extends ItemForestryBlock {
    public ItemMagicHive(Block block) {
        super(block);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return getBlock().getUnlocalizedName() + "." + HiveType.getHiveFromMeta(itemstack.getItemDamage()).name().toLowerCase();
    }

    @Override
    public String getItemStackDisplayName(ItemStack p_77653_1_)
    {
        return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(p_77653_1_) + ".name")).trim();
    }
}