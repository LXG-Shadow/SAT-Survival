package com.lxgshadow.customitem.energySystem;

import com.lxgshadow.customitem.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class EnergyManager {
    static HashMap<UUID, Integer> currentEnergy;

    public static void initialize(){
        currentEnergy = new HashMap<>();
        Main.getInstance().getServer().getOnlinePlayers().forEach(EnergyManager::register);
        new BukkitRunnable(){
            @Override
            public void run(){
                for (UUID uuid:currentEnergy.keySet()){
                    change(uuid,1);
                }
            }
        }.runTaskTimer(Main.getInstance(),0,1*20);
    }

    public static void register(Player player) {
        if (!currentEnergy.containsKey(player.getUniqueId())) {
            currentEnergy.put(player.getUniqueId(), getMaximum(player));
        }
    }

    public static boolean have(UUID uuid,int amount){
        return (currentEnergy.get(uuid) != null) && (currentEnergy.get(uuid) >= amount);
    }

    public static boolean have(Player player,int amount){
        return have(player.getUniqueId(),amount);
    }

    public static int getMaximum(UUID uuid) {
        return 250;
    }

    public static int getMaximum(Player player) {
        return getMaximum(player.getUniqueId());
    }

    public static int getCurrent(UUID uuid) {
        return currentEnergy.get(uuid);
    }


    public static int getCurrent(Player player) {
        return getCurrent(player.getUniqueId());
    }

    public static boolean change(UUID uuid, int amount) {
        return change(uuid,amount,false);
    }

    public static boolean change(UUID uuid, int amount, boolean update) {
        if (((getCurrent(uuid) + amount) > getMaximum(uuid)) || ((getCurrent(uuid) + amount) < 0)) {
            return false;
        } else {
            currentEnergy.put(uuid, currentEnergy.get(uuid) + amount);
            if (update){energyDisplay.update(Main.getInstance().getServer().getPlayer(uuid));}
            return true;
        }
    }

    public static boolean change(Player player, int amount) {
        return change(player.getUniqueId(),amount);
    }

    public static boolean change(Player player, int amount,boolean update) {
        return change(player.getUniqueId(),amount,update);
    }

    public static String toText(Player player){
        return ChatColor.LIGHT_PURPLE+"Shama: "+getCurrent(player)+" / "+getMaximum(player);
    }
}
