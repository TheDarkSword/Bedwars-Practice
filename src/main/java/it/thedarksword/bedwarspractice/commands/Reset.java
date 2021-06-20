package it.thedarksword.bedwarspractice.commands;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.sql.SQLException;

@RequiredArgsConstructor
public class Reset implements CommandExecutor {

    private final BedwarsPractice bedwarsPractice;
    private boolean first = true;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof ConsoleCommandSender)){
            bedwarsPractice.sendMessage(sender, bedwarsPractice.getConfigValue().INVALID_COMMAND);
            return true;
        }
        if(first) {
            if(args.length == 0){
                sender.sendMessage(ChatColor.RED + "Sei sicuro di resettare? Se si fai /reset gG");
                first = false;
            } else {
                sender.sendMessage(ChatColor.RED + "Comando ultra sicuro! Fai solo /reset");
            }
        } else {
            if(args.length == 1 && args[0].equals("gG")) {
                try {
                    bedwarsPractice.getMySQLManager().deleteAllRecords();
                    sender.sendMessage(ChatColor.GREEN + "Tutti i records sono stati resettati");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Errore, non ho resettato nulla");
            }
            first = true;
        }
        return true;
    }
}
