package magicbees.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialHive extends Material
{

	public MaterialHive()
	{
		super(MapColor.stoneColor);
		this.setImmovableMobility();
		this.setRequiresTool();
	}

}
