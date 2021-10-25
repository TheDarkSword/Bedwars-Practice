package it.thedarksword.bedwarspractice.manager;

import it.thedarksword.bedwarspractice.utils.location.CloneableLocation;
import it.thedarksword.bedwarspractice.yaml.Configuration;
import lombok.Data;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

@Data
public class Spawns {

    private final Configuration settings;

    private final Bridging bridging = new Bridging();
    private final KnockbackClutch knockbackClutch = new KnockbackClutch();
    private final WallClutch wallClutch = new WallClutch();
    private final Launch launch = new Launch();
    private final BedBurrow bedBurrow = new BedBurrow();

    public Spawns(Configuration settings) {
        this.settings = settings;

        if(!settings.getString("spawn.bridging.world").isEmpty()) {
            bridging.spawn = new Location(
                    Bukkit.getWorld(settings.getString("spawn.bridging.world")),
                    settings.getDouble("spawn.bridging.x"),
                    settings.getDouble("spawn.bridging.y"),
                    settings.getDouble("spawn.bridging.z"),
                    settings.getFloat("spawn.bridging.yaw"),
                    settings.getFloat("spawn.bridging.pitch")
            );
        }

        if(!settings.getString("schematic.world").isEmpty()) {
            bridging.schematicSpawn = new CloneableLocation(new Location(
                    Bukkit.getWorld(settings.getString("schematic.world")),
                    settings.getInt("schematic.x"),
                    settings.getInt("schematic.y"),
                    settings.getInt("schematic.z"),
                    settings.getFloat("schematic.yaw"),
                    settings.getFloat("schematic.pitch")
            ));
        }

        if(!settings.getString("spawn.clutch.knockback.world").isEmpty()) {
            knockbackClutch.spawn = new Location(
                    Bukkit.getWorld(settings.getString("spawn.clutch.knockback.world")),
                    settings.getInt("spawn.clutch.knockback.x"),
                    settings.getInt("spawn.clutch.knockback.y"),
                    settings.getInt("spawn.clutch.knockback.z"),
                    settings.getFloat("spawn.clutch.knockback.yaw"),
                    settings.getFloat("spawn.clutch.knockback.pitch")
            );
        }

        if(!settings.getString("finish.1.clutch.knockback.world").isEmpty()) {
            knockbackClutch.finish1 = new Location(
                    Bukkit.getWorld(settings.getString("finish.1.clutch.knockback.world")),
                    settings.getInt("finish.1.clutch.knockback.x"),
                    settings.getInt("finish.1.clutch.knockback.y"),
                    settings.getInt("finish.1.clutch.knockback.z")
            );
        }

        if(!settings.getString("finish.2.clutch.knockback.world").isEmpty()) {
            knockbackClutch.finish2 = new Location(
                    Bukkit.getWorld(settings.getString("finish.2.clutch.knockback.world")),
                    settings.getInt("finish.2.clutch.knockback.x"),
                    settings.getInt("finish.2.clutch.knockback.y"),
                    settings.getInt("finish.2.clutch.knockback.z")
            );
        }

        if(!settings.getString("spawn.clutch.wall.world").isEmpty()) {
            wallClutch.spawn = new Location(
                    Bukkit.getWorld(settings.getString("spawn.clutch.wall.world")),
                    settings.getInt("spawn.clutch.wall.x"),
                    settings.getInt("spawn.clutch.wall.y"),
                    settings.getInt("spawn.clutch.wall.z"),
                    settings.getFloat("spawn.clutch.wall.yaw"),
                    settings.getFloat("spawn.clutch.wall.pitch")
            );
        }

        if(!settings.getString("finish.1.clutch.wall.world").isEmpty()) {
            wallClutch.finish1 = new Location(
                    Bukkit.getWorld(settings.getString("finish.1.clutch.wall.world")),
                    settings.getInt("finish.1.clutch.wall.x"),
                    settings.getInt("finish.1.clutch.wall.y"),
                    settings.getInt("finish.1.clutch.wall.z")
            );
        }

        if(!settings.getString("finish.2.clutch.wall.world").isEmpty()) {
            wallClutch.finish2 = new Location(
                    Bukkit.getWorld(settings.getString("finish.2.clutch.wall.world")),
                    settings.getInt("finish.2.clutch.wall.x"),
                    settings.getInt("finish.2.clutch.wall.y"),
                    settings.getInt("finish.2.clutch.wall.z")
            );
        }

        if(!settings.getString("spawn.launch.world").isEmpty()) {
            launch.spawn = new Location(
                    Bukkit.getWorld(settings.getString("spawn.launch.world")),
                    settings.getInt("spawn.launch.x"),
                    settings.getInt("spawn.launch.y"),
                    settings.getInt("spawn.launch.z"),
                    settings.getFloat("spawn.launch.yaw"),
                    settings.getFloat("spawn.launch.pitch")
            );
            launch.finish = Material.GOLD_BLOCK;
        }

        if(!settings.getString("spawn.bedburrow.world").isEmpty()) {
            bedBurrow.spawn = new Location(
                    Bukkit.getWorld(settings.getString("spawn.bedburrow.world")),
                    settings.getInt("spawn.bedburrow.x"),
                    settings.getInt("spawn.bedburrow.y"),
                    settings.getInt("spawn.bedburrow.z"),
                    settings.getFloat("spawn.bedburrow.yaw"),
                    settings.getFloat("spawn.bedburrow.pitch")
            );
        }
    }


    @SneakyThrows
    public boolean setSpawnByMode(String mode, Location location){
        switch (mode) {
            case "bridging":
                bridging.spawn = location;
                settings.set("spawn.bridging.world", location.getWorld().getName());
                settings.set("spawn.bridging.x", location.getBlockX());
                settings.set("spawn.bridging.y", location.getBlockY());
                settings.set("spawn.bridging.z", location.getBlockZ());
                settings.set("spawn.bridging.yaw", location.getYaw());
                settings.set("spawn.bridging.pitch", location.getPitch());
                break;
            case "clutchknockback":
                knockbackClutch.spawn = location;
                settings.set("spawn.clutch.knockback.world", location.getWorld().getName());
                settings.set("spawn.clutch.knockback.x", location.getBlockX());
                settings.set("spawn.clutch.knockback.y", location.getBlockY());
                settings.set("spawn.clutch.knockback.z", location.getBlockZ());
                settings.set("spawn.clutch.knockback.yaw", location.getYaw());
                settings.set("spawn.clutch.knockback.pitch", location.getPitch());
                break;
            case "clutchwall":
                wallClutch.spawn = location;
                settings.set("spawn.clutch.wall.world", location.getWorld().getName());
                settings.set("spawn.clutch.wall.x", location.getBlockX());
                settings.set("spawn.clutch.wall.y", location.getBlockY());
                settings.set("spawn.clutch.wall.z", location.getBlockZ());
                settings.set("spawn.clutch.wall.yaw", location.getYaw());
                settings.set("spawn.clutch.wall.pitch", location.getPitch());
                break;
            case "launch":
                launch.spawn = location;
                settings.set("spawn.launch.world", location.getWorld().getName());
                settings.set("spawn.launch.x", location.getBlockX());
                settings.set("spawn.launch.y", location.getBlockY());
                settings.set("spawn.launch.z", location.getBlockZ());
                settings.set("spawn.launch.yaw", location.getYaw());
                settings.set("spawn.launch.pitch", location.getPitch());
                break;
            case "bedburrow":
                bedBurrow.spawn = location;
                settings.set("spawn.bedburrow.world", location.getWorld().getName());
                settings.set("spawn.bedburrow.x", location.getBlockX());
                settings.set("spawn.bedburrow.y", location.getBlockY());
                settings.set("spawn.bedburrow.z", location.getBlockZ());
                settings.set("spawn.bedburrow.yaw", location.getYaw());
                settings.set("spawn.bedburrow.pitch", location.getPitch());
                break;
            default:
                return false;
        }

        settings.save();
        return true;
    }

    @SneakyThrows
    public boolean setFinishByMode(String mode, Location location, boolean first){
        switch (mode) {
            case "clutchknockback":
                if(first) {
                    knockbackClutch.finish1 = location;
                    settings.set("finish.1.clutch.knockback.world", location.getWorld().getName());
                    settings.set("finish.1.clutch.knockback.x", location.getBlockX());
                    settings.set("finish.1.clutch.knockback.y", location.getBlockY());
                    settings.set("finish.1.clutch.knockback.z", location.getBlockZ());
                } else {
                    knockbackClutch.finish2 = location;
                    settings.set("finish.2.clutch.knockback.world", location.getWorld().getName());
                    settings.set("finish.2.clutch.knockback.x", location.getBlockX());
                    settings.set("finish.2.clutch.knockback.y", location.getBlockY());
                    settings.set("finish.2.clutch.knockback.z", location.getBlockZ());
                }
                break;
            case "clutchwall":
                if(first) {
                    wallClutch.finish1 = location;
                    settings.set("finish.1.clutch.wall.world", location.getWorld().getName());
                    settings.set("finish.1.clutch.wall.x", location.getBlockX());
                    settings.set("finish.1.clutch.wall.y", location.getBlockY());
                    settings.set("finish.1.clutch.wall.z", location.getBlockZ());
                } else {
                    wallClutch.finish2 = location;
                    settings.set("finish.2.clutch.wall.world", location.getWorld().getName());
                    settings.set("finish.2.clutch.wall.x", location.getBlockX());
                    settings.set("finish.2.clutch.wall.y", location.getBlockY());
                    settings.set("finish.2.clutch.wall.z", location.getBlockZ());
                }
                break;
            case "launch":

            default:
                return false;
        }

        settings.save();
        return true;
    }

    @Data
    public static class Bridging {
        private Location spawn;
        private CloneableLocation schematicSpawn;
    }

    @Data
    public static class KnockbackClutch {
        private Location spawn;
        private Location finish1;
        private Location finish2;
    }

    @Data
    public static class WallClutch {
        private Location spawn;
        private Location finish1;
        private Location finish2;
    }

    @Data
    public static class Launch {
        private Location spawn;
        private Material finish;
    }

    @Data
    public static class BedBurrow {
        private Location spawn;
    }
}
