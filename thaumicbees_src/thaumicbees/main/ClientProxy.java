package thaumicbees.main;

import java.lang.reflect.Field;

import thaumicbees.main.render.BeeRenderEffect;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{

	public ClientProxy() { }
	
	@Override
	public void preloadTextures()
	{
		// Preload the TCBees items file
		MinecraftForgeClient.preloadTexture(TCBEES_ITEMS_IMAGE);
		MinecraftForgeClient.preloadTexture(TCBEES_LIQUIDS_IMAGE);
		
		Field f;
		try
		{
			f = Class.forName("forestry.core.config.Config").getField("enableParticleFX");
			ThaumicBees.getConfig().DrawParticleEffects = f.getBoolean(null);
			
			f = Class.forName("forestry.core.config.Defaults").getField("TEXTURE_PARTICLES_BEE");
			thaumicbees.main.CommonProxy.FORESTRY_GFX_BEEEFFECTS = (String)f.get(null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void drawBeeEffects(World world, double xPos, double yPos, double zPos, int colour, int rangeX, int rangeY, int rangeZ)
	{
		// drawParticleEffects flag pulled from Forestry after configs
		if (ThaumicBees.getConfig().DrawParticleEffects)
		{
			double spawnX;
			double spawnY;
			double spawnZ;
			
			if (world.rand.nextBoolean())
			{
				spawnX = xPos + 0.5;
				spawnY = yPos + 0.75;
				spawnZ = zPos + 0.5;
			}
			else
			{
				spawnX = xPos + world.rand.nextInt(rangeX * 2) - rangeX;
				spawnY = yPos + world.rand.nextInt(rangeY);
				spawnZ = zPos + world.rand.nextInt(rangeZ * 2) - rangeZ;
			}
			
			this.getClientInstance().effectRenderer.addEffect(new BeeRenderEffect(world, spawnX, spawnY, spawnZ, 0f, 0f, 0f, colour));
		}
	}
}
