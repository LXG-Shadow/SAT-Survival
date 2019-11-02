package com.lxgshadow.customitem.UltimateWeapon;

import com.lxgshadow.customitem.Config;
import com.lxgshadow.customitem.Main;
import com.lxgshadow.customitem.utils.IgnoreSelf;
import com.lxgshadow.customitem.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.RayTraceResult;

import java.util.ArrayList;
import java.util.Arrays;

public class LighteningWand {
    static ItemStack costs = new ItemStack(Material.GUNPOWDER, 1);
    private static ItemStack item;
    private static String name = "Lightening Wand";
    private static String dpName = ChatColor.GOLD+name;
    public static String regName = name.replace(" ","_").toLowerCase();
    private static String[] lores = {
            ChatColor.GOLD+""+ChatColor.ITALIC+"Ultimate Weapon",
            ChatColor.WHITE+""+ChatColor.ITALIC+"Description: The Anger From God",
            ChatColor.WHITE+""+ChatColor.ITALIC+"*Maximum Distance " + Config.lighteningwand_maxdistance,
            ChatColor.BLACK+"Register Name: ["+regName+"]"
    };

    public static ItemStack getItem() {
        return item;
    }

    public static void createRecipe() {
        item = new ItemStack(Material.BLAZE_ROD);
        item.addUnsafeEnchantment(Enchantment.CHANNELING, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(dpName);
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(), regName), item);
        r.shape("121", "303", "454");
        r.setIngredient('0', Material.NETHER_STAR);
        r.setIngredient('1', Material.GUNPOWDER);
        r.setIngredient('2', Material.CREEPER_HEAD);
        r.setIngredient('3', Material.TNT);
        r.setIngredient('4', Material.REDSTONE);
        r.setIngredient('5', Material.BLAZE_ROD);
        Main.getInstance().getServer().addRecipe(r);
        Main.getInstance().getServer().getPluginManager().registerEvents(new LighteningWandListener(), Main.getInstance());
    }
}

class LighteningWandListener implements Listener {
    @EventHandler
    public void onRightclick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inv = player.getInventory();
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if ((inv.contains(LighteningWand.costs.getType(), LighteningWand.costs.getAmount()))
                    && (ItemUtils.isRegisterNameSimilar(LighteningWand.regName,inv.getItemInMainHand()) || ItemUtils.isRegisterNameSimilar(LighteningWand.regName,inv.getItemInOffHand()))) {
//                RayTraceResult rr = player.getWorld().rayTrace(player.getLocation(), player.getEyeLocation().getDirection(),
//                        Config.lighteningwand_maxdistance,FluidCollisionMode.ALWAYS,false,1,null);
                RayTraceResult rr = player.getWorld().rayTraceEntities(player.getEyeLocation(),player.getEyeLocation().getDirection(),
                        Config.lighteningwand_maxdistance,new IgnoreSelf(player));
                if (rr != null) {
                    Entity e = rr.getHitEntity();
                    if ( e != null) {
                        player.getWorld().strikeLightning(e.getLocation());
                        if (e instanceof LivingEntity){((LivingEntity) e).damage(Config.lighteningwand_realdamage);}
                        inv.removeItem(LighteningWand.costs);
                        return;
                    }
                }
                rr = player.rayTraceBlocks(Config.lighteningwand_maxdistance);
                if (rr != null) {
                    if (rr.getHitBlock() != null) {
                        player.getWorld().strikeLightning(rr.getHitBlock().getLocation());
                        inv.removeItem(LighteningWand.costs);
                    }
                }
            }
        }
    }
}
