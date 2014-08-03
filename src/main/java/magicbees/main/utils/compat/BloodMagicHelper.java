package magicbees.main.utils.compat;

import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipe;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import forestry.api.apiculture.EnumBeeType;
import magicbees.bees.BeeSpecies;
import magicbees.main.Config;
import magicbees.main.utils.BlockInterface;
import magicbees.main.utils.ItemInterface;
import magicbees.main.utils.LogHelper;
import net.minecraft.item.ItemStack;

public class BloodMagicHelper {

    private static boolean isBloodMagicActive = false;
    public static final String Name = "AWWayofTime";

    public static boolean isActive()
    {
        return isBloodMagicActive;
    }

    public static void preInit()
    {
        if (Loader.isModLoaded(Name))
        {
            isBloodMagicActive = true;
        }
    }

    public static void init()
    {
        if (isActive())
        {
            getBlocks();
            getItems();
        }
    }

    public static void postInit(){}

    public static void getBlocks()
    {
        Config.bmBloodStoneBrick = BlockInterface.getBlock(Name, "bloodStoneBrick");
    }

    public static void getItems()
    {
        Config.bmIncendium = ItemInterface.getItemStack(Name, "incendium");
        Config.bmMagicales = ItemInterface.getItemStack(Name, "magicales");
        Config.bmSanctus = ItemInterface.getItemStack(Name, "sanctus");
        Config.bmAether = ItemInterface.getItemStack(Name, "aether");
        Config.bmCrepitous = ItemInterface.getItemStack(Name, "crepitous");
        Config.bmCrystallos = ItemInterface.getItemStack(Name, "crystallos");
        Config.bmTerrae = ItemInterface.getItemStack(Name, "terrae");
        Config.bmAquasalus = ItemInterface.getItemStack(Name, "aquasalus");
        Config.bmTennebrae = ItemInterface.getItemStack(Name, "tennebrae");
    }

    public static void addAltarRecipe(ItemStack requiredItem, ItemStack result, int minTier, int liquidRequired, int consumptionRate, int drainRate, boolean canBeFilled)
    {
        AltarRecipeRegistry.registerAltarRecipe(result, requiredItem, minTier, liquidRequired, consumptionRate, drainRate, canBeFilled);
    }

    public static void addAltarRecipeBee(BeeSpecies inputBee, BeeSpecies outputBee, int minTier, int liquidRequired, int consumptionRate, int drainRate)
    {
        addAltarRecipe(inputBee.getBeeItem(EnumBeeType.DRONE), outputBee.getBeeItem(EnumBeeType.DRONE), minTier, liquidRequired, consumptionRate, drainRate, false);
        addAltarRecipe(inputBee.getBeeItem(EnumBeeType.PRINCESS), outputBee.getBeeItem(EnumBeeType.PRINCESS), minTier, liquidRequired, consumptionRate, drainRate, false);
        addAltarRecipe(inputBee.getBeeItem(EnumBeeType.QUEEN), outputBee.getBeeItem(EnumBeeType.QUEEN), minTier, liquidRequired, consumptionRate, drainRate, false);
    }

    public static void addBindingRecipe(ItemStack input, ItemStack output)
    {
        BindingRegistry.registerRecipe(output, input);
    }

    public static void addBindingRecipeBee(BeeSpecies inputBee, BeeSpecies outputBee)
    {
        addBindingRecipe(inputBee.getBeeItem(EnumBeeType.DRONE), outputBee.getBeeItem(EnumBeeType.DRONE));
        addBindingRecipe(inputBee.getBeeItem(EnumBeeType.PRINCESS), outputBee.getBeeItem(EnumBeeType.PRINCESS));
        addBindingRecipe(inputBee.getBeeItem(EnumBeeType.QUEEN), outputBee.getBeeItem(EnumBeeType.QUEEN));
    }

}
