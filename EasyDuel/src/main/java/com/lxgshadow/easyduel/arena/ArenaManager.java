package com.lxgshadow.easyduel.arena;

import com.lxgshadow.easyduel.Config;
import com.lxgshadow.easyduel.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
public class ArenaManager {
    private static HashMap<Integer, Arena> arenas;
    private static int id;
    public static void initialize(){
        id = 0;
        arenas = new HashMap<>();
        Main.getInstance().getServer().getPluginManager().registerEvents(new ArenaListener(),Main.getInstance());
    }

    public static boolean create(ArrayList<Player> players, ArrayList<Integer> team, int mode, int size){
        for (Player p:players){
            if (getArenaId(p) != -1){
                return false;
            }
        }

        if (ArenaModes.getName(mode) == null){return false;}
        Arena arena = new Arena(id,size,mode,players,team);
        // check player in the arena size
        if (arena.getPlayerOutOfBoarder().size()>0){
            return false;
        }
        arenas.put(id,arena);
        id ++;
        arena.sendStartInfo();
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