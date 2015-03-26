package magicbees.tileentity;

import magicbees.api.bees.IMagicApiaryAuraProvider;
import magicbees.bees.BeeManager;
import magicbees.main.CommonProxy;
import magicbees.main.MagicBees;
import magicbees.main.utils.ChunkCoords;
import magicbees.main.utils.ItemStackUtils;
import magicbees.main.utils.LogHelper;
import magicbees.main.utils.net.NetworkEventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
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

public class TileEntityMagicApiary extends TileEntity implements ISidedInventory, IBeeHousing, ITileEntityFlags {

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
    private static int CHARGE_TIME_MUTATION = 1200;
    private static int CHARGE_TIME_DEATH = 400;
    private static int CHARGE_TIME_PRODUCTION = 300;
    
    private IBeekeepingLogic logic;
    private IMagicApiaryAuraProvider auraProvider;
    private ChunkCoords auraProviderPosition;
    private BiomeGenBase biome;
    private int displayHealthMax = 0;
    private int displayHealth = 0;
    private boolean init = false;
    
    private static final int OFFSET_MUTATION = 0;
    private static final int OFFSET_DEATH = 1;
    private static final int OFFSET_PRODUCTION = 2;    
    private static final int FLAG_MUTATIONCHARGE = 1 << OFFSET_MUTATION;
    private static final int FLAG_DEATHCHARGE = 1 << OFFSET_DEATH;
    private static final int FLAG_PRODUCTIONCHARGE = 1 << OFFSET_PRODUCTION;
    private long[] chargeTimestamps = new long[3];
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
        if (this.isProductionBoosted()) {
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
        compound.setLong("timestamp0", chargeTimestamps[0]);
        compound.setLong("timestamp1", chargeTimestamps[1]);
        compound.setLong("timestamp2", chargeTimestamps[2]);
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
        this.chargeTimestamps[0] = compound.getLong("timestamp0");
        this.chargeTimestamps[1] = compound.getLong("timestamp1");
        this.chargeTimestamps[2] = compound.getLong("timestamp2");
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
   		if (this.auraProvider == null) {
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
    
    public boolean isProductionBoosted() {
    	return (flags & FLAG_PRODUCTIONCHARGE) == FLAG_PRODUCTIONCHARGE;
    }
    
    public boolean isDeathRateBoosted() {
    	return (flags & FLAG_DEATHCHARGE) == FLAG_DEATHCHARGE;
    }
    
    public boolean isMutationBoosted() {
    	return (flags & FLAG_MUTATIONCHARGE) == FLAG_MUTATIONCHARGE;
    }
    
    private void updateAuraProvider() {
    	if (worldObj.getTotalWorldTime() % 15 == 0 && locationHasAuraProvider(auraProviderPosition)) {
    		
    	}
    	
    	int oldFlags = flags;
    	if (!isMutationBoosted()) {
    		if (this.auraProvider.getMutationCharge()) {
    			flags |= FLAG_MUTATIONCHARGE;
    			chargeTimestamps[OFFSET_MUTATION] = worldObj.getTotalWorldTime();
    		}
    	}
    	else {
    		if (chargeTimestamps[OFFSET_MUTATION] + CHARGE_TIME_MUTATION <= worldObj.getTotalWorldTime()) {
    			if (!this.auraProvider.getMutationCharge()) {
    				flags = flags & ~FLAG_MUTATIONCHARGE;
    			}
    		}
    	}

    	if (!isDeathRateBoosted()) {
    		if (this.auraProvider.getDeathRateCharge()) {
    			flags |= FLAG_DEATHCHARGE;
    			chargeTimestamps[OFFSET_DEATH] = worldObj.getTotalWorldTime();
    		}
    	}
    	else {
    		if (chargeTimestamps[OFFSET_DEATH] + CHARGE_TIME_DEATH <= worldObj.getTotalWorldTime()) {
    			if (!this.auraProvider.getDeathRateCharge()) {
    				flags = flags & ~FLAG_DEATHCHARGE;
    			}
    		}
    	}

    	if (!isProductionBoosted()) {
    		if (this.auraProvider.getProductionCharge()) {
    			flags |= FLAG_PRODUCTIONCHARGE;
    			chargeTimestamps[OFFSET_PRODUCTION] = worldObj.getTotalWorldTime();
    		}
    	}
    	else {
    		if (chargeTimestamps[OFFSET_PRODUCTION] + CHARGE_TIME_PRODUCTION <= worldObj.getTotalWorldTime()) {
    			if (!this.auraProvider.getProductionCharge()) {
    				flags = flags & ~FLAG_PRODUCTIONCHARGE;
    			}
    		}
    	}
    	
    	if (oldFlags != flags) {
    		NetworkEventHandler.getInstance().sendFlagsUpdate(this, new int[] {flags});
    	}
    }

	private static int MAX_BLOCKS_SEARCH_PER_CHECK = (AURAPROVIDER_SEARCH_RADIUS * 2 + 1) * 12;
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
    		if (locationHasAuraProvider(auraProviderPosition)) {
    			IMagicApiaryAuraProvider provider = (IMagicApiaryAuraProvider)(worldObj.getTileEntity(auraProviderPosition.x,
    																				auraProviderPosition.y,
    																				auraProviderPosition.x));
    			if (provider == null) {
    				// ... well, it WAS here...
    				this.auraProviderPosition = null;
    			}
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

    private boolean locationHasAuraProvider(ChunkCoords coords) {
		return locationHasAuraProvider(coords.x, coords.y, coords.z);
	}
    
	private boolean locationHasAuraProvider(int x, int y, int z) {
		Chunk chunk = worldObj.getChunkFromBlockCoords(x, z);
		x %= 16;
		z %= 16;
		if (x < 0) {
			x += 16;
		}
		if (z < 0) {
			z += 16;
		}
		ChunkPosition cPos = new ChunkPosition(x, y, z);
		TileEntity entity = (TileEntity)chunk.chunkTileEntityMap.get(cPos);
		if (entity != null && entity instanceof IMagicApiaryAuraProvider) {
			return true;
		}
		return false;
	}

	@Override
	public void setFlags(int[] flagArray) {
		if (flagArray.length == 1) {
			this.flags = flagArray[0];
		}
	}
}
