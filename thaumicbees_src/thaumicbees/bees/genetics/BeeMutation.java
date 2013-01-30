package thaumicbees.bees.genetics;

import forestry.api.apiculture.*;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import java.util.ArrayList;

import thaumcraft.api.AuraNode;
import thaumcraft.api.EnumNodeType;
import thaumcraft.api.ThaumcraftApi;
import thaumicbees.bees.MoonPhase;

public class BeeMutation implements IBeeMutation
{

	public static BeeMutation Esoteric;
	public static BeeMutation Esoteric1;
	public static BeeMutation Mysterious;
	public static BeeMutation Mysterious1;
	public static BeeMutation Arcane;
	public static BeeMutation Charmed;
	public static BeeMutation Enchanted;
	public static BeeMutation Supernatural;
	public static BeeMutation Stark;
	public static BeeMutation Pupil;
	public static BeeMutation Scholarly;
	public static BeeMutation Savant;
	public static BeeMutation Aware;
	public static BeeMutation Vis;
	public static BeeMutation Vis1;
	public static BeeMutation Pure;
	public static BeeMutation Flux;
	public static BeeMutation Node;
	public static BeeMutation Node1;
	public static BeeMutation Skulking;
	public static BeeMutation Brainy;
	public static BeeMutation Gossamer;
	public static BeeMutation Wispy;
	public static BeeMutation Batty;
	public static BeeMutation Ghastly;
	
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

	public BeeMutation(IAlleleBeeSpecies species0, IAlleleBeeSpecies species1, BeeSpecies resultSpecies, int percentChance)
	{
		this.parent1 = species0;
		this.parent2 = species1;
		this.mutationTemplate = resultSpecies.getGenome();
		this.baseChance = percentChance;
		this.isSecret = false;
		this.isMoonRestricted = false;
		this.moonPhaseMutationBonus = -1f;
		this.nodeType = null;
		
		BeeManager.breedingManager.registerBeeMutation(this);
	}

	public int getChance(IBeeHousing housing, IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1)
	{
		int finalChance = 0;
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
				if (nodeId >= 0 && this.nodeType != null)
				{
					AuraNode node = ThaumcraftApi.getNodeCopy(nodeId);
					if (node.type != this.nodeType)
					{
						chance = 0;
					}
				}
				else
				{
					chance = 0;
				}
			}
			
			finalChance = Math.round(chance
					* housing.getMutationModifier((IBeeGenome) genome0,
							(IBeeGenome) genome1)
					* BeeManager.breedingManager.getBeekeepingMode(housing.getWorld())
							.getMutationModifier((IBeeGenome) genome0,
									(IBeeGenome) genome1));
		}
		
		return finalChance;
	}

	public IAllele getAllele0()
	{
		return parent1;
	}

	public IAllele getAllele1()
	{
		return parent2;
	}

	public IAllele[] getTemplate()
	{
		return mutationTemplate;
	}

	public int getBaseChance()
	{
		return baseChance;
	}

	public boolean isPartner(IAllele allele)
	{
		return parent1.getUID().equals(allele.getUID()) || parent2.getUID().equals(allele.getUID());
	}

	public IAllele getPartner(IAllele allele)
	{
		IAllele val = parent1;
		if (val.getUID().equals(allele.getUID()))
			val = parent2;
		return val;
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
