package it.thedarksword.bedwarspractice.manager;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.Session;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class Manager {

    @Getter private final Map<Integer, Session> sessions = new HashMap<>();

    private final BedwarsPractice bedwarsPractice;

    public void endSession(Player player) {
        Validate.notNull(player, "Null players can't have training sessions");
        sessions.computeIfPresent(player.getEntityId(), (integer, session) -> {
            session.stop(player);
            session.clearSchematic(player);
            for(Player other : Bukkit.getOnlinePlayers()) {
                player.showPlayer(other);
            }
            bedwarsPractice.getPacketListener().removePlayer(player);
            return sessions.remove(player.getEntityId());
        });
        /*Optional.of(sessions.get(player.getEntityId())).ifPresent(session -> {
            session.stop(player);
            session.clearSchematic(player);
            for(Player other : Bukkit.getOnlinePlayers()) {
                player.showPlayer(other);
            }
            sessions.remove(player.getEntityId());
            bedwarsPractice.getPacketListener().removePlayer(player);
        });*/
    }

    private void switchSession(Player player, Session oldSession, Session newSession) {
        if(oldSession.isRunning()) player.teleport(bedwarsPractice.getSpawn());
        oldSession.clearSchematic(player);
        oldSession.stop(player);
        sessions.put(player.getEntityId(), newSession);
        newSession.pasteSchematic(player, bedwarsPractice.getSchematic(), bedwarsPractice.getSchematicSpawn().cloneLocation());
        newSession.load(player);
    }

    @SneakyThrows
    public void newSession(Player player, Session session) {
        Validate.notNull(player, "Null players can't have training sessions");
        if(sessions.containsKey(player.getEntityId())) {
            Session oldSession = sessions.get(player.getEntityId());
            if(oldSession.getClass().equals(session.getClass())) return;
            switchSession(player, oldSession, session);
            return;
        }
        session.load(player);
        session.init(player);
        player.teleport(bedwarsPractice.getSpawn());
        for(Player other : Bukkit.getOnlinePlayers()) {
            player.hidePlayer(other);
        }
        sessions.put(player.getEntityId(), session);
        bedwarsPractice.getPacketListener().addPlayer(player);
        session.pasteSchematic(player, bedwarsPractice.getSchematic(), bedwarsPractice.getSchematicSpawn().cloneLocation());
    }

    public Optional<Session> session(Player player) {
        return Optional.ofNullable(sessions.get(player.getEntityId()));
    }
}
