package magicbees.main.utils.net;

import forestry.core.proxy.Proxies;
import io.netty.buffer.Unpooled;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import magicbees.main.utils.LogHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.common.registry.GameData;

public class EventBase {

	private NetworkEventHandler.EventType eventType;
	
	public EventBase() {
		this.eventType = NetworkEventHandler.EventType.UNKNOWN;
	}
	
	public EventBase(NetworkEventHandler.EventType type) {
		this.eventType = type;
	}
	
	public FMLProxyPacket getPacket() {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(bytes);
		
		try {
			data.writeInt(eventType.ordinal());
			writeDataToOutputStream(data);
		}
		catch (IOException e) {
			LogHelper.error("Could not write EventBase data.");
			e.printStackTrace();
		}
		
		return new FMLProxyPacket(Unpooled.wrappedBuffer(bytes.toByteArray()), NetworkEventHandler.CHANNEL_NAME);
	}
	
	public void process(EntityPlayerMP player) {
		
	}
	
	protected void writeDataToOutputStream(DataOutputStream data) {
		return;
	}
	
	protected void readDataFromInputStream(DataInputStream data) {
		return;
	}
	
	protected ItemStack readItemStackFromData(DataInputStream data) throws IOException {
		ItemStack itemstack = null;

		String itemName = data.readUTF();

		if (!itemName.isEmpty()) {
			Item item = GameData.getItemRegistry().getRaw(itemName);
			byte stackSize = data.readByte();
			short meta = data.readShort();
			itemstack = new ItemStack(item, stackSize, meta);

			if (item.isDamageable() || Proxies.common.needsTagCompoundSynched(item)) {
				itemstack.stackTagCompound = this.readNBTTagCompound(data);
			}
		}

		return itemstack;
	}

	protected void writeItemStackToData(ItemStack itemstack, DataOutputStream data) throws IOException {
		if (itemstack == null) {
			data.writeUTF("");
		} else {
			data.writeUTF(GameData.getItemRegistry().getNameForObject(itemstack.getItem()));
			data.writeByte(itemstack.stackSize);
			data.writeShort(itemstack.getItemDamage());

			if (itemstack.getItem().isDamageable() || Proxies.common.needsTagCompoundSynched(itemstack.getItem())) {
				this.writeNBTTagCompound(itemstack.stackTagCompound, data);
			}
		}
	}

	protected NBTTagCompound readNBTTagCompound(DataInputStream data) throws IOException {

		short length = data.readShort();

		if (length < 0) {
			return null;
		} else {
			byte[] compressed = new byte[length];
			data.readFully(compressed);
			return CompressedStreamTools.readCompressed(new ByteArrayInputStream(compressed));
		}

	}

	protected void writeNBTTagCompound(NBTTagCompound nbttagcompound, DataOutputStream data) throws IOException {

		if (nbttagcompound == null) {
			data.writeShort(-1);
		} else {
			byte[] compressed = CompressedStreamTools.compress(nbttagcompound);
			data.writeShort((short) compressed.length);
			data.write(compressed);
		}

	}
}
