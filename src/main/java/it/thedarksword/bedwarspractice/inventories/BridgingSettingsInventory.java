package it.thedarksword.bedwarspractice.inventories;

import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingConfiguration;
import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingSession;
import it.thedarksword.bedwarspractice.bridging.sessions.InfiniteBridging;
import it.thedarksword.bedwarspractice.bridging.sessions.diagonal.none.*;
import it.thedarksword.bedwarspractice.bridging.sessions.diagonal.up.*;
import it.thedarksword.bedwarspractice.bridging.sessions.diagonal.upper.*;
import it.thedarksword.bedwarspractice.bridging.sessions.straight.none.*;
import it.thedarksword.bedwarspractice.bridging.sessions.straight.up.*;
import it.thedarksword.bedwarspractice.bridging.sessions.straight.upper.*;
import it.thedarksword.bedwarspractice.enchantment.GlowEnchant;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class BridgingSettingsInventory extends SettingsInventory {

    private BridgingConfiguration.BridgeLength length = BridgingConfiguration.BridgeLength.SHORT;
    private BridgingConfiguration.BridgeHeight height = BridgingConfiguration.BridgeHeight.NONE;
    private BridgingConfiguration.BridgeDirection direction = BridgingConfiguration.BridgeDirection.FORWARD;

    public BridgingSettingsInventory(BridgingSession session){
        super(session, "Settings", 54);
    }

    public void init() {
        for(int j = 0; j < 6; j++) {
            if(j > 0 && j < 5) {
                inventory.setItem((j * 9), new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
                inventory.setItem((j * 9) + 8, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
            } else {
                for (int i = 0; i < 9; i++) {
                    inventory.setItem((j * 9) + i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
                }
            }
        }
        inventory.setItem(10, createItem(10, Material.WOOL, 30, ChatColor.GREEN + "30 Blocchi", true, event -> {
            if(!(event.getWhoClicked() instanceof Player) || GlowEnchant.isGlowing(event.getCurrentItem())) return;
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();
            GlowEnchant.removeGlow(inventory.getItem(19));
            GlowEnchant.removeGlow(inventory.getItem(28));
            GlowEnchant.removeGlow(inventory.getItem(37));
            GlowEnchant.addGlow(event.getCurrentItem());

            length = BridgingConfiguration.BridgeLength.SHORT;
            session.getBedwarsPractice().getManager().newSession(player, calculateSession());
        }, ChatColor.GRAY + "Imposta la distanza", ChatColor.GRAY + "di arrivo a 30 blocchi"));
        inventory.setItem(19, createItem(19, Material.WOOL, 50, ChatColor.GREEN + "50 Blocchi", event -> {
            if(!(event.getWhoClicked() instanceof Player) || GlowEnchant.isGlowing(event.getCurrentItem())) return;
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();
            GlowEnchant.removeGlow(inventory.getItem(10));
            GlowEnchant.removeGlow(inventory.getItem(28));
            GlowEnchant.removeGlow(inventory.getItem(37));
            GlowEnchant.addGlow(event.getCurrentItem());

            length = BridgingConfiguration.BridgeLength.MEDIUM;
            session.getBedwarsPractice().getManager().newSession(player, calculateSession());
        }, ChatColor.GRAY + "Imposta la distanza", ChatColor.GRAY + "di arrivo a 50 blocchi"));
        inventory.setItem(28, createItem(28, Material.WOOL, 64, ChatColor.GREEN + "100 Blocchi", event -> {
            if(!(event.getWhoClicked() instanceof Player) || GlowEnchant.isGlowing(event.getCurrentItem())) return;
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();
            GlowEnchant.removeGlow(inventory.getItem(10));
            GlowEnchant.removeGlow(inventory.getItem(19));
            GlowEnchant.removeGlow(inventory.getItem(37));
            GlowEnchant.addGlow(event.getCurrentItem());

            length = BridgingConfiguration.BridgeLength.LONG;
            session.getBedwarsPractice().getManager().newSession(player, calculateSession());
        }, ChatColor.GRAY + "Imposta la distanza", ChatColor.GRAY + "di arrivo a 100 blocchi"));
        inventory.setItem(37, createItem(37, Material.WOOL, 1, ChatColor.GREEN + "Infiniti!", event -> {
            if(!(event.getWhoClicked() instanceof Player) || GlowEnchant.isGlowing(event.getCurrentItem())) return;
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();
            GlowEnchant.removeGlow(inventory.getItem(10));
            GlowEnchant.removeGlow(inventory.getItem(19));
            GlowEnchant.removeGlow(inventory.getItem(28));
            GlowEnchant.addGlow(event.getCurrentItem());

            session.getBedwarsPractice().getManager().newSession(player, new InfiniteBridging(session.getBedwarsPractice(),
                    (BridgingSettingsInventory) session.getSettingsInventory()));
        },ChatColor.GRAY + "Non hai una fine", ChatColor.GRAY + "vai più lontano che puoi"));

        inventory.setItem(12, createItem(12, Material.WOOD_STAIRS, 1, ChatColor.GREEN + "A Livello", true, event -> {
            if(!(event.getWhoClicked() instanceof Player) || GlowEnchant.isGlowing(event.getCurrentItem())) return;
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();
            GlowEnchant.removeGlow(inventory.getItem(21));
            GlowEnchant.removeGlow(inventory.getItem(30));
            GlowEnchant.addGlow(event.getCurrentItem());

            height = BridgingConfiguration.BridgeHeight.NONE;
            session.getBedwarsPractice().getManager().newSession(player, calculateSession());
        },ChatColor.GRAY + "Imposta l'isola", ChatColor.GRAY + "a livello con la tua"));
        inventory.setItem(21, createItem(21, Material.WOOD_STAIRS, 1, ChatColor.GREEN + "Lievemente Rialzato", event -> {
            if(!(event.getWhoClicked() instanceof Player) || GlowEnchant.isGlowing(event.getCurrentItem())) return;
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();
            GlowEnchant.removeGlow(inventory.getItem(12));
            GlowEnchant.removeGlow(inventory.getItem(30));
            GlowEnchant.addGlow(event.getCurrentItem()); //7

            height = BridgingConfiguration.BridgeHeight.SLIGHT;
            session.getBedwarsPractice().getManager().newSession(player, calculateSession());
        },ChatColor.GRAY + "Imposta l'isola", ChatColor.GRAY + "leggermente in alto"));
        inventory.setItem(30, createItem(30, Material.WOOD_STAIRS, 1, ChatColor.GREEN + "Rialzato", event -> {
            if(!(event.getWhoClicked() instanceof Player) || GlowEnchant.isGlowing(event.getCurrentItem())) return;
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();
            GlowEnchant.removeGlow(inventory.getItem(12));
            GlowEnchant.removeGlow(inventory.getItem(21));
            GlowEnchant.addGlow(event.getCurrentItem()); //30

            height = BridgingConfiguration.BridgeHeight.STAIRCASE;
            session.getBedwarsPractice().getManager().newSession(player, calculateSession());
        },ChatColor.GRAY + "Imposta l'isola", ChatColor.GRAY + "molto in alto"));

        inventory.setItem(14, createItem(14, Material.ARROW, 1, ChatColor.GREEN + "Dritta", true, event -> {
            if(!(event.getWhoClicked() instanceof Player) || GlowEnchant.isGlowing(event.getCurrentItem())) return;
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();
            GlowEnchant.removeGlow(inventory.getItem(23));
            GlowEnchant.addGlow(event.getCurrentItem());

            direction = BridgingConfiguration.BridgeDirection.FORWARD;
            session.getBedwarsPractice().getManager().newSession(player, calculateSession());
        }, ChatColor.GRAY + "Imposta l'isola", ChatColor.GRAY + "di fronte alla tua"));
        inventory.setItem(23, createItem(23, Material.ARROW, 1, ChatColor.GREEN + "Diagonale", event -> {
            if(!(event.getWhoClicked() instanceof Player) || GlowEnchant.isGlowing(event.getCurrentItem())) return;
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();
            GlowEnchant.removeGlow(inventory.getItem(14));
            GlowEnchant.addGlow(event.getCurrentItem());

            direction = BridgingConfiguration.BridgeDirection.DIAGONAL;
            session.getBedwarsPractice().getManager().newSession(player, calculateSession());
        }, ChatColor.GRAY + "Imposta l'isola", ChatColor.GRAY + "in diagonale alla tua"));
    }

    private BridgingSession calculateSession() {
        if(length == BridgingConfiguration.BridgeLength.INFINITE)
            return new InfiniteBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());

        if(direction == BridgingConfiguration.BridgeDirection.FORWARD) {
            if(length == BridgingConfiguration.BridgeLength.SHORT) {
                if(height == BridgingConfiguration.BridgeHeight.NONE) {
                    return new ShortStraightBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());
                } else if (height == BridgingConfiguration.BridgeHeight.SLIGHT) {
                    return new ShortUpStraightBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());
                } else {
                    return new ShortUpperStraightBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());
                }
            } else if(length == BridgingConfiguration.BridgeLength.MEDIUM) {
                if(height == BridgingConfiguration.BridgeHeight.NONE) {
                    return new MediumStraightBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());
                } else if (height == BridgingConfiguration.BridgeHeight.SLIGHT) {
                    return new MediumUpStraightBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());
                } else {
                    return new MediumUpperStraightBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());
                }
            } else {
                if(height == BridgingConfiguration.BridgeHeight.NONE) {
                    return new LongStraightBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());
                } else if (height == BridgingConfiguration.BridgeHeight.SLIGHT) {
                    return new LongUpStraightBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());
                } else {
                    return new LongUpperStraightBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());
                }
            }
        } else {
            if(length == BridgingConfiguration.BridgeLength.SHORT) {
                if(height == BridgingConfiguration.BridgeHeight.NONE) {
                    return new ShortDiagonalBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());
                } else if (height == BridgingConfiguration.BridgeHeight.SLIGHT) {
                    return new ShortUpDiagonalBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());
                } else {
                    return new ShortUpperDiagonalBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());
                }
            } else if(length == BridgingConfiguration.BridgeLength.MEDIUM) {
                if(height == BridgingConfiguration.BridgeHeight.NONE) {
                    return new MediumDiagonalBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());
                } else if (height == BridgingConfiguration.BridgeHeight.SLIGHT) {
                    return new MediumUpDiagonalBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());
                } else {
                    return new MediumUpperDiagonalBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());
                }
            } else {
                if(height == BridgingConfiguration.BridgeHeight.NONE) {
                    return new LongDiagonalBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());
                } else if (height == BridgingConfiguration.BridgeHeight.SLIGHT) {
                    return new LongUpDiagonalBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());
                } else {
                    return new LongUpperDiagonalBridging(session.getBedwarsPractice(), (BridgingSettingsInventory) session.getSettingsInventory());
                }
            }
        }
    }
}
