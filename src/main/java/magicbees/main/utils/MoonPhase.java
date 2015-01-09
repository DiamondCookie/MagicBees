package magicbees.main.utils;

import net.minecraft.world.World;

/**
 * Class to abstract the moon phase from the world time a bit.
 */
public enum MoonPhase
{
	FULL("full"),
	WANING_GIBBOUS("gibbousWaning"),
	WANING_HALF("halfWaning"),
	WANING_CRESCENT("crescentWaning"),
	NEW("new"),
	WAXING_CRESCENT("crecentWaxing"),
	WAXING_HALF("halfWaxing"),
	WAXING_GIBBOUS("gibbousWaxing");

	private String phaseName;

	private MoonPhase(String name)
	{
		this.phaseName = name;
	}

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

	public String getLocalizedName()
	{
		return LocalizationManager.getLocalizedString("moon." + this.phaseName);
	}

	public static MoonPhase getMoonPhase(World w)
	{
		return getMoonPhaseFromTime(w.getWorldTime());
	}

	public static MoonPhase getMoonPhaseFromTime(long time)
	{
		return MoonPhase.values()[(int)((time - 6000) / 24000L) % 8];
	}
}
