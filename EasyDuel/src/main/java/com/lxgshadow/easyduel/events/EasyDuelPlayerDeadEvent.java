package com.lxgshadow.easyduel.events;

import com.lxgshadow.easyduel.arena.Arena;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class EasyDuelPlayerDeadEvent extends EasyDuelEvent {
    private Arena arena;
    private Player player;
    private Entity damager;

    public EasyDuelPlayerDeadEvent(Arena arena, Player player, Entity damager){
        this.arena = arena;
        this.player = player;
        this.damager = damager;
    }

    public EasyDuelPlayerDeadEvent(Arena arena, Player player){
        this.arena = arena;
        this.player = player;
        this.damager = null;
    }

    public Arena getArena() {
        return arena;
    }

    public Player getPlayer(){
        return this.player;
    }

    public Entity getDamager() {
        return damager;
    }
}
