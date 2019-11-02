package com.lxgshadow.customitem.potionwand;

import com.lxgshadow.customitem.Config;
import com.lxgshadow.customitem.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BasicPotionWand {
    private static ItemStack item;
    private static int duration = Config.potionwand_basic_duration;
    private static int amplifier = Config.potionwand_basic_amplifier;

    public static ItemStack getItem(){return item;}
    public static int getDuration(){return duration;};
    public static int getAmplifier(){return amplifier;}
    public static void createRecipe(){
        item = new ItemStack(Material.STICK,1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY,1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Basic Potion Wand");
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        List<String> lore = new ArrayList<>();
        lore.add("Description: The base for any of basic potion wand");
        meta.setLore(lore);
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(),"basicpotionwand"),item);
        r.shape("010","232","242");
        r.setIngredient('0',Material.GOLD_INGOT);
        r.setIngredient('1',Material.COAL);
        r.setIngredient('2',Material.IRON_INGOT);
        r.setIngredient('3', PotionWandCore.getItem().getType());
        r.setIngredient('4',Material.STICK);
        Main.getInstance().getServer().addRecipe(r);
        Main.getInstance().getServer().getPluginManager().registerEvents(new BasicPotionWandListener(),Main.getInstance());
    }
}

class BasicPotionWandListener implements Listener{
    @EventHandler
    public void onCraft(CraftItemEvent event){
        if ((!event.getInventory().contains(PotionWandCore.getItem())) && event.getRecipe().getResult().isSimilar(BasicPotionWand.getItem())){
            event.setCancelled(true);
        }
    }
}
