package thaumicbees.bees;

import net.minecraft.world.World;

/**
 * Class to abstract the moon phase from the world time a bit.
 */
public enum MoonPhase
{
	FULL,
	WANING_GIBBOUS,
	WANING_HALF,
	WANING_CRESCENT,
	NEW,
	WAXING_CRESCENT,
	WAXING_HALF,
	WAXING_GIBBOUS;
	
	public boolean isBetween(MoonPhase first, MoonPhase second)
	{
		boolean flag = false;
		
		if (first.ordinal() <= second.ordinal())
		{
			// Straightforward.
			flag = first.ordinal() <= this.ordinal() && this.ordinal() <= second.ordinal();
		}
		else
		{
			// Wraps around the boundary.
			flag = (first.ordinal() <= this.ordinal() && this.ordinal() <= WAXING_GIBBOUS.ordinal()) ||
					(FULL.ordinal() <= this.ordinal() && this.ordinal() <= second.ordinal());
		}
		
		return flag;
	}
	
	public static MoonPhase getMoonPhase(World w)
	{
		return MoonPhase.values()[(int)(w.getWorldTime() / 24000L) % 8];
	}
}
