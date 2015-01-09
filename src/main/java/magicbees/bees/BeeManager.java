package magicbees.bees;

import magicbees.block.types.HiveType;
import magicbees.item.types.DropType;
import magicbees.main.Config;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.genetics.AlleleManager;

public class BeeManager
{
	public static IBeeRoot beeRoot;
	
	public static void ititializeBees()
	{
		beeRoot = (IBeeRoot)AlleleManager.alleleRegistry.getSpeciesRoot("rootBees");
		
		Allele.setupAdditionalAlleles();
		BeeSpecies.setupBeeSpecies();
		Allele.registerDeprecatedAlleleReplacements();
		BeeMutation.setupMutations();
		
		HiveType.initHiveData();
		
		beeRoot.setResearchSuitability(Config.drops.getStackForType(DropType.INTELLECT), 0.5f);
	}
}
