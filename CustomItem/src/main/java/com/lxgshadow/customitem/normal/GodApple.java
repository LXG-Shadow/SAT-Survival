package com.lxgshadow.customitem.normal;

import com.lxgshadow.customitem.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;


public class GodApple {
    public static void createRecipe(){
        ItemStack item = new ItemStack(Material.GOLDEN_APPLE,1);
        ItemMeta meta = item.getItemMeta();
        ((Damageable) meta).setDamage(1);
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(),"EnchantedApple"),item);
        r.shape("111","101","111");
        r.setIngredient('1',Material.GOLD_BLOCK);
        r.setIngredient('0',Material.APPLE);
        Main.getInstance().getServer().addRecipe(r);
    }

}
