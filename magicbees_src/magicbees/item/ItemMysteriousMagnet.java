package magicbees.item;

import java.util.HashMap;
import java.util.List;

import magicbees.main.CommonProxy;
import magicbees.main.utils.LocalizationManager;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMysteriousMagnet extends Item
{
	@SideOnly(Side.CLIENT)
	private Icon activeIcon;
	
	private float rangeBase;
	private float multiplier;
	private HashMap<Integer, Float> special = new HashMap<Integer, Float>();
	
	public ItemMysteriousMagnet(int par1)
	{
		super(par1);
		this.setNoRepair();
		this.setHasSubtypes(true);
		this.rangeBase = 3f;
		this.multiplier = 0.75f;
		this.special.put(792499080, 6f);
		this.special.put(-1813233790, 0.4f);
		this.special.put(902599041, 5.5f);
		this.setUnlocalizedName(CommonProxy.DOMAIN + ":mysteriousMagnet");
	}
	
	public void setBaseRange(float value)
	{
		this.rangeBase = value;
	}
	
	public void setLevelMultiplier(float value)
	{
		this.multiplier = value;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4)
	{
		String s = LocalizationManager.getLocalizedString("misc.level", itemStack.getItemDamage() >> 1);
		if (isMagnetActive(itemStack))
		{
			list.add(LocalizationManager.getLocalizedString("misc.magnetActive", s));
		}
		else
		{
			list.add(LocalizationManager.getLocalizedString("misc.magnetInactive", s));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		super.getSubItems(par1, par2CreativeTabs, list);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister)
	{
		this.itemIcon = iconRegister.registerIcon(CommonProxy.DOMAIN + ":magnetInactive");
		this.activeIcon = iconRegister.registerIcon(CommonProxy.DOMAIN + ":magnetActive");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int damage)
	{
		return isMagnetActive(damage) ? this.activeIcon : this.itemIcon;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		if (player.isSneaking())
		{
			itemStack.setItemDamage(itemStack.getItemDamage() ^ 1);
		}
		return itemStack;
	}
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5)
	{
		if (isMagnetActive(itemStack) && entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			int dragCount = player.username.hashCode();
			float radius = getRadius(itemStack.getItemDamage());
			AxisAlignedBB bounds = player.boundingBox.expand(radius, radius, radius);
			List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, bounds);
			
			for (EntityItem e : list)
			{
				if (e.age >= 10)
				{
					double x = player.posX - e.posX;
					double y = player.posY - e.posY;
					double z = player.posZ - e.posZ;
					
					double length = Math.sqrt(x * x + y * y + z * z) * 2;
					
					x = x / length + player.motionX / 2;
					y = y / length + player.motionY / 2;
					z = z / length + player.motionZ / 2;
					
					e.motionX =+ x;
					e.motionY =+ y;
					e.motionZ =+ z;
					e.isAirBorne = true;
					
					if (e.isCollidedHorizontally)
					{
						e.motionY += 1;
					}
					
					if (world.rand.nextInt(20) == 0)
					{
						float pitch = 0.9f - world.rand.nextFloat() / 10f;
			            world.playSoundEffect(e.posX, e.posY, e.posZ, "mob.endermen.portal", 0.6f, pitch);
					}
				}
				if (this.special.containsKey(dragCount) && world.rand.nextInt(15) == 0)
				{
		            world.playSoundEffect(entity.posX, entity.posY, entity.posZ, "mob.endermen.portal", 1.5f, this.special.get(dragCount));
				}
			}
			
			if ((itemStack.getItemDamage() >> 1) >= 7 && FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			{
				List<EntityArrow> arrows = world.getEntitiesWithinAABB(EntityArrow.class, bounds);
				
				for (EntityArrow arrow : arrows)
				{
					if (world.rand.nextInt(10) == 0)
					{
						EntityItem replacement = new EntityItem(world, arrow.posX, arrow.posY, arrow.posZ, new ItemStack(Item.arrow));
						world.spawnEntityInWorld(replacement);
					}
					world.removeEntity(arrow);
				}
			}
		}
	}
	
	private boolean isMagnetActive(ItemStack itemStack)
	{
		return isMagnetActive(itemStack.getItemDamage());
	}
	
	private boolean isMagnetActive(int damage)
	{
		return (damage & 0x01) == 1;
	}

	private float getRadius(int damageValue)
	{
		return this.rangeBase + (multiplier * (damageValue >> 1));
	}

}
