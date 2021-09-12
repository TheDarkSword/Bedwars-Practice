package it.thedarksword.bedwarspractice.manager;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

@Getter
public class ConstantObjects {

    private final BedwarsPractice bedwarsPractice;

    //private final ItemStack block;

    private final ItemStack leave;
    private final ItemStack settings;
    private final ItemStack mode;
    private final ItemStack kbcDifficulty;

    private final ItemStack checkpointEnabled;
    private final ItemStack checkpointDisabled;

    @SuppressWarnings("deprecation")
    public ConstantObjects(BedwarsPractice bedwarsPractice) {
        this.bedwarsPractice = bedwarsPractice;

        //block = new ItemStack(bedwarsPractice.getConfigValue().BLOCK_MATERIAL, 64);
        leave = new ItemStack(Material.BED, 1);
        ItemMeta meta = leave.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Esci");
        leave.setItemMeta(meta);
        settings = new ItemStack(bedwarsPractice.getConfigValue().SETTINGS_MATERIAL, 1);
        meta = settings.getItemMeta();
        meta.setDisplayName(bedwarsPractice.getConfigValue().SETTINGS_NAME);
        meta.setLore(bedwarsPractice.getConfigValue().SETTINGS_LORE);
        settings.setItemMeta(meta);

        mode = new ItemStack(bedwarsPractice.getConfigValue().MODE_MATERIAL, 1);
        meta = mode.getItemMeta();
        meta.setDisplayName(bedwarsPractice.getConfigValue().MODE_NAME);
        meta.setLore(bedwarsPractice.getConfigValue().MODE_LORE);
        mode.setItemMeta(meta);

        kbcDifficulty = new ItemStack(bedwarsPractice.getConfigValue().KBC_DIFFICULTY_MATERIAL, 1);
        meta = kbcDifficulty.getItemMeta();
        meta.setDisplayName(bedwarsPractice.getConfigValue().KBC_DIFFICULTY_NAME);
        meta.setLore(bedwarsPractice.getConfigValue().KBC_DIFFICULTY_LORE);
        kbcDifficulty.setItemMeta(meta);

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

    @SuppressWarnings("deprecation")
    public enum PlaceableBlock {

        WHITE_WOOL(new ItemStack(Material.WOOL, 64, DyeColor.WHITE.getData())),
        ORANGE_WOOL(new ItemStack(Material.WOOL, 64, DyeColor.ORANGE.getData())),
        MAGENTA_WOOL(new ItemStack(Material.WOOL, 64, DyeColor.MAGENTA.getData())),
        LIGHT_BLUE_WOOL(new ItemStack(Material.WOOL, 64, DyeColor.LIGHT_BLUE.getData())),
        YELLOW_WOOL(new ItemStack(Material.WOOL, 64, DyeColor.YELLOW.getData())),
        LIME_WOOL(new ItemStack(Material.WOOL, 64, DyeColor.LIME.getData())),
        PINK_WOOL(new ItemStack(Material.WOOL, 64, DyeColor.PINK.getData())),
        GRAY_WOOL(new ItemStack(Material.WOOL, 64, DyeColor.GRAY.getData())),
        SILVER_WOOL(new ItemStack(Material.WOOL, 64, DyeColor.SILVER.getData())),
        CYAN_WOOL(new ItemStack(Material.WOOL, 64, DyeColor.CYAN.getData())),
        PURPLE_WOOL(new ItemStack(Material.WOOL, 64, DyeColor.PURPLE.getData())),
        BLUE_WOOL(new ItemStack(Material.WOOL, 64, DyeColor.BLUE.getData())),
        BROWN_WOOL(new ItemStack(Material.WOOL, 64, DyeColor.BROWN.getData())),
        GREEN_WOOL(new ItemStack(Material.WOOL, 64, DyeColor.GREEN.getData())),
        RED_WOOL(new ItemStack(Material.WOOL, 64, DyeColor.RED.getData())),
        BLACK_WOOL(new ItemStack(Material.WOOL, 64, DyeColor.BLACK.getData()));

        private final ItemStack placeableBlock;

        PlaceableBlock(ItemStack placeableBlock) {
            this.placeableBlock = placeableBlock;
        }

        public ItemStack get() {
            return placeableBlock;
        }
    }
}
