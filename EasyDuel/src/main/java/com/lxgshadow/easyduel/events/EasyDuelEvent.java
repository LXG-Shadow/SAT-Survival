package com.lxgshadow.easyduel.events;


import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class EasyDuelEvent extends Event {
    private static final HandlerList handler = new HandlerList();
    public static HandlerList getHandlerList(){return handler;}
    @Override
    public HandlerList getHandlers() {
        return handler;
    }
}
