package magicbees.tileentity;

import com.mojang.authlib.GameProfile;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.IIndividual;
import forestry.apiculture.gadgets.TileBeehouse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityThaumicApiary extends TileEntity implements IInventory, IBeeHousing {

    private GameProfile ownerName;

    public void setOwner(EntityPlayer player) { this.ownerName = player.getGameProfile(); }

    @Override
    public ItemStack getQueen() {
        return null;
    }

    @Override
    public ItemStack getDrone() {
        return null;
    }

    @Override
    public void setQueen(ItemStack itemstack) {

    }

    @Override
    public void setDrone(ItemStack itemstack) {

    }

    @Override
    public boolean canBreed() {
        return false;
    }

    @Override
    public void onQueenChange(ItemStack queen) {

    }

    @Override
    public void wearOutEquipment(int amount) {

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
        return 0;
    }

    @Override
    public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return 0;
    }

    @Override
    public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return 0;
    }

    @Override
    public float getProductionModifier(IBeeGenome genome, float currentModifier) {
        return 0;
    }

    @Override
    public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
        return 0;
    }

    @Override
    public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
        return 0;
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
        return null;
    }

    @Override
    public int getXCoord() {
        return 0;
    }

    @Override
    public int getYCoord() {
        return 0;
    }

    @Override
    public int getZCoord() {
        return 0;
    }

    @Override
    public int getBiomeId() {
        return 0;
    }

    @Override
    public EnumTemperature getTemperature() {
        return null;
    }

    @Override
    public EnumHumidity getHumidity() {
        return null;
    }

    @Override
    public void setErrorState(int state) {

    }

    @Override
    public int getErrorOrdinal() {
        return 0;
    }

    @Override
    public boolean addProduct(ItemStack product, boolean all) {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int p_70301_1_) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {

    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return false;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return false;
    }
}
