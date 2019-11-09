package com.lxgshadow.customitem.UltimateWeapon;

import com.lxgshadow.customitem.Config;
import com.lxgshadow.customitem.Messages;
import com.lxgshadow.customitem.interfaces.CustomItems;
import com.lxgshadow.customitem.Main;
import com.lxgshadow.customitem.utils.EtcUtils;
import com.lxgshadow.customitem.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class SoulSword implements CustomItems {
    private static ItemStack item;
    private static String name = "Soul Sword";
    private static String dpName = ChatColor.GOLD+name;
    public static String regName = name.replace(" ","_").toLowerCase();
    static HashMap<Integer,Integer> levelup = new HashMap<>();
    private static String[] lores = {
            ChatColor.GOLD+""+ChatColor.ITALIC+"Ultimate Weapon",
            ChatColor.WHITE+""+ChatColor.ITALIC+"Description: Blood Strength",
            ChatColor.WHITE+""+ChatColor.ITALIC+"Soul: 0",
            ChatColor.BLACK+"Register Name: ["+regName+"]"
    };


    public static ItemStack getItem() {
        return item;
    }

    public static void createRecipe(){
        getConfig();
        item = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(dpName);
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(),regName),item);
        r.shape("123","454","767");
        r.setIngredient('1',Material.CREEPER_HEAD);
        r.setIngredient('2',Material.ZOMBIE_HEAD);
        r.setIngredient('3',Material.SKELETON_SKULL);
        r.setIngredient('4',Material.REDSTONE_BLOCK);
        r.setIngredient('5',Material.DIAMOND_SWORD);
        r.setIngredient('6',Material.GHAST_TEAR);
        r.setIngredient('7',Material.SOUL_SAND);
        Main.getInstance().getServer().addRecipe(r);
        Main.getInstance().getServer().getPluginManager().registerEvents(new SoulSwordListener(),Main.getInstance());
    }

    private static void getConfig(){
        //todo: write level up setting in config
        levelup.put((int)Math.pow(5,1),1);
        levelup.put((int)Math.pow(5,2),2);
        levelup.put((int)Math.pow(5,3),3);
        levelup.put((int)Math.pow(5,4),4);
        levelup.put((int)Math.pow(5,5),5);
        levelup.put((int)Math.pow(5,6),6);
        levelup.put((int)Math.pow(5,7),7);
        levelup.put((int)Math.pow(5,8),8);
        levelup.put((int)Math.pow(5,9),9);
        levelup.put((int)Math.pow(5,10),10);

    }
}

class SoulSwordListener implements Listener{
    @EventHandler
    public void onKill(EntityDeathEvent event){
        Player player = event.getEntity().getKiller();
        if (player == null){return;}
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if (meta == null){return;}
        if (!meta.hasLore()){return;}
        List<String> lores = meta.getLore();
        String regName = ItemUtils.getRegisterName(lores);
        if (SoulSword.regName.equals(regName)){
            for (int i=0;i<lores.size();i++){
                if (ChatColor.stripColor(lores.get(i)).startsWith("Soul: ")){
                    int soul = Integer.parseInt(ChatColor.stripColor(lores.get(i)).replace("Soul: ",""))+1;
                    if (SoulSword.levelup.get(soul) != null){
                        meta.addEnchant(Enchantment.DAMAGE_ALL,SoulSword.levelup.get(soul),true);
                    }
                    lores.set(i, ChatColor.WHITE+""+ChatColor.ITALIC+"Soul: "+soul);
                    break;
                }
            }
            meta.setLore(lores);
            item.setItemMeta(meta);
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        PlayerInventory inv = ((Player) event.getDamager()).getInventory();
        if (ItemUtils.isRegisterNameSimilar(SoulSword.regName,inv.getItemInMainHand())) {
            if (EtcUtils.chance(3,10)) {
                if (event.getEntity() instanceof LivingEntity) {
                    EtcUtils.sendMessageIfPlayer(event.getEntity(), Messages.soulsword_effect_victim);
                    EtcUtils.sendMessageIfPlayer(event.getDamager(), Messages.soulsword_effect_murderer);
                    Entity victim = event.getEntity();
                    int bleedingtime = EtcUtils.randInt(2,10);
                    new BukkitRunnable(){
                        int time = 0;
                        public void run(){
                            if (time>bleedingtime){
                                return;
                            }
                            ((LivingEntity) victim).damage(2);
                            time++;
                        }
                    }.runTaskTimer(Main.getInstance(),0,20);
                }
            }
        }
    }
}