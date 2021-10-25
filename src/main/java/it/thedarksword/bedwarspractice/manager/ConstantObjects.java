package it.thedarksword.bedwarspractice.manager;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
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

    private final ItemStack launchItemSettings;

    private final BedBurrow bedBurrow;
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

        launchItemSettings = new ItemStack(bedwarsPractice.getConfigValue().LAUNCH_SETTINGS_MATERIAL);
        meta = launchItemSettings.getItemMeta();
        meta.setDisplayName(bedwarsPractice.getConfigValue().LAUNCH_SETTINGS_NAME);
        meta.setLore(bedwarsPractice.getConfigValue().LAUNCH_SETTINGS_LORE);
        launchItemSettings.setItemMeta(meta);

        bedBurrow = new BedBurrow();
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

    @Getter
    public static class BedBurrow {
        private final ItemStack sword;
        private final ItemStack pickaxe;
        private final ItemStack axe;
        private final ItemStack shears;
        private final ItemStack anvil;

        public BedBurrow() {
            net.minecraft.server.v1_8_R3.ItemStack sword = new net.minecraft.server.v1_8_R3.ItemStack(CraftMagicNumbers.getItem(Material.WOOD_SWORD));
            NBTTagCompound swordCompound = sword.hasTag() ? sword.getTag() : new NBTTagCompound();
            swordCompound.setBoolean("Unbreakable", true);
            sword.setTag(swordCompound);
            this.sword = CraftItemStack.asBukkitCopy(sword);

            net.minecraft.server.v1_8_R3.ItemStack pickaxe = new net.minecraft.server.v1_8_R3.ItemStack(CraftMagicNumbers.getItem(Material.WOOD_PICKAXE));
            NBTTagCompound pickaxeCompound = pickaxe.hasTag() ? pickaxe.getTag() : new NBTTagCompound();
            pickaxeCompound.setBoolean("Unbreakable", true);
            pickaxe.setTag(pickaxeCompound);
            this.pickaxe = CraftItemStack.asBukkitCopy(pickaxe);

            net.minecraft.server.v1_8_R3.ItemStack axe = new net.minecraft.server.v1_8_R3.ItemStack(CraftMagicNumbers.getItem(Material.WOOD_AXE));
            NBTTagCompound axeCompound = axe.hasTag() ? axe.getTag() : new NBTTagCompound();
            axeCompound.setBoolean("Unbreakable", true);
            axe.setTag(axeCompound);
            this.axe = CraftItemStack.asBukkitCopy(axe);

            net.minecraft.server.v1_8_R3.ItemStack shears = new net.minecraft.server.v1_8_R3.ItemStack(CraftMagicNumbers.getItem(Material.SHEARS));
            NBTTagCompound shearsCompound = shears.hasTag() ? shears.getTag() : new NBTTagCompound();
            shearsCompound.setBoolean("Unbreakable", true);
            shears.setTag(shearsCompound);
            this.shears = CraftItemStack.asBukkitCopy(shears);

            anvil = new ItemStack(Material.ANVIL);
            ItemMeta anvilMeta = anvil.getItemMeta();
            anvilMeta.setDisplayName(ChatColor.RED + "Drop Down!");
            anvil.setItemMeta(anvilMeta);
        }
    }
}
