package thaumicbees.bees.genetics;

import forestry.api.apiculture.IAlleleFlowers;
import forestry.api.apiculture.IFlowerProvider;
import forestry.api.genetics.AlleleManager;

public class AlleleFlower extends Allele implements IAlleleFlowers
{
	
	public static AlleleFlower alleleFlowerBookshelf;
	public static AlleleFlower alleleFlowerThaumcraft;

	private String uid;
	private IFlowerProvider provider;
	
	public AlleleFlower(String uid, IFlowerProvider flowerProvider, boolean isDominant)
	{
		super("flower" + uid, isDominant);
		this.provider = flowerProvider;
		
		AlleleManager.alleleRegistry.registerAllele(this);
	}

	@Override
	public IFlowerProvider getProvider()
	{
		return this.provider;
	}

}
