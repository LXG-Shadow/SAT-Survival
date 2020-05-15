package com.lxgshadow.easyduel.mode;

import com.lxgshadow.easyduel.Main;
import com.lxgshadow.easyduel.arena.Arena;
import com.lxgshadow.easyduel.arena.ArenaManager;
import com.lxgshadow.easyduel.events.EasyDuelPlayerDeadEvent;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ArenaModeBase extends ArenaMode {

    public ArenaModeBase(String id, String displayname) {
        super(id, displayname);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Arena arena = ArenaManager.getArena(event.getPlayer());
        if (super.isSameMode(arena)){
            Player player = event.getPlayer();
            assert arena != null;
            if (!arena.isAlive(player)) {
                return;
            }
            if (arena.checkPlayerOutOfBoarder(player)) {
                arena.setAlive(player, false);
                player.teleport(arena.getCenter());
                arena.updateStatus();
            }

        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Arena arena = ArenaManager.getArena(event.getPlayer());
        if (this.isSameMode(arena)) {
            assert arena != null;
            if (!arena.isAlive(event.getPlayer())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDeadInArena(EasyDuelPlayerDeadEvent event){
        // set dead player health to max
        Player player = event.getPlayer();
        event.getPlayer().setHealth(event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        Arena arena = event.getArena();
        for (Player p1 : arena.getPlayers()) {
            player.showPlayer(Main.getInstance(),p1);
            // vanish died player in alive people
            if (arena.isAlive(p1)) {
                p1.hidePlayer(Main.getInstance(),player);
            }
        }

    }
}
