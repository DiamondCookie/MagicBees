package magicbees.main;

import magicbees.client.render.RendererEffectJar;
import magicbees.client.render.RendererVisAuraProvider;
import magicbees.tileentity.TileEntityEffectJar;
import magicbees.tileentity.TileEntityVisAuraProvider;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers()
	{
		super.registerRenderers();
		
		RenderIdEffectJar = RenderingRegistry.getNextAvailableRenderId();
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Config.effectJar), RendererEffectJar.instance);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEffectJar.class, RendererEffectJar.instance);
		
		RenderIdVisAuraProvider = RenderingRegistry.getNextAvailableRenderId();
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Config.visAuraProvider), RendererVisAuraProvider.instance);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityVisAuraProvider.class, RendererVisAuraProvider.instance);
	}
}
