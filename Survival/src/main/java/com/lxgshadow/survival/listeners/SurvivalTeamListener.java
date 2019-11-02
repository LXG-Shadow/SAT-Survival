package com.lxgshadow.survival.listeners;

import com.lxgshadow.survival.events.playerTpaEvent;
import com.lxgshadow.survival.models.SurvivalTeam;
import com.lxgshadow.survival.managers.TeamsManager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class SurvivalTeamListener implements Listener {

    @EventHandler
    public void teammateDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player victim = (Player) event.getEntity();
        Player murder;
        if (event.getDamager() instanceof Projectile) {
            if (!(((Projectile) event.getDamager()).getShooter() instanceof Player)) {
                return;
            }
            murder = (Player) ((Projectile) event.getDamager()).getShooter();
        } else {
            if (!(event.getDamager() instanceof Player)) {
                return;
            }
            murder = (Player) event.getDamager();
        }
        SurvivalTeam t = TeamsManager.get(victim);
        if (t != null){
            if (t.containsTeammate(murder) && !(t.getSetting().get("teammateDamage"))){
                event.setCancelled(true);
                return;
            }
        }
        event.setCancelled(false);
    }

    @EventHandler(ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        if (TeamsManager.get(p) != null){
            TeamsManager.quit(p);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onQuit(playerTpaEvent event) {
        if (TeamsManager.inSameTeam(event.getTpdPlayer(),event.getTargetPlayer())){
            event.setSkippable(true);
        }
    }
}
