package com.lxgshadow.customitem.ArkNightsCollection;

import com.lxgshadow.customitem.interfaces.CustomItems;
import com.lxgshadow.customitem.Main;
import com.lxgshadow.customitem.energySystem.EnergyManager;
import com.lxgshadow.customitem.utils.ItemUtils;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EyjafjallaVolcano implements CustomItems {
    private static ItemStack item;
    private static String name = "Eyjafjalla Volcano";
    private static String dpName = ChatColor.GOLD+"艾雅法拉 · 火山";
    public static String regName = name.replace(" ","_").toLowerCase();
    private static String[] lores = {
            ChatColor.GOLD+""+ChatColor.ITALIC+"Ultimate Weapon",
            ChatColor.WHITE+""+ChatColor.ITALIC+"隐秘的极地精灵,冰与火的孕育之地",
            ChatColor.GOLD+""+ChatColor.UNDERLINE+"*明日方舟收藏品*",
            ChatColor.BLACK+"Register Name: ["+regName+"]"
    };
    static double volcanoDelay;
    static double volcanoMaximum;
    static double volcanoRange;
    static double volcanoDamage;
    static double volcanoSpeed;
    static int energyCost;
    static int energyCostForSingleAttack;


    public static ItemStack getItem() {
        return item;
    }

    public static void createRecipe(){
        getConfig();
        item = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(dpName);
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(),regName),item);
        r.shape("   ","   ","000");
        r.setIngredient('0',Material.BLAZE_POWDER);
        Main.getInstance().getServer().addRecipe(r);
        Main.getInstance().getServer().getPluginManager().registerEvents(new EyjafjallaVolcanoListener(),Main.getInstance());
    }

    public static void getConfig(){
        volcanoDelay = 0.6;
        volcanoMaximum = 5;
        volcanoRange = 10;
        volcanoDamage = 16;
        volcanoSpeed = 0.7;
        energyCost = 250;
        energyCostForSingleAttack = 5;
    }
}

class EyjafjallaVolcanoListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        PlayerInventory inv = player.getInventory();
        if (event.getAction() == Action.RIGHT_CLICK_AIR
                && ItemUtils.isRegisterNameSimilar(EyjafjallaVolcano.regName,inv.getItemInMainHand())) {
            if (EnergyManager.have(player,EyjafjallaVolcano.energyCost)){
                BukkitRunnable particle = new BukkitRunnable(){
                    double t = 0;
                    @Override
                    public void run(){
                        for (int i=0;i<2;i++){
                        Location loc1 = player.getLocation();
                        loc1.add(0.8*Math.sin(t/16*Math.PI),1.3*Math.abs(Math.sin(t/100*Math.PI)),0.8*Math.cos(t/16*Math.PI));
                        Location loc2 = player.getLocation();
                        loc2.add(0.8*Math.sin(t/16*Math.PI+Math.PI),1.3*Math.abs(Math.sin(t/100*Math.PI+Math.PI)),0.8*Math.cos(t/16*Math.PI+Math.PI));
                        player.spawnParticle(Particle.FLAME,loc1,0);
                        player.spawnParticle(Particle.FLAME,loc2,0);
                        t++;}
                    }
                };
                playerFloat(player,particle);
                new BukkitRunnable(){
                    @Override
                    public void run(){
                        if (!EnergyManager.have(player,EyjafjallaVolcano.energyCostForSingleAttack)){
                            playerDescend(player,particle);
                            this.cancel();
                            return;
                        }
                        if (!ItemUtils.isRegisterNameSimilar(EyjafjallaVolcano.regName,inv.getItemInMainHand())){
                            playerDescend(player,particle);
                            EnergyManager.set(player,0);
                            this.cancel();
                            return;
                        }
                        List<Entity> entities = player.getNearbyEntities(EyjafjallaVolcano.volcanoRange,EyjafjallaVolcano.volcanoRange,EyjafjallaVolcano.volcanoRange);
                        Collections.shuffle(entities);
                        int count = 0;
                        for (Entity entity: entities){
                            if (count >= EyjafjallaVolcano.volcanoMaximum){break;}
                            if (entity instanceof LivingEntity){
                                fire(player,(LivingEntity)entity);
                                count++;
                            }
                        }
                        EnergyManager.change(player,-EyjafjallaVolcano.energyCostForSingleAttack,true);
                    }
                }.runTaskTimer(Main.getInstance(),0,(int)(EyjafjallaVolcano.volcanoDelay*20));
            }
        }

    }

    @EventHandler
    public void fireballhit(ProjectileHitEvent event){
        if (event.getEntity().getCustomName() != null && event.getEntity().getCustomName().equals("EyjafjallaVolcano")){
            if (event.getHitEntity() != null && event.getHitEntity() instanceof LivingEntity){
                ((LivingEntity) event.getHitEntity()).damage(EyjafjallaVolcano.volcanoDamage);
            }
        }
    }

    @EventHandler
    public void preventExplode(EntityExplodeEvent event){
        if (event.getEntity().getCustomName() != null && event.getEntity().getCustomName().equals("EyjafjallaVolcano")){
            event.blockList().clear();
        }
    }

    private void playerFloat(Player player,BukkitRunnable particle){
        particle.runTaskTimer(Main.getInstance(),0,0);
        player.setGravity(false);
        player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,3,2));
    }

    private void playerDescend(Player player,BukkitRunnable particle){
        player.setGravity(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,20,2));
        particle.cancel();
    }

    private void fire(Player player,LivingEntity target){
        SmallFireball fb = player.launchProjectile(SmallFireball.class);
        fb.setCustomName("EyjafjallaVolcano");
        fb.setCustomNameVisible(false);
        fb.setBounce(false);
        fb.setGravity(false);
        // prevent from set a fire
        fb.setIsIncendiary(false);
        fb.setVelocity(traceAlgorithm3(fb,target).add(new Vector(0,1,0)).normalize().multiply(EyjafjallaVolcano.volcanoSpeed));
        new BukkitRunnable(){
            @Override
            public void run(){
                if (fb.isDead() || target.isDead()){
                    fb.remove();
                    this.cancel();
                    return;
                }
                fb.setVelocity(traceAlgorithm3(fb,target).multiply(EyjafjallaVolcano.volcanoSpeed));
            }
        }.runTaskTimer(Main.getInstance(),3,1);

    }

    private Vector traceAlgorithm3(Projectile projectile, LivingEntity target) {
        return target.getEyeLocation().toVector().subtract(projectile.getLocation().toVector()).normalize();
    }
}