package it.thedarksword.bedwarspractice.inventories;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.enchantment.GlowEnchant;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;

@Getter
public abstract class BaseInventory {

    protected final BedwarsPractice bedwarsPractice;
    protected final Inventory inventory;
    protected final HashMap<Integer, ClickableItem> items = new HashMap<>();

    public BaseInventory(BedwarsPractice bedwarsPractice, String name, int size){
        this.bedwarsPractice = bedwarsPractice;
        this.inventory = Bukkit.createInventory(null, size, name);
        init();
    }

    public abstract void init();

    protected ItemStack createItem(int slot, Material material, int amount, String name, Consumer<InventoryClickEvent> consumer, String... lore) {
        return createItem(slot, material, amount, name, false, consumer, lore);
    }

    protected ItemStack createItem(int slot, Material material, int amount, String name, boolean glowing, Consumer<InventoryClickEvent> consumer,
                                   String... lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        if(lore.length != 0) meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        if(glowing) GlowEnchant.addGlow(item);
        items.put(slot, ClickableItem.of(item, consumer));
        return item;
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }
}
