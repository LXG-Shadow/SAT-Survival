package com.lxgshadow.easyduel.arena;

import com.lxgshadow.easyduel.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
public class ArenaManager {
    private static HashMap<Integer, Arena> arenas;
    private static int id;
    private static int defaultSize;
    public static void initialize(){
        id = 0;
        arenas = new HashMap<>();
        defaultSize = 16;
        Main.getInstance().getServer().getPluginManager().registerEvents(new ArenaListener(),Main.getInstance());
    }

    public static boolean create(ArrayList<Player> players, ArrayList<Integer> team, int mode, int size){
        for (Player p:players){
            if (getArenaId(p) != -1){
                return false;
            }
        }
        // todo: check player distance, check in same world
        // todo: use arenasize

        Arena arena = new Arena(id,size,mode,players,team);
        if (arena.getPlayerOutOfBoarder().size()>0){
            return false;
        }
        arenas.put(id,arena);
        id ++;
        return true;
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

    public static Arena getArena(int id){
        return arenas.get(id);
    }
}