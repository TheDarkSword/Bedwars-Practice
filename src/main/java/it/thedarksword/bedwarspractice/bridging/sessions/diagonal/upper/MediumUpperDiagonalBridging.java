package it.thedarksword.bedwarspractice.bridging.sessions.diagonal.upper;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingConfiguration;
import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingSession;
import it.thedarksword.bedwarspractice.inventories.BridgingSettingsInventory;
import it.thedarksword.bedwarspractice.utils.formatter.Format;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class MediumUpperDiagonalBridging extends BridgingSession {

    public MediumUpperDiagonalBridging(BedwarsPractice bedwarsPractice) {
        this(bedwarsPractice, null);
    }

    public MediumUpperDiagonalBridging(BedwarsPractice bedwarsPractice, BridgingSettingsInventory inventory) {
        super(new BridgingConfiguration(BridgingConfiguration.BridgeLength.MEDIUM, BridgingConfiguration.BridgeHeight.STAIRCASE, BridgingConfiguration.BridgeDirection.DIAGONAL),
                bedwarsPractice, inventory);
    }
}
