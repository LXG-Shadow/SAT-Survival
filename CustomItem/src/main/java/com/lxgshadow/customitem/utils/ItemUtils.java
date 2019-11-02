package com.lxgshadow.customitem.utils;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Boat;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemUtils {
    public static ItemStack removeEnchantments(ItemStack item){
        item = item.clone();
        for(Enchantment e : item.getEnchantments().keySet())
        {
            item.removeEnchantment(e);
        }
        return item;
    }

    public static boolean isSimilarIgnoreEnchantments(ItemStack item1, ItemStack item2){
        return removeEnchantments(item1).isSimilar(removeEnchantments(item2));
    }

    public static String getRegisterName(List<String> lores){
        for (String lore:lores){
            if (lore.contains("Register Name")){
                return ChatColor.stripColor(lore.substring(lore.indexOf("[")+1,lore.indexOf("]")));
            }
        }
        return null;
    }

    public static Boolean isRegisterNameSimilar(String regName,ItemStack item){
        ItemMeta meta = item.getItemMeta();
        if (meta == null){return false;}
        if (!meta.hasLore()){return false;}
        List<String> lores = meta.getLore();
        return regName.equals(ItemUtils.getRegisterName(lores));
    }
}
