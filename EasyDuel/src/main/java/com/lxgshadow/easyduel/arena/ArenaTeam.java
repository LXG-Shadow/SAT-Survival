package com.lxgshadow.easyduel.arena;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;

public class ArenaTeam {
    private HashSet<Player> players;
    private boolean win;
    public ArenaTeam(Iterable<Player> ps){
        this.players = new HashSet<>();
        this.win =false;
        for (Player p:ps){
            this.players.add(p);
        }
    }


    public HashSet<Player> getPlayers() {
        return players;
    }

    public static boolean HaveSamePlayer(Iterable<ArenaTeam> teams){
        HashSet<Player> total = new HashSet<>();
        int totalnum = 0;
        for (ArenaTeam at:teams){
            total.addAll(at.players);
            totalnum += at.players.size();
        }
        return totalnum != total.size();
    }

    public boolean havePlayer(Player player){
        return this.players.contains(player);
    }

    public void setWin(boolean b){
        this.win = b;
    }

    public boolean isWin() {
        return win;
    }
}
