package com.lxgshadow.survival.models;

import org.bukkit.Bukkit;

import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;


public class PlayerInventoryChestViewer{
    private String id;
    private UUID puuid;
    private boolean offline;
    private CraftPlayer offplayer;
    public static HashSet<Integer> unused = new HashSet<>(Arrays.asList(27,28,29,30,31,32,33,34,35,40,42,43,44));
    private Inventory inventory;
    private PlayerInventory pInventory;

    public PlayerInventoryChestViewer(Player player){
        this.id = RandomStringUtils.randomAlphanumeric(6);
        this.puuid = player.getUniqueId();
        this.offline = false;
        inventory = Bukkit.createInventory(null,54,"InvViewer - "+player.getName()+ " - ["+id+"]");
        pInventory = player.getInventory();
        this.player2inventory();

        if (!player.isOnline()){
            this.offline = true;
            offplayer = (CraftPlayer) player;
        }
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

        ItemStack bglass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = bglass.getItemMeta();
        meta.setDisplayName("Refresh Inventory");
        bglass.setItemMeta(meta);

        for (int i:this.unused){
            items[i] = bglass;
        }

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

    public UUID getPuuid() {
        return puuid;
    }

    public void saveData(){
        if (this.offline){
            offplayer.saveData();
        }
    }
}

