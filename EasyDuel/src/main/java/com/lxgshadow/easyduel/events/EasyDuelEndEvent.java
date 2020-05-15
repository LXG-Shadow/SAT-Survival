package com.lxgshadow.easyduel.events;

import com.lxgshadow.easyduel.arena.Arena;
import com.lxgshadow.easyduel.arena.ArenaTeam;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class EasyDuelEndEvent extends EasyDuelEvent{
    private Arena arena;

    public EasyDuelEndEvent(Arena arena){
        this.arena = arena;
    }

    public Arena getArena() {
        return arena;
    }


    public ArenaTeam getWinnerTeam(){
        for (ArenaTeam at:arena.getTeams()){
            if (at.isWin()){
                return at;
            }
        }
        return null;
    }
}