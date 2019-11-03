package com.lxgshadow.advancedmob.Modification;

import com.lxgshadow.advancedmob.Main;
import com.lxgshadow.advancedmob.utils.EtcUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class commonMonster implements Listener {
    EntityType[] potionEnhancementList = {EntityType.CREEPER,EntityType.ZOMBIE,EntityType.SKELETON,EntityType.SPIDER,EntityType.CAVE_SPIDER,
            EntityType.WITCH};

    @EventHandler
    public void potionEnhancement(CreatureSpawnEvent event) {
        if (Arrays.asList(potionEnhancementList).contains(event.getEntityType())){
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
        }
    }
}
