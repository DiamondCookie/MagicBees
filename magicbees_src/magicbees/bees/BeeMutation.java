package magicbees.bees;

import forestry.api.apiculture.*;
import forestry.api.core.BlockInterface;
import forestry.api.core.ItemInterface;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import java.util.ArrayList;
import java.util.Collection;

import magicbees.main.MagicBees;
import magicbees.main.utils.MoonPhase;
import magicbees.main.utils.compat.ArsMagicaHelper;
import magicbees.main.utils.compat.EquivalentExchangeHelper;
import magicbees.main.utils.compat.ExtraBeesHelper;
import magicbees.main.utils.compat.ForestryHelper;
import magicbees.main.utils.compat.ThaumcraftHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.FMLLog;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aura.AuraNode;
import thaumcraft.api.aura.EnumNodeType;

public class BeeMutation implements IBeeMutation
{	
	public static void setupMutations()
	{
		IAlleleBeeSpecies baseA, baseB;
		
		// Forestry + These -> Common
		
		BeeSpecies[] magicMundane = new BeeSpecies[] { BeeSpecies.MYSTICAL, BeeSpecies.SORCEROUS, BeeSpecies.UNUSUAL, BeeSpecies.ATTUNED };
		String[] forestryMundane = new String[] { "Forest", "Meadows", "Modest", "Wintry", "Tropical", "Marshy" };
		String[] binnieMundane = new String[] { "marble", "rocky", "water", "embittered" };
		
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
		new BeeMutation(BeeSpecies.CHARMED, BeeSpecies.ENCHANTED, BeeSpecies.SUPERNATURAL, 8);
		
		new BeeMutation(BeeSpecies.ARCANE, BeeSpecies.SUPERNATURAL, BeeSpecies.ETHEREAL, 7);
		
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
	private EnumNodeType nodeType;
	private double nodeRange;
	private boolean requiresBlock;
	private int requiredBlockId;
	private int requiredBlockMeta;
	private String requiredBlockOreDictEntry;
	
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
				int nodeId = ThaumcraftApi.getClosestAuraWithinRange(housing.getWorld(),
						housing.getXCoord(), housing.getYCoord(), housing.getZCoord(), this.nodeRange);
				if (nodeId >= 0)
				{
					if (this.nodeType != null)
					{
						// Needs a _specific_ variety of node
						AuraNode node = ThaumcraftApi.getNodeCopy(nodeId);
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
		return new ArrayList<String>(0);
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
	
	public BeeMutation setAuraNodeTypeRequired(double range, EnumNodeType type)
	{
		this.nodeType = type;
		
		return this.setAuraNodeRequired(range);
	}
}
