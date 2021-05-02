package it.thedarksword.bedwarspractice.scoreboard;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BoardsHandler {

    private static final String TITLE = ChatColor.YELLOW.toString() + ChatColor.BOLD + "BED WARS PRACTICE";

    private final Map<UUID, ScoreBoard> boards = new ConcurrentHashMap<>();
    @Getter private final BedwarsPractice bedwarsPractice;

    public BoardsHandler(BedwarsPractice bedwarsPractice) {
        this.bedwarsPractice = bedwarsPractice;
        new SidebarRunnable().runTaskTimerAsynchronously(bedwarsPractice, 5L, 2);

        Bukkit.getOnlinePlayers().forEach(this::createBoard);
    }

    /**
     * Creates a board to a player.
     *
     * @param player the player
     * @return the newly created board
     */
    public ScoreBoard createBoard(Player player) {
        return createBoard(player, null, TITLE);
    }

    /**
     * Creates a board to a player, using a predefined scoreboard.
     *
     * @param player     the player
     * @param scoreboard the scoreboard to use
     * @param name       the name of the board
     * @return the newly created board
     */
    public ScoreBoard createBoard(Player player, Scoreboard scoreboard, String name) {
        deleteBoard(player);

        ScoreBoard ScoreBoard = new ScoreBoard(this, player, TITLE);
        boards.put(player.getUniqueId(), ScoreBoard);
        return ScoreBoard;
    }

    /**
     * Deletes the board of a player.
     *
     * @param player the player
     */
    public void deleteBoard(Player player) {
        ScoreBoard scoreBoard = boards.get(player.getUniqueId());
        if(scoreBoard != null) {
            scoreBoard.delete();
        }
    }

    /**
     * Removes the board of a player from the boards map.<br>
     * <b>WARNING: Do not use this to delete the board of a player!</b>
     *
     * @param player the player
     */
    public void removeBoard(Player player) {
        boards.remove(player.getUniqueId());
    }

    /**
     * Checks if the player has a board.
     *
     * @param player the player
     * @return <code>true</code> if the player has a board, otherwise <code>false</code>
     */
    public boolean hasBoard(Player player) {
        return boards.containsKey(player.getUniqueId());
    }

    @RequiredArgsConstructor
    public static class BoardsListener implements Listener {

        private final BoardsHandler handler;

        @EventHandler(priority = EventPriority.HIGHEST)
        public void playerJoin(PlayerJoinEvent event) {
            handler.createBoard(event.getPlayer());
        }

        @EventHandler(priority = EventPriority.LOWEST)
        public void playerQuit(PlayerQuitEvent event) {
            handler.deleteBoard(event.getPlayer());
        }
    }

    /**
     * Gets the board of a player.
     *
     * @param player the player
     * @return the player board, or null if the player has no board
     */
    public ScoreBoard getBoard(Player player) {
        return boards.get(player.getUniqueId());
    }

    private class SidebarRunnable extends BukkitRunnable {

        @Override
        public void run() {
            boards.values().forEach(scoreBoard -> {
                try {
                    scoreBoard.refresh();
                } catch (Exception ignored) {}
            });
        }

    }
}
