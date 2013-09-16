package magicbees.bees;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import magicbees.item.types.CombType;
import magicbees.item.types.DropType;
import magicbees.item.types.NuggetType;
import magicbees.item.types.PollenType;
import magicbees.item.types.ResourceType;
import magicbees.main.Config;
import magicbees.main.utils.LocalizationManager;
import magicbees.main.utils.VersionInfo;
import magicbees.main.utils.compat.ArsMagicaHelper;
import magicbees.main.utils.compat.EquivalentExchangeHelper;
import magicbees.main.utils.compat.ForestryHelper;
import magicbees.main.utils.compat.ThaumcraftHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IIconProvider;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;

public enum BeeSpecies implements IAlleleBeeSpecies, IIconProvider
{
	MYSTICAL("Mystical", "mysticum",
			BeeClassification.VEILED, 0xAFFFB7, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false, true),
	SORCEROUS("Sorcerous", "fascinatio",
			BeeClassification.VEILED, 0xEA9A9A, EnumTemperature.HOT, EnumHumidity.ARID, false, false, true),
	UNUSUAL("Unusual", "inusitatus",
			BeeClassification.VEILED, 0x72D361, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false, true),
	ATTUNED("Attuned", "similis",
			BeeClassification.VEILED, 0x0086A8, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false, true),
	
	ELDRITCH("Eldritch", "prodigiosus",
			BeeClassification.VEILED, 0x8D75A0, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	
	ESOTERIC("Esoteric", "secretiore",
			BeeClassification.ARCANE, 0x001099, 0xFF9D60, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	MYSTERIOUS("Mysterious", "mysticus",
			BeeClassification.ARCANE, 0x762bc2, 0xFF9D60, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	ARCANE("Arcane", "arcanus",
			BeeClassification.ARCANE, 0xd242df, 0xFF9D60, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, true),
			
	CHARMED("Charmed", "larvatus",
			BeeClassification.SUPERNATURAL, 0x48EEEC, 0xFF9D60, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	ENCHANTED("Enchanted", "cantatus",
			BeeClassification.SUPERNATURAL, 0x18e726, 0xFF9D60, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	SUPERNATURAL("Supernatural", "coeleste",
			BeeClassification.SUPERNATURAL, 0x005614, 0xFF9D60, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, true),
			
	ETHEREAL("Ethereal", "diaphanum",
			BeeClassification.MAGICAL, 0xBA3B3B, 0xEFF8FF, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
			
	WATERY("Watery", "aquatilis",
			BeeClassification.MAGICAL, 0x313C5E, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false ,true),
	EARTHY("Earthen", "fictili",
			BeeClassification.MAGICAL, 0x78822D, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false ,true),
	FIREY("Firey", "ardens",
			BeeClassification.MAGICAL, 0xD35119, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false ,true),
	WINDY("Windy", "ventosum",
			BeeClassification.MAGICAL, 0xFFFDBA, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false ,true),
			
	PUPIL("Pupil", "disciplina",
				BeeClassification.SCHOLARLY, 0xFFFF00, EnumTemperature.NORMAL, EnumHumidity.ARID, false, true),
	SCHOLARLY("Scholarly", "studiosis",
			BeeClassification.SCHOLARLY, 0x6E0000, EnumTemperature.NORMAL, EnumHumidity.ARID, false, false),
	SAVANT("Savant", "philologus",
			BeeClassification.SCHOLARLY, 0xFFA042, EnumTemperature.NORMAL, EnumHumidity.ARID, true, false),
				
	AWARE("Aware", "sensibilis",
			BeeClassification.MAGICAL, 0x5E95B5, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),			
	SPIRIT("Spirit", "larva",
			BeeClassification.SOUL, 0xb2964b, EnumTemperature.WARM, EnumHumidity.NORMAL, false, true),
	SOUL("Soul", "anima",
			BeeClassification.SOUL, 0x7d591b, EnumTemperature.HELLISH, EnumHumidity.NORMAL, true, false),

	SKULKING("Skulking", "malevolens",
			BeeClassification.SKULKING, 0x524827, 0xe15236, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	GHASTLY("Ghastly", "pallens",
			BeeClassification.SKULKING, 0xccccee, 0xbf877c, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	SPIDERY("Spidery", "araneolus",
			BeeClassification.SKULKING, 0x0888888, 0x222222, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	SMOULDERING("Smouldering", "flagrantia",
			BeeClassification.SKULKING, 0xFFC747, 0xEA8344, EnumTemperature.HELLISH, EnumHumidity.NORMAL, false, false),
			
	TIMELY("Timely", "gallifreis",
			BeeClassification.TIME, 0xC6AF86, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	LORDLY("Lordly", "rassilonis",
			BeeClassification.TIME, 0xC6AF86, 0x8E0213, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	DOCTORAL("Doctoral", "medicus qui",
			BeeClassification.TIME, 0xDDE5FC, 0x4B6E8C, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, false),

	INFERNAL("Infernal", "infernales",
			BeeClassification.ABOMINABLE, 0xFF1C1C, 0x960F00, EnumTemperature.HELLISH, EnumHumidity.ARID, false, true),
	HATEFUL("Hateful", "odibilis",
			BeeClassification.ABOMINABLE, 0xDB00DB, 0x960F00, EnumTemperature.HELLISH, EnumHumidity.ARID, false, false),
	SPITEFUL("Spiteful", "maligna",
			BeeClassification.ABOMINABLE, 0x5FCC00, 0x960F00, EnumTemperature.HELLISH, EnumHumidity.ARID, true, false),
	WITHERING("Withering", "vietus",
			BeeClassification.ABOMINABLE, 0x5B5B5B, 0x960F00, EnumTemperature.HELLISH, EnumHumidity.ARID, true, false),

	OBLIVION("Oblivion", "oblivioni",
			BeeClassification.EXTRINSIC, 0xD5C3E5, 0xF696FF, EnumTemperature.COLD, EnumHumidity.NORMAL, false, false),
	NAMELESS("Nameless", "sine nomine",
			BeeClassification.EXTRINSIC, 0x8ca7cb, 0xF696FF, EnumTemperature.COLD, EnumHumidity.NORMAL, false, true),
	ABANDONED("Abandoned", "reliquit",
			BeeClassification.EXTRINSIC, 0xc5cb8c, 0xF696FF, EnumTemperature.COLD, EnumHumidity.NORMAL, false, true),
	FORLORN("Forlorn", "perditus",
			BeeClassification.EXTRINSIC, 0xcba88c, 0xF696FF, EnumTemperature.COLD, EnumHumidity.NORMAL, true, false),
	DRACONIC("Draconic", "draconic",
			BeeClassification.EXTRINSIC, 0x9f56ad, 0x5a3b62, EnumTemperature.COLD, EnumHumidity.NORMAL, true, false),
			
	IRON("Iron", "ferrus",
			BeeClassification.METALLIC, 0x686868, 0xE9E9E9, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	GOLD("Gold", "aurum",
			BeeClassification.METALLIC, 0x684B01, 0xFFFF0B, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, false),
	COPPER("Copper", "aercus",
			BeeClassification.METALLIC, 0x684B01, 0xFFC81A, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	TIN("Tin", "stannum",
			BeeClassification.METALLIC, 0x3E596D, 0xA6BACB, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	SILVER("Silver", "argenteus",
			BeeClassification.METALLIC, 0x747C81, 0x96BFC4, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	LEAD("Lead", "plumbeus",
			BeeClassification.METALLIC, 0x96BFC4, 0x91A9F3, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	ALUMINUM("Aluminum", "aluminium",
			BeeClassification.METALLIC, 0xEDEDED, 0x767676, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	ARDITE("Ardite", "aurantiaco",
			BeeClassification.METALLIC, 0x720000, 0xFF9E00, EnumTemperature.HOT, EnumHumidity.ARID, false, false),
	COBALT("Cobalt", "caeruleo",
			BeeClassification.METALLIC, 0x03265F, 0x59AAEF, EnumTemperature.HOT, EnumHumidity.ARID, false, false),
	MANYULLYN("Manyullyn", "manahmanah",
			BeeClassification.METALLIC, 0x481D6D, 0xBD92F1, EnumTemperature.HOT, EnumHumidity.ARID, true, false),
			
	DIAMOND("Diamond", "diamond",
			BeeClassification.GEM, 0x209581, 0x8DF5E3, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, false),
	EMERALD("Emerald", "prasinus",
			BeeClassification.GEM, 0x005300, 0x17DD62, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, false),
	APATITE("Apatite", "apatite",
			BeeClassification.GEM, 0x2EA7EC, 0x001D51, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
			
	MUTABLE("Mutable", "mutable",
			BeeClassification.TRANSMUTING, 0xDBB24C, 0xE0D5A6, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	TRANSMUTING("Transmuting", "transmuting",
			BeeClassification.TRANSMUTING, 0xDBB24C, 0xA2D2D8, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	CRUMBLING("Crumbling", "crumbling", 
			BeeClassification.TRANSMUTING, 0xDBB24C, 0xDBA4A4, EnumTemperature.HOT, EnumHumidity.ARID, false, false),

	// --------- Thaumcraft Bees ---------------------------------------------------------------------------------------
	TC_AIR("TCAir", "aether",
			BeeClassification.THAUMIC, 0xD9D636, 0x999999, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, true),
	TC_FIRE("TCFire", "praefervidus",
			BeeClassification.THAUMIC, 0xE50B0B, 0x999999, EnumTemperature.HOT, EnumHumidity.ARID, true, true),
	TC_WATER("TCWater", "umidus",
			BeeClassification.THAUMIC, 0x36CFD9, 0x999999, EnumTemperature.NORMAL, EnumHumidity.DAMP, true, true),
	TC_EARTH("TCEarth", "sordida",
			BeeClassification.THAUMIC, 0x005100, 0x999999, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, true),
	TC_ORDER("TCOrder", "ordinatus",
			BeeClassification.THAUMIC, 0xaa32fc, 0x999999, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, true),
	TC_CHAOS("TCChaos", "tenebrarum",
			BeeClassification.THAUMIC, 0xCCCCCC, 0x999999, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, false),
			
	TC_VIS("TCVis", "arcanus saecula",
			BeeClassification.THAUMIC, 0x004c99, 0x675ED1, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	TC_FLUX("TCFlux", "arcanus labe",
			BeeClassification.THAUMIC, 0x91376A, 0x675ED1, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	TC_ATTRACT("TCAttractive", "tractus",
			BeeClassification.THAUMIC, 0x96FFBC, 0x675ED1, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	TC_REJUVENATING("TCRejuvenating", "arcanus vitae",
			BeeClassification.THAUMIC, 0x91D0D9, 0x675ED1, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	TC_PURE("TCPure", "arcanus puritatem",
			BeeClassification.THAUMIC, 0xb0092e, 0x675ED1, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
			
	TC_BRAINY("TCBrainy", "cerebrum",
			BeeClassification.THAUMIC, 0x83FF70, 0xe15236, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	TC_WISPY("TCWispy", "umbrabilis",
			BeeClassification.THAUMIC, 0x9cb8d5, 0xe15236, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	TC_BATTY("TCBatty", "chiroptera",
			BeeClassification.THAUMIC, 0x27350d, 0xe15236, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
			
	TC_CHICKEN("TCChicken", "pullus",
			BeeClassification.FLESHY, 0x7D431E, 0xE0905E, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	TC_BEEF("TCBeef", "bubulae",
			BeeClassification.FLESHY, 0x40221A, 0xAC6753, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	TC_PORK("TCPork", "porcina",
			BeeClassification.FLESHY, 0x725D2F, 0xD2BF93, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),

	// --------- Equivalent Exchange Bees -----------------------------------------------------------------------------	
	EE_MINIUM("EEMinium", "mutabilis",
			BeeClassification.ALCHEMICAL, 0xac0921, 0x3a030b, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
			
	// --------- Ars Magica Bees --------------------------------------------------------------------------------------	
	AM_ESSENCE("AMEssence", "essentia",
			BeeClassification.ESSENTIAL, 0x86BBC5, 0xE3A55B, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	AM_QUINTESSENCE("AMQuintessence", "cor essentia",
			BeeClassification.ESSENTIAL, 0xE3A45B, 0xE3A55B, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, true),
			
	AM_EARTH("AMEarth", "magica terra",
			BeeClassification.ESSENTIAL, 0xAA875E, 0xE3A55B, EnumTemperature.WARM, EnumHumidity.ARID, false, false),
	AM_AIR("AMAir", "magica aer",
			BeeClassification.ESSENTIAL, 0xD5EB9D, 0xE3A55B, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	AM_FIRE("AMFire", "magica ignis",
			BeeClassification.ESSENTIAL, 0x93451E, 0xE3A55B, EnumTemperature.HOT, EnumHumidity.ARID, false, false),
	AM_WATER("AMWater", "magica aqua",
			BeeClassification.ESSENTIAL, 0x3B7D8C, 0xE3A55B, EnumTemperature.NORMAL, EnumHumidity.DAMP, false, false),
	AM_LIGHTNING("AMLightning", "magica fulgur",
			BeeClassification.ESSENTIAL, 0xEBEFA1, 0xE3A55B, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	AM_PLANT("AMPlant", "magica herba",
			BeeClassification.ESSENTIAL, 0x49B549, 0xE3A55B, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	AM_ICE("AMIce", "magica glacium",
			BeeClassification.ESSENTIAL, 0x86BAC6, 0xE3A55B, EnumTemperature.COLD, EnumHumidity.NORMAL, false, false),
	//AM_MAGMA("AMMagma", "magica torrens igneus",
	//		BeeClassification.ESSENTIAL, 0x932B1E, 0xE3A55B, EnumTemperature.HELLISH, EnumHumidity.ARID, false, false),
	AM_ARCANE("AMArcane", "magica arcanum",
			BeeClassification.ESSENTIAL, 0x76184D, 0xE3A55B, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, false),
			
	AM_VORTEX("AMVortex", "gurges",
			BeeClassification.ESSENTIAL, 0x71BBE2, 0x0B35A8, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	AM_WIGHT("AMWight", "vectem",
			BeeClassification.ESSENTIAL, 0xB50000, 0x4C4837, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	;
	
	public static void setupBeeSpecies()
	{
		// Species must be set inactive prior to registration.
		if (ThaumcraftHelper.isActive())
		{
			SCHOLARLY.addSpecialty(Config.miscResources.getStackForType(ResourceType.LORE_FRAGMENT), 2);
			SAVANT.addSpecialty(Config.miscResources.getStackForType(ResourceType.LORE_FRAGMENT), 5);
			
			TC_AIR.addSpecialty(new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.AIR.ordinal()), 5);
			TC_FIRE.addSpecialty(new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.FIRE.ordinal()), 5);
			TC_WATER.addSpecialty(new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.WATER.ordinal()), 5);
			TC_EARTH.addSpecialty(new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.EARTH.ordinal()), 5);
			TC_ORDER.addSpecialty(new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.ORDER.ordinal()), 5);
			TC_CHAOS.addSpecialty(new ItemStack(Config.tcShard, 1, ThaumcraftHelper.ShardType.CHAOS.ordinal()), 5);
			
			TC_BRAINY.addSpecialty(new ItemStack(Config.tcMiscResource,  1, ThaumcraftHelper.MiscResource.ZOMBIE_BRAIN.ordinal()), 2);
			TC_CHICKEN.addSpecialty(new ItemStack(Config.tcNuggetChicken, 1), 9);
			TC_BEEF.addSpecialty(new ItemStack(Config.tcNuggetBeef, 1), 9);
			TC_PORK.addSpecialty(new ItemStack(Config.tcNuggetPork, 1), 9);
		}
		else
		{
			TC_CHAOS.setInactive();
			TC_AIR.setInactive();
			TC_FIRE.setInactive();
			TC_WATER.setInactive();
			TC_EARTH.setInactive();
			TC_ORDER.setInactive();
			TC_BRAINY.setInactive();
			TC_BATTY.setInactive();
			TC_CHICKEN.setInactive();
			TC_BEEF.setInactive();
			TC_PORK.setInactive();
		}
		// TODO: Temporary always off. Maybe.
			TC_WISPY.setInactive();
			TC_VIS.setInactive();
			TC_FLUX.setInactive();
			TC_ATTRACT.setInactive();
			TC_PURE.setInactive();
			TC_REJUVENATING.setInactive();
		
		if (EquivalentExchangeHelper.isActive())
		{
			EE_MINIUM.addSpecialty(new ItemStack(Config.eeMinuimShard), 6);
		}
		else
		{
			EE_MINIUM.setInactive();
		}
		
		if (ArsMagicaHelper.isActive())
		{
			AM_QUINTESSENCE.addSpecialty(new ItemStack(Config.amItemResource, 1, ArsMagicaHelper.ResourceType.ARCANE_COMPOUND.ordinal()), 5);
			AM_EARTH.addSpecialty(new ItemStack(Config.amEssence, 1, ArsMagicaHelper.EssenceType.EARTH.ordinal()), 7);
			AM_AIR.addSpecialty(new ItemStack(Config.amEssence, 1, ArsMagicaHelper.EssenceType.AIR.ordinal()), 7);
			AM_FIRE.addSpecialty(new ItemStack(Config.amEssence, 1, ArsMagicaHelper.EssenceType.FIRE.ordinal()), 7);
			AM_WATER.addSpecialty(new ItemStack(Config.amEssence, 1, ArsMagicaHelper.EssenceType.WATER.ordinal()), 7);
			AM_LIGHTNING.addSpecialty(new ItemStack(Config.amEssence, 1, ArsMagicaHelper.EssenceType.LIGHTNING.ordinal()), 7);
			AM_PLANT.addSpecialty(new ItemStack(Config.amEssence, 1, ArsMagicaHelper.EssenceType.PLANT.ordinal()), 7);
			AM_ICE.addSpecialty(new ItemStack(Config.amEssence, 1, ArsMagicaHelper.EssenceType.ICE.ordinal()), 7);
			AM_ARCANE.addSpecialty(new ItemStack(Config.amEssence, 1, ArsMagicaHelper.EssenceType.ARCANE.ordinal()), 11);
			AM_VORTEX.addSpecialty(new ItemStack(Config.amEssence, 1, ArsMagicaHelper.EssenceType.EARTH.ordinal()), 15);
			AM_WIGHT.addSpecialty(new ItemStack(Config.amItemResource, 1, ArsMagicaHelper.ResourceType.ARCANE_COMPOUND.ordinal()), 11);
		}
		else
		{
			AM_ESSENCE.setInactive();
			AM_QUINTESSENCE.setInactive();
			AM_EARTH.setInactive();
			AM_AIR.setInactive();
			AM_FIRE.setInactive();
			AM_WATER.setInactive();
			AM_LIGHTNING.setInactive();
			AM_PLANT.setInactive();
			AM_ICE.setInactive();
			AM_ARCANE.setInactive();
			AM_VORTEX.setInactive();
			AM_WIGHT.setInactive();
		}
		AlleleManager.alleleRegistry.registerDeprecatedAlleleReplacement("thaumicbees.speciesBlitz", AM_LIGHTNING);
		
		// Oredict bees
		if (OreDictionary.getOres("ingotCopper").size() <= 0)
		{
			COPPER.setInactive();
		}
		if (OreDictionary.getOres("ingotTin").size() <= 0)
		{
			TIN.setInactive();
		}
		if (OreDictionary.getOres("ingotSilver").size() <= 0)
		{
			SILVER.setInactive();
		}
		if (OreDictionary.getOres("ingotLead").size() <= 0)
		{
			LEAD.setInactive();
		}
		if (OreDictionary.getOres("ingotNaturalAluminum").size() <= 0)
		{
			ALUMINUM.setInactive();
		}
		if (OreDictionary.getOres("ingotArdite").size() <= 0)
		{
			ARDITE.setInactive();
		}
		if (OreDictionary.getOres("ingotCobalt").size() <= 0)
		{
			COBALT.setInactive();
		}
		if (OreDictionary.getOres("ingotManyullyn").size() <= 0)
		{
			MANYULLYN.setInactive();
		}
		
		MYSTICAL.addProduct(Config.combs.getStackForType(CombType.MUNDANE), 15)
			.setGenome(BeeGenomeManager.getTemplateMystical())
			.register();
		SORCEROUS.addProduct(Config.combs.getStackForType(CombType.MUNDANE), 15)
			.setGenome(BeeGenomeManager.getTemplateSorcerous())
			.register();
		UNUSUAL.addProduct(Config.combs.getStackForType(CombType.MUNDANE), 15)
			.setGenome(BeeGenomeManager.getTemplateUnusual())
			.register();
		ATTUNED.addProduct(Config.combs.getStackForType(CombType.MUNDANE), 15)
			.setGenome(BeeGenomeManager.getTemplateAttuned())
			.register();
		ELDRITCH.addProduct(Config.combs.getStackForType(CombType.MUNDANE), 15)
			.setGenome(BeeGenomeManager.getTemplateEldritch())
			.register();
		
		ESOTERIC.addProduct(Config.combs.getStackForType(CombType.OCCULT), 18)
			.setGenome(BeeGenomeManager.getTemplateEsoteric())
			.register();
		MYSTERIOUS.addProduct(Config.combs.getStackForType(CombType.OCCULT), 20)
			.setGenome(BeeGenomeManager.getTemplateMysterious())
			.register();
		ARCANE.addProduct(Config.combs.getStackForType(CombType.OCCULT), 25)
			.addSpecialty(Config.drops.getStackForType(DropType.ENCHANTED, 1), 9)
			.setGenome(BeeGenomeManager.getTemplateArcane())
			.register();
		
		CHARMED.addProduct(Config.combs.getStackForType(CombType.OTHERWORLDLY), 18)
			.setGenome(BeeGenomeManager.getTemplateCharmed())
			.register();
		ENCHANTED.addProduct(Config.combs.getStackForType(CombType.OTHERWORLDLY), 20)
			.setGenome(BeeGenomeManager.getTemplateEnchanted())
			.register();
		SUPERNATURAL.addProduct(Config.combs.getStackForType(CombType.OTHERWORLDLY), 25)
			.addSpecialty(Config.pollen.getStackForType(PollenType.UNUSUAL), 8)
			.setGenome(BeeGenomeManager.getTemplateSupernatural())
			.register();
		
		ETHEREAL.addProduct(Config.combs.getStackForType(CombType.OCCULT), 10)
			.addProduct(Config.combs.getStackForType(CombType.OTHERWORLDLY), 10)
			.setGenome(BeeGenomeManager.getTemplateEthereal())
			.register();
		
		WINDY.addProduct(Config.combs.getStackForType(CombType.AIRY), 25)
			.setGenome(BeeGenomeManager.getTemplateWindy())
			.register();
		FIREY.addProduct(Config.combs.getStackForType(CombType.FIREY), 25)
			.setGenome(BeeGenomeManager.getTemplateFirey())
			.register();
		EARTHY.addProduct(Config.combs.getStackForType(CombType.EARTHY), 25)
			.setGenome(BeeGenomeManager.getTemplateEarthy())
			.register();
		WATERY.addProduct(Config.combs.getStackForType(CombType.WATERY), 25)
			.addSpecialty(new ItemStack(Block.ice), 2)
			.setGenome(BeeGenomeManager.getTemplateWatery())
			.register();
				
		PUPIL.addProduct(Config.combs.getStackForType(CombType.PAPERY), 20)
			.setGenome(BeeGenomeManager.getTemplatePupil())
			.register();
		SCHOLARLY.addProduct(Config.combs.getStackForType(CombType.PAPERY), 25)
			.setGenome(BeeGenomeManager.getTemplateScholarly())
			.register();
		SAVANT.addProduct(Config.combs.getStackForType(CombType.PAPERY), 40)
			.setGenome(BeeGenomeManager.getTemplateSavant())
			.register();
		
		AWARE.addProduct(Config.combs.getStackForType(CombType.INTELLECT), 18)
			.setGenome(BeeGenomeManager.getTemplateAware())
			.register();
		SPIRIT.addProduct(Config.combs.getStackForType(CombType.INTELLECT), 22)
			.addSpecialty(Config.combs.getStackForType(CombType.SOUL), 16)
			.setGenome(BeeGenomeManager.getTemplateSpirit())
			.register();
		SOUL.addProduct(Config.combs.getStackForType(CombType.INTELLECT), 28)
			.addSpecialty(Config.combs.getStackForType(CombType.SOUL), 20)
			.setGenome(BeeGenomeManager.getTemplateSoul())
			.register();
		
		SKULKING.addProduct(Config.combs.getStackForType(CombType.FURTIVE), 10)
			.setGenome(BeeGenomeManager.getTemplateSkulking())
			.register();	
		GHASTLY.addProduct(Config.combs.getStackForType(CombType.FURTIVE), 8)
			.addSpecialty(new ItemStack(Item.ghastTear), 2)
			.setGenome(BeeGenomeManager.getTemplateGhastly())
			.register();
		SPIDERY.addProduct(Config.combs.getStackForType(CombType.FURTIVE), 13)
			.addProduct(new ItemStack(Item.silk), 8)
			.addSpecialty(new ItemStack(Item.spiderEye), 8)
			.setGenome(BeeGenomeManager.getTemplateSpidery())
			.register();
		SMOULDERING.addProduct(Config.combs.getStackForType(CombType.FURTIVE), 10)
			.addProduct(Config.combs.getStackForType(CombType.MOLTEN), 10)
			.addSpecialty(new ItemStack(Item.blazeRod), 5)
			.setGenome(BeeGenomeManager.getTemplateSmouldering())
			.register();
		
		TIMELY.addProduct(Config.combs.getStackForType(CombType.TEMPORAL), 16)
			.setGenome(BeeGenomeManager.getTemplateTimely())
			.register();		
		LORDLY.addProduct(Config.combs.getStackForType(CombType.TEMPORAL), 19)
			.setGenome(BeeGenomeManager.getTemplateLordly())
			.register();		
		DOCTORAL.addProduct(Config.combs.getStackForType(CombType.TEMPORAL), 24)
			.addSpecialty(new ItemStack(Config.jellyBaby), 7)
			.setGenome(BeeGenomeManager.getTemplateDoctoral())
			.register();

		INFERNAL.addProduct(Config.combs.getStackForType(CombType.MOLTEN), 12)
			.setGenome(BeeGenomeManager.getTemplateInfernal())
			.register();
		HATEFUL.addProduct(Config.combs.getStackForType(CombType.MOLTEN), 18)
			.setGenome(BeeGenomeManager.getTemplateHateful())
			.register();
		SPITEFUL.addProduct(Config.combs.getStackForType(CombType.MOLTEN), 24)
			.setGenome(BeeGenomeManager.getTemplateSpiteful())
			.register();
		WITHERING.addSpecialty(Config.miscResources.getStackForType(ResourceType.SKULL_CHIP), 15)
			.setGenome(BeeGenomeManager.getTemplateWithering())
			.register();

		OBLIVION.addProduct(Config.combs.getStackForType(CombType.FORGOTTEN), 14)
			.setGenome(BeeGenomeManager.getTemplateOblivion())
			.register();
		NAMELESS.addProduct(Config.combs.getStackForType(CombType.FORGOTTEN), 19)
			.setGenome(BeeGenomeManager.getTemplateNameless())
			.register();
		ABANDONED.addProduct(Config.combs.getStackForType(CombType.FORGOTTEN), 24)
			.setGenome(BeeGenomeManager.getTemplateAbandoned())
			.register();
		FORLORN.addProduct(Config.combs.getStackForType(CombType.FORGOTTEN), 30)
			.setGenome(BeeGenomeManager.getTemplateForlorn())
			.register();
		DRACONIC.addSpecialty(Config.miscResources.getStackForType(ResourceType.DRAGON_DUST), 15)
			.setGenome(BeeGenomeManager.getTemplateDraconic())
			.register();
		
		IRON.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.addSpecialty(Config.nuggets.getStackForType(NuggetType.IRON), 18)
			.setGenome(BeeGenomeManager.getTemplateIron())
			.register();
		GOLD.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.addSpecialty(new ItemStack(Item.goldNugget, 1), 16)
			.setGenome(BeeGenomeManager.getTemplateGold())
			.register();
		COPPER.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.addSpecialty(Config.nuggets.getStackForType(NuggetType.COPPER), 20)
			.setGenome(BeeGenomeManager.getTemplateCopper())
			.register();
		TIN.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.addSpecialty(Config.nuggets.getStackForType(NuggetType.TIN), 20)
			.setGenome(BeeGenomeManager.getTemplateTin())
			.register();
		SILVER.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.addSpecialty(Config.nuggets.getStackForType(NuggetType.SILVER), 16)
			.setGenome(BeeGenomeManager.getTemplateSilver())
			.register();
		LEAD.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.addSpecialty(Config.nuggets.getStackForType(NuggetType.LEAD), 17)
			.setGenome(BeeGenomeManager.getTemplateLead())
			.register();
		
		ALUMINUM.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.setGenome(BeeGenomeManager.getTemplateAluminum());
		if (OreDictionary.getOres("nuggetNaturalAluminum").size() > 0)
		{
			ALUMINUM.addSpecialty(OreDictionary.getOres("nuggetNaturalAluminum").get(0), 20);
		}
		else
		{
			ALUMINUM.setInactive();
		}
		ALUMINUM.register();
		
		ARDITE.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.setGenome(BeeGenomeManager.getTemplateArdite());
		if (OreDictionary.getOres("nuggetArdite").size() > 0)
		{
			ARDITE.addSpecialty(OreDictionary.getOres("nuggetArdite").get(0), 18);
		}
		else
		{
			ARDITE.setInactive();
		}
		ARDITE.register();
		
		COBALT.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.setGenome(BeeGenomeManager.getTemplateCobalt());
		if (OreDictionary.getOres("nuggetCobalt").size() > 0)
		{
			COBALT.addSpecialty(OreDictionary.getOres("nuggetCobalt").get(0), 18);
		}
		else
		{
			COBALT.setInactive();
		}
		COBALT.register();
		
		MANYULLYN.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.setGenome(BeeGenomeManager.getTemplateManyullyn());
		if (OreDictionary.getOres("nuggetManyullyn").size() > 0)
		{
			MANYULLYN.addSpecialty(OreDictionary.getOres("nuggetManyullyn").get(0), 16);
		}
		else
		{
			MANYULLYN.setInactive();
		}
		MANYULLYN.register();
		
		DIAMOND.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.addSpecialty(Config.nuggets.getStackForType(NuggetType.DIAMOND), 6)
			.setGenome(BeeGenomeManager.getTemplateDiamond())
			.register();
		EMERALD.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.addSpecialty(Config.nuggets.getStackForType(NuggetType.EMERALD), 4)
			.setGenome(BeeGenomeManager.getTemplateEmerald())
			.register();
		APATITE.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.addSpecialty(Config.nuggets.getStackForType(NuggetType.APATITE), 10)
			.setGenome(BeeGenomeManager.getTemplateApatite())
			.register();
		
		MUTABLE.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.PARCHED.ordinal()), 30)
			.addProduct(Config.combs.getStackForType(CombType.TRANSMUTED), 10)
			.setGenome(BeeGenomeManager.getTemplateMutable())
			.register();
		TRANSMUTING.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.PARCHED.ordinal()), 10)
			.addProduct(Config.combs.getStackForType(CombType.TRANSMUTED), 30)
			.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.SILKY.ordinal()), 5)
			.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.SIMMERING.ordinal()), 5)
			.setGenome(BeeGenomeManager.getTemplateTransmuting())
			.register();
		CRUMBLING.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.PARCHED.ordinal()), 10)
			.addProduct(Config.combs.getStackForType(CombType.TRANSMUTED), 30)
			.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.POWDERY.ordinal()), 10)
			.addProduct(new ItemStack(Config.fBeeComb, 1 , ForestryHelper.Comb.COCOA.ordinal()), 15)
			.setGenome(BeeGenomeManager.getTemplateCrumbling())
			.register();
		
		
		TC_AIR.addProduct(Config.combs.getStackForType(CombType.AIRY), 20)
			.setGenome(BeeGenomeManager.getTemplateTCAir())
			.register();
		TC_FIRE.addProduct(Config.combs.getStackForType(CombType.FIREY), 20)
			.setGenome(BeeGenomeManager.getTemplateTCFire())
			.register();
		TC_WATER.addProduct(Config.combs.getStackForType(CombType.WATERY), 20)
			.setGenome(BeeGenomeManager.getTemplateTCWater())
			.register();
		TC_EARTH.addProduct(Config.combs.getStackForType(CombType.EARTHY), 20)
			.setGenome(BeeGenomeManager.getTemplateTCEarth())
			.register();
		TC_ORDER.addProduct(Config.combs.getStackForType(CombType.TC_ORDER), 20)
			.setGenome(BeeGenomeManager.getTemplateTCMagic())
			.register();
		TC_CHAOS.addProduct(Config.combs.getStackForType(CombType.TC_CHAOS), 20)
			.setGenome(BeeGenomeManager.getTemplateTCStark())
			.register();
		
		TC_VIS.addProduct(Config.combs.getStackForType(CombType.INTELLECT), 10)
			.setGenome(BeeGenomeManager.getTemplateTCVis())
			.register();
		TC_FLUX.addProduct(Config.combs.getStackForType(CombType.INTELLECT), 18)
			.setGenome(BeeGenomeManager.getTemplateTCFlux())
			.register();
		TC_ATTRACT.addProduct(Config.combs.getStackForType(CombType.INTELLECT), 14)
			.setGenome(BeeGenomeManager.getTemplateTCAttract())
			.register();
		TC_PURE.addProduct(Config.combs.getStackForType(CombType.INTELLECT), 16)
			.setGenome(BeeGenomeManager.getTemplateTCPure())
			.register();
		TC_REJUVENATING.addProduct(Config.combs.getStackForType(CombType.INTELLECT), 18)
			.setGenome(BeeGenomeManager.getTemplateTCRejuvinating())
			.register();
		
		TC_BRAINY.addProduct(Config.combs.getStackForType(CombType.FURTIVE), 10)
			.addProduct(new ItemStack(Item.rottenFlesh), 6)
			.setGenome(BeeGenomeManager.getTemplateTCBrainy())
			.register();
		TC_BATTY.addProduct(Config.combs.getStackForType(CombType.FURTIVE), 10)
			.addSpecialty(new ItemStack(Item.gunpowder), 4)
			.setGenome(BeeGenomeManager.getTemplateTCBatty())
			.register();	
		TC_WISPY.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.SILKY.ordinal()), 22)
			.addSpecialty(new ItemStack(Config.fCraftingResource, 1, ForestryHelper.CraftingMaterial.SILK_WISP.ordinal()), 4)
			.setGenome(BeeGenomeManager.getTemplateTCWispy())
			.register();
		TC_CHICKEN.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.setGenome(BeeGenomeManager.getTemplateTCChicken())
			.register();
		TC_BEEF.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.setGenome(BeeGenomeManager.getTemplateTCBeef())
			.register();
		TC_PORK.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.setGenome(BeeGenomeManager.getTemplateTCPork())
			.register();

		
		EE_MINIUM.addProduct(Config.combs.getStackForType(CombType.OCCULT), 16)
			.setGenome(BeeGenomeManager.getTemplateEEMinium())
			.register();
		
		
		AM_ESSENCE.addProduct(Config.combs.getStackForType(CombType.AM_ESSENCE), 12)
			.setGenome(BeeGenomeManager.getTemplateAMEssence())
			.register();
		AM_QUINTESSENCE.addProduct(Config.combs.getStackForType(CombType.AM_ESSENCE), 23)
			.setGenome(BeeGenomeManager.getTemplateAMQuintessence())
			.register();
		AM_EARTH.addProduct(Config.combs.getStackForType(CombType.AM_POTENT), 12)
			.setGenome(BeeGenomeManager.getTemplateAMEarth())
			.register();
		AM_AIR.addProduct(Config.combs.getStackForType(CombType.AM_POTENT), 12)
			.setGenome(BeeGenomeManager.getTemplateAMAir())
			.register();
		AM_FIRE.addProduct(Config.combs.getStackForType(CombType.AM_POTENT), 12)
			.setGenome(BeeGenomeManager.getTemplateAMFire())
			.register();
		AM_WATER.addProduct(Config.combs.getStackForType(CombType.AM_POTENT), 12)
			.setGenome(BeeGenomeManager.getTemplateAMWater())
			.register();
		AM_LIGHTNING.addProduct(Config.combs.getStackForType(CombType.AM_POTENT), 12)
			.setGenome(BeeGenomeManager.getTemplateAMLightning())
			.register();
		AM_PLANT.addProduct(Config.combs.getStackForType(CombType.AM_POTENT), 12)
			.setGenome(BeeGenomeManager.getTemplateAMPlant())
			.register();
		AM_ICE.addProduct(Config.combs.getStackForType(CombType.AM_POTENT), 12)
			.setGenome(BeeGenomeManager.getTemplateAMIce())
			.register();
		AM_ARCANE.addProduct(Config.combs.getStackForType(CombType.AM_POTENT), 19)
			.setGenome(BeeGenomeManager.getTemplateAMArcane())
			.register();
		AM_VORTEX.addProduct(Config.combs.getStackForType(CombType.AM_ESSENCE), 10)
			.setGenome(BeeGenomeManager.getTemplateAMVortex())
			.register();
		AM_WIGHT.addProduct(Config.combs.getStackForType(CombType.SOUL), 30)
			.addProduct(Config.combs.getStackForType(CombType.FURTIVE), 10)
			.setGenome(BeeGenomeManager.getTemplateAMWight())
			.register();
	}
	
	private String binomial;
	private String authority;
	private int primaryColour;
	private int secondaryColour;
	private EnumTemperature temperature;
	private EnumHumidity humidity;
	private boolean hasEffect;
	private boolean isSecret;
	private boolean isCounted;
	private boolean isActive;
	private boolean isNocturnal;
	private IClassification branch;
	private HashMap<ItemStack, Integer> products;
	private HashMap<ItemStack, Integer> specialties;
	private IAllele genomeTemplate[];
	private String uid;
	private boolean dominant;
	private int complexity;
	
	@SideOnly(Side.CLIENT)
	private Icon[][] icons;
	
	private final static int defaultBodyColour = 0xFF7C26;
	
	private BeeSpecies(String speciesName, String genusName, IClassification classification, int firstColour,
			EnumTemperature preferredTemp, EnumHumidity preferredHumidity, boolean hasGlowEffect, boolean isSpeciesDominant)
	{
		this(speciesName, genusName, classification, firstColour, defaultBodyColour,
				preferredTemp, preferredHumidity, hasGlowEffect, true, true, isSpeciesDominant);
	}

	private BeeSpecies(String speciesName, String genusName, IClassification classification, int firstColour, int secondColour,
			EnumTemperature preferredTemp, EnumHumidity preferredHumidity, boolean hasGlowEffect, boolean isSpeciesDominant)
	{
		this(speciesName, genusName, classification, firstColour, secondColour, preferredTemp, preferredHumidity, hasGlowEffect, true, true, isSpeciesDominant);
	}

	private BeeSpecies(String speciesName, String genusName, IClassification classification, int firstColour, int secondColour,
			EnumTemperature preferredTemp, EnumHumidity preferredHumidity, boolean isSecret, boolean hasGlowEffect, boolean isSpeciesDominant)
	{
		this(speciesName, genusName, classification, firstColour, secondColour,
				preferredTemp, preferredHumidity, hasGlowEffect, isSecret, true, isSpeciesDominant);
	}

	private BeeSpecies(String speciesName, String genusName, IClassification classification, int firstColour,
			EnumTemperature preferredTemp, EnumHumidity preferredHumidity, boolean isSecret, boolean hasGlowEffect, boolean isSpeciesDominant)
	{
		this(speciesName, genusName, classification, firstColour, defaultBodyColour,
				preferredTemp, preferredHumidity, hasGlowEffect, isSecret, true, isSpeciesDominant);
	}

	private BeeSpecies(String speciesName, String genusName, IClassification classification, int firstColour, int secondColour,
			EnumTemperature preferredTemp, EnumHumidity preferredHumidity,
			boolean hasGlowEffect, boolean isSpeciesSecret, boolean isSpeciesCounted, boolean isSpeciesDominant)
	{
		this.uid = "magicbees.species" + speciesName;
		this.dominant = isSpeciesDominant;
		AlleleManager.alleleRegistry.registerAllele(this);
		binomial = genusName;
		authority = "MysteriousAges";
		primaryColour = firstColour;
		secondaryColour = secondColour;
		temperature = preferredTemp;
		humidity = preferredHumidity;
		hasEffect = hasGlowEffect;
		isSecret = isSpeciesSecret;
		isCounted = isSpeciesCounted;
		products = new HashMap();
		specialties = new HashMap();
		this.branch = classification;
		this.branch.addMemberSpecies(this);
		this.isActive = true;
		this.isNocturnal = false;
	}

	public BeeSpecies setGenome(IAllele genome[])
	{
		genomeTemplate = genome;
		return this;
	}

	public IAllele[] getGenome()
	{
		return genomeTemplate;
	}

	public BeeSpecies addProduct(ItemStack produce, int percentChance)
	{
		products.put(produce, percentChance);
		return this;
	}

	public BeeSpecies addSpecialty(ItemStack produce, int percentChance)
	{
		specialties.put(produce, Integer.valueOf(percentChance));
		return this;
	}

	public ItemStack getBeeItem(EnumBeeType beeType)
	{
		return BeeManager.beeRoot.getMemberStack(BeeManager.beeRoot.getBee(null, BeeManager.beeRoot.templateAsGenome(genomeTemplate)), beeType.ordinal());
	}

	@Override
	public String getName()
	{
		return LocalizationManager.getLocalizedString(getUID());
	}

	@Override
	public String getDescription()
	{
		return LocalizationManager.getLocalizedString(getUID() + ".description");
	}

	@Override
	public EnumTemperature getTemperature()
	{
		return temperature;
	}

	@Override
	public EnumHumidity getHumidity()
	{
		return humidity;
	}

	@Override
	public boolean hasEffect()
	{
		return hasEffect;
	}

	public BeeSpecies setInactive()
	{
		this.isActive = false;
		return this;
	}

	public boolean isActive()
	{
		return this.isActive;
	}

	@Override
	public boolean isSecret()
	{
		return isSecret;
	}

	@Override
	public boolean isCounted()
	{
		return isCounted;
	}

	@Override
	public String getBinomial()
	{
		return binomial;
	}

	@Override
	public String getAuthority()
	{
		return authority;
	}

	@Override
	public IClassification getBranch()
	{
		return this.branch;
	}

	@Override
	public HashMap getProducts()
	{
		return products;
	}

	@Override
	public HashMap getSpecialty()
	{
		return specialties;
	}

	@Override
	public String getUID()
	{
		return this.uid;
	}

	@Override
	public boolean isDominant()
	{
		return this.dominant;
	}

	@Override
	public IBeeRoot getRoot()
	{
		return BeeManager.beeRoot;
	}

	@Override
	public boolean isNocturnal()
	{
		return this.isNocturnal;
	}

	@Override
	public boolean isJubilant(IBeeGenome genome, IBeeHousing housing)
	{
		return true;
	}

	private BeeSpecies register()
	{
		BeeManager.beeRoot.registerTemplate(this.getGenome());
		if (!this.isActive)
		{
			AlleleManager.alleleRegistry.blacklistAllele(this.getUID());
		}
		return this;
	}

	@Override
	public int getIconColour(int renderPass)
	{
		int value = 0xffffff;
		if (renderPass == 0) {
			value = this.primaryColour;
		}
		else if (renderPass == 1) {
			value = this.secondaryColour;
		}
		return value;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIconProvider getIconProvider()
	{
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(EnumBeeType type, int renderPass)
	{
		return icons[type.ordinal()][Math.min(renderPass, 2)];
	}

	@Override
	public int getComplexity()
	{
		return 1 + getMutationPathLength(this, new ArrayList<IAllele>());
	}
	
	private int getMutationPathLength(IAllele species, ArrayList<IAllele> excludeSpecies)
	{
		int own = 1;
		int highest = 0;
		excludeSpecies.add(species);
		
		for(IMutation mutation : getRoot().getPaths(species, EnumBeeChromosome.SPECIES.ordinal())) {
			if(!excludeSpecies.contains(mutation.getAllele0())) {
				int otherAdvance = getMutationPathLength(mutation.getAllele0(), excludeSpecies);
				if(otherAdvance > highest)
					highest = otherAdvance;
			}
			if(!excludeSpecies.contains(mutation.getAllele1())) {
				int otherAdvance = getMutationPathLength(mutation.getAllele1(), excludeSpecies);
				if(otherAdvance > highest)
					highest = otherAdvance;
			}
		}
		
		return own + (highest > 0 ? highest : 0);
	}

	@Override
	public float getResearchSuitability(ItemStack itemStack)
	{
		float value = 0f;
		if(itemStack != null)
		{
			for (ItemStack product : this.products.keySet())
			{
				if (itemStack.isItemEqual(product))
				{
					value = 1f;
					break;
				}
			}
			
			if (value <= 0f)
			{
				for (ItemStack specialty : this.specialties.keySet())
				{
					if (specialty.isItemEqual(itemStack))
					{
						value = 1f;
						break;
					}
				}
				if (value <= 0f)
				{
					if (itemStack.itemID == Config.fHoneyDrop.itemID)
					{
						
					}
					else if (itemStack.itemID == Config.fHoneydew.itemID)
					{
						
					}
					else if (itemStack.itemID == Config.fBeeComb.itemID ||
							itemStack.itemID == Config.combs.itemID)
					{
						value = 4f;
					}
					else
					{
						for (Map.Entry<ItemStack, Float> catalyst : BeeManager.beeRoot.getResearchCatalysts().entrySet())
						{
							if (OreDictionary.itemMatches(itemStack, catalyst.getKey(), false))
							{
								value = catalyst.getValue().floatValue();
								break;
							}
						}
					}
				}
			}
		}
		
		return value;
	}

	@Override
	public ItemStack[] getResearchBounty(World world, String researcher, IIndividual individual, int bountyLevel)
	{
		System.out.println("Bounty level: " + bountyLevel);
		ArrayList<ItemStack> bounty = new ArrayList<ItemStack>();
		
		if (world.rand.nextFloat() < ((10f / bountyLevel)))
		{
			Collection<? extends IMutation> resultantMutations = getRoot().getCombinations(this);
			if (resultantMutations.size() > 0)
			{
				IMutation[] candidates = resultantMutations.toArray(new IMutation[resultantMutations.size()]);
				bounty.add(AlleleManager.alleleRegistry.getMutationNoteStack(researcher, candidates[world.rand.nextInt(candidates.length)]));
			}
		}
		
		for (ItemStack product : this.products.keySet())
		{
			ItemStack copy = product.copy();
			copy.stackSize = 1 + world.rand.nextInt(bountyLevel / 2);
			bounty.add(copy);
		}
		
		for (ItemStack specialty : this.specialties.keySet())
		{
			ItemStack copy = specialty.copy();
			copy.stackSize = world.rand.nextInt(bountyLevel / 3);
			if (copy.stackSize > 0)
			{
				bounty.add(copy);
			}
		}
		
		return bounty.toArray(new ItemStack[bounty.size()]);
	}

	@Override
	public String getEntityTexture()
	{
		return "/gfx/forestry/entities/bees/honeyBee.png";
	}
	
	private static final BeeSpecies[] skulkingIconBees = { SKULKING, GHASTLY, SPIDERY, SMOULDERING, TC_BRAINY, TC_WISPY, TC_BATTY, AM_VORTEX, TC_WISPY };

	@Override
	public void registerIcons(IconRegister itemMap)
	{
		this.icons = new Icon[EnumBeeType.values().length][3];
		
		String root = this.getIconPath();
		
		Icon body1 = itemMap.registerIcon(root + "body1");

		for (int i = 0; i < EnumBeeType.values().length; i++)
		{
			if(EnumBeeType.values()[i] == EnumBeeType.NONE)
				continue;
			
			icons[i][0] = itemMap.registerIcon(root + EnumBeeType.values()[i].toString().toLowerCase(Locale.ENGLISH) + ".outline");
			icons[i][1] = (EnumBeeType.values()[i] != EnumBeeType.LARVAE) ? body1 :
							itemMap.registerIcon(root + EnumBeeType.values()[i].toString().toLowerCase(Locale.ENGLISH) + ".body");
			icons[i][2] = itemMap.registerIcon(root + EnumBeeType.values()[i].toString().toLowerCase(Locale.ENGLISH) + ".body2");
		}
	}
	
	private String getIconPath()
	{
		String value;
		
		switch (this)
		{
		case SKULKING: case GHASTLY: case SPIDERY: case SMOULDERING:
		case TC_BRAINY: case TC_WISPY: case TC_BATTY:
		case AM_VORTEX: case AM_WIGHT:
			value = VersionInfo.ModName.toLowerCase() + ":bees/skulking/";
			break;
			
		default:
			value = ForestryHelper.Name.toLowerCase() + ":bees/default/";
			break;
		}
		
		return value;
	}

	/// --------- Unused Functions ---------------------------------------------
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(short texUID)
	{
		return icons[0][0];
	}
}
