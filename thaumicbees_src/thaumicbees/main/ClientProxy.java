package thaumicbees.main;

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
		MinecraftForgeClient.preloadTexture(TCBEES_ITEMSPNG);
	}
	
	@Override
	public void drawBeeEffects(World world, double xPos, double yPos, double zPos, int colour, int rangeX, int rangeY, int rangeZ)
	{
		// drawParticleEffects flag pulled from Forestry after configs
		if (ThaumicBees.object.configFlags.DrawParticleEffects)
		{
			double spawnX = xPos;
			double spawnY = yPos;
			double spawnZ = zPos;
			
			if (world.rand.nextBoolean())
			{
				spawnX += 0.5;
				spawnY += 0.5;
				spawnZ += 0.5;
			}
			else
			{
				spawnX += world.rand.nextInt(rangeX * 2) - rangeX;
				spawnY += world.rand.nextInt(rangeY);
				spawnZ += world.rand.nextInt(rangeZ * 2) - rangeY;
			}
			
			this.getClientInstance().effectRenderer.addEffect(new BeeRenderEffect(world, spawnX, spawnY, spawnZ, 0f, 0f, 0f, colour));
		}
	}
}
