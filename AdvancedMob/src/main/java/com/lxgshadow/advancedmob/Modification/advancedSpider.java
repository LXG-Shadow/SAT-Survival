package com.lxgshadow.advancedmob.Modification;

import com.lxgshadow.advancedmob.utils.EtcUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class advancedSpider implements Listener{
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Spider & event.getEntity() instanceof LivingEntity){
            if (EtcUtils.chance(1,6)){
                ((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,EtcUtils.randInt(1,5)*20,EtcUtils.randInt(0,2)),true);
            }
            if (EtcUtils.chance(5,7)){
                event.getEntity().getWorld().getBlockAt(event.getEntity().getLocation()).setType(Material.COBWEB);
            }
        }
    }
}
