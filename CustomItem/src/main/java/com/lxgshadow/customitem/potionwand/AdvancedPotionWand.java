package com.lxgshadow.customitem.potionwand;

import com.lxgshadow.customitem.Config;
import com.lxgshadow.customitem.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AdvancedPotionWand {
    private static ItemStack item;
    private static int duration = Config.potionwand_advanced_duration;
    private static int amplifier = Config.potionwand_advanced_amplifier;

    public static ItemStack getItem(){return item;}
    public static int getDuration(){return duration;};
    public static int getAmplifier(){return amplifier;}

    public static void createRecipe(){
        item = new ItemStack(Material.BLAZE_ROD,1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY,2);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Advanced Potion Wand");
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        List<String> lore = new ArrayList<>();
        lore.add("Description: The base for any of advanced potion wand");
        meta.setLore(lore);
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(),"advancedpotionwand"),item);
        r.shape("016","232","545");
        r.setIngredient('0',Material.GOLD_BLOCK);
        r.setIngredient('1',Material.DIAMOND);
        r.setIngredient('2',Material.EMERALD_BLOCK);
        r.setIngredient('3', PotionWandCore.getItem().getType());
        r.setIngredient('4',Material.BLAZE_ROD);
        r.setIngredient('5',Material.OBSIDIAN);
        r.setIngredient('6',Material.IRON_BLOCK);
        Main.getInstance().getServer().addRecipe(r);
        Main.getInstance().getServer().getPluginManager().registerEvents(new AdvancedPotionWandListener(),Main.getInstance());
    }
}

class AdvancedPotionWandListener implements Listener {
    @EventHandler
    public void onCraft(CraftItemEvent event){
        if ((!event.getInventory().contains(PotionWandCore.getItem())) && event.getRecipe().getResult().isSimilar(AdvancedPotionWand.getItem())){
            event.setCancelled(true);
        }
    }
}
