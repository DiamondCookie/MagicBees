package thaumicbees.bees;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.LanguageRegistry;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;

public enum BeeClassification implements IClassification
{
	ARCANE("Arcane"),
	SUPERNATURAL("Supernatural"),
	SCHOLARLY("Scholarly"),
	THAUMIC("Thaumic"),
	SKULKING("Skulking"),
	VIS("Vis"),
	TIME("Time"),
	;
	
	private String uID;
	private ArrayList<IAlleleSpecies> species;
	private IClassification parent;
	private EnumClassLevel level;
	
	private BeeClassification(String name)
	{
		this.uID = "classification." + name.toLowerCase();
		this.level = EnumClassLevel.GENUS;
		this.species = new ArrayList();
		this.parent = AlleleManager.alleleRegistry.getClassification("family.apidae");
		AlleleManager.alleleRegistry.registerClassification(this);
	}
	
	@Override
	public EnumClassLevel getLevel()
	{
		return this.level;
	}

	@Override
	public String getUID()
	{
		return this.uID;
	}

	@Override
	public String getName()
	{
		return LanguageRegistry.instance().getStringLocalization(getUID());
	}

	@Override
	public String getScientific()
	{
		return LanguageRegistry.instance().getStringLocalization(getUID() + ".latin");
	}

	@Override
	public String getDescription()
	{
		return LanguageRegistry.instance().getStringLocalization(getUID() + ".description");
	}

	@Override
	public IClassification[] getMemberGroups()
	{
		return null;
	}

	@Override
	public void addMemberGroup(IClassification group)
	{
		
	}

	@Override
	public IAlleleSpecies[] getMemberSpecies()
	{
		return this.species.toArray(new IAlleleSpecies[this.species.size()]);
	}

	@Override
	public void addMemberSpecies(IAlleleSpecies species)
	{
		if (!this.species.contains(species))
		{
			this.species.add(species);
		}
	}

	@Override
	public IClassification getParent()
	{
		return this.parent;
	}

	@Override
	public void setParent(IClassification parent)
	{
		this.parent = parent;
	}

}
