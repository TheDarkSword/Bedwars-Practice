package it.thedarksword.bedwarspractice.config;

import it.thedarksword.bedwarspractice.yaml.Configuration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ConfigValue {

    @Getter
    private final Configuration configuration;

    //Messages
    public final String NOT_PLAYER;
    public final String INSUFFICIENT_PERMISSION;
    public final String INVALID_COMMAND;
    public final String INVALID_PLACE;

    //MySQL
    public final String HOST;
    public final int PORT;
    public final String DATABASE;
    public final String USERNAME;
    public final String PASSWORD;
    public final String TABLE_PLAYERS;
    public final String TABLE_RECORDS;
    public final String TABLE_TYPES;

    //Items
    public final Material BLOCK_MATERIAL;
    public final Material CAN_PLACED;
    public final Material SETTINGS_MATERIAL;
    public final String SETTINGS_NAME;
    public final List<String> SETTINGS_LORE;
    public final Material MODE_MATERIAL;
    public final String MODE_NAME;
    public final List<String> MODE_LORE;

    //Game
    public final float MIN_Y;
    public final float FINISH_Y;
    public final String WIN_TITLE;
    public final int fadeIn;
    public final int duration;
    public final int fadeOut;
    public final String WIN_MESSAGE;
    public final String LOOSE_MESSAGE;

    public ConfigValue(Configuration configuration) {
        this.configuration = configuration;

        NOT_PLAYER = getTranslated(configuration.getString("messages.not-player"));
        INSUFFICIENT_PERMISSION = getTranslated(configuration.getString("messages.insufficient-permission"));
        INVALID_COMMAND = getTranslated(configuration.getString("messages.invalid-command"));
        INVALID_PLACE = getTranslated(configuration.getString("messages.invalid-place"));

        HOST = configuration.getString("mysql.host");
        PORT = configuration.getInt("mysql.port");
        DATABASE = configuration.getString("mysql.database");
        USERNAME = configuration.getString("mysql.username");
        PASSWORD = configuration.getString("mysql.password");
        TABLE_PLAYERS = configuration.getString("mysql.tables.players");
        TABLE_RECORDS = configuration.getString("mysql.tables.records");
        TABLE_TYPES = configuration.getString("mysql.tables.types");

        BLOCK_MATERIAL = Material.getMaterial(configuration.getString("block.material"));
        CAN_PLACED = Material.getMaterial(configuration.getString("block.can-placed"));
        SETTINGS_MATERIAL = Material.getMaterial(configuration.getString("settings.material"));
        SETTINGS_NAME = getTranslated(configuration.getString("settings.name"));
        SETTINGS_LORE = getTranslated(configuration.getStringList("settings.lore"));
        MODE_MATERIAL = Material.getMaterial(configuration.getString("mode.material"));
        MODE_NAME = getTranslated(configuration.getString("mode.name"));
        MODE_LORE = getTranslated(configuration.getStringList("mode.lore"));

        MIN_Y = configuration.getFloat("game.min-y");
        FINISH_Y = configuration.getFloat("game.finish-y");
        WIN_TITLE = getTranslated(configuration.getString("game.win.title.message"));
        fadeIn = configuration.getInt("game.win.title.fadeIn");
        duration = configuration.getInt("game.win.title.duration");
        fadeOut = configuration.getInt("game.win.title.fadeOut");
        WIN_MESSAGE = getTranslated(configuration.getString("game.win.message"));
        LOOSE_MESSAGE = getTranslated(configuration.getString("game.loose.message"));
    }

    private String getTranslated(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    private List<String> getTranslated(List<String> list) {
        List<String> translated = new ArrayList<>();
        for(String element : list) {
            translated.add(ChatColor.translateAlternateColorCodes('&', element));
        }
        return translated;
    }
}
