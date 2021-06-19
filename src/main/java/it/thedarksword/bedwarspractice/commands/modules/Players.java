package it.thedarksword.bedwarspractice.commands.modules;

import com.google.common.collect.ImmutableList;
import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.commands.Module;
import lombok.SneakyThrows;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Players extends Module {

    public Players(BedwarsPractice bedwarsPractice) {
        super(bedwarsPractice, "players", "bwp.admin");
    }

    @SneakyThrows
    @Override
    public void execute(Player player, String[] args) {
        if(args.length < 3) {
            player.sendMessage(bedwarsPractice.getConfigValue().INVALID_COMMAND);
            return;
        }

        String mode = args[1];
        if(mode.equalsIgnoreCase("show")) {
            if(args[2].equalsIgnoreCase("all")) {
                for(Player other : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(other);
                }
            } else {
                Player other = Bukkit.getPlayer(args[2]);
                if(other != null) {
                    player.showPlayer(other);
                }
            }
        } else if(mode.equalsIgnoreCase("hide")) {
            CraftPlayer craftPlayer = (CraftPlayer) player;
            if(args[2].equalsIgnoreCase("all")) {
                for(CraftPlayer other : bedwarsPractice.getCraftServer().getOnlinePlayers()) {
                    player.hidePlayer(other);
                    craftPlayer.getHandle().playerConnection.sendPacket(
                            new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, other.getHandle()));
                }
            } else {
                CraftPlayer other = (CraftPlayer) Bukkit.getPlayer(args[2]);
                if(other != null) {
                    player.hidePlayer(other);
                    craftPlayer.getHandle().playerConnection.sendPacket(
                            new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, other.getHandle()));
                }
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if(args.length == 2) {
          if("show".startsWith(args[1])) return ImmutableList.of("show");
          else if("hide".startsWith(args[1])) return ImmutableList.of("hide");
          return ImmutableList.of("show", "hide");
        } else if(args.length == 3) {
            List<String> list = new ArrayList<>();
            for(Player player : Bukkit.getOnlinePlayers()) {
                if(player.getName().startsWith(args[2]))
                    list.add(player.getName());
            }
            if("all".startsWith(args[2])) list.add("all");
            return list;
        }
        return ImmutableList.of();
    }
}
