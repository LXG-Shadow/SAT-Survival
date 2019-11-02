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

public class BasicStrengthWand {
    static ItemStack costs = new ItemStack(Material.BLAZE_POWDER,1);
    static PotionEffectType effectType = PotionEffectType.INCREASE_DAMAGE;
    static int duration = BasicPotionWand.getDuration();
    static int amplifier = BasicPotionWand.getAmplifier();

    private static ItemStack item;
    private static String name = "Basic Strength Wand";
    private static String[] lores = {
            "Description: Get "+effectType.getName() + " " + (BasicPotionWand.getAmplifier()+1)+" for "+ BasicPotionWand.getDuration() +" secs when you right click on it.",
            "Using "+ costs.getAmount() +" "+costs.getType().getKey().getKey()+" for activating the wand"
    };

    public static ItemStack getItem(){return item;}

    public static void createRecipe(){
        item = BasicPotionWand.getItem().clone();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(),name.replace(" ","_").toLowerCase()),item);
        r.shape("111","101","222");
        r.setIngredient('1',Material.BLAZE_POWDER);
        r.setIngredient('0',BasicPotionWand.getItem().getType());
        r.setIngredient('2',Material.STONE_SWORD);
        Main.getInstance().getServer().addRecipe(r);
        Main.getInstance().getServer().getPluginManager().registerEvents(new BasicStrengthWandListener(),Main.getInstance());
    }
}

class BasicStrengthWandListener implements Listener {
    @EventHandler
    public void onCraft(CraftItemEvent event){
        if ((!event.getInventory().contains(BasicPotionWand.getItem())) && event.getRecipe().getResult().isSimilar(BasicStrengthWand.getItem())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onRightclick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        PlayerInventory inv = player.getInventory();
        if (event.getAction() == Action.RIGHT_CLICK_AIR){
            if (inv.contains(BasicStrengthWand.costs.getType(),BasicStrengthWand.costs.getAmount()) && (inv.getItemInMainHand().isSimilar(BasicStrengthWand.getItem()) || inv.getItemInOffHand().isSimilar(BasicStrengthWand.getItem()))){
                inv.removeItem(BasicStrengthWand.costs);
                player.removePotionEffect(BasicStrengthWand.effectType);
                player.addPotionEffect(new PotionEffect(BasicStrengthWand.effectType, BasicStrengthWand.duration*20,BasicStrengthWand.amplifier));
            }
        }
    }
}