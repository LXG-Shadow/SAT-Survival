package com.lxgshadow.survival.managers;

import com.lxgshadow.survival.Main;
import com.lxgshadow.survival.models.PlayerInventoryChestViewer;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_14_R1.DedicatedServer;
import net.minecraft.server.v1_14_R1.DimensionManager;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.PlayerInteractManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

// todo: using same method for online and offline player

public class PicvManager {
    private static HashMap<String, PlayerInventoryChestViewer> picvs;
    private static HashMap<UUID, String> targets;

    public static void initialtize() {
        picvs = new HashMap<>();
        targets = new HashMap<>();
        Main.getInstance().getServer().getPluginManager().registerEvents(new PicvListener(), Main.getInstance());
    }

    public static PlayerInventoryChestViewer create(Player player) {
        PlayerInventoryChestViewer viewer = new PlayerInventoryChestViewer(player);
        picvs.put(viewer.getId(), viewer);
        targets.put(viewer.getPuuid(),viewer.getId());
        return viewer;
    }

    public static PlayerInventoryChestViewer createOffline(OfflinePlayer offplayer){
        if (!offplayer.hasPlayedBefore()){
            return null;
        }
        GameProfile gprofile = new GameProfile(offplayer.getUniqueId(),offplayer.getName());
        DedicatedServer dServer = ((CraftServer) Bukkit.getServer()).getServer();
        EntityPlayer entity = new EntityPlayer(dServer,dServer.getWorldServer(DimensionManager.OVERWORLD),gprofile,new PlayerInteractManager(dServer.getWorldServer(DimensionManager.OVERWORLD)));

        CraftPlayer cPlayer = entity.getBukkitEntity();
        if (cPlayer != null){
            cPlayer.loadData();
            PlayerInventoryChestViewer viewer = new PlayerInventoryChestViewer(cPlayer);
            picvs.put(viewer.getId(), viewer);
            targets.put(viewer.getPuuid(),viewer.getId());
            return viewer;
        }
        return null;
    }

    public static void delete(PlayerInventoryChestViewer viewer) {
        delete(viewer.getId());
    }

    public static void delete(String id) {
        if (picvs.containsKey(id)){
            targets.remove(picvs.remove(id).getPuuid());
        }
    }

    public static void deleteByTitle(String title) {
        delete(title.substring(title.indexOf("[") + 1, title.indexOf("]")));
    }

    public static PlayerInventoryChestViewer getViewer(String id) {
        return picvs.get(id);
    }

    public static void i2pUpdate(String title) {
        getViewer(title.substring(title.indexOf("[") + 1, title.indexOf("]"))).inventory2player();
    }

    public static void p2iUpdate(String title) {
        getViewer(title.substring(title.indexOf("[") + 1, title.indexOf("]"))).player2inventory();
    }

    public static void p2iUpdate(Player player) {
        if (targets.containsKey(player.getUniqueId())) {
            getViewer(targets.get(player.getUniqueId())).player2inventory();
        }
    }
}

class PicvListener implements Listener {
    @EventHandler
    public void oninteract(InventoryClickEvent event) {
        if (event.getView().getTitle().startsWith("InvViewer -")) {
            if (PlayerInventoryChestViewer.unused.contains(event.getRawSlot())){
                PicvManager.p2iUpdate(event.getView().getTitle());
                event.setCancelled(true);
                return;
            }
            new BukkitRunnable() {
                public void run() {
                    PicvManager.i2pUpdate(event.getView().getTitle());
                }
            }.runTaskLater(Main.getInstance(), 1);
        }
        if (event.getWhoClicked() instanceof Player) {
            new BukkitRunnable() {
                public void run() {
                    PicvManager.p2iUpdate((Player) event.getWhoClicked());
                }
            }.runTaskLater(Main.getInstance(), 1);
        }
    }

    @EventHandler
    public void ondrag(InventoryDragEvent event) {
        if (event.getView().getTitle().startsWith("InvViewer -")) {
            new BukkitRunnable() {
                public void run() {
                    PicvManager.i2pUpdate(event.getView().getTitle());
                }
            }.runTaskLater(Main.getInstance(), 1);
            return;
        }
        if (event.getWhoClicked() instanceof Player) {
            new BukkitRunnable() {
                public void run() {
                    PicvManager.p2iUpdate((Player) event.getWhoClicked());
                }
            }.runTaskLater(Main.getInstance(), 1);
        }
    }

    @EventHandler
    public void onopen(InventoryCloseEvent event) {
        if (event.getView().getTitle().startsWith("InvViewer -")) {
//            String title = event.getView().getTitle();
//            PicvManager.i2pUpdate(title);
            PicvManager.deleteByTitle(event.getView().getTitle());
        }
    }
}