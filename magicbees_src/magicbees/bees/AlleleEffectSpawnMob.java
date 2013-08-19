package magicbees.bees;

import java.util.List;


import magicbees.item.ItemArmorApiarist;
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
	protected String mobSound;
	protected int chanceToSpawn;
	protected int maxMobsInArea;

	public AlleleEffectSpawnMob(String id, boolean isDominant, String mobToSpawn)
	{
		this(id, isDominant, mobToSpawn, null);
	}
	
	public AlleleEffectSpawnMob(String id, boolean isDominant, String mobToSpawn, String mobSoundFx)
	{
		super(id, isDominant, 200);
		this.aggosOnPlayer = false;
		this.mobName = mobToSpawn;
		this.chanceToSpawn = 100;
		this.maxMobsInArea = 6;
		this.mobSound = mobSoundFx;
	}

	@Override
	public IEffectData validateStorage(IEffectData storedData)
	{
		if (storedData == null || !(storedData instanceof magicbees.bees.EffectData))
		{
			// Throttle; Ready to spawn
			storedData = new EffectData(1, 1, 0);
		}
		return storedData;
	}

	@Override
	public IEffectData doEffectThrottled(IBeeGenome genome, IEffectData storedData, IBeeHousing housing)
	{
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
				if (this.mobSound != null && w.rand.nextInt(100) < 35)
				{
					int range = genome.getTerritory()[0];
					double x = housing.getXCoord() + w.rand.nextDouble() * (range * 2) - range;
					range = genome.getTerritory()[1];
					double y = housing.getYCoord() + w.rand.nextDouble() * (range * 2) - range;
					range = genome.getTerritory()[2];
					double z = housing.getZCoord() + w.rand.nextDouble() * (range * 2) - range;
					w.playSoundEffect(x, y, z, this.mobSound, 0.5f,
							(w.rand.nextFloat() - w.rand.nextFloat()) * 0.2f + 1.0f);
				}
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
			
				mob.setPositionAndRotation(pos[0], pos[1], pos[2], world.rand.nextFloat() * 360f, 0f);
				
			if (entitiesCount < this.maxMobsInArea && mob.getCanSpawnHere())
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
		pos[0] = housing.getXCoord() + (world.rand.nextDouble() * (bee.getTerritory()[0] * housing.getTerritoryModifier(bee, 1f)) - bee.getTerritory()[0] / 2);
		pos[1] = housing.getYCoord() + world.rand.nextInt(3) - 1;
		pos[2] = housing.getZCoord() + (world.rand.nextDouble() * (bee.getTerritory()[2] * housing.getTerritoryModifier(bee, 1f)) - bee.getTerritory()[2] / 2);
		return pos;
	}
}
