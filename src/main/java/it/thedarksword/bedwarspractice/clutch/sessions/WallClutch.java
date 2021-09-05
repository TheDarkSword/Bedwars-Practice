package it.thedarksword.bedwarspractice.clutch.sessions;

import io.netty.util.internal.ConcurrentSet;
import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.SessionType;
import it.thedarksword.bedwarspractice.abstraction.sessions.clutch.ClutchSession;
import it.thedarksword.bedwarspractice.clipboards.Cuboid;
import it.thedarksword.bedwarspractice.utils.Title;
import it.thedarksword.bedwarspractice.utils.formatter.Format;
import it.thedarksword.bedwarspractice.utils.location.FakeBlock;
import lombok.SneakyThrows;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftSound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WallClutch extends ClutchSession implements Comparable<WallClutch> {

    protected final Map<FakeBlock, Long> fakeBlocks = new ConcurrentHashMap<>();

    public WallClutch(BedwarsPractice bedwarsPractice, Player player) {
        super(SessionType.WALL_CLUTCH, bedwarsPractice, player);
        setFinishArea(new Cuboid(bedwarsPractice.getSpawns().getWallClutch().getFinish1(),
                bedwarsPractice.getSpawns().getWallClutch().getFinish2()));
    }

    @SuppressWarnings("deprecation")
    @Override
    public PacketPlayInBlockPlace handlePlace(Plugin plugin, Player player, PacketPlayInBlockPlace packet) {
        BlockPosition blockPosition = packet.a();
        org.bukkit.inventory.ItemStack hand = player.getItemInHand();

        if (blockPosition.getX() == -1 && blockPosition.getZ() == -1) {
            if (hand.getType() == bedwarsPractice.getConfigValue().CHECKPOINT_ENABLED_MATERIAL) {
                if(hand.getData().getData() == bedwarsPractice.getConfigValue().CHECKPOINT_ENABLED_COLOR.getDyeData()) {
                    player.setItemInHand(bedwarsPractice.getConstantObjects().getCheckpointDisabled());
                    setCheckPointEnabled(false);
                    setCheckPoint(null);
                } else {
                    player.setItemInHand(bedwarsPractice.getConstantObjects().getCheckpointEnabled());
                    setCheckPointEnabled(true);
                }
            } else if (hand.getType() == bedwarsPractice.getConfigValue().MODE_MATERIAL) {
                bedwarsPractice.getInventories().getModeInventory().open(player);
            }
            return null;
        }

        FakeBlock fakeBlock;
        try {
            Material material = Material.getMaterial(Item.getId(packet.getItemStack().getItem()));
            if(!material.isSolid() &&
                    material != bedwarsPractice.getConfigValue().MODE_MATERIAL &&
                    material != bedwarsPractice.getConfigValue().CHECKPOINT_DISABLED_MATERIAL &&
                    material != bedwarsPractice.getConfigValue().CHECKPOINT_ENABLED_MATERIAL) return packet;
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

        Material fromBlock = player.getWorld().getBlockAt(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ()).getType();
        if((fromBlock != bedwarsPractice.getConfigValue().WALL_BLOCK && fromBlock != Material.AIR) ||
                fakeBlock.getX() <= getSpawn().getX() || fakeBlock.getX() >= getFinishArea().lastX()) {
            player.sendMessage(bedwarsPractice.getConfigValue().INVALID_PLACE);
            player.sendBlockChange(fakeBlock.toBukkitLocation(), 0, (byte) 0);
            player.setItemInHand(hand);
            return null;
        }

        if (!isRunning()) {
            start(player);
        }

        setFakeBlock(fakeBlock);

        if (hand.getAmount() == 1) {
            player.setItemInHand(null);
        } else {
            hand.setAmount(hand.getAmount() - 1);
        }

        return null;
    }

    @Override
    public void setFakeBlock(FakeBlock fakeBlock) {
        fakeBlocks.put(fakeBlock, System.currentTimeMillis());
    }

    @SuppressWarnings("deprecation")
    @SneakyThrows
    @Override
    public void win(Player player) {
        checkPoint = null;
        player.teleport(getSpawn());
        setRunning(false);

        float time = (System.currentTimeMillis() - getSessionStart())/1000f;
        if(time < getBestTime()) {
            bedwarsPractice.getMySQLManager().saveRecord(player.getName(), getClass().getSimpleName(), time);
            setBestTime(time);
        }

        String timeFormatted = Format.decimal3(time);
        Title.buildAndSend(player, bedwarsPractice.getConfigValue().WC_WIN_TITLE.replace("{time}", timeFormatted),
                bedwarsPractice.getConfigValue().WC_fadeIn,
                bedwarsPractice.getConfigValue().WC_duration, bedwarsPractice.getConfigValue().WC_fadeOut);
        player.sendMessage(bedwarsPractice.getConfigValue().WC_WIN_MESSAGE.replace("{time}", timeFormatted));

        fakeBlocks.forEach((fakeBlock, placed) -> player.sendBlockChange(fakeBlock.toBukkitLocation(), 0, (byte) 0));
        fakeBlocks.clear();

        player.getInventory().setItem(0, bedwarsPractice.getConstantObjects().getBlock());
        player.getInventory().setItem(2, bedwarsPractice.getConstantObjects().getBlock());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void loose(Player player) {
        player.teleport(getSpawn());
        if(checkPoint == null) {
            setRunning(false);
            player.sendMessage(bedwarsPractice.getConfigValue().WC_LOOSE_MESSAGE);
        }

        fakeBlocks.forEach((fakeBlock, placed) -> player.sendBlockChange(fakeBlock.toBukkitLocation(), 0, (byte) 0));
        fakeBlocks.clear();

        player.getInventory().setItem(0, bedwarsPractice.getConstantObjects().getBlock());
        player.getInventory().setItem(2, bedwarsPractice.getConstantObjects().getBlock());

        PacketPlayOutNamedSoundEffect soundEffect = new PacketPlayOutNamedSoundEffect(CraftSound.getSound(Sound.STEP_WOOL),
                player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 1, 1);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(soundEffect);
    }

    @Override
    public void stop(Player player) {
        super.stop(player);
        bedwarsPractice.getWallClutchTask().removeTask(this);
    }

    @Override
    public void start(Player player) {
        super.start(player);
        bedwarsPractice.getWallClutchTask().addTask(this);
    }

    @SuppressWarnings("deprecation")
    public void tick() {
        fakeBlocks.entrySet().removeIf(entry -> {
            if(System.currentTimeMillis() - entry.getValue() > 500) {
                player.sendBlockChange(entry.getKey().toBukkitLocation(), 0, (byte) 0);
                return true;
            }
            return false;
        });
    }

    @Override
    public Location getSpawn() {
        if(getCheckPoint() == null || !isCheckPointEnabled())
            return bedwarsPractice.getSpawns().getWallClutch().getSpawn();
        return getCheckPoint();
    }

    @Override
    public int hashCode() {
        return Objects.hash(player.getName(), player.getEntityId());
    }

    @Override
    public int compareTo(WallClutch o) {
        return getPlayer().getName().compareTo(o.getPlayer().getName());
    }

    public static class WallClutchTask extends BukkitRunnable {
        private final Set<WallClutch> knockbackClutches = new ConcurrentSet<>();

        @Override
        public void run() {
            knockbackClutches.forEach(WallClutch::tick);
        }

        public void addTask(WallClutch wallClutch) {
            knockbackClutches.add(wallClutch);
        }

        public void removeTask(WallClutch wallClutch) {
            knockbackClutches.remove(wallClutch);
        }
    }
}
