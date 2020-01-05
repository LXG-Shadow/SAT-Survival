package com.lxgshadow.easyduel.arena;

import com.lxgshadow.easyduel.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ArenaManager {
    private static HashMap<Integer, Arena> arenas;
//    private static HashMap<Integer, Integer> arenaSize;
//    private static HashMap<UUID,Integer> playerIn;
    private static int id;
    private static int defaultSize;
    public static void initialize(){
        id = 0;
        arenas = new HashMap<>();
        defaultSize = 16;
        Main.getInstance().getServer().getPluginManager().registerEvents(new ArenaListener(),Main.getInstance());
    }

    public static boolean create(Player[] players){
        for (Player p:players){
            if (getArenaId(p) != -1){
                return false;
            }
        }
        // todo: check player distance, check in same world
        //todo: use arenasize
        Arena arena = new Arena(id,defaultSize,1,players);
//        int x =0;
//        int y=0;
//        int z=0;
//        for (Player p:players){
//            x+=p.getLocation().getBlockX();
//            y+=p.getLocation().getBlockY();
//            z+=p.getLocation().getBlockZ();
//        }
//        x = x/players.length;
//        y = y/players.length;
//        z = z/players.length;
//        arenas.put(id,new Location(players[0].getWorld(),x,y,z));
//        arenaSize.put(id,defaultSize);
//        for (Player p:players){
//            playerIn.put(p.getUniqueId(),id);
//        }
        arenas.put(id,arena);
        id ++;
        return true;
    }

    public static void remove(int id){
        arenas.remove(id);
    }

    public static int getArenaId(Player player){
        for (Arena arena:arenas.values()){
            if (arena.havePlayer(player)){
                return arena.getId();
            }
        }
        return -1;
    }

    public static int[] getArena(int id){
        if (arenas.get(id) == null){
            return new int[]{};
        }
        return arenas.get(id).getMargin();
    }
}