package it.thedarksword.bedwarspractice.inventories.top;

import it.thedarksword.bedwarspractice.inventories.BaseInventory;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Getter
public class TopsInventory extends BaseInventory {

    //TODO: Get From Config
    public TopsInventory() {
        super(null, "Tops", 9);
    }

    @Override
    public void init() {
        inventory.setItem(0, createItem(0, Material.BED, 1, ChatColor.GREEN + "Bridging", event -> {
            Player player = (Player) event.getWhoClicked();
            player.performCommand("bwp top bridging");
        }));

        inventory.setItem(1, createItem(1, Material.IRON_BLOCK, 1, ChatColor.GREEN + "Wall Clutch", event -> {
            Player player = (Player) event.getWhoClicked();
            player.performCommand("bwp top WallClutch");
        }));

        inventory.setItem(2, createItem(2, Material.WOOD_SWORD, 1, ChatColor.GREEN + "Knockback Clutch", event -> {
            Player player = (Player) event.getWhoClicked();
            player.performCommand("bwp top knockbackclutch");
        }));

        inventory.setItem(8, createItem(8, Material.BARRIER, 1, ChatColor.RED + "Chiudi", event ->
                event.getWhoClicked().closeInventory()));
    }
}
