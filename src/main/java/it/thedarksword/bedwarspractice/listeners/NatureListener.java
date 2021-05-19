package it.thedarksword.bedwarspractice.listeners;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.Session;
import it.thedarksword.bedwarspractice.abstraction.sessions.clutch.ClutchSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

@RequiredArgsConstructor
public class NatureListener implements Listener {

    private final BedwarsPractice bedwarsPractice;

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onSettings(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Optional<Session> optional = bedwarsPractice.getManager().session(player);
        ItemStack hand = player.getItemInHand();
        if(hand != null && optional.isPresent()){
            Material handType = hand.getType();
            Session session = optional.get();
            if(handType == bedwarsPractice.getConfigValue().SETTINGS_MATERIAL) {
                optional.get().getSettingsInventory().open(player);
            } else if(handType == bedwarsPractice.getConfigValue().MODE_MATERIAL) {
                optional.get().getModeInventory().open(player);
            } else if(handType == bedwarsPractice.getConfigValue().CHECKPOINT_ENABLED_MATERIAL) {
                if(hand.getData().getData() == bedwarsPractice.getConfigValue().CHECKPOINT_ENABLED_COLOR.getDyeData()) {
                    player.setItemInHand(bedwarsPractice.getConstantObjects().getCheckpointDisabled());
                    if(session instanceof ClutchSession) {
                        ClutchSession clutchSession = (ClutchSession) session;
                        clutchSession.setCheckPointEnabled(false);
                    }
                } else {
                    player.setItemInHand(bedwarsPractice.getConstantObjects().getCheckpointEnabled());
                    if(session instanceof ClutchSession) {
                        ClutchSession clutchSession = (ClutchSession) session;
                        clutchSession.setCheckPointEnabled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        if(!event.getWhoClicked().hasMetadata("session")) return;
        event.setCancelled(true);

        if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
            if(event.getInventory().equals(bedwarsPractice.getInventories().getModeInventory().getInventory())) {
                bedwarsPractice.getInventories().getModeInventory().getItems().get(event.getSlot()).run(event);
            } else if(event.getInventory().equals(bedwarsPractice.getInventories().getBridgingSpawnInventory().getInventory())) {
                bedwarsPractice.getInventories().getBridgingSpawnInventory().getItems().get(event.getSlot()).run(event);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(event.getPlayer().hasMetadata("session") &&
                event.getInventory().equals(bedwarsPractice.getInventories().getBridgingSpawnInventory().getInventory())) {
            Player player = (Player) event.getPlayer();
            bedwarsPractice.getManager().session(player).ifPresent(session ->
                    session.pasteSchematic(player, bedwarsPractice.getSchematic(),
                            bedwarsPractice.getSpawns().getBridging().getSchematicSpawn().cloneLocation()));
        }
    }

    @EventHandler
    public void weatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState())
            event.setCancelled(true);
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        event.setFoodLevel(20);
        event.setCancelled(true);
    }

    @EventHandler
    public void entityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        if (event.getCause() == EntityDamageEvent.DamageCause.CUSTOM)
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onPhysic(BlockPhysicsEvent event) {
        event.setCancelled(true);
    }

    @SneakyThrows
    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        bedwarsPractice.getMySQLManager().savePlayer(event.getUniqueId().toString(), event.getName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.getPlayer().removeMetadata("session", bedwarsPractice);
        bedwarsPractice.getManager().endSession(event.getPlayer());
    }
}
