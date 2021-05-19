package it.thedarksword.bedwarspractice.commands.modules;

import com.google.common.collect.ImmutableList;
import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.commands.Module;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Stop extends Module {

    public Stop(BedwarsPractice bedwarsPractice) {
        super(bedwarsPractice, "stop", "bwp.stop");
    }

    @Override
    public void execute(Player player, String[] args) {
        if(player.hasMetadata("session")) {
            bedwarsPractice.getManager().endSession(player);
            player.removeMetadata("session", bedwarsPractice);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }
}
