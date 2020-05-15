package com.lxgshadow.easyduel.mode;

import com.lxgshadow.easyduel.Main;
import com.lxgshadow.easyduel.arena.Arena;
import com.lxgshadow.easyduel.arena.ArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;


public class ArenaModeCommon extends ArenaModeBase {
    private static String id = "common";
    private static String displayname = "决战到死";

    public ArenaModeCommon() {
        super(id, displayname);
    }


    @EventHandler
    public void onDamage(EntityDamageEvent event){
        Arena arena = ArenaManager.getArena(event.getEntity());
        if (super.isSameMode(arena)){
            Player player = (Player) event.getEntity();
            if (player.getHealth() - event.getFinalDamage() < 0) {
                arena.setAlive(player, false);
                arena.updateStatus();
                event.setCancelled(true);
            }
        };
    }



}
