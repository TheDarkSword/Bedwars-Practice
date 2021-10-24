package it.thedarksword.bedwarspractice.abstraction.sessions.launch;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.Session;
import it.thedarksword.bedwarspractice.abstraction.sessions.SessionType;
import it.thedarksword.bedwarspractice.clipboards.Schematic;
import it.thedarksword.bedwarspractice.manager.ConstantObjects;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;

@Setter(value = AccessLevel.PROTECTED)
public class LaunchSession extends Session {

    @Getter
    protected final BedwarsPractice bedwarsPractice;
    @Getter protected final Player player;

    @Getter private long sessionStart;
    @Getter private boolean running;
    @Getter private double bestDistance;

    @Getter private ItemStack launchItem;

    public LaunchSession(SessionType sessionType, BedwarsPractice bedwarsPractice, Player player, Material launchMaterial) {
        super(sessionType);
        this.bedwarsPractice = bedwarsPractice;
        this.player = player;
        this.launchItem = new ItemStack(launchMaterial, 1);
    }

    @SneakyThrows
    @Override
    public void load(Player player) {
        bestDistance = bedwarsPractice.getMySQLManager().getBestTime(player.getName(), getClass().getSimpleName());

        player.getInventory().setItem(0, launchItem);
    }

    @Override
    public void init(Player player) {
        //player.getInventory().setItem(0, launchItem);


        player.getInventory().setItem(6, bedwarsPractice.getConstantObjects().getLeave());
        player.getInventory().setItem(7, bedwarsPractice.getConstantObjects().getLaunchItemSettings());
        player.getInventory().setItem(8, bedwarsPractice.getConstantObjects().getMode());
    }

    @Override
    public void pasteSchematic(Player player, Schematic schematic, Location location) {

    }

    @Override
    public Location getSpawn() {
        return bedwarsPractice.getSpawns().getLaunch().getSpawn();
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

        setFakeBlock(fakeBlock);
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

    @SuppressWarnings("deprecation")
    @Override
    public void stop(Player player) {
        setSessionStart(-1L);
        setRunning(false);

        fakeBlocks.forEach(fakeBlock -> player.sendBlockChange(fakeBlock.toBukkitLocation(), 0, (byte) 0));
        fakeBlocks.clear();
    }

    @Override
    public void start(Player player) {
        setSessionStart(System.currentTimeMillis());
        setRunning(true);
    }

    @SneakyThrows
    @Override
    public void win(Player player) {
        int distance = player.getLocation().getBlockX() - (bedwarsPractice.getConfigValue().LAUNCH_START_OFFSET + getSpawn().getBlockX());

        player.teleport(getSpawn());
        setRunning(false);
        if(distance > bestDistance) {
            bedwarsPractice.getMySQLManager().saveRecord(player.getName(), getClass().getSimpleName(), distance);
            bestDistance = distance;
        }

        Title.buildAndSend(player, bedwarsPractice.getConfigValue().LAUNCH_WIN_TITLE.replace("{distance}", String.valueOf(distance)),
                bedwarsPractice.getConfigValue().LAUNCH_fadeIn,
                bedwarsPractice.getConfigValue().LAUNCH_duration, bedwarsPractice.getConfigValue().LAUNCH_fadeOut);
        player.sendMessage(bedwarsPractice.getConfigValue().LAUNCH_WIN_MESSAGE.replace("{distance}", String.valueOf(distance)));

        player.getInventory().setItem(0, launchItem);
    }

    @Override
    public void loose(Player player) {
        player.teleport(getSpawn());

        setRunning(false);
        player.sendMessage(bedwarsPractice.getConfigValue().LAUNCH_LOOSE_MESSAGE);

        player.getInventory().setItem(0, launchItem);
        PacketPlayOutNamedSoundEffect soundEffect = new PacketPlayOutNamedSoundEffect(CraftSound.getSound(Sound.ENDERMAN_TELEPORT),
                player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 1, 1);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(soundEffect);
    }

    @Override
    protected void updatePlaceableBlock(ConstantObjects.PlaceableBlock placeableBlock, Player player) {

    }

    @Override
    public void sidebarTemplate(List<String> list) {
        list.add(ChatColor.GRAY + Format.now());
        if(!isRunning()) {
            list.add("     ");
            list.add("§bLanciati");
        }
        list.add("  ");
        list.add("Miglior Distanza: §b" + (getBestDistance() == Float.MAX_VALUE ? "Nessuna" : getBestDistance()));
        list.add("   ");
        list.add("Modalità: §7" + getType().getName());
        list.add("    ");
        list.add(ChatColor.YELLOW + "play.coralmc.it");
    }
}
