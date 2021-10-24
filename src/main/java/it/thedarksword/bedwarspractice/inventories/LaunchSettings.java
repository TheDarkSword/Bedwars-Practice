package it.thedarksword.bedwarspractice.inventories;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.launch.LaunchSession;
import it.thedarksword.bedwarspractice.launch.sessions.FireballLaunchSession;
import it.thedarksword.bedwarspractice.launch.sessions.TNTLaunchSession;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class LaunchSettings extends BaseInventory {

    //TODO: Get From Config
    public LaunchSettings(BedwarsPractice bedwarsPractice) {
        super(bedwarsPractice, "Impostazioni", 36);
    }

    @Override
    public void init() {
        inventory.setItem(11, createItem(11, Material.TNT, 1, ChatColor.GREEN + "TNT", event -> {
            Player player = (Player) event.getWhoClicked();
            bedwarsPractice.getManager().session(player).ifPresent(session -> {
                if(session instanceof LaunchSession) {
                    bedwarsPractice.getManager().newSession(player, new TNTLaunchSession(bedwarsPractice, player));
                }
            });
            player.closeInventory();
        }));

        inventory.setItem(15, createItem(15, Material.FIREBALL, 1, ChatColor.RED + "Fireball", event -> {
            Player player = (Player) event.getWhoClicked();
            bedwarsPractice.getManager().session(player).ifPresent(session -> {
                if(session instanceof LaunchSession) {
                    bedwarsPractice.getManager().newSession(player, new FireballLaunchSession(bedwarsPractice, player));
                }
            });
            player.closeInventory();
        }));

        inventory.setItem(31, createItem(31, Material.BARRIER, 1, ChatColor.RED + "Chiudi", event -> event.getWhoClicked().closeInventory()));
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }
}
