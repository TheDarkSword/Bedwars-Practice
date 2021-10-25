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

        if(args.length < 2) {
            player.sendMessage(bedwarsPractice.getConfigValue().INVALID_COMMAND);
            return;
        }

        String mode = args[1].toLowerCase();
        if(bedwarsPractice.getSpawns().setSpawnByMode(mode, location)){
            player.sendMessage("Spawn Set");
        } else {
            player.sendMessage("Invalid Mode");
        }
        //bedwarsPractice.getPractice().setSpawn(location);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of("bridging", "clutchKnockback", "clutchwall", "launch", "bedburrow");
    }
}
