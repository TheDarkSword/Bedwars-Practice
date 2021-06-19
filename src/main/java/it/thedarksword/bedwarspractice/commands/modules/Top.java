package it.thedarksword.bedwarspractice.commands.modules;

import com.google.common.collect.ImmutableList;
import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.commands.Module;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Top extends Module {

    public Top(BedwarsPractice bedwarsPractice) {
        super(bedwarsPractice, "top", "bwp.top", "tops");
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args.length == 1) {
            bedwarsPractice.getInventories().getTopsInventory().open(player);
            return;
        }
        String mode = args[1].toLowerCase();

        switch (mode) {
            case "bridging": {
                bedwarsPractice.getInventories().getTopBridgingInventory().open(player);
            }
            break;
            case "knockbackclutch": {
                bedwarsPractice.getInventories().getTopKnockbackClutchInventory().open(player);
            }
            break;
            default: {
                player.openInventory(bedwarsPractice.getInventories().getTopModality().get(args[1]));
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }
}
