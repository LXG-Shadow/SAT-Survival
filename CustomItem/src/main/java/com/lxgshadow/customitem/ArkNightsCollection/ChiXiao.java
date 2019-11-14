package com.lxgshadow.customitem.ArkNightsCollection;

import com.lxgshadow.customitem.Main;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagInt;
import net.minecraft.server.v1_14_R1.NBTTagList;
import net.minecraft.server.v1_14_R1.NBTTagString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class ChiXiao {
    private static ItemStack item;
    private static String name = "ChiXiao";
    private static String dpName = ChatColor.DARK_RED+"赤霄";
    public static String regName = name.replace(" ","_").toLowerCase();
    private static String[] lores = {
            ChatColor.GOLD+""+ChatColor.ITALIC+"Ultimate Weapon",
            ChatColor.GOLD+""+ChatColor.UNDERLINE+"*明日方舟收藏品*",
            ChatColor.BLACK+"Register Name: ["+regName+"]"
    };

    public static ItemStack getItem() {
        return item;
    }

    public static void createRecipe(){
        item = createBaseSword();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(dpName);
        meta.setLore(new ArrayList<>(Arrays.asList(lores)));
        item.setItemMeta(meta);
        //Main.getInstance().getServer().getPluginManager().registerEvents(new LumbererAxeListener(),Main.getInstance());
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

class ChiXiaoSkills{
    public static void BaDao(){

    }
}
