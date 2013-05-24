package magicbees.main.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

import thaumcraft.api.EnumTag;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchList;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.core.BlockInterface;
import forestry.api.core.ItemInterface;
import forestry.api.storage.BackpackManager;

import magicbees.bees.Allele;
import magicbees.bees.BeeGenomeManager;
import magicbees.block.types.PlankType;
import magicbees.item.ItemComb;
import magicbees.item.ItemMiscResources;
import magicbees.item.types.CombType;
import magicbees.item.types.DropType;
import magicbees.item.types.LiquidType;
import magicbees.item.types.PollenType;
import magicbees.item.types.PropolisType;
import magicbees.item.types.ResourceType;
import magicbees.item.types.WaxType;
import magicbees.main.CommonProxy;
import magicbees.main.Config;
import magicbees.main.ThaumicBees;
import magicbees.main.utils.compat.ExtraBeesHelper;
import magicbees.main.utils.compat.ForestryHelper;
import magicbees.main.utils.compat.ThaumcraftHelper;
import magicbees.main.utils.compat.ForestryHelper.CircuitBoard;
import magicbees.main.utils.compat.ForestryHelper.Comb;
import magicbees.main.utils.compat.ForestryHelper.CraftingMaterial;
import magicbees.main.utils.compat.ForestryHelper.Pollen;
import magicbees.main.utils.compat.ForestryHelper.Propolis;
import magicbees.main.utils.compat.ForestryHelper.Tube;
import magicbees.main.utils.compat.ThaumcraftHelper.MiscResource;
import magicbees.storage.BackpackDefinition;
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
