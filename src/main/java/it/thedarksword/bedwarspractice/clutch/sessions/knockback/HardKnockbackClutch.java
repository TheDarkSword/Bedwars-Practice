package it.thedarksword.bedwarspractice.clutch.sessions.knockback;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.clutch.KnockBackClutchConfiguration;
import it.thedarksword.bedwarspractice.clutch.sessions.KnockbackClutch;
import it.thedarksword.bedwarspractice.manager.ConstantObjects;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HardKnockbackClutch extends KnockbackClutch {

    public HardKnockbackClutch(BedwarsPractice bedwarsPractice, Player player, boolean checkpointEnabled, ConstantObjects.PlaceableBlock placeableBlock) {
        super(bedwarsPractice, player, KnockBackClutchConfiguration.HARD, checkpointEnabled, placeableBlock);
    }

    public HardKnockbackClutch(BedwarsPractice bedwarsPractice, Player player, ConstantObjects.PlaceableBlock placeableBlock) {
        super(bedwarsPractice, player, KnockBackClutchConfiguration.HARD, placeableBlock);
    }
}
