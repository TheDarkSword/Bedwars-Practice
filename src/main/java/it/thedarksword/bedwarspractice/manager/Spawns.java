package it.thedarksword.bedwarspractice.manager;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.clipboards.Cuboid;
import it.thedarksword.bedwarspractice.utils.location.CloneableLocation;
import it.thedarksword.bedwarspractice.yaml.Configuration;
import lombok.Data;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Data
public class Spawns {

    private final Configuration settings;

    private final Bridging bridging = new Bridging();
    private final KnockbackClutch knockbackClutch = new KnockbackClutch();

    public Spawns(Configuration settings) {
        this.settings = settings;

        if(!settings.getString("spawn.bridging.world").isEmpty()) {
            bridging.spawn = new Location(
                    Bukkit.getWorld(settings.getString("spawn.bridging.world")),
                    settings.getInt("spawn.bridging.x"),
                    settings.getInt("spawn.bridging.y"),
                    settings.getInt("spawn.bridging.z"),
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
}
