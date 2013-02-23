package thaumicbees.bees;

import forestry.api.apiculture.*;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.ItemInterface;
import forestry.api.genetics.*;
import forestry.api.genetics.IClassification.EnumClassLevel;

import java.util.HashMap;

import cpw.mods.fml.common.registry.LanguageRegistry;

import thaumicbees.item.types.CombType;
import thaumicbees.item.types.DropType;
import thaumicbees.item.types.PollenType;
import thaumicbees.item.types.ResourceType;
import thaumicbees.main.Config;
import thaumicbees.main.utils.LocalizationManager;
import thaumicbees.main.utils.compat.ForestryHelper;
import thaumicbees.main.utils.compat.ThaumcraftHelper;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.world.World;

public enum BeeSpecies implements IAlleleBeeSpecies
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
	SKULKING("Skulking", "malevolens",
			BeeClassification.SKULKING, 0x524827, 0xe15236, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	BRAINY("Brainy", "cerebrum",
			BeeClassification.SKULKING, 0x83FF70, 0xe15236, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	GOSSAMER("Gossamer", "perlucidus",
			BeeClassification.SKULKING, 0x183f66, 0xe15236, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	WISPY("Wispy", "umbrabilis",
			BeeClassification.SKULKING, 0x9cb8d5, 0xe15236, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	BATTY("Batty", "chiroptera",
			BeeClassification.SKULKING, 0x27350d, 0xe15236, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false,true),
	GHASTLY("Ghastly", "pallens",
			BeeClassification.SKULKING, 0xccccee, 0xbf877c, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	TIMELY("Timely", "gallifreis",
			BeeClassification.TIME, 0xC6AF86, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, true),
	LORDLY("Lordly", "rassilonis",
			BeeClassification.TIME, 0xC6AF86, 0x8E0213, EnumTemperature.NORMAL, EnumHumidity.NORMAL, false, false),
	DOCTORAL("Doctoral", "medicus qui",
			BeeClassification.TIME, 0xDDE5FC, 0x4B6E8C, EnumTemperature.NORMAL, EnumHumidity.NORMAL, true, false),
	;
	
	public static void setupBeeSpecies()
	{
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
			.addSpecialty(new ItemStack(Config.tcNuggets, 1, ThaumcraftHelper.NuggetType.QUICKSILVER.ordinal()), 8)
			.setGenome(BeeGenomeManager.getTemplateAir())
			.register();		
		FIRE.addProduct(Config.combs.getStackForType(CombType.FIREY), 15)
			.addSpecialty(new ItemStack(Item.blazePowder), 8)
			.setGenome(BeeGenomeManager.getTemplateFire())
			.register();		
		WATER.addProduct(Config.combs.getStackForType(CombType.WATERY), 20)
			.addSpecialty(new ItemStack(Block.ice), 1)
			.addSpecialty(new ItemStack(Config.fPollen, 1, ForestryHelper.Pollen.CRYSTALLINE.ordinal()), 5)
			.setGenome(BeeGenomeManager.getTemplateWater())
			.register();		
		EARTH.addProduct(Config.combs.getStackForType(CombType.EARTHY), 30)
			.addSpecialty(new ItemStack(Config.tcMiscResource, 1, ThaumcraftHelper.MiscResource.AMBER.ordinal()), 6)
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
		SKULKING.addProduct(Config.combs.getStackForType(CombType.SKULKING), 10)
			.setGenome(BeeGenomeManager.getTemplateSkulking())
			.register();		
		BRAINY.addProduct(Config.combs.getStackForType(CombType.SKULKING), 10)
			.addProduct(new ItemStack(Item.rottenFlesh), 6)
			.addSpecialty(new ItemStack(Config.tcMiscResource,  1, ThaumcraftHelper.MiscResource.ZOMBIE_BRAIN.ordinal()), 2)
			.setGenome(BeeGenomeManager.getTemplateBrainy())
			.register();		
		GOSSAMER.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.SILKY.ordinal()), 15)
			.setGenome(BeeGenomeManager.getTemplateGossamer())
			.register();		
		WISPY.addProduct(new ItemStack(Config.fBeeComb, 1, ForestryHelper.Comb.SILKY.ordinal()), 22)
			.addSpecialty(new ItemStack(Config.fCraftingResource, 1, ForestryHelper.CraftingMaterial.SILK_WISP.ordinal()), 4)
			.setGenome(BeeGenomeManager.getTemplateWispy())
			.register();		
		BATTY.addProduct(Config.combs.getStackForType(CombType.SKULKING), 10)
			.addSpecialty(new ItemStack(Item.gunpowder), 4)
			.setGenome(BeeGenomeManager.getTemplateBatty())
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
	private IClassification branch;
	private HashMap products;
	private HashMap specialty;
	private IAllele genomeTemplate[];
	private String uid;
	private boolean dominant;
	
	private BeeSpecies(String speciesName, String genusName, IClassification classification, int firstColour, EnumTemperature preferredTemp, EnumHumidity preferredHumidity, boolean hasGlowEffect, boolean isSpeciesDominant)
	{
		this(speciesName, genusName, classification, 0, firstColour, 0xFF6E0D, preferredTemp, preferredHumidity, hasGlowEffect, true, true, isSpeciesDominant);
	}

	private BeeSpecies(String speciesName, String genusName, IClassification classification, int firstColour, int secondColour, EnumTemperature preferredTemp, EnumHumidity preferredHumidity, boolean hasGlowEffect, boolean isSpeciesDominant)
	{
		this(speciesName, genusName, classification, 0, firstColour, secondColour, preferredTemp, preferredHumidity, hasGlowEffect, true, true, isSpeciesDominant);
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

	public String getName()
	{
		return LocalizationManager.getLocalizedString(getUID());
	}

	public String getDescription()
	{
		return LocalizationManager.getLocalizedString(getUID() + ".description");
	}

	public int getBodyType()
	{
		return bodyType;
	}

	public int getPrimaryColor()
	{
		return primaryColour;
	}

	public int getSecondaryColor()
	{
		return secondaryColour;
	}

	public EnumTemperature getTemperature()
	{
		return temperature;
	}

	public EnumHumidity getHumidity()
	{
		return humidity;
	}

	public boolean hasEffect()
	{
		return hasEffect;
	}

	public boolean isSecret()
	{
		return isSecret;
	}

	public boolean isCounted()
	{
		return isCounted;
	}

	public String getBinomial()
	{
		return binomial;
	}

	public String getAuthority()
	{
		return authority;
	}

	public IClassification getBranch()
	{
		return this.branch;
	}

	public HashMap getProducts()
	{
		return products;
	}

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
	public boolean isJubilant(World world, int biomeid, int x, int y, int z)
	{
		return true;
	}
	
	private BeeSpecies register()
	{
		BeeManager.breedingManager.registerBeeTemplate(this.getGenome());
		return this;
	}

	/**
	 * @deprecated Method getAchievement is deprecated
	 */

	public Achievement getAchievement()
	{
		return null;
	}
}
