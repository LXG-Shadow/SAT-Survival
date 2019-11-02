package com.lxgshadow.advancedmob.Modification;

import com.lxgshadow.advancedmob.Main;
import com.lxgshadow.advancedmob.MonWeapon.TheStoneSword;
import com.lxgshadow.advancedmob.utils.EtcUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class advancedZombie implements Listener {
    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Zombie){
            if (EtcUtils.chance(1,2)){
                event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,((int)Math.pow(2,10))*20,EtcUtils.randInt(0,2)));
            }
            if (EtcUtils.chance(1,2)){
                event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,((int)Math.pow(2,10))*20,EtcUtils.randInt(0,2)));
            }
            if (EtcUtils.chance(1,2)){
                event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,((int)Math.pow(2,10))*20,EtcUtils.randInt(0,2)));
            }
            if (EtcUtils.chance(1,2)){
                event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,((int)Math.pow(2,10))*20,EtcUtils.randInt(0,2)));
            }
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

}
