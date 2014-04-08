package magicbees.main;

import magicbees.client.render.EffectJarRenderer;
import magicbees.tileentity.TileEntityEffectJar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{	@Override
	public void registerRenderers()
	{
		super.registerRenderers();
		
		RenderIdEffectJar = RenderingRegistry.getNextAvailableRenderId();
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Config.effectJar), EffectJarRenderer.instance);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEffectJar.class, EffectJarRenderer.instance);
	}
}
