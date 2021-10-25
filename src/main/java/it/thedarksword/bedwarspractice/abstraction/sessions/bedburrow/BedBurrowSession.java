package it.thedarksword.bedwarspractice.abstraction.sessions.bedburrow;

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
import org.bukkit.plugin.Plugin;

import java.util.List;

@Setter(value = AccessLevel.PROTECTED)
public class BedBurrowSession extends Session {

    @Getter
    protected final BedwarsPractice bedwarsPractice;
    @Getter protected final Player player;

    @Getter private long sessionStart;
    @Getter private boolean running;
    @Getter private float bestTime;

    public BedBurrowSession(BedwarsPractice bedwarsPractice, Player player, ConstantObjects.PlaceableBlock placeableBlock) {
        super(SessionType.BED_BURROW);
        this.bedwarsPractice = bedwarsPractice;
        this.player = player;

        if(placeableBlock == null){
            setPlaceableBlock(ConstantObjects.PlaceableBlock.WHITE_WOOL, player);
        } else {
            setPlaceableBlock(placeableBlock, player);
        }
    }

    public void tick() {

    }

    @SneakyThrows
    @Override
    public void load(Player player) {
        bestTime = bedwarsPractice.getMySQLManager().getBestTime(player.getName(), getClass().getSimpleName());
    }

    @Override
    public void init(Player player) {
        player.getInventory().setItem(0, bedwarsPractice.getConstantObjects().getBedBurrow().getSword());
        player.getInventory().setItem(1, bedwarsPractice.getConstantObjects().getBedBurrow().getPickaxe());
        player.getInventory().setItem(2, getPlaceableBlock().get());
        player.getInventory().setItem(3, bedwarsPractice.getConstantObjects().getBedBurrow().getAxe());
        player.getInventory().setItem(4, bedwarsPractice.getConstantObjects().getBedBurrow().getShears());

        player.getInventory().setItem(6, bedwarsPractice.getConstantObjects().getBedBurrow().getAnvil());
        player.getInventory().setItem(7, bedwarsPractice.getConstantObjects().getLeave());
        player.getInventory().setItem(8, bedwarsPractice.getConstantObjects().getMode());
    }

    @Override
    public void pasteSchematic(Player player, Schematic schematic, Location location) {

    }

    @Override
    public Location getSpawn() {
        return bedwarsPractice.getSpawns().getBedBurrow().getSpawn();
    }

    @Override
    public PacketPlayInBlockDig handleBreak(Plugin plugin, Player player, PacketPlayInBlockDig packet) {
        //TODO Handle Destroy Block
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
            } else if (hand.getType() == Material.BED) {
                bedwarsPractice.getDictation().getPlayerManager().sendToServer(player.getName(), "BWLobby");
            }
            return null;
        }

        FakeBlock fakeBlock;
        try {
            Material material = Material.getMaterial(Item.getId(packet.getItemStack().getItem()));
            if(!material.isSolid() &&
                    material != bedwarsPractice.getConfigValue().MODE_MATERIAL) return packet;
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

        setFakeBlock(fakeBlock);

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
        player.teleport(getSpawn());
        setRunning(false);

        float time = (System.currentTimeMillis() - getSessionStart())/1000f;
        if(time > 2 && time < bestTime) {
            bedwarsPractice.getMySQLManager().saveRecord(player.getName(), getClass().getSimpleName(), time);
            bestTime = time;
        }

        String timeFormatted = Format.decimal3(time);
        Title.buildAndSend(player, bedwarsPractice.getConfigValue().BEDBURROW_WIN_TITLE.replace("{time}", timeFormatted),
                bedwarsPractice.getConfigValue().BEDBURROW_fadeIn,
                bedwarsPractice.getConfigValue().BEDBURROW_duration, bedwarsPractice.getConfigValue().BEDBURROW_fadeOut);
        player.sendMessage(bedwarsPractice.getConfigValue().BEDBURROW_WIN_MESSAGE.replace("{time}", timeFormatted));

        player.getInventory().setItem(2, getPlaceableBlock().get());
    }

    @Override
    public void loose(Player player) {
        player.teleport(getSpawn());
        setRunning(false);
        player.sendMessage(bedwarsPractice.getConfigValue().BEDBURROW_LOOSE_MESSAGE);

        player.getInventory().setItem(2, getPlaceableBlock().get());
        PacketPlayOutNamedSoundEffect soundEffect = new PacketPlayOutNamedSoundEffect(CraftSound.getSound(Sound.ENDERMAN_TELEPORT),
                player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 1, 1);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(soundEffect);
    }

    @Override
    protected void updatePlaceableBlock(ConstantObjects.PlaceableBlock placeableBlock, Player player) {
        player.getInventory().setItem(2, getPlaceableBlock().get());
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
