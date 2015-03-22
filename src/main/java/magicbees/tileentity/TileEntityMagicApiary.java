package magicbees.tileentity;

import magicbees.api.bees.IMagicApiaryAuraProvider;
import magicbees.bees.BeeManager;
import magicbees.main.CommonProxy;
import magicbees.main.MagicBees;
import magicbees.main.utils.ChunkCoords;
import magicbees.main.utils.ItemStackUtils;
import magicbees.main.utils.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.Constants;

import com.mojang.authlib.GameProfile;

import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeekeepingLogic;
import forestry.api.apiculture.IHiveFrame;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.ErrorStateRegistry;
import forestry.api.core.IErrorState;
import forestry.api.genetics.IIndividual;
import forestry.core.utils.Utils;

public class TileEntityMagicApiary extends TileEntity implements ISidedInventory, IBeeHousing {

    public static final String tileEntityName = CommonProxy.DOMAIN + ".magicApiary";
    private GameProfile ownerProfile;

    // Constants
    private static final int SLOT_QUEEN = 0;
    private static final int SLOT_DRONE = 1;
    private static final int SLOT_FRAME_START = 2;
    private static final int SLOT_FRAME_COUNT = 3;
    private static final int SLOT_PRODUCTS_START = 5;
    private static final int SLOT_PRODUCTS_COUNT = 7;
    
    private static final int AURAPROVIDER_SEARCH_RADIUS = 6;
    
    private IBeekeepingLogic logic;
    private boolean hasAuraProvider = false;
    private IMagicApiaryAuraProvider auraProvider;
    private ChunkCoords auraProviderPosition;
    private BiomeGenBase biome;
    private int displayHealthMax = 0;
    private int displayHealth = 0;
    private boolean init = false;
    
    private static final int FLAG_MUTATIONCHARGE = 1 << 0;
    private static final int FLAG_DEATHCHARGE = 1 << 1;
    private static final int FLAG_PRODUCTION = 1 << 2;
    private int flags;
    
    private IErrorState errorState = ErrorStateRegistry.getErrorState("ok");

    private ItemStack[] items;

    public TileEntityMagicApiary(){
    	flags = 0;
        items = new ItemStack[12];
        logic = BeeManager.beeRoot.createBeekeepingLogic(this);
    }

    public void setOwner(EntityPlayer player) {
    	this.ownerProfile = player.getGameProfile();
    }

    @Override
    public ItemStack getQueen() {
        return getStackInSlot(SLOT_QUEEN);
    }

    @Override
    public ItemStack getDrone() {
        return getStackInSlot(SLOT_DRONE);
    }

    @Override
    public void setQueen(ItemStack itemstack) {
        setInventorySlotContents(SLOT_QUEEN, itemstack);
    }

    @Override
    public void setDrone(ItemStack itemstack) {
        setInventorySlotContents(SLOT_DRONE, itemstack);
    }

    @Override
    public boolean canBreed() {
        return true;
    }

    @Override
    public void onQueenChange(ItemStack queenStack) {
    	if (!worldObj.isRemote) {
    		MagicBees.object.netHandler.sendInventoryUpdate(this, SLOT_QUEEN, queenStack);
    	}
    }

    @Override
    public void wearOutEquipment(int amount) {
        int wear = Math.round(amount * BeeManager.beeRoot.getBeekeepingMode(worldObj).getWearModifier());

        for (int i = SLOT_FRAME_START; i < SLOT_FRAME_START + SLOT_FRAME_COUNT; i++) {
            if (getStackInSlot(i) == null) {
                continue;
            }
            if (!(getStackInSlot(i).getItem() instanceof  IHiveFrame)) {
                continue;
            }

            setInventorySlotContents(i, ((IHiveFrame) getStackInSlot(i).getItem()).frameUsed(this, getStackInSlot(i),
                            BeeManager.beeRoot.getMember(getStackInSlot(SLOT_QUEEN)), wear));
        }
    }

    @Override
    public void onQueenDeath(IBee queen) {

    }

    @Override
    public void onPostQueenDeath(IBee queen) {

    }

    @Override
    public boolean onPollenRetrieved(IBee queen, IIndividual pollen, boolean isHandled) {
        return false;
    }

    @Override
    public boolean onEggLaid(IBee queen) {
        return false;
    }

	private boolean isItemStackFrame(int i) {
		return getStackInSlot(i) != null && getStackInSlot(i).getItem() instanceof IHiveFrame;
	}

    @Override
    public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
        float mod = 1.0f;
        for (int slotIndex = SLOT_FRAME_START; slotIndex < SLOT_FRAME_START + SLOT_FRAME_COUNT; slotIndex++) {
            if (isItemStackFrame(slotIndex)) {
                mod *= ((IHiveFrame) getStackInSlot(slotIndex).getItem()).getTerritoryModifier(genome, mod);
            }
        }
        return mod;
    }

    @Override
    public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        float mod = 1.0f;
        for (int slotIndex = SLOT_FRAME_START; slotIndex < SLOT_FRAME_START + SLOT_FRAME_COUNT; slotIndex++) {
            if (isItemStackFrame(slotIndex)) {
                mod *= ((IHiveFrame) getStackInSlot(slotIndex).getItem()).getMutationModifier(genome, mate, mod);
            }
        }
        if (this.isMutationBoosted()) {
        	mod = mod * 2f;
        }
        return mod;
    }

    @Override
    public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        float mod = 1.0f;
        for (int slotIndex = SLOT_FRAME_START; slotIndex < SLOT_FRAME_START + SLOT_FRAME_COUNT; slotIndex++) {
            if (isItemStackFrame(slotIndex)) {
                mod *= ((IHiveFrame) getStackInSlot(slotIndex).getItem()).getLifespanModifier(genome, mate, mod);
            }
        }
        if (this.isDeathRateBoosted()) {
        	mod = mod / 2f;
        }
        return mod;
    }

    @Override
    public float getProductionModifier(IBeeGenome genome, float currentModifier) {
        float mod = 0.9f;
        for (int slotIndex = SLOT_FRAME_START; slotIndex < SLOT_FRAME_START + SLOT_FRAME_COUNT; slotIndex++){
            if (isItemStackFrame(slotIndex)) {
                mod *= ((IHiveFrame) getStackInSlot(slotIndex).getItem()).getProductionModifier(genome, mod);
            }
        }
        if (this.isWorkBoosted()) {
        	mod = mod * 2f;
        }
        return mod;
    }

    @Override
    public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
        float mod = 1.0f;
        for (int slotIndex = SLOT_FRAME_START; slotIndex < SLOT_FRAME_START + SLOT_FRAME_COUNT; slotIndex++) {
            if (isItemStackFrame(slotIndex)) {
                mod *= ((IHiveFrame) getStackInSlot(slotIndex).getItem()).getFloweringModifier(genome, mod);
            }
        }
        return mod;
    }

    @Override
    public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
        float mod = 0.8f;
        for (int slotIndex = SLOT_FRAME_START; slotIndex < SLOT_FRAME_START + SLOT_FRAME_COUNT; slotIndex++) {
            if (isItemStackFrame(slotIndex)) {
                mod *= ((IHiveFrame) getStackInSlot(slotIndex).getItem()).getGeneticDecay(genome, mod);
            }
        }
        return mod;
    }

    @Override
    public boolean isSealed() {
        return false;
    }

    @Override
    public boolean isSelfLighted() {
        return false;
    }

    @Override
    public boolean isSunlightSimulated() {
        return false;
    }

    @Override
    public boolean isHellish() {
        return false;
    }

    @Override
    public GameProfile getOwnerName() {
        return this.ownerProfile;
    }

    @Override
    public World getWorld() {
        return worldObj;
    }

    @Override
    public int getXCoord() {
        return xCoord;
    }

    @Override
    public int getYCoord() {
        return yCoord;
    }

    @Override
    public int getZCoord() {
        return zCoord;
    }

    @Override
    public int getBiomeId() {
        return biome.biomeID;
    }

    @Override
    public BiomeGenBase getBiome() {
        return biome;
    }

    @Override
    public EnumTemperature getTemperature() {
        return EnumTemperature.getFromValue(getExactTemperature());
    }

    @Override
    public EnumHumidity getHumidity() {
        return EnumHumidity.getFromValue(getExactHumidity());
    }

    @Override
    public void setErrorState(int state) {
    	this.errorState = ErrorStateRegistry.getErrorState((short) state);
    }

    @Override
    public void setErrorState(IErrorState state) {
    	this.errorState = state;
    }

    @Override
    public int getErrorOrdinal() {
        return this.errorState.getID();
    }

    @Override
    public IErrorState getErrorState() {
        return this.errorState;
    }

    @Override
    public boolean addProduct(ItemStack product, boolean all) {
    	int countAdded = ItemStackUtils.addItemToInventory(this, product, SLOT_PRODUCTS_START, SLOT_PRODUCTS_COUNT);
    	
    	if (all) {
    		return countAdded == product.stackSize;
    	}
    	else {
    		return countAdded > 0;
    	}
    }

    @Override
    public int getSizeInventory() {
        return items.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return items[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        ItemStack itemStack = getStackInSlot(i);

        if (itemStack != null) {
            if (itemStack.stackSize <= j) {
                setInventorySlotContents(i, null);
            }else{
                itemStack = itemStack.splitStack(j);
                markDirty();
            }
        }

        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        ItemStack item = getStackInSlot(i);
        setInventorySlotContents(i, null);
        return item;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {
        items[i] = itemStack;

        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()){
            itemStack.stackSize = getInventoryStackLimit();
        }

        markDirty();
    }

    @Override
    public String getInventoryName() {
        return tileEntityName;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        return entityPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return true;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
        if(slot == SLOT_QUEEN && BeeManager.beeRoot.isMember(itemStack)
                && !BeeManager.beeRoot.isDrone(itemStack)) {
            return true;
        }
        else if (slot == SLOT_DRONE && BeeManager.beeRoot.isDrone(itemStack)) {
        	return true;
        }
        return slot == SLOT_DRONE && BeeManager.beeRoot.isDrone(itemStack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
        switch (slot){
            case SLOT_QUEEN:
            case SLOT_DRONE:
            case SLOT_FRAME_START:
            case SLOT_FRAME_START + 1:
            case SLOT_FRAME_START + 2:
                return false;
            default:
                return true;
        }

    }

    private int getHealthDisplay() {
        if (getStackInSlot(SLOT_QUEEN) == null) {
            return 0;
        }

        if (BeeManager.beeRoot.isMated(getStackInSlot(SLOT_QUEEN))) {
            return BeeManager.beeRoot.getMember(getStackInSlot(SLOT_QUEEN)).getHealth();
        }
        else if (!BeeManager.beeRoot.isDrone(getStackInSlot(SLOT_QUEEN))) {
            return displayHealth;
        }
        else {
            return 0;
        }
    }

    private int getMaxHealthDisplay() {
        if (getStackInSlot(SLOT_QUEEN) == null) {
            return 0;
        }
        if (BeeManager.beeRoot.isMated(getStackInSlot(SLOT_QUEEN))) {
            return BeeManager.beeRoot.getMember(getStackInSlot(SLOT_QUEEN)).getMaxHealth();
        }
        else if (!BeeManager.beeRoot.isDrone(getStackInSlot(SLOT_QUEEN))) {
            return displayHealthMax;
        }
        else {
            return 0;
        }
    }

    public int getHealthScaled(int i) {
        if (getMaxHealthDisplay() == 0) {
            return 0;
        }

        return (getHealthDisplay() * i) / getMaxHealthDisplay();
    }

    public int getTemperatureScaled(int i) {
        return Math.round(i * (getExactTemperature() / 2));
    }

    public int getHumidityScaled(int i) {
        return Math.round(i * getExactHumidity());
    }

    /* Saving and loading */
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        NBTTagList items = new NBTTagList();

        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack itemStack = getStackInSlot(i);

            if (itemStack != null) {
                NBTTagCompound item = new NBTTagCompound();
                item.setByte("Slot", (byte)i);
                itemStack.writeToNBT(item);
                items.appendTag(item);
            }
        }
        compound.setTag("Items", items);

        compound.setInteger("BiomeId", biome.biomeID);
        if (logic != null) {
            logic.writeToNBT(compound);
        }

        ChunkCoords.writeToNBT(this.auraProviderPosition, compound);
        
        compound.setInteger("flags", this.flags);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        NBTTagList items = compound.getTagList("Items", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < items.tagCount(); i++) {
            NBTTagCompound item = (NBTTagCompound)items.getCompoundTagAt(i);
            int slot = item.getByte("Slot");

            if (slot >= 0 && slot < getSizeInventory()) {
                setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
            }
        }

        int biomeId = compound.getInteger("BiomeId");
        biome = BiomeGenBase.getBiome(biomeId);
        logic.readFromNBT(compound);
        
        this.auraProviderPosition = ChunkCoords.readFromNBT(compound);
        this.flags = compound.getInteger("flags");
    }

    public float getExactTemperature() {
        return biome.temperature;
    }

    public float getExactHumidity() {
        return biome.rainfall;
    }

    @Override
    public void updateEntity() {
        if (init == false) {
            init = true;
            updateBiome();
        }

        if (worldObj.isRemote) {
            updateClientSide();
        }
        else {
        	updateServerSide();
        }
    }

    public void updateBiome() {
        if (worldObj != null) {
            if (biome == null) {
                BiomeGenBase biome = Utils.getBiomeAt(worldObj, xCoord, yCoord);
                if (biome != null) {
                    this.biome = biome;
                    setErrorState(ErrorStateRegistry.getErrorState("OK"));
                }
            }
        }
    }

    public void updateClientSide() {
        if (BeeManager.beeRoot.isMated(getStackInSlot(SLOT_QUEEN))) {
            if (getErrorState() == ErrorStateRegistry.getErrorState("OK") && worldObj.getTotalWorldTime() % 10 == 0) {
                IBee displayQueen = BeeManager.beeRoot.getMember(getStackInSlot(SLOT_QUEEN));
                displayQueen.doFX(logic.getEffectData(), this);
            }
        }
    }

    public void updateServerSide() {
   		if (!this.hasAuraProvider) {
   			findAuraProvider();
   		}
   		else {
   			updateAuraProvider();
   		}
    	
        logic.update();

        IBee queen = logic.getQueen();
        if (queen == null) {
            return;
        }

        if (worldObj.getTotalWorldTime() % 5 == 0) {
            onQueenChange(getStackInSlot(SLOT_QUEEN));
        }
    }

    public void getGUINetworkData(int i, int j) {
        if (logic == null) {
            return;
        }

        switch(i) {
            case 0:
                displayHealth = j;
                break;
            case 1: displayHealthMax = j;
                break;
        }
    }

    public void sendGUINetworkData(Container container, ICrafting iCrafting) {
        if (logic == null) {
            return;
        }

        iCrafting.sendProgressBarUpdate(container, 0, logic.getBreedingTime());
        iCrafting.sendProgressBarUpdate(container, 1, logic.getTotalBreedingTime());
    }
    
    public boolean isWorkBoosted() {
    	return false;
    }
    
    public boolean isDeathRateBoosted() {
    	return false;
    }
    
    public boolean isMutationBoosted() {
    	return false;
    }
    
    private void updateAuraProvider() {
    	
    }
    
    private static final int MAX_BLOCKS_SEARCH_PER_CHECK = (AURAPROVIDER_SEARCH_RADIUS * 2 + 1) * 2;
    private void findAuraProvider() {
    	if (worldObj.getTotalWorldTime() % 10 != 0) {
    		return;
    	}
    	
    	if (this.auraProviderPosition == null) {
    		this.auraProviderPosition = new ChunkCoords(0,
    				xCoord - AURAPROVIDER_SEARCH_RADIUS,
    				(yCoord - AURAPROVIDER_SEARCH_RADIUS >= 0) ? yCoord - AURAPROVIDER_SEARCH_RADIUS : 0,
    				zCoord - AURAPROVIDER_SEARCH_RADIUS);
    		LogHelper.info("Apiary starting search");
    	}
    	else {
    		// Will end up here after loading from save with a valid auraProvider.
    		if (locationHasAuraProvider(auraProviderPosition.x, auraProviderPosition.y, auraProviderPosition.z)) {
    			this.auraProvider = (IMagicApiaryAuraProvider)(worldObj.getTileEntity(auraProviderPosition.x,
    																				auraProviderPosition.y,
    																				auraProviderPosition.x));
    			this.hasAuraProvider = true;
    			return;
    		}
    	}
    	
    	int blocksCounted = 0;
    	int x = auraProviderPosition.x;
    	int y = auraProviderPosition.y;
    	int z = auraProviderPosition.z;
    	while ((y < yCoord + AURAPROVIDER_SEARCH_RADIUS) && blocksCounted < MAX_BLOCKS_SEARCH_PER_CHECK) {
    		while ((z < zCoord + AURAPROVIDER_SEARCH_RADIUS) && blocksCounted < MAX_BLOCKS_SEARCH_PER_CHECK) {
    			while ((x < xCoord + AURAPROVIDER_SEARCH_RADIUS) && blocksCounted < MAX_BLOCKS_SEARCH_PER_CHECK) {
    	    		if (locationHasAuraProvider(x, y, z)) {
    	    			LogHelper.info("Apiary found aura provider!");
    	    			this.auraProvider = (IMagicApiaryAuraProvider)(worldObj.getTileEntity(x, y, z));
    	    			this.hasAuraProvider = true;
    	    	    	saveAuraProviderPosition(x, y, z);
    	    	    	return;
    	    		}    				

    				++blocksCounted;
    	    		++x;
    			}
	    		if (blocksCounted < MAX_BLOCKS_SEARCH_PER_CHECK) {
	    			++z;
	    			x = xCoord - AURAPROVIDER_SEARCH_RADIUS;
    			}
    		}
    		if (blocksCounted < MAX_BLOCKS_SEARCH_PER_CHECK) {
    			++y;
    			z = zCoord - AURAPROVIDER_SEARCH_RADIUS;
    		}
    	}

    	LogHelper.info("Apiary suspending search");
    	
    	if (auraProvider == null) {
	    	saveAuraProviderPosition(x, y, z);
	    	
	    	if (auraProviderPosition.y >= yCoord + AURAPROVIDER_SEARCH_RADIUS) {
	    		LogHelper.info("Apiary finished scanning blocks; did not find aura provider.");
	    		this.auraProviderPosition = null;
	    		return;
	    	}
    	}
    }

	private void saveAuraProviderPosition(int x, int y, int z) {
		auraProviderPosition = new ChunkCoords(auraProviderPosition.dimension, x, y, z);
	}

	private boolean locationHasAuraProvider(int x, int y, int z) {
		TileEntity entity = worldObj.getTileEntity(x, y, z);
		if (entity != null && entity instanceof IMagicApiaryAuraProvider) {
			return true;
		}
		return false;
	}
}