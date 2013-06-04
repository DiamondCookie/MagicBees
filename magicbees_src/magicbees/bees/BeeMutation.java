package magicbees.bees;

import java.util.ArrayList;
import java.util.Collection;

import thaumcraft.api.aura.EnumNodeType;

import magicbees.main.utils.LocalizationManager;
import magicbees.main.utils.MoonPhase;
import magicbees.main.utils.compat.ArsMagicaHelper;
import magicbees.main.utils.compat.EquivalentExchangeHelper;
import magicbees.main.utils.compat.ExtraBeesHelper;
import magicbees.main.utils.compat.ForestryHelper;
import magicbees.main.utils.compat.ThaumcraftHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLLog;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeMutation;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.core.BlockInterface;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;

public class BeeMutation implements IBeeMutation
{	
	public static void setupMutations()
	{
		IAlleleBeeSpecies baseA, baseB;
		BeeMutation mutation;
		
		// Forestry + These -> Common
		
		BeeSpecies[] magicMundane = new BeeSpecies[] { BeeSpecies.MYSTICAL, BeeSpecies.SORCEROUS, BeeSpecies.UNUSUAL, BeeSpecies.ATTUNED };
		String[] forestryMundane = new String[] { "Forest", "Meadows", "Modest", "Wintry", "Tropical", "Marshy" };
		String[] binnieMundane = new String[] { "marble", "rock", "water", "basalt" };
		
		for (BeeSpecies species : magicMundane)
		{
			for (String str : forestryMundane)
			{
				new BeeMutation(species, Allele.getBaseSpecies(str), ForestryHelper.getTemplateForestryForSpecies("Common"), 15);
			}
			if (ExtraBeesHelper.isActive())
			{
				for (String str : binnieMundane)
				{
					FMLLog.info("Registering %s", str);
					new BeeMutation(species, Allele.getExtraSpecies(str), ForestryHelper.getTemplateForestryForSpecies("Common"), 15);
				}
			}
			new BeeMutation(species, Allele.getBaseSpecies("Common"), ForestryHelper.getTemplateForestryForSpecies("Cultivated"), 12);
			new BeeMutation(species, Allele.getBaseSpecies("Cultivated"), BeeSpecies.ELDRITCH, 12);
		}
		
		new BeeMutation(Allele.getBaseSpecies("Cultivated"), BeeSpecies.ELDRITCH, BeeSpecies.ESOTERIC, 10);
		new BeeMutation(BeeSpecies.ELDRITCH, BeeSpecies.ESOTERIC, BeeSpecies.MYSTERIOUS, 8);
		new BeeMutation(BeeSpecies.ESOTERIC, BeeSpecies.MYSTERIOUS, BeeSpecies.ARCANE, 8);
		
		new BeeMutation(Allele.getBaseSpecies("Cultivated"), BeeSpecies.ELDRITCH, BeeSpecies.CHARMED, 10);
		new BeeMutation(BeeSpecies.ELDRITCH, BeeSpecies.CHARMED, BeeSpecies.ENCHANTED, 8);
		new BeeMutation(BeeSpecies.CHARMED, BeeSpecies.ENCHANTED, BeeSpecies.SUPERNATURAL, 8)
			.setMoonPhaseRestricted(MoonPhase.FULL, MoonPhase.WANING_GIBBOUS);
		
		new BeeMutation(BeeSpecies.ARCANE, BeeSpecies.SUPERNATURAL, BeeSpecies.ETHEREAL, 7)
			.setMoonPhaseRestricted(MoonPhase.FULL, MoonPhase.FULL);
		
		new BeeMutation(BeeSpecies.ETHEREAL, BeeSpecies.ATTUNED, BeeSpecies.AWARE, 10);
		new BeeMutation(BeeSpecies.ETHEREAL, BeeSpecies.AWARE, BeeSpecies.SPIRIT, 8);
		new BeeMutation(BeeSpecies.ATTUNED, BeeSpecies.AWARE, BeeSpecies.SPIRIT, 8);
		new BeeMutation(BeeSpecies.AWARE, BeeSpecies.SPIRIT, BeeSpecies.SOUL, 7);
		
		new BeeMutation(Allele.getBaseSpecies("Monastic"), BeeSpecies.ARCANE, BeeSpecies.PUPIL, 10);
		new BeeMutation(BeeSpecies.ARCANE, BeeSpecies.PUPIL, BeeSpecies.SCHOLARLY, 8);
		new BeeMutation(BeeSpecies.PUPIL, BeeSpecies.SCHOLARLY, BeeSpecies.SAVANT, 6);
		
		new BeeMutation(Allele.getBaseSpecies("Imperial"), BeeSpecies.ETHEREAL, BeeSpecies.TIMELY, 8);
		new BeeMutation(Allele.getBaseSpecies("Imperial"), BeeSpecies.TIMELY, BeeSpecies.LORDLY, 8);
		new BeeMutation(BeeSpecies.TIMELY, BeeSpecies.LORDLY, BeeSpecies.DOCTORAL, 7);
		
		new BeeMutation(BeeSpecies.INFERNAL, BeeSpecies.ELDRITCH, BeeSpecies.HATEFUL, 9)
			.setBiomeRequired(BiomeDictionary.Type.NETHER);
		new BeeMutation(BeeSpecies.INFERNAL, BeeSpecies.HATEFUL, BeeSpecies.SPITEFUL, 7)
			.setBiomeRequired(BiomeDictionary.Type.NETHER);
		new BeeMutation(Allele.getBaseSpecies("Demonic"), BeeSpecies.SPITEFUL, BeeSpecies.WITHERING, 6)
			.setBiomeRequired(BiomeDictionary.Type.NETHER);
				
		new BeeMutation(Allele.getBaseSpecies("Modest"), BeeSpecies.ELDRITCH, BeeSpecies.SKULKING, 12);
		new BeeMutation(Allele.getBaseSpecies("Tropical"), BeeSpecies.SKULKING, BeeSpecies.SPIDERY, 10);
		new BeeMutation(BeeSpecies.SKULKING, BeeSpecies.ETHEREAL, BeeSpecies.GHASTLY, 9);
		new BeeMutation(BeeSpecies.SKULKING, BeeSpecies.HATEFUL, BeeSpecies.SMOULDERING, 7)
			.setBiomeRequired(BiomeDictionary.Type.NETHER);
		
		if (BeeSpecies.COPPER.isActive())
		{
			new BeeMutation(Allele.getBaseSpecies("Industrious"), Allele.getBaseSpecies("Meadows"), BeeSpecies.COPPER, 12)
				.setBlockRequired("blockCopper");
		}
		if (BeeSpecies.TIN.isActive())
		{
			new BeeMutation(Allele.getBaseSpecies("Industrious"), Allele.getBaseSpecies("Forest"), BeeSpecies.TIN, 12)
				.setBlockRequired("blockTin");
		}
		new BeeMutation(Allele.getBaseSpecies("Common"), Allele.getBaseSpecies("Industrious"), BeeSpecies.IRON, 10)
			.setBlockRequired(Block.blockIron);
		if (BeeSpecies.LEAD.isActive())
		{
			baseA = (BeeSpecies.TIN.isActive()) ? BeeSpecies.TIN : (BeeSpecies.COPPER.isActive()) ? BeeSpecies.COPPER : BeeSpecies.IRON;
			mutation = new BeeMutation(baseA, Allele.getBaseSpecies("Common"), BeeSpecies.LEAD, 10);
			if (OreDictionary.getOres("blockLead").size() > 0)
			{
				mutation.setBlockRequired("blockLead");
			}
		}
		if (BeeSpecies.SILVER.isActive())
		{
			mutation = new BeeMutation(Allele.getBaseSpecies("Imperial"), Allele.getBaseSpecies("Modest"), BeeSpecies.SILVER, 8);
			if (OreDictionary.getOres("blockSilver").size() > 0)
			{
				mutation.setBlockRequired("blockSilver");
			}
		}
		baseA = (BeeSpecies.EE_MINIUM.isActive()) ? BeeSpecies.EE_MINIUM : Allele.getBaseSpecies("Imperial");
		baseB = (BeeSpecies.LEAD.isActive()) ? BeeSpecies.LEAD : BeeSpecies.IRON;
		new BeeMutation(baseA, baseB, BeeSpecies.GOLD, 8)
			.setBlockRequired(Block.blockGold);
		new BeeMutation(Allele.getBaseSpecies("Austere"), BeeSpecies.GOLD, BeeSpecies.DIAMOND, 7)
			.setBlockRequired(Block.blockDiamond);
		baseA = (BeeSpecies.SILVER.isActive()) ? BeeSpecies.SILVER : Allele.getBaseSpecies("Imperial");
		new BeeMutation(Allele.getBaseSpecies("Austere"), baseA, BeeSpecies.EMERALD, 6)
			.setBlockRequired(Block.blockEmerald);
		new BeeMutation(Allele.getBaseSpecies("Rural"), BeeSpecies.COPPER, BeeSpecies.APATITE, 12)
			.setBlockAndMetaRequired(BlockInterface.getBlock("resources").itemID, 0);
		
		if (ThaumcraftHelper.isActive())
		{
			new BeeMutation(BeeSpecies.ELDRITCH, BeeSpecies.ETHEREAL, BeeSpecies.TC_VIS, 10)
				.setAuraNodeRequired(75);
			new BeeMutation(Allele.getBaseSpecies("Demonic"), BeeSpecies.TC_VIS, BeeSpecies.TC_FLUX, 8)
				.setAuraNodeTypeRequired(50, thaumcraft.api.aura.EnumNodeType.DARK);
			new BeeMutation(Allele.getBaseSpecies("Demonic"), BeeSpecies.TC_VIS, BeeSpecies.TC_FLUX, 9)
				.setAuraNodeTypeRequired(200, thaumcraft.api.aura.EnumNodeType.UNSTABLE);
			new BeeMutation(BeeSpecies.TC_VIS, BeeSpecies.TC_FLUX, BeeSpecies.TC_ATTRACT, 6)
				.setAuraNodeTypeRequired(20, EnumNodeType.UNSTABLE);
			new BeeMutation(Allele.getBaseSpecies("Imperial"), BeeSpecies.TC_VIS, BeeSpecies.TC_REJUVENATING, 8)
				.setAuraNodeRequired(25);
			new BeeMutation(BeeSpecies.TC_VIS, BeeSpecies.TC_REJUVENATING, BeeSpecies.TC_PURE, 6)
				.setAuraNodeTypeRequired(10, thaumcraft.api.aura.EnumNodeType.PURE);
			
			new BeeMutation(BeeSpecies.SKULKING, BeeSpecies.WINDY, BeeSpecies.TC_BATTY, 9);
			new BeeMutation(BeeSpecies.SKULKING, BeeSpecies.PUPIL, BeeSpecies.TC_BRAINY, 9);
			new BeeMutation(BeeSpecies.ETHEREAL, BeeSpecies.GHASTLY, BeeSpecies.TC_WISPY, 9);
		}
		
		if (ArsMagicaHelper.isActive())
		{
			new BeeMutation(BeeSpecies.ARCANE, BeeSpecies.ETHEREAL, BeeSpecies.AM_ESSENCE, 10);
			new BeeMutation(BeeSpecies.ARCANE, BeeSpecies.AM_ESSENCE, BeeSpecies.AM_QUINTESSENCE, 7);
			
			new BeeMutation(BeeSpecies.SKULKING, BeeSpecies.AM_ESSENCE, BeeSpecies.AM_VORTEX, 8);
			new BeeMutation(BeeSpecies.SKULKING, BeeSpecies.GHASTLY, BeeSpecies.AM_WIGHT, 8);
		}
		
		if (EquivalentExchangeHelper.isActive())
		{
			new BeeMutation(Allele.getBaseSpecies("Common"), BeeSpecies.ELDRITCH, BeeSpecies.EE_MINIUM, 8);
		}
	}
	
	private IAllele parent1;
	private IAllele parent2;
	private IAllele mutationTemplate[];
	private int baseChance;
	private boolean isSecret;
	private boolean isMoonRestricted;
	private MoonPhase moonPhaseStart;
	private MoonPhase moonPhaseEnd;
	private float moonPhaseMutationBonus;
	private boolean nodeRequired;
	private thaumcraft.api.aura.EnumNodeType nodeType;
	private double nodeRange;
	private boolean requiresBlock;
	private int requiredBlockId;
	private int requiredBlockMeta;
	private String requiredBlockOreDictEntry;
	private BiomeDictionary.Type requiredBiomeType;
	
	public BeeMutation(IAlleleBeeSpecies species0, IAlleleBeeSpecies species1, BeeSpecies resultSpecies, int percentChance)
	{
		this(species0, species1, resultSpecies.getGenome(), percentChance);
	}

	public BeeMutation(IAlleleBeeSpecies species0, IAlleleBeeSpecies species1, IAllele[] resultSpeciesGenome, int percentChance)
	{
		this.parent1 = species0;
		this.parent2 = species1;
		this.mutationTemplate = resultSpeciesGenome;
		this.baseChance = percentChance;
		this.isSecret = false;
		this.isMoonRestricted = false;
		this.moonPhaseMutationBonus = -1f;
		this.nodeType = null;
		this.requiresBlock = false;
		this.requiredBlockMeta = OreDictionary.WILDCARD_VALUE;
		this.requiredBlockOreDictEntry = null;
		this.requiredBiomeType = null;
		
		Allele.beeRoot.registerMutation(this);
	}

	@Override
	public float getChance(IBeeHousing housing, IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1)
	{
		float finalChance = 0f;
		float chance = this.baseChance * 1f;
		
		if (this.arePartners(allele0, allele1))
		{
			// This mutation applies. Continue calculation.
			if (this.moonPhaseStart != null && this.moonPhaseEnd != null)
			{
				// Only occurs during the phases.
				if (this.isMoonRestricted && !MoonPhase.getMoonPhase(housing.getWorld()).isBetween(this.moonPhaseStart, this.moonPhaseEnd))
				{
					chance = 0;
				}
				else if (this.moonPhaseMutationBonus != -1f)
				{
					// There is a bonus to this mutation during moon phases...
					if (MoonPhase.getMoonPhase(housing.getWorld()).isBetween(this.moonPhaseStart, this.moonPhaseEnd))
					{
						chance = (int)(chance * this.moonPhaseMutationBonus);
					}
				}
			}
			
			if (this.nodeRequired)
			{
				int nodeId = thaumcraft.api.ThaumcraftApi.getClosestAuraWithinRange(housing.getWorld(),
						housing.getXCoord(), housing.getYCoord(), housing.getZCoord(), this.nodeRange);
				if (nodeId >= 0)
				{
					if (this.nodeType != null)
					{
						// Needs a _specific_ variety of node
						thaumcraft.api.aura.AuraNode node = thaumcraft.api.ThaumcraftApi.getNodeCopy(nodeId);
						if (node.type != this.nodeType)
						{
							chance = 0;
						}
					}
				}
				else
				{
					chance = 0;
				}
			}
			
			if (this.requiresBlock)
			{
				int blockId = housing.getWorld().getBlockId(housing.getXCoord(), housing.getYCoord() - 1, housing.getZCoord());
				int blockMeta = housing.getWorld().getBlockMetadata(housing.getXCoord(), housing.getYCoord() - 1, housing.getZCoord());
				
				if (this.requiredBlockOreDictEntry != null)
				{
					int dicId = OreDictionary.getOreID(new ItemStack(blockId, 1, blockMeta));
					if (dicId != -1)
					{
						if (!OreDictionary.getOreName(dicId).equals(this.requiredBlockOreDictEntry))
						{
							chance = 0;
						}
					}
					else
					{
						chance = 0;
					}
				}
				else if (this.requiredBlockId != blockId || (this.requiredBlockMeta != OreDictionary.WILDCARD_VALUE && this.requiredBlockMeta != blockMeta))
				{
					chance = 0;
				}
			}
			
			if (this.requiredBiomeType != null)
			{
				BiomeDictionary.Type[] types = BiomeDictionary.getTypesForBiome(housing.getWorld().getBiomeGenForCoords(housing.getXCoord(), housing.getZCoord()));
				boolean found = false;
				for (int i = 0; i < types.length; ++i)
				{
					if (this.requiredBiomeType == types[i])
					{
						found = true;
						break;
					}
				}
				if (!found)
				{
					chance = 0;
				}
			}
			
			finalChance = Math.round(chance
					* housing.getMutationModifier((IBeeGenome) genome0,
							(IBeeGenome) genome1, chance)
					* Allele.beeRoot.getBeekeepingMode(housing.getWorld())
							.getMutationModifier((IBeeGenome) genome0,
									(IBeeGenome) genome1, chance));
		}
		
		return finalChance;
	}

	@Override
	public IAllele getAllele0()
	{
		return parent1;
	}

	@Override
	public IAllele getAllele1()
	{
		return parent2;
	}

	@Override
	public IAllele[] getTemplate()
	{
		return mutationTemplate;
	}

	@Override
	public float getBaseChance()
	{
		return baseChance;
	}

	@Override
	public boolean isPartner(IAllele allele)
	{
		return parent1.getUID().equals(allele.getUID()) || parent2.getUID().equals(allele.getUID());
	}

	@Override
	public IAllele getPartner(IAllele allele)
	{
		IAllele val = parent1;
		if (val.getUID().equals(allele.getUID()))
			val = parent2;
		return val;
	}

	@Override
	public Collection<String> getSpecialConditions()
	{
		ArrayList<String> conditions = new ArrayList<String>();
		
		if (this.isMoonRestricted && moonPhaseStart != null && moonPhaseEnd != null)
		{
			if (moonPhaseStart != moonPhaseEnd)
			{
				conditions.add(String.format(LocalizationManager.getLocalizedString("research.requiresPhase"),
						moonPhaseStart.getLocalizedName(), moonPhaseEnd.getLocalizedName()));
			}
			else
			{
				conditions.add(String.format(LocalizationManager.getLocalizedString("research.requiresPhaseSingle"),
						moonPhaseStart.getLocalizedName()));
			}
		}
		if (this.nodeRequired)
		{
			if (this.nodeType != null)
			{
				conditions.add(LocalizationManager.getLocalizedString("research.requiresNode"));
			}
			else
			{
				conditions.add(String.format(LocalizationManager.getLocalizedString("research.requiresNodeSpecific"), 
						LocalizationManager.getLocalizedString("research.node." + this.nodeType.name().toLowerCase())));
			}
		}
		if (this.requiresBlock)
		{
			if (this.requiredBlockOreDictEntry != null)
			{
				ArrayList<ItemStack> ores = OreDictionary.getOres(this.requiredBlockOreDictEntry);
				if (ores.size() > 0)
				{
					conditions.add(String.format(LocalizationManager.getLocalizedString("research.requiresBlock"), ores.get(0).getDisplayName()));
				}
			}
			else
			{
				conditions.add(String.format(LocalizationManager.getLocalizedString("research.requiresBlock"), 
						new ItemStack(this.requiredBlockId, 1, this.requiredBlockMeta).getDisplayName()));
			}
		}
		if (this.requiredBiomeType != null)
		{
			String biomeName = this.requiredBiomeType.name().substring(0, 1) + this.requiredBiomeType.name().substring(1).toLowerCase();
			conditions.add(String.format(LocalizationManager.getLocalizedString("research.requiresBiome"), biomeName));
		}
		
		return conditions;
	}

	@Override
	public IBeeRoot getRoot()
	{
		return Allele.beeRoot;
	}
	
	public boolean arePartners(IAllele alleleA, IAllele alleleB)
	{
		return (this.parent1.getUID().equals(alleleA.getUID())) && this.parent2.getUID().equals(alleleB.getUID()) ||
				this.parent1.getUID().equals(alleleB.getUID()) && this.parent2.getUID().equals(alleleA.getUID());
	}
	
	public BeeMutation setSecret()
	{
		this.isSecret = true;
		
		return this;
	}

	public boolean isSecret()
	{
		return isSecret;
	}
	
	public BeeMutation setBlockRequired(int blockId)
	{
		this.requiresBlock = true;
		this.requiredBlockId = blockId;
		
		return this;
	}
	
	public BeeMutation setBlockRequired(Block block)
	{
		this.requiresBlock = true;
		this.requiredBlockId = block.blockID;
		
		return this;
	}
	
	public BeeMutation setBlockAndMetaRequired(int blockId, int meta)
	{
		this.requiresBlock = true;
		this.requiredBlockId = blockId;
		this.requiredBlockMeta = meta;
		
		return this;
	}
	
	public BeeMutation setBlockAndMetaRequired(Block block, int meta)
	{
		this.requiresBlock = true;
		this.requiredBlockId = block.blockID;
		this.requiredBlockMeta = meta;
		
		return this;
	}
	
	public BeeMutation setBlockRequired(String oreDictEntry)
	{
		this.requiresBlock = true;
		this.requiredBlockOreDictEntry = oreDictEntry;
		
		return this;
	}
	
	public BeeMutation setMoonPhaseRestricted(MoonPhase begin, MoonPhase end)
	{
		this.isMoonRestricted = true;
		this.moonPhaseStart = begin;
		this.moonPhaseEnd = end;
		
		return this;
	}
	
	public BeeMutation setMoonPhaseBonus(MoonPhase begin, MoonPhase end, float mutationBonus)
	{
		this.moonPhaseMutationBonus = mutationBonus;
		this.moonPhaseStart = begin;
		this.moonPhaseEnd = end;
		
		return this;
	}
	
	public BeeMutation setAuraNodeRequired(double range)
	{
		this.nodeRequired = true;
		this.nodeRange = range;
		
		return this;
	}
	
	public BeeMutation setAuraNodeTypeRequired(double range, thaumcraft.api.aura.EnumNodeType type)
	{
		this.nodeType = type;
		
		return this.setAuraNodeRequired(range);
	}
	
	public BeeMutation setBiomeRequired(BiomeDictionary.Type biomeType)
	{
		this.requiredBiomeType = biomeType;
		
		return this;
	}
}
