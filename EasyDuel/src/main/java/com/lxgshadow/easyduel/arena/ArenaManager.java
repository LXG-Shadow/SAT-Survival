package com.lxgshadow.easyduel.arena;

import com.lxgshadow.easyduel.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ArenaManager {
    private static HashMap<Integer, Location> arenas;
    private static HashMap<Integer, Integer> arenaSize;
    private static HashMap<UUID,Integer> playerIn;
    private static int id;
    private static int defaultSize;
    public static void initialize(){
        id = 0;
        arenas = new HashMap<>();
        arenaSize = new HashMap<>();
        playerIn = new HashMap<>();
        defaultSize = 16;
        Main.getInstance().getServer().getPluginManager().registerEvents(new ArenaListener(),Main.getInstance());
    }

    public static boolean create(Player[] players){
        for (Player p:players){
            if (getId(p) != -1){
                return false;
            }
        }
        // todo: check player distance, check in same world

        int x =0;
        int y=0;
        int z=0;
        for (Player p:players){
            x+=p.getLocation().getBlockX();
            y+=p.getLocation().getBlockY();
            z+=p.getLocation().getBlockZ();
        }
        x = x/players.length;
        y = y/players.length;
        z = z/players.length;
        arenas.put(id,new Location(players[0].getWorld(),x,y,z));
        arenaSize.put(id,defaultSize);
        for (Player p:players){
            playerIn.put(p.getUniqueId(),id);
        }
        id ++;
        return true;
    }

    public static void remove(int id){
        arenas.remove(id);
        arenaSize.remove(id);
        for (UUID uuid:playerIn.keySet()){
            if (playerIn.get(uuid) == id){
                playerIn.remove(uuid);
            }
        }
    }

    public static int getId(Player player){
        if (playerIn.get(player.getUniqueId()) == null){
            return -1;
        }
        return playerIn.get(player.getUniqueId());
    }

    public static int[] getArena(int id){
        //todo: use arenasize
        if (arenas.get(id) == null){
            return new int[]{};
        }
        Location center = arenas.get(id);
        int x = center.getBlockX();
        int z = center.getBlockZ();
        return new int[]{x-(defaultSize/2)+1,z-(defaultSize/2)+1,x+(defaultSize/2),z+(defaultSize/2)};
    }
}