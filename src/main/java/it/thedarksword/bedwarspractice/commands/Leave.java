package it.thedarksword.bedwarspractice.commands;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.ytnoos.dictation.api.commands.impl.SendServerCommand;
import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Leave implements CommandExecutor {

    private final BedwarsPractice bedwarsPractice;

    public Leave(BedwarsPractice bedwarsPractice){
        this.bedwarsPractice = bedwarsPractice;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            bedwarsPractice.sendMessage(sender, bedwarsPractice.getConfigValue().NOT_PLAYER);
            return true;
        }

        bedwarsPractice.getDictation().getCommandManager().executeCommand(new SendServerCommand(sender.getName(), "BWLobby"));
        return true;
    }
}
