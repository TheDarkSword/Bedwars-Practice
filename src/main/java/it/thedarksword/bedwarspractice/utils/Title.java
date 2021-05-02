package it.thedarksword.bedwarspractice.utils;

import lombok.Getter;
import lombok.NonNull;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

@Getter
public class Title {

    private final PacketPlayOutTitle title;
    private final PacketPlayOutTitle subtitle;

    public Title(String title, String subtitle, int fadeIn, int duration, int fadeOut) {
        if(title == null) this.title = null;
        else this.title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
                IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', title) + "\"}"), fadeIn, duration, fadeOut);
        if(subtitle == null) this.subtitle = null;
        else this.subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
                IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', subtitle) + "\"}"), fadeIn, duration, fadeOut);
    }

    public void send(@NonNull Player player) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        if(title != null) connection.sendPacket(title);
        if(subtitle != null) connection.sendPacket(subtitle);
    }

    public static void buildAndSend(@NonNull Player player, String title, int fadeIn, int duration, int fadeOut){
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        if(title != null) {
            connection.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
                    IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', title) + "\"}"), fadeIn, duration, fadeOut));
        }
    }

    public static void buildAndSend(@NonNull Player player, String title, String subtitle, int fadeIn, int duration, int fadeOut){
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        if(title != null) {
            connection.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
                    IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', title) + "\"}"), fadeIn, duration, fadeOut));
        }
        if(subtitle != null) {
            connection.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
                    IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', subtitle) + "\"}"), fadeIn, duration, fadeOut));
        }
    }
}
