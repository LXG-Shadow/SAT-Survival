package com.lxgshadow.customitem.commands;

import com.lxgshadow.customitem.energySystem.EnergyManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class energyCommand implements CommandExecutor,TabCompleter {
    private JavaPlugin plugin;
    public energyCommand(JavaPlugin p){this.plugin=p;}
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (!(sender instanceof Player)){return new ArrayList<>();}
        if (!sender.isOp()){
            return new ArrayList<>();
        }
        switch (args.length) {
            case 1: {
                List<String> possibles = new ArrayList<>();
                possibles.add("recover");
                for (String s : possibles) {
                    if (StringUtil.startsWithIgnoreCase(s, args[0].toLowerCase())) {
                        completions.add(s);
                    }
                }
                return completions;
            }
        }
        return new ArrayList<>();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){return true;}
        if (!sender.isOp()){
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("energy")){
            if (args.length<1){
                return false;
            }
            switch (args[0]){
                case "recover":
                    EnergyManager.set((Player)sender,EnergyManager.getMaximum((Player)sender));
                    break;
            }
        }
        return true;
    }
}
