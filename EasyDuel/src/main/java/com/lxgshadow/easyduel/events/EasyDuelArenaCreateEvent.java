package com.lxgshadow.easyduel.events;

import com.lxgshadow.easyduel.arena.Arena;
import org.bukkit.event.Cancellable;

public class EasyDuelArenaCreateEvent extends EasyDuelEvent implements Cancellable {
    private Arena arena;
    private boolean cancelled = false;

    public EasyDuelArenaCreateEvent(Arena arena){
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