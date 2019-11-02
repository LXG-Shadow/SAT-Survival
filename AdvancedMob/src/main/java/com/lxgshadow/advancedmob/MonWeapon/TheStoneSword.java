package com.lxgshadow.advancedmob.MonWeapon;

import com.lxgshadow.advancedmob.utils.EtcUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class TheStoneSword {
    public static ItemStack item;
    private static String name = "The Stone Sword";
    private static String[] lores = {
            ChatColor.WHITE+"Monster Weapon",
            ChatColor.WHITE+"Description: Made From Stone"
    };
    public static void create(){
        item = new ItemStack(Material.STONE_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        item.setItemMeta(meta);
    }

    public static ItemStack get(){
        ItemStack n = item.clone();
        if (EtcUtils.chance(1,3)){n.addEnchantment(Enchantment.DAMAGE_ALL,EtcUtils.randInt(1,3));}
        if (EtcUtils.chance(1,10)){n.addEnchantment(Enchantment.FIRE_ASPECT,EtcUtils.randInt(1,2));}
        return n;
    }
}
