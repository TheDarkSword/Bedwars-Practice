package it.thedarksword.bedwarspractice.listeners;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.Session;
import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingSession;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

import java.util.Optional;

@RequiredArgsConstructor
public class BridgingListener implements Listener {

    private final BedwarsPractice bedwarsPractice;

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!player.hasMetadata("session"))
            return;

        event.setCancelled(true);

        Optional<Session> optional = bedwarsPractice.getManager().session(player);
        if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR &&
                optional.isPresent()) {
            Session session = optional.get();

            if(session instanceof BridgingSession &&
                    event.getInventory().equals(((BridgingSession)session).getSettingsInventory().getInventory())) {
                ((BridgingSession)session).getSettingsInventory().getItems().computeIfPresent(event.getSlot(),(integer, clickableItem) -> {
                    clickableItem.run(event);
                    return clickableItem;
                });
            }
        }
    }

    @EventHandler
    public void playerMovement(PlayerMoveEvent event) {
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
        if(!(optional.get() instanceof BridgingSession)) return;
        BridgingSession session = (BridgingSession) optional.get();

        /*if(System.currentTimeMillis() - session.getSessionStart() < 450L) {
            event.setTo(from);
            return;
        }*/

        if(to.getY() < bedwarsPractice.getConfigValue().MIN_Y) {
            session.loose(player);
            session.stop(player);
        }

        if(!session.isRunning()) return;

        if(session.getFinishArea().isInside(to)){
            session.win(player);
            session.stop(player);
            return;
        }

        session.movement(from, to);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        if(event.getPlayer().hasMetadata("session")) event.setCancelled(true);
    }

    @EventHandler
    public void entityDamageEvent(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        if (event.getCause() != EntityDamageEvent.DamageCause.VOID) return;

        event.setCancelled(true);
        bedwarsPractice.getManager().session((Player) event.getEntity()).ifPresent(session -> event.getEntity().teleport(session.getSpawn()));
    }
}
