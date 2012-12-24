package thaumicbees.main.bees;

import forestry.api.apiculture.IBeeBranch;
import forestry.api.genetics.IAlleleSpecies;
import java.util.ArrayList;

public class BeeBranch implements IBeeBranch
{

	public static BeeBranch Arcane;
	public static BeeBranch Supernatural;
	public static BeeBranch Stark;
	public static BeeBranch Elemental;
	public static BeeBranch Scholarly;
	private String uID;
	private String name;
	private String scientific;
	private String description;
	private ArrayList memberSpecies;

	public BeeBranch(String branchName, String scientificName)
	{
		this(branchName, scientificName, "");
	}

	public BeeBranch(String branchName, String scientificName, String branchDescription)
	{
		uID = (new StringBuilder()).append("thaumicbees.branch.").append(branchName.toLowerCase()).toString();
		name = branchName;
		scientific = scientificName;
		description = branchDescription;
		memberSpecies = new ArrayList();
	}

	public String getUID()
	{
		return uID;
	}

	public String getName()
	{
		return name;
	}

	public String getScientific()
	{
		return scientific;
	}

	public String getDescription()
	{
		return description;
	}

	public IAlleleSpecies[] getMembers()
	{
		return (IAlleleSpecies[]) memberSpecies.toArray(new IAlleleSpecies[memberSpecies.size()]);
	}

	public void addMember(IAlleleSpecies species)
	{
		memberSpecies.add(species);
	}
}
