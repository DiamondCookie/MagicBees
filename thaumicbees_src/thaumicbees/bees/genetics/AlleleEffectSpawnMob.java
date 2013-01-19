package thaumicbees.bees.genetics;

import java.util.List;

import thaumicbees.item.ItemArmorApiarist;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IEffectData;

public class AlleleEffectSpawnMob extends AlleleEffect
{
	protected boolean aggosOnPlayer;
	protected boolean spawnsWhilePlayerNear;
	protected String alternateMob;
	protected String mobName;
	protected int throttle;

	public AlleleEffectSpawnMob(String id, boolean isDominant, String identifier, String mobToSpawn)
	{
		super(id, isDominant, identifier);
		this.aggosOnPlayer = false;
		this.mobName = mobToSpawn;
		this.throttle = 200;
	}

	@Override
	public IEffectData validateStorage(IEffectData storedData)
	{
		if (storedData == null)
		{
			// Throttle; Ready to spawn
			storedData = new EffectData(1, 1, 0);
		}
		return storedData;
	}

	@Override
	public IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing)
	{
		int count = storedData.getInteger(0);
		
		if (count >= this.throttle && !storedData.getBoolean(0))
		{
			storedData.setBoolean(0, true);
			storedData.setInteger(0, 0);
		}
		else
		{
			storedData.setInteger(0, count + 1);
		}
		
		// Check if we're ready to spawn the mob.
		if (storedData.getBoolean(0))
		{
			World w = housing.getWorld();
			
			if (this.spawnsWhilePlayerNear)
			{
				List<Entity> entities = this.getEntitiesWithinRange(genome, housing);
				EntityPlayer target = null;
				for (Entity e : entities)
				{
					if (e instanceof EntityPlayer)
					{
						target = (EntityPlayer)e;
						// Check for wearing armor & cancel
						if (ItemArmorApiarist.getNumberPiecesWorn(target) >= 4)
						{
							// Full armor suit is treated as "invisible."
							target = null;
						}
						else
						{
							break;
						}
					}
				}
				
				// Let's spawn a mob. =D
				if (target != null)
				{
					storedData.setBoolean(0, !this.spawnMob(genome, target, w, housing, false));
				}
				else if (this.alternateMob != null)
				{
					storedData.setBoolean(0, !this.spawnMob(genome, null, w, housing, true));
				}
			}
			else
			{
				storedData.setBoolean(0, !this.spawnMob(genome, null, w, housing, true));
			}
		}
		
		return storedData;
	}
	
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
			// Success!
			spawnedFlag = world.spawnEntityInWorld(mob);
			if (this.aggosOnPlayer && player != null)
			{
				if (ItemArmorApiarist.getNumberPiecesWorn(player) < 4)
				{
					// Protect fully suited player from initial murder intent.
					mob.setAttackTarget(player);
				}
			}
		}
		
		return spawnedFlag;
	}
	
	public AlleleEffectSpawnMob setSpawnsOnPlayerNear(String alternateMobToSpawn)
	{
		this.spawnsWhilePlayerNear = true;
		this.alternateMob = alternateMobToSpawn;
		
		return this;
	}
	
	public AlleleEffectSpawnMob setAggrosPlayerOnSpawn()
	{
		this.aggosOnPlayer = true;
		
		return this;
	}

	public AlleleEffectSpawnMob setThrottle(int val)
	{
		this.throttle = val;
		
		return this;
	}
}
