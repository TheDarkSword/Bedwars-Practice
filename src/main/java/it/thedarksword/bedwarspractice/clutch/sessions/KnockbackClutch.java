package it.thedarksword.bedwarspractice.clutch.sessions;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.clutch.ClutchSession;
import it.thedarksword.bedwarspractice.clipboards.Cuboid;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class KnockbackClutch extends ClutchSession implements Comparable<KnockbackClutch> {

    private long lastKnock;

    public KnockbackClutch(BedwarsPractice bedwarsPractice, Player player) {
        super(bedwarsPractice, player);
        setFinishArea(new Cuboid(bedwarsPractice.getSpawns().getKnockbackClutch().getFinish1(),
                bedwarsPractice.getSpawns().getKnockbackClutch().getFinish2()));
    }

    @Override
    public void loose(Player player) {
        super.loose(player);
    }

    @Override
    public void stop(Player player) {
        super.stop(player);
        bedwarsPractice.getKnockbackClutchTask().removeTask(this);
    }

    @Override
    public void start(Player player) {
        super.start(player);
        bedwarsPractice.getKnockbackClutchTask().addTask(this);
    }

    private static final double y = 0.4444;
    private static final Random random = new SecureRandom();
    public void tick() {
        if(System.currentTimeMillis() - lastKnock > bedwarsPractice.getConfigValue().KNOCKBACK_DELAY) {
            double rX = -0.35 + (0.35 + 0.35) * random.nextDouble();
            double rZ;
            if(random.nextBoolean()) {
                rZ = 0.28 + (0.34 - 0.28) * random.nextDouble();
            } else {
                rZ = (0.28 + (0.34 - 0.28) * random.nextDouble()) * -1;
            }
            player.setVelocity(new Vector(rX, y, rZ));
            lastKnock = System.currentTimeMillis();
        }
    }

    @Override
    public Location getSpawn() {
        if(getCheckPoint() == null || !isCheckPointEnabled())
            return bedwarsPractice.getSpawns().getKnockbackClutch().getSpawn();
        return getCheckPoint();
    }

    @Override
    public int hashCode() {
        return Objects.hash(player.getName(), player.getEntityId());
    }

    @Override
    public int compareTo(KnockbackClutch o) {
        return getPlayer().getName().compareTo(o.getPlayer().getName());
    }

    public static class KnockbackClutchTask extends BukkitRunnable {
        private final Set<KnockbackClutch> knockbackClutches = new HashSet<>();

        @Override
        public void run() {
            knockbackClutches.forEach(KnockbackClutch::tick);
        }

        public void addTask(KnockbackClutch knockbackClutch) {
            knockbackClutches.add(knockbackClutch);
        }

        public void removeTask(KnockbackClutch knockbackClutch) {
            knockbackClutches.remove(knockbackClutch);
        }
    }
}
