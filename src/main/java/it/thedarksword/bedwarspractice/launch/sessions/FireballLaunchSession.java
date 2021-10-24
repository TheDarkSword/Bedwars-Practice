package it.thedarksword.bedwarspractice.launch.sessions;

import it.thedarksword.bedwarspractice.BedwarsPractice;
import it.thedarksword.bedwarspractice.abstraction.sessions.SessionType;
import it.thedarksword.bedwarspractice.abstraction.sessions.launch.LaunchSession;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class FireballLaunchSession extends LaunchSession {

    public FireballLaunchSession(BedwarsPractice bedwarsPractice, Player player) {
        super(SessionType.LAUNCH, bedwarsPractice, player, Material.FIREBALL);
    }
}
