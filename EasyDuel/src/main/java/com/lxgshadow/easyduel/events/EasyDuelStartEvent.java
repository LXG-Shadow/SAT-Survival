package com.lxgshadow.easyduel.events;

import com.lxgshadow.easyduel.arena.Arena;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EasyDuelStartEvent extends EasyDuelEvent implements Cancellable {
    private Arena arena;
    private boolean cancelled = false;

    public EasyDuelStartEvent(Arena arena){
        this.arena = arena;
    }

    public Arena getArena() {
        return arena;
    }


    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled =b;
    }
}
