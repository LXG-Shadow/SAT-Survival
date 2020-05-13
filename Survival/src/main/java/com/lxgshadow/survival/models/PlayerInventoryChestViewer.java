package com.lxgshadow.survival.models;

import org.bukkit.Bukkit;

import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;


public class PlayerInventoryChestViewer{
    private String id;
    private Inventory inventory;
    private PlayerInventory pInventory;

    public PlayerInventoryChestViewer(Player player){
        this.id = RandomStringUtils.randomAlphanumeric(6);
        inventory = Bukkit.createInventory(null,54,"InvViewer - "+player.getName()+ " - ["+id+"]");
        pInventory = player.getInventory();
        this.player2inventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void player2inventory(){
        ItemStack[] items = new ItemStack[54];
        for (int i=0;i<27;i++){
            items[i] = pInventory.getStorageContents()[i+9];
        }

        for (int i=45;i<54;i++){
            items[i] = pInventory.getStorageContents()[i-45];
        }

        for (int i=36;i<40;i++){
            items[i] = pInventory.getArmorContents()[i-36];
        }
        items[41] = pInventory.getExtraContents()[0];

        inventory.setContents(items);
    }

    public void inventory2player(){
        ItemStack[] storage = new ItemStack[36];
        ItemStack[] inv = this.inventory.getContents();
        for (int i=0;i<9;i++){
            storage[i] = inv[i+45];
        }
        for (int i=9;i<36;i++){
            storage[i] = inv[i-9];
        }
        pInventory.setStorageContents(storage);
        ItemStack[] armor = new ItemStack[4];
        for (int i=0;i<4;i++){
            armor[i] = inv[i+36];
        }
        pInventory.setArmorContents(armor);
        ItemStack[] extra = new ItemStack[1];
        extra[0] = inv[41];

        pInventory.setExtraContents(extra);
    }

    public String getId() {
        return id;
    }
}

