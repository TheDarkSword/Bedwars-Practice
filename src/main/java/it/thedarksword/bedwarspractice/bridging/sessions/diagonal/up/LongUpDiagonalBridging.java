package it.thedarksword.bedwarspractice.bridging.sessions.diagonal.up;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingConfiguration;
import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingSession;
import it.thedarksword.bedwarspractice.inventories.BridgingSettingsInventory;
import it.thedarksword.bedwarspractice.utils.formatter.Format;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class LongUpDiagonalBridging extends BridgingSession {

    public LongUpDiagonalBridging(BedwarsPractice bedwarsPractice) {
        this(bedwarsPractice, null);
    }

    public LongUpDiagonalBridging(BedwarsPractice bedwarsPractice, BridgingSettingsInventory inventory) {
        super(new BridgingConfiguration(BridgingConfiguration.BridgeLength.LONG, BridgingConfiguration.BridgeHeight.SLIGHT, BridgingConfiguration.BridgeDirection.DIAGONAL),
                bedwarsPractice, inventory);
    }
}
