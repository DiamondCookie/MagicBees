package thaumicbees.main;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.*;
import forestry.api.apiculture.EnumBeeType;
import java.io.PrintStream;
import java.lang.reflect.Field;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;
import thaumcraft.api.*;
import thaumicbees.main.bees.BeeSpecies;
import thaumicbees.main.bees.ThaumicBeesPlugin;

@Mod(modid="ThaumicBees", name="Thaumic Bees", version="0.0", dependencies="required-after:Forestry;required-after:Thaumcraft")
public class ThaumicBees
{

	@Mod.Instance(value="ThaumicBees")
	public static ThaumicBees object;
	private boolean ThaumcraftRecipesAdded;
	public static String FORESTRY_GFX_ITEMS;

	public ThaumicBees()
	{
		ThaumcraftRecipesAdded = false;
	}

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent fmlpreinitializationevent)
	{

	}

	@Mod.Init
	public void init(FMLInitializationEvent fmlinitializationevent)
	{

	}

	@Mod.PostInit
	public void postInit(FMLPostInitializationEvent e)
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@ForgeSubscribe
	public void worldLoad(net.minecraftforge.event.world.WorldEvent.Load e)
	{
		if (!ThaumcraftRecipesAdded)
		{
			ThaumicBeesPlugin.setupBeeInfusions(e.world);
			ThaumcraftRecipesAdded = true;
		}
	}
}
