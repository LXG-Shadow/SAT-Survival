package com.lxgshadow.easyduel.utils;

import com.lxgshadow.easyduel.Main;
import org.bukkit.entity.Player;

import java.util.UUID;

public class playerUtil {
    public static Player getPlayer(UUID uuid){
        return Main.getInstance().getServer().getPlayer(uuid);
    }
}
