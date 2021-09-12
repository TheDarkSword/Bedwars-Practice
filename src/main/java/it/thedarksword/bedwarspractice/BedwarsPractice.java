package it.thedarksword.bedwarspractice;

import it.thedarksword.bedwarspractice.clipboards.Schematic;
import it.thedarksword.bedwarspractice.clutch.sessions.KnockbackClutch;
import it.thedarksword.bedwarspractice.clutch.sessions.WallClutch;
import it.thedarksword.bedwarspractice.commands.*;
import it.thedarksword.bedwarspractice.config.ConfigValue;
import it.thedarksword.bedwarspractice.enchantment.GlowEnchant;
import it.thedarksword.bedwarspractice.listeners.BridgingListener;
import it.thedarksword.bedwarspractice.listeners.ClutchListener;
import it.thedarksword.bedwarspractice.listeners.NatureListener;
import it.thedarksword.bedwarspractice.manager.ConstantObjects;
import it.thedarksword.bedwarspractice.manager.Inventories;
import it.thedarksword.bedwarspractice.manager.Manager;
import it.thedarksword.bedwarspractice.manager.Spawns;
import it.thedarksword.bedwarspractice.mysql.MySQL;
import it.thedarksword.bedwarspractice.mysql.MySQLManager;
import it.thedarksword.bedwarspractice.packets.PacketListener;
import it.thedarksword.bedwarspractice.scoreboard.BoardsHandler;
import it.thedarksword.bedwarspractice.scoreboard.NMS;
import it.thedarksword.bedwarspractice.tasks.TopUpdater;
import it.thedarksword.bedwarspractice.yaml.Configuration;
import it.ytnoos.dictation.api.Dictation;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Getter
public class BedwarsPractice extends JavaPlugin {

    /**
     * Test rebase master branch
     */

    private Configuration settings;

    private ConfigValue configValue;

    private MySQLManager mySQLManager;

    private Dictation dictation;

    private PacketListener packetListener;

    private Manager manager;
    private ConstantObjects constantObjects;

    private BoardsHandler boardsHandler;

    private Schematic schematic;

    private KnockbackClutch.KnockbackClutchTask knockbackClutchTask;
    private WallClutch.WallClutchTask wallClutchTask;

    private Spawns spawns;
    private Inventories inventories;

    private TopUpdater topUpdater;

    private CraftServer craftServer;

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

        craftServer = (CraftServer) getServer();

        configValue = new ConfigValue(configuration);

        mySQLManager = new MySQLManager(configValue,
                new MySQL(configValue.HOST, configValue.PORT, configValue.DATABASE, configValue.USERNAME, configValue.PASSWORD));

        mySQLManager.createTables();

        if(!registerDictation()) {
            getServer().getLogger().severe("Dictation not Found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        GlowEnchant.registerGlow();
        NMS.init();

        packetListener = new PacketListener(this);

        spawns = new Spawns(settings);
        inventories = new Inventories(this);

        schematic = Schematic.loadSchematic(schematicFile);

        manager = new Manager(this);
        constantObjects = new ConstantObjects(this);

        boardsHandler = new BoardsHandler(this);

        knockbackClutchTask = new KnockbackClutch.KnockbackClutchTask();
        knockbackClutchTask.runTaskTimerAsynchronously(this, 2, 2);

        wallClutchTask = new WallClutch.WallClutchTask();
        wallClutchTask.runTaskTimerAsynchronously(this, 2, 2);

        topUpdater = new TopUpdater(this);
        topUpdater.runTaskTimerAsynchronously(this, 0, 6000); // 6000 ticks = 5 minutes

        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        getCommand("bedwarspractice").setExecutor(new BedwarsPracticeCommand(this));
        getCommand("bwtest").setExecutor(new BwTest(this));
        getCommand("top").setExecutor(new Top(this));
        getCommand("reset").setExecutor(new Reset(this));
        getCommand("leave").setExecutor(new Leave(this));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new BridgingListener(this), this);
        getServer().getPluginManager().registerEvents(new NatureListener(this), this);
        getServer().getPluginManager().registerEvents(new BoardsHandler.BoardsListener(boardsHandler), this);
        getServer().getPluginManager().registerEvents(new ClutchListener(this), this);
    }

    public void sendMessage(CommandSender sender, String message){
        sender.sendMessage(message);
    }

    public void sendMessage(CommandSender sender, String message, String from, String to){
        sender.sendMessage(message.replace(from, to));
    }

    private boolean registerDictation() {
        RegisteredServiceProvider<Dictation> provider = getServer().getServicesManager().getRegistration(Dictation.class);
        if(provider == null) return false;
        dictation = provider.getProvider();
        return true;
    }
}
