package it.thedarksword.bedwarspractice.packets;

import io.netty.channel.Channel;
import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.Session;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Optional;

public class PacketListener extends PacketInjector {

    public PacketListener(BedwarsPractice bedwarsPractice) {
        super(bedwarsPractice);
    }

    @Override
    public Object onPacketInAsync(Player player, Channel channel, Object obj) {

        if (obj instanceof PacketPlayInArmAnimation && player.hasMetadata("session")) {
            return null;
        }

        Optional<Session> optional;

        if (obj instanceof PacketPlayInBlockPlace && (optional = bedwarsPractice.getManager().session(player)).isPresent()) {
            Session session = optional.get();
            return session.handlePlace(bedwarsPractice, player, (PacketPlayInBlockPlace) obj);
        }

        if (obj instanceof PacketPlayInBlockDig && (optional = bedwarsPractice.getManager().session(player)).isPresent()) {
            Session session = optional.get();
            return session.handleBreak(bedwarsPractice, player, (PacketPlayInBlockDig) obj);
        }
        return obj;
    }

    @Override
    public Object onPacketOutAsync(Player player, Channel channel, Object obj) {
        
        return obj;
    }
}
