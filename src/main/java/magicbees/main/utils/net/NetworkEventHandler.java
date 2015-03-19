package magicbees.main.utils.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;

import java.io.DataInputStream;
import java.io.IOException;

import magicbees.main.utils.ChunkCoords;
import magicbees.main.utils.LogHelper;
import magicbees.main.utils.VersionInfo;
import magicbees.main.utils.error.InvalidEventTypeIndexException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;

public class NetworkEventHandler {
	
	public static final String CHANNEL_NAME = VersionInfo.ModName;
	
	public enum EventType {
		UNKNOWN,
		INVENTORY_UPDATE,
		;
	}
	
	private FMLEventChannel channel;
	
	public NetworkEventHandler() {
		channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(CHANNEL_NAME);
		channel.register(this);
	}
	
	@SubscribeEvent
	public void onPacket(ServerCustomPacketEvent event) {
		this.parseAndDispatchPacket(event.packet.payload(), ((NetHandlerPlayServer)event.handler).playerEntity);
	}
	
	@SubscribeEvent
	public void onPacket(ClientCustomPacketEvent event) {
		this.parseAndDispatchPacket(event.packet.payload(), null);
	}
	
	public void sendInventoryUpdate(TileEntity entity, int slotIndex, ItemStack itemStack) {
		EventInventoryUpdate event = new EventInventoryUpdate(new ChunkCoords(entity), slotIndex, itemStack);
		FMLProxyPacket packet = event.getPacket();
		
		sendPacket(packet);
	}
	
	private void sendPacket(FMLProxyPacket packet) {
    	if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
    		channel.sendToAll(packet);
    	}
    	else {
    		channel.sendToServer(packet);
    	}
	}
	
	private void parseAndDispatchPacket(ByteBuf packetPayload, EntityPlayerMP player) {
		try {
			DataInputStream data = new DataInputStream(new ByteBufInputStream(packetPayload));
		
			processEventData(data, player);
			
			data.close();
		} catch (IOException e) {
			LogHelper.info("DataInputStream had a problem closing. Good for it.");
			//e.printStackTrace();
		}
	}

	private void processEventData(DataInputStream data, EntityPlayerMP player) throws IOException {
		int eventId = data.readInt();
		
		if (eventId == EventType.INVENTORY_UPDATE.ordinal()) {
			EventInventoryUpdate eventData = new EventInventoryUpdate(data);
			eventData.process(player);
		}
		else {
			throw new InvalidEventTypeIndexException("");
		}
	}

}
