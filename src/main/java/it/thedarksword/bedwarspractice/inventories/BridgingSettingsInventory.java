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

@Getter
public class BridgingSettingsInventory extends SettingsInventory {

    private BridgingConfiguration.BridgeLength length = BridgingConfiguration.BridgeLength.SHORT;
    private BridgingConfiguration.BridgeHeight height = BridgingConfiguration.BridgeHeight.NONE;
    private BridgingConfiguration.BridgeDirection direction = BridgingConfiguration.BridgeDirection.FORWARD;

    public BridgingSettingsInventory(BridgingSession session){
        super(session, "Settings", 54);
    }

    public void init() {
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
        }));
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
        }));
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
        }));
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
        }));

        inventory.setItem(12, createItem(12, Material.WOOD_STAIRS, 1, ChatColor.GREEN + "None", true, event -> {
            if(!(event.getWhoClicked() instanceof Player) || GlowEnchant.isGlowing(event.getCurrentItem())) return;
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();
            GlowEnchant.removeGlow(inventory.getItem(21));
            GlowEnchant.removeGlow(inventory.getItem(30));
            GlowEnchant.addGlow(event.getCurrentItem());

            height = BridgingConfiguration.BridgeHeight.NONE;
            session.getBedwarsPractice().getManager().newSession(player, calculateSession());
        }));
        inventory.setItem(21, createItem(21, Material.WOOD_STAIRS, 1, ChatColor.GREEN + "Slight", event -> {
            if(!(event.getWhoClicked() instanceof Player) || GlowEnchant.isGlowing(event.getCurrentItem())) return;
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();
            GlowEnchant.removeGlow(inventory.getItem(12));
            GlowEnchant.removeGlow(inventory.getItem(30));
            GlowEnchant.addGlow(event.getCurrentItem()); //7

            height = BridgingConfiguration.BridgeHeight.SLIGHT;
            session.getBedwarsPractice().getManager().newSession(player, calculateSession());
        }));
        inventory.setItem(30, createItem(30, Material.WOOD_STAIRS, 1, ChatColor.GREEN + "Staircase", event -> {
            if(!(event.getWhoClicked() instanceof Player) || GlowEnchant.isGlowing(event.getCurrentItem())) return;
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();
            GlowEnchant.removeGlow(inventory.getItem(12));
            GlowEnchant.removeGlow(inventory.getItem(21));
            GlowEnchant.addGlow(event.getCurrentItem()); //30

            height = BridgingConfiguration.BridgeHeight.STAIRCASE;
            session.getBedwarsPractice().getManager().newSession(player, calculateSession());
        }));

        inventory.setItem(14, createItem(14, Material.ARROW, 1, ChatColor.GREEN + "Straight", true, event -> {
            if(!(event.getWhoClicked() instanceof Player) || GlowEnchant.isGlowing(event.getCurrentItem())) return;
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();
            GlowEnchant.removeGlow(inventory.getItem(23));
            GlowEnchant.addGlow(event.getCurrentItem());

            direction = BridgingConfiguration.BridgeDirection.FORWARD;
            session.getBedwarsPractice().getManager().newSession(player, calculateSession());
        }));
        inventory.setItem(23, createItem(23, Material.ARROW, 1, ChatColor.GREEN + "Diagonal", event -> {
            if(!(event.getWhoClicked() instanceof Player) || GlowEnchant.isGlowing(event.getCurrentItem())) return;
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();
            GlowEnchant.removeGlow(inventory.getItem(14));
            GlowEnchant.addGlow(event.getCurrentItem());

            direction = BridgingConfiguration.BridgeDirection.DIAGONAL;
            session.getBedwarsPractice().getManager().newSession(player, calculateSession());
        }));
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
