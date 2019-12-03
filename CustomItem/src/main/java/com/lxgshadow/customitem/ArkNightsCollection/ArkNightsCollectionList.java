package com.lxgshadow.customitem.ArkNightsCollection;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum  ArkNightsCollectionList {
    EyjafjallaVolcano(com.lxgshadow.customitem.ArkNightsCollection.EyjafjallaVolcano.regName,com.lxgshadow.customitem.ArkNightsCollection.EyjafjallaVolcano.class),
    ChiXiao(com.lxgshadow.customitem.ArkNightsCollection.ChiXiao.regName, com.lxgshadow.customitem.ArkNightsCollection.ChiXiao.class);

    public Class clazz;
    public String regName;
    private ArkNightsCollectionList(String regName,Class clazz){
        this.clazz = clazz;
        this.regName = regName;
    }

    public static ItemStack getRandomly(){
        int number = (int)(Math.random()*(ArkNightsCollectionList.values().length));
        try {
            return (ItemStack) ArkNightsCollectionList.values()[number].clazz.getMethod("getItem").invoke(null);
        }catch (Exception e){
            return new ItemStack(Material.AIR);
        }
    }
}
