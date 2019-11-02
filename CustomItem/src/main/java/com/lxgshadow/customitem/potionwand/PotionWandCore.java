package com.lxgshadow.customitem.potionwand;

import com.lxgshadow.customitem.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PotionWandCore {
    private static ItemStack item;

    public static ItemStack getItem(){return item;}

    public static void createRecipe(){
        item = new ItemStack(Material.REDSTONE,1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY,1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("PotionWandCore");
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        List<String> lore = new ArrayList<>();
        lore.add("Description: The Core for a potion wand");
        meta.setLore(lore);
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(),"PotionWandCore"),item);
        r.shape("121","202","121");
        r.setIngredient('0',Material.GLASS_BOTTLE);
        r.setIngredient('1',Material.LAPIS_LAZULI);
        r.setIngredient('2',Material.REDSTONE_BLOCK);
        Main.getInstance().getServer().addRecipe(r);
    }

    public static void initialize(){
        createRecipe();
        BasicPotionWand.createRecipe();
        BasicSpeedWand.createRecipe();
        BasicStrengthWand.createRecipe();
        AdvancedPotionWand.createRecipe();
        AdvancedSpeedWand.createRecipe();
        AdvancedStrengthWand.createRecipe();
    }
}
