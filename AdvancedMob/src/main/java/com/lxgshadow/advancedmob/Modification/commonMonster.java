package com.lxgshadow.advancedmob.Modification;

import com.lxgshadow.advancedmob.utils.EtcUtils;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class commonMonster implements Listener {
    EntityType[] potionEnhancementList = {EntityType.CREEPER,EntityType.ZOMBIE,EntityType.SKELETON,EntityType.SPIDER,EntityType.CAVE_SPIDER,
            EntityType.WITCH};

    EntityType[] mitosisList = {EntityType.ZOMBIE,EntityType.SKELETON,EntityType.SPIDER,EntityType.CAVE_SPIDER};

    @EventHandler
    public void potionEnhancement(CreatureSpawnEvent event) {
        if (Arrays.asList(potionEnhancementList).contains(event.getEntityType())){
            event.getEntity().addScoreboardTag("commonModification");
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

    // remove the effect when creeper explode, to cancel the explode
    @EventHandler
    public void onExplode(EntityExplodeEvent event){
        if (event.getEntityType() == EntityType.CREEPER){
            if (event.getEntity().getScoreboardTags().contains("commonModification")){
                ((LivingEntity) event.getEntity()).getActivePotionEffects().forEach((e)->((LivingEntity) event.getEntity()).removePotionEffect(e.getType()));
            }
        }
    }

    // Monster Mitosis
    @EventHandler
    public void getDamage(EntityDamageByEntityEvent event){
        if (Arrays.asList(mitosisList).contains(event.getEntityType()) && (event.getDamager() instanceof Player)){
            if (EtcUtils.chance(1,50)){
                event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(),event.getEntityType());
            }
        }
    }
}
