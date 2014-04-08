package magicbees.bees;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleFlowers;
import forestry.api.genetics.IFlowerProvider;

public class AlleleFlower extends Allele implements IAlleleFlowers
{
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
