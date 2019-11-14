package com.lxgshadow.customitem.AdvancedTool;

import com.lxgshadow.customitem.interfaces.CustomItems;
import com.lxgshadow.customitem.Main;
import com.lxgshadow.customitem.utils.ItemUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

public class LumbererAxe implements CustomItems {
    private static ItemStack item;
    private static String name = "Lumberer Axe";
    private static String dpName = ChatColor.AQUA+"Lumberer's Axe";
    public static String regName = name.replace(" ","_").toLowerCase();
    private static String[] lores = {
            ChatColor.AQUA+""+ChatColor.ITALIC+"Advanced Tool",
            ChatColor.WHITE+""+ChatColor.ITALIC+"Description: Protect Environment? Never heard of it.",
            ChatColor.WHITE+""+ChatColor.ITALIC+"*Cutting down the entire tree",
            ChatColor.BLACK+"Register Name: ["+regName+"]"
    };

    public static ItemStack getItem() {
        return item;
    }

    public static void createRecipe(){
        item = new ItemStack(Material.IRON_AXE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(dpName);
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(),regName),item);
        r.shape("112","103"," 0 ");
        r.setIngredient('0',Material.STICK);
        r.setIngredient('1',Material.IRON_AXE);
        r.setIngredient('2',Material.FLINT);
        r.setIngredient('3', Material.REDSTONE_TORCH);
        Main.getInstance().getServer().addRecipe(r);
        Main.getInstance().getServer().getPluginManager().registerEvents(new LumbererAxeListener(),Main.getInstance());
    }
}

class LumbererAxeListener implements Listener{
    @EventHandler
    public void onbreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if (ItemUtils.isRegisterNameSimilar(LumbererAxe.regName,player.getInventory().getItemInMainHand())){
            if (Tag.LOGS.isTagged(event.getBlock().getType())){
                breakNearbyLogs(event.getBlock(),player.getInventory().getItemInMainHand());
            }
        }
    }

    public void breakNearbyLogs(Block block, ItemStack item){
        if (!(Tag.LOGS.isTagged(block.getType())||Tag.LEAVES.isTagged(block.getType()))){return;}
        block.breakNaturally(item);
        new BukkitRunnable(){
            @Override
            public void run(){
                Location bl = block.getLocation();
                breakNearbyLogs(block.getWorld().getBlockAt(bl.clone().add(0,0,1)),item);
                breakNearbyLogs(block.getWorld().getBlockAt(bl.clone().add(0,0,-1)),item);
                breakNearbyLogs(block.getWorld().getBlockAt(bl.clone().add(1,0,0)),item);
                breakNearbyLogs(block.getWorld().getBlockAt(bl.clone().add(-1,0,0)),item);

                breakNearbyLogs(block.getWorld().getBlockAt(bl.clone().add(1,0,1)),item);
                breakNearbyLogs(block.getWorld().getBlockAt(bl.clone().add(1,0,-1)),item);
                breakNearbyLogs(block.getWorld().getBlockAt(bl.clone().add(-1,0,1)),item);
                breakNearbyLogs(block.getWorld().getBlockAt(bl.clone().add(-1,0,-1)),item);

                breakNearbyLogs(block.getWorld().getBlockAt(bl.clone().add(0,1,0)),item);
            }
        }.runTaskLater(Main.getInstance(),5);

    }
}
