package com.lxgshadow.survival.listeners;

import com.lxgshadow.survival.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class deathMessageListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        player.sendMessage(Messages.death_location.replace("%x",player.getLocation().getBlockX()+"")
                .replace("%y",player.getLocation().getBlockY()+"")
                .replace("%z",player.getLocation().getBlockZ()+""));
    }
}
