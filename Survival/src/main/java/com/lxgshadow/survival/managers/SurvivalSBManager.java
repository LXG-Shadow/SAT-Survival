package com.lxgshadow.survival.managers;

import com.lxgshadow.survival.Main;
import com.lxgshadow.survival.models.PlayerScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SurvivalSBManager {
    private static List<PlayerScoreboard> scoreboards = new ArrayList<>();
    private static BukkitRunnable runnable;

    public static void initialize(){
        Main.getInstance().getServer().getOnlinePlayers().forEach(SurvivalSBManager::create);
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                scoreboards.forEach(PlayerScoreboard::update);
            }
        };
        runnable.runTaskTimer(Main.getInstance(),0,10*20);
    }
    public static PlayerScoreboard create(Player p){
        PlayerScoreboard sb = new PlayerScoreboard(Main.getInstance(),p);
        scoreboards.add(sb);
        return sb;
    }
    public static PlayerScoreboard get(Player p){
        for (PlayerScoreboard sb:scoreboards){
            if (sb.getPlayer().equals(p.getUniqueId())){
                return sb;
            }
        }
        return null;
    }
    public static void remove(Player p){
        if (get(p) == null){return;}
        scoreboards.remove(get(p));
    }
}
