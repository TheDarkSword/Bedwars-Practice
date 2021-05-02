package it.thedarksword.bedwarspractice.bridging.sessions.diagonal.none;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingConfiguration;
import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingSession;
import it.thedarksword.bedwarspractice.inventories.BridgingSettingsInventory;
import it.thedarksword.bedwarspractice.utils.formatter.Format;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class ShortDiagonalBridging extends BridgingSession {

    public ShortDiagonalBridging(BedwarsPractice bedwarsPractice) {
        this(bedwarsPractice, null);
    }

    public ShortDiagonalBridging(BedwarsPractice bedwarsPractice, BridgingSettingsInventory inventory) {
        super(new BridgingConfiguration(BridgingConfiguration.BridgeLength.SHORT, BridgingConfiguration.BridgeHeight.NONE, BridgingConfiguration.BridgeDirection.DIAGONAL),
                bedwarsPractice, inventory);
    }
}
