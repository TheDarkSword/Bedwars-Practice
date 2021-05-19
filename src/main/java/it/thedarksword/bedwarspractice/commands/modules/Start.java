package it.thedarksword.bedwarspractice.commands.modules;

import com.google.common.collect.ImmutableList;
import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingSession;
import it.thedarksword.bedwarspractice.bridging.sessions.straight.none.ShortStraightBridging;
import it.thedarksword.bedwarspractice.clutch.sessions.KnockbackClutch;
import it.thedarksword.bedwarspractice.commands.Module;
import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;

public class Start extends Module {

    public Start(BedwarsPractice bedwarsPractice) {
        super(bedwarsPractice, "start", "bwp.start");
    }

    @SneakyThrows
    @Override
    public void execute(Player player, String[] args) {
        /*if(player.hasMetadata("session")) {
            //Bridging.STRAIGHT.endSession(player);
            bedwarsPractice.getManager().endSession(player);
            player.removeMetadata("session", bedwarsPractice);
            return;
        }*/

        if(args.length < 2) {
            BridgingSession session = new ShortStraightBridging(bedwarsPractice);
            player.setMetadata("session", new FixedMetadataValue(bedwarsPractice, session.getType().name()));
            bedwarsPractice.getManager().newSession(player, session);
        } else {
            if(args[1].equalsIgnoreCase("bridging")) {
                BridgingSession session = new ShortStraightBridging(bedwarsPractice);
                player.setMetadata("session", new FixedMetadataValue(bedwarsPractice, session.getType().name()));
                bedwarsPractice.getManager().newSession(player, session);
            } else if(args[1].equalsIgnoreCase("knockbackclutch")) {
                KnockbackClutch session = new KnockbackClutch(bedwarsPractice, player);
                player.setMetadata("session", new FixedMetadataValue(bedwarsPractice, session.getType().name()));
                bedwarsPractice.getManager().newSession(player, session);
            }
        }
        //BridgingSession session = Bridging.STRAIGHT.newSession(player);
        //player.setMetadata("bridging", new FixedMetadataValue(bedwarsPractice, session.getType().getName()));
        /*if(bedwarsPractice.getPractice().getPlayers().containsKey(player.getName())) {
            return;
        }
        player.teleport(bedwarsPractice.getPractice().getSpawn());

        for(Player other : Bukkit.getOnlinePlayers()) {
            player.hidePlayer(other);
        }

        bedwarsPractice.getPractice().getPacketInjector().addPlayer(player);
        Location location = bedwarsPractice.getPractice().getSpawn().clone().add(30, -1, -3);
        PracticePlayer practicePlayer = new PracticePlayer(player, bedwarsPractice.getPractice());
        bedwarsPractice.getPractice().getFinishSchematic()
                .pasteSchematic(location, practicePlayer);
        bedwarsPractice.getPractice().getPlayers().put(player.getName(), practicePlayer);

        player.getInventory().clear();
        player.getInventory().setItem(0, bedwarsPractice.getPractice().getWood());
        player.getInventory().setItem(1, bedwarsPractice.getPractice().getWood());
        player.getInventory().setItem(2, bedwarsPractice.getPractice().getWood());
        player.getInventory().setItem(3, bedwarsPractice.getPractice().getWood());
        player.getInventory().setItem(7, bedwarsPractice.getPractice().getSettingsItem());
        player.getInventory().setItem(7, bedwarsPractice.getPractice().getModeItem());*/
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of("bridging", "knockbackclutch");
    }
}
