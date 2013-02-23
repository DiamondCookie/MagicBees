package thaumicbees.item.types;

import cpw.mods.fml.common.registry.LanguageRegistry;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumicbees.main.utils.LocalizationManager;

public enum HiveFrameType
{
	MAGIC("magic", 32, 240, 1f, 1f, 1f, 2f, 2, 5,
			null),
	RESILIENT("resillient", 33, 840, 1f, 1f, 1f, 2f, 2, 5,
			new ObjectTags().add(EnumTag.ARMOR, 1)),
	GENTLE("gentle", 34, 200, 1f, 0.7f, 1.5f, 1.4f, 1, 5,
			new ObjectTags().add(EnumTag.HEAL, 1).add(EnumTag.LIFE, 1)),
	METABOLIC("metabolic", 35, 120, 1f, 1.8f, 1f, 1.2f, 5, 5,
			new ObjectTags().add(EnumTag.EXCHANGE, 1).add(EnumTag.MOTION, 1)),
	NECROTIC("necrotic", 36, 290, 1f, 1f, 0.3f, 0.75f, 1, 5,
			new ObjectTags().add(EnumTag.DEATH, 3).add(EnumTag.POISON, 2)),
	TEMPORAL("temporal", 37, 300, 1f, 1f, 2.5f, 1f, 1, 5,
			new ObjectTags().add(EnumTag.TIME, 5)),
	;
	
	private HiveFrameType(String name, int icon, int damage,
			float territory, float mutation, float lifespan, float production,
			int auraUse, int wearTicks, ObjectTags fluxTags)
	{
		this(name, icon, damage, territory, mutation, lifespan, production, 1f, false, false, false, false, auraUse, wearTicks, fluxTags);
	}
	
	private HiveFrameType(String name, int icon, int damage,
			float territory, float mutation, float lifespan, float production, float flowering,
			boolean sealed, boolean lit, boolean sunlit, boolean hellish,
			int auraUse, int wearTicks, ObjectTags fluxTags)
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
		this.wearTicksPerAura = wearTicks;
	}
	
	private String frameName;
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
	public int wearTicksPerAura;
	
	public String getName()
	{
		return LocalizationManager.getLocalizedString("tb.frame." + this.frameName);
	}
}
