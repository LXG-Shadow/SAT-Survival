package com.lxgshadow.survival.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class AntiExplosionListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onExplosion(EntityExplodeEvent event){
        if (event.getEntityType() == EntityType.CREEPER){
            event.blockList().clear();
        }
    }
}
