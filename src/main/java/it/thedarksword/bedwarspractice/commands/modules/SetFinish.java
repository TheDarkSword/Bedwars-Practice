package it.thedarksword.bedwarspractice.commands.modules;

import com.google.common.collect.ImmutableList;
import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.commands.Module;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetFinish extends Module {

    public SetFinish(BedwarsPractice bedwarsPractice) {
        super(bedwarsPractice, "setfinish", "bwp.admin", "sf");
    }

    @SneakyThrows
    @Override
    public void execute(Player player, String[] args) {
        Location location = player.getLocation();

        if(args.length < 3) {
            player.sendMessage(bedwarsPractice.getConfigValue().INVALID_COMMAND);
            return;
        }

        String mode = args[1].toLowerCase();
        boolean first = args[2].equals("1");
        if(bedwarsPractice.getSpawns().setFinishByMode(mode, location, first)){
            player.sendMessage("Finish Set (" + args[2] + ")");
        } else {
            player.sendMessage("Invalid Mode");
        }
        //bedwarsPractice.getPractice().setSpawn(location);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of("clutchKnockback 1", "clutchKnockback 2", "clutchwall 1", "clutchwall 2");
    }
}
