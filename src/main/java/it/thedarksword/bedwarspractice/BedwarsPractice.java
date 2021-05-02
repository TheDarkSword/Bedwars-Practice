package it.thedarksword.bedwarspractice;

import it.thedarksword.bedwarspractice.clipboards.Schematic;
import it.thedarksword.bedwarspractice.commands.BedwarsPracticeCommand;
import it.thedarksword.bedwarspractice.commands.BwTest;
import it.thedarksword.bedwarspractice.config.ConfigValue;
import it.thedarksword.bedwarspractice.enchantment.GlowEnchant;
import it.thedarksword.bedwarspractice.listeners.BridgingListener;
import it.thedarksword.bedwarspractice.listeners.NatureListener;
import it.thedarksword.bedwarspractice.manager.ConstantObjects;
import it.thedarksword.bedwarspractice.manager.Manager;
import it.thedarksword.bedwarspractice.mysql.MySQL;
import it.thedarksword.bedwarspractice.mysql.MySQLManager;
import it.thedarksword.bedwarspractice.packets.PacketListener;
import it.thedarksword.bedwarspractice.scoreboard.BoardsHandler;
import it.thedarksword.bedwarspractice.scoreboard.NMS;
import it.thedarksword.bedwarspractice.utils.Title;
import it.thedarksword.bedwarspractice.utils.location.CloneableLocation;
import it.thedarksword.bedwarspractice.yaml.Configuration;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Getter
public class BedwarsPractice extends JavaPlugin {

    private Configuration settings;

    private ConfigValue configValue;

    private MySQLManager mySQLManager;

    private PacketListener packetListener;

    private Manager manager;
    private ConstantObjects constantObjects;

    private BoardsHandler boardsHandler;

    @Setter private Location spawn;
    @Setter private CloneableLocation schematicSpawn;
    private Schematic schematic;

    @SneakyThrows
    @Override
    public void onEnable() {
        Configuration configuration = new Configuration(new File(getDataFolder(), "config.yml"), getResource("config.yml"));
        settings = new Configuration(new File(getDataFolder(), "settings.yml"), getResource("settings.yml"));
        File schematicFile = new File(getDataFolder(), "finish.schematic");
        if(!schematicFile.exists()) Files.copy(getResource("finish.schematic"), schematicFile.toPath());

        try {
            configuration.autoload();
        } catch (IOException e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        try {
            settings.autoload();
        } catch (IOException e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        configValue = new ConfigValue(configuration);

        mySQLManager = new MySQLManager(configValue,
                new MySQL(configValue.HOST, configValue.PORT, configValue.DATABASE, configValue.USERNAME, configValue.PASSWORD));

        mySQLManager.createTables();

        GlowEnchant.registerGlow();
        NMS.init();

        packetListener = new PacketListener(this);

        if(!settings.getString("spawn.world").isEmpty()) {
            spawn = new Location(
                    Bukkit.getWorld(settings.getString("spawn.world")),
                    settings.getInt("spawn.x"),
                    settings.getInt("spawn.y"),
                    settings.getInt("spawn.z"),
                    settings.getFloat("spawn.yaw"),
                    settings.getFloat("spawn.pitch")
            );
        }

        if(!settings.getString("schematic.world").isEmpty()) {
            schematicSpawn = new CloneableLocation(new Location(
                    Bukkit.getWorld(settings.getString("schematic.world")),
                    settings.getInt("schematic.x"),
                    settings.getInt("schematic.y"),
                    settings.getInt("schematic.z"),
                    settings.getFloat("schematic.yaw"),
                    settings.getFloat("schematic.pitch")
            ));
        }

        schematic = Schematic.loadSchematic(schematicFile);

        manager = new Manager(this);
        constantObjects = new ConstantObjects(this);

        boardsHandler = new BoardsHandler(this);

        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        getCommand("bedwarspractice").setExecutor(new BedwarsPracticeCommand(this));
        getCommand("bwtest").setExecutor(new BwTest(this));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new BridgingListener(this), this);
        getServer().getPluginManager().registerEvents(new NatureListener(this), this);
        getServer().getPluginManager().registerEvents(new BoardsHandler.BoardsListener(boardsHandler), this);
    }

    public void sendMessage(CommandSender sender, String message){
        sender.sendMessage(message);
    }

    public void sendMessage(CommandSender sender, String message, String from, String to){
        sender.sendMessage(message.replace(from, to));
    }
}
