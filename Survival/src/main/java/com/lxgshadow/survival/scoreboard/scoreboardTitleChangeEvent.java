package com.lxgshadow.survival.scoreboard;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class scoreboardTitleChangeEvent extends Event {
    private static final HandlerList handler = new HandlerList();
    private scoreboards sb;
    public String oldtitle;
    public String newtitle;
    private boolean cancelled = false;

    public scoreboardTitleChangeEvent(scoreboards sb,String oldtitle, String newtitle){
        this.sb = sb;
        this.oldtitle= oldtitle;
        this.newtitle = newtitle;
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
