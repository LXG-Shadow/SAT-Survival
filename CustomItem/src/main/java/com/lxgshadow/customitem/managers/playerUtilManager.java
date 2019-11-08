package com.lxgshadow.customitem.managers;

import com.lxgshadow.customitem.Main;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.time.LocalTime;
import java.util.*;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class playerUtilManager {
    static HashSet<UUID> playerPreventMove;
    static HashSet<UUID> playerDisableKnockBack;
    public static void initialize(){
        playerPreventMove = new HashSet<>();
        playerDisableKnockBack = new HashSet<>();
        Main.getInstance().getServer().getPluginManager().registerEvents(new playerUtilManagerListener(),Main.getInstance());
    }

    public static void addPreventMove(Player player){
        playerPreventMove.add(player.getUniqueId());
    }

    public static void removePreventMove(Player player){
        playerPreventMove.remove(player.getUniqueId());
    }

    public static void addPreventKB(Player player){
        playerDisableKnockBack.add(player.getUniqueId());
    }

    public static void removePreventKB(Player player){
        playerDisableKnockBack.remove(player.getUniqueId());
    }
}

class playerUtilManagerListener implements Listener{
    @EventHandler(priority = EventPriority.HIGH)
    public void onMove(PlayerMoveEvent event){
        if (playerUtilManager.playerPreventMove.contains(event.getPlayer().getUniqueId())){
            Location l1 = event.getFrom();
            Location l2 = event.getTo();
            if (l1.getX() != l2.getX() || l1.getY() != l2.getY() || l1.getZ() != l2.getZ()){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player && playerUtilManager.playerDisableKnockBack.contains(event.getEntity().getUniqueId())){
            event.getEntity().sendMessage("123");
            event.getEntity().setVelocity(event.getEntity().getVelocity().multiply(-1));
        }
    }
}
