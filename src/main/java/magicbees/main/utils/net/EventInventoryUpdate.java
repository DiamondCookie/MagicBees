package magicbees.main.utils.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.client.FMLClientHandler;
import magicbees.main.utils.ChunkCoords;
import magicbees.main.utils.LogHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class EventInventoryUpdate extends EventCoords {

	private int slotIndex;
	private ItemStack itemStack;
	
	public EventInventoryUpdate(ChunkCoords position, int slot, ItemStack item) {
		super(NetworkEventHandler.EventType.INVENTORY_UPDATE, position);
		
		this.slotIndex = slot;
		this.itemStack = item;
	}
	
	public EventInventoryUpdate(DataInputStream byteStream) {
		super(NetworkEventHandler.EventType.INVENTORY_UPDATE, byteStream);
		
		this.readDataFromInputStream(byteStream);
	}

	@Override
	protected void writeDataToOutputStream(DataOutputStream byteStream) {
		super.writeDataToOutputStream(byteStream);
		
		try {
			byteStream.writeInt(this.slotIndex);
			this.writeItemStackToData(this.itemStack, byteStream);
		} catch (IOException e) {
			LogHelper.error("Could not write EventInventoryUpdate to byte stream.");
			e.printStackTrace();
		}
	}

	@Override
	protected void readDataFromInputStream(DataInputStream byteStream) {
		super.readDataFromInputStream(byteStream);
		
		try {
			this.slotIndex = byteStream.readInt();
			this.itemStack = this.readItemStackFromData(byteStream);
		} catch (IOException e) {
			LogHelper.error("Could not read EventInventoryUpdate to byte stream.");
			e.printStackTrace();
		}
	}
	
	@Override
	public void process(EntityPlayerMP player) {
		TileEntity tile = FMLClientHandler.instance().getClient().theWorld.getTileEntity(getCoords().x, getCoords().y, getCoords().z);
			
		if (tile != null && tile instanceof IInventory) {
			((IInventory) tile).setInventorySlotContents(slotIndex, itemStack);
		}
	}
}
