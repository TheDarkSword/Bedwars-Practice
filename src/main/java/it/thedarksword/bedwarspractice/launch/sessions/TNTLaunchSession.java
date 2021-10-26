package it.thedarksword.bedwarspractice.launch.sessions;

import io.netty.util.internal.ConcurrentSet;
import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.SessionType;
import it.thedarksword.bedwarspractice.abstraction.sessions.launch.LaunchSession;
import it.thedarksword.bedwarspractice.utils.location.FakeBlock;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftSound;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class TNTLaunchSession extends LaunchSession implements Comparable<TNTLaunchSession> {

    private int tntId;
    private long lastTNT;
    private Location tntLocation;

    public TNTLaunchSession(BedwarsPractice bedwarsPractice, Player player) {
        super(SessionType.LAUNCH, bedwarsPractice, player, Material.TNT);
    }

    private boolean tick() {
        if(System.currentTimeMillis() - lastTNT >= 2250) {
            Vector vector = player.getLocation().add(0.0, 1.0, 0.0).toVector().subtract(tntLocation.toVector());
            double l = vector.length();
            vector.normalize();
            vector.multiply(4.0 / l);

            player.setVelocity(vector.divide(new Vector(1, 7, 1)));

            PacketPlayOutNamedSoundEffect soundEffect = new PacketPlayOutNamedSoundEffect(CraftSound.getSound(Sound.EXPLODE),
                    tntLocation.getX(), tntLocation.getY(), tntLocation.getZ(), 1, 1);
            PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(tntId);

            PlayerConnection playerConnection = ((CraftPlayer)player).getHandle().playerConnection;
            playerConnection.sendPacket(destroy);
            playerConnection.sendPacket(soundEffect);

            lastTNT = Long.MAX_VALUE;
            return true;
        }
        return false;
    }

    @Override
    public void start(Player player) {
        super.start(player);
        bedwarsPractice.getTntLaunchTask().addTask(this);
    }

    @Override
    public void stop(Player player) {
        super.stop(player);
        bedwarsPractice.getTntLaunchTask().removeTask(this);
    }

    @Override
    public void loose(Player player) {
        super.loose(player);
        if(tntId != 0) ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(tntId));
    }

    @SuppressWarnings("deprecation")
    @Override
    public PacketPlayInBlockPlace handlePlace(Plugin plugin, Player player, PacketPlayInBlockPlace packet) {
        BlockPosition blockPosition = packet.a();
        org.bukkit.inventory.ItemStack hand = player.getItemInHand();

        if (blockPosition.getX() == -1 && blockPosition.getZ() == -1) {
            if (hand.getType() == bedwarsPractice.getConfigValue().MODE_MATERIAL) {
                bedwarsPractice.getInventories().getModeInventory().open(player);
            } else if (hand.getType() == bedwarsPractice.getConfigValue().LAUNCH_SETTINGS_MATERIAL) {
                bedwarsPractice.getInventories().getLaunchSettings().open(player);
            } else if (hand.getType() == Material.BED) {
                bedwarsPractice.getDictation().getPlayerManager().sendToServer(player.getName(), "BWLobby");
            }
            return null;
        }

        FakeBlock fakeBlock;
        try {
            Material material = Material.getMaterial(Item.getId(packet.getItemStack().getItem()));
            if(!material.isSolid() &&
                    material != bedwarsPractice.getConfigValue().MODE_MATERIAL &&
                    material != bedwarsPractice.getConfigValue().LAUNCH_SETTINGS_MATERIAL) return packet;
            fakeBlock = new FakeBlock(Material.getMaterial(Item.getId(packet.getItemStack().getItem())),
                    player.getWorld(), blockPosition.getX(), blockPosition.getY(), blockPosition.getZ(), packet.getFace());
        } catch (NullPointerException e) {
            return null;
        }

        if(hand.getType() == bedwarsPractice.getConfigValue().LAUNCH_SETTINGS_MATERIAL) {
            bedwarsPractice.getInventories().getLaunchSettings().open(player);
            player.sendBlockChange(fakeBlock.toBukkitLocation(), 0, (byte) 0);
            return null;
        } else if(hand.getType() == bedwarsPractice.getConfigValue().MODE_MATERIAL) {
            bedwarsPractice.getInventories().getModeInventory().open(player);
            player.sendBlockChange(fakeBlock.toBukkitLocation(), 0, (byte) 0);
            return null;
        }

        //Material fromBlock = player.getWorld().getBlockAt(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ()).getType();
        if(/*(fromBlock != bedwarsPractice.getConfigValue().KNOCKBACK_START && fromBlock != Material.AIR) ||*/
                fakeBlock.getX() <= getSpawn().getX()) {
            player.sendMessage(bedwarsPractice.getConfigValue().INVALID_PLACE);
            player.sendBlockChange(fakeBlock.toBukkitLocation(), 0, (byte) 0);
            player.setItemInHand(hand);
            return null;
        }

        if (!isRunning()) {
            start(player);
        }

        Location location = fakeBlock.toBukkitLocation();
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        WorldServer worldServer = ((CraftWorld)player.getWorld()).getHandle();

        player.sendBlockChange(location, 0, (byte) 0);

        /*Class<?> EntityTNTPrimed = Class.forName("net.minecraft.server.v1_8_R3.EntityTNTPrimed");
        Constructor<?> constructor = EntityTNTPrimed.getConstructor(Location.class, World.class, double.class, double.class, double.class, EntityLiving.class);
        Object tntPrimed = constructor.newInstance(location, ((CraftWorld)player.getWorld()).getHandle(), location.getX(), location.getY(), location.getZ(), entityPlayer);*/
        /*EntityTNTPrimed tntPrimed = new EntityTNTPrimed(location, ((CraftWorld)player.getWorld()).getHandle(),
                location.getX(), location.getY(), location.getZ(), entityPlayer);*/
        EntityTNTPrimed tntPrimed = new EntityTNTPrimed(worldServer);
        tntPrimed.setPosition(location.getX(), location.getY(), location.getZ());
        tntPrimed.setPositionRotation(location.getX() + 0.5, location.getY(), location.getZ() + 0.5, 0, 0);
        tntPrimed.fuseTicks = 45;
        tntPrimed.yield = 3;

        //NBTTagCompound compound = new NBTTagCompound();
        //compound.setByte("Fuse", (byte) 45);
        tntId = tntPrimed.getId();
        lastTNT = System.currentTimeMillis();
        tntLocation = location;

        /*bedwarsPractice.getServer().getScheduler().runTask(bedwarsPractice, () -> {
            TNTPrimed primed = player.getWorld().spawn(location, TNTPrimed.class);
            primed.setFuseTicks(45);
            primed.setYield(4);
            System.out.println("Entity ID: " + primed.getEntityId());
        });*/


        PacketPlayOutSpawnEntity spawnPacket = new PacketPlayOutSpawnEntity(tntPrimed, 50);
        //PacketPlayOutUpdateEntityNBT nbtPacket = new PacketPlayOutUpdateEntityNBT(tntPrimed.getId(), compound);
        entityPlayer.playerConnection.sendPacket(spawnPacket);
        //entityPlayer.playerConnection.sendPacket(nbtPacket);
        /*worldServer.createExplosion(tntPrimed, tntPrimed.locX, tntPrimed.locY + (double)(tntPrimed.length / 2.0F),
                tntPrimed.locZ, tntPrimed.yield, false, true);*/
        //getPlaced().increment();

        if (hand.getAmount() == 1) {
            player.setItemInHand(null);
        } else {
            hand.setAmount(hand.getAmount() - 1);
        }

        /*PacketPlayOutNamedSoundEffect soundEffect = new PacketPlayOutNamedSoundEffect(CraftSound.getSound(Sound.STEP_WOOL),
                player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 1, 1);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(soundEffect);*/

        return null;
    }

    @Override
    public int hashCode() {
        return player.getEntityId();
    }

    @Override
    public int compareTo(@NotNull TNTLaunchSession o) {
        return getPlayer().getName().compareTo(o.getPlayer().getName());
    }

    public static class TNTLaunchTask extends BukkitRunnable {
        private final Set<TNTLaunchSession> tntLaunchSessions = new ConcurrentSet<>();

        @Override
        public void run() {
            tntLaunchSessions.removeIf(TNTLaunchSession::tick);
        }

        public void addTask(TNTLaunchSession tntLaunchSession) {
            tntLaunchSessions.add(tntLaunchSession);
        }

        public void removeTask(TNTLaunchSession tntLaunchSession) {
            tntLaunchSessions.remove(tntLaunchSession);
        }
    }
}
