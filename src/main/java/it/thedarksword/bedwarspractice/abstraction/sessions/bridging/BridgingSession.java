package it.thedarksword.bedwarspractice.abstraction.sessions.bridging;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.Session;
import it.thedarksword.bedwarspractice.abstraction.sessions.SessionType;
import it.thedarksword.bedwarspractice.clipboards.Cuboid;
import it.thedarksword.bedwarspractice.clipboards.InfiniteCuboid;
import it.thedarksword.bedwarspractice.clipboards.Schematic;
import it.thedarksword.bedwarspractice.inventories.BridgingSettingsInventory;
import it.thedarksword.bedwarspractice.utils.Title;
import it.thedarksword.bedwarspractice.utils.formatter.Format;
import it.thedarksword.bedwarspractice.utils.location.FakeBlock;
import it.thedarksword.bedwarspractice.utils.values.Numeric;
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
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;

@Setter(value = AccessLevel.PROTECTED)
public abstract class BridgingSession extends Session {

    //@Getter private final MutableValue<Integer> placed = Numeric.Integers.mutable();
    private final Map<Long, Double> movements = new HashMap<>();
    @Getter private final BridgingConfiguration configuration;
    @Getter private final BedwarsPractice bedwarsPractice;

    @Getter private long sessionStart;
    @Getter private int attempts;
    @Getter private boolean running;
    @Getter private double peekSpeed;
    @Getter private float bestTime;

    public BridgingSession(BridgingConfiguration configuration, BedwarsPractice bedwarsPractice) {
        this(configuration, bedwarsPractice, null);
    }

    public BridgingSession(BridgingConfiguration configuration, BedwarsPractice bedwarsPractice, BridgingSettingsInventory inventory) {
        super(SessionType.BRIDGING);
        this.configuration = configuration;
        this.bedwarsPractice = bedwarsPractice;
        if(inventory == null) setSettingsInventory(new BridgingSettingsInventory(this));
        else setSettingsInventory(inventory);

        if(configuration.getLength() == BridgingConfiguration.BridgeLength.INFINITE) {
            setFinishArea(new InfiniteCuboid());
            return;
        }
        Schematic schematic = bedwarsPractice.getSchematic();
        Location location = bedwarsPractice.getSpawns().getBridging().getSchematicSpawn().cloneLocation();
        int startX;
        if(configuration.getDirection() == BridgingConfiguration.BridgeDirection.FORWARD) {
            startX = configuration.getLength().getXForward();
        } else {
            startX = configuration.getLength().getXDiagonal();
        }
        location.add(startX, configuration.getHeight().getY(), configuration.getDirection().getZ());
        setFinishArea(new Cuboid(location.getX(),
                location.getY() + schematic.getHeight() - bedwarsPractice.getConfigValue().FINISH_Y, location.getZ(),
                location.getX() + schematic.getWidth(),
                location.getY() + schematic.getHeight(),
                location.getZ() + schematic.getLength()));
    }

    @SneakyThrows
    @Override
    public void load(Player player) {
        bestTime = bedwarsPractice.getMySQLManager().getBestTime(player.getName(), getClass().getSimpleName());
    }

    @Override
    public void init(Player player) {
        player.getInventory().setItem(0, bedwarsPractice.getConstantObjects().getBlock());
        player.getInventory().setItem(1, bedwarsPractice.getConstantObjects().getBlock());
        player.getInventory().setItem(2, bedwarsPractice.getConstantObjects().getBlock());
        player.getInventory().setItem(3, bedwarsPractice.getConstantObjects().getBlock());

        player.getInventory().setItem(7, bedwarsPractice.getConstantObjects().getSettings());
        player.getInventory().setItem(8, bedwarsPractice.getConstantObjects().getMode());

        bedwarsPractice.getServer().getScheduler().runTaskLater(bedwarsPractice, () ->
                player.openInventory(bedwarsPractice.getInventories().getBridgingSpawnInventory().getInventory()), 1);
    }

    public double getMovementSpeed() {
        movements.entrySet().removeIf(e -> e.getKey() + 500L < System.currentTimeMillis());
        double speed = Numeric.Doubles.sum(movements.values());
        if(speed > peekSpeed) peekSpeed = speed;
        return speed;
    }

    public void movement(Location from, Location to) {
        movements.put(System.currentTimeMillis(), from.distance(to)*2);
    }

    @SuppressWarnings("deprecation")
    @Override
    public PacketPlayInBlockDig handleBreak(Plugin plugin, Player player, PacketPlayInBlockDig packet) {
        //org.bukkit.inventory.PlayerInventory playerInventory = player.getInventory();

        if(packet.c() != PacketPlayInBlockDig.EnumPlayerDigType.STOP_DESTROY_BLOCK) return packet;

        Optional<FakeBlock> fakeBlockOptional = getFakeBlock(packet.a().getX(), packet.a().getY(), packet.a().getZ());

        FakeBlock fakeBlock;
        if(fakeBlockOptional.isPresent()) {
            fakeBlock = fakeBlockOptional.get();
        } else {
            Optional<FakeBlock> fakeBlockSchemOptional = getSchematicFakeBlock(packet.a().getX(), packet.a().getY(), packet.a().getZ());
            if(!fakeBlockSchemOptional.isPresent()) return packet;
            fakeBlock = fakeBlockSchemOptional.get();
        }

        player.sendBlockChange(fakeBlock.toBukkitLocation(), fakeBlock.getMaterial(), (byte) 0);

        Location playerLocation = player.getLocation();

        if(playerLocation.getBlockX() == fakeBlock.getX() &&
                playerLocation.getBlockZ() == fakeBlock.getZ() &&
                playerLocation.getBlockY() == fakeBlock.getY()+1) {
            player.teleport(playerLocation);
        }

        return packet;
    }

    @SuppressWarnings("deprecation")
    @Override
    public PacketPlayInBlockPlace handlePlace(Plugin plugin, Player player, PacketPlayInBlockPlace packet) {
        //org.bukkit.inventory.PlayerInventory playerInventory = player.getInventory();

        BlockPosition blockPosition = packet.a();
        org.bukkit.inventory.ItemStack hand = player.getItemInHand();

        if (blockPosition.getX() == -1 && blockPosition.getZ() == -1) {
            if (hand.getType() == bedwarsPractice.getConfigValue().SETTINGS_MATERIAL) {
                getSettingsInventory().open(player);
            } else if (hand.getType() == bedwarsPractice.getConfigValue().MODE_MATERIAL) {
                bedwarsPractice.getInventories().getModeInventory().open(player);
            }
            return null;
        }

        FakeBlock fakeBlock;
        try {
            Material material = Material.getMaterial(Item.getId(packet.getItemStack().getItem()));
            if(!material.isSolid()) return null;
            fakeBlock = new FakeBlock(Material.getMaterial(Item.getId(packet.getItemStack().getItem())),
                    player.getWorld(), blockPosition.getX(), blockPosition.getY(), blockPosition.getZ(), packet.getFace());
        } catch (NullPointerException e) {
            return null;
        }


        if(hand.getType() == bedwarsPractice.getConfigValue().SETTINGS_MATERIAL) {
            getSettingsInventory().open(player);
            player.sendBlockChange(fakeBlock.toBukkitLocation(), 0, (byte) 0);
            return null;
        } else if(hand.getType() == bedwarsPractice.getConfigValue().MODE_MATERIAL) {
            //bedwarsPractice.getInventories().getModeInventory().open(player);
            return null;
        }


        /*if(fakeBlock.getX() == player.getLocation().getBlockX() &&
                fakeBlock.getY() == player.getLocation().getBlockY() &&
                fakeBlock.getZ() == player.getLocation().getBlockZ()) return null;
        System.out.println(fakeBlock.getX() + " - " + player.getLocation().getX());
        System.out.println(fakeBlock.getY() + " - " + player.getLocation().getY());
        System.out.println(fakeBlock.getZ() + " - " + player.getLocation().getZ());*/

        /*bedwarsPractice.getServer().getScheduler().runTask(bedwarsPractice, () ->
                System.out.println(packet.getItemStack().getItem().interactWith(
                        packet.getItemStack(), ((CraftPlayer) player).getHandle(), ((CraftWorld)player.getWorld()).getHandle(),
                        blockPosition, EnumDirection.fromType1(packet.getFace()), packet.d(), packet.e(), packet.f())));*/
        Item item = packet.getItemStack().getItem();
        if(item instanceof ItemBlock) {
            if(!((CraftWorld) player.getWorld()).getHandle().a(
                    ((ItemBlock) item).d(),
                    blockPosition.shift(EnumDirection.fromType1(packet.getFace())),
                    false,
                    EnumDirection.fromType1(packet.getFace()),
                    null,
                    packet.getItemStack()))
                return null;
        }

        //Material fromBlock = player.getWorld().getBlockAt(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ()).getType();
        if(/*(fromBlock != bedwarsPractice.getConfigValue().CAN_PLACED && fromBlock != Material.AIR) ||*/
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
        //getPlaced().increment();

        if (hand.getAmount() == 1) {
            player.setItemInHand(null);
        } else {
            hand.setAmount(hand.getAmount() - 1);
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void pasteSchematic(Player player, Schematic schematic, Location location) {
        if(configuration.getLength() == BridgingConfiguration.BridgeLength.INFINITE) return;
        //bedwarsPractice.getServer().getScheduler().runTaskLaterAsynchronously(bedwarsPractice, () -> {
            int startX;
            if(configuration.getDirection() == BridgingConfiguration.BridgeDirection.FORWARD) {
                startX = configuration.getLength().getXForward();
            } else {
                startX = configuration.getLength().getXDiagonal();
            }
            location.add(startX, configuration.getHeight().getY(), configuration.getDirection().getZ());
            for (int x = 0; x < schematic.getWidth(); ++x) {
                for (int y = 0; y < schematic.getHeight(); ++y) {
                    for (int z = 0; z < schematic.getLength(); ++z) {
                        int index = y * schematic.getWidth() * schematic.getLength() + z * schematic.getWidth() + x;
                        player.sendBlockChange(new Location(player.getWorld(), x + location.getX(), y + location.getY(), z + location.getZ()),
                                schematic.getBlocks()[index], schematic.getData()[index]);
                        setSchematicBlock(new FakeBlock(Material.getMaterial(schematic.getBlocks()[index]), player.getWorld(),
                                x + location.getBlockX(), y + location.getBlockY(), z + location.getBlockZ(), -1));
                    }
                }
            }
        //}, 20);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void stop(Player player) {
        setSessionStart(-1L);
        setRunning(false);
        setPeekSpeed(0);
        //getPlaced().setValue(0);

        bedwarsPractice.getServer().getScheduler().runTaskAsynchronously(bedwarsPractice, () -> Optional.ofNullable(player).ifPresent(other -> {
            /* TODO
             * Play sound Δ times
             * Δ = ln(N) -> integer
             * N = number of fakeBlocks
             * Make an effect where block disappear ???
             * Example effect: Smoke
             */
            fakeBlocks.forEach(fakeBlock -> {
                other.sendBlockChange(fakeBlock.toBukkitLocation(), 0, (byte) 0);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            fakeBlocks.clear();
        }));
        /*Optional.ofNullable(player).ifPresent(other -> {
            fakeBlocks.forEach(fakeBlock ->
                    other.sendBlockChange(fakeBlock.toBukkitLocation(), 0, (byte) 0));
            fakeBlocks.clear();
        });*/
    }

    @Override
    public void start(Player player) {
        setSessionStart(System.currentTimeMillis());
        setAttempts(getAttempts() + 1);
        setRunning(true);
    }

    @SneakyThrows
    @Override
    public void win(Player player) {
        player.teleport(getSpawn());

        float time = (System.currentTimeMillis() - getSessionStart())/1000f;
        String timeFormatted = Format.decimal3(time);
        Title.buildAndSend(player, bedwarsPractice.getConfigValue().B_WIN_TITLE.replace("{time}", timeFormatted),
                bedwarsPractice.getConfigValue().B_fadeIn, bedwarsPractice.getConfigValue().B_duration, bedwarsPractice.getConfigValue().B_fadeOut);
        player.sendMessage(bedwarsPractice.getConfigValue().B_WIN_MESSAGE.replace("{time}", timeFormatted)
                .replace("{peekSpeed}", Format.decimal3(peekSpeed)));
        setRunning(false);

        player.getInventory().setItem(0, bedwarsPractice.getConstantObjects().getBlock());
        player.getInventory().setItem(1, bedwarsPractice.getConstantObjects().getBlock());
        player.getInventory().setItem(2, bedwarsPractice.getConstantObjects().getBlock());
        player.getInventory().setItem(3, bedwarsPractice.getConstantObjects().getBlock());

        if(time < bestTime) {
            bedwarsPractice.getMySQLManager().saveRecord(player.getName(), getClass().getSimpleName(), time);
            bestTime = time;
        }
    }

    @Override
    public void loose(Player player) {
        player.teleport(getSpawn());

        player.sendMessage(bedwarsPractice.getConfigValue().B_LOOSE_MESSAGE.replace("{peekSpeed}", Format.decimal3(peekSpeed)));
        setRunning(false);

        player.getInventory().setItem(0, bedwarsPractice.getConstantObjects().getBlock());
        player.getInventory().setItem(1, bedwarsPractice.getConstantObjects().getBlock());
        player.getInventory().setItem(2, bedwarsPractice.getConstantObjects().getBlock());
        player.getInventory().setItem(3, bedwarsPractice.getConstantObjects().getBlock());
        PacketPlayOutNamedSoundEffect soundEffect = new PacketPlayOutNamedSoundEffect(CraftSound.getSound(Sound.ENDERMAN_TELEPORT),
                player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 1, 1);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(soundEffect);
    }

    @Override
    public void sidebarTemplate(List<String> list) {
        list.add(ChatColor.GRAY + Format.now());
        /*if (isRunning()) {
            list.add("§fCronometro: §e" + Format.now());
        }else{
            list.add("§ePiazza un blocco");
            list.add("§eper iniziare!");
        }*/
        if(!isRunning()) {
            list.add("     ");
            list.add("§bPiazza un blocco");
            list.add("§bper iniziare!");
        }
        list.add(" ");
        if(configuration.getLength() == BridgingConfiguration.BridgeLength.INFINITE)
            list.add("Distanza: §bInfinita");
         else
            list.add("Distanza: §b" + (configuration.getLength().getXForward() + configuration.getHeight().getY()));


        //list.add("Piazzati: §b" + getPlaced());
        if(isRunning())
            list.add("Tempo: §b" + Format.decimal1((System.currentTimeMillis() - getSessionStart())/1000f));
        else
            list.add("Tempo: §b0");
        list.add("Velocità: §b" + Format.decimal1(getMovementSpeed()) + " m/s");
        list.add("  ");
        list.add("Miglior Tempo: §b" + (bestTime == Float.MAX_VALUE ? "Nessuno" : bestTime));
        list.add("   ");
        list.add("Modalità: §7" + getType().name());
        list.add("    ");
        list.add(ChatColor.YELLOW + "play.coralmc.it");
    }

    @Override
    public Location getSpawn() {
        return bedwarsPractice.getSpawns().getBridging().getSpawn();
    }
}
