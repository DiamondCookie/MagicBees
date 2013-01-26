package thaumicbees.thaumcraft;

public enum TCEntity
{
	BRAINY_ZOMBIE("entBrainyZombie", "EntityBrainyZombie"),
	GIANT_BRAINY_ZOMBIE("entGiantBrainyZombie", "EntityGiantBrainyZombie"),
	WISP("entWisp", "EntityWisp"),
	FIREBAT("entFirebat", "EntityFireBat"),
	;
	
	private static String packageName = "thaumcraft.common.entities.monster.";
	
	public String entityID;
	private String className;
	
	private TCEntity(String id, String clazz)
	{
		this.entityID = id;
		this.className = clazz;
	}
	
	public String getClassName()
	{
		return packageName + this.className;
	}
}
