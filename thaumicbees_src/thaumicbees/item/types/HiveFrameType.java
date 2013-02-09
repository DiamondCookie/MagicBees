package thaumicbees.item.types;

import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;

public enum HiveFrameType
{
	MAGIC("Magic Frame", 32, 240, 1f, 1f, 1f, 2f, 2,
			null),
	RESILIENT("Resilient Frame", 33, 840, 1f, 1f, 1f, 2f, 2,
			new ObjectTags().add(EnumTag.ARMOR, 1)),
	GENTLE("Gentle Frame", 34, 200, 1f, 0.7f, 1.5f, 1.4f, 1,
			new ObjectTags().add(EnumTag.HEAL, 1).add(EnumTag.LIFE, 1)),
	METABOLIC("Metabolic Frame", 35, 120, 1f, 1.8f, 1f, 1.2f, 5,
			new ObjectTags().add(EnumTag.EXCHANGE, 1).add(EnumTag.MOTION, 1)),
	NECROTIC("Necrotic Frame", 36, 290, 1f, 1f, 0.3f, 0.75f, 1,
			new ObjectTags().add(EnumTag.DEATH, 3).add(EnumTag.POISON, 2)),
	TEMPORAL("Temporal Frame", 37, 300, 1f, 1f, 2.5f, 1f, 1,
			new ObjectTags().add(EnumTag.TIME, 5)),
	;
	
	private HiveFrameType(String name, int icon, int damage,
			float territory, float mutation, float lifespan, float production,
			int auraUse, ObjectTags fluxTags)
	{
		this(name, icon, damage, territory, mutation, lifespan, production, 1f, false, false, false, false, auraUse, fluxTags);
	}
	
	private HiveFrameType(String name, int icon, int damage,
			float territory, float mutation, float lifespan, float production, float flowering,
			boolean sealed, boolean lit, boolean sunlit, boolean hellish,
			int auraUse, ObjectTags fluxTags)
	{
		this.frameName = name;
		this.iconIdx = icon;
		this.maxDamage = damage;
		
		this.territoryMod = territory;
		this.mutationMod = mutation;
		this.lifespanMod = lifespan;
		this.productionMod = production;
		this.floweringMod = flowering;
		this.isSealed = sealed;
		this.isLit = lit;
		this.isSunlit = sunlit;
		this.isHellish = hellish;
		
		this.auraPerUse = auraUse;
		this.flux = fluxTags;
	}
	
	public String frameName;
	public int iconIdx;
	public int maxDamage;
	
	public float territoryMod;
	public float mutationMod;
	public float lifespanMod;
	public float productionMod;
	public float floweringMod;
	public boolean isSealed;
	public boolean isLit;
	public boolean isSunlit;
	public boolean isHellish;
	
	public ObjectTags flux;
	public int auraPerUse;
}
