package it.thedarksword.bedwarspractice.manager;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter
public class ConstantObjects {

    private final BedwarsPractice bedwarsPractice;

    private final ItemStack block;

    private final ItemStack settings;
    private final ItemStack mode;

    private final ItemStack checkpointEnabled;
    private final ItemStack checkpointDisabled;

    @SuppressWarnings("deprecation")
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

        if(bedwarsPractice.getConfigValue().CHECKPOINT_ENABLED_MATERIAL == Material.INK_SACK)
            checkpointEnabled = new ItemStack(bedwarsPractice.getConfigValue().CHECKPOINT_ENABLED_MATERIAL, 1,
                    bedwarsPractice.getConfigValue().CHECKPOINT_ENABLED_COLOR.getDyeData());
        else
            checkpointEnabled = new ItemStack(bedwarsPractice.getConfigValue().CHECKPOINT_ENABLED_MATERIAL, 1);
        meta = checkpointEnabled.getItemMeta();
        meta.setDisplayName(bedwarsPractice.getConfigValue().CHECKPOINT_ENABLED_NAME);
        meta.setLore(bedwarsPractice.getConfigValue().CHECKPOINT_ENABLED_LORE);
        checkpointEnabled.setItemMeta(meta);

        if(bedwarsPractice.getConfigValue().CHECKPOINT_DISABLED_MATERIAL == Material.INK_SACK)
            checkpointDisabled = new ItemStack(bedwarsPractice.getConfigValue().CHECKPOINT_DISABLED_MATERIAL, 1,
                    bedwarsPractice.getConfigValue().CHECKPOINT_DISABLED_COLOR.getDyeData());
        else
            checkpointDisabled = new ItemStack(bedwarsPractice.getConfigValue().CHECKPOINT_DISABLED_MATERIAL, 1);
        meta = checkpointDisabled.getItemMeta();
        meta.setDisplayName(bedwarsPractice.getConfigValue().CHECKPOINT_DISABLED_NAME);
        meta.setLore(bedwarsPractice.getConfigValue().CHECKPOINT_DISABLED_LORE);
        checkpointDisabled.setItemMeta(meta);
    }
}
