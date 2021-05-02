package it.thedarksword.bedwarspractice.manager;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter
public class ConstantObjects {

    private final BedwarsPractice bedwarsPractice;

    private final ItemStack block;
    private final ItemStack settings;
    private final ItemStack mode;

    public ConstantObjects(BedwarsPractice bedwarsPractice) {
        this.bedwarsPractice = bedwarsPractice;

        block = new ItemStack(bedwarsPractice.getConfigValue().BLOCK_MATERIAL, 64);
        settings = new ItemStack(bedwarsPractice.getConfigValue().SETTINGS_MATERIAL, 1);
        ItemMeta meta = settings.getItemMeta();
        meta.setDisplayName(bedwarsPractice.getConfigValue().SETTINGS_NAME);
        meta.setLore(bedwarsPractice.getConfigValue().SETTINGS_LORE);
        settings.setItemMeta(meta);

        mode = new ItemStack(bedwarsPractice.getConfigValue().MODE_MATERIAL, 1);
        meta = mode.getItemMeta();
        meta.setDisplayName(bedwarsPractice.getConfigValue().MODE_NAME);
        meta.setLore(bedwarsPractice.getConfigValue().MODE_LORE);
        mode.setItemMeta(meta);
    }
}
