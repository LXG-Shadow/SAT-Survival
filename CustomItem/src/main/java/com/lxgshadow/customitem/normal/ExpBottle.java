package com.lxgshadow.customitem.normal;

import com.lxgshadow.customitem.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ExpBottle {
    public static void createRecipe(){
        ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE,16);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(),"CraftsExpBottle"),item);
        r.shape(" 1 ","101"," 1 ");
        r.setIngredient('1',Material.REDSTONE_BLOCK);
        r.setIngredient('0',Material.GLASS_BOTTLE);
        Main.getInstance().getServer().addRecipe(r);
    }
}
