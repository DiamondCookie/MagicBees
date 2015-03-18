package magicbees.main.utils;

import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;

public class PacketHandler {
	
	public enum PacketTypes {
		INVENTORY_UPDATE,
	}
	
	private FMLEventChannel channel;
	
	public PacketHandler() {
		channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(VersionInfo.ModName);
		channel.register(this);
	}

}
