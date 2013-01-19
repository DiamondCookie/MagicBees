package thaumicbees.bees.genetics;

import forestry.api.genetics.IAlleleInteger;

public class AlleleInteger extends Allele implements IAlleleInteger
{
	private int value;
	
	public AlleleInteger(String id, int val, boolean isDominant)
	{
		super(id, isDominant);
		this.value = val;
	}

	@Override
	public int getValue()
	{
		return this.value;
	}

}
