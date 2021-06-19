package it.thedarksword.bedwarspractice.commands;

import com.google.common.collect.ImmutableList;
import it.thedarksword.bedwarspractice.BedwarsPractice;
import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class Top implements CommandExecutor, TabCompleter {

    private final BedwarsPractice bedwarsPractice;

    public Top(BedwarsPractice bedwarsPractice){
        this.bedwarsPractice = bedwarsPractice;
    }

    @SneakyThrows
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            bedwarsPractice.sendMessage(sender, bedwarsPractice.getConfigValue().NOT_PLAYER);
            return true;
        }

        if(!sender.hasPermission("bwp.top")) {
            bedwarsPractice.sendMessage(sender, bedwarsPractice.getConfigValue().INSUFFICIENT_PERMISSION);
            return true;
        }
        /*if(args.length < 1){
            bedwarsPractice.sendMessage(sender, bedwarsPractice.getConfigValue().INVALID_COMMAND);
            return true;
        }
        String mode = args[0];
        int limit;
        if(args.length > 1) {
            try {
                limit = Integer.parseInt(args[1]);
                if(limit > 32) limit = 32;
            } catch (NumberFormatException ignored) {
                bedwarsPractice.sendMessage(sender, bedwarsPractice.getConfigValue().INVALID_COMMAND);
                return true;
            }
        } else {
            limit = 3;
        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lCORAL&f&lMC CLASSIFICA (" +
                mode + ") &7»"));
        List<Pair<String, Double>> list = bedwarsPractice.getMySQLManager().getTop(mode, limit);
        for(int i = 0; i < list.size(); i++) {
            Pair<String, Double> pair = list.get(i);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&f&l" + (i+1) + " &7» &b" + pair.getKey() + " &7- &b" + pair.getValue()));
        }*/
        bedwarsPractice.getInventories().getTopsInventory().open(((Player) sender));
        return true;
    }


    @SneakyThrows
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        /*if(args.length == 1) {
            List<String> types = new ArrayList<>(this.types);
            types.removeIf(string -> !string.startsWith(args[0]));
            return types;
        } else if(args.length == 2) {
            List<String> list = new ArrayList<String>() {{
                add("1");add("2");add("3");add("4");add("5");add("6");add("7");add("8");add("9");
            }};
            list.removeIf(string -> !string.startsWith(args[1]));
            return list;
        }*/
        return ImmutableList.of();
    }
}
