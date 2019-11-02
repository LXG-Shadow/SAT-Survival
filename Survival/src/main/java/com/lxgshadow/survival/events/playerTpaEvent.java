package com.lxgshadow.survival.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class playerTpaEvent extends Event {
    private static final HandlerList handler = new HandlerList();
    private Player tpdPlayer;
    private Player targetPlayer;
    private boolean cancelled = false;
    private boolean skippable = false;

    public playerTpaEvent(Player p1,Player p2){
        this.tpdPlayer = p1;
        this.targetPlayer = p2;

    }
    @Override
    public HandlerList getHandlers() {
        return handler;
    }

    public void setCancelled(boolean b){
        this.cancelled = b;
    }

    public void setSkippable(boolean b){this.skippable = b;}

    public boolean isCancelled(){
        return this.cancelled;
    }
    public boolean isSkippable(){
        return this.skippable;
    }

    public Player getTpdPlayer(){return this.tpdPlayer;}
    public Player getTargetPlayer(){return this.targetPlayer;}

    public static HandlerList getHandlerList(){return handler;}
}
