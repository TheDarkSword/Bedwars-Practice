package it.thedarksword.bedwarspractice.listeners;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.Session;
import it.thedarksword.bedwarspractice.abstraction.sessions.clutch.ClutchSession;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Optional;

@RequiredArgsConstructor
public class ClutchListener implements Listener {

    private final BedwarsPractice bedwarsPractice;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if ((from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ())
                || !(player.hasMetadata("session")))
            return;

        Optional<Session> optional = bedwarsPractice.getManager().session(player);

        if (!optional.isPresent()) {
            player.removeMetadata("session", bedwarsPractice);
            return;
        }
        if(!(optional.get() instanceof ClutchSession)) return;
        ClutchSession session = (ClutchSession) optional.get();

        if(to.getY() < bedwarsPractice.getConfigValue().MIN_Y) {
            session.loose(player);
            if(session.getCheckPoint() == null)
                session.stop(player);
        }

        if(session.getFinishArea().isInside(to)){
            session.win(player);
            session.stop(player);
            return;
        }

        if(session.isRunning()) {
            Block block = player.getLocation().subtract(0, 1, 0).getBlock();
            if(session.isCheckPointEnabled() &&
                    block.getType() == bedwarsPractice.getConfigValue().KNOCKBACK_CHECKPOINT) {
                Location checkpoint = block.getLocation().add(0.5, 1, 0.5);
                checkpoint.setYaw(-90);
                session.setCheckPoint(checkpoint);
            }
        } else {
            if(player.getLocation().subtract(0, 1, 0).getBlock().getType() == bedwarsPractice.getConfigValue().KNOCKBACK_START)
                session.start(player);
        }
    }
}
