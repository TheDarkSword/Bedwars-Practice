package it.thedarksword.bedwarspractice.commands.modules;

import com.google.common.collect.ImmutableList;
import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.commands.Module;
import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RegisterType extends Module {

    public RegisterType(BedwarsPractice bedwarsPractice) {
        super(bedwarsPractice, "registertype", "bwp.admin", "rt");
    }

    @SneakyThrows
    @Override
    public void execute(Player player, String[] args) {
        if(args.length < 2) {
            player.sendMessage(bedwarsPractice.getConfigValue().INVALID_COMMAND);
            return;
        }

        bedwarsPractice.getMySQLManager().registerType(args[1]);

        player.sendMessage("Registered new type! (" + args[1] + ")");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }
}
