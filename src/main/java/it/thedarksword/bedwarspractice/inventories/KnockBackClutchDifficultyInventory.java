package it.thedarksword.bedwarspractice.inventories;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.clutch.sessions.KnockbackClutch;
import it.thedarksword.bedwarspractice.clutch.sessions.knockback.EasyKnockbackClutch;
import it.thedarksword.bedwarspractice.clutch.sessions.knockback.HardKnockbackClutch;
import it.thedarksword.bedwarspractice.clutch.sessions.knockback.MediumKnockbackClutch;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Getter
public class KnockBackClutchDifficultyInventory extends BaseInventory {

    //TODO: Get From Config
    public KnockBackClutchDifficultyInventory(BedwarsPractice bedwarsPractice) {
        super(bedwarsPractice, "Modalità", 36);
    }

    @Override
    public void init() {
        inventory.setItem(11, createItem(11, Material.STONE, 1, ChatColor.GREEN + "Facile", event -> {
            Player player = (Player) event.getWhoClicked();
            bedwarsPractice.getManager().session(player).ifPresent(session -> {
                if(session instanceof KnockbackClutch) {
                    bedwarsPractice.getManager().newSession(player, new EasyKnockbackClutch(bedwarsPractice, player));
                }
            });
            player.closeInventory();
        }));

        inventory.setItem(13, createItem(13, Material.GOLD_BLOCK, 1, ChatColor.GOLD + "Medio", event -> {
            Player player = (Player) event.getWhoClicked();
            bedwarsPractice.getManager().session(player).ifPresent(session -> {
                if(session instanceof KnockbackClutch) {
                    bedwarsPractice.getManager().newSession(player, new MediumKnockbackClutch(bedwarsPractice, player));
                }
            });
            player.closeInventory();
        }));

        inventory.setItem(15, createItem(15, Material.DIAMOND_BLOCK, 1, ChatColor.RED + "Difficile", event -> {
            Player player = (Player) event.getWhoClicked();
            bedwarsPractice.getManager().session(player).ifPresent(session -> {
                if(session instanceof KnockbackClutch) {
                    bedwarsPractice.getManager().newSession(player, new HardKnockbackClutch(bedwarsPractice, player));
                }
            });
            player.closeInventory();
        }));

        inventory.setItem(31, createItem(31, Material.BARRIER, 1, ChatColor.RED + "Chiudi", event -> {
            event.getWhoClicked().closeInventory();
        }));
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }
}
