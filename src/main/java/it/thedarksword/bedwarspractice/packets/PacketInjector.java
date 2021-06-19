package it.thedarksword.bedwarspractice.packets;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import it.thedarksword.bedwarspractice.BedwarsPractice;
import lombok.RequiredArgsConstructor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.logging.Level;

@RequiredArgsConstructor
public abstract class PacketInjector {

    protected final BedwarsPractice bedwarsPractice;

    public void addPlayer(Player player) {
        try {
            CraftPlayer craftPlayer = (CraftPlayer) player;
            Channel channel = craftPlayer.getHandle().playerConnection.networkManager.channel;
            if(channel.pipeline().get("PacketInjector") == null) {
                channel.pipeline().addBefore("packet_handler", "PacketInjector", new PacketInterceptor(player));
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void removePlayer(Player player) {
        try {
            CraftPlayer craftPlayer = (CraftPlayer) player;
            Channel channel = craftPlayer.getHandle().playerConnection.networkManager.channel;
            if(channel.pipeline().get("PacketInjector") != null) {
                channel.pipeline().remove("PacketInjector");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Invoked when the server is starting to send a packet to a player.
     * <p>
     * Note that this is not executed on the main thread.
     *
     * @param receiver - the receiving player, NULL for early login/status packets.
     * @param channel - the channel that received the packet. Never NULL.
     * @param packet - the packet being sent.
     * @return The packet to send instead, or NULL to cancel the transmission.
     */
    public Object onPacketOutAsync(Player receiver, Channel channel, Object packet) {
        return packet;
    }

    /**
     * Invoked when the server has received a packet from a given player.
     * <p>
     * Use {@link Channel#remoteAddress()} to get the remote address of the client.
     *
     * @param sender - the player that sent the packet, NULL for early login/status packets.
     * @param channel - channel that received the packet. Never NULL.
     * @param packet - the packet being received.
     * @return The packet to recieve instead, or NULL to cancel.
     */
    public Object onPacketInAsync(Player sender, Channel channel, Object packet) {
        return packet;
    }

    /**
     * Channel handler that is inserted into the player's channel pipeline, allowing us to intercept sent and received packets.
     *
     * @author Kristian
     */
    private final class PacketInterceptor extends ChannelDuplexHandler {
        // Updated by the login event
        public volatile Player player;

        public PacketInterceptor(Player player) {
            this.player = player;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            try {
                msg = onPacketInAsync(player, ctx.channel(), msg);
            } catch (Exception ignored) {
                //bedwarsPractice.getLogger().log(Level.SEVERE, "Error in onPacketInAsync().", e);
            }

            if (msg != null) {
                super.channelRead(ctx, msg);
            }
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            try {
                msg = onPacketOutAsync(player, ctx.channel(), msg);
            } catch (Exception e) {
                bedwarsPractice.getLogger().log(Level.SEVERE, "Error in onPacketOutAsync().", e);
            }

            if (msg != null) {
                super.write(ctx, msg, promise);
            }
        }
    }
}
