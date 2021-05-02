package it.thedarksword.bedwarspractice.utils.location;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

@Getter
public class FakeBlock {

    private final Material material;
    private final World world;
    private final int x;
    private final int y;
    private final int z;

    /*
    0: y--
    1: y++
    2: z--
    3: z++
    4: x--
    5: x++
     */

    public FakeBlock(Material material, World world, int x, int y, int z, int face) {
        switch (face) {
            case 0:
                y--;
                break;
            case 1:
                y++;
                break;
            case 2:
                z--;
                break;
            case 3:
                z++;
                break;
            case 4:
                x--;
                break;
            case 5:
                x++;
                break;
            default:
                break;
        }

        this.material = material;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location toBukkitLocation() {
        return new Location(world, x, y, z);
    }
}
