package thaumicbees.bees.genetics;

import java.util.List;

import thaumicbees.item.ItemArmorApiarist;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
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
	protected int chanceToSpawn;
	protected int maxMobsInArea;

	public AlleleEffectSpawnMob(String id, boolean isDominant, String identifier, String mobToSpawn)
	{
		super(id, isDominant, identifier);
		this.aggosOnPlayer = false;
		this.mobName = mobToSpawn;
		this.throttle = 200;
		this.chanceToSpawn = 100;
		this.maxMobsInArea = 6;
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
			
			int roll = w.rand.nextInt(100);
			
			if (roll <= this.chanceToSpawn)
			{
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
			else
			{
				storedData.setBoolean(0, true);
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
			double pos[] = this.randomMobSpawnCoords(world, bee, housing);			
				
			int entitiesCount = world.getEntitiesWithinAABB(mob.getClass(),
					AxisAlignedBB.getBoundingBox((int)pos[0], (int)pos[1], (int)pos[2], (int)pos[0] + 1, (int)pos[1] + 1, (int)pos[2] + 1)
							.expand(8.0D, 4.0D, 8.0D)).size();
			
				mob.setPosition(pos[0], pos[1], pos[2]);
				mob.setAngles(world.rand.nextFloat() * 360f, 0f);
				
			if (entitiesCount < this.maxMobsInArea && mob.getCanSpawnHere())
			{
				if (mob.getCanSpawnHere())
				{
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
	
	public AlleleEffectSpawnMob setChanceToSpawn(int value)
	{
		this.chanceToSpawn = value;
		
		return this;
	}
	
	public AlleleEffectSpawnMob setMaxMobsInSpawnZone(int value)
	{
		this.maxMobsInArea = value;
		
		return this;
	}

	protected double[] randomMobSpawnCoords(World world, IBeeGenome bee, IBeeHousing housing)
	{
		double pos[] = new double[3];
		pos[0] = housing.getXCoord() + (world.rand.nextDouble() * (bee.getTerritory()[0] * housing.getTerritoryModifier(bee)) - bee.getTerritory()[0] / 2);
		pos[1] = housing.getYCoord() + world.rand.nextInt(3) - 1;
		pos[2] = housing.getZCoord() + (world.rand.nextDouble() * (bee.getTerritory()[2] * housing.getTerritoryModifier(bee)) - bee.getTerritory()[2] / 2);
		return pos;
	}
}
