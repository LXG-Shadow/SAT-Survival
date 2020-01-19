package com.lxgshadow.customitem.ArkNightsCollection;

import com.lxgshadow.customitem.Main;
import com.lxgshadow.customitem.utils.IgnoreSelf;
import com.lxgshadow.customitem.utils.ItemUtils;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagInt;
import net.minecraft.server.v1_14_R1.NBTTagList;
import net.minecraft.server.v1_14_R1.NBTTagString;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class ChiXiao {
    private static ItemStack item;
    private static ChatColor itemColor = ChatColor.DARK_RED;
    private static String name = "ChiXiao";
    private static String dpName = itemColor+"赤霄";
    public static String regName = name.replace(" ","_").toLowerCase();
    private static String[] lores = {
            ChatColor.GOLD+""+ChatColor.ITALIC+"Ultimate Weapon",
            ChatColor.GOLD+""+ChatColor.UNDERLINE+"*明日方舟收藏品*",
            ChatColor.BLACK+"Register Name: ["+regName+"]"
    };

    public static ItemStack getItem() {
        return item;
    }
    public static ChatColor getItemColor(){return itemColor;}

    public static void createRecipe(){
        item = createBaseSword();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(dpName);
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        Main.getInstance().getServer().getPluginManager().registerEvents(new ChiXiaoListener(),Main.getInstance());
    }

    // from https://bukkit.org/threads/how-do-i-add-attributes-to-a-itemstack.404299/
    private static ItemStack createBaseSword() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);

        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = nmsStack.getTag();
        if (compound == null) {
            compound = new NBTTagCompound();
            nmsStack.setTag(compound);
            compound = nmsStack.getTag();
        }
        NBTTagList modifiers = new NBTTagList();

        //Attributes are set like this:
        NBTTagCompound mainHandDamage = new NBTTagCompound();
        mainHandDamage.set("Slot", new NBTTagString("mainHand"));
        mainHandDamage.set("AttributeName", new NBTTagString("generic.attackDamage"));
        mainHandDamage.set("Name", new NBTTagString("generic.attackDamage"));
        mainHandDamage.set("Amount", new NBTTagInt(1024));
        mainHandDamage.set("Operation", new NBTTagInt(0));
        mainHandDamage.set("UUIDLeast", new NBTTagInt(894654));
        mainHandDamage.set("UUIDMost", new NBTTagInt(2872));
        modifiers.add(mainHandDamage);

        NBTTagCompound mainHandSpeed = new NBTTagCompound();
        mainHandSpeed.set("Slot", new NBTTagString("mainHand"));
        mainHandSpeed.set("AttributeName", new NBTTagString("generic.attackSpeed"));
        mainHandSpeed.set("Name", new NBTTagString("generic.attackSpeed"));
        mainHandSpeed.set("Amount", new NBTTagInt(1024));
        mainHandSpeed.set("Operation", new NBTTagInt(0));
        mainHandSpeed.set("UUIDLeast", new NBTTagInt(894654));
        mainHandSpeed.set("UUIDMost", new NBTTagInt(2872));
        modifiers.add(mainHandSpeed);

        compound.set("AttributeModifiers", modifiers);
        nmsStack.setTag(compound);
        item = CraftItemStack.asBukkitCopy(nmsStack);
        return item;
    }
}
class ChiXiaoListener implements Listener{
    HashMap<UUID, List<Integer>> trigger;
    public ChiXiaoListener(){
        trigger = new HashMap<>();
    }
    @EventHandler
    public void onClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        PlayerInventory inv = player.getInventory();
        if (!ItemUtils.isRegisterNameSimilar(ChiXiao.regName,inv.getItemInMainHand())){return;}
        if (!trigger.containsKey(player.getUniqueId())){
            trigger.put(player.getUniqueId(),new ArrayList<>());
        }
        int val = -1;
        // left 0 right 1
        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            val = 0;
        }else if (event.getAction() == Action.RIGHT_CLICK_AIR){
            val = 1;
        }else {return;}
        if (trigger.get(player.getUniqueId()).size() == 0){
            new BukkitRunnable(){
                @Override
                public void run() {
                    trigger.get(player.getUniqueId()).clear();
                }
            }.runTaskLater(Main.getInstance(),2*20);
        }
        trigger.get(player.getUniqueId()).add(val);
        if (trigger.get(player.getUniqueId()).size() == 3){
            ChiXiaoSkills.choose(player,
                    trigger.get(player.getUniqueId()).get(0),
                    trigger.get(player.getUniqueId()).get(1),
                    trigger.get(player.getUniqueId()).get(2));
        }
    }

}

class ChiXiaoSkills{
    public static void choose(Player player,int s1,int s2,int s3){
        if (s1 == 1 && s2 == 1 && s3 == 0){
            BaDao(player);
            return;
        }
        if (s1 == 0 && s2 == 0 && s3 == 0){
            QiaoJi(player);
        }
    }

    public static void BaDao(Player player){
        Location location = player.getLocation();
        Vector direction = player.getEyeLocation().getDirection();
        // lift up to the middle of player's height
        location.add(0,0.8,0);
        // front
        Location temp;
        HashSet<LivingEntity> livingEntities = new HashSet<>();
        for (double i=0.2;i<=6;i+=0.2){
            temp = location.clone().add(direction.clone().multiply(i));
            // particle effect
            for (int j=0;j<2;j++){
                player.getWorld().spawnParticle(Particle.REDSTONE,temp,
                        0,2,2,2,new Particle.DustOptions(Color.RED,2F));
            }
            for (Entity entity:player.getWorld().getNearbyEntities(temp,
                    1.5,1.5,1.5,new IgnoreSelf(player))){
                if (entity instanceof LivingEntity){
                    livingEntities.add((LivingEntity) entity);
                }
            }
        }
        for (Entity entity:player.getNearbyEntities(3,3,3)){
            if (entity instanceof LivingEntity){
                livingEntities.add((LivingEntity) entity);
            }
        }
        for (LivingEntity entity:livingEntities){
            entity.damage(1024,player);
        }
    }

    public static void BaDao1(Player player){
        Location location = player.getLocation();
        Location eyeLocation = player.getEyeLocation();
        double yaw = eyeLocation.getYaw();
        if (yaw < 0){
            yaw += 360;
        }
        // Yaw
        // positive z 0
        // negative z 180
        // positive x 270
        // negative x 90
        Vector front,left,right;
        if (yaw >= 315 || yaw < 45){
            front = new Vector(0,0,1);
        }
        else if (yaw >= 45 && yaw < 135){
            front = new Vector(-1,0,0);
        }
        else if (yaw >= 135 && yaw < 225){
            front = new Vector(0,0,-1);
        }
        //if (yaw >= -135 && yaw <-45){
        else {
            front = new Vector(1,0,0);
        }
        // lift up to the middle of player's height
        location.add(0,0.8,0);
        // front
        for (double i=0.2;i<=6;i+=0.2){
            // particle effect
            for (int j=0;j<2;j++){
                player.getWorld().spawnParticle(Particle.REDSTONE,location.clone().add(front.clone().multiply(i)),
                        0,2,2,2,new Particle.DustOptions(Color.RED,2F));
            }
        }
        HashSet<LivingEntity> livingEntities = new HashSet<>();
        for (int i=0;i<=6;i++){
            for (Entity entity:player.getWorld().getNearbyEntities(location.clone().add(front.clone().multiply(i)),
                    1.5,1.5,1.5,new IgnoreSelf(player))){
                if (entity instanceof LivingEntity){
                    livingEntities.add((LivingEntity) entity);
                }
            }
        }
        for (Entity entity:player.getNearbyEntities(3,3,3)){
            if (entity instanceof LivingEntity){
                livingEntities.add((LivingEntity) entity);
            }
        }
        for (LivingEntity entity:livingEntities){
            entity.damage(1024,player);
        }
    }

    public static void QiaoJi(Player player){
        HashSet<LivingEntity> livingEntities = new HashSet<>();
        for (Entity entity:player.getNearbyEntities(3,3,3)){
            if (entity instanceof LivingEntity){
                livingEntities.add((LivingEntity) entity);
            }
        }
        for (LivingEntity le : livingEntities){
            le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20,66),true);
            le.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,20,-10),true);
        }
    }

    public static void JueYing(Player player){
    }
}
