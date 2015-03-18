package magicbees.tileentity;

import magicbees.main.CommonProxy;
import magicbees.main.utils.LogHelper;
import magicbees.reference.Names;
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

import forestry.api.apiculture.BeeManager;
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
import forestry.core.config.ForestryItem;
import forestry.core.inventory.InvTools;
import forestry.core.inventory.InventoryAdapter;
import forestry.core.proxy.Proxies;
import forestry.core.utils.Utils;
import forestry.plugins.PluginApiculture;

public class TileEntityThaumicApiary extends TileEntity implements ISidedInventory, IBeeHousing {

    public static final String tileEntityName = CommonProxy.DOMAIN + ".thaumicApiary";
    private GameProfile ownerName;

    // Constants
    private static final int SLOT_QUEEN = 0;
    private static final int SLOT_DRONE = 1;
    private static final int SLOT_FRAME_START = 2;
    private static final int SLOT_FRAME_COUNT = 3;
    private static final int SLOT_INVENTORY_START = 5;
    private static final int SLOT_INVENTORY_COUNT = 7;


    private final IBeekeepingLogic logic;
    private BiomeGenBase biome;
    private int displayHealthMax = 0;
    private int displayHealth = 0;
    private boolean init = false;

    private ItemStack[] items;

    protected final InventoryAdapter inventory = new InventoryAdapter(12, "Items");

    public TileEntityThaumicApiary()
    {
        items = new ItemStack[12];
        logic = PluginApiculture.beeInterface.createBeekeepingLogic(this);
    }

    public void setOwner(EntityPlayer player) { this.ownerName = player.getGameProfile(); }

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

    // TODO: Investigate this :D
    @Override
    public void onQueenChange(ItemStack queenStack) {

    }

    @Override
    public void wearOutEquipment(int amount) {
        int wear = Math.round(amount * PluginApiculture.beeInterface.getBeekeepingMode(worldObj).getWearModifier());

        for (int i = SLOT_FRAME_START; i < SLOT_FRAME_START + SLOT_FRAME_COUNT; i++) {
            if (getStackInSlot(i) == null) {
                continue;
            }
            if (!(getStackInSlot(i).getItem() instanceof  IHiveFrame)) {
                continue;
            }

            setInventorySlotContents(i, ((IHiveFrame) getStackInSlot(i).getItem()).frameUsed(this, getStackInSlot(i),
                            PluginApiculture.beeInterface.getMember(getStackInSlot(SLOT_QUEEN)), wear));
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

    @Override
    public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
        float mod = 1.0f;
        for (int i = SLOT_FRAME_START; i < SLOT_FRAME_START + SLOT_FRAME_COUNT; i++){
            if (getStackInSlot(i) == null)
                continue;
            if (getStackInSlot(i).getItem() instanceof IHiveFrame)
                mod *= ((IHiveFrame) getStackInSlot(i).getItem()).getTerritoryModifier(genome, mod);
        }
        return mod;
    }

    @Override
    public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        float mod = 1.0f;
        for (int i = SLOT_FRAME_START; i < SLOT_FRAME_START + SLOT_FRAME_COUNT; i++){
            if (getStackInSlot(i) == null)
                continue;
            if (getStackInSlot(i).getItem() instanceof IHiveFrame)
                mod *= ((IHiveFrame) getStackInSlot(i).getItem()).getMutationModifier(genome, mate, mod);
        }
        if (this.isMutationBoosted()) {
        	mod = mod * 2f;
        }
        return mod;
    }

    @Override
    public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        float mod = 1.0f;
        for (int i = SLOT_FRAME_START; i < SLOT_FRAME_START + SLOT_FRAME_COUNT; i++){
            if (getStackInSlot(i) == null)
                continue;
            if (getStackInSlot(i).getItem() instanceof IHiveFrame)
                mod *= ((IHiveFrame) getStackInSlot(i).getItem()).getLifespanModifier(genome, mate, mod);
        }
        if (this.isDeathRateBoosted()) {
        	mod = mod / 2f;
        }
        return mod;
    }

    @Override
    public float getProductionModifier(IBeeGenome genome, float currentModifier) {
        float mod = 1.0f;
        for (int i = SLOT_FRAME_START; i < SLOT_FRAME_START + SLOT_FRAME_COUNT; i++){
            if (getStackInSlot(i) == null)
                continue;
            if (getStackInSlot(i).getItem() instanceof IHiveFrame)
                mod *= ((IHiveFrame) getStackInSlot(i).getItem()).getProductionModifier(genome, mod);
        }
        if (this.isWorkBoosted()) {
        	mod = mod * 2f;
        }
        return mod;
    }

    @Override
    public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
        float mod = 1.0f;
        for (int i = SLOT_FRAME_START; i < SLOT_FRAME_START + SLOT_FRAME_COUNT; i++){
            if (getStackInSlot(i) == null)
                continue;
            if (getStackInSlot(i).getItem() instanceof IHiveFrame)
                mod *= ((IHiveFrame) getStackInSlot(i).getItem()).getFloweringModifier(genome, mod);
        }
        return mod;
    }

    @Override
    public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
        float mod = 1.0f;
        for (int i = SLOT_FRAME_START; i < SLOT_FRAME_START + SLOT_FRAME_COUNT; i++){
            if (getStackInSlot(i) == null)
                continue;
            if (getStackInSlot(i).getItem() instanceof IHiveFrame)
                mod *= ((IHiveFrame) getStackInSlot(i).getItem()).getGeneticDecay(genome, mod);
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
        return this.ownerName;
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

    }

    @Override
    public void setErrorState(IErrorState state) {

    }

    @Override
    public int getErrorOrdinal() {
        return 0;
    }

    @Override
    public IErrorState getErrorState() {
        return null;
    }

    public InventoryAdapter getInternalInventory() {
        return inventory;
    }

    // TODO: Implement own version of this
    @Override
    public boolean addProduct(ItemStack product, boolean all) {
        LogHelper.info("Trying to add " + product.getDisplayName() + " to thaumicApiary");
        return InvTools.tryAddStack(getInternalInventory(), product, SLOT_INVENTORY_START, SLOT_INVENTORY_COUNT, all, true);
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
        return Names.Containers.THAUMIC_APIARY;
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
        if(slot == SLOT_QUEEN && PluginApiculture.beeInterface.isMember(itemStack)
                && !PluginApiculture.beeInterface.isDrone(itemStack)) {
            return true;
        }
        else if (slot == SLOT_DRONE && PluginApiculture.beeInterface.isDrone(itemStack)) {
        	return true;
        }
        return slot == SLOT_DRONE && PluginApiculture.beeInterface.isDrone(itemStack);
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

        if (ForestryItem.beeQueenGE.isItemEqual(inventory.getStackInSlot(SLOT_QUEEN))) {
            return PluginApiculture.beeInterface.getMember(getStackInSlot(SLOT_QUEEN)).getHealth();
        }
        else if (ForestryItem.beePrincessGE.isItemEqual(getStackInSlot(SLOT_QUEEN))) {
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
        if (ForestryItem.beeQueenGE.isItemEqual(getStackInSlot(SLOT_QUEEN))) {
            return PluginApiculture.beeInterface.getMember(getStackInSlot(SLOT_QUEEN)).getMaxHealth();
        }
        else if (ForestryItem.beePrincessGE.isItemEqual(getStackInSlot(SLOT_QUEEN))) {
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

        compound.setInteger("BiomeId", biome.biomeID);
        if (logic != null) {
            logic.writeToNBT(compound);
        }

        compound.setTag("Items", items);
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

        if (!Proxies.common.isSimulating(worldObj)) {
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
        if (PluginApiculture.beeInterface.isMated(getStackInSlot(SLOT_QUEEN))) {
            if (getErrorState() == ErrorStateRegistry.getErrorState("OK") && worldObj.getTotalWorldTime() % 2 % 2 == 0) {
                IBee displayQueen = PluginApiculture.beeInterface.getMember(getStackInSlot(SLOT_QUEEN));
                displayQueen.doFX(logic.getEffectData(), this);
            }
        }
    }

    public void updateServerSide() {
        logic.update();

        IBee queen = logic.getQueen();
        if (queen == null) {
            return;
        }

        if (worldObj.getTotalWorldTime() % 200 * 10 == 0) {
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
}
