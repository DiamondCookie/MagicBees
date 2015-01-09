package magicbees.item;

import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import magicbees.main.CommonProxy;
import magicbees.main.utils.LocalizationManager;
import magicbees.main.utils.TabMagicBees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMysteriousMagnet extends Item
{
	@SideOnly(Side.CLIENT)
	private IIcon activeIcon;
	
	private float rangeBase;
	private float multiplier;
	private int maxLevel;
	private final float fudgeFactor = 0.2f;
	
	public ItemMysteriousMagnet()
	{
		super();
		this.setNoRepair();
		this.setHasSubtypes(true);
		this.rangeBase = 3f;
		this.multiplier = 0.75f;
		this.maxLevel = 8;
		this.setUnlocalizedName(CommonProxy.DOMAIN + ":mysteriousMagnet");
		this.setCreativeTab(TabMagicBees.tabMagicBees);
	}
	
	public void setBaseRange(float value)
	{
		this.rangeBase = value;
	}
	
	public void setLevelMultiplier(float value)
	{
		this.multiplier = value;
	}
	
	public void setMaximumLevel(int value)
	{
		this.maxLevel = value;
	}
	
	public int getMaximumLevel()
	{
		return this.maxLevel;
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
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		for (int i = 0; i <= this.maxLevel; i++)
		{
			list.add(new ItemStack(this, 1, i * 2));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		this.itemIcon = iconRegister.registerIcon(CommonProxy.DOMAIN + ":magnetInactive");
		this.activeIcon = iconRegister.registerIcon(CommonProxy.DOMAIN + ":magnetActive");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		return isMagnetActive(damage) ? this.activeIcon : this.itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack itemStack, int pass)
	{
		return isMagnetActive(itemStack);
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
			int dragCount = player.getCommandSenderName().hashCode();
			float radius = getRadius(itemStack.getItemDamage()) - fudgeFactor;
			AxisAlignedBB bounds = player.boundingBox.expand(radius, radius, radius);
			
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			{
				bounds.expand(fudgeFactor, fudgeFactor, fudgeFactor);
				
				if ((itemStack.getItemDamage() >> 1) >= 7)
				{
					List<EntityArrow> arrows = world.getEntitiesWithinAABB(EntityArrow.class, bounds);
					
					for (EntityArrow arrow : arrows)
					{
						if (arrow.canBePickedUp == 1 && world.rand.nextInt(6) == 0)
						{
							EntityItem replacement = new EntityItem(world, arrow.posX, arrow.posY, arrow.posZ,
									new ItemStack(Items.arrow));
							world.spawnEntityInWorld(replacement);
						}
						world.removeEntity(arrow);
					}
				}
			}
			
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
					
					e.motionX = x;
					e.motionY = y;
					e.motionZ = z;
					e.isAirBorne = true;
					
					if (e.isCollidedHorizontally)
					{
						e.motionY += 1;
					}
					
					if (world.rand.nextInt(20) == 0)
					{
						float pitch = 0.85f - world.rand.nextFloat() * 3f / 10f;
						world.playSoundEffect(e.posX, e.posY, e.posZ, "mob.endermen.portal", 0.6f, pitch);
					}
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
