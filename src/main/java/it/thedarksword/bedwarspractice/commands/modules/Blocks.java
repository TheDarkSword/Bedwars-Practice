package it.thedarksword.bedwarspractice.commands.modules;

import com.google.common.collect.ImmutableList;
import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.commands.Module;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Blocks extends Module {

    public Blocks(BedwarsPractice bedwarsPractice) {
        super(bedwarsPractice, "blocks", "bwp.blocks", "block", "blocchi");
    }

    @Override
    public void execute(Player player, String[] args) {
        bedwarsPractice.getInventories().getBlocksInventory().open(player);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }
}
