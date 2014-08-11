package magicbees.main.utils.compat;

public class ModHelper
{
    public static void preInit()
    {
        ForestryHelper.preInit();
        ExtraBeesHelper.preInit();
        ThaumcraftHelper.preInit();
        EquivalentExchangeHelper.preInit();
        ArsMagicaHelper.preInit();
        ThermalFoundationHelper.preInit();
        ThermalExpansionHelper.preInit();
        RedstoneArsenalHelper.preInit();
        BloodMagicHelper.preInit();
    }

    public static void init()
    {
        ForestryHelper.init();
        ExtraBeesHelper.init();
        ThaumcraftHelper.init();
        EquivalentExchangeHelper.init();
        ArsMagicaHelper.init();
        ThermalFoundationHelper.init();
        ThermalExpansionHelper.init();
        RedstoneArsenalHelper.init();
        BloodMagicHelper.init();
    }

    public static void postInit()
    {
        ForestryHelper.postInit();
        ExtraBeesHelper.postInit();
        ThaumcraftHelper.postInit();
        EquivalentExchangeHelper.postInit();
        ArsMagicaHelper.postInit();
        ThermalFoundationHelper.postInit();
        ThermalExpansionHelper.postInit();
        RedstoneArsenalHelper.postInit();
        BloodMagicHelper.postInit();
    }
}
