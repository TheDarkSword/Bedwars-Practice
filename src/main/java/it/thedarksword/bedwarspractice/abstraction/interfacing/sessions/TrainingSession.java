package it.thedarksword.bedwarspractice.abstraction.interfacing.sessions;

import it.thedarksword.bedwarspractice.abstraction.interfacing.sidebar.SidebarTemplate;
import it.thedarksword.bedwarspractice.clipboards.Schematic;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface TrainingSession extends SidebarTemplate {

    long getSessionStart();

    boolean isRunning();

    void load(Player player);
    void init(Player player);

    void stop(Player player);
    void start(Player player);

    void win(Player player);
    void loose(Player player);

    /**
     *
     * @param player
     * @param schematic
     * @param location is the start location
     */
    void pasteSchematic(Player player, Schematic schematic, Location location);
    void clearSchematic(Player player);

    Location getSpawn();
}
