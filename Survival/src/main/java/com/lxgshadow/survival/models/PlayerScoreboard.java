package com.lxgshadow.survival.models;

import com.lxgshadow.survival.Config;
import com.lxgshadow.survival.Main;
import com.lxgshadow.survival.scoreboard.SurvivalScoreboard;
import com.lxgshadow.survival.scoreboard.sideBar;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class PlayerScoreboard {
    private UUID player;
    private SurvivalScoreboard sb;
    private sideBar sdb;
    public PlayerScoreboard(JavaPlugin plugin,Player p){
        this.player = p.getUniqueId();
        sb = new SurvivalScoreboard(plugin,p);
        HashMap<Integer,String> textList = new HashMap<>();
        textList.put(2,"Username: "+p.getName());
        textList.put(3,"Time: waiting for update...");
        textList.put(1,"Ping: " + ((CraftPlayer) plugin.getServer().getPlayer(player)).getHandle().ping);
        sdb = new sideBar(p,"sidebar","dummy", Config.scoreboard_title,textList);
        sb.register(sdb);
    }

    public UUID getPlayer() {
        return player;
    }

    public void update(){
        sdb.setText(3,"Time: "+new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date()));
        sdb.setText(1,"Ping: " + ((CraftPlayer) Main.getInstance().getServer().getPlayer(player)).getHandle().ping);
    }
}
