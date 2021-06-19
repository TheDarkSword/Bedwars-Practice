package it.thedarksword.bedwarspractice.clutch.sessions;

import io.netty.util.internal.ConcurrentSet;
import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.SessionType;
import it.thedarksword.bedwarspractice.abstraction.sessions.clutch.ClutchSession;
import it.thedarksword.bedwarspractice.abstraction.sessions.clutch.KnockBackClutchConfiguration;
import it.thedarksword.bedwarspractice.clipboards.Cuboid;
import it.thedarksword.bedwarspractice.utils.formatter.Format;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class KnockbackClutch extends ClutchSession implements Comparable<KnockbackClutch> {

    private long lastKnock;

    private final KnockBackClutchConfiguration configuration;

    public KnockbackClutch(BedwarsPractice bedwarsPractice, Player player, KnockBackClutchConfiguration configuration) {
        super(SessionType.KB_CLUTCH, bedwarsPractice, player);
        this.configuration = configuration;
        setFinishArea(new Cuboid(bedwarsPractice.getSpawns().getKnockbackClutch().getFinish1(),
                bedwarsPractice.getSpawns().getKnockbackClutch().getFinish2()));
    }

    @Override
    public void init(Player player) {
        super.init(player);
        player.getInventory().setItem(6, bedwarsPractice.getConstantObjects().getKbcDifficulty());
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

    private static final double y = 0.48;
    private static final Random random = new SecureRandom();
    public void tick() {
        if(System.currentTimeMillis() - lastKnock > bedwarsPractice.getConfigValue().KNOCKBACK_DELAY) {
            //double rX = -0.35 + (0.35 + 0.35) * random.nextDouble();
            double rX, rZ;
            if(random.nextBoolean()) {
                rX = configuration.getXMin() + (configuration.getXMax() - configuration.getXMin()) * random.nextDouble();
            } else {
                rX = (configuration.getXMin() + (configuration.getXMax() - configuration.getXMin()) * random.nextDouble()) * -1;
            }
            if(random.nextBoolean()) {
                rZ = configuration.getZMin() + (configuration.getZMax() - configuration.getZMin()) * random.nextDouble();
            } else {
                rZ = (configuration.getZMin() + (configuration.getZMax() - configuration.getZMin()) * random.nextDouble()) * -1;
            }
            /*if(random.nextBoolean()) {
                rX = 0.1 + 0.1 * random.nextDouble();
            } else {
                rX = (0.1 + 0.1 * random.nextDouble()) * -1;
            }
            if(random.nextBoolean()) {
                rZ = 0.3 + 0.1 * random.nextDouble();
            } else {
                rZ = (0.3 + 0.1 * random.nextDouble()) * -1;
            }*/
            player.setVelocity(new Vector(rX, y, rZ));
            player.damage(0.0000000001, null);
            /*double d0 = (player.getLocation().getX() + rX) - player.getLocation().getX();

            double d1;

            for (d1 = (player.getLocation().getZ() + rZ) - player.getLocation().getZ(); d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D) {
                d0 = (Math.random() - Math.random()) * 0.01D;
            }

            //this.aw = (float) (MathHelper.b(d1, d0) * 180.0D / 3.1415927410125732D - (double) this.yaw);
            //this.a(entity, f, d0, d1);
            float f1 = MathHelper.sqrt(d0 * d0 + d1 * d1);
            float f2 = 0.4F;
            EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
            entityPlayer.motX /= 2.0D;
            entityPlayer.motY /= 2.0D;
            entityPlayer.motZ /= 2.0D;
            entityPlayer.motX -= d0 / (double) f1 * (double) f2;
            entityPlayer.motY += f2;
            entityPlayer.motZ -= d1 / (double) f1 * (double) f2;
            if (entityPlayer.motY > 0.4000000059604645D) {
                entityPlayer.motY = 0.4000000059604645D;
            }
            entityPlayer.velocityChanged = true;*/
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
        private final Set<KnockbackClutch> knockbackClutches = new ConcurrentSet<>();

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

    @Override
    public void sidebarTemplate(List<String> list) {
        list.add(ChatColor.GRAY + Format.now());
        if(!isRunning()) {
            list.add("     ");
            list.add("§bSegui il percorso");
        }
        list.add(" ");
        if(isRunning())
            list.add("Tempo: §b" + Format.decimal1((System.currentTimeMillis() - getSessionStart())/1000f));
        else
            list.add("Tempo: §b0");
        list.add("  ");
        list.add("Miglior Tempo: §b" + (getBestTime() == Float.MAX_VALUE ? "Nessuno" : getBestTime()));
        list.add("   ");
        list.add("Modalità: §7" + getType().getName());
        list.add("    ");
        list.add("Difficoltà: §7" + configuration.getName());
        list.add("      ");
        list.add(ChatColor.YELLOW + "play.coralmc.it");
    }
}
