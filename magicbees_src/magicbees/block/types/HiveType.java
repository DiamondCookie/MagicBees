package magicbees.block.types;

import java.util.ArrayList;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import magicbees.bees.BeeSpecies;
import magicbees.bees.HiveDrop;
import magicbees.item.types.CombType;
import magicbees.main.Config;
import magicbees.main.utils.VersionInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.IHiveDrop;

public enum HiveType
{
	CURIOUS("curious", 12, true),
	UNUSUAL("unusual", 12, true),
	DEEP("deep", 12, true),
	INFERNAL("infernal", 15, false),
	END("end", 7, false),
	;
	
	private static String[] nameList;
	
	private String name;
	public boolean show;
	private int lightLevel;
	private ArrayList<IHiveDrop> drops;
	@SideOnly(Side.CLIENT)
	private Icon[] icons;
	
	public static HiveType getHiveFromMeta(int meta)
	{
		HiveType type = CURIOUS;
		
		if (meta > 0 && meta < HiveType.values().length)
		{
			type = HiveType.values()[meta];
		}
		
		return type;
	}
	
	public static void initHiveDrops()
	{
		CURIOUS.drops.add(new HiveDrop(BeeSpecies.MYSTICAL.getGenome(), new ItemStack[] {Config.combs.getStackForType(CombType.MUNDANE)}, 55));
		CURIOUS.drops.add(new HiveDrop(BeeSpecies.SORCEROUS.getGenome(), new ItemStack[] {Config.combs.getStackForType(CombType.MUNDANE)}, 40));
	}
	
	private HiveType(String hiveName, int light, boolean visible)
	{
		this.name = hiveName;
		this.lightLevel = light;
		this.show = visible;
		this.drops = new ArrayList<IHiveDrop>();
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerIcons(IconRegister register)
	{
		for (HiveType type : HiveType.values())
		{
			type.icons = new Icon[2];

			type.icons[0] = register.registerIcon(VersionInfo.ModName.toLowerCase() + ":beehive." + type.ordinal() + ".top");
			type.icons[1] = register.registerIcon(VersionInfo.ModName.toLowerCase() + ":beehive." + type.ordinal() + ".side");
		}
	}
	
	private HiveType()
	{
		this.drops = new ArrayList<IHiveDrop>();
	}
	
	public void addDrop(IHiveDrop drop)
	{
		this.drops.add(drop);
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIconForSide(int side)
	{
		Icon i = this.icons[0];
		
		if (side != 0 && side != 1)
		{
			i = this.icons[1];
		}
		
		return i;
	}
	
	public int getLightValue()
	{
		return this.lightLevel;
	}
	
	public static String[] getAllNames()
	{
		return (nameList == null) ? nameList = generateNames() : nameList;
	}
	
	private static String[] generateNames()
	{
		String[] names = new String[values().length];
		for (int i = 0; i < names.length; ++i)
		{
			names[i] = values()[i].name;
		}
		return names;
	}
}
