package it.thedarksword.bedwarspractice.abstraction.sessions.clutch;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.Session;
import it.thedarksword.bedwarspractice.abstraction.sessions.SessionType;
import it.thedarksword.bedwarspractice.clipboards.Schematic;
import it.thedarksword.bedwarspractice.utils.Title;
import it.thedarksword.bedwarspractice.utils.formatter.Format;
import it.thedarksword.bedwarspractice.utils.location.FakeBlock;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftSound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

@Setter(value = AccessLevel.PROTECTED)
public abstract class ClutchSession extends Session {

    @Getter protected final BedwarsPractice bedwarsPractice;
    @Getter protected final Player player;

    @Getter private long sessionStart;
    @Getter private boolean running;
    @Getter private float bestTime;

    @Setter @Getter protected boolean checkPointEnabled;
    @Setter @Getter protected Location checkPoint;

    public ClutchSession(SessionType sessionType, BedwarsPractice bedwarsPractice, Player player) {
        super(sessionType);
        this.bedwarsPractice = bedwarsPractice;
        this.player = player;
    }

    public abstract void tick();

    @SneakyThrows
    @Override
    public void load(Player player) {
        bestTime = bedwarsPractice.getMySQLManager().getBestTime(player.getName(), getClass().getSimpleName());
    }

    @Override
    public void init(Player player) {
        player.getInventory().setItem(0, bedwarsPractice.getConstantObjects().getBlock());
        player.getInventory().setItem(2, bedwarsPractice.getConstantObjects().getBlock());

        player.getInventory().setItem(7, bedwarsPractice.getConstantObjects().getCheckpointDisabled());
        player.getInventory().setItem(8, bedwarsPractice.getConstantObjects().getMode());
    }

    @Override
    public void pasteSchematic(Player player, Schematic schematic, Location location) {

    }

    @Override
    public PacketPlayInBlockDig handleBreak(Plugin plugin, Player player, PacketPlayInBlockDig packet) {
        return packet;
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
            } else if (hand.getType() == bedwarsPractice.getConfigValue().KBC_DIFFICULTY_MATERIAL) {
                bedwarsPractice.getInventories().getKnockBackClutchDifficultyInventory().open(player);
            }
            return null;
        }

        FakeBlock fakeBlock;
        try {
            Material material = Material.getMaterial(Item.getId(packet.getItemStack().getItem()));
            if(!material.isSolid() &&
                    material != bedwarsPractice.getConfigValue().MODE_MATERIAL &&
                    material != bedwarsPractice.getConfigValue().KBC_DIFFICULTY_MATERIAL &&
                    material != bedwarsPractice.getConfigValue().CHECKPOINT_DISABLED_MATERIAL &&
                    material != bedwarsPractice.getConfigValue().CHECKPOINT_ENABLED_MATERIAL) return packet;
            fakeBlock = new FakeBlock(Material.getMaterial(Item.getId(packet.getItemStack().getItem())),
                    player.getWorld(), blockPosition.getX(), blockPosition.getY(), blockPosition.getZ(), packet.getFace());
        } catch (NullPointerException e) {
            return null;
        }

        if (hand.getType() == bedwarsPractice.getConfigValue().KBC_DIFFICULTY_MATERIAL) {
            bedwarsPractice.getInventories().getKnockBackClutchDifficultyInventory().open(player);
            player.sendBlockChange(fakeBlock.toBukkitLocation(), 0, (byte) 0);
            return null;
        } else if(hand.getType() == bedwarsPractice.getConfigValue().MODE_MATERIAL) {
            bedwarsPractice.getInventories().getModeInventory().open(player);
            player.sendBlockChange(fakeBlock.toBukkitLocation(), 0, (byte) 0);
            return null;
        }

        //Material fromBlock = player.getWorld().getBlockAt(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ()).getType();
        if(/*(fromBlock != bedwarsPractice.getConfigValue().KNOCKBACK_START && fromBlock != Material.AIR) ||*/
                fakeBlock.getX() <= getSpawn().getX() || fakeBlock.getX() >= getFinishArea().lastX() || !isRunning()) {
            player.sendMessage(bedwarsPractice.getConfigValue().INVALID_PLACE);
            player.sendBlockChange(fakeBlock.toBukkitLocation(), 0, (byte) 0);
            player.setItemInHand(hand);
            return null;
        }

        setFakeBlock(fakeBlock);
        //getPlaced().increment();

        if (hand.getAmount() == 1) {
            player.setItemInHand(null);
        } else {
            hand.setAmount(hand.getAmount() - 1);
        }

        PacketPlayOutNamedSoundEffect soundEffect = new PacketPlayOutNamedSoundEffect(CraftSound.getSound(Sound.STEP_WOOL),
                player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 1, 1);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(soundEffect);

        return null;
    }

    @Override
    public void stop(Player player) {
        setSessionStart(-1L);
        setRunning(false);
    }

    @Override
    public void start(Player player) {
        setSessionStart(System.currentTimeMillis());
        setRunning(true);
    }

    @SuppressWarnings("deprecation")
    @SneakyThrows
    @Override
    public void win(Player player) {
        checkPoint = null;
        player.teleport(getSpawn());
        setRunning(false);

        float time = (System.currentTimeMillis() - getSessionStart())/1000f;
        if(time > 2000 && time < bestTime) {
            bedwarsPractice.getMySQLManager().saveRecord(player.getName(), getClass().getSimpleName(), time);
            bestTime = time;
        }

        String timeFormatted = Format.decimal3(time);
        Title.buildAndSend(player, bedwarsPractice.getConfigValue().KBC_WIN_TITLE.replace("{time}", timeFormatted),
                bedwarsPractice.getConfigValue().KBC_fadeIn,
                bedwarsPractice.getConfigValue().KBC_duration, bedwarsPractice.getConfigValue().KBC_fadeOut);
        player.sendMessage(bedwarsPractice.getConfigValue().KBC_WIN_MESSAGE.replace("{time}", timeFormatted));

        fakeBlocks.forEach(fakeBlock -> player.sendBlockChange(fakeBlock.toBukkitLocation(), 0, (byte) 0));
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
            player.sendMessage(bedwarsPractice.getConfigValue().KBC_LOOSE_MESSAGE);
        }

        fakeBlocks.forEach(fakeBlock -> player.sendBlockChange(fakeBlock.toBukkitLocation(), 0, (byte) 0));
        fakeBlocks.clear();

        player.getInventory().setItem(0, bedwarsPractice.getConstantObjects().getBlock());
        player.getInventory().setItem(2, bedwarsPractice.getConstantObjects().getBlock());
        PacketPlayOutNamedSoundEffect soundEffect = new PacketPlayOutNamedSoundEffect(CraftSound.getSound(Sound.ENDERMAN_TELEPORT),
                player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 1, 1);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(soundEffect);
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
        list.add(ChatColor.YELLOW + "play.coralmc.it");
    }
}
