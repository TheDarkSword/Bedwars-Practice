package it.thedarksword.bedwarspractice.config;

import it.thedarksword.bedwarspractice.yaml.Configuration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
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
    public final Material KBC_DIFFICULTY_MATERIAL;
    public final String KBC_DIFFICULTY_NAME;
    public final List<String> KBC_DIFFICULTY_LORE;

    public final Material CHECKPOINT_ENABLED_MATERIAL;
    public final DyeColor CHECKPOINT_ENABLED_COLOR;
    public final String CHECKPOINT_ENABLED_NAME;
    public final List<String> CHECKPOINT_ENABLED_LORE;
    public final Material CHECKPOINT_DISABLED_MATERIAL;
    public final DyeColor CHECKPOINT_DISABLED_COLOR;
    public final String CHECKPOINT_DISABLED_NAME;
    public final List<String> CHECKPOINT_DISABLED_LORE;

    public final Material LAUNCH_SETTINGS_MATERIAL;
    public final String LAUNCH_SETTINGS_NAME;
    public final List<String> LAUNCH_SETTINGS_LORE;

    //Games
    public final float MIN_Y;

    //Bridging
    public final float FINISH_Y;
    public final String B_WIN_TITLE;
    public final int B_fadeIn;
    public final int B_duration;
    public final int B_fadeOut;
    public final String B_WIN_MESSAGE;
    public final String B_LOOSE_MESSAGE;

    //Clutch Knockback
    public final int KNOCKBACK_DELAY;
    public final Material KNOCKBACK_START;
    public final Material KNOCKBACK_CHECKPOINT;
    public final String KBC_WIN_TITLE;
    public final int KBC_fadeIn;
    public final int KBC_duration;
    public final int KBC_fadeOut;
    public final String KBC_WIN_MESSAGE;
    public final String KBC_LOOSE_MESSAGE;

    //Clutch Wall
    public final Material WALL_BLOCK;
    public final Material WALL_CHECKPOINT;
    public final String WC_WIN_TITLE;
    public final int WC_fadeIn;
    public final int WC_duration;
    public final int WC_fadeOut;
    public final String WC_WIN_MESSAGE;
    public final String WC_LOOSE_MESSAGE;
    public final float WC_MAX_Y;

    //Launch
    public final int LAUNCH_START_OFFSET;
    public final String LAUNCH_WIN_TITLE;
    public final int LAUNCH_fadeIn;
    public final int LAUNCH_duration;
    public final int LAUNCH_fadeOut;
    public final String LAUNCH_WIN_MESSAGE;
    public final String LAUNCH_LOOSE_MESSAGE;

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
        KBC_DIFFICULTY_MATERIAL = Material.getMaterial(configuration.getString("kbc-difficulty.material"));
        KBC_DIFFICULTY_NAME = getTranslated(configuration.getString("kbc-difficulty.name"));
        KBC_DIFFICULTY_LORE = getTranslated(configuration.getStringList("kbc-difficulty.lore"));

        CHECKPOINT_ENABLED_MATERIAL = Material.getMaterial(configuration.getString("checkpoint.enabled.material"));
        CHECKPOINT_ENABLED_COLOR = DyeColor.valueOf(configuration.getString("checkpoint.enabled.color"));
        CHECKPOINT_ENABLED_NAME = getTranslated(configuration.getString("checkpoint.enabled.name"));
        CHECKPOINT_ENABLED_LORE = getTranslated(configuration.getStringList("checkpoint.enabled.lore"));

        CHECKPOINT_DISABLED_MATERIAL = Material.getMaterial(configuration.getString("checkpoint.disabled.material"));
        CHECKPOINT_DISABLED_COLOR = DyeColor.valueOf(configuration.getString("checkpoint.disabled.color"));
        CHECKPOINT_DISABLED_NAME = getTranslated(configuration.getString("checkpoint.disabled.name"));
        CHECKPOINT_DISABLED_LORE = getTranslated(configuration.getStringList("checkpoint.disabled.lore"));

        LAUNCH_SETTINGS_MATERIAL = Material.getMaterial(configuration.getString("launch.settings.material"));
        LAUNCH_SETTINGS_NAME = getTranslated(configuration.getString("launch.settings.name"));
        LAUNCH_SETTINGS_LORE = getTranslated(configuration.getStringList("launch.settings.lore"));

        MIN_Y = configuration.getFloat("games.min-y");

        FINISH_Y = configuration.getFloat("bridging.finish-y");
        B_WIN_TITLE = getTranslated(configuration.getString("bridging.win.title.message"));
        B_fadeIn = configuration.getInt("bridging.win.title.fadeIn");
        B_duration = configuration.getInt("bridging.win.title.duration");
        B_fadeOut = configuration.getInt("bridging.win.title.fadeOut");
        B_WIN_MESSAGE = getTranslated(configuration.getString("bridging.win.message"));
        B_LOOSE_MESSAGE = getTranslated(configuration.getString("bridging.loose.message"));

        KNOCKBACK_DELAY = configuration.getInt("clutch.knockback.delay");
        KNOCKBACK_START = Material.getMaterial(configuration.getString("clutch.knockback.materials.start"));
        KNOCKBACK_CHECKPOINT = Material.getMaterial(configuration.getString("clutch.knockback.materials.checkpoint"));
        KBC_WIN_TITLE = getTranslated(configuration.getString("clutch.knockback.win.title.message"));
        KBC_fadeIn = configuration.getInt("clutch.knockback.win.title.fadeIn");
        KBC_duration = configuration.getInt("clutch.knockback.win.title.duration");
        KBC_fadeOut = configuration.getInt("clutch.knockback.win.title.fadeOut");
        KBC_WIN_MESSAGE = getTranslated(configuration.getString("clutch.knockback.win.message"));
        KBC_LOOSE_MESSAGE = getTranslated(configuration.getString("clutch.knockback.loose.message"));

        WALL_BLOCK = Material.getMaterial(configuration.getString("clutch.wall.materials.block"));
        WALL_CHECKPOINT = Material.getMaterial(configuration.getString("clutch.wall.materials.checkpoint"));
        WC_WIN_TITLE = getTranslated(configuration.getString("clutch.wall.win.title.message"));
        WC_fadeIn = configuration.getInt("clutch.wall.win.title.fadeIn");
        WC_duration = configuration.getInt("clutch.wall.win.title.duration");
        WC_fadeOut = configuration.getInt("clutch.wall.win.title.fadeOut");
        WC_WIN_MESSAGE = getTranslated(configuration.getString("clutch.wall.win.message"));
        WC_LOOSE_MESSAGE = getTranslated(configuration.getString("clutch.wall.loose.message"));
        WC_MAX_Y = configuration.getFloat("clutch.wall.max-y");

        LAUNCH_START_OFFSET = configuration.getInt("launch.start-offset");
        LAUNCH_WIN_TITLE = getTranslated(configuration.getString("launch.win.title.message"));
        LAUNCH_fadeIn = configuration.getInt("launch.win.title.fadeIn");
        LAUNCH_duration = configuration.getInt("launch.win.title.duration");
        LAUNCH_fadeOut = configuration.getInt("launch.win.title.fadeOut");
        LAUNCH_WIN_MESSAGE = getTranslated(configuration.getString("launch.win.message"));
        LAUNCH_LOOSE_MESSAGE = getTranslated(configuration.getString("launch.loose.message"));
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
