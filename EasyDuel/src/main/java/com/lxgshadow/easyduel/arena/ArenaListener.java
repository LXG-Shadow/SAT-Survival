package com.lxgshadow.easyduel.arena;

import com.lxgshadow.easyduel.Main;
import com.lxgshadow.easyduel.Messages;
import com.lxgshadow.easyduel.events.EasyDuelArenaCreateEvent;
import com.lxgshadow.easyduel.events.EasyDuelStartEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
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
    // 如果要重写的话，就把priority调高
    @EventHandler(ignoreCancelled = true)
    public void onduelstart(EasyDuelArenaCreateEvent event){
        Arena arena = event.getArena();
        // check if player are in other match.
        for (Player p:arena.getPlayers()){
            if (ArenaManager.getArenaId(p) != -1){
                arena.addErrorMsg(Messages.arena_error_PlayerExist.replace("%p",p.getDisplayName()));
                event.setCancelled(true);
                return;
            }
        }
        // check player in the arena size
        if (arena.getPlayerOutOfBoarder().size()>0){
            arena.addErrorMsg(Messages.arena_error_PlayerOutOfBoarder);
            event.setCancelled(true);
            return;
        }
        // check if two or more team have same player
        if (ArenaTeam.HaveSamePlayer(arena.getTeams())){
            arena.addErrorMsg(Messages.arena_error_PlayerInDifferentTeam);
            event.setCancelled(true);
            return;
        }
        // check if mode exist
        if (arena.getMode() == null){
            arena.addErrorMsg(Messages.arena_error_Mode404);
            event.setCancelled(true);
            return;
        }
    }
}
