package thaumicbees.item;

import extrabiomes.utility.CreativeTab;
import thaumicbees.main.CommonProxy;
import net.minecraft.item.Item;

public class ItemKnowledgeFragment extends Item
{

	public ItemKnowledgeFragment(int ID)
	{
		super(ID);
		this.iconIndex = 3;
		this.setTextureFile(CommonProxy.TCBEES_ITEMS);
		this.setCreativeTab(CreativeTab.tabMaterials);
	}

}
