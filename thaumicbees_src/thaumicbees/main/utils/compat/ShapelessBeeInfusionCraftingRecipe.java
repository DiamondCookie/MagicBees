package thaumicbees.main.utils.compat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.genetics.IAllele;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.crafting.ShapelessInfusionCraftingRecipes;
import thaumcraft.api.research.ResearchList;

public class ShapelessBeeInfusionCraftingRecipe extends ShapelessInfusionCraftingRecipes
{
	private String alleleId;
	private byte alleleSlot;
	
	/**
	 * Creates & adds a Crafting recipe to Thaumcraft for our Stark bee infusions.
	 */
	public static void createNewRecipe(String key, String researchKey, ItemStack output, Object[] input,
			int auraCost, ObjectTags tags, IAllele allele, EnumBeeChromosome slot)
	{
		ArrayList<ItemStack> inputList = new ArrayList(input.length);
		for (Object item : input)
		{
			if (item instanceof ItemStack)
			{
				inputList.add(((ItemStack) item).copy());
			}
			else if (item instanceof Item)
			{
				inputList.add(new ItemStack((Item)item));
			}
			else if (item instanceof Block)
			{
				inputList.add(new ItemStack((Block)item));
			}
			else
			{
				throw new RuntimeException("Invalid shapeless crafting recipe!");
			}
		}
		ThaumcraftApi.getCraftingRecipes().add(
				new ShapelessBeeInfusionCraftingRecipe(key, output, inputList, auraCost, tags, allele, slot)
				);
		ResearchList.craftingRecipesForResearch.put(researchKey, Arrays.asList(ThaumcraftApi.getCraftingRecipes().size()-1));
	}

	private ShapelessBeeInfusionCraftingRecipe(String key, ItemStack output, List<ItemStack> itemList, int cost, ObjectTags tags,
			IAllele allele, EnumBeeChromosome slot)
	{
		super(key, output, itemList, cost, tags);
		
		this.alleleId = allele.getUID();
		this.alleleSlot = (byte)slot.ordinal();
	}

	@Override
	public boolean matches(IInventory craftingInventory, EntityPlayer player)
	{	
		// We'll remove items from the list as they are found.
        ArrayList<ItemStack> recipeItemsList = new ArrayList(this.recipeItems);
        
        boolean invalidItemFound = false;
        
    	if (key.length() == 0 || (key.length() > 0 && ThaumcraftApiHelper.isResearchComplete(player.username, key)))
    	{

	        for (int i = 0; i < 3 && !invalidItemFound; ++i)
	        {
	            for (int j = 0; j < 3 && !invalidItemFound; ++j)
	            {
	                ItemStack itemStack = ThaumcraftApiHelper.getStackInRowAndColumn(craftingInventory, j, i);
	
	                if (itemStack != null)
	                {
	                	Iterator<ItemStack> itr = recipeItemsList.iterator();
	                	boolean currentItemInRecipe = false;
	                	
	                	while (itr.hasNext())
	                	{
	                		ItemStack recipeItem = itr.next();
	                		
		                	if (itemStack.itemID == recipeItem.itemID && (recipeItem.getItemDamage() == -1 || itemStack.getItemDamage() == recipeItem.getItemDamage()))
			                {
		                		if (recipeItem.hasTagCompound())
		                		{
		                			currentItemInRecipe = this.hasMatchingChromosome(itemStack);
		                		}
		                		else
		                		{
		                			currentItemInRecipe = true;
		                		}
			                }
		                	
		                	if (currentItemInRecipe)
		                	{
                				itr.remove();
                				break;
		                	}
	                	}
	                	
	                	if (!currentItemInRecipe)
	                	{
	                		invalidItemFound = true;
	                	}
	                }
	            }
	        }
    	}
    	
    	return recipeItemsList.isEmpty() && !invalidItemFound;
	}
	
	private boolean hasMatchingChromosome(ItemStack itemStack)
	{
		boolean genesMatch = false;
		
		NBTTagCompound compoundRoot = itemStack.getTagCompound();
		if (compoundRoot.hasKey("Genome"))
		{
			NBTTagCompound genome = compoundRoot.getCompoundTag("Genome");
			if (genome.hasKey("Chromosomes"))
			{
				NBTTagList chromosomes = genome.getTagList("Chromosomes");
				
				for (int i = 0; i < chromosomes.tagCount() && !genesMatch; ++i)
				{
					NBTTagCompound genes = (NBTTagCompound)chromosomes.tagAt(i);
					if (genes.hasKey("Slot") && genes.getByte("Slot") == this.alleleSlot)
					{
						if (genes.getString("UID0").equals(this.alleleId) && genes.getString("UID1").equals(this.alleleId))
						{
							genesMatch = true;
						}
						
					}
				}
			}
		}
		
		return genesMatch;
	}

}
