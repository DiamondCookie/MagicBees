package thaumicbees.bees.genetics;

import java.lang.reflect.Field;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class AlleleEffectSpawnWisp extends AlleleEffectSpawnMob
{
	
	private byte[] wispTypes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47 };

	public AlleleEffectSpawnWisp(String id, boolean isDominant, String identifier, String mobToSpawn)
	{
		super(id, isDominant, identifier, mobToSpawn);
	}

	@Override
	protected boolean spawnMob(IBeeGenome bee, EntityPlayer player, World world, IBeeHousing housing, boolean spawnAlternate)
	{
		boolean spawnedFlag = false;
		
		EntityLiving mob;
		if (spawnAlternate && this.alternateMob != null)
		{
			mob = (EntityLiving) EntityList.createEntityByName(this.alternateMob, world);
		}
		else
		{
			mob = (EntityLiving) EntityList.createEntityByName(this.mobName, world);
		}
		
		if (mob != null)
		{
			double pos[] = new double[3];
			pos[0] = housing.getXCoord() + (world.rand.nextDouble() * (bee.getTerritory()[0] * housing.getTerritoryModifier(bee)) - bee.getTerritory()[0] / 2);
			pos[1] = housing.getYCoord() + world.rand.nextInt(3) - 1;
			pos[2] = housing.getYCoord() + (world.rand.nextDouble() * (bee.getTerritory()[2] * housing.getTerritoryModifier(bee)) - bee.getTerritory()[2] / 2);
			
			mob.setLocationAndAngles(pos[0], pos[1], pos[2], world.rand.nextFloat() * 360f, 0f);
			
			// Try some Reflection on TC code. This could be dangerous.
			try
			{
				Class wispEntity = Class.forName("thaumcraft.common.entities.EntityWisp");
				Field type = wispEntity.getDeclaredField("type");
				type.setByte(mob, wispTypes[world.rand.nextInt(wispTypes.length)]);
			}
			catch (Exception e)
			{
				
			}
			
			// Success!
			spawnedFlag = world.spawnEntityInWorld(mob);
			if (this.aggosOnPlayer && player != null)
			{
				mob.setAttackTarget(player);
			}
		}
		
		return spawnedFlag;
	}
}
