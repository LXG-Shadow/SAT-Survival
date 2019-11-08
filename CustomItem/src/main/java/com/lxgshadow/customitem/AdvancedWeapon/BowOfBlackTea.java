package com.lxgshadow.customitem.AdvancedWeapon;

import com.lxgshadow.customitem.interfaces.CustomItems;
import com.lxgshadow.customitem.Main;
import com.lxgshadow.customitem.Messages;
import com.lxgshadow.customitem.utils.EtcUtils;
import com.lxgshadow.customitem.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


import java.util.ArrayList;
import java.util.Arrays;

public class BowOfBlackTea implements CustomItems {
    private static ItemStack item;
    private static String name = "Bow of Black Tea";
    private static String dpName = ChatColor.AQUA+name;
    public static String regName = name.replace(" ","_").toLowerCase();
    private static String[] lores = {
            ChatColor.AQUA+""+ChatColor.ITALIC+"Advanced Weapon",
            ChatColor.WHITE+""+ChatColor.ITALIC+"Description: 紅茶だけができますか？",
            ChatColor.WHITE+""+ChatColor.ITALIC+"*每次射中生物时，有 1% 的概率额外造成14的真实伤害",
            ChatColor.WHITE+""+ChatColor.ITALIC+"*有 5% 的概率额外造成14秒的失明眩晕虚弱缓慢",
            ChatColor.BLACK+"Register Name: ["+regName+"]"
    };

    public static ItemStack getItem() {
        return item;
    }

    public static void createRecipe(){
        item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(dpName);
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(),regName),item);
        r.shape("123","203","123");
        r.setIngredient('0',Material.GLASS_BOTTLE);
        r.setIngredient('1',Material.WHEAT_SEEDS);
        r.setIngredient('2',Material.STICK);
        r.setIngredient('3', Material.RED_BED);
        Main.getInstance().getServer().addRecipe(r);
        Main.getInstance().getServer().getPluginManager().registerEvents(new BowOfBlackTeaListener(),Main.getInstance());
    }
}

class BowOfBlackTeaListener implements Listener {
    @EventHandler

    public void onShoot(EntityShootBowEvent event){
        if (!(event.getProjectile() instanceof Arrow)){
            return;
        }
        if (event.getBow() != null && ItemUtils.isRegisterNameSimilar(BowOfBlackTea.regName,event.getBow())){
            Arrow arrow = (Arrow) event.getProjectile();
            arrow.setCustomName("BlackTea");
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow){
            if (event.getDamager().getCustomName() == null){
                return;
            }
            if (event.getDamager().getCustomName().equals("BlackTea") && (event.getEntity() instanceof LivingEntity)){
                if (EtcUtils.chance(5,100)){
                    ((LivingEntity)event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,14*20,66),true);
                    ((LivingEntity)event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,14*20,5),true);
                    ((LivingEntity)event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,14*20,66),true);
                    ((LivingEntity)event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,14*20,66),true);
                    ((LivingEntity)event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.JUMP,14*20,-10),true);
                    if (event.getEntity() instanceof Player){
                        event.getEntity().sendMessage(Messages.bowofblacktea_effect_victim);
                    }
                    if (((Arrow) event.getDamager()).getShooter() != null && ((Arrow) event.getDamager()).getShooter() instanceof Player){
                        ((Player) ((Arrow) event.getDamager()).getShooter()).sendMessage(Messages.bowofblacktea_effect_shooter);
                    }
                }
                if (EtcUtils.chance(1,100)){
                    ((LivingEntity) event.getEntity()).damage(14);
                    if (event.getEntity() instanceof Player){
                        event.getEntity().sendMessage(Messages.bowofblacktea_realdamage_victim);
                    }
                    if (((Arrow) event.getDamager()).getShooter() != null && ((Arrow) event.getDamager()).getShooter() instanceof Player){
                        ((Player) ((Arrow) event.getDamager()).getShooter()).sendMessage(Messages.bowofblacktea_realdamage_shooter);
                    }
                }
            }
        }
    }
}