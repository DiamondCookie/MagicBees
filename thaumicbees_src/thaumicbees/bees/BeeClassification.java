package thaumicbees.bees;

import java.util.ArrayList;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;

public enum BeeClassification implements IClassification
{
	ARCANE("Arcane", "Arcanus", ""),
	SUPERNATURAL("Supernatural", "Coeleste", ""),
	SCHOLARLY("Scholarly", "Docto", ""),
	THAUMIC("Thaumic", "Thaumis", ""),
	SKULKING("Skulking", "Malevolens", ""),
	VIS("Vis", "Patabilis", ""),
	TIME("Time", "Sempiternus", ""),
	;
	
	private String uID;
	private String name;
	private String scientific;
	private String description;
	private ArrayList<IAlleleSpecies> species;
	private IClassification parent;
	private EnumClassLevel level;
	
	private BeeClassification(String name, String latin, String desc)
	{
		this.uID = "classification." + name.toLowerCase();
		this.name = name;
		this.scientific = latin;
		this.level = EnumClassLevel.GENUS;
		this.description = desc;
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
		return this.name;
	}

	@Override
	public String getScientific()
	{
		return this.scientific;
	}

	@Override
	public String getDescription()
	{
		return this.description;
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
