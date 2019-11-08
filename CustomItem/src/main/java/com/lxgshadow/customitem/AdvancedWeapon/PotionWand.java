package com.lxgshadow.customitem.AdvancedWeapon;

import com.lxgshadow.customitem.interfaces.CustomItems;
import com.lxgshadow.customitem.Main;
import com.lxgshadow.customitem.Messages;
import com.lxgshadow.customitem.energySystem.EnergyManager;
import com.lxgshadow.customitem.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PotionWand implements CustomItems {
    static int energyCost = 10;
    private static ItemStack item;
    private static String name = "Potion Wand";
    private static String dpName = ChatColor.AQUA + name;
    public static String regName = name.replace(" ", "_").toLowerCase();
    private static String[] lores = {
            ChatColor.AQUA + "" + ChatColor.ITALIC + "Advanced Weapon",
            ChatColor.WHITE + "" + ChatColor.ITALIC + "Description: Avada Kedavra! ",
            ChatColor.WHITE + "" + ChatColor.ITALIC + "Effect: Speed-1",
            ChatColor.BLACK + "Register Name: [" + regName + "]"
    };
    static int duration1;
    static int duration2;
    static HashMap<String, PotionEffectType> effectsHashMap;
    static List<String> effects;

    public static ItemStack getItem() {
        return item;
    }

    public static void createRecipe() {
        getConfig();
        item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(dpName);
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        item.setItemMeta(meta);
        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(), regName), item);
        r.shape("414", "323", " 2 ");
        r.setIngredient('1', Material.ENDER_EYE);
        r.setIngredient('2', Material.BLAZE_ROD);
        r.setIngredient('3', Material.REDSTONE);
        r.setIngredient('4', Material.GLOWSTONE_DUST);
        Main.getInstance().getServer().addRecipe(r);
        Main.getInstance().getServer().getPluginManager().registerEvents(new PotionWandLisnter(), Main.getInstance());
    }

    public static void getConfig() {
        duration1 = 60;
        duration2 = 30;
        effectsHashMap = new HashMap<>();
        effects = new ArrayList<>();
        effectsHashMap.put("Speed", PotionEffectType.SPEED);
        effectsHashMap.put("JumpBoost", PotionEffectType.JUMP);
        effectsHashMap.put("Regeneration", PotionEffectType.REGENERATION);
        effectsHashMap.put("Heal", PotionEffectType.HEAL);
        effectsHashMap.put("FastDigging", PotionEffectType.FAST_DIGGING);
        effectsHashMap.put("Strength", PotionEffectType.INCREASE_DAMAGE);
        effectsHashMap.put("Resistance", PotionEffectType.DAMAGE_RESISTANCE);
        effectsHashMap.put("FireResistance", PotionEffectType.FIRE_RESISTANCE);
        effectsHashMap.put("NightVision", PotionEffectType.NIGHT_VISION);
        effectsHashMap.put("WaterBreathing", PotionEffectType.WATER_BREATHING);
        effectsHashMap.put("Levitation", PotionEffectType.LEVITATION);
        effectsHashMap.put("SlowFalling", PotionEffectType.SLOW_FALLING);
        effects.add("Speed");
        effects.add("JumpBoost");
        effects.add("Regeneration");
        effects.add("Heal");
        effects.add("FastDigging");
        effects.add("Strength");
        effects.add("Resistance");
        effects.add("FireResistance");
        effects.add("NightVision");
        effects.add("WaterBreathing");
        effects.add("Levitation");
        effects.add("SlowFalling");
    }
}

class PotionWandLisnter implements Listener {
    @EventHandler
    public void onRightclick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inv = player.getInventory();
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if ((EnergyManager.have(player, PotionWand.energyCost)) &&
                    (ItemUtils.isRegisterNameSimilar(PotionWand.regName, inv.getItemInMainHand()) ||
                            ItemUtils.isRegisterNameSimilar(PotionWand.regName, inv.getItemInOffHand()))) {
                ItemStack item = ItemUtils.isRegisterNameSimilar(PotionWand.regName, inv.getItemInMainHand()) ? inv.getItemInMainHand() : inv.getItemInOffHand();
                ItemMeta meta = item.getItemMeta();
                if (meta == null) {
                    return;
                }
                if (!meta.hasLore()) {
                    return;
                }
                List<String> lores = meta.getLore();
                String effect = "Speed";
                int amp = 0;
                for (int i = 0; i < lores.size(); i++) {
                    if (ChatColor.stripColor(lores.get(i)).startsWith("Effect: ")) {
                        String temp = ChatColor.stripColor(lores.get(i)).replace("Effect: ", "");
                        effect = temp.substring(0, temp.indexOf("-"));
                        amp = Integer.parseInt(temp.substring(temp.indexOf("-") + 1)) - 1;
                        break;
                    }
                }
                int duration;
                if (amp == 0) {
                    duration = PotionWand.duration1 * 20;
                } else {
                    duration = PotionWand.duration2 * 20;
                }
                if (effect.equals("Heal")) {
                    duration = 1;
                }
                player.addPotionEffect(
                        new PotionEffect(PotionWand.effectsHashMap.get(effect), duration, amp), true);

                EnergyManager.change(player, -PotionWand.energyCost, true);
            }
        }
    }

    @EventHandler
    public void onLeftclick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inv = player.getInventory();
        if (ItemUtils.isRegisterNameSimilar(PotionWand.regName, inv.getItemInMainHand()) ||
                ItemUtils.isRegisterNameSimilar(PotionWand.regName, inv.getItemInOffHand())) {
            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                ItemStack item = ItemUtils.isRegisterNameSimilar(PotionWand.regName, inv.getItemInMainHand()) ? inv.getItemInMainHand() : inv.getItemInOffHand();
                ItemMeta meta = item.getItemMeta();
                if ((meta == null) || (!meta.hasLore())) {
                    return;
                }
                List<String> lores = meta.getLore();
                String effect = "Speed";
                int amp = 0;
                int i;
                boolean changed = false;
                for (i = 0; i < lores.size(); i++) {
                    if (ChatColor.stripColor(lores.get(i)).startsWith("Effect: ")) {
                        String temp = ChatColor.stripColor(lores.get(i)).replace("Effect: ", "");
                        amp = Integer.parseInt(temp.substring(temp.indexOf("-") + 1)) - 1;
                        effect = temp.substring(0, temp.indexOf("-"));
                        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                            if (event.getClickedBlock().getType() == Material.GLOWSTONE) {
                                amp = 1;
                                changed = true;
                                break;
                            }
                            if (event.getClickedBlock().getType() == Material.REDSTONE_BLOCK) {
                                amp = 0;
                                changed = true;
                                break;
                            }
                            break;
                        }
                        if (event.getAction() == Action.LEFT_CLICK_AIR) {
                            int index = PotionWand.effects.indexOf(effect);
                            if (index == (PotionWand.effects.size() - 1)) {
                                effect = PotionWand.effects.get(0);
                            } else {
                                effect = PotionWand.effects.get(index + 1);
                            }
                            changed = true;
                            break;
                        }
                    }
                }
                if (changed){
                    player.sendMessage(Messages.potionwand_effectchange
                            .replace("%(e)", effect).replace("%(a)", (amp + 1) + ""));
                    lores.set(i, ChatColor.WHITE + "" + ChatColor.ITALIC + "Effect: " + effect + "-" + (amp + 1));
                    meta.setLore(lores);
                    item.setItemMeta(meta);
                }
            }

        }

    }
}
