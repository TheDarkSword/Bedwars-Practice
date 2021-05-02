package it.thedarksword.bedwarspractice.clipboards;

import org.bukkit.Location;

public class InfiniteCuboid implements Region {

    public boolean isInside(Location location) {
        return false;
    }

    @Override
    public double lastX() {
        return Double.MAX_VALUE;
    }
}
