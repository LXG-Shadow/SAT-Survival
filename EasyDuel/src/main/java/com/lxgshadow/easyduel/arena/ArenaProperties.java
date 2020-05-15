package com.lxgshadow.easyduel.arena;

import com.lxgshadow.easyduel.Config;
import com.lxgshadow.easyduel.Main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ArenaProperties {
    private Arena arena;
    public int size;
    public Location center;
    public int[] margin;


    public ArenaProperties(){
        this.size = Config.arena_defaultsize;
        this.arena = null;
    }

    public void parseArgs(String[] args){
        for (int i=1;i<args.length;i++){
            if (args[i].startsWith("size=") && args[i].length()>5){
                size = Integer.parseInt(args[i].substring(5));
            }
        }
    }

    private Location calCenter() {
        int x = 0;
        int y = 0;
        int z = 0;
        World world = null;
        for (Player p : this.arena.getPlayers()) {
            x += p.getLocation().getBlockX();
            y += p.getLocation().getBlockY();
            z += p.getLocation().getBlockZ();
            world = p.getWorld();
        }
        x = x / this.arena.getPlayers().size();
        y = y / this.arena.getPlayers().size();
        z = z / this.arena.getPlayers().size();

        return new Location(world, x, y, z);

    }

    public void updateMargin(){
        int x = this.center.getBlockX();
        int z = this.center.getBlockZ();
        this.margin = new int[]{x - (this.size / 2) + 1, z - (this.size / 2) + 1, x + (this.size / 2), z + (this.size / 2)};
    }

    public void addArena(Arena arena){
        if (this.arena != null){return;}
        this.arena = arena;
    }

    public void update(){
        if (this.arena == null){return;}
        this.center = calCenter();
        updateMargin();
    }

}
