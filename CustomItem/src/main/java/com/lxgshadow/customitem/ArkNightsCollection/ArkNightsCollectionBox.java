package com.lxgshadow.customitem.ArkNightsCollection;

import com.lxgshadow.customitem.Main;
import com.lxgshadow.customitem.utils.EtcUtils;
import com.lxgshadow.customitem.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class ArkNightsCollectionBox {
    private static ItemStack item;
    private static String name = "ArkNights Collection Box";
    private static String dpName = ChatColor.GOLD+"明日方舟抽奖箱";
    public static String regName = name.replace(" ","_").toLowerCase();
    private static String[] lores = {
            ChatColor.WHITE+"氪金就完事了",
            ChatColor.WHITE+""+ChatColor.ITALIC+"*可能获取"+ ChatColor.GOLD+""+ChatColor.UNDERLINE+"*明日方舟收藏品*" + ChatColor.WHITE+""+ChatColor.ITALIC+ "中的物品",
            ChatColor.WHITE+""+ChatColor.ITALIC+"*掉率3%, 无保底.",
            ChatColor.WHITE+""+ChatColor.ITALIC+"*对着空中右键使用",
            ChatColor.BLACK+"Register Name: ["+regName+"]"
    };
    static int[] chance;


    public static ItemStack getItem() {
        return item;
    }

    public static void createRecipe(){
        getConfig();
        item = new ItemStack(Material.END_PORTAL_FRAME);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(dpName);
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(),regName),item);
        r.shape("101","010","101");
        r.setIngredient('0',Material.NETHER_STAR);
        r.setIngredient('1',Material.DIAMOND_BLOCK);
        Main.getInstance().getServer().addRecipe(r);
        Main.getInstance().getServer().getPluginManager().registerEvents(new ArkNightsCollectionBoxListener(),Main.getInstance());
    }

    public static void getConfig(){
        chance = new int[]{3, 100};
    }
}

class ArkNightsCollectionBoxListener implements Listener{
    @EventHandler
    public void onclick(PlayerInteractEvent event){
        if (event.getAction() == Action.RIGHT_CLICK_AIR && ItemUtils.isRegisterNameSimilar(ArkNightsCollectionBox.regName,event.getPlayer().getInventory().getItemInMainHand())){
            event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount()-1);
            if (EtcUtils.chance(ArkNightsCollectionBox.chance[0],ArkNightsCollectionBox.chance[1])){
                event.getPlayer().getInventory().addItem(ArkNightsCollectionList.getRandomly());
            }else {
                event.getPlayer().getInventory().addItem(new ItemStack(Material.NETHER_STAR,(int)(Math.random()*2)));
                event.getPlayer().getInventory().addItem(new ItemStack(Material.DIAMOND_BLOCK,(int)(Math.random()*3)));
            }
            event.setCancelled(true);
        }else if (event.getAction() == Action.RIGHT_CLICK_BLOCK && ItemUtils.isRegisterNameSimilar(ArkNightsCollectionBox.regName,event.getPlayer().getInventory().getItemInMainHand())){
            event.setCancelled(true);
        }
    }
}
