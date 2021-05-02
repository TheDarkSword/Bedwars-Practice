package it.thedarksword.bedwarspractice.commands.modules;

import com.google.common.collect.ImmutableList;
import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.commands.Module;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetSpawn extends Module {

    public SetSpawn(BedwarsPractice bedwarsPractice) {
        super(bedwarsPractice, "setspawn", "bwp.admin", "ss");
    }

    @SneakyThrows
    @Override
    public void execute(Player player, String[] args) {
        Location location = player.getLocation();

        bedwarsPractice.getSettings().set("spawn.world", location.getWorld().getName());
        bedwarsPractice.getSettings().set("spawn.x", location.getBlockX());
        bedwarsPractice.getSettings().set("spawn.y", location.getBlockY());
        bedwarsPractice.getSettings().set("spawn.z", location.getBlockZ());
        bedwarsPractice.getSettings().set("spawn.yaw", location.getYaw());
        bedwarsPractice.getSettings().set("spawn.pitch", location.getPitch());

        bedwarsPractice.getSettings().save();
        bedwarsPractice.setSpawn(location);
        //bedwarsPractice.getPractice().setSpawn(location);
        player.sendMessage("Spawn Set");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }
}
