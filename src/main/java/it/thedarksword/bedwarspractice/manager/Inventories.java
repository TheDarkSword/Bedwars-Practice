package it.thedarksword.bedwarspractice.manager;

import it.thedarksword.bedwarspractice.inventories.BridgingSpawnInventory;
import it.thedarksword.bedwarspractice.inventories.ModeInventory;
import lombok.Getter;

@Getter
public class Inventories {

    private final ModeInventory modeInventory;
    private final BridgingSpawnInventory bridgingSpawnInventory;

    public Inventories() {
        modeInventory = new ModeInventory();
        bridgingSpawnInventory = new BridgingSpawnInventory();
    }
}
