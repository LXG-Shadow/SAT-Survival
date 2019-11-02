package com.lxgshadow.customitem.vehicle;

import com.lxgshadow.customitem.Config;
import com.lxgshadow.customitem.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class carBoat {
    private static ItemStack item;
    private static String name = "Car Boat Key";
    private static String[] lores = {
            "Description: Let a boat move like a car"
    };
    public static ItemStack getItem(){return item;}
    public static void createRecipe() {
        item = new ItemStack(Material.TRIPWIRE_HOOK, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY,1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(), name.replace(" ", "_").toLowerCase()), item);
        r.shape("212", "101", "212");
        r.setIngredient('2', Material.IRON_INGOT);
        r.setIngredient('1', Material.REDSTONE);
        r.setIngredient('0', Material.TRIPWIRE_HOOK);
        Main.getInstance().getServer().addRecipe(r);
        Main.getInstance().getServer().getPluginManager().registerEvents(new carBoatListener(), Main.getInstance());
    }
}

class carBoatListener implements Listener{
    @EventHandler
    public void onMove(VehicleMoveEvent event){
        if (!(event.getVehicle() instanceof Boat) || !(event.getVehicle().isOnGround())){
            return;
        }
        for (Entity entity:event.getVehicle().getPassengers()){
            if (entity instanceof Player){
                if (((Player) entity).getInventory().getItemInMainHand().isSimilar(carBoat.getItem()) || ((Player) entity).getInventory().getItemInOffHand().isSimilar(carBoat.getItem())){
                    event.getVehicle().setVelocity(entity.getVelocity().multiply(Config.carboat_speed));
                }
            }
        }
    }
}


