package thaumicbees.bees;

import forestry.api.apiculture.IAlleleFlowers;
import forestry.api.apiculture.IFlowerProvider;
import forestry.api.genetics.AlleleManager;

public class AlleleFlower implements IAlleleFlowers
{
	
	public static AlleleFlower alleleFlowerBookshelf;
	public static AlleleFlower alleleFlowerThaumcraft;

	private String uid;
	private IFlowerProvider provider;
	private boolean dominant;
	
	public AlleleFlower(String uid, IFlowerProvider flowerProvider, boolean isDominant)
	{
		this.uid = "thaumicbees." + uid;
		this.provider = flowerProvider;
		this.dominant = isDominant;
		
		AlleleManager.alleleRegistry.registerAllele(this);
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
	public IFlowerProvider getProvider()
	{
		return this.provider;
	}

}
