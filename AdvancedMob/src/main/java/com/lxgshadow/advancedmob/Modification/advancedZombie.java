package com.lxgshadow.advancedmob.Modification;

import com.lxgshadow.advancedmob.MonWeapon.TheStoneSword;
import com.lxgshadow.advancedmob.utils.EtcUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class advancedZombie implements Listener {
    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Zombie){

            if (event.getEntity().getEquipment() != null){
                if (EtcUtils.chance(1,5)){
                    event.getEntity().getEquipment().setItemInMainHand(TheStoneSword.get());
                    event.getEntity().getEquipment().setItemInMainHandDropChance(0.05F);
                }
                if (EtcUtils.chance(1,5)){
                    event.getEntity().getEquipment().setItemInOffHand(TheStoneSword.get());
                    event.getEntity().getEquipment().setItemInOffHandDropChance(0.05F);
                }
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Zombie & event.getEntity() instanceof LivingEntity){
            if (EtcUtils.chance(1,10)){((LivingEntity) event.getEntity())
                    .addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,EtcUtils.randInt(1,5)*20,EtcUtils.randInt(0,4)),true);}
            if (EtcUtils.chance(1,10)){((LivingEntity) event.getEntity())
                    .addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,EtcUtils.randInt(1,5)*20,EtcUtils.randInt(0,2)),true);}
            if (EtcUtils.chance(1,10)){((LivingEntity) event.getEntity())
                    .addPotionEffect(new PotionEffect(PotionEffectType.POISON,EtcUtils.randInt(1,5)*20,EtcUtils.randInt(0,2)),true);}
        }
    }
}
