package it.thedarksword.bedwarspractice.tasks;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
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
        update("ShortStraightBridging");
        update("ShortUpStraightBridging");
        update("ShortUpperStraightBridging");

        update("MediumStraightBridging");
        update("MediumUpStraightBridging");
        update("MediumUpperStraightBridging");

        update("LongStraightBridging");
        update("LongUpStraightBridging");
        update("LongUpperStraightBridging");

        update("ShortDiagonalBridging");
        update("ShortUpDiagonalBridging");
        update("ShortUpperDiagonalBridging");

        update("MediumDiagonalBridging");
        update("MediumUpDiagonalBridging");
        update("MediumUpperDiagonalBridging");

        update("LongDiagonalBridging");
        update("LongUpDiagonalBridging");
        update("LongUpperDiagonalBridging");

        update("EasyKnockbackClutch");
        update("MediumKnockbackClutch");
        update("HardKnockbackClutch");

        update("WallClutch");
    }

    private void update(String mode) {
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

            bedwarsPractice.getInventories().getTopModality().put(mode, inventory);
        } catch (Exception e) {
            bedwarsPractice.getLogger().log(Level.SEVERE, "La top " + mode + " non è stata aggiornata", e);
        }
    }
}
