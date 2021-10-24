package it.thedarksword.bedwarspractice.manager;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.inventories.*;
import it.thedarksword.bedwarspractice.inventories.top.TopBridgingInventory;
import it.thedarksword.bedwarspractice.inventories.top.TopKnockbackClutchInventory;
import it.thedarksword.bedwarspractice.inventories.top.TopsInventory;
import lombok.Getter;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Inventories {

    private final ModeInventory modeInventory;
    private final BlocksInventory blocksInventory;
    private final BridgingSpawnInventory bridgingSpawnInventory;
    private final KnockBackClutchDifficultyInventory knockBackClutchDifficultyInventory;
    private final LaunchSettings launchSettings;

    private final TopsInventory topsInventory;
    private final TopBridgingInventory topBridgingInventory;
    private final TopKnockbackClutchInventory topKnockbackClutchInventory;

    private final Map<String, Inventory> topModality = new ConcurrentHashMap<>();

    public Inventories(BedwarsPractice bedwarsPractice) {
        modeInventory = new ModeInventory();
        blocksInventory = new BlocksInventory(bedwarsPractice);
        bridgingSpawnInventory = new BridgingSpawnInventory();
        knockBackClutchDifficultyInventory = new KnockBackClutchDifficultyInventory(bedwarsPractice);
        launchSettings = new LaunchSettings(bedwarsPractice);

        topsInventory = new TopsInventory();
        topBridgingInventory = new TopBridgingInventory();
        topKnockbackClutchInventory = new TopKnockbackClutchInventory();
    }
}
