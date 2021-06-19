package it.thedarksword.bedwarspractice.packets;

import io.netty.channel.Channel;
import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.Session;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import org.bukkit.entity.Player;

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

        if (obj instanceof PacketPlayInBlockPlace && /*player.hasMetadata("session")*/ bedwarsPractice.getManager().session(player).isPresent()) {
            Optional<Session> optional = bedwarsPractice.getManager().session(player);
            if (!optional.isPresent()) return obj;
            Session session = optional.get();
            return session.handlePlace(bedwarsPractice, player, (PacketPlayInBlockPlace) obj);
        }

        if (obj instanceof PacketPlayInBlockDig && bedwarsPractice.getManager().session(player).isPresent()) {
            Optional<Session> optional = bedwarsPractice.getManager().session(player);
            if (!optional.isPresent()) return obj;
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
