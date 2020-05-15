package com.lxgshadow.easyduel.arena;

import com.lxgshadow.easyduel.Main;
import com.lxgshadow.easyduel.Messages;
import com.lxgshadow.easyduel.events.EasyDuelStartEvent;
import com.lxgshadow.easyduel.mode.ArenaMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
public class ArenaManager {
    private static HashMap<Integer, Arena> arenas;
    private static int id;
    public static void initialize(){
        id = 0;
        arenas = new HashMap<>();

        Main.getInstance().getServer().getPluginManager().registerEvents(new ArenaListener(),Main.getInstance());

        new BukkitRunnable(){
            @Override
            public void run(){
                for (Arena arena:ArenaManager.getAllArena()){
                    for (Player player:arena.getPlayers()){
                        int[] margin = arena.getMargin();
                        if (Math.abs(player.getLocation().getBlockX()-margin[0])<3
                                || Math.abs(player.getLocation().getBlockX()-margin[2])<3
                                || Math.abs(player.getLocation().getBlockZ()-margin[1])<3
                                || Math.abs(player.getLocation().getBlockZ()-margin[3])<3){
                            arena.showBoarder(player,player.getLocation().getBlockY());
                        }else {
                            arena.hideBoarder(player);
                        }
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(),0,1);
    }

    public static Arena create(Iterable<ArenaTeam> teams, ArenaProperties properties,ArenaMode mode){
        id ++;
        Arena arena = new Arena(id,properties,mode,teams);
        // arena create and duel start
        EasyDuelStartEvent event = new EasyDuelStartEvent(arena);
        Main.getInstance().getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()){
            return arena;
        }
        arenas.put(id,arena);
        arena.start();
        arena.sendStartInfo();
        return arena;
    }

    public static Arena[] getAllArena(){
        Arena[] as = new Arena[arenas.values().size()];
        int index = 0;
        for (Arena arena:arenas.values()){
            as[index] = arena;
            index++;
        }
        return as;
    }

    public static void remove(int id){
        arenas.remove(id).stop();
    }

    public static int getArenaId(Player player){
        for (Arena arena:arenas.values()){
            if (arena.havePlayer(player)){
                return arena.getId();
            }
        }
        return -1;
    }

    public static Arena getArena(int id){
        return arenas.get(id);
    }

    public static Arena getArena(Player player){
        int aid = ArenaManager.getArenaId(player);
        if (aid == -1){
            return null;
        }
        return getArena(aid);
    }

    public static Arena getArena(Entity entity){
        if (entity instanceof Player){
            return getArena((Player) entity);
        }
        return null;
    }

}