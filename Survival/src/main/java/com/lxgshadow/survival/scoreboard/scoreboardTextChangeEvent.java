package com.lxgshadow.survival.scoreboard;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class scoreboardTextChangeEvent extends Event {
    private static final HandlerList handler = new HandlerList();
    private scoreboards sb;
    public String newtext;
    public int value;
    public String oldtext;
    private boolean cancelled = false;

    public scoreboardTextChangeEvent(scoreboards sb,int value,String oldtext, String newtext){
        this.sb = sb;
        this.value = value;
        this.newtext= newtext;
        this.oldtext = oldtext;
    }

    public scoreboards getSb() {
        return sb;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
    @Override
    public HandlerList getHandlers() {
        return handler;
    }
    public static HandlerList getHandlerList(){return handler;}
}
