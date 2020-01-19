package com.lxgshadow.easyduel.arena;

import com.lxgshadow.easyduel.Main;
import com.lxgshadow.easyduel.Messages;
import com.lxgshadow.easyduel.utils.playerUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.lang.reflect.Array;
import java.util.*;


/***
 * mode 1: combat until death
 */

public class Arena {
    private int id;
    private int size;
    private int mode;
    private HashSet<Player> players;
    private HashSet<Player> team1;
    private HashSet<Player> team2;
    private HashMap<Player, Integer> alive;
    private int win;
    private HashMap<UUID, HashSet<Location>> showing;
    private Location center;
    private int[] margin;

    public Arena(int id, int size, int mode, ArrayList<Player> players, ArrayList<Integer> team) {
        this.id = id;
        this.size = size;
        this.mode = mode;
        this.players = new HashSet<>(players);
        this.team1 = new HashSet<>();
        this.team2 = new HashSet<>();
        this.alive = new HashMap<>();
        for (int i = 0; i < players.size(); i++) {
            alive.put(players.get(i), 1);
            if (team.get(i) == 1) {
                team1.add(players.get(i));
            } else {
                team2.add(players.get(i));
            }
        }
        this.win = 0;
        this.showing = new HashMap<>();
        for (Player player : this.players) {
            showing.put(player.getUniqueId(), new HashSet<>());
        }
        calCenter();
        sendStartInfo();
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

    public HashSet<Player> getPlayers() {
        return this.players;
    }

    public int getMode() {
        return mode;
    }

    public HashMap<UUID, HashSet<Location>> getShowing() {
        return this.showing;
    }

    public void update(Event e) {
        if (e instanceof EntityDamageEvent) {
            EntityDamageEvent event = (EntityDamageEvent) e;
            Player player = (Player) event.getEntity();
            // not alive ignore update
            if (!this.isAlive(player)) {
                return;
            }
            if (mode == 1) {
                if (player.getHealth() - event.getFinalDamage() < 0) {
                    this.alive.put(player, 0);
                    event.setCancelled(true);
                }
            }
            this.updateStatus();
        }
        if (e instanceof PlayerMoveEvent) {
            PlayerMoveEvent event = (PlayerMoveEvent) e;
            Player player = event.getPlayer();
            if (!this.isAlive(player)) {
                return;
            }
            if (this.checkPlayerOutOfBoarder(player)) {
                this.alive.put(player, 0);
            }
            this.updateStatus();
        }
        return;
    }

    public void updateStatus() {
        boolean ddd = false;
        for (Player player : this.team1) {
            ddd = ddd || this.isAlive(player);
        }
        if (!ddd) {
            this.win = 2;
        }
        ddd = false;
        for (Player player : this.team2) {
            ddd = ddd || this.isAlive(player);
        }
        if (!ddd) {
            this.win = 1;
        }

        // check if game end
        if (win != 0) {
            for (Player p : players) {
                //recover health
                p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                this.alive.put(p, 0);

                //hide boarder
                this.hideBoarder(p);
            }
            sendEndInfo();
            // show all player
            for (Player player : players) {
                for (Player p : players) {
                    player.showPlayer(Main.getInstance(), p);
                }
            }
            ArenaManager.remove(this.id);
        }

        for (Player player : players) {
            // alive player
            // vanish died player in alive people
            if (this.alive.get(player) == 1) {
                for (Player tempP : players) {
                    if (this.alive.get(tempP) == 0) {
                        player.hidePlayer(Main.getInstance(), tempP);
                    }
                }
            }
            // dead player
            // set health to max
            else {
                player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            }
        }

        // tp outside player to the center
        for (Player player : this.getPlayerOutOfBoarder()) {
            player.teleport(this.center);
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

    public void sendStartInfo() {
        StringBuilder opps;
        for (Player player : this.players) {
            player.sendMessage(Messages.duel_splitor);
            player.sendMessage("决斗信息: ");
            player.sendMessage("模式: "+ArenaModes.getName(this.mode));
            opps = new StringBuilder();
            opps.append("对手: ");
            if (this.team1.contains(player)) {
                for (Player player2 : team2) {
                    opps.append(player2.getDisplayName()).append(",");
                }
            } else {
                for (Player player2 : team1) {
                    opps.append(player2.getDisplayName()).append(",");
                }
            }
            opps.delete(opps.length() - 1, opps.length());
            player.sendMessage(opps.toString());
            player.sendMessage(Messages.duel_splitor);
        }
    }

    public void sendEndInfo() {
        StringBuilder opps;
        for (Player player : this.players) {
            opps = new StringBuilder();
            player.sendMessage(Messages.duel_splitor);
            player.sendMessage("对局结束: ");
            player.sendMessage("模式: "+ArenaModes.getName(this.mode));
            opps.append("胜利者: ");
            if (win == 1){
                for (Player player1:team1){
                    opps.append(player1.getDisplayName()).append(",");
                }
            }else{
                for (Player player1:team2){
                    opps.append(player1.getDisplayName()).append(",");
                }



            }
            opps.delete(opps.length() - 1, opps.length());
            player.sendMessage(opps.toString());
            player.sendMessage(Messages.duel_splitor);
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
        if (player.getLocation().getBlockX() <= this.margin[0]
                || player.getLocation().getBlockZ() <= this.margin[1]
                || player.getLocation().getBlockX() >= this.margin[2]
                || player.getLocation().getBlockZ() >= this.margin[3]) {
            return true;
        }
        return false;
    }

    private void calCenter() {
        int x = 0;
        int y = 0;
        int z = 0;
        World world = null;
        for (Player p : this.players) {
            x += p.getLocation().getBlockX();
            y += p.getLocation().getBlockY();
            z += p.getLocation().getBlockZ();
            world = p.getWorld();
        }
        x = x / this.players.size();
        y = y / this.players.size();
        z = z / this.players.size();

        this.center = new Location(world, x, y, z);

        x = this.center.getBlockX();
        z = this.center.getBlockZ();
        this.margin = new int[]{x - (this.size / 2) + 1, z - (this.size / 2) + 1, x + (this.size / 2), z + (this.size / 2)};
    }

    public void showBoarder(Player player, int y) {
        int x1, z1, x2, z2;
        x1 = this.margin[0];
        z1 = this.margin[1];
        x2 = this.margin[2];
        z2 = this.margin[3];
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
            player.sendBlockChange(l, Material.GLASS.createBlockData());
        }
    }

    public void hideBoarder(Player player) {
        HashSet<Location> pshowing = this.showing.get(player.getUniqueId());
        for (Location l : pshowing) {
            player.sendBlockChange(l, player.getWorld().getBlockAt(l).getBlockData());
        }
        pshowing.clear();
    }

}
