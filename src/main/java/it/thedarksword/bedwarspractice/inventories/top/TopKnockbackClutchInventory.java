package it.thedarksword.bedwarspractice.inventories.top;

import it.thedarksword.bedwarspractice.inventories.BaseInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TopKnockbackClutchInventory extends BaseInventory {

    public TopKnockbackClutchInventory() {
        super(null, "Tops Knockback Clutch", 36);
    }

    @Override
    public void init() {
        inventory.setItem(11, createItem(11, Material.STONE, 1, ChatColor.GREEN + "Facile", event -> {
            Player player = (Player) event.getWhoClicked();
            player.performCommand("bwp top EasyKnockbackClutch");
        }));

        inventory.setItem(13, createItem(13, Material.GOLD_BLOCK, 1, ChatColor.GOLD + "Medio", event -> {
            Player player = (Player) event.getWhoClicked();
            player.performCommand("bwp top MediumKnockbackClutch");
        }));

        inventory.setItem(15, createItem(15, Material.DIAMOND_BLOCK, 1, ChatColor.RED + "Difficile", event -> {
            Player player = (Player) event.getWhoClicked();
            player.performCommand("bwp top HardKnockbackClutch");
        }));

        inventory.setItem(27, createItem(27, Material.PAPER, 1, ChatColor.AQUA + "Indietro", event -> {
            Player player = (Player) event.getWhoClicked();
            player.performCommand("bwp top");
        }));

        inventory.setItem(35, createItem(35, Material.BARRIER, 1, ChatColor.RED + "Chiudi", event ->
                event.getWhoClicked().closeInventory()));
    }
}
