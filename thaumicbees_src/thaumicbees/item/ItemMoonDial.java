package thaumicbees.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumicbees.item.types.PollenType;
import thaumicbees.main.ThaumicBees;
import thaumicbees.main.utils.MoonPhase;
import thaumicbees.main.utils.TabThaumicBees;
import thaumicbees.main.utils.VersionInfo;
import thaumicbees.main.utils.compat.ForestryHelper;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Icon;

public class ItemMoonDial extends Item
{
	@SideOnly(Side.CLIENT)
	private Icon[] icons;
	
	public ItemMoonDial(int id)
	{
		super(id);
		this.setCreativeTab(TabThaumicBees.tabThaumicBees);
		this.setUnlocalizedName("moonDial");
	}

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
    	this.icons = new Icon[MoonPhase.values().length];
    	for (int i = 0; i < MoonPhase.values().length; ++i)
    	{
    		this.icons[i] = par1IconRegister.registerIcon(VersionInfo.ModName.toLowerCase() + ":moonDial." + i);
    	}
    	this.itemIcon = this.icons[0];
    }
	
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
    	par3List.add("\u00A77" 
    		+ MoonPhase.getMoonPhaseFromTime(MinecraftServer.getServer().worldServers[0].getWorldTime()).getLocalizedName());
    }
	
	@Override
    public Icon getIconFromDamage(int damage)
    {
        return this.icons[MoonPhase.getMoonPhaseFromTime(MinecraftServer.getServer().worldServers[0].getWorldTime()).ordinal()];
    }
}
