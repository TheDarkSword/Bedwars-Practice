package it.thedarksword.bedwarspractice.clipboards;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

import java.io.File;
import java.io.FileInputStream;

@Getter
@RequiredArgsConstructor
public class Schematic {

    private final short[] blocks;
    private final byte[] data;
    private final short width;
    private final short length;
    private final short height;

    /*@SuppressWarnings("deprecation")
    public void spawn(Player player, Location start) {
        for(int i = 0; i < 6; i++) {
            player.sendBlockChange(start, Material.GOLD_BLOCK, (byte)0);
            start.add(1, 0, 0);
        }
    }*/

    /*@SuppressWarnings("deprecation")
    public void pasteSchematic(Location location, PracticePlayer practicePlayer) {
        World world = location.getWorld();
        Player player = practicePlayer.getPlayer();
        practicePlayer.setFinish(new it.thedarksword.bedwarspractice.objects.Cuboid(location.getX(), location.getY(), location.getZ(),
                location.getX() + 8, location.getY() + 1, location.getZ() + 7));
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                for (int z = 0; z < length; ++z) {
                    int index = y * width * length + z * width + x;
                    player.sendBlockChange(new Location(world, x + location.getX(), y + location.getY(), z + location.getZ()),
                            blocks[index], data[index]);
                    practicePlayer.getSchematicBlocks().add(new BlockLocation(x + location.getBlockX(),
                            y + location.getBlockY(), z + location.getBlockZ(), -1));
                }
            }
        }
    }*/

    public static Schematic loadSchematic(File schematic) {
        if (!schematic.exists())
            return null;
        try {
            FileInputStream stream = new FileInputStream(schematic);
            NBTTagCompound nbtdata = NBTCompressedStreamTools.a(stream);

            short width = nbtdata.getShort("Width");
            short height = nbtdata.getShort("Height");
            short length = nbtdata.getShort("Length");

            byte[] blocks = nbtdata.getByteArray("Blocks");
            byte[] data = nbtdata.getByteArray("Data");

            byte[] addId = new byte[0];

            if (nbtdata.hasKey("AddBlocks")) {
                addId = nbtdata.getByteArray("AddBlocks");
            }

            short[] sblocks = new short[blocks.length];
            for (int index = 0; index < blocks.length; index++) {
                if ((index >> 1) >= addId.length) {
                    sblocks[index] = (short) (blocks[index] & 0xFF);
                } else {
                    if ((index & 1) == 0) {
                        sblocks[index] = (short) (((addId[index >> 1] & 0x0F) << 8) + (blocks[index] & 0xFF));
                    } else {
                        sblocks[index] = (short) (((addId[index >> 1] & 0xF0) << 4) + (blocks[index] & 0xFF));
                    }
                }
            }

            stream.close();
            return new Schematic(sblocks, data, width, length, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
