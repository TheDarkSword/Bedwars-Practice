package it.thedarksword.bedwarspractice.inventories;

import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingSession;
import it.thedarksword.bedwarspractice.enchantment.GlowEnchant;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.function.Consumer;

@Getter
public abstract class SettingsInventory {

    protected final Inventory inventory;
    protected final BridgingSession session;
    protected final HashMap<Integer, ClickableItem> items = new HashMap<>();

    public SettingsInventory(BridgingSession session, String name, int size){
        this.inventory = Bukkit.createInventory(null, size, name);
        this.session = session;
        init();
    }

    public abstract void init();

    protected ItemStack createItem(int slot, Material material, int amount, String name, Consumer<InventoryClickEvent> consumer) {
        return createItem(slot, material, amount, name, false, consumer);
    }

    protected ItemStack createItem(int slot, Material material, int amount, String name, boolean glowing, Consumer<InventoryClickEvent> consumer) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        if(glowing) GlowEnchant.addGlow(item);
        items.put(slot, ClickableItem.of(item, consumer));
        return item;
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }
}
