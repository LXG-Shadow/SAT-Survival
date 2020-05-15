package com.lxgshadow.easyduel.mode;

import com.lxgshadow.easyduel.Main;
import com.lxgshadow.easyduel.arena.Arena;
import com.lxgshadow.easyduel.arena.ArenaManager;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class ArenaMode implements Listener {
    private String id;
    private String displayname;

    public ArenaMode(String id,String displayname){
        this.id = id;
        this.displayname = displayname;
    }

    public String getId(){
        return this.id;
    };
    public String getDisplayname(){
        return this.displayname;
    };
    public void setDisplayname(String dn){
        this.displayname = dn;
    }

    public boolean isSameMode(Arena arena){
        if (arena == null){
            return false;
        }
        return arena.getMode().getId().equals(this.id);
    }
}
