package it.thedarksword.bedwarspractice.listeners;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.Session;
import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingSession;
import it.thedarksword.bedwarspractice.abstraction.sessions.clutch.ClutchSession;
import it.thedarksword.bedwarspractice.bridging.sessions.straight.none.ShortStraightBridging;
import it.thedarksword.bedwarspractice.clutch.sessions.KnockbackClutch;
import it.thedarksword.bedwarspractice.inventories.BaseInventory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

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
            if(handType == bedwarsPractice.getConfigValue().SETTINGS_MATERIAL && session instanceof BridgingSession) {
                ((BridgingSession)optional.get()).getSettingsInventory().open(player);
            } else if(handType == bedwarsPractice.getConfigValue().MODE_MATERIAL) {
                bedwarsPractice.getInventories().getModeInventory().open(player);
            } else if (handType == bedwarsPractice.getConfigValue().KBC_DIFFICULTY_MATERIAL && session instanceof KnockbackClutch) {
                bedwarsPractice.getInventories().getKnockBackClutchDifficultyInventory().open(player);
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
            } else if(hand.getType() == Material.BED) {
                player.performCommand("leave");
            }
        }
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        if(!event.getWhoClicked().hasMetadata("session")) return;
        event.setCancelled(true);

        if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
            if(event.getInventory().equals(bedwarsPractice.getInventories().getModeInventory().getInventory())) {
                //bedwarsPractice.getInventories().getModeInventory().getItems().get(event.getSlot()).run(event);
                bedwarsPractice.getInventories().getModeInventory().getItems().computeIfPresent(event.getSlot(),(integer, clickableItem) -> {
                    clickableItem.run(event);
                    return clickableItem;
                });
            } else if(event.getInventory().equals(bedwarsPractice.getInventories().getBridgingSpawnInventory().getInventory())) {
                //bedwarsPractice.getInventories().getBridgingSpawnInventory().getItems().get(event.getSlot()).run(event);
                bedwarsPractice.getInventories().getBridgingSpawnInventory().getItems().computeIfPresent(event.getSlot(), ((integer, clickableItem) -> {
                    clickableItem.run(event);
                    return clickableItem;
                }));
            } else if(event.getInventory().equals(bedwarsPractice.getInventories().getKnockBackClutchDifficultyInventory().getInventory())) {
                bedwarsPractice.getInventories().getKnockBackClutchDifficultyInventory().getItems().computeIfPresent(event.getSlot(), (((integer, clickableItem) -> {
                    clickableItem.run(event);
                    return clickableItem;
                })));
            } else if(event.getInventory().equals(bedwarsPractice.getInventories().getBlocksInventory().getInventory())) {
                bedwarsPractice.getInventories().getBlocksInventory().getItems().computeIfPresent(event.getSlot(), (((integer, clickableItem) -> {
                    clickableItem.run(event);
                    return clickableItem;
                })));
            } else if(event.getInventory().equals(bedwarsPractice.getInventories().getTopsInventory().getInventory())) {
                bedwarsPractice.getInventories().getTopsInventory().getItems().computeIfPresent(event.getSlot(), (((integer, clickableItem) -> {
                    clickableItem.run(event);
                    return clickableItem;
                })));
            } else if(event.getInventory().equals(bedwarsPractice.getInventories().getTopBridgingInventory().getInventory())) {
                bedwarsPractice.getInventories().getTopBridgingInventory().getItems().computeIfPresent(event.getSlot(), (((integer, clickableItem) -> {
                    clickableItem.run(event);
                    return clickableItem;
                })));
            } else if(event.getInventory().equals(bedwarsPractice.getInventories().getTopKnockbackClutchInventory().getInventory())) {
                bedwarsPractice.getInventories().getTopKnockbackClutchInventory().getItems().computeIfPresent(event.getSlot(), (((integer, clickableItem) -> {
                    clickableItem.run(event);
                    return clickableItem;
                })));
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(event.getPlayer().hasMetadata("session") &&
                event.getInventory().equals(bedwarsPractice.getInventories().getBridgingSpawnInventory().getInventory())) {
            Player player = (Player) event.getPlayer();
            bedwarsPractice.getManager().session(player).ifPresent(session ->
                    bedwarsPractice.getServer().getScheduler().runTaskLater(bedwarsPractice, () ->
                    session.pasteSchematic(player, bedwarsPractice.getSchematic(),
                    bedwarsPractice.getSpawns().getBridging().getSchematicSpawn().cloneLocation()), 20));
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

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(!event.getPlayer().hasPermission("bwp.admin") || bedwarsPractice.getManager().session(event.getPlayer()).isPresent()) {
            event.setCancelled(true);
        }
    }

    @SneakyThrows
    @EventHandler
    public void onPreJoin(AsyncPlayerPreLoginEvent event) {
        bedwarsPractice.getMySQLManager().savePlayer(event.getUniqueId().toString(), event.getName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        CraftPlayer player = (CraftPlayer) event.getPlayer();
        PacketPlayOutPlayerInfo playerInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, player.getHandle());
        for(CraftPlayer other : bedwarsPractice.getCraftServer().getOnlinePlayers()) {
            player.hidePlayer(other);
            other.hidePlayer(player);

            player.getHandle().playerConnection.sendPacket(
                    new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, other.getHandle()));
            other.getHandle().playerConnection.sendPacket(playerInfo);
        }
        BridgingSession session = new ShortStraightBridging(bedwarsPractice);
        player.setMetadata("session", new FixedMetadataValue(bedwarsPractice, session.getType().name()));
        bedwarsPractice.getManager().newSession(player, session);
        event.setJoinMessage("");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        CraftPlayer player = (CraftPlayer) event.getPlayer();
        PacketPlayOutPlayerInfo playerInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, player.getHandle());
        for(CraftPlayer other : bedwarsPractice.getCraftServer().getOnlinePlayers()) {
            other.getHandle().playerConnection.sendPacket(playerInfo);
        }
        player.removeMetadata("session", bedwarsPractice);
        bedwarsPractice.getManager().endSession(player);
        event.setQuitMessage("");
    }

    /*@EventHandler (priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        event.setFormat(ChatColor.GRAY + "%s " + ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "%s");
    }*/

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        /*Block block = event.getBlockPlaced();
        if(block != null){
            bedwarsPractice.getLogger().warning(event.getPlayer().getName() + " ha piazzato " + block.getType().name() + " in " +
                    "(x=" + block.getX() + ",y=" + block.getY() + ",z=" + block.getZ() + ")");
        }*/
        if(!event.getPlayer().hasPermission("bwp.admin")) event.setCancelled(true);
    }

    @EventHandler
    public void onPreCommand(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        if(message.startsWith("/worldedit:/calc") || message.startsWith("/worldedit:/eval") || message.startsWith("/worldedit:/solve") ||
                message.startsWith("//calc") || message.startsWith("//eval") || message.startsWith("//solve")) event.setCancelled(true);
    }
}
