package com.lxgshadow.easyduel.events;

import com.lxgshadow.easyduel.arena.Arena;
import org.bukkit.event.HandlerList;

public class EasyDuelArenaTimeChangeEvent extends EasyDuelEvent {
    private Arena arena;

    public EasyDuelArenaTimeChangeEvent(Arena arena){
        this.arena = arena;
    }

    public Arena getArena() {
        return arena;
    }

    public int getTimeInTick(){
        return this.arena.getTimeInTick();
    }

    public int getTimeInSecs(){
        return this.arena.getTimeInTick() / 20;
    }
}
