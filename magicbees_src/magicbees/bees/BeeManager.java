package magicbees.bees;

import magicbees.block.types.HiveType;

public class BeeManager
{
	public static void ititializeBees()
	{
		Allele.setupAdditionalAlleles();
		BeeSpecies.setupBeeSpecies();
		Allele.registerDeprecatedAlleleReplacements();
		BeeMutation.setupMutations();
		HiveType.initHiveDrops();
	}
}
