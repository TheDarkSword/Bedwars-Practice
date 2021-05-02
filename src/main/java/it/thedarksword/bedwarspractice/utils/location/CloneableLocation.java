package it.thedarksword.bedwarspractice.utils.location;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;

@RequiredArgsConstructor
@Getter
public class CloneableLocation {

    private final Location location;

    public Location cloneLocation() {
        return new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
    }
}
