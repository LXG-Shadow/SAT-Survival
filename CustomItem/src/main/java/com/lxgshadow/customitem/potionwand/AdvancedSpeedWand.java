package com.lxgshadow.customitem.potionwand;

import com.lxgshadow.customitem.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
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

public class AdvancedSpeedWand {
    static ItemStack costs = new ItemStack(Material.SUGAR,1);
    static PotionEffectType effectType = PotionEffectType.SPEED;
    static int duration = AdvancedPotionWand.getDuration();
    static int amplifier = AdvancedPotionWand.getAmplifier();

    private static ItemStack item;
    private static String name = "Advanced Speed Wand";
    private static String[] lores = {
            "Description: Get "+effectType.getName() + " " + (AdvancedPotionWand.getAmplifier()+1)+" for "+ AdvancedPotionWand.getDuration() +" secs when you right click on it.",
            "Using "+ costs.getAmount() +" "+costs.getType().getKey().getKey()+" for activating the wand"
    };

    public static ItemStack getItem(){return item;}

    public static void createRecipe(){
        item = new ItemStack(Material.BLAZE_ROD,1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY,2);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(),name.replace(" ","_").toLowerCase()),item);
        r.shape("111","101","222");
        r.setIngredient('1',Material.SUGAR);
        r.setIngredient('0',AdvancedPotionWand.getItem().getType());
        r.setIngredient('2',Material.FEATHER);
        Main.getInstance().getServer().addRecipe(r);
        Main.getInstance().getServer().getPluginManager().registerEvents(new AdvancedSpeedWandListener(),Main.getInstance());
    }
}

class AdvancedSpeedWandListener implements Listener {
    @EventHandler
    public void onCraft(CraftItemEvent event){
        if ((!event.getInventory().contains(AdvancedPotionWand.getItem())) && event.getRecipe().getResult().isSimilar(AdvancedSpeedWand.getItem())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onRightclick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        PlayerInventory inv = player.getInventory();
        if (event.getAction() == Action.RIGHT_CLICK_AIR){
            if (inv.contains(AdvancedSpeedWand.costs.getType(),AdvancedSpeedWand.costs.getAmount()) && (inv.getItemInMainHand().isSimilar(AdvancedSpeedWand.getItem()) || inv.getItemInOffHand().isSimilar(AdvancedSpeedWand.getItem()))){
                inv.removeItem(AdvancedSpeedWand.costs);
                player.removePotionEffect(AdvancedSpeedWand.effectType);
                player.addPotionEffect(new PotionEffect(AdvancedSpeedWand.effectType, AdvancedSpeedWand.duration*20,AdvancedSpeedWand.amplifier));
            }
        }
    }
}