package com.lxgshadow.customitem.AdvancedWeapon;

import com.lxgshadow.customitem.Main;
import com.lxgshadow.customitem.interfaces.CustomItems;
import com.lxgshadow.customitem.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class DullahanSword implements CustomItems {
    private static ItemStack item;
    private static ChatColor itemColor = ChatColor.AQUA;
    private static String name = "Dullahan Sword";
    private static String dpName = itemColor+"Dullahan's Sword";
    public static String regName = name.replace(" ","_").toLowerCase();
    private static String[] lores = {
            itemColor+""+ChatColor.ITALIC+"Advanced Weapon",
            ChatColor.WHITE+""+ChatColor.ITALIC+"Description: Where is my head?",
            ChatColor.WHITE+""+ChatColor.ITALIC+"*Player drop head when killed by this sword",
            ChatColor.BLACK+"Register Name: ["+regName+"]"
    };

    public static ItemStack getItem() {
        return item;
    }
    public static ChatColor getItemColor(){return itemColor;}

    public static void createRecipe(){
        item = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(dpName);
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(),regName),item);
        r.shape("101","101","101");
        r.setIngredient('0',Material.IRON_SWORD);
        r.setIngredient('1',Material.REDSTONE_BLOCK);
        Main.getInstance().getServer().addRecipe(r);
        Main.getInstance().getServer().getPluginManager().registerEvents(new DullahanSwordListener(),Main.getInstance());
    }
}
class DullahanSwordListener implements Listener{
    @EventHandler
    public void ondead(PlayerDeathEvent event){
        if (event.getEntity().getKiller() != null){
            if (ItemUtils.isRegisterNameSimilar(DullahanSword.regName,event.getEntity().getKiller().getInventory().getItemInMainHand())){
                ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta)skull.getItemMeta();
                meta.setOwningPlayer(event.getEntity());
                skull.setItemMeta(meta);
                event.getDrops().add(skull);
            }
        }
    }
}