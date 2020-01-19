package com.lxgshadow.easyduel.arena;

import com.lxgshadow.easyduel.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;


public class ArenaListener implements Listener {
    public ArenaListener(){
        new BukkitRunnable(){
            @Override
            public void run(){
                for (Arena arena:ArenaManager.getAllArena()){
                    for (Player player:arena.getPlayers()){
                        int[] margin = arena.getMargin();
                        if (Math.abs(player.getLocation().getBlockX()-margin[0])<3
                                || Math.abs(player.getLocation().getBlockX()-margin[2])<3
                                || Math.abs(player.getLocation().getBlockZ()-margin[1])<3
                                || Math.abs(player.getLocation().getBlockZ()-margin[3])<3){
                            arena.showBoarder(player,player.getLocation().getBlockY());
                        }else {
                            arena.hideBoarder(player);
                        }
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(),0,1);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        int aid = ArenaManager.getArenaId(player);
        if (aid == -1){
            return;
        }
        Arena arena = ArenaManager.getArena(aid);
        arena.update(event);
    }

    @EventHandler
    public void teleport(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        int aid = ArenaManager.getArenaId(player);
        if (aid == -1){
            return;
        }
        Arena arena = ArenaManager.getArena(aid);
        arena.update(event);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        int aid = ArenaManager.getArenaId(event.getPlayer());
        if (aid == -1){
            return;
        }
        Arena arena = ArenaManager.getArena(aid);
        if (!arena.isAlive(event.getPlayer())){
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if (!(event.getEntity() instanceof Player)){return;}
        Player player = (Player) event.getEntity();
        int aid = ArenaManager.getArenaId(player);
        if (aid == -1){
            return;
        }
        Arena arena = ArenaManager.getArena(aid);
        Entity damager = event.getDamager();
        if (damager instanceof Player && !arena.isAlive((Player) damager)){
            event.setCancelled(true);
            return;
        }
        if (damager instanceof Projectile && ((Projectile) damager).getShooter() instanceof Player){
            if (!arena.isAlive((Player)((Projectile) damager).getShooter())){
                event.setCancelled(true);
                return;
            }
        }
        arena.update(event);
    }

    @EventHandler
    public void onDamage2(EntityDamageEvent event){
        if (!(event.getEntity() instanceof Player)){return;}
        Player player = (Player) event.getEntity();
        int aid = ArenaManager.getArenaId(player);
        if (aid == -1){
            return;
        }
        Arena arena = ArenaManager.getArena(aid);
        arena.update(event);
    }
}
