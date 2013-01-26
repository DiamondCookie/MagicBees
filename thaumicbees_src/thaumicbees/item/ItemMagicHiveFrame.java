package thaumicbees.item;

import java.util.List;

import thaumcraft.api.AuraNode;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumicbees.item.types.HiveFrameType;
import thaumicbees.item.types.LiquidType;
import thaumicbees.main.CommonProxy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IHiveFrame;

public class ItemMagicHiveFrame extends Item implements IHiveFrame
{
	private HiveFrameType type;

	public ItemMagicHiveFrame(int itemID, HiveFrameType frameType)
	{
		super(itemID);
		this.type = frameType;
		this.setMaxDamage(this.type.maxDamage);
		this.setTextureFile(CommonProxy.TCBEES_ITEMS_IMAGE);
		this.setIconIndex(this.type.iconIdx);
		this.setMaxStackSize(1);
	}

	@Override
	public String getItemDisplayName(ItemStack itemStack)
	{
		return this.type.frameName;
	}
	
	// --------- IHiveFrame functions -----------------------------------------
	
	@Override
	public ItemStack frameUsed(IBeeHousing housing, ItemStack frame, IBee queen, int wear)
	{
		this.doFluxEffect(housing.getWorld(), housing.getXCoord(), housing.getYCoord(), housing.getZCoord());
		frame = this.doWear(housing.getWorld(), housing.getXCoord(), housing.getYCoord(), housing.getZCoord(), frame, wear);
		
		return frame;
	}
	
	private void doFluxEffect(World w, int x, int y, int z)
	{
		if (this.type.flux != null && w.rand.nextInt(5) <= 1)
		{			
			ThaumcraftApi.addFluxToClosest(w, x, y, z, this.type.flux);
		}
	}
	
	private ItemStack doWear(World w, int x, int y, int z, ItemStack frame, int wear)
	{
		int damage = wear;
		
		if (this.type.auraPerUse > 0)
		{
			// Attempt to use aura for the frame.
			if (!ThaumcraftApi.decreaseClosestAura(w, x, y, z, this.type.auraPerUse, true))
			{
				// Insufficient aura, or no nearby node.
				damage = wear + this.type.auraPerUse;
			}
		}
		
		frame.setItemDamage(frame.getItemDamage() + damage);
		
		if (frame.getItemDamage() >= frame.getMaxDamage())
		{
			// Break!
			frame = null;
		}
		
		return frame;
	}
	
	@Override
	public float getTerritoryModifier(IBeeGenome genome)
	{
		return this.type.territoryMod;
	}

	@Override
	public float getMutationModifier(IBeeGenome genome, IBeeGenome mate)
	{
		return this.type.mutationMod;
	}

	@Override
	public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate)
	{
		return this.type.lifespanMod;
	}

	@Override
	public float getProductionModifier(IBeeGenome genome)
	{
		return this.type.productionMod;
	}

	@Override
	public float getFloweringModifier(IBeeGenome genome)
	{
		return this.type.floweringMod;
	}

	@Override
	public boolean isSealed()
	{
		return this.type.isSealed;
	}

	@Override
	public boolean isSelfLighted()
	{
		return this.type.isLit;
	}

	@Override
	public boolean isSunlightSimulated()
	{
		return this.type.isSunlit;
	}

	@Override
	public boolean isHellish()
	{
		return this.type.isHellish;
	}

}
