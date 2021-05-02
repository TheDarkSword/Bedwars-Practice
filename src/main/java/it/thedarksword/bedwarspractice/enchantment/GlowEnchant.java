package it.thedarksword.bedwarspractice.enchantment;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public class GlowEnchant extends EnchantmentWrapper {

    private static Enchantment glow;

    public GlowEnchant(int id) {
        super(id);
    }
    @Override
    public boolean canEnchantItem(ItemStack item) {
        return true;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return null;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public String getName() {
        return "Glow";
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    public static void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null , true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        glow = new GlowEnchant(255);
        Enchantment.registerEnchantment(glow);
    }

    public static void addGlow(ItemStack item) {
        item.addEnchantment(glow , 1);
    }

    public static void removeGlow(ItemStack item) {
        item.removeEnchantment(glow);
    }

    public static boolean isGlowing(ItemStack item) {
        return item.containsEnchantment(glow);
    }
}
