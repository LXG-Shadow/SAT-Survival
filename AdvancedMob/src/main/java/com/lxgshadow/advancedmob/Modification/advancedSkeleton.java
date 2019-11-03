package com.lxgshadow.advancedmob.Modification;

import com.lxgshadow.advancedmob.MonWeapon.TheBoneBow;
import com.lxgshadow.advancedmob.MonWeapon.TheStoneSword;
import com.lxgshadow.advancedmob.utils.EtcUtils;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class advancedSkeleton implements Listener {
    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Skeleton){
            if (event.getEntity().getEquipment() != null){
                if (EtcUtils.chance(1,2)){
                    event.getEntity().getEquipment().setItemInMainHand(TheBoneBow.get());
                    event.getEntity().getEquipment().setItemInMainHandDropChance(0.05F);
                }
            }
        }
    }
}
