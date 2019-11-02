package com.lxgshadow.customitem.potionwand;

import com.lxgshadow.customitem.Config;
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
import java.util.List;

public class BasicSpeedWand{
    static ItemStack costs = new ItemStack(Material.SUGAR,1);
    static PotionEffectType effectType = PotionEffectType.SPEED;
    static int duration = BasicPotionWand.getDuration();
    static int amplifier = BasicPotionWand.getAmplifier();

    private static ItemStack item;
    private static String name = "Basic Speed Wand";
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
        r.setIngredient('1',Material.SUGAR);
        r.setIngredient('0',BasicPotionWand.getItem().getType());
        r.setIngredient('2',Material.FEATHER);
        Main.getInstance().getServer().addRecipe(r);
        Main.getInstance().getServer().getPluginManager().registerEvents(new BasicSpeedWandListener(),Main.getInstance());
    }
}

class BasicSpeedWandListener implements Listener {
    @EventHandler
    public void onCraft(CraftItemEvent event){
        if ((!event.getInventory().contains(BasicPotionWand.getItem())) && event.getRecipe().getResult().isSimilar(BasicSpeedWand.getItem())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onRightclick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        PlayerInventory inv = player.getInventory();
        if (event.getAction() == Action.RIGHT_CLICK_AIR){
            if (inv.contains(BasicSpeedWand.costs.getType(),BasicSpeedWand.costs.getAmount()) && (inv.getItemInMainHand().isSimilar(BasicSpeedWand.getItem()) || inv.getItemInOffHand().isSimilar(BasicSpeedWand.getItem()))){
                inv.removeItem(BasicSpeedWand.costs);
                player.removePotionEffect(BasicSpeedWand.effectType);
                player.addPotionEffect(new PotionEffect(BasicSpeedWand.effectType, BasicSpeedWand.duration*20,BasicSpeedWand.amplifier));
            }
        }
    }
}
