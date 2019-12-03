package com.lxgshadow.advancedmob.Modification;

import com.lxgshadow.advancedmob.utils.EtcUtils;
import org.bukkit.Material;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class advancedEnderman implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Enderman & event.getEntity() instanceof Player){
            if (EtcUtils.chance(1,1)){
                ItemStack dropped = ((Player) event.getEntity()).getInventory().getItemInMainHand();
                if (dropped.getType() == Material.AIR){return;}
                event.getEntity().getWorld().dropItemNaturally(event.getDamager().getLocation(),dropped);
                ((Player) event.getEntity()).getInventory().remove(dropped);
            }
        }
    }
}
