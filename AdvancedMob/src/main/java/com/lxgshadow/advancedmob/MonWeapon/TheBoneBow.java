package com.lxgshadow.advancedmob.MonWeapon;

import com.lxgshadow.advancedmob.utils.EtcUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class TheBoneBow {
    public static ItemStack item;
    private static String name = "The Bone Bow";
    private static String dpname = ChatColor.GRAY+name;
    private static String[] lores = {
            ChatColor.WHITE+""+ChatColor.ITALIC+"Monster Weapon",
            ChatColor.WHITE+""+ChatColor.ITALIC+"Description: Made by Bone"
    };
    public static void create(){
        item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(dpname);
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        item.setItemMeta(meta);
    }

    public static ItemStack get(){
        ItemStack n = item.clone();
        if (EtcUtils.chance(1,1)){n.addEnchantment(Enchantment.ARROW_DAMAGE,EtcUtils.randInt(1,5));}
        if (EtcUtils.chance(1,3)){n.addEnchantment(Enchantment.ARROW_KNOCKBACK,EtcUtils.randInt(1,2));}
        if (EtcUtils.chance(1,10)){n.addEnchantment(Enchantment.ARROW_FIRE,EtcUtils.randInt(1,1));}
        return n;
    }
}
