package it.thedarksword.bedwarspractice.abstraction.sessions;

import it.thedarksword.bedwarspractice.abstraction.interfacing.sessions.TrainingSession;
import it.thedarksword.bedwarspractice.clipboards.Cuboid;
import it.thedarksword.bedwarspractice.clipboards.Region;
import it.thedarksword.bedwarspractice.inventories.SettingsInventory;
import it.thedarksword.bedwarspractice.utils.location.FakeBlock;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class Session implements TrainingSession {

    @Getter private final SessionType type;
    @Getter @Setter private Region finishArea;

    protected final List<FakeBlock> fakeBlocks = new ArrayList<>();
    protected final List<FakeBlock> schematicBlocks = new ArrayList<>();

    @Getter @Setter protected SettingsInventory settingsInventory;
    @Getter @Setter protected SettingsInventory modeInventory;

    public abstract PacketPlayInBlockDig handleBreak(Plugin plugin, Player player, PacketPlayInBlockDig packet);
    public abstract PacketPlayInBlockPlace handlePlace(Plugin plugin, Player player, PacketPlayInBlockPlace packet);

    public void removeFakeBlock(FakeBlock fakeBlock) {
        fakeBlocks.remove(fakeBlock);
    }

    public void setFakeBlock(FakeBlock fakeBlock) {
        fakeBlocks.add(fakeBlock);
    }

    public Optional<FakeBlock> getFakeBlock(int x, int y, int z) {
        for(FakeBlock fakeBlock : fakeBlocks) {
            if(fakeBlock.getX() == x && fakeBlock.getY() == y && fakeBlock.getZ() == z) return Optional.of(fakeBlock);
        }
        return Optional.empty();
    }

    public Optional<FakeBlock> getSchematicFakeBlock(int x, int y, int z) {
        for(FakeBlock fakeBlock : schematicBlocks) {
            if(fakeBlock.getX() == x && fakeBlock.getY() == y && fakeBlock.getZ() == z) return Optional.of(fakeBlock);
        }
        return Optional.empty();
    }

    public void setSchematicBlock(FakeBlock fakeBlock) {
        schematicBlocks.add(fakeBlock);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void clearSchematic(Player player) {
        for(FakeBlock fakeBlock : schematicBlocks) {
            player.sendBlockChange(fakeBlock.toBukkitLocation(), 0, (byte) 0);
        }

        schematicBlocks.clear();
    }
}
