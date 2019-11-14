package com.lxgshadow.customitem.commands;

import com.lxgshadow.customitem.AdvancedTool.LumbererAxe;
import com.lxgshadow.customitem.AdvancedTool.MinerPickaxe;
import com.lxgshadow.customitem.AdvancedWeapon.BowOfBlackTea;
import com.lxgshadow.customitem.AdvancedWeapon.PotionWand;
import com.lxgshadow.customitem.ArkNightsCollection.ChiXiao;
import com.lxgshadow.customitem.ArkNightsCollection.EyjafjallaVolcano;
import com.lxgshadow.customitem.Main;
import com.lxgshadow.customitem.UltimateWeapon.AimbotBow;
import com.lxgshadow.customitem.UltimateWeapon.LighteningWand;
import com.lxgshadow.customitem.UltimateWeapon.SoulSword;
import com.lxgshadow.customitem.UltimateWeapon.WitherSword;
import com.lxgshadow.customitem.interfaces.CustomItemList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class getCICommand implements CommandExecutor,TabCompleter {
    private HashMap<String,Class> items;

    public getCICommand(){
        items = new HashMap<>();
        for (CustomItemList item:CustomItemList.values()){
            items.put(item.regName,item.clazz);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (!(sender instanceof Player)){return new ArrayList<>();}
        if (!sender.isOp()){
            return new ArrayList<>();
        }
        switch (args.length) {
            case 1: {
                for (String s : items.keySet()) {
                    if (StringUtil.startsWithIgnoreCase(s, args[0].toLowerCase())) {
                        completions.add(s);
                    }
                }
                return completions;
            }
        }
        //todo:changeto immutableList.of()
        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){return true;}
        if (!sender.isOp()){
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("getcustomitem")){
            if (args.length<1){
                return false;
            }
            if (items.containsKey(args[0])){
                ((Player)sender).getInventory().addItem(getItem(items.get(args[0])));
            }
        }
        return true;
    }

    private ItemStack getItem(Class c){
        try {
            return (ItemStack) c.getMethod("getItem").invoke(null);
        }catch (Exception e){
            return null;
        }
    }
}
