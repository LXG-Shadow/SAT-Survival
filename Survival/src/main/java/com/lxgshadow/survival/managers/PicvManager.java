package com.lxgshadow.survival.managers;

import com.lxgshadow.survival.Main;
import com.lxgshadow.survival.models.PlayerInventoryChestViewer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class PicvManager {
    private static HashMap<String,PlayerInventoryChestViewer> picvs;

    public static void initialtize(){
        picvs = new HashMap<>();
        Main.getInstance().getServer().getPluginManager().registerEvents(new PicvListener(),Main.getInstance());
    }

    public static PlayerInventoryChestViewer create(Player player){
        PlayerInventoryChestViewer viewer = new PlayerInventoryChestViewer(player);
        picvs.put(viewer.getId(),viewer);
        return viewer;
    }

    public static void delete(PlayerInventoryChestViewer viewer){
        picvs.remove(viewer.getId());
    }

    public static void delete(String id){
        picvs.remove(id);
        Main.getInstance().getLogger().info(String.valueOf(picvs.isEmpty()));
    }

    public static void deleteByTitle(String title){
        delete(title.substring(title.indexOf("[")+1,title.indexOf("]")));
    }

    public static PlayerInventoryChestViewer getViewer(String id){
        return picvs.get(id);
    }

    public static void i2pUpdate(String title){
        getViewer(title.substring(title.indexOf("[")+1,title.indexOf("]"))).inventory2player();
    }
}

class PicvListener implements Listener {
    @EventHandler
    public void oninteract(InventoryClickEvent event){
        if (event.getView().getTitle().startsWith("InvViewer -")){
            new BukkitRunnable(){
                public void run(){
                    PicvManager.i2pUpdate(event.getView().getTitle());
                }
            }.runTaskLater(Main.getInstance(),1);

        }
    }

    @EventHandler
    public void ondrag(InventoryDragEvent event){
        if (event.getView().getTitle().startsWith("InvViewer -")){
            new BukkitRunnable(){
                public void run(){
                    PicvManager.i2pUpdate(event.getView().getTitle());
                }
            }.runTaskLater(Main.getInstance(),1);
        }
    }

    @EventHandler
    public void onopen(InventoryCloseEvent event){
        if (event.getView().getTitle().startsWith("InvViewer -")){
            String title = event.getView().getTitle();
            PicvManager.i2pUpdate(title);
            PicvManager.deleteByTitle(title);
        }
    }
}