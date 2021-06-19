package it.thedarksword.bedwarspractice.commands;

import com.google.common.collect.ImmutableList;
import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.commands.modules.*;
import it.thedarksword.bedwarspractice.commands.modules.Top;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BedwarsPracticeCommand implements CommandExecutor, TabCompleter {

    private final BedwarsPractice bedwarsPractice;

    public BedwarsPracticeCommand(BedwarsPractice bedwarsPractice) {
        this.bedwarsPractice = bedwarsPractice;
        Module.registerModule(new SetSpawn(bedwarsPractice));
        Module.registerModule(new SetSchematicSpawn(bedwarsPractice));
        Module.registerModule(new Start(bedwarsPractice));
        Module.registerModule(new RegisterType(bedwarsPractice));
        Module.registerModule(new SetFinish(bedwarsPractice));
        Module.registerModule(new Menu(bedwarsPractice));
        Module.registerModule(new Stop(bedwarsPractice));
        Module.registerModule(new Players(bedwarsPractice));
        Module.registerModule(new Top(bedwarsPractice));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            bedwarsPractice.sendMessage(sender, bedwarsPractice.getConfigValue().NOT_PLAYER);
            return true;
        }

        if (args.length == 0){
            bedwarsPractice.sendMessage(sender, bedwarsPractice.getConfigValue().INVALID_COMMAND);
            return true;
        }

        Module module = Module.getModuleByName(args[0]);
        if(module == null) {
            bedwarsPractice.sendMessage(sender, bedwarsPractice.getConfigValue().INVALID_COMMAND);
            return true;
        }

        if(!sender.hasPermission(module.getPermission())) {
            bedwarsPractice.sendMessage(sender, bedwarsPractice.getConfigValue().INSUFFICIENT_PERMISSION);
            return true;
        }

        module.execute((Player) sender, args);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            List<String> completer = new ArrayList<>();
           for(String module : Module.getModules().keySet()) {
               if(module.startsWith(args[0])) completer.add(module);
           }
           return completer;
        } else if(args.length > 1) {
            Module module = Module.getModuleByName(args[0]);
            if(module != null) return module.onTabComplete(sender, args);
        }
        return ImmutableList.of();
    }
}
