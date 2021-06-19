package it.thedarksword.bedwarspractice.clutch.sessions.knockback;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.clutch.KnockBackClutchConfiguration;
import it.thedarksword.bedwarspractice.clutch.sessions.KnockbackClutch;
import org.bukkit.entity.Player;

public class HardKnockbackClutch extends KnockbackClutch {

    public HardKnockbackClutch(BedwarsPractice bedwarsPractice, Player player) {
        super(bedwarsPractice, player, KnockBackClutchConfiguration.HARD);
    }
}
