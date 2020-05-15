package com.lxgshadow.easyduel.mode;

import com.lxgshadow.easyduel.arena.Arena;
import com.lxgshadow.easyduel.arena.ArenaManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ArenaModeFirstTouch extends ArenaModeBase {
    private static String id = "firsttouch";
    private static String displayname = "一击定胜负";

    public ArenaModeFirstTouch() {
        super(id, displayname);
    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Arena arena = ArenaManager.getArena(event.getEntity());
        if (super.isSameMode(arena)) {
            Player player = (Player) event.getEntity();
            Entity damager = event.getDamager();
            if (damager instanceof Player && arena.havePlayer((Player) damager) && event.getFinalDamage() > 0) {
                arena.setAlive(player, false);
                event.setCancelled(true);
            }
            if (damager instanceof Projectile && ((Projectile) damager).getShooter() instanceof Player
                    && arena.havePlayer((Player) ((Projectile) damager).getShooter())
                    && ((EntityDamageByEntityEvent) damager).getFinalDamage() > 0) {
                arena.setAlive(player, false);
                event.setCancelled(true);
            }
            arena.updateStatus();
        }
    }

}
