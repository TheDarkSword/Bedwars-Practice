package it.thedarksword.bedwarspractice.listeners;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.Session;
import it.thedarksword.bedwarspractice.abstraction.sessions.clutch.ClutchSession;
import it.thedarksword.bedwarspractice.clutch.sessions.KnockbackClutch;
import it.thedarksword.bedwarspractice.clutch.sessions.WallClutch;
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

        if(to.getY() < bedwarsPractice.getConfigValue().MIN_Y ||
                (session instanceof WallClutch && to.getY() > bedwarsPractice.getConfigValue().WC_MAX_Y)) {
            session.loose(player);
            if(session.isCheckPointEnabled()) {
                if(session.getCheckPoint() == null) session.stop(player);
            } else session.stop(player);
        }

        if(session.isRunning()) {
            if(session.getFinishArea().isInside(to)){
                session.win(player);
                session.stop(player);
                return;
            }
            Block block = player.getLocation().subtract(0, 1, 0).getBlock();
            if(session instanceof WallClutch){
                if(block.getType() == bedwarsPractice.getConfigValue().WALL_BLOCK ||
                        player.getLocation().subtract(0, 1, 0).getBlock().getType() == bedwarsPractice.getConfigValue().WALL_BLOCK) {
                    session.loose(player);
                    if(session.isCheckPointEnabled()) {
                        if(session.getCheckPoint() == null) session.stop(player);
                    } else session.stop(player);
                }
            }
            if(session.isCheckPointEnabled() &&
                    ((session instanceof KnockbackClutch && block.getType() == bedwarsPractice.getConfigValue().KNOCKBACK_CHECKPOINT) ||
                            (session instanceof WallClutch && block.getType() == bedwarsPractice.getConfigValue().WALL_CHECKPOINT))) {
                Location checkpoint = block.getLocation().add(0.5, 1, 0.5);
                checkpoint.setYaw(-90);
                session.setCheckPoint(checkpoint);
            }
        } else {
            if(session instanceof KnockbackClutch) {
                Location location = player.getLocation().clone().subtract(0, 1, 0);
                if(location.getBlock().getType() == bedwarsPractice.getConfigValue().KNOCKBACK_START ||
                        location.add(0, 0, 1).getBlock().getType() == bedwarsPractice.getConfigValue().KNOCKBACK_START ||
                        location.add(0, 0, -2).getBlock().getType() == bedwarsPractice.getConfigValue().KNOCKBACK_START ||
                        location.add(0, -1, 1).getBlock().getType() == bedwarsPractice.getConfigValue().KNOCKBACK_START)
                    session.start(player);
            }
        }
    }
}
