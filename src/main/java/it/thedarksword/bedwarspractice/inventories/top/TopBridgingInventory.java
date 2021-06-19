package it.thedarksword.bedwarspractice.inventories.top;

import it.thedarksword.bedwarspractice.inventories.BaseInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TopBridgingInventory extends BaseInventory {

    public TopBridgingInventory() {
        super(null, "Tops Bridging", 45);
    }

    @Override
    public void init() {
        straight();
        diagonal();

        inventory.setItem(36, createItem(36, Material.PAPER, 1, ChatColor.AQUA + "Indietro", event -> {
            Player player = (Player) event.getWhoClicked();
            player.performCommand("bwp top");
        }));

        inventory.setItem(44, createItem(44, Material.BARRIER, 1, ChatColor.RED + "Chiudi", event ->
                event.getWhoClicked().closeInventory()));
    }

    private void straight(){
        inventory.setItem(0, createItem(0, Material.BED, 1, ChatColor.GREEN + "Dritto (30)", event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.performCommand("bwp top ShortStraightBridging");
                }, ChatColor.AQUA + "Direzione " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Dritto",
                ChatColor.AQUA + "Distanza: " + ChatColor.GRAY + "» " + ChatColor.AQUA + "30 Blocchi",
                ChatColor.AQUA + "Altezza " + ChatColor.GRAY + "» " + ChatColor.AQUA + "A Livello"));

        inventory.setItem(9, createItem(9, Material.BED, 1, ChatColor.GREEN + "Dritto (50)", event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.performCommand("bwp top MediumStraightBridging");
                }, ChatColor.AQUA + "Direzione " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Dritto",
                ChatColor.AQUA + "Distanza: " + ChatColor.GRAY + "» " + ChatColor.AQUA + "50 Blocchi",
                ChatColor.AQUA + "Altezza " + ChatColor.GRAY + "» " + ChatColor.AQUA + "A Livello"));

        inventory.setItem(18, createItem(18, Material.BED, 1, ChatColor.GREEN + "Dritto (100)", event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.performCommand("bwp top LongStraightBridging");
                }, ChatColor.AQUA + "Direzione " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Dritto",
                ChatColor.AQUA + "Distanza: " + ChatColor.GRAY + "» " + ChatColor.AQUA + "100 Blocchi",
                ChatColor.AQUA + "Altezza " + ChatColor.GRAY + "» " + ChatColor.AQUA + "A Livello"));

        inventory.setItem(1, createItem(1, Material.BED, 1, ChatColor.GREEN + "Dritto (37)", event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.performCommand("bwp top ShortUpStraightBridging");
                }, ChatColor.AQUA + "Direzione " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Dritto",
                ChatColor.AQUA + "Distanza: " + ChatColor.GRAY + "» " + ChatColor.AQUA + "30 Blocchi",
                ChatColor.AQUA + "Altezza " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Lievemente Rialzato"));

        inventory.setItem(10, createItem(10, Material.BED, 1, ChatColor.GREEN + "Dritto (57)", event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.performCommand("bwp top MediumUpStraightBridging");
                }, ChatColor.AQUA + "Direzione " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Dritto",
                ChatColor.AQUA + "Distanza: " + ChatColor.GRAY + "» " + ChatColor.AQUA + "50 Blocchi",
                ChatColor.AQUA + "Altezza " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Lievemente Rialzato"));

        inventory.setItem(19, createItem(19, Material.BED, 1, ChatColor.GREEN + "Dritto (107)", event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.performCommand("bwp top LongUpStraightBridging");
                }, ChatColor.AQUA + "Direzione " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Dritto",
                ChatColor.AQUA + "Distanza: " + ChatColor.GRAY + "» " + ChatColor.AQUA + "100 Blocchi",
                ChatColor.AQUA + "Altezza " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Lievemente Rialzato"));

        inventory.setItem(2, createItem(2, Material.BED, 1, ChatColor.GREEN + "Dritto (60)", event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.performCommand("bwp top ShortUpperStraightBridging");
                }, ChatColor.AQUA + "Direzione " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Dritto",
                ChatColor.AQUA + "Distanza: " + ChatColor.GRAY + "» " + ChatColor.AQUA + "30 Blocchi",
                ChatColor.AQUA + "Altezza " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Rialzato"));

        inventory.setItem(11, createItem(11, Material.BED, 1, ChatColor.GREEN + "Dritto (80)", event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.performCommand("bwp top MediumUpperStraightBridging");
                }, ChatColor.AQUA + "Direzione " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Dritto",
                ChatColor.AQUA + "Distanza: " + ChatColor.GRAY + "» " + ChatColor.AQUA + "50 Blocchi",
                ChatColor.AQUA + "Altezza " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Rialzato"));

        inventory.setItem(20, createItem(20, Material.BED, 1, ChatColor.GREEN + "Dritto (130)", event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.performCommand("bwp top LongUpperStraightBridging");
                }, ChatColor.AQUA + "Direzione " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Dritto",
                ChatColor.AQUA + "Distanza: " + ChatColor.GRAY + "» " + ChatColor.AQUA + "100 Blocchi",
                ChatColor.AQUA + "Altezza " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Rialzato"));
    }

    private void diagonal() {
        inventory.setItem(6, createItem(6, Material.BED, 1, ChatColor.GREEN + "Diagonale (30)", event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.performCommand("bwp top ShortDiagonalBridging");
                }, ChatColor.AQUA + "Direzione " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Diagonale",
                ChatColor.AQUA + "Distanza: " + ChatColor.GRAY + "» " + ChatColor.AQUA + "30 Blocchi",
                ChatColor.AQUA + "Altezza " + ChatColor.GRAY + "» " + ChatColor.AQUA + "A Livello"));

        inventory.setItem(15, createItem(15, Material.BED, 1, ChatColor.GREEN + "Diagonale (50)", event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.performCommand("bwp top MediumDiagonalBridging");
                }, ChatColor.AQUA + "Direzione " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Diagonale",
                ChatColor.AQUA + "Distanza: " + ChatColor.GRAY + "» " + ChatColor.AQUA + "50 Blocchi",
                ChatColor.AQUA + "Altezza " + ChatColor.GRAY + "» " + ChatColor.AQUA + "A Livello"));

        inventory.setItem(24, createItem(24, Material.BED, 1, ChatColor.GREEN + "Diagonale (100)", event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.performCommand("bwp top LongDiagonalBridging");
                }, ChatColor.AQUA + "Direzione " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Diagonale",
                ChatColor.AQUA + "Distanza: " + ChatColor.GRAY + "» " + ChatColor.AQUA + "100 Blocchi",
                ChatColor.AQUA + "Altezza " + ChatColor.GRAY + "» " + ChatColor.AQUA + "A Livello"));

        inventory.setItem(7, createItem(7, Material.BED, 1, ChatColor.GREEN + "Diagonale (37)", event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.performCommand("bwp top ShortUpDiagonalBridging");
                }, ChatColor.AQUA + "Direzione " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Diagonale",
                ChatColor.AQUA + "Distanza: " + ChatColor.GRAY + "» " + ChatColor.AQUA + "30 Blocchi",
                ChatColor.AQUA + "Altezza " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Lievemente Rialzato"));

        inventory.setItem(16, createItem(16, Material.BED, 1, ChatColor.GREEN + "Diagonale (57)", event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.performCommand("bwp top MediumUpDiagonalBridging");
                }, ChatColor.AQUA + "Direzione " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Diagonale",
                ChatColor.AQUA + "Distanza: " + ChatColor.GRAY + "» " + ChatColor.AQUA + "50 Blocchi",
                ChatColor.AQUA + "Altezza " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Lievemente Rialzato"));

        inventory.setItem(25, createItem(25, Material.BED, 1, ChatColor.GREEN + "Diagonale (107)", event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.performCommand("bwp top LongUpDiagonalBridging");
                }, ChatColor.AQUA + "Direzione " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Diagonale",
                ChatColor.AQUA + "Distanza: " + ChatColor.GRAY + "» " + ChatColor.AQUA + "100 Blocchi",
                ChatColor.AQUA + "Altezza " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Lievemente Rialzato"));

        inventory.setItem(8, createItem(8, Material.BED, 1, ChatColor.GREEN + "Diagonale (60)", event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.performCommand("bwp top ShortUpperDiagonalBridging");
                }, ChatColor.AQUA + "Direzione " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Diagonale",
                ChatColor.AQUA + "Distanza: " + ChatColor.GRAY + "» " + ChatColor.AQUA + "30 Blocchi",
                ChatColor.AQUA + "Altezza " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Rialzato"));

        inventory.setItem(17, createItem(17, Material.BED, 1, ChatColor.GREEN + "Diagonale (80)", event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.performCommand("bwp top MediumUpperDiagonalBridging");
                }, ChatColor.AQUA + "Direzione " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Diagonale",
                ChatColor.AQUA + "Distanza: " + ChatColor.GRAY + "» " + ChatColor.AQUA + "50 Blocchi",
                ChatColor.AQUA + "Altezza " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Rialzato"));

        inventory.setItem(26, createItem(26, Material.BED, 1, ChatColor.GREEN + "Diagonale (130)", event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.performCommand("bwp top LongUpperDiagonalBridging");
                }, ChatColor.AQUA + "Direzione " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Diagonale",
                ChatColor.AQUA + "Distanza: " + ChatColor.GRAY + "» " + ChatColor.AQUA + "100 Blocchi",
                ChatColor.AQUA + "Altezza " + ChatColor.GRAY + "» " + ChatColor.AQUA + "Rialzato"));
    }
}
