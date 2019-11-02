package com.lxgshadow.customitem.potionwand;

import com.lxgshadow.customitem.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

public class AdvancedStrengthWand {
    static ItemStack costs = new ItemStack(Material.BLAZE_POWDER,1);
    static PotionEffectType effectType = PotionEffectType.INCREASE_DAMAGE;
    static int duration = AdvancedPotionWand.getDuration();
    static int amplifier = AdvancedPotionWand.getAmplifier();

    private static ItemStack item;
    private static String name = "Advanced Strength Wand";
    private static String[] lores = {
            "Description: Get "+effectType.getName() + " " + (AdvancedPotionWand.getAmplifier()+1)+" for "+ AdvancedPotionWand.getDuration() +" secs when you right click on it.",
            "Using "+ costs.getAmount() +" "+costs.getType().getKey().getKey()+" for activating the wand"
    };

    public static ItemStack getItem(){return item;}

    public static void createRecipe(){
        item = AdvancedPotionWand.getItem().clone();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(),name.replace(" ","_").toLowerCase()),item);
        r.shape("111","101","222");
        r.setIngredient('1',Material.BLAZE_POWDER);
        r.setIngredient('0',AdvancedPotionWand.getItem().getType());
        r.setIngredient('2',Material.STONE_SWORD);
        Main.getInstance().getServer().addRecipe(r);
        Main.getInstance().getServer().getPluginManager().registerEvents(new AdvancedStrengthWandListener(),Main.getInstance());
    }
}

class AdvancedStrengthWandListener implements Listener {
    @EventHandler
    public void onCraft(CraftItemEvent event){
        if ((!event.getInventory().contains(AdvancedPotionWand.getItem())) && event.getRecipe().getResult().isSimilar(AdvancedStrengthWand.getItem())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onRightclick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        PlayerInventory inv = player.getInventory();
        if (event.getAction() == Action.RIGHT_CLICK_AIR){
            if (inv.contains(AdvancedStrengthWand.costs.getType(),AdvancedStrengthWand.costs.getAmount()) && (inv.getItemInMainHand().isSimilar(AdvancedStrengthWand.getItem()) || inv.getItemInOffHand().isSimilar(AdvancedStrengthWand.getItem()))){
                inv.removeItem(AdvancedStrengthWand.costs);
                player.removePotionEffect(AdvancedStrengthWand.effectType);
                player.addPotionEffect(new PotionEffect(AdvancedStrengthWand.effectType, AdvancedStrengthWand.duration*20,AdvancedStrengthWand.amplifier));
            }
        }
    }
}
