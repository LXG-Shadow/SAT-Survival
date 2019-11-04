package com.lxgshadow.customitem.energySystem;

import com.lxgshadow.customitem.Main;
import com.lxgshadow.customitem.utils.ActionbarUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class energyDisplay implements Listener {
    public energyDisplay() {
        EnergyManager.initialize();
        new BukkitRunnable() {
            @Override
            public void run() {
                energyDisplay.updateAll();

            }
        }.runTaskTimer(Main.getInstance(), 0, 20 * 2);
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        EnergyManager.register(event.getPlayer());
    }

    public static void update(Player player){
        ActionbarUtils.send(player,EnergyManager.toText(player));
    }

    public static void updateAll(){
        Main.getInstance().getServer().getOnlinePlayers().forEach((p) -> ActionbarUtils.send(p, EnergyManager.toText(p)));
    }

}

