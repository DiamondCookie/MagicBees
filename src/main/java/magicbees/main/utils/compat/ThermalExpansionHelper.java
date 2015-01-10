package magicbees.main.utils.compat;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import magicbees.item.types.DropType;
import magicbees.main.Config;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;

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
		FluidEnder,;
	}

	public enum NuggetType
	{
		ENDERIUM,
		ELECTRUM,
		BRONZE,
		INVAR,
		NICKEL,
		PLATINUM,;
	}

	public enum Entity
	{
		BLIZZ("Blizz"),;

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
		if (Loader.isModLoaded(Name) && Config.thermalExpansionActive)
		{
			isThermalExpansionPresent = true;
		}
	}

	public static void init()
	{
		if (isActive())
		{
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
		toSend.setTag("input", new NBTTagCompound());
		toSend.setTag("output", new NBTTagCompound());

		carbonDrop.writeToNBT(toSend.getCompoundTag("input"));
		Config.teFluidCoal.writeToNBT(toSend.getCompoundTag("output"));
		FMLInterModComms.sendMessage("ThermalExpansion", "CrucibleRecipe", toSend);

		//redstone drop to molten redstone

		ItemStack destabilizedDrop = Config.drops.getStackForType(DropType.DESTABILIZED);

		toSend = new NBTTagCompound();
		toSend.setInteger("energy", 4000);
		toSend.setTag("input", new NBTTagCompound());
		toSend.setTag("output", new NBTTagCompound());

		destabilizedDrop.writeToNBT(toSend.getCompoundTag("input"));
		Config.teFluidRedstone.writeToNBT(toSend.getCompoundTag("output"));
		FMLInterModComms.sendMessage("ThermalExpansion", "CrucibleRecipe", toSend);

		//endearing drop to molten ender

		ItemStack endearingDrop = Config.drops.getStackForType(DropType.ENDEARING);

		toSend = new NBTTagCompound();
		toSend.setInteger("energy", 4000);
		toSend.setTag("input", new NBTTagCompound());
		toSend.setTag("output", new NBTTagCompound());

		endearingDrop.writeToNBT(toSend.getCompoundTag("input"));
		Config.teFluidEnder.writeToNBT(toSend.getCompoundTag("output"));
		FMLInterModComms.sendMessage("ThermalExpansion", "CrucibleRecipe", toSend);

		//lux drop to molten glowstone

		ItemStack luxDrop = Config.drops.getStackForType(DropType.LUX);

		toSend = new NBTTagCompound();
		toSend.setInteger("energy", 4000);
		toSend.setTag("input", new NBTTagCompound());
		toSend.setTag("output", new NBTTagCompound());

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

	}

	private static void getItems()
	{


	}
}