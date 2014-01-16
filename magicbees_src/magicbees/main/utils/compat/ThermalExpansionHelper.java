package magicbees.main.utils.compat;

import java.util.ArrayList;

import magicbees.item.types.PollenType;
import magicbees.item.types.PropolisType;
import magicbees.item.types.ResourceType;
import magicbees.item.types.WaxType;
import magicbees.item.types.DropType;
import magicbees.main.CommonProxy;
import magicbees.main.Config;
import magicbees.main.MagicBees;
import magicbees.main.utils.LocalizationManager;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.core.ItemInterface;
import net.minecraftforge.fluids.*;

public class ThermalExpansionHelper
{
	public enum MiscResource
	{
		EnderiumBlock,
		ElectrumBlock,
		BronzeBlock,
		FluidCoal,
		FluidRedstone,
		FluidGlowstone,
		FluidEnder,	
		;
	}
	
	public enum NuggetType
	{
		ENDERIUM,
		ELECTRUM,
		BRONZE,		
		INVAR,
		NICKEL,
		PLATINUM,
		;
	}
	
	public enum Entity
	{
		BLIZZ("Blizz"),
		;
		
		public final String entityID;
		private Entity(String s)
		{
			this.entityID = s;
		}
	}

	public static final String Name = "ThermalExpansion";
	private static boolean isThermalExpansionPresent = false;
	
	public static boolean isActive()
	{
		return isThermalExpansionPresent;
	}
	
	public static void preInit()
	{
		if (cpw.mods.fml.common.Loader.isModLoaded(Name))
		{
			isThermalExpansionPresent = true;
		}
	}

	public static void init()
	{
		if (isActive()) {
			getFluids();
			setupCrafting();
		}
	}
	
	private static void getFluids()
	{
		Config.teFluidGlowstone = FluidRegistry.getFluidStack("glowstone", 50);
		Config.teFluidCoal = FluidRegistry.getFluidStack("coal", 50);
		Config.teFluidRedstone = FluidRegistry.getFluidStack("redstone", 50);
		Config.teFluidEnder = FluidRegistry.getFluidStack("ender", 50);	
	}
	private static void setupCrafting()
	{
		//crucible recipes
		// carbon to liquid coal
		ItemStack carbonDrop = Config.drops.getStackForType(DropType.CARBON);
				
		NBTTagCompound toSend = new NBTTagCompound();
		toSend.setInteger("energy", 4000);
		toSend.setCompoundTag("input", new NBTTagCompound());
		toSend.setCompoundTag("output", new NBTTagCompound());

		carbonDrop.writeToNBT(toSend.getCompoundTag("input"));
		Config.teFluidCoal.writeToNBT(toSend.getCompoundTag("output"));
		FMLInterModComms.sendMessage("ThermalExpansion", "CrucibleRecipe", toSend);
		
		//redstone drop to molten redstone
		
		ItemStack destabilizedDrop = Config.drops.getStackForType(DropType.DESTABILIZED);
		
		toSend = new NBTTagCompound();
		toSend.setInteger("energy", 4000);
		toSend.setCompoundTag("input", new NBTTagCompound());
		toSend.setCompoundTag("output", new NBTTagCompound());

		destabilizedDrop.writeToNBT(toSend.getCompoundTag("input"));
		Config.teFluidRedstone.writeToNBT(toSend.getCompoundTag("output"));
		FMLInterModComms.sendMessage("ThermalExpansion", "CrucibleRecipe", toSend);
		
		//endearing drop to molten ender
		
        ItemStack endearingDrop = Config.drops.getStackForType(DropType.ENDEARING);
		
		toSend = new NBTTagCompound();
		toSend.setInteger("energy", 4000);
		toSend.setCompoundTag("input", new NBTTagCompound());
		toSend.setCompoundTag("output", new NBTTagCompound());

		endearingDrop.writeToNBT(toSend.getCompoundTag("input"));
		Config.teFluidEnder.writeToNBT(toSend.getCompoundTag("output"));
		FMLInterModComms.sendMessage("ThermalExpansion", "CrucibleRecipe", toSend);
		
		//lux drop to molten glowstone
		
        ItemStack luxDrop = Config.drops.getStackForType(DropType.LUX);
		
		toSend = new NBTTagCompound();
		toSend.setInteger("energy", 4000);
		toSend.setCompoundTag("input", new NBTTagCompound());
		toSend.setCompoundTag("output", new NBTTagCompound());

		luxDrop.writeToNBT(toSend.getCompoundTag("input"));
		Config.teFluidGlowstone.writeToNBT(toSend.getCompoundTag("output"));
		FMLInterModComms.sendMessage("ThermalExpansion", "CrucibleRecipe", toSend);
	}
	
	public static void postInit()
	{
		if (isActive())
		{
			// Apparently the Game Registry isn't populated until now. ):
			getBlocks();
			getItems();
		}
	}

	private static void getBlocks()
	{
		Config.teEnderiumBlock = GameRegistry.findItemStack("ThermalExpansion", "blockEnderium", 1);
		Config.teElectrumBlock = GameRegistry.findItemStack("ThermalExpansion", "blockElectrum", 1);
		Config.teInvarBlock = GameRegistry.findItemStack("ThermalExpansion", "blockInvar", 1);
		Config.teNickelBlock = GameRegistry.findItemStack("ThermalExpansion", "blockNickel", 1);
		Config.tePlatinumBlock = GameRegistry.findItemStack("ThermalExpansion", "blockPlatinum", 1);
		Config.teBronzeBlock = GameRegistry.findItemStack("ThermalExpansion", "blockBronze", 1);		
	}
	
	private static void getItems()
	{		
		Config.teEnderiumNugget = GameRegistry.findItemStack("ThermalExpansion", "nuggetEnderium", 1);
		Config.teInvarNugget = GameRegistry.findItemStack("ThermalExpansion", "nuggetInvar", 1);
		Config.teElectrumNugget = GameRegistry.findItemStack("ThermalExpansion", "nuggetElectrum", 1);		
		Config.teNickelNugget = GameRegistry.findItemStack("ThermalExpansion", "nuggetNickel", 1);
		Config.tePlatinumNugget = GameRegistry.findItemStack("ThermalExpansion", "nuggetPlatinum", 1);
		Config.teDustCryotheum = GameRegistry.findItemStack("ThermalExpansion", "dustCryotheum", 1);
		Config.teDustBlizz = GameRegistry.findItemStack("ThermalExpansion", "dustBlizz", 1);
		Config.teDustPyrotheum = GameRegistry.findItemStack("ThermalExpansion", "dustPyrotheum", 1);
		Config.teDustSulfur = GameRegistry.findItemStack("ThermalExpansion", "dustSulfur", 1);
		Config.teDustPlatinum = GameRegistry.findItemStack("ThermalExpansion", "dustPlatinum", 1);
		
	}
}