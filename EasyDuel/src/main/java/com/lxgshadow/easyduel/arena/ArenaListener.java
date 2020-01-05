package com.lxgshadow.easyduel.arena;

import com.lxgshadow.easyduel.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class ArenaListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        int aid = ArenaManager.getArenaId(player);
        if (aid == -1){
            return;
        }
        int[] margin = ArenaManager.getArena(aid);
        if (Math.abs(player.getLocation().getBlockX()-margin[0])<3
                || Math.abs(player.getLocation().getBlockX()-margin[2])<3
                || Math.abs(player.getLocation().getBlockZ()-margin[1])<3
                || Math.abs(player.getLocation().getBlockZ()-margin[3])<3){
            showBoarder(player,margin,player.getLocation().getBlockY());
        }
    }

    public void showBoarder(Player player,int[] margin,int y){
        int x1,z1,x2,z2;
        x1 = margin[0];
        z1 = margin[1];
        x2 = margin[2];
        z2 = margin[3];
        for (int x=x1;x<=x2;x++){
            for (int i=0;i<=4;i++){
                player.sendBlockChange(new Location(player.getWorld(),x,y+i,z1), Material.GLASS.createBlockData());
                player.sendBlockChange(new Location(player.getWorld(),x,y+i,z2), Material.GLASS.createBlockData());
            }
        }
        for (int z=z1;z<=z2;z++){
            for (int i=0;i<=4;i++){
                player.sendBlockChange(new Location(player.getWorld(),x1,y+i,z), Material.GLASS.createBlockData());
                player.sendBlockChange(new Location(player.getWorld(),x2,y+i,z), Material.GLASS.createBlockData());
            }
        }

        // return to original state
        // todo: need optimize
        new BukkitRunnable(){
            @Override
            public void run(){
                if (Math.abs(player.getLocation().getBlockX()-margin[0])<3
                        || Math.abs(player.getLocation().getBlockX()-margin[2])<3
                        || Math.abs(player.getLocation().getBlockZ()-margin[1])<3
                        || Math.abs(player.getLocation().getBlockZ()-margin[3])<3){
                    this.cancel();
                    return;
                }
                for (int x=x1;x<=x2;x++){
                    for (int i=0;i<=4;i++){
                        player.sendBlockChange(new Location(player.getWorld(),x,y+i,z1), player.getWorld().getBlockAt(x,y+i,z1).getBlockData());
                        player.sendBlockChange(new Location(player.getWorld(),x,y+i,z2), player.getWorld().getBlockAt(x,y+i,z2).getBlockData());
                    }
                }
                for (int z=z1;z<=z2;z++){
                    for (int i=0;i<=4;i++){
                        player.sendBlockChange(new Location(player.getWorld(),x1,y+i,z), player.getWorld().getBlockAt(x1,y+i,z).getBlockData());
                        player.sendBlockChange(new Location(player.getWorld(),x2,y+i,z), player.getWorld().getBlockAt(x2,y+i,z).getBlockData());
                    }
                }
            }
        }.runTaskLater(Main.getInstance(),20*2);
    }
}
