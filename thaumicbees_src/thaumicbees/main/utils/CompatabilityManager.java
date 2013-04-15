package thaumicbees.main.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

import thaumcraft.api.EnumTag;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchList;
import thaumicbees.bees.Allele;
import thaumicbees.bees.BeeGenomeManager;
import thaumicbees.item.ItemComb;
import thaumicbees.item.ItemMiscResources;
import thaumicbees.item.types.CombType;
import thaumicbees.item.types.DropType;
import thaumicbees.item.types.LiquidType;
import thaumicbees.item.types.PlankType;
import thaumicbees.item.types.PollenType;
import thaumicbees.item.types.PropolisType;
import thaumicbees.item.types.ResourceType;
import thaumicbees.item.types.WaxType;
import thaumicbees.main.CommonProxy;
import thaumicbees.main.Config;
import thaumicbees.main.ThaumicBees;
import thaumicbees.main.utils.compat.ExtraBeesHelper;
import thaumicbees.main.utils.compat.ForestryHelper;
import thaumicbees.main.utils.compat.ThaumcraftHelper;
import thaumicbees.main.utils.compat.ForestryHelper.CircuitBoard;
import thaumicbees.main.utils.compat.ForestryHelper.Comb;
import thaumicbees.main.utils.compat.ForestryHelper.CraftingMaterial;
import thaumicbees.main.utils.compat.ForestryHelper.Pollen;
import thaumicbees.main.utils.compat.ForestryHelper.Propolis;
import thaumicbees.main.utils.compat.ForestryHelper.Tube;
import thaumicbees.main.utils.compat.ThaumcraftHelper.MiscResource;
import thaumicbees.storage.BackpackDefinition;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.core.BlockInterface;
import forestry.api.core.ItemInterface;
import forestry.api.storage.BackpackManager;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Property;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.OreDictionary;

public class CompatabilityManager
{	
	public static void setupBackpacks()
	{
		ThaumcraftHelper.addItemsToBackpack();
	}
}
