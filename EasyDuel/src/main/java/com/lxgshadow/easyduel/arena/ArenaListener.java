package com.lxgshadow.easyduel.arena;

import com.lxgshadow.easyduel.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;


public class ArenaListener implements Listener {
    public ArenaListener(){
        new BukkitRunnable(){
            @Override
            public void run(){
                for (Arena arena:ArenaManager.getAllArena()){
                    for (UUID uuid:arena.getPlayers()){
                        Player player = Main.getInstance().getServer().getPlayer(uuid);
                        int[] margin = arena.getMargin();
                        if (Math.abs(player.getLocation().getBlockX()-margin[0])<3
                                || Math.abs(player.getLocation().getBlockX()-margin[2])<3
                                || Math.abs(player.getLocation().getBlockZ()-margin[1])<3
                                || Math.abs(player.getLocation().getBlockZ()-margin[3])<3){
//                            if (showing.get(uuid) == 1){
//                                continue;
//                            }
                            showBoarder(player,arena,player.getLocation().getBlockY());
                        }else {
                            hideBoarder(player,arena);
                        }
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(),0,1);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        int aid = ArenaManager.getArenaId(player);
        if (aid == -1){
            return;
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if (!(event.getEntity() instanceof Player)){return;}
        Player player = (Player) event.getEntity();
        int aid = ArenaManager.getArenaId(player);
        if (aid == -1){
            return;
        }
        Arena arena = ArenaManager.getArena(aid);
        int mode = arena.getMode();
        if (mode == 1){
            if (player.getHealth() - event.getFinalDamage() < 0){

            }
        }
    }

    public void showBoarder(Player player,Arena arena,int y){
        int x1,z1,x2,z2;
        x1 = arena.getMargin()[0];
        z1 = arena.getMargin()[1];
        x2 = arena.getMargin()[2];
        z2 = arena.getMargin()[3];
        HashSet<Location> showing = arena.getShowing().get(player.getUniqueId());
        for (int x=x1;x<=x2;x++){
            for (int i=0;i<=4;i++){
                showing.add(new Location(player.getWorld(),x,y+i,z1));
                showing.add(new Location(player.getWorld(),x,y+i,z2));
            }
        }
        for (int z=z1;z<=z2;z++){
            for (int i=0;i<=4;i++){
                showing.add(new Location(player.getWorld(),x1,y+i,z));
                showing.add(new Location(player.getWorld(),x2,y+i,z));
            }
        }
        for (Location l:showing){
            player.sendBlockChange(l, Material.GLASS.createBlockData());
        }
    }

    public void hideBoarder(Player player,Arena arena){
        HashSet<Location> showing = arena.getShowing().get(player.getUniqueId());
        for (Location l:showing){
            player.sendBlockChange(l, player.getWorld().getBlockAt(l).getBlockData());
        }
        showing.clear();
    }
}
