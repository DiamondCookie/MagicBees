package magicbees.bees;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IHiveDrop;
import forestry.api.genetics.IAllele;

public class HiveDrop implements IHiveDrop
{
	private IAllele[] template;
	private int chance;
	private ItemStack[] bonus;
	
	public HiveDrop(IAllele[] beeTemplate, ItemStack[] bonusDrops, int dropChance)
	{
		this.template = beeTemplate;
		this.bonus = bonusDrops;
		this.chance = dropChance;
	}

	@Override
	public ItemStack getPrincess(World world, int x, int y, int z, int fortune)
	{
		return BeeManager.beeRoot.getMemberStack(BeeManager.beeRoot.getBee(world, BeeManager.beeRoot.templateAsGenome(this.template)), EnumBeeType.PRINCESS.ordinal());
	}

	@Override
	public Collection<ItemStack> getDrones(World world, int x, int y, int z, int fortune)
	{
		ArrayList<ItemStack> value = new ArrayList<ItemStack>(1);
		value.add(BeeManager.beeRoot.getMemberStack(BeeManager.beeRoot.getBee(world, BeeManager.beeRoot.templateAsGenome(this.template)), EnumBeeType.DRONE.ordinal()));
		return value;
	}

	@Override
	public Collection<ItemStack> getAdditional(World world, int x, int y, int z, int fortune)
	{
		ArrayList<ItemStack> value = new ArrayList<ItemStack>(bonus.length);
		
		for (int i = 0; i < bonus.length; ++i)
		{
			value.add(bonus[i].copy());
		}
		
		return value;
	}

	@Override
	public int getChance(World world, int x, int y, int z)
	{
		return this.chance;
	}
}
