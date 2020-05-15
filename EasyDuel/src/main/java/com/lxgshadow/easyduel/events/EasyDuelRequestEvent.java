package com.lxgshadow.easyduel.events;

import com.lxgshadow.easyduel.Messages;
import com.lxgshadow.easyduel.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class EasyDuelRequestEvent extends EasyDuelEvent implements Cancellable {

    private Player requester;
    private Player target;
    private boolean cancelled = false;
    private String msg;

    public EasyDuelRequestEvent(Player player,Player target){
        this.requester = player;
        this.target = target;
        this.msg = "";
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled =b;
    }

    public Player getRequester() {
        return requester;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
