package com.lxgshadow.easyduel.arena;

import com.lxgshadow.easyduel.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;


/***
 * mode 1: combat until death
 */

public class Arena {
    private int id;
    private int size;
    private int mode;
    private UUID[] players;
    private UUID[] winners;
    private UUID[] losers;
    private HashMap<UUID, HashSet<Location>> showing;
    private Location center;
    private int[] margin;
    public Arena(int id, int size, int mode,Player[] players){
        this.id = id;
        this.size = size;
        this.mode = mode;
        this.players = getUUIDs(players);
        this.showing = new HashMap<>();
        for (UUID uuid:this.players){
            showing.put(uuid,new HashSet<>());
        }
        calCenter();
    }

    public Location getCenter() {
        return center;
    }

    public int getId() {
        return id;
    }

    public int[] getMargin() {
        return margin;
    }

    public UUID[] getPlayers(){
        return this.players;
    }

    public HashMap<UUID,HashSet<Location>> getShowing(){
        return this.showing;
    }

    public boolean checkFinish(){
        if (mode == 1){
            System.out.println();
        }
        return false;
    }

    public boolean havePlayer(Player player){
        for (UUID uuid:this.players){
            if (player.getUniqueId().equals(uuid)){
                return true;
            }
        }
        return false;
    }

    public void calCenter(){
        int x =0;
        int y=0;
        int z=0;
        Player p;
        for (UUID uuid:this.players){
            p = Main.getInstance().getServer().getPlayer(uuid);
            x+=p.getLocation().getBlockX();
            y+=p.getLocation().getBlockY();
            z+=p.getLocation().getBlockZ();
        }
        x = x/this.players.length;
        y = y/this.players.length;
        z = z/this.players.length;

        this.center = new Location(Main.getInstance().getServer().getPlayer(this.players[0]).getWorld(),x,y,z);

        x = this.center.getBlockX();
        z = this.center.getBlockZ();
        this.margin = new int[]{x-(this.size/2)+1,z-(this.size/2)+1,x+(this.size/2),z+(this.size/2)};
    }

    public UUID[] getUUIDs(Player[] players){
        UUID[] uuids = new UUID[players.length];
        for (int i=0;i<players.length;i++){
            uuids[i] = players[i].getUniqueId();
        }
        return uuids;
    }

}
