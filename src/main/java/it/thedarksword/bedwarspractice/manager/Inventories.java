package it.thedarksword.bedwarspractice.manager;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.inventories.BridgingSpawnInventory;
import it.thedarksword.bedwarspractice.inventories.KnockBackClutchDifficultyInventory;
import it.thedarksword.bedwarspractice.inventories.ModeInventory;
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
    private final BridgingSpawnInventory bridgingSpawnInventory;
    private final KnockBackClutchDifficultyInventory knockBackClutchDifficultyInventory;
    private final TopsInventory topsInventory;
    private final TopBridgingInventory topBridgingInventory;
    private final TopKnockbackClutchInventory topKnockbackClutchInventory;

    private final Map<String, Inventory> topModality = new ConcurrentHashMap<>();

    public Inventories(BedwarsPractice bedwarsPractice) {
        modeInventory = new ModeInventory();
        bridgingSpawnInventory = new BridgingSpawnInventory();
        knockBackClutchDifficultyInventory = new KnockBackClutchDifficultyInventory(bedwarsPractice);
        topsInventory = new TopsInventory();
        topBridgingInventory = new TopBridgingInventory();
        topKnockbackClutchInventory = new TopKnockbackClutchInventory();
    }
}
