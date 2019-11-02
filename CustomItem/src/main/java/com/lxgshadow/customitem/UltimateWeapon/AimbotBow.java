package com.lxgshadow.customitem.UltimateWeapon;

import com.lxgshadow.customitem.Main;
import com.lxgshadow.customitem.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AimbotBow {
    private static ItemStack item;
    private static String name = "Aimisery Bow";
    private static String dpName = ChatColor.GOLD+name;
    public static String regName = name.replace(" ","_").toLowerCase();
    private static String[] lores = {
            ChatColor.GOLD+""+ChatColor.ITALIC+"Ultimate Weapon",
            ChatColor.WHITE+""+ChatColor.ITALIC+"Description: I got you in my sights",
            ChatColor.BLACK+"Register Name: ["+regName+"]"
    };

    public static ItemStack getItem() {
        return item;
    }

    public static void createRecipe(){
        item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(dpName);
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(),regName),item);
        r.shape(" 13","402"," 13");
        r.setIngredient('0',Material.BOW);
        r.setIngredient('1',Material.BLAZE_ROD);
        r.setIngredient('2',Material.NETHER_STAR);
        r.setIngredient('3',Material.HEART_OF_THE_SEA);
        r.setIngredient('4',Material.COMPASS);
        Main.getInstance().getServer().addRecipe(r);
        Main.getInstance().getServer().getPluginManager().registerEvents(new AimbotBowListener(),Main.getInstance());
    }
}

class AimbotBowListener implements Listener{
    @EventHandler
    public void onShoot(EntityShootBowEvent event){
        if (event.getBow() != null && ItemUtils.isRegisterNameSimilar(AimbotBow.regName,event.getBow())){
            if (!(event.getProjectile() instanceof Arrow)){
                return;
            }
            Arrow arrow = (Arrow) event.getProjectile();
            List<Entity> es = arrow.getNearbyEntities(10D,10D,10D);
            LivingEntity shooter = event.getEntity();
            new BukkitRunnable(){
                @Override
                public void run(){
                    if (arrow.isDead() || arrow.isOnGround()){
                        this.cancel();
                    }
                    LivingEntity target = null;
                    double minDis = 10000000000D;
                    for (Entity e:arrow.getNearbyEntities(15D,15D,15D)){
                        if (!(e instanceof LivingEntity)){continue;}
                        if (e == shooter){continue;}
                        if (arrow.getLocation().distance(((LivingEntity) e).getEyeLocation())<minDis){
                            minDis = arrow.getLocation().distance(((LivingEntity) e).getEyeLocation());
                            target = (LivingEntity) e;
                        }
                    }
                    if (target != null){
                        if (target.isDead()){this.cancel();}
                        arrow.setVelocity(velocityAlgorithm3(arrow,target));
                    }
                }

            }.runTaskTimer(Main.getInstance(),5,10);
        }
    }

    private Vector velocityAlgorithm1(Arrow arrow, LivingEntity target){
        return arrow.getVelocity().multiply(target.getEyeLocation().toVector().subtract(arrow.getLocation().toVector()).normalize().divide(arrow.getVelocity().normalize()));
    }

    private Vector velocityAlgorithm2(Arrow arrow, LivingEntity target) {
        // formal tracing algorithm
        double vDirectionX = target.getEyeLocation().getX() - arrow.getLocation().getX();
        double vDirectionY = target.getEyeLocation().getY() - arrow.getLocation().getY();
        double vDirectionZ = target.getEyeLocation().getZ() - arrow.getLocation().getZ();
        // normalize
        double len = Math.sqrt(vDirectionX*vDirectionX+vDirectionY*vDirectionY+vDirectionZ*vDirectionZ);
        double vDNX = vDirectionX / len;
        double vDNY = vDirectionY / len;
        double vDNZ = vDirectionZ / len;
        // add vector
        double x = arrow.getVelocity().getX()+ (vDNX - arrow.getVelocity().normalize().getX());
        double y = arrow.getVelocity().getY()+ (vDNY - arrow.getVelocity().normalize().getY());
        double z = arrow.getVelocity().getZ()+ (vDNZ - arrow.getVelocity().normalize().getZ());
        return new Vector(x,y,z);
    }

    private Vector velocityAlgorithm3(Arrow arrow, LivingEntity target) {
//        // formal tracing algorithm
//        // get the arrow to target position vector
//        double DirectionX = target.getEyeLocation().getX() - arrow.getLocation().getX();
//        double DirectionY = target.getEyeLocation().getY() - arrow.getLocation().getY();
//        double DirectionZ = target.getEyeLocation().getZ() - arrow.getLocation().getZ();
//        // normalize vector to get the direction only. directionNormalizedX
//        double Dlen = Math.sqrt(DirectionX*DirectionX+DirectionY*DirectionY+DirectionZ*DirectionZ);
//        double DNX = DirectionX / Dlen;
//        double DNY = DirectionY / Dlen;
//        double DNZ = DirectionZ / Dlen;
//        // re-calculate velocity
//        // get the speed of all direction
//        double VelocityX = arrow.getVelocity().getX();
//        double VelocityY = /
//        double VelocityZ = arrow.getVelocity().getZ();
//        double Vlen = Math.sqrt(VelocityX*VelocityX+VelocityY*VelocityY+VelocityZ*VelocityZ);
//        double x = DNX * Vlen;
//        double y = DNY * Vlen;
//        double z = DNZ * Vlen;
//        return new Vector(x,y,z);
        return target.getEyeLocation().toVector().subtract(arrow.getLocation().toVector()).normalize().multiply(arrow.getVelocity().length());
    }

    private Vector velocityAlgorithm4(Arrow arrow, LivingEntity target) {
        return target.getEyeLocation().toVector().subtract(arrow.getLocation().toVector()).normalize().add(arrow.getVelocity().normalize().multiply(0.456789)).normalize()
                .multiply(arrow.getVelocity().length());
    }
}