package it.thedarksword.bedwarspractice.clutch.sessions.knockback;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.clutch.KnockBackClutchConfiguration;
import it.thedarksword.bedwarspractice.clutch.sessions.KnockbackClutch;
import it.thedarksword.bedwarspractice.manager.ConstantObjects;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MediumKnockbackClutch extends KnockbackClutch {

    public MediumKnockbackClutch(BedwarsPractice bedwarsPractice, Player player, boolean checkpointEnabled, ConstantObjects.PlaceableBlock placeableBlock) {
        super(bedwarsPractice, player, KnockBackClutchConfiguration.MEDIUM, checkpointEnabled, placeableBlock);
    }

    public MediumKnockbackClutch(BedwarsPractice bedwarsPractice, Player player, ConstantObjects.PlaceableBlock placeableBlock) {
        super(bedwarsPractice, player, KnockBackClutchConfiguration.MEDIUM, placeableBlock);
    }
}
