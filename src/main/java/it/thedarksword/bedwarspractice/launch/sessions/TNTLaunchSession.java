package it.thedarksword.bedwarspractice.launch.sessions;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.SessionType;
import it.thedarksword.bedwarspractice.abstraction.sessions.launch.LaunchSession;
import it.thedarksword.bedwarspractice.utils.location.FakeBlock;
import lombok.SneakyThrows;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;

public class TNTLaunchSession extends LaunchSession {

    public TNTLaunchSession(BedwarsPractice bedwarsPractice, Player player) {
        super(SessionType.LAUNCH, bedwarsPractice, player, Material.TNT);
    }

    @SneakyThrows
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

        if(hand.getType() == bedwarsPractice.getConfigValue().MODE_MATERIAL) {
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
        /*EntityTNTPrimed tntPrimed = new EntityTNTPrimed(worldServer);
        tntPrimed.setPosition(location.getX(), location.getY(), location.getZ());
        tntPrimed.setPositionRotation(location.getX() + 0.5, location.getY(), location.getZ() + 0.5, 0, 0);
        tntPrimed.fuseTicks = 80;
        worldServer.addEntity(tntPrimed);*/
        /*PacketPlayOutSpawnEntity spawnPacket = new PacketPlayOutSpawnEntity(tntPrimed, 1);
        entityPlayer.playerConnection.sendPacket(spawnPacket);*/
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
