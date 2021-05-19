package it.thedarksword.bedwarspractice.inventories;

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
public class BridgingSpawnInventory {

    private final Inventory inventory;
    private final HashMap<Integer, ClickableItem> items = new HashMap<>();

    //TODO: Get From Config
    public BridgingSpawnInventory() {
        inventory = Bukkit.createInventory(null, 9, "Sei pronto?");

        inventory.setItem(4, createItem(4, Material.BED, 1, ChatColor.GREEN + "Si", event ->
                event.getWhoClicked().closeInventory()));
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
