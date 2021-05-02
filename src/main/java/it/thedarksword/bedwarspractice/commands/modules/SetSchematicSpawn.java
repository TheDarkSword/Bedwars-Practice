package it.thedarksword.bedwarspractice.commands.modules;

import com.google.common.collect.ImmutableList;
import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.commands.Module;
import it.thedarksword.bedwarspractice.utils.location.CloneableLocation;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetSchematicSpawn extends Module {

    public SetSchematicSpawn(BedwarsPractice bedwarsPractice) {
        super(bedwarsPractice, "setschematicspawn", "bwp.admin", "ssc");
    }

    @SneakyThrows
    @Override
    public void execute(Player player, String[] args) {
        Location location = player.getLocation();

        bedwarsPractice.getSettings().set("schematic.world", location.getWorld().getName());
        bedwarsPractice.getSettings().set("schematic.x", location.getBlockX());
        bedwarsPractice.getSettings().set("schematic.y", location.getBlockY());
        bedwarsPractice.getSettings().set("schematic.z", location.getBlockZ());
        bedwarsPractice.getSettings().set("schematic.yaw", location.getYaw());
        bedwarsPractice.getSettings().set("schematic.pitch", location.getPitch());

        bedwarsPractice.getSettings().save();
        bedwarsPractice.setSchematicSpawn(new CloneableLocation(location));
        //bedwarsPractice.getPractice().setSpawn(location);
        player.sendMessage("Schematic Spawn Set");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }
}
