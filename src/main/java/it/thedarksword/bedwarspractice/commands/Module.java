package it.thedarksword.bedwarspractice.commands;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public abstract class Module {

    @Getter private static final Map<String, Module> modules = new HashMap<>();

    protected final BedwarsPractice bedwarsPractice;

    protected final String name;
    protected final String permission;
    protected final String[] aliases;

    public Module(BedwarsPractice bedwarsPractice, String name, String permission, String... aliases) {
        this.bedwarsPractice = bedwarsPractice;
        this.name = name;
        this.permission = permission;
        this.aliases = aliases;
    }

    public abstract void execute(Player player, String[] args);
    public abstract List<String> onTabComplete(CommandSender sender, String[] args);

    public static void registerModule(Module module) {
        modules.put(module.getName(), module);
        for(String alias : module.getAliases()) modules.put(alias, module);
    }

    public static Module getModuleByName(String name) {
        return modules.get(name);
    }
}
