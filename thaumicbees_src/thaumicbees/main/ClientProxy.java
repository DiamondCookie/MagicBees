package thaumicbees.main;

import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import thaumicbees.client.render.EffectJarRenderer;
import thaumicbees.main.render.BeeRenderEffect;
import thaumicbees.tileentity.TileEntityEffectJar;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{

	public ClientProxy() { }
	
	@Override
	public void registerTileEntities()
	{
		super.registerTileEntities();
		
		RenderIdEffectJar = RenderingRegistry.getNextAvailableRenderId();
		MinecraftForgeClient.registerItemRenderer(Config.effectJar.blockID, EffectJarRenderer.instance);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEffectJar.class, EffectJarRenderer.instance);
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
