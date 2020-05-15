package com.lxgshadow.easyduel.arena;

import com.lxgshadow.easyduel.Main;
import com.lxgshadow.easyduel.Messages;
import com.lxgshadow.easyduel.events.EasyDuelArenaTimeChangeEvent;
import com.lxgshadow.easyduel.events.EasyDuelEndEvent;
import com.lxgshadow.easyduel.events.EasyDuelPlayerDeadEvent;
import com.lxgshadow.easyduel.mode.ArenaMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;


/***
 * mode 1: combat until death
 */

public class Arena {
    private int id;

    private int time;
    private ArenaMode mode;
    private ArenaProperties properties;
    private HashSet<Player> players;
    private HashSet<ArenaTeam> teams;
    private HashMap<Player, Integer> alive;
    private HashMap<UUID, HashSet<Location>> showing;

    private ArrayList<String> errorMsg;
    private BukkitRunnable timer;

    public Arena(int id, ArenaProperties arenaProperties,ArenaMode mode, Iterable<ArenaTeam> teams) {
        this.id = id;
        this.properties = arenaProperties;
        this.mode = mode;
        this.time = 0;
        this.players = new HashSet<>();
        this.teams = new HashSet<>((Collection<? extends ArenaTeam>) teams);
        this.alive = new HashMap<>();
        for (ArenaTeam at : teams) {
            this.players.addAll(at.getPlayers());
            // try use simple method
            for (Player p : at.getPlayers()) {
                this.alive.put(p, 1);
            }
        }
        this.showing = new HashMap<>();
        for (Player player : this.players) {
            showing.put(player.getUniqueId(), new HashSet<>());
        }
        this.errorMsg = new ArrayList<>();

        this.properties.addArena(this);
        this.properties.update();
    }

    public void addErrorMsg(String s) {
        this.errorMsg.add(s);
    }

    public void clearErrorMsg(String s){this.errorMsg.clear();}

    public ArrayList<String> getErrormsg() {
        return this.errorMsg;
    }

    public Location getCenter() {
        return this.properties.center;
    }

    public int getId() {
        return id;
    }

    public HashSet<ArenaTeam> getTeams() {
        return teams;
    }

    public int[] getMargin() {
        return this.properties.margin;
    }

    public HashSet<Player> getPlayers() {
        return this.players;
    }

    public ArenaMode getMode() {
        return this.mode;
    }

    public HashMap<UUID, HashSet<Location>> getShowing() {
        return this.showing;
    }

    public void start() {
        Arena arena = this;
        timer = new BukkitRunnable() {
            @Override
            public void run() {
                time += 1;
                Main.getInstance().getServer().getPluginManager().callEvent(new EasyDuelArenaTimeChangeEvent(arena));
            }
        };
        timer.runTaskTimer(Main.getInstance(), 0, 20);
        timer.cancel();
    }

    public void stop() {
        timer.cancel();
    }

    public int getTime() {
        return this.time;
    }


    public void updateStatus() {

        // check how many team alive.
        ArrayList<ArenaTeam> aliveteams = new ArrayList<>();

        for (ArenaTeam at : this.teams) {
            boolean tmp = false;
            for (Player player : at.getPlayers()) {
                tmp = tmp || this.isAlive(player);
            }
            if (tmp) {
                aliveteams.add(at);
            }
        }

        // if only one team left -> result appear
        if (aliveteams.size() == 1) {
            aliveteams.get(0).setWin(true);
            sendEndInfo();

            // show all player
            for (Player player : players) {
                for (Player p : players) {
                    player.showPlayer(Main.getInstance(), p);
                }
                this.hideBoarder(player);
            }
            EasyDuelEndEvent event = new EasyDuelEndEvent(this);
            Main.getInstance().getServer().getPluginManager().callEvent(event);

            // delete this arena
            ArenaManager.remove(this.id);
        }
    }

    public boolean havePlayer(Player player) {
        for (Player player1 : this.players) {
            if (player.getUniqueId().equals(player1.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public boolean isAlive(Player player) {
        if (!this.havePlayer(player)) {
            return false;
        }
        return this.alive.get(player) == 1;
    }

    public void setAlive(Player player, Boolean b) {
        if (!this.havePlayer(player)) {
            return;
        }
        if (b) {
            this.alive.put(player, 1);
        } else {
            this.alive.put(player, 0);
            EasyDuelPlayerDeadEvent de = new EasyDuelPlayerDeadEvent(this,player);
            Main.getInstance().getServer().getPluginManager().callEvent(de);
        }
    }

    public void sendStartInfo() {
        StringBuilder opps;
        for (Player player : this.players) {
            player.sendMessage(Messages.arena_splitor);
            player.sendMessage("决斗信息: ");
            player.sendMessage("模式: " + this.mode.getDisplayname());
            opps = new StringBuilder();
            opps.append("对手: ");
            for (ArenaTeam at : this.teams) {
                if (at.havePlayer(player)) {
                    continue;
                }
                for (Player p2 : at.getPlayers()) {
                    opps.append(p2.getDisplayName()).append(",");
                }
            }
            opps.delete(opps.length() - 1, opps.length());
            player.sendMessage(opps.toString());
            player.sendMessage(Messages.arena_splitor);
        }
    }

    public void sendEndInfo() {
        StringBuilder opps;
        for (Player player : this.players) {
            opps = new StringBuilder();
            player.sendMessage(Messages.arena_splitor);
            player.sendMessage("决斗结束: ");
            player.sendMessage("模式: " + this.mode.getDisplayname());
            opps.append("胜利者: ");
            for (ArenaTeam at : this.teams) {
                if (at.isWin()) {
                    for (Player p2 : at.getPlayers()) {
                        opps.append(p2.getDisplayName()).append(",");
                    }
                }
            }
            opps.delete(opps.length() - 1, opps.length());
            player.sendMessage(opps.toString());
            player.sendMessage(Messages.arena_splitor);
        }
    }

    public void broadcast(String msg){
        for (Player player : this.players) {
            player.sendMessage(msg);
        }
    }

    public ArrayList<Player> getPlayerOutOfBoarder() {
        ArrayList<Player> oob = new ArrayList<>();
        for (Player player : this.players) {
            if (this.checkPlayerOutOfBoarder(player)) {
                oob.add(player);
            }
        }
        return oob;
    }

    public boolean checkPlayerOutOfBoarder(Player player) {
        if (player.getLocation().getBlockX() <= this.properties.margin[0]
                || player.getLocation().getBlockZ() <= this.properties.margin[1]
                || player.getLocation().getBlockX() >= this.properties.margin[2]
                || player.getLocation().getBlockZ() >= this.properties.margin[3]) {
            return true;
        }
        return false;
    }

    public void showBoarder(Player player, int y) {
        int x1, z1, x2, z2;
        x1 = this.properties.margin[0];
        z1 = this.properties.margin[1];
        x2 = this.properties.margin[2];
        z2 = this.properties.margin[3];
        HashSet<Location> pshowing = this.showing.get(player.getUniqueId());
        for (int x = x1; x <= x2; x++) {
            for (int i = 0; i <= 4; i++) {
                pshowing.add(new Location(player.getWorld(), x, y + i, z1));
                pshowing.add(new Location(player.getWorld(), x, y + i, z2));
            }
        }
        for (int z = z1; z <= z2; z++) {
            for (int i = 0; i <= 4; i++) {
                pshowing.add(new Location(player.getWorld(), x1, y + i, z));
                pshowing.add(new Location(player.getWorld(), x2, y + i, z));
            }
        }
        for (Location l : pshowing) {
            //player.sendBlockChange(l,Material.GLASS, (byte) 0);
            player.sendBlockChange(l, Material.GLASS.createBlockData());
        }
    }


    public void hideBoarder(Player player) {
        HashSet<Location> pshowing = this.showing.get(player.getUniqueId());
        for (Location l : pshowing) {
            //player.sendBlockChange(l,player.getWorld().getBlockAt(l).getType(), player.getWorld().getBlockAt(l).getData());
            player.sendBlockChange(l, player.getWorld().getBlockAt(l).getBlockData());
        }
        pshowing.clear();
    }

}
