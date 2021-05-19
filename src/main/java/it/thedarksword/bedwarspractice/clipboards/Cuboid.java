package it.thedarksword.bedwarspractice.clipboards;

import org.bukkit.Location;

public class Cuboid implements Region {

    private final double minX;
    private final double minY;
    private final double minZ;
    private final double maxX;
    private final double maxY;
    private final double maxZ;

    public Cuboid(Location location1, Location location2) {
        this(location1.getX(), location1.getY(), location1.getZ(), location2.getX(), location2.getY(), location2.getZ());
    }

    public Cuboid(double x1, double y1, double z1, double x2, double y2, double z2) {
        if(x1 < x2){
            minX = x1;
            maxX = x2;
        } else {
            minX = x2;
            maxX = x1;
        }

        if(y1 < y2){
            minY = y1;
            maxY = y2;
        } else {
            minY = y2;
            maxY = y1;
        }

        if(z1 < z2){
            minZ = z1;
            maxZ = z2;
        } else {
            minZ = z2;
            maxZ = z1;
        }
    }

    public boolean isInside(Location location) {
        return location.getX() >= minX && location.getX() <= maxX &&
                location.getY() >= minY && location.getY() <= maxY &&
                location.getZ() >= minZ && location.getZ() <= maxZ;
    }

    @Override
    public double lastX() {
        return maxX;
    }
}
