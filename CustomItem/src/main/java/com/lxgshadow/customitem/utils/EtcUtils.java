package com.lxgshadow.customitem.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class EtcUtils {
    public static Boolean chance(int numerator,int denominator){
        int value = (int)(Math.random()*denominator);
        return value < numerator;
    }

    public static void sendMessageIfPlayer(Entity entity, String message){
        if (entity == null){return;}
        if (entity instanceof Player){
            entity.sendMessage(message);
        }
    }

    public static int randInt(int min,int max){
        return (int)(Math.random()*(max-min+1))+min;
    }
}
