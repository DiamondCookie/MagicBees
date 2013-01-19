package thaumicbees.main;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

/**
 * A class to hold some data related to mod state & functions.
 * @author MysteriousAges
 *
 */
public class ThaumicBeesConfiguration
{
	public boolean DrawParticleEffects;	
	public boolean ThaumcraftRecipesAdded;
	public boolean ExtraBeesInstalled;
	public boolean AddThaumcraftItemsToBackpacks;
	
	public String ThaumaturgeExtraItems;
	
	public void doMiscConfig(Configuration cfg)
	{
		Property p;
		
		p = cfg.get("general", "backpack.thaumaturge.additionalItems", "");
		p.comment = "Add additional items to the Thaumaturge's Backpack." +
				"\n Format is the same as Forestry's: id:meta;id;id:meta (etc)";
		this.ThaumaturgeExtraItems = p.value;
		
		p = cfg.get("general", "backpack.forestry.addThaumcraftItems", true);
		p.comment = "Set to true if you want ThaumicBees to add several Thaumcraft blocks & items to Forestry backpacks." +
				"\n Set to false to disable.";
		this.AddThaumcraftItemsToBackpacks = p.getBoolean(true);
	}

}
