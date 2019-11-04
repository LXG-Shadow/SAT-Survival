package com.lxgshadow.customitem.UltimateWeapon;

import com.lxgshadow.customitem.Config;
import com.lxgshadow.customitem.Main;

import com.lxgshadow.customitem.Messages;
import com.lxgshadow.customitem.energySystem.EnergyManager;
import com.lxgshadow.customitem.utils.EtcUtils;
import com.lxgshadow.customitem.utils.ItemUtils;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagInt;
import net.minecraft.server.v1_14_R1.NBTTagList;
import net.minecraft.server.v1_14_R1.NBTTagString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.security.MessageDigest;
import java.util.*;

public class WitherSword {
    static int energyCost = 50;
    static PotionEffectType effectType = PotionEffectType.WITHER;
    private static ItemStack item;
    private static String name = "Wither Sword";
    private static String dpName = ChatColor.GOLD+name;
    static String regName = name.replace(" ","_").toLowerCase();
    private static String corename = "Wither Sword Core";
    static ItemStack core;
    private static String[] lores = {
            ChatColor.GOLD+""+ChatColor.ITALIC+"Ultimate Weapon",
            ChatColor.WHITE+""+ChatColor.ITALIC+"Description: I Am Withering",
            ChatColor.WHITE+""+ChatColor.ITALIC+"*Using " +energyCost +" energy for launching a wither skull",
            ChatColor.BLACK+"Register Name: ["+regName+"]"
    };

    public static ItemStack getItem() {
        return item;
    }

    public static void createRecipe() {
        createCore();
        item = createBaseSword();
        item.addEnchantment(Enchantment.DAMAGE_ALL, 5);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(dpName);
        meta.setUnbreakable(true);
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(), regName), item);
        r.shape("121", "303", "121");
        r.setIngredient('0', core.getType());
        r.setIngredient('1', Material.COAL_BLOCK);
        r.setIngredient('2', Material.DIAMOND_SWORD);
        r.setIngredient('3', Material.DIAMOND_BLOCK);
        Main.getInstance().getServer().addRecipe(r);
        Main.getInstance().getServer().getPluginManager().registerEvents(new WitherSwordListener(), Main.getInstance());
    }

    private static void createCore() {
        core = new ItemStack(Material.NETHER_STAR, 1);
        core.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        ItemMeta m = core.getItemMeta();
        m.setDisplayName(corename);
        List<String> l = new ArrayList<>();
        l.add("Description: The core for Wither Sword");
        m.setLore(l);
        core.setItemMeta(m);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(), corename.replace(" ", "_").toLowerCase()), core);
        r.shape("111", "101", "111");
        r.setIngredient('1', Material.WITHER_SKELETON_SKULL);
        r.setIngredient('0', Material.NETHER_STAR);
        Main.getInstance().getServer().addRecipe(r);
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
        NBTTagCompound damage = new NBTTagCompound();
        damage.set("AttributeName", new NBTTagString("generic.attackDamage"));
        damage.set("Slot", new NBTTagString("mainHand"));
        damage.set("Name", new NBTTagString("generic.attackDamage"));
        damage.set("Amount", new NBTTagInt(18/*Item damage*/));
        damage.set("Operation", new NBTTagInt(0));
        damage.set("UUIDLeast", new NBTTagInt(894654));
        damage.set("UUIDMost", new NBTTagInt(2872));
        modifiers.add(damage);

        compound.set("AttributeModifiers", modifiers);
        nmsStack.setTag(compound);
        item = CraftItemStack.asBukkitCopy(nmsStack);
        return item;
    }
}

class WitherSwordListener implements Listener {

    @EventHandler
    public void onRightclick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if ((EnergyManager.have(event.getPlayer(),WitherSword.energyCost)) &&
                    (ItemUtils.isRegisterNameSimilar(WitherSword.regName,event.getPlayer().getInventory().getItemInMainHand())
                            || ItemUtils.isRegisterNameSimilar(WitherSword.regName,event.getPlayer().getInventory().getItemInOffHand())))
            {
                WitherSkull w = event.getPlayer().launchProjectile(WitherSkull.class);
                w.setVelocity((event.getPlayer().getEyeLocation().getDirection().multiply(Config.withersword_skullspeed)));
                w.setCharged(true);
                w.setCustomName("WitherSwordSkull");
                EnergyManager.change(event.getPlayer(),-WitherSword.energyCost,true);
            }
        }
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        if (event.getEntity() instanceof WitherSkull) {
            String cn = ((WitherSkull) event.getEntity()).getCustomName();
            if (cn == null) {
                return;
            }
            if ((cn.equals("WitherSwordSkull") && (!Config.withersword_skullbreak))) {
                event.blockList().clear();
            }
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if ((!event.getInventory().contains(WitherSword.core)) && event.getRecipe().getResult().isSimilar(WitherSword.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof WitherSkull){
            if (event.getDamager().getCustomName() == null){
                return;
            }
            if (event.getDamager().getCustomName().equals("WitherSwordSkull") && (event.getEntity() instanceof LivingEntity)){
                ((LivingEntity)event.getEntity()).damage(Config.withersword_realdamage);
            }
        }
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        PlayerInventory inv = ((Player) event.getDamager()).getInventory();
        if (ItemUtils.isRegisterNameSimilar(WitherSword.regName,inv.getItemInMainHand())) {
            if (Math.random() < Config.withersword_witherpotential) {
                if (event.getEntity() instanceof LivingEntity) {
                    EtcUtils.sendMessageIfPlayer(event.getEntity(), Messages.withersword_effect_victim);
                    EtcUtils.sendMessageIfPlayer(event.getDamager(), Messages.withersword_effect_murderer);
                    ((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(WitherSword.effectType, (new Random().nextInt(10)+1)*20, 1),true);
                }
            }
        }
    }
}