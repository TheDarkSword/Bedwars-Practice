package it.thedarksword.bedwarspractice.scoreboard;

import com.google.common.collect.Sets;
import it.thedarksword.bedwarspractice.abstraction.interfacing.sidebar.Sidebar;
import it.thedarksword.bedwarspractice.abstraction.sessions.Session;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.text.SimpleDateFormat;
import java.util.*;

public class ScoreBoard implements Sidebar<String, Integer, String> {

    private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    private final CraftPlayer player;
    private final BoardsHandler boardsHandler;
    private Scoreboard scoreboard;
    private Objective objective, buffer;

    private String name;

    private Map<Integer, String> lines = new HashMap<>();
    private boolean deleted = false;

    public ScoreBoard(BoardsHandler boardsHandler, Player player, String name) {
        this(boardsHandler, player, null, name);
    }

    public ScoreBoard(BoardsHandler boardsHandler, Player player, Scoreboard scoreboard, String name) {
        this.boardsHandler = boardsHandler;
        this.player = (CraftPlayer) player;
        this.scoreboard = scoreboard;

        if (this.scoreboard == null) {
            Scoreboard sb = player.getScoreboard();

            if(sb == null || sb == Bukkit.getScoreboardManager().getMainScoreboard())
                sb = Bukkit.getScoreboardManager().getNewScoreboard();

            this.scoreboard = sb;
        }

        this.name = name;

        String subName = player.getName().length() <= 14
                ? player.getName()
                : player.getName().substring(0, 14);

        this.objective = this.scoreboard.getObjective("sb" + subName);
        this.buffer = this.scoreboard.getObjective("bf" + subName);

        if(this.objective == null)
            this.objective = this.scoreboard.registerNewObjective("sb" + subName, "dummy");
        if(this.buffer == null)
            this.buffer = this.scoreboard.registerNewObjective("bf" + subName, "dummy");

        this.objective.setDisplayName(name);
        sendObjective(this.objective, ObjectiveMode.CREATE);
        sendObjectiveDisplay(this.objective);

        this.buffer.setDisplayName(name);
        sendObjective(this.buffer, ObjectiveMode.CREATE);

        this.player.setScoreboard(this.scoreboard);
    }

    @Override
    public String get(Integer score) {
        if(this.deleted)
            throw new IllegalStateException("The PlayerBoard is deleted!");

        return this.lines.get(score);
    }

    @Override
    public void set(String name, Integer score) {
        if(this.deleted)
            throw new IllegalStateException("The PlayerBoard is deleted!");

        String oldName = this.lines.get(score);

        if(name.equals(oldName))
            return;

        this.lines.entrySet()
                .removeIf(entry -> entry.getValue().equals(name));

        if(oldName != null) {
            sendScore(this.buffer, oldName, score, true);
            sendScore(this.buffer, name, score, false);

            swapBuffers();

            sendScore(this.buffer, oldName, score, true);
            sendScore(this.buffer, name, score, false);
        } else {
            sendScore(this.objective, name, score, false);
            sendScore(this.buffer, name, score, false);
        }

        this.lines.put(score, name);
    }

    @Override
    public void setAll(String... lines) {
        if(this.deleted)
            throw new IllegalStateException("The PlayerBoard is deleted!");

        for(int i = 0; i < lines.length; i++) {
            String line = lines[i];

            set(line, lines.length - i);
        }

        Set<Integer> scores = Sets.newHashSet(this.lines.keySet());

        for (int score : scores) {
            if (score <= 0 || score > lines.length) {
                remove(score);
            }
        }
    }

    @Override
    public void clear() {
        Sets.newHashSet(this.lines.keySet()).forEach(this::remove);
        this.lines.clear();
    }

    private void swapBuffers() {
        sendObjectiveDisplay(this.buffer);

        Objective temp = this.buffer;

        this.buffer = this.objective;
        this.objective = temp;
    }

    private void sendObjective(Objective obj, ObjectiveMode mode) {
        player.getHandle().playerConnection.sendPacket(
                new PacketPlayOutScoreboardObjective((ScoreboardObjective) NMS.getObjective(obj), mode.ordinal()));
    }

    private void sendObjectiveDisplay(Objective obj) {
        player.getHandle().playerConnection.sendPacket(
                new PacketPlayOutScoreboardDisplayObjective(1, (ScoreboardObjective) NMS.getObjective(obj)));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void sendScore(Objective obj, String name, int score, boolean remove) {
        net.minecraft.server.v1_8_R3.Scoreboard nmsScoreboard = ((CraftScoreboard) scoreboard).getHandle();
        ScoreboardObjective scoreboardObjective = (ScoreboardObjective) NMS.getObjective(obj);
        ScoreboardScore scoreboardScore = new ScoreboardScore(
                nmsScoreboard, scoreboardObjective, name);

        scoreboardScore.setScore(score);

        Map<String, Map<ScoreboardObjective, ScoreboardScore>> scores = (Map) NMS.getPlayerScores(nmsScoreboard);

        if(remove) {
            if(scores.containsKey(name))
                ((Map) scores.get(name)).remove(scoreboardObjective);
        }
        else {
            if(!scores.containsKey(name))
                scores.put(name, new HashMap());

            ((Map) scores.get(name)).put(scoreboardObjective, nmsScoreboard);
        }

        PacketPlayOutScoreboardScore packet;
        if (remove) {
            packet = new PacketPlayOutScoreboardScore(name, scoreboardObjective);
        } else {
            packet = new PacketPlayOutScoreboardScore(scoreboardScore);
        }

        player.getHandle().playerConnection.sendPacket(packet);
    }

    private List<String> template() {
        List<String> template = new ArrayList<>();

        if(player.hasMetadata("session")) {
            Optional<Session> optional = boardsHandler.getBedwarsPractice().getManager().session(player);
            if(optional.isPresent()) {
                optional.get().sidebarTemplate(template);
                return template;
            }
        }

        template.add(ChatColor.GRAY + format.format(new Date()));
        template.add(" ");
        template.add("Mode: " + ChatColor.GREEN + "None");
        //template.add("  ");
        //template.add("Speed: " + ChatColor.GREEN + "0.00 m/s");
        //template.add("   ");
        //template.add("Personal Best: " + ChatColor.GREEN + "?");
        template.add("    ");
        template.add(ChatColor.YELLOW + "play.coralmc.it");

        return template;
    }

    public void refresh() {
        setAll(template().toArray(new String[0]));
    }

    @Override
    public void remove(Integer score) {
        if (this.deleted)
            throw new IllegalStateException("The PlayerBoard is deleted!");

        String name = this.lines.get(score);

        if(name != null) {
            scoreboard.resetScores(name);
            lines.remove(score);
        }
    }

    @Override
    public void delete() {
        if (this.deleted)
            return;

        boardsHandler.removeBoard(player);

        sendObjective(this.objective, ObjectiveMode.REMOVE);
        sendObjective(this.buffer, ObjectiveMode.REMOVE);

        this.objective.unregister();
        this.objective = null;

        this.buffer.unregister();
        this.buffer = null;

        this.lines = null;

        this.deleted = true;
    }

    @Override
    public Map<Integer, String> getLines() {
        if(this.deleted)
            throw new IllegalStateException("The PlayerBoard is deleted!");

        return new HashMap<>(lines);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if(this.deleted)
            throw new IllegalStateException("The PlayerBoard is deleted!");

        this.name = name;

        this.objective.setDisplayName(name);
        this.buffer.setDisplayName(name);

        sendObjective(this.objective, ObjectiveMode.UPDATE);
        sendObjective(this.buffer, ObjectiveMode.UPDATE);
    }

    public Player getPlayer() {
        return player;
    }

    public Scoreboard getScoreboard() { return scoreboard; }

    private enum ObjectiveMode { CREATE, REMOVE, UPDATE }
}
