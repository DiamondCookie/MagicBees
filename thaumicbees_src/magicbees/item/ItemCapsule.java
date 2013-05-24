package magicbees.item;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import forestry.api.recipes.RecipeManagers;
import magicbees.block.types.PlankType;
import magicbees.item.types.CapsuleType;
import magicbees.item.types.LiquidType;
import magicbees.main.CommonProxy;
import magicbees.main.ThaumicBees;
import magicbees.main.utils.TabThaumicBees;
import magicbees.main.utils.VersionInfo;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;

public class ItemCapsule extends Item
{
	private CapsuleType capsuleType;
	
	public ItemCapsule(CapsuleType type, int itemId, int maxStackSize)
	{
		super(itemId);
		this.capsuleType = type;
		this.setCreativeTab(TabThaumicBees.tabThaumicBees);
		this.setHasSubtypes(true);
		this.setMaxStackSize(maxStackSize);
		this.setUnlocalizedName("tb.capsule." + type.toString().toLowerCase());
		GameRegistry.registerItem(this, "tb.capsule." + type.toString().toLowerCase());
	}
	
	public CapsuleType getType()
	{
		return this.capsuleType;
	}

	@Override
	public String getItemDisplayName(ItemStack itemStack)
	{
		return LiquidType.values()[itemStack.getItemDamage()].getDisplayName() + " " + this.capsuleType.getLocalizedName();
	}

	public ItemStack getCapsuleForLiquid(LiquidType l)
	{
		return new ItemStack(this, 1, l.ordinal());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs tab, List itemList)
	{
		for (LiquidType l : LiquidType.values())
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
    public void registerIcons(IconRegister par1IconRegister)
    {
    	this.itemIcon = par1IconRegister.registerIcon(VersionInfo.ModName.toLowerCase() + ":capsule" + this.capsuleType.getName().substring(0, 1).toUpperCase() 
    			+ this.capsuleType.getName().substring(1));
    	for (LiquidType t : LiquidType.values())
    	{
    		if (t != LiquidType.EMPTY && t.liquidIcon == null)
    		{
    			t.liquidIcon = par1IconRegister.registerIcon(VersionInfo.ModName.toLowerCase() + ":liquids/" + t.liquidID);
    		}
    	}
    }

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamageForRenderPass(int metadata, int pass)
	{
		Icon i = this.itemIcon;
		if (metadata != 0 && pass == 0)
		{
			i = LiquidType.values()[Math.max(0, Math.min(metadata, LiquidType.values().length - 1))].liquidIcon;
		}
		return i;
	}

	@Override
	public int getRenderPasses(int metadata)
	{
		return (metadata == 0) ? 1 : 2;
	}
}
