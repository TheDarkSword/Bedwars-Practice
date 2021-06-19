package it.thedarksword.bedwarspractice.clutch.sessions.knockback;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.clutch.KnockBackClutchConfiguration;
import it.thedarksword.bedwarspractice.clutch.sessions.KnockbackClutch;
import org.bukkit.entity.Player;

public class MediumKnockbackClutch extends KnockbackClutch {

    public MediumKnockbackClutch(BedwarsPractice bedwarsPractice, Player player) {
        super(bedwarsPractice, player, KnockBackClutchConfiguration.MEDIUM);
    }
}
