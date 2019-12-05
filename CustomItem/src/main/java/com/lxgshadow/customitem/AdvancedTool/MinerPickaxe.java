package com.lxgshadow.customitem.AdvancedTool;

import com.lxgshadow.customitem.Main;
import com.lxgshadow.customitem.interfaces.CustomItems;
import com.lxgshadow.customitem.utils.EtcUtils;
import com.lxgshadow.customitem.utils.ItemUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinerPickaxe implements CustomItems {
    private static ItemStack item;
    private static String name = "Miner Pickaxe";
    private static String dpName = ChatColor.AQUA+"Miner's Axe";
    public static String regName = name.replace(" ","_").toLowerCase();
    private static String[] lores = {
            ChatColor.AQUA+""+ChatColor.ITALIC+"Advanced Tool",
            ChatColor.WHITE+""+ChatColor.ITALIC+"Description: So we back in the mine,",
            ChatColor.WHITE+""+ChatColor.ITALIC+"Got our pickaxe swimming from side to side",
            ChatColor.WHITE+""+ChatColor.ITALIC+"*Mine all connected minerals",
            ChatColor.BLACK+"Register Name: ["+regName+"]"
    };
    static List<Material> minerals;

    public static ItemStack getItem() {
        return item;
    }

    public static void createRecipe(){
        getConfig();
        item = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(dpName);
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(),regName),item);
        r.shape("111","202"," 3 ");
        r.setIngredient('0',Material.DIAMOND_PICKAXE);
        r.setIngredient('1',Material.IRON_PICKAXE);
        r.setIngredient('2',Material.REDSTONE_TORCH);
        r.setIngredient('3', Material.STICK);
        Main.getInstance().getServer().addRecipe(r);
        Main.getInstance().getServer().getPluginManager().registerEvents(new MinerPickaxeListener(),Main.getInstance());
    }

    public static void getConfig(){
        minerals = new ArrayList<>();
        minerals.add(Material.LAPIS_ORE);
        minerals.add(Material.COAL_ORE);
        minerals.add(Material.IRON_ORE);
        minerals.add(Material.REDSTONE_ORE);
        minerals.add(Material.DIAMOND_ORE);
        minerals.add(Material.GOLD_ORE);
        minerals.add(Material.EMERALD_ORE);
        minerals.add(Material.NETHER_QUARTZ_ORE);

    }
}

class MinerPickaxeListener implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if (ItemUtils.isRegisterNameSimilar(MinerPickaxe.regName,player.getInventory().getItemInMainHand())){
            if (MinerPickaxe.minerals.contains(event.getBlock().getType())){
                breakNearbyLogs(event.getBlock(),player.getInventory().getItemInMainHand(),event.getBlock().getType(),player);
            }
        }
    }

    public void breakNearbyLogs(Block block, ItemStack item, Material material, Player player){
        if (material != block.getType()){
            return;
        }
        // fix bug: no exp drop
        int exp;
        switch (material){
            case DIAMOND_ORE:
            case EMERALD_ORE:
                exp = EtcUtils.randInt(3,7);
                break;
            case LAPIS_ORE:
            case NETHER_QUARTZ_ORE:
                exp = EtcUtils.randInt(2,5);
                break;
            case REDSTONE_ORE:
                exp = EtcUtils.randInt(1,5);
                break;
            case COAL_ORE:
                exp = EtcUtils.randInt(0,2);
                break;
            default:
                exp = 0;
                break;
        }
        block.breakNaturally(item);

        if (exp != 0){
            ExperienceOrb expo = (ExperienceOrb) player.getWorld().spawnEntity(block.getLocation(), EntityType.EXPERIENCE_ORB);
            // fix bug of less xp caused by merging happens before setExperience
            expo.setExperience(expo.getExperience()+exp);
        }


        new BukkitRunnable(){
            public void run(){
                Location bl = block.getLocation();
                breakNearbyLogs(block.getWorld().getBlockAt(bl.clone().add(0,0,1)),item,material,player);
                breakNearbyLogs(block.getWorld().getBlockAt(bl.clone().add(0,0,-1)),item,material,player);
                breakNearbyLogs(block.getWorld().getBlockAt(bl.clone().add(1,0,0)),item,material,player);
                breakNearbyLogs(block.getWorld().getBlockAt(bl.clone().add(-1,0,0)),item,material,player);
                breakNearbyLogs(block.getWorld().getBlockAt(bl.clone().add(0,1,0)),item,material,player);
                breakNearbyLogs(block.getWorld().getBlockAt(bl.clone().add(0,-1,0)),item,material,player);
            }
        }.runTaskLater(Main.getInstance(),5);
    }
}
