package thaumicbees.main.bees;

import forestry.api.apiculture.*;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import java.util.ArrayList;

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
	private IAllele parent1;
	private IAllele parent2;
	private IAllele mutationTemplate[];
	private int chance;
	private boolean isSecret;

	public BeeMutation(IAlleleBeeSpecies species0, IAlleleBeeSpecies species1, IAllele producesGenome[], int percentChance, boolean hide)
	{
		parent1 = species0;
		parent2 = species1;
		mutationTemplate = producesGenome;
		chance = percentChance;
		isSecret = hide;
		BeeManager.beeMutations.add(this);
	}

	public int getChance(IBeeHousing housing, IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1)
	{
		return Math.round((float) chance * BeeManager.breedingManager.getBeekeepingMode(housing.getWorld()).getMutationModifier((IBeeGenome) genome0, (IBeeGenome) genome1));
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
		return chance;
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

	public boolean isSecret()
	{
		return isSecret;
	}
}
