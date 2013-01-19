package thaumicbees.compat;

public enum TCEntity
{
	BRAINY_ZOMBIE("entBrainyZombie"),
	GIANT_BRAINY_ZOMBIE("entGiantBrainyZombie"),
	WISP("entWisp"),
	FIREBAT("entFirebat"),
	;
	
	public String entityID;
	
	private TCEntity(String id)
	{
		this.entityID = id;
	}
}
