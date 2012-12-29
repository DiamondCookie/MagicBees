package thaumicbees.bees.genetics;

import forestry.api.genetics.IAllele;

public class Allele implements IAllele
{

	private String uid;
	private boolean dominant;
	
	public Allele(String id, boolean isDominant)
	{
		this.uid = "thaumicbees." + id;
		this.dominant = isDominant;
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

}
