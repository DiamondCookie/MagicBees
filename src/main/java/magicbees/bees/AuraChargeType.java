package magicbees.bees;

import magicbees.api.bees.IAuraChargeType;
import thaumcraft.api.aspects.Aspect;

public enum AuraChargeType implements IAuraChargeType {
	MUTATION(Aspect.WATER, 850, 11),
	DEATH(Aspect.ENTROPY, 400, 5),
	PRODUCTION(Aspect.AIR, 300, 3);

	public final int duration;
	public final Aspect aspect;
	/** The number of ticks to count between draining vis from the environment **/
	public final int tickRate;
	public final int flag = 1 << ordinal();

	AuraChargeType(Aspect aspect, int duration, int tickRate) {
		this.aspect = aspect;
		this.duration = duration;
		this.tickRate = tickRate;
	}
}
