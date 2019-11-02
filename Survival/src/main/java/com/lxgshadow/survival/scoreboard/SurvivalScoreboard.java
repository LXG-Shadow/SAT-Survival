package com.lxgshadow.survival.scoreboard;

import com.lxgshadow.survival.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import javax.jws.Oneway;
import java.util.ArrayList;
import java.util.UUID;

public class SurvivalScoreboard implements Listener {
    private Scoreboard scoreboard;
    private UUID player;

    public SurvivalScoreboard(JavaPlugin plugin, Player p) {
        scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard();
        player = p.getUniqueId();
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    public void register(scoreboards sb) {
        Objective objective = scoreboard.registerNewObjective(sb.getName(), sb.getCriteria(), sb.getTitle());
        objective.setDisplaySlot(sb.getDisplaySlot());
        sb.getTextList().forEach((index, text) -> objective.getScore(text).setScore(index));
        Main.getInstance().getServer().getPlayer(player).setScoreboard(scoreboard);
    }

    @EventHandler
    public void onTitleUpdate(scoreboardTitleChangeEvent event) {
        if (!event.getSb().getPlayer().equals(player)) {
            return;
        }
        Objective objective = scoreboard.getObjective(event.getSb().getName());
        if (objective == null || event.newtitle == null) {
            event.setCancelled(true);
            return;
        }
        objective.setDisplayName(event.newtitle);
    }

    @EventHandler
    public void onTextUpdate(scoreboardTextChangeEvent event) {
        if (!event.getSb().getPlayer().equals(player)) {
            return;
        }
        Objective objective = scoreboard.getObjective(event.getSb().getName());
        if (objective == null || event.newtext == null) {
            event.setCancelled(true);
            return;
        }
        if (event.oldtext != null) {
            scoreboard.resetScores(event.oldtext);
        }
        objective.getScore(event.newtext).setScore(event.value);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (event.getPlayer().getUniqueId().equals(player)) {
            HandlerList.unregisterAll(this);
        }
    }
}
