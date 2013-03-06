package thaumicbees.main.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import thaumicbees.main.ThaumicBees;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * Using some of the VersionInfo code from cofh. (Thanks, Lemming!)
 * @author MysteriousAges
 *
 */
public class VersionInfo
{

	public static final String Version = "@VERSION@";
	public static final String Build = "@BUILD_NUMBER@";
	public static final String MCVersion = "@MCVERSION@";
	public static final String VersionURL = "http://bit.ly/thaumicbeeversion";

	public static final String Logo = "/gfx/thaumicbees/logo.png";

	public static final String Depends = "required-after:Forestry;required-after:Thaumcraft@[3.0.3,);after:ExtraBees";

	boolean criticalUpdate;
	boolean newVersion;
	boolean newMinecraftVersion;
	boolean versionCheckComplete;

	String latestModVersion;
	String latestMCVersion = MCVersion;
	String description = "";

	String modName;
	String modVersion;
	String releaseURL;
	Logger modLogger = FMLLog.getLogger();

	public static int[] parseVersion(String rawVersion)
	{
		ArrayList<Integer> versionTokens = new ArrayList<Integer>();
		String[] tokens = rawVersion.trim().split("\\.");

		for (int i = 0; i < tokens.length; ++i)
		{
			if (tokens[i].matches("[0-9]+[a-z]"))
			{
				String numberString = tokens[i].substring(0, tokens[i].length()-1);
				versionTokens.add(Integer.valueOf(numberString));
				versionTokens.add(Character.getNumericValue(tokens[i].charAt(tokens[i].length()-1)));
			}
			else
			{
				versionTokens.add(Integer.valueOf(tokens[i]));
			}
		}    	

		int[] value = new int[versionTokens.size()];
		for (int i = 0; i < value.length; ++i)
		{
			value[i] = versionTokens.get(i).intValue();
		}
		return value;
	}

	/* VERSION COMPARISON */
	public static boolean beforeTargetVersion(String version, String target)
	{
		boolean result = false;
		int[] versionTokens = parseVersion(version);
		int[] targetTokens = parseVersion(target);

		for (int i = 0; i < versionTokens.length; ++i)
		{
			if (versionTokens[i] < targetTokens[i])
			{
				result = true;
				break;
			}
			else if (versionTokens[i] > targetTokens[i]) 
			{
				result = false;
				break;
			}
		}

		return result;
	}

	public static boolean afterTargetVersion(String version, String target)
	{
		boolean result = false;
		int[] versionTokens = parseVersion(version);
		int[] targetTokens = parseVersion(target);

		for (int i = 0; i < versionTokens.length; ++i)
		{
			if (versionTokens[i] > targetTokens[i])
			{
				result = true;
				break;
			}
		}

		return result;
	}

	public VersionInfo(String name, String version, String url)
	{
		modName = name;
		modVersion = latestModVersion = version;
		releaseURL = url;
	}

	public VersionInfo(String name, String version, String url, Logger logger)
	{
		modName = name;
		modVersion = latestModVersion = version;
		releaseURL = url;
		modLogger = logger;
	}

	public void checkForNewVersion()
	{
		Thread versionCheckThread = new VersionCheckThread();
		versionCheckThread.start();
	}

	public String getCurrentVersion()
	{
		return modVersion;
	}

	public String getLatestVersion()
	{
		return latestModVersion;
	}

	public String getLatestMCVersion()
	{
		return latestMCVersion;
	}

	public String getVersionDescription()
	{
		return description;
	}

	public boolean isCriticalUpdate()
	{
		return criticalUpdate;
	}

	public boolean isNewVersionAvailable()
	{
		return newVersion;
	}

	public boolean isMinecraftOutdated()
	{
		return newMinecraftVersion;
	}

	public boolean isVersionCheckComplete()
	{
		return versionCheckComplete;
	}

	/* VERSION CHECK THREAD CLASS */
	private class VersionCheckThread extends Thread
	{
		@Override
		public void run()
		{

			try
			{
				String location = VersionURL;
				
				HttpURLConnection connection = null;
				
				// Used to "dereference" any location headers we may get.
				while (location != null && !location.isEmpty())
				{
					URL url = new URL(location);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestProperty("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.60 Safari/537.17");
					connection.connect();
					location = connection.getHeaderField("Location");
				}

				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				latestModVersion = reader.readLine();
				criticalUpdate = Boolean.parseBoolean(reader.readLine());
				latestMCVersion = reader.readLine();
				description = reader.readLine();
				reader.close();

				if (beforeTargetVersion(modVersion, latestModVersion))
				{
					modLogger.log(Level.INFO, "An updated version of " + modName + " is available: " + latestModVersion + ".");
					newVersion = true;
					if (criticalUpdate)
					{
						modLogger.log(Level.INFO, "This update has been marked as CRITICAL and will ignore notification suppression.");
					}
					if (beforeTargetVersion(MCVersion, latestMCVersion))
					{
						newMinecraftVersion = true;
						modLogger.log(Level.INFO, "This update is for Minecraft " + latestMCVersion + ".");
					}
				}

			}
			catch (Exception e)
			{
				modLogger.log(Level.WARNING, "Version Check Failed: " + e.getMessage());
			}
			versionCheckComplete = true;
		}
	}

	public static void doVersionCheck()
	{
		if (!ThaumicBees.getConfig().SkipUpdateCheck)
		{
			VersionInfo main = new VersionInfo("ThaumicBees", Version, VersionURL);
			TickHandlerVersion.registerModVersionInfo(main);
			TickHandlerVersion.initialize();
			TickRegistry.registerScheduledTickHandler(TickHandlerVersion.instance, Side.CLIENT);
			
			VersionCheckThread thread = main.new VersionCheckThread();
			thread.start();
		}
	}
}
