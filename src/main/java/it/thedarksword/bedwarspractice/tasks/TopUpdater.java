package it.thedarksword.bedwarspractice.tasks;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

@RequiredArgsConstructor
public class TopUpdater extends BukkitRunnable {

    private final BedwarsPractice bedwarsPractice;

    @Override
    public void run() {
        update("ShortStraightBridging", "bwp top bridging");
        update("ShortUpStraightBridging", "bwp top bridging");
        update("ShortUpperStraightBridging", "bwp top bridging");

        update("MediumStraightBridging", "bwp top bridging");
        update("MediumUpStraightBridging", "bwp top bridging");
        update("MediumUpperStraightBridging", "bwp top bridging");

        update("LongStraightBridging", "bwp top bridging");
        update("LongUpStraightBridging", "bwp top bridging");
        update("LongUpperStraightBridging", "bwp top bridging");

        update("ShortDiagonalBridging", "bwp top bridging");
        update("ShortUpDiagonalBridging", "bwp top bridging");
        update("ShortUpperDiagonalBridging", "bwp top bridging");

        update("MediumDiagonalBridging", "bwp top bridging");
        update("MediumUpDiagonalBridging", "bwp top bridging");
        update("MediumUpperDiagonalBridging", "bwp top bridging");

        update("LongDiagonalBridging", "bwp top bridging");
        update("LongUpDiagonalBridging", "bwp top bridging");
        update("LongUpperDiagonalBridging", "bwp top bridging");

        update("EasyKnockbackClutch", "bwp top knockbackclutch");
        update("MediumKnockbackClutch", "bwp top knockbackclutch");
        update("HardKnockbackClutch", "bwp top knockbackclutch");

        update("WallClutch", "top");
    }

    private void update(String mode, String backCommand) {
        try {
            List<Pair<String, Double>> list = bedwarsPractice.getMySQLManager().getTop(mode);
            Inventory inventory = bedwarsPractice.getServer().createInventory(null, 27, "Top " + mode);

            for(int i = 0; i < list.size(); i++) {
                Pair<String, Double> pair = list.get(i);
                ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte)3);
                SkullMeta meta = (SkullMeta) skull.getItemMeta();
                meta.setOwner(pair.getKey());
                meta.setDisplayName(ChatColor.GREEN + pair.getKey());
                meta.setLore(Collections.singletonList(ChatColor.AQUA + "Tempo " + ChatColor.GRAY + "» " + ChatColor.AQUA + pair.getValue()));
                skull.setItemMeta(meta);
                inventory.setItem(i, skull);
            }

            net.minecraft.server.v1_8_R3.ItemStack nmsBack = new net.minecraft.server.v1_8_R3.ItemStack(CraftMagicNumbers.getItem(Material.PAPER));
            NBTTagCompound tag = nmsBack.hasTag() ? nmsBack.getTag() : new NBTTagCompound();
            tag.setString("back", backCommand);
            nmsBack.setTag(tag);

            ItemStack back = CraftItemStack.asBukkitCopy(nmsBack);
            ItemMeta meta = back.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + "Indietro");
            back.setItemMeta(meta);
            inventory.setItem(26, back);

            bedwarsPractice.getInventories().getTopModality().put(mode, inventory);
        } catch (Exception e) {
            bedwarsPractice.getLogger().log(Level.SEVERE, "La top " + mode + " non è stata aggiornata", e);
        }
    }
}
