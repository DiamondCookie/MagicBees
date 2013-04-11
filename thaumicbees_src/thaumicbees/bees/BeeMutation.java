package thaumicbees.bees;

import forestry.api.apiculture.*;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import java.util.ArrayList;

import cpw.mods.fml.common.FMLLog;

import thaumcraft.api.AuraNode;
import thaumcraft.api.EnumNodeType;
import thaumcraft.api.ThaumcraftApi;
import thaumicbees.main.ThaumicBees;
import thaumicbees.main.utils.MoonPhase;
import thaumicbees.main.utils.compat.EquivalentExchangeHelper;
import thaumicbees.main.utils.compat.ExtraBeesHelper;
import thaumicbees.main.utils.compat.ThaumcraftHelper;

public class BeeMutation implements IBeeMutation
{
	private static BeeMutation Esoteric;
	private static BeeMutation Esoteric1;
	private static BeeMutation Mysterious;
	private static BeeMutation Mysterious1;
	private static BeeMutation Arcane;
	private static BeeMutation Charmed;
	private static BeeMutation Enchanted;
	private static BeeMutation Supernatural;
	private static BeeMutation Stark;
	private static BeeMutation Fire;
	private static BeeMutation Water;
	private static BeeMutation Pupil;
	private static BeeMutation Scholarly;
	private static BeeMutation Savant;
	private static BeeMutation Aware;
	private static BeeMutation Vis;
	private static BeeMutation Vis1;
	private static BeeMutation Pure;
	private static BeeMutation Flux;
	private static BeeMutation Node;
	private static BeeMutation Node1;
	private static BeeMutation Skulking;
	private static BeeMutation Brainy;
	private static BeeMutation Gossamer;
	private static BeeMutation Wispy;
	private static BeeMutation Batty;
	private static BeeMutation Ghastly;
	private static BeeMutation Timely;
	private static BeeMutation Lordly;
	private static BeeMutation Doctoral;
	private static BeeMutation Spirit;
	private static BeeMutation Spirit1;
	private static BeeMutation Soul;
	private static BeeMutation Minium;
	private static BeeMutation Iron;
	private static BeeMutation Gold;
	private static BeeMutation Copper;
	private static BeeMutation Tin;
	private static BeeMutation Silver;
	private static BeeMutation Lead;
	private static BeeMutation Diamond;
	private static BeeMutation Emerald;
	private static BeeMutation Lead1;
	private static BeeMutation Lead2;
	private static BeeMutation Beef;
	private static BeeMutation Chicken;
	private static BeeMutation Pork;
	private static BeeMutation Aura;
	private static BeeMutation Aura1;
	
	public static void setupMutations()
	{
		IAlleleBeeSpecies baseA, baseB;
		
		BeeMutation.Esoteric = new BeeMutation(Allele.getBaseSpecies("Imperial"), Allele.getBaseSpecies("Demonic"), BeeSpecies.ESOTERIC, 10);
		BeeMutation.Esoteric1 = new BeeMutation(Allele.getBaseSpecies("Heroic"), Allele.getBaseSpecies("Demonic"), BeeSpecies.ESOTERIC, 25);
		BeeMutation.Mysterious = new BeeMutation(Allele.getBaseSpecies("Ended"), BeeSpecies.ESOTERIC, BeeSpecies.MYSTERIOUS, 15);
		BeeMutation.Mysterious1 = new BeeMutation(Allele.getBaseSpecies("Demonic"), BeeSpecies.ESOTERIC, BeeSpecies.MYSTERIOUS, 4);
		BeeMutation.Arcane = new BeeMutation(BeeSpecies.ESOTERIC, BeeSpecies.MYSTERIOUS, BeeSpecies.ARCANE, 8)
			.setMoonPhaseBonus(MoonPhase.WANING_CRESCENT, MoonPhase.WAXING_CRESCENT, 1.5f);
		BeeMutation.Charmed = new BeeMutation(Allele.getBaseSpecies("Diligent"), Allele.getBaseSpecies("Valiant"), BeeSpecies.CHARMED, 20);
		BeeMutation.Enchanted = new BeeMutation(BeeSpecies.CHARMED, Allele.getBaseSpecies("Valiant"), BeeSpecies.ENCHANTED, 8);
		BeeMutation.Supernatural = new BeeMutation(BeeSpecies.CHARMED, BeeSpecies.ENCHANTED, BeeSpecies.SUPERNATURAL, 10)
			.setMoonPhaseBonus(MoonPhase.WAXING_GIBBOUS, MoonPhase.WANING_GIBBOUS, 1.5f);
		BeeMutation.Pupil = new BeeMutation(BeeSpecies.ARCANE, BeeSpecies.ENCHANTED, BeeSpecies.PUPIL, 10);
		BeeMutation.Stark = new BeeMutation(BeeSpecies.ARCANE, BeeSpecies.SUPERNATURAL, BeeSpecies.STARK, 8);
		
		BeeMutation.Timely = new BeeMutation(Allele.getBaseSpecies("Industrious"), BeeSpecies.ENCHANTED, BeeSpecies.TIMELY, 10);
		BeeMutation.Lordly = new BeeMutation(BeeSpecies.TIMELY, Allele.getBaseSpecies("Imperial"), BeeSpecies.LORDLY, 9);
		BeeMutation.Doctoral = new BeeMutation(BeeSpecies.TIMELY, BeeSpecies.LORDLY, BeeSpecies.DOCTORAL, 7);
		
		BeeMutation.Minium = new BeeMutation(Allele.getBaseSpecies("Sinister"), BeeSpecies.MYSTERIOUS, BeeSpecies.MINIUM, 11);
		
		BeeMutation.Spirit = new BeeMutation(Allele.getBaseSpecies("Fiendish"), BeeSpecies.ENCHANTED, BeeSpecies.SPIRIT, 12);
		BeeMutation.Spirit1 = new BeeMutation(Allele.getBaseSpecies("Fiendish"), Allele.getBaseSpecies("Ended"), BeeSpecies.SPIRIT, 20);
		BeeMutation.Soul = new BeeMutation(Allele.getBaseSpecies("Fiendish"), BeeSpecies.SPIRIT, BeeSpecies.SOUL, 11);
		
		BeeMutation.Iron = new BeeMutation(Allele.getBaseSpecies("Industrious"), Allele.getBaseSpecies("Diligent"), BeeSpecies.IRON, 10);
		
		baseA = (BeeSpecies.MINIUM.isActive()) ? BeeSpecies.MINIUM : Allele.getBaseSpecies("Imperial");
		baseB = (BeeSpecies.LEAD.isActive()) ? BeeSpecies.LEAD : BeeSpecies.IRON;
		BeeMutation.Gold = new BeeMutation(baseA, baseB, BeeSpecies.GOLD, 8);
		
		if (BeeSpecies.COPPER.isActive())
		{
			BeeMutation.Copper = new BeeMutation(Allele.getBaseSpecies("Industrious"), Allele.getBaseSpecies("Meadows"), BeeSpecies.COPPER, 12);
		}
		if (BeeSpecies.TIN.isActive())
		{
			BeeMutation.Tin = new BeeMutation(Allele.getBaseSpecies("Industrious"), Allele.getBaseSpecies("Forest"), BeeSpecies.TIN, 12);
		}
		if (BeeSpecies.SILVER.isActive())
		{
			BeeMutation.Silver = new BeeMutation(Allele.getBaseSpecies("Imperial"), Allele.getBaseSpecies("Modest"), BeeSpecies.SILVER, 10);
		}
		if (BeeSpecies.LEAD.isActive())
		{
			if (BeeSpecies.COPPER.isActive())
			{
				BeeMutation.Lead = new BeeMutation(Allele.getBaseSpecies("Industrious"), BeeSpecies.COPPER, BeeSpecies.LEAD, 13);
			}
			if (BeeSpecies.TIN.isActive())
			{
				BeeMutation.Lead1 = new BeeMutation(Allele.getBaseSpecies("Industrious"), BeeSpecies.TIN, BeeSpecies.LEAD, 13);
			}
			BeeMutation.Lead2 = new BeeMutation(Allele.getBaseSpecies("Industrious"), Allele.getBaseSpecies("Common"), BeeSpecies.LEAD, 9);
		}
		
		BeeMutation.Diamond = new BeeMutation(Allele.getBaseSpecies("Austere"), BeeSpecies.GOLD, BeeSpecies.DIAMOND, 6);
		baseA = (BeeSpecies.SILVER.isActive()) ? BeeSpecies.SILVER : Allele.getBaseSpecies("Imperial");
		BeeMutation.Emerald = new BeeMutation(Allele.getBaseSpecies("Austere"), baseA, BeeSpecies.EMERALD, 6);
		
		if (BeeSpecies.BEEF.isActive())
		{
			BeeMutation.Beef = new BeeMutation(Allele.getBaseSpecies("Common"), BeeSpecies.SKULKING, BeeSpecies.BEEF, 10);
		}
		if (BeeSpecies.CHICKEN.isActive())
		{
			BeeMutation.Chicken = new BeeMutation(Allele.getBaseSpecies("Common"), BeeSpecies.SKULKING, BeeSpecies.CHICKEN, 10);
		}
		if (BeeSpecies.PORK.isActive())
		{
			BeeMutation.Pork = new BeeMutation(Allele.getBaseSpecies("Common"), BeeSpecies.SKULKING, BeeSpecies.PORK, 10);
		}
		
		// Now we get into a little bit of branching...
		if (ExtraBeesHelper.isActive())
		{
			BeeMutation.Skulking = new BeeMutation(BeeSpecies.MYSTERIOUS, Allele.getExtraSpecies("desolate"), BeeSpecies.SKULKING, 10);
			BeeMutation.Ghastly = new BeeMutation(BeeSpecies.SKULKING, Allele.getExtraSpecies("creeper"), BeeSpecies.GHASTLY, 13);
		}
		else
		{
			BeeMutation.Skulking = new BeeMutation(BeeSpecies.MYSTERIOUS, Allele.getBaseSpecies("Modest"), BeeSpecies.SKULKING, 10);
			BeeMutation.Ghastly = new BeeMutation(BeeSpecies.SKULKING, Allele.getBaseSpecies("Austere"), BeeSpecies.GHASTLY, 13);
		}
		
		if (ThaumcraftHelper.isActive())
		{
			BeeMutation.Scholarly = new BeeMutation(BeeSpecies.PUPIL, BeeSpecies.ARCANE, BeeSpecies.SCHOLARLY, 8);
			BeeMutation.Savant = new BeeMutation(BeeSpecies.PUPIL, BeeSpecies.SCHOLARLY, BeeSpecies.SAVANT, 6);
			
			BeeMutation.Aware = new BeeMutation(Allele.getBaseSpecies("Demonic"), Allele.getBaseSpecies("Edenic"), BeeSpecies.AWARE, 12);
			BeeMutation.Vis = new BeeMutation(BeeSpecies.AWARE, BeeSpecies.ARCANE, BeeSpecies.VIS, 9)
			.setAuraNodeRequired(40);
			BeeMutation.Vis1 = new BeeMutation(BeeSpecies.AWARE, BeeSpecies.STARK, BeeSpecies.VIS, 12)
			.setAuraNodeRequired(80);
			BeeMutation.Pure = new BeeMutation(BeeSpecies.VIS, Allele.getBaseSpecies("Edenic"), BeeSpecies.PURE, 7)
				.setAuraNodeTypeRequired(5, EnumNodeType.PURE).setMoonPhaseBonus(MoonPhase.NEW, MoonPhase.NEW, 1.6f);
			BeeMutation.Flux = new BeeMutation(BeeSpecies.VIS, Allele.getBaseSpecies("Edenic"), BeeSpecies.FLUX, 7)
				.setAuraNodeTypeRequired(30, EnumNodeType.UNSTABLE).setMoonPhaseBonus(MoonPhase.FULL, MoonPhase.FULL, 1.6f);
					
			BeeMutation.Node = new BeeMutation(BeeSpecies.PURE, BeeSpecies.FLUX, BeeSpecies.NODE, 2)
				.setAuraNodeRequired(4).setMoonPhaseRestricted(MoonPhase.WANING_HALF, MoonPhase.WANING_HALF);
			BeeMutation.Node1 = new BeeMutation(BeeSpecies.PURE, BeeSpecies.FLUX, BeeSpecies.NODE, 2)
				.setAuraNodeRequired(4).setMoonPhaseRestricted(MoonPhase.WAXING_HALF, MoonPhase.WAXING_HALF);
			
			BeeMutation.Aura = new BeeMutation(BeeSpecies.VIS, Allele.getBaseSpecies("Industrious"), BeeSpecies.AURA, 3)
				.setMoonPhaseRestricted(MoonPhase.WANING_CRESCENT, MoonPhase.WANING_CRESCENT);
			BeeMutation.Aura1 = new BeeMutation(BeeSpecies.VIS, Allele.getBaseSpecies("Industrious"), BeeSpecies.AURA, 3)
				.setMoonPhaseRestricted(MoonPhase.WAXING_CRESCENT, MoonPhase.WAXING_CRESCENT);
			
			if (ExtraBeesHelper.isActive())
			{
				BeeMutation.Brainy = new BeeMutation(BeeSpecies.SKULKING, Allele.getExtraSpecies("rotten"), BeeSpecies.BRAINY, 12);
				BeeMutation.Gossamer = new BeeMutation(BeeSpecies.SKULKING, Allele.getExtraSpecies("ancient"), BeeSpecies.GOSSAMER, 10);
				BeeMutation.Batty = new BeeMutation(BeeSpecies.SKULKING, Allele.getExtraSpecies("rock"), BeeSpecies.BATTY, 14);
			}
			else
			{
				BeeMutation.Brainy = new BeeMutation(BeeSpecies.SKULKING, Allele.getBaseSpecies("Sinister"), BeeSpecies.BRAINY, 10);
				BeeMutation.Gossamer = new BeeMutation(BeeSpecies.SKULKING, BeeSpecies.SUPERNATURAL, BeeSpecies.GOSSAMER, 10);
				BeeMutation.Batty = new BeeMutation(BeeSpecies.SKULKING, Allele.getBaseSpecies("Frugal"), BeeSpecies.BATTY, 11);
			}
				
			BeeMutation.Gossamer.setMoonPhaseRestricted(MoonPhase.FULL, MoonPhase.WANING_CRESCENT);
			BeeMutation.Wispy = new BeeMutation(BeeSpecies.GOSSAMER, Allele.getBaseSpecies("Cultivated"), BeeSpecies.WISPY, 8);
		}
		else
		{
			BeeMutation.Fire = new BeeMutation(BeeSpecies.STARK, Allele.getBaseSpecies("Fiendish"), BeeSpecies.FIRE, 10);
			if (ExtraBeesHelper.isActive())
			{
				BeeMutation.Water = new BeeMutation(BeeSpecies.STARK, Allele.getExtraSpecies("ocean"), BeeSpecies.WATER, 12);
				BeeMutation.Water = new BeeMutation(BeeSpecies.STARK, Allele.getExtraSpecies("river"), BeeSpecies.WATER, 8);
			}
			else
			{
				BeeMutation.Water = new BeeMutation(BeeSpecies.STARK, Allele.getBaseSpecies("Marshy"), BeeSpecies.WATER, 10);
			}
		}
		
		if (EquivalentExchangeHelper.isActive())
		{
			BeeMutation.Minium = new BeeMutation(Allele.getBaseSpecies("Frugal"), BeeSpecies.PUPIL, BeeSpecies.MINIUM, 10);
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
