package com.lxgshadow.survival.listeners;

import com.lxgshadow.survival.Main;
import com.lxgshadow.survival.commands.vanishCommands;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class vanishListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event){
        vanishCommands.getVanishList().forEach((uuid -> event.getPlayer().hidePlayer(Main.getInstance(),Main.getInstance().getServer().getPlayer(uuid))));
    }

    @EventHandler(ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent event){
        vanishCommands.getVanishList().remove(event.getPlayer().getUniqueId());
    }
}
