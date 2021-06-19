package it.thedarksword.bedwarspractice.commands;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.Session;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.security.SecureRandom;
import java.util.Optional;

@RequiredArgsConstructor
public class BwTest implements CommandExecutor {

    private final BedwarsPractice bedwarsPractice;
    private final SecureRandom random = new SecureRandom();

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(!player.hasPermission("bw.test")) return true;
        /*if(args.length == 1) {
            /*World nmsWorld = ((CraftWorld) player.getWorld()).getHandle();
            org.bukkit.Chunk chunk = player.getWorld().getChunkAt(player.getLocation());
            Chunk nmsChunk = nmsWorld.getChunkAt(chunk.getX(), chunk.getZ());
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutMapChunk(nmsChunk, true, 20));*/
            /*List<BlockLocation> blocks = bedwarsPractice.getPractice().getPlayers().get(player.getName()).getBlocks();
            for (BlockLocation location : blocks) {
                player.sendBlockChange(location.toBukkitLocation(bedwarsPractice.getPractice().getWorld()), 0, (byte)0);
            }
            blocks.clear();
            return true;
        }
        /*bedwarsPractice.getPractice().getPacketInjector().addPlayer(player);
        PracticePlayer practicePlayer = new PracticePlayer(player, null);
        bedwarsPractice.getPractice().getFinishSchematic().pasteSchematic(player.getLocation(), practicePlayer);
        bedwarsPractice.getPractice().getPlayers().put(player.getName(), practicePlayer);*/
        /*List<BlockLocation> blocks = bedwarsPractice.getPractice().getPlayers().get(player.getName()).getSchematicBlocks();
        for (BlockLocation location : blocks) {
            player.sendBlockChange(location.toBukkitLocation(bedwarsPractice.getPractice().getWorld()), 0, (byte)0);
        }
        blocks.clear();
        Location location = bedwarsPractice.getPractice().getSpawn().clone().add(50, -1, -3);
        bedwarsPractice.getPractice().getFinishSchematic()
                .pasteSchematic(location, bedwarsPractice.getPractice().getPlayers().get(player.getName()));
        sender.sendMessage("Executed");*/
        player.sendMessage("Y: " + player.getEyeLocation().getY());
        return true;
    }
}
