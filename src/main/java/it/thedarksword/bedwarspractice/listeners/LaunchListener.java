package it.thedarksword.bedwarspractice.listeners;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.Session;
import it.thedarksword.bedwarspractice.abstraction.sessions.clutch.ClutchSession;
import it.thedarksword.bedwarspractice.abstraction.sessions.launch.LaunchSession;
import it.thedarksword.bedwarspractice.clutch.sessions.KnockbackClutch;
import it.thedarksword.bedwarspractice.clutch.sessions.WallClutch;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.Optional;

@RequiredArgsConstructor
public class LaunchListener implements Listener {

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
        if(!(optional.get() instanceof LaunchSession)) return;
        LaunchSession session = (LaunchSession) optional.get();

        if(to.getY() < bedwarsPractice.getConfigValue().MIN_Y) {
            session.loose(player);
            session.stop(player);
            return;
        }

        if(session.isRunning()) {
            if(player.getLocation().getX() >= bedwarsPractice.getConfigValue().LAUNCH_START_OFFSET + session.getSpawn().getX() &&
                    player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.GOLD_BLOCK){
                session.win(player);
                session.stop(player);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTNTDoDamage(EntityDamageByEntityEvent event) {

        if (event.getDamager().getType() == EntityType.PRIMED_TNT) {
            event.setDamage(0);
            TNTPrimed tntPrimed = (TNTPrimed) event.getDamager();
            Player source;
            if(tntPrimed.getSource() instanceof Player) {
                source = (Player) tntPrimed.getSource();
            } else {
                source = null;
            }

            Vector vector = event.getEntity().getLocation().add(0.0, 1.0, 0.0).toVector().subtract(tntPrimed.getLocation().toVector());
            double l = vector.length();
            vector.normalize();
            vector.multiply(4.0 / l);

            if (event.getEntity().getType() == EntityType.PLAYER) {
                Player player = (Player) event.getEntity();

                if (source != null && source == event.getEntity()) {
                    player.setVelocity(vector.divide(new Vector(1, 7, 1)));
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageByEntityEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) return;

        if (event.getDamager().getType() == EntityType.FIREBALL) {
            event.setDamage(0);
            Fireball fireball = (Fireball) event.getDamager();
            if (fireball.getShooter() == event.getEntity()) {

                Vector vector = event.getEntity().getLocation().toVector().subtract(fireball.getLocation().toVector());
                vector.normalize();
                vector.setY(1);
                vector.multiply(1.25f);

                event.getEntity().setVelocity(new Vector());

                bedwarsPractice.getServer().getScheduler().runTaskLater(bedwarsPractice, () -> event.getEntity().setVelocity(vector), 1);
            }
        }
    }
}
