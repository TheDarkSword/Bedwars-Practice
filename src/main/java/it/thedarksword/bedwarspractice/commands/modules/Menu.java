package it.thedarksword.bedwarspractice.commands.modules;

import com.google.common.collect.ImmutableList;
import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.commands.Module;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Menu extends Module {

    public Menu(BedwarsPractice bedwarsPractice) {
        super(bedwarsPractice, "menu", "bwp.menu");
    }

    @Override
    public void execute(Player player, String[] args) {
        bedwarsPractice.getInventories().getModeInventory().open(player);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }
}
