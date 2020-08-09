/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.network;

import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import net.dries007.tfc.util.Helpers;

public class PacketHandler
{
    private static final String VERSION = Integer.toString(1);
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(Helpers.identifier("network"), () -> VERSION, VERSION::equals, VERSION::equals);

    public static <MSG> void send(PacketDistributor.PacketTarget target, MSG message)
    {
        CHANNEL.send(target, message);
    }

    public static SimpleChannel get()
    {
        return CHANNEL;
    }

    @SuppressWarnings("UnusedAssignment")
    public static void init()
    {
        int id = 0;

        CHANNEL.registerMessage(id++, ChunkDataUpdatePacket.class, ChunkDataUpdatePacket::encode, ChunkDataUpdatePacket::new, ChunkDataUpdatePacket::handle);
        CHANNEL.registerMessage(id++, ChunkDataRequestPacket.class, ChunkDataRequestPacket::encode, ChunkDataRequestPacket::new, ChunkDataRequestPacket::handle);
        CHANNEL.registerMessage(id++, CalendarUpdatePacket.class, CalendarUpdatePacket::encode, CalendarUpdatePacket::new, CalendarUpdatePacket::handle);
        CHANNEL.registerMessage(id++, SwitchInventoryTabPacket.class, SwitchInventoryTabPacket::encode, SwitchInventoryTabPacket::new, SwitchInventoryTabPacket::handle);
    }
}
