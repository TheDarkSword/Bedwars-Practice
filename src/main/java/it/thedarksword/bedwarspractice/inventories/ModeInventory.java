package it.thedarksword.bedwarspractice.inventories;

import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingConfiguration;
import it.thedarksword.bedwarspractice.enchantment.GlowEnchant;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.function.Consumer;

@Getter
public class ModeInventory {

    private final Inventory inventory;
    private final HashMap<Integer, ClickableItem> items = new HashMap<>();

    //TODO: Get From Config
    public ModeInventory() {
        inventory = Bukkit.createInventory(null, 9, "Modalità");

        inventory.setItem(2, createItem(2, Material.BED, 1, ChatColor.GREEN + "Bridging", event -> {
            Player player = (Player) event.getWhoClicked();
            player.performCommand("bwp start bridging");
        }));

        inventory.setItem(6, createItem(6, Material.WOOD_SWORD, 1, ChatColor.GREEN + "Knockback Clutch", event -> {
            Player player = (Player) event.getWhoClicked();
            player.performCommand("bwp start knockbackclutch");
        }));
    }

    protected ItemStack createItem(int slot, Material material, int amount, String name, Consumer<InventoryClickEvent> consumer) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        items.put(slot, ClickableItem.of(item, consumer));
        return item;
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }
}
