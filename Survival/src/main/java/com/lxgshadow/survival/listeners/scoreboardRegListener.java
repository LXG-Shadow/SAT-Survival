package com.lxgshadow.survival.listeners;

import com.lxgshadow.survival.Main;
import com.lxgshadow.survival.managers.SurvivalSBManager;
import com.lxgshadow.survival.models.PlayerScoreboard;
import com.lxgshadow.survival.scoreboard.SurvivalScoreboard;
import com.lxgshadow.survival.scoreboard.sideBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.HashMap;

public class scoreboardRegListener implements Listener {
    private JavaPlugin plugin;
    public scoreboardRegListener(JavaPlugin p){this.plugin = p;}
    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event){
        PlayerScoreboard sb = SurvivalSBManager.create(event.getPlayer());
    }
    @EventHandler(ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent event){
        SurvivalSBManager.remove(event.getPlayer());
    }
}
