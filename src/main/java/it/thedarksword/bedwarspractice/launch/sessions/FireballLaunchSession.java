package it.thedarksword.bedwarspractice.launch.sessions;

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
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFireball;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.Set;

public class FireballLaunchSession extends LaunchSession {

    public FireballLaunchSession(BedwarsPractice bedwarsPractice, Player player) {
        super(SessionType.LAUNCH, bedwarsPractice, player, Material.FIREBALL);
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
                    material != bedwarsPractice.getConfigValue().LAUNCH_SETTINGS_MATERIAL &&
                    material != Material.FIREBALL) return packet;
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

        Location location = player.getEyeLocation();
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        WorldServer worldServer = ((CraftWorld)player.getWorld()).getHandle();

        player.sendBlockChange(fakeBlock.toBukkitLocation(), 0, (byte) 0);

        EntityLargeFireball largeFireball = new EntityLargeFireball(worldServer);
        Vector direction = location.getDirection().multiply(10);
        largeFireball.setDirection(direction.getX(), direction.getY(), direction.getZ());
        largeFireball.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        Vector velocity = location.getDirection().multiply(1.5);
        largeFireball.motX = velocity.getX();
        largeFireball.motY = velocity.getY();
        largeFireball.motZ = velocity.getZ();
        largeFireball.isIncendiary = false;
        largeFireball.bukkitYield = 2.5f;

        /*bedwarsPractice.getServer().getScheduler().runTask(bedwarsPractice, () -> {
            Fireball fireball = player.launchProjectile(Fireball.class);

            fireball.setIsIncendiary(true);
            fireball.setYield(2.5f);
            fireball.setVelocity(location.getDirection().multiply(1.5));
            EntityFireball entityFireball = ((CraftFireball) fireball).getHandle();
            System.out.println("MotX: " + entityFireball.motX);
            System.out.println("MotY: " + entityFireball.motY);
            System.out.println("MotZ: " + entityFireball.motZ);
        });*/

        int fireBallId = largeFireball.getId();

        Location collisionLocation = player.getTargetBlock((Set<Material>) null, 20).getLocation();
        double distance = location.distance(collisionLocation);
        double time = (distance/velocity.length());
        /*System.out.println("Distance: " + distance); //m
        System.out.println("Time to collision: " + time + " - " + Math.floor(time));*/

        bedwarsPractice.getServer().getScheduler().runTaskLater(bedwarsPractice, () -> {
            Vector vector = player.getLocation().toVector().subtract(collisionLocation.toVector());
            vector.normalize();
            vector.setY(1);
            vector.multiply(1.25f);

            player.setVelocity(new Vector());

            bedwarsPractice.getServer().getScheduler().runTaskLater(bedwarsPractice, () -> player.setVelocity(vector), 1);

            PacketPlayOutNamedSoundEffect soundEffect = new PacketPlayOutNamedSoundEffect(CraftSound.getSound(Sound.EXPLODE),
                    collisionLocation.getX(), collisionLocation.getY(), collisionLocation.getZ(), 1, 1);
            PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(fireBallId);

            PlayerConnection playerConnection = ((CraftPlayer)player).getHandle().playerConnection;
            playerConnection.sendPacket(destroy);
            playerConnection.sendPacket(soundEffect);
        }, (long) (4 * Math.floor(time)));

        PacketPlayOutSpawnEntity spawnPacket = new PacketPlayOutSpawnEntity(largeFireball, 63, 1);
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
}
