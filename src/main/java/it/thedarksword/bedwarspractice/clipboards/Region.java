package it.thedarksword.bedwarspractice.clipboards;

import org.bukkit.Location;

public interface Region {

    boolean isInside(Location location);

    double lastX();
}
