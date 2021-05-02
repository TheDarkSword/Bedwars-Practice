package it.thedarksword.bedwarspractice.bridging.sessions.straight.none;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingSession;
import it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingConfiguration;
import it.thedarksword.bedwarspractice.inventories.BridgingSettingsInventory;

public class ShortStraightBridging extends BridgingSession {

    public ShortStraightBridging(BedwarsPractice bedwarsPractice) {
        this(bedwarsPractice, null);
    }

    public ShortStraightBridging(BedwarsPractice bedwarsPractice, BridgingSettingsInventory inventory) {
        super(new BridgingConfiguration(BridgingConfiguration.BridgeLength.SHORT, BridgingConfiguration.BridgeHeight.NONE, BridgingConfiguration.BridgeDirection.FORWARD),
                bedwarsPractice, inventory);
    }
}
