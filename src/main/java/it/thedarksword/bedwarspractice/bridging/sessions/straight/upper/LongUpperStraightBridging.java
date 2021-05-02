package it.thedarksword.bedwarspractice.bridging.sessions.straight.upper;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingConfiguration;
import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingSession;
import it.thedarksword.bedwarspractice.inventories.BridgingSettingsInventory;
import it.thedarksword.bedwarspractice.utils.formatter.Format;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class LongUpperStraightBridging extends BridgingSession {

    public LongUpperStraightBridging(BedwarsPractice bedwarsPractice) {
        this(bedwarsPractice, null);
    }

    public LongUpperStraightBridging(BedwarsPractice bedwarsPractice, BridgingSettingsInventory inventory) {
        super(new BridgingConfiguration(BridgingConfiguration.BridgeLength.LONG, BridgingConfiguration.BridgeHeight.STAIRCASE, BridgingConfiguration.BridgeDirection.FORWARD),
                bedwarsPractice, inventory);
    }
}
