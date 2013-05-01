package thaumicbees.bees;

import forestry.api.genetics.IAlleleFloat;

public class AlleleFloat extends Allele implements IAlleleFloat
{
	private float value;
	
	public AlleleFloat(String id, float val, boolean isDominant)
	{
		super(id, isDominant);
		this.value = val;
	}

	@Override
	public float getValue()
	{
		return this.value;
	}

}
