package thaumicbees.bees;

import java.util.HashMap;
import java.util.Locale;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.oredict.OreDictionary;
import thaumicbees.item.types.CombType;
import thaumicbees.item.types.DropType;
import thaumicbees.item.types.NuggetType;
import thaumicbees.item.types.PollenType;
import thaumicbees.item.types.ResourceType;
import thaumicbees.main.Config;
import thaumicbees.main.utils.LocalizationManager;
import thaumicbees.main.utils.compat.EquivalentExchangeHelper;
import thaumicbees.main.utils.compat.ForestryHelper;
import thaumicbees.main.utils.compat.ThaumcraftHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IIconProvider;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;

public enum BeeSpecies implements IAlleleBeeSpecies, IIconProvider
{
	ESOTERIC("Esoteric", "secretiore",
			BeeClassification.ARCANE, 0x001099, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	MYSTERIOUS("Mysterious", "mysticus",
			BeeClassification.ARCANE, 0x762bc2, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	ARCANE("Arcane", "arcanus",
			BeeClassification.ARCANE, 0xd242df, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, true),
	CHARMED("Charmed", "larvatus",
			BeeClassification.SUPERNATURAL, 0x48EEEC, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	ENCHANTED("Enchanted", "cantatus",
			BeeClassification.SUPERNATURAL, 0x18e726, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	SUPERNATURAL("Supernatural", "coeleste",
			BeeClassification.SUPERNATURAL, 0x005614, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, true),
	PUPIL("Pupil", "disciplina",
				BeeClassification.SCHOLARLY, 0xFFFF00, EnumTemperature.NORMAL, EnumHumidity.ARID, false, true),
	SCHOLARLY("Scholarly", "studiosis",
			BeeClassification.SCHOLARLY, 0x6E0000, EnumTemperature.NORMAL, EnumHumidity.ARID, false, false),
	SAVANT("Savant", "philologus",
			BeeClassification.SCHOLARLY, 0x6E1C6D, EnumTemperature.NORMAL, EnumHumidity.ARID, true, false),
	STARK("Stark", "torridae",
			BeeClassification.THAUMIC, 0xCCCCCC, 0x999999, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, false),
	AIR("Aura", "ventosa",
			BeeClassification.THAUMIC, 0xD9D636, 0xA19E10, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, true),
	FIRE("Ignis", "praefervidus",
			BeeClassification.THAUMIC, 0xE50B0B, 0x95132F, EnumTemperature.HOT, EnumHumidity.ARID, true, true),
	WATER("Aqua", "umidus",
			BeeClassification.THAUMIC, 0x36CFD9, 0x1054A1, EnumTemperature.NORMAL, EnumHumidity.DAMP, true, true),
	EARTH("Solum", "sordida",
			BeeClassification.THAUMIC, 0x005100, 0x00a000, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, true),
	INFUSED("Praecantatio", "azanorius",
			BeeClassification.THAUMIC, 0xaa32fc, 0x7A489E, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, true),
	AWARE("Aware", "sensibilis",
			BeeClassification.VIS, 0xb0092e9, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	VIS("Vis", "arcanus saecula",
			BeeClassification.VIS, 0x004c99, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	PURE("Pure", "arcanus puritatem",
			BeeClassification.VIS, 0xb0092e9, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, false),
	FLUX("Flux", "arcanus labe",
			BeeClassification.VIS, 0x004c99, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, false),
	NODE("Node", "conficiens",
			BeeClassification.VIS, 0xFFF266, 0xFF8CE9, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, false),
	REJUVENATING("Rejuvenating", "arcanus vitae",
			BeeClassification.VIS, 0x91D0D9, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, false),
	SKULKING("Skulking", "malevolens",
			BeeClassification.SKULKING, 0x524827, 0xe15236, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	BRAINY("Brainy", "cerebrum",
			BeeClassification.SKULKING, 0x83FF70, 0xe15236, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	GOSSAMER("Gossamer", "perlucidus",
			BeeClassification.SKULKING, 0x183f66, 0xe15236, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	WISPY("Wispy", "umbrabilis",
			BeeClassification.SKULKING, 0x9cb8d5, 0xe15236, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	BATTY("Batty", "chiroptera",
			BeeClassification.SKULKING, 0x27350d, 0xe15236, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	GHASTLY("Ghastly", "pallens",
			BeeClassification.SKULKING, 0xccccee, 0xbf877c, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	TIMELY("Timely", "gallifreis",
			BeeClassification.TIME, 0xC6AF86, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	LORDLY("Lordly", "rassilonis",
			BeeClassification.TIME, 0xC6AF86, 0x8E0213, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	DOCTORAL("Doctoral", "medicus qui",
			BeeClassification.TIME, 0xDDE5FC, 0x4B6E8C, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, false),
	MINIUM("Minium", "mutabilis",
			BeeClassification.ALCHEMICAL, 0xac0921, 0x3a030b, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	SPIRIT("Spirit", "larva",
			BeeClassification.SOUL, 0xb2964b, EnumTemperature.WARM, EnumHumidity.NORMAL, false, true),
	SOUL("Soul", "anima",
			BeeClassification.SOUL, 0x7d591b, EnumTemperature.HELLISH, EnumHumidity.NORMAL, true, false),
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
	DIAMOND("Diamond", "diamond",
			BeeClassification.GEM, 0x209581, 0x8DF5E3, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, false),
	EMERALD("Emerald", "prasinus",
			BeeClassification.GEM, 0x005300, 0x17DD62, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, false),
	CHICKEN("Chicken", "pullus",
			BeeClassification.FLESHY, 0x7D431E, 0xE0905E, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	BEEF("Beef", "bubulae",
			BeeClassification.FLESHY, 0x40221A, 0xAC6753, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	PORK("Pork", "porcina",
			BeeClassification.FLESHY, 0x725D2F, 0xD2BF93, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	;
	
	public static void setupBeeSpecies()
	{
		// Species must be set inactive prior to registration.
		if (ThaumcraftHelper.isActive())
		{
			AIR.addSpecialty(new ItemStack(Config.tcNuggets, 1, ThaumcraftHelper.NuggetType.QUICKSILVER.ordinal()), 8);
			EARTH.addSpecialty(new ItemStack(Config.tcMiscResource, 1, ThaumcraftHelper.MiscResource.AMBER.ordinal()), 6);
			BRAINY.addSpecialty(new ItemStack(Config.tcMiscResource,  1, ThaumcraftHelper.MiscResource.ZOMBIE_BRAIN.ordinal()), 2);
			CHICKEN.addSpecialty(new ItemStack(Config.tcNuggetChicken, 1), 9);
			BEEF.addSpecialty(new ItemStack(Config.tcNuggetBeef, 1), 9);
			PORK.addSpecialty(new ItemStack(Config.tcNuggetPork, 1), 9);
		}
		else
		{
			SCHOLARLY.setInactive();
			SAVANT.setInactive();
			AIR.setInactive();
			EARTH.setInactive();
			INFUSED.setInactive();
			PURE.setInactive();
			FLUX.setInactive();
			NODE.setInactive();
			REJUVENATING.setInactive();
			BRAINY.setInactive();
			GOSSAMER.setInactive();
			WISPY.setInactive();
			BATTY.setInactive();
			CHICKEN.setInactive();
			BEEF.setInactive();
			PORK.setInactive();
		}
		
		if (EquivalentExchangeHelper.isActive())
		{
			MINIUM.addSpecialty(new ItemStack(Config.eeMinuimShard), 6);
		}
		else
		{
			MINIUM.setInactive();
		}
		
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
		
		
		ESOTERIC.addProduct(Config.combs.getStackForType(CombType.OCCULT), 20)
			.setGenome(BeeGenomeManager.getTemplateEsoteric())
			.register();		
		MYSTERIOUS.addProduct(Config.combs.getStackForType(CombType.OCCULT), 25)
			.setGenome(BeeGenomeManager.getTemplateMysterious())
			.register();		
		ARCANE.addProduct(Config.combs.getStackForType(CombType.OCCULT), 30)
			.addSpecialty(Config.drops.getStackForType(DropType.ENCHANTED, 1), 9)
			.setGenome(BeeGenomeManager.getTemplateArcane())
			.register();		
		CHARMED.addProduct(Config.combs.getStackForType(CombType.OTHERWORLDLY), 20)
			.setGenome(BeeGenomeManager.getTemplateCharmed())
			.register();		
		ENCHANTED.addProduct(Config.combs.getStackForType(CombType.OTHERWORLDLY), 30)
			.setGenome(BeeGenomeManager.getTemplateEnchanted())
			.register();		
		SUPERNATURAL.addProduct(Config.combs.getStackForType(CombType.OTHERWORLDLY), 40)
			.addSpecialty(Config.pollen.getStackForType(PollenType.UNUSUAL), 8)
			.setGenome(BeeGenomeManager.getTemplateSupernatural())
			.register();		
		PUPIL.addProduct(Config.combs.getStackForType(CombType.PAPERY), 20)
			.setGenome(BeeGenomeManager.getTemplatePupil())
			.register();
		SCHOLARLY.addProduct(Config.combs.getStackForType(CombType.PAPERY), 25)
			.addSpecialty(Config.miscResources.getStackForType(ResourceType.LORE_FRAGMENT), 2)
			.setGenome(BeeGenomeManager.getTemplateScholarly())
			.register();
		SAVANT.addProduct(Config.combs.getStackForType(CombType.PAPERY), 40)
			.addSpecialty(Config.miscResources.getStackForType(ResourceType.LORE_FRAGMENT), 5)
			.setGenome(BeeGenomeManager.getTemplateSavant())
			.register();
		STARK.addProduct(Config.combs.getStackForType(CombType.STARK), 10)
			.setGenome(BeeGenomeManager.getTemplateStark())
			.register();
		AIR.addProduct(Config.combs.getStackForType(CombType.AIRY), 9)
			.setGenome(BeeGenomeManager.getTemplateAir())
			.register();		
		FIRE.addProduct(Config.combs.getStackForType(CombType.FIREY), 15)
			.addSpecialty(new ItemStack(Item.blazePowder), 4).setGenome(BeeGenomeManager.getTemplateFire())
			.register();		
		WATER.addProduct(Config.combs.getStackForType(CombType.WATERY), 20)
			.addSpecialty(new ItemStack(Block.ice), 1)
			.addSpecialty(new ItemStack(Config.fPollen, 1, ForestryHelper.Pollen.CRYSTALLINE.ordinal()), 5)
			.setGenome(BeeGenomeManager.getTemplateWater())
			.register();		
		EARTH.addProduct(Config.combs.getStackForType(CombType.EARTHY), 30)
			.setGenome(BeeGenomeManager.getTemplateEarth())
			.register();		
		INFUSED.addProduct(Config.combs.getStackForType(CombType.INFUSED), 20)
			.setGenome(BeeGenomeManager.getTemplateInfused())
			.register();		
		AWARE.addProduct(Config.combs.getStackForType(CombType.INTELLECT), 18)
			.setGenome(BeeGenomeManager.getTemplateAware())
			.register();
		VIS.addProduct(Config.combs.getStackForType(CombType.INTELLECT), 25)
			.setGenome(BeeGenomeManager.getTemplateVis())
			.register();		
		PURE.addProduct(Config.combs.getStackForType(CombType.INTELLECT), 20)
			.setGenome(BeeGenomeManager.getTemplatePure())
			.register();
		FLUX.addProduct(Config.combs.getStackForType(CombType.INTELLECT), 20)
			.setGenome(BeeGenomeManager.getTemplateFlux())
			.register();
		NODE.addProduct(Config.combs.getStackForType(CombType.INTELLECT), 20)
			.setGenome(BeeGenomeManager.getTemplateNode())
			.register();
		REJUVENATING.addProduct(Config.combs.getStackForType(CombType.INTELLECT), 20)
			.setGenome(BeeGenomeManager.getTemplateRejuvinating())
			.register();
		SKULKING.addProduct(Config.combs.getStackForType(CombType.SKULKING), 10)
			.setGenome(BeeGenomeManager.getTemplateSkulking())
			.register();
		BRAINY.addProduct(Config.combs.getStackForType(CombType.SKULKING), 10)
			.addProduct(new ItemStack(Item.rottenFlesh), 6)
			.setGenome(BeeGenomeManager.getTemplateBrainy())
			.register();		
		BATTY.addProduct(Config.combs.getStackForType(CombType.SKULKING), 10)
			.addSpecialty(new ItemStack(Item.gunpowder), 4)
			.setGenome(BeeGenomeManager.getTemplateBatty())
			.register();	
		GOSSAMER.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.SILKY.ordinal()), 15)
			.setGenome(BeeGenomeManager.getTemplateGossamer())
			.register();		
		WISPY.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.SILKY.ordinal()), 22)
			.addSpecialty(new ItemStack(Config.fCraftingResource, 1, ForestryHelper.CraftingMaterial.SILK_WISP.ordinal()), 4)
			.setGenome(BeeGenomeManager.getTemplateWispy())
			.register();		
		GHASTLY.addProduct(Config.combs.getStackForType(CombType.SKULKING), 8)
			.addSpecialty(new ItemStack(Item.ghastTear), 2)
			.setGenome(BeeGenomeManager.getTemplateGhastly())
			.register();		
		TIMELY.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.DRIPPING.ordinal()), 25)
			.addProduct(Config.pollen.getStackForType(PollenType.PHASED), 10)
			.setGenome(BeeGenomeManager.getTemplateTimely())
			.register();		
		LORDLY.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.MYSTERIOUS.ordinal()), 5)
			.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.DRIPPING.ordinal()), 25)
			.addProduct(Config.pollen.getStackForType(PollenType.PHASED), 15)
			.setGenome(BeeGenomeManager.getTemplateLordly())
			.register();		
		DOCTORAL.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.MYSTERIOUS.ordinal()), 10)
			.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.DRIPPING.ordinal()), 25)
			.addProduct(Config.pollen.getStackForType(PollenType.PHASED), 19)
			.addSpecialty(new ItemStack(Config.jellyBaby), 7)
			.setGenome(BeeGenomeManager.getTemplateDoctoral())
			.register();
		MINIUM.addProduct(Config.combs.getStackForType(CombType.OCCULT), 16)
			.setGenome(BeeGenomeManager.getTemplateMinium())
			.register();
		SPIRIT.addProduct(Config.combs.getStackForType(CombType.SOUL), 13)
			.setGenome(BeeGenomeManager.getTemplateSpirit())
			.register();
		SOUL.addProduct(Config.combs.getStackForType(CombType.SOUL), 24)
			.setGenome(BeeGenomeManager.getTemplateSoul())
			.register();
		IRON.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.addSpecialty(Config.nuggets.getStackForType(NuggetType.IRON), 8)
			.setGenome(BeeGenomeManager.getTemplateIron())
			.register();		
		GOLD.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.addSpecialty(new ItemStack(Item.goldNugget, 1), 6)
			.setGenome(BeeGenomeManager.getTemplateGold())
			.register();		
		COPPER.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.addSpecialty(Config.nuggets.getStackForType(NuggetType.COPPER), 9)
			.setGenome(BeeGenomeManager.getTemplateCopper())
			.register();
		TIN.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.addSpecialty(Config.nuggets.getStackForType(NuggetType.TIN), 9)
			.setGenome(BeeGenomeManager.getTemplateTin())
			.register();
		SILVER.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.addSpecialty(Config.nuggets.getStackForType(NuggetType.SILVER), 6)
			.setGenome(BeeGenomeManager.getTemplateSilver())
			.register();
		LEAD.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.addSpecialty(Config.nuggets.getStackForType(NuggetType.LEAD), 7)
			.setGenome(BeeGenomeManager.getTemplateLead())
			.register();
		DIAMOND.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.addSpecialty(Config.nuggets.getStackForType(NuggetType.DIAMOND), 3)
			.setGenome(BeeGenomeManager.getTemplateDiamond())
			.register();
		EMERALD.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.addSpecialty(Config.nuggets.getStackForType(NuggetType.EMERALD), 2)
			.setGenome(BeeGenomeManager.getTemplateEmerald())
			.register();
		CHICKEN.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.setGenome(BeeGenomeManager.getTemplateChicken())
			.register();
		BEEF.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.setGenome(BeeGenomeManager.getTemplateBeef())
			.register();
		PORK.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.HONEY.ordinal()), 10)
			.setGenome(BeeGenomeManager.getTemplatePork())
			.register();
	}
	
	private String binomial;
	private String authority;
	private int bodyType;
	private int primaryColour;
	private int secondaryColour;
	private EnumTemperature temperature;
	private EnumHumidity humidity;
	private boolean hasEffect;
	private boolean isSecret;
	private boolean isCounted;
	private boolean isActive;
	private IClassification branch;
	private HashMap products;
	private HashMap specialty;
	private IAllele genomeTemplate[];
	private String uid;
	private boolean dominant;
	
	@SideOnly(Side.CLIENT)
	private Icon[][] icons;
	
	private final static boolean defaultSecretSetting = true;
	
	private BeeSpecies(String speciesName, String genusName, IClassification classification, int firstColour, EnumTemperature preferredTemp, EnumHumidity preferredHumidity, boolean hasGlowEffect, boolean isSpeciesDominant)
	{
		this(speciesName, genusName, classification, 0, firstColour, 0xFF6E0D, preferredTemp, preferredHumidity, hasGlowEffect, defaultSecretSetting, true, isSpeciesDominant);
	}

	private BeeSpecies(String speciesName, String genusName, IClassification classification, int firstColour, int secondColour, EnumTemperature preferredTemp, EnumHumidity preferredHumidity, boolean hasGlowEffect, boolean isSpeciesDominant)
	{
		this(speciesName, genusName, classification, 0, firstColour, secondColour, preferredTemp, preferredHumidity, hasGlowEffect, defaultSecretSetting, true, isSpeciesDominant);
	}

	private BeeSpecies(String speciesName, String genusName, IClassification classification, int body, int firstColour, int secondColour, EnumTemperature preferredTemp, EnumHumidity preferredHumidity, boolean hasGlowEffect, boolean isSpeciesSecret, boolean isSpeciesCounted, boolean isSpeciesDominant)
	{
		this.uid = "thaumicbees.species" + speciesName;
		this.dominant = isSpeciesDominant;
		AlleleManager.alleleRegistry.registerAllele(this);
		binomial = genusName;
		authority = "MysteriousAges";
		bodyType = body;
		primaryColour = firstColour;
		secondaryColour = secondColour;
		temperature = preferredTemp;
		humidity = preferredHumidity;
		hasEffect = hasGlowEffect;
		isSecret = isSpeciesSecret;
		isCounted = isSpeciesCounted;
		products = new HashMap();
		specialty = new HashMap();
		this.branch = classification;
		this.branch.addMemberSpecies(this);
		this.isActive = true;
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
		products.put(produce, Integer.valueOf(percentChance));
		return this;
	}

	public BeeSpecies addSpecialty(ItemStack produce, int percentChance)
	{
		specialty.put(produce, Integer.valueOf(percentChance));
		return this;
	}

	public ItemStack getBeeItem(EnumBeeType beeType)
	{
		return BeeManager.beeInterface.getBeeStack(BeeManager.beeInterface.getBee(null, BeeManager.beeInterface.templateAsGenome(genomeTemplate)), beeType);
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
		return specialty;
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
	public boolean isJubilant(IBeeGenome genome, IBeeHousing housing)
	{
		return true;
	}

	private BeeSpecies register()
	{
		BeeManager.breedingManager.registerBeeTemplate(this.getGenome());
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

	public void registerItemIcons(IconRegister itemMap)
	{
		this.icons = new Icon[EnumBeeType.values().length][3];
		Icon outline = itemMap.registerIcon(ForestryHelper.Name + ":bees/outline");
		Icon body1 = itemMap.registerIcon(ForestryHelper.Name + ":bees/outline");

		for (int i = 0; i < EnumBeeType.values().length; i++) {
			if(EnumBeeType.values()[i] == EnumBeeType.NONE)
				continue;
			
			icons[i][0] = outline;
			icons[i][1] = body1;
			icons[i][2] = itemMap.registerIcon(ForestryHelper.Name + ":bees/" + EnumBeeType.values()[i].toString().toLowerCase(Locale.ENGLISH) + ".body2");
		}
	}

	/// --------- Unused Functions ---------------------------------------------
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(short texUID)
	{
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerTerrainIcons(IconRegister terrainMap) { }
}
