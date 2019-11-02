package com.lxgshadow.survival.listeners;

import com.lxgshadow.survival.Main;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.UUID;


public class ChairsListener implements Listener {
    private static Material[] supportedChairs = {};
    private static int maxDis = 2;
    private static HashMap<UUID,UUID> chairs = new HashMap<>();
    private static HashMap<UUID,UUID> chairBlocks = new HashMap<>();
    @EventHandler
    public void onClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getType() != Material.AIR){
            return;
        }
        if (chairs.containsKey(player.getUniqueId())){
            return;
        }
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
            Block block = event.getClickedBlock();
            if (block.getBlockData() instanceof Stairs){
                if (event.getClickedBlock().getLocation().distance(player.getLocation()) < maxDis){
                    World world = player.getWorld();
                    Entity chair = world.spawnEntity(block.getLocation().add(0.5D,0.1D,0.5D), EntityType.SNOWBALL);
                    chair.setGravity(false);
                    chair.setVelocity(new Vector(0,0,0));
                    chair.setCustomName("chair");
                    chair.addPassenger(player);
                    chairs.put(player.getUniqueId(),chair.getUniqueId());
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true,priority = EventPriority.LOWEST)
    public void onPlayermove(PlayerMoveEvent event){
        removeChair(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        removeChair(event.getPlayer());
    }

    public static void removeChair(Player player){
        if (chairs.containsKey(player.getUniqueId())) {
            Entity entity = player.getServer().getEntity(chairs.get(player.getUniqueId()));
            if (entity != null){
                entity.remove();
            }
            chairs.remove(player.getUniqueId());
        }
    }

    public static void clear(){
        for (UUID uuid:chairs.values()){
            Main.getInstance().getServer().getEntity(uuid).remove();
        }
    }
}
