package it.thedarksword.bedwarspractice.scoreboard;

import lombok.SneakyThrows;
import net.minecraft.server.v1_8_R3.Scoreboard;
import org.bukkit.scoreboard.Objective;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NMS {
    private static Method getHandle;
    private static Field playerScores;

    @SneakyThrows
    public static void init() {
        Class<?> craftObjective = Class.forName("org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftObjective");
        getHandle = craftObjective.getDeclaredMethod("getHandle");
        getHandle.setAccessible(true);

        playerScores = Scoreboard.class.getDeclaredField("playerScores");
        playerScores.setAccessible(true);
    }

    @SneakyThrows
    public static Object getObjective(Objective objective) {
        return getHandle.invoke(objective);
    }

    @SneakyThrows
    public static Object getPlayerScores(Scoreboard scoreboard) {
        return playerScores.get(scoreboard);
    }
}
