package com.lxgshadow.survival.commands;

import com.lxgshadow.survival.Config;
import com.lxgshadow.survival.Messages;
import com.lxgshadow.survival.managers.HomeManager;
import com.lxgshadow.survival.mysql.mysqlConnection;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class homeCommands implements CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;

    public homeCommands(JavaPlugin p) {
        this.plugin = p;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (!mysqlConnection.getStatus()){
            return completions;
        }
        switch (args.length) {
            case 1: {
                List<String> possibles = new ArrayList<>();
                possibles.add("list");
                possibles.add("set");
                possibles.add("back");
                possibles.add("delete");
                for (String s : possibles) {
                    if (StringUtil.startsWithIgnoreCase(s, args[0].toLowerCase())) {
                        completions.add(s);
                    }
                }
                return completions;
            }
            case 2: {
                if (args[0].equals("set") || args[0].equals("back") || args[0].equals("delete")){
                    for (String h:new HomeManager((Player) sender).getAllHome()){
                        if (StringUtil.startsWithIgnoreCase(h, args[1].toLowerCase())) {
                            completions.add(h);
                        }
                    }
                    return completions;
                }
                break;
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            return true;
        }
        if (!mysqlConnection.getStatus()){
            sender.sendMessage(Messages.mysql_fail);
            return true;
        }

        HomeManager hm = new HomeManager((Player) sender);
        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("homelist")) {
            List<String> homes = hm.getAllHome();
            p.sendMessage(Messages.home_List1);
            p.sendMessage(Messages.home_List2.replace("%(n1)", String.valueOf(homes.size()))
                    .replace("%(n2)", String.valueOf(Config.home_maxNum - homes.size())));
            for (String h:homes){
                p.sendMessage(Messages.home_List3.replace("%(h)",h));
            }
            p.sendMessage(Messages.home_List1);
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("sethome")) {
            if (args.length < 1) {
                return false;
            }
            switch (hm.setHome(p.getLocation(), args[0])) {
                case 0: {
                    sender.sendMessage(Messages.home_SetSuccess.replace("%(h)", args[0]));
                    break;
                }
                case 1: {
                    sender.sendMessage(Messages.home_ReachLimit);
                    break;
                }
                case 2: {
                    sender.sendMessage(Messages.home_SameNameExist);
                    break;
                }
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("delhome")) {
            if (args.length<1){
                return false;
            }
            switch (hm.delHome(args[0])) {
                case -1: {
                    return false;
                }
                case 0: {
                    sender.sendMessage(Messages.home_DeleteSuccess.replace("%(h)", args[0]));
                    break;
                }
                case 1: {
                    sender.sendMessage(Messages.home_404.replace("%(h)", args[0]));
                    break;
                }
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("home")) {
            if (args.length >= 2) {
                if (args[0].equals("set")) {
                    int rs = hm.setHome(p.getLocation(), args[1]);
                    switch (rs) {
                        case 0: {
                            sender.sendMessage(Messages.home_SetSuccess.replace("%(h)", args[1]));
                            break;
                        }
                        case 1: {
                            sender.sendMessage(Messages.home_ReachLimit);
                            break;
                        }
                        case 2: {
                            sender.sendMessage(Messages.home_SameNameExist);
                            break;
                        }
                    }
                    return true;
                }
                if (args[0].equals("delete")) {
                    int rs = hm.delHome(args[1]);
                    switch (rs) {
                        case -1: {
                            return false;
                        }
                        case 0: {
                            sender.sendMessage(Messages.home_DeleteSuccess.replace("%(h)", args[1]));
                            break;
                        }
                        case 1: {
                            sender.sendMessage(Messages.home_404.replace("%(h)", args[1]));
                            break;
                        }
                    }
                    return true;
                }
                if (args[0].equals("back")) {
                    Location loc = hm.getHome(args[1]);
                    if (loc == null) {
                        sender.sendMessage(Messages.home_404.replace("%(h)", args[1]));
                    } else {
                        p.teleport(loc);
                        sender.sendMessage(Messages.home_TeleportSuccess.replace("%(h)", args[1]));
                    }
                    return true;
                }
            }
            else {
                if (args.length<1){
                    return false;
                }
                if (args[0].equals("list")) {
                    List<String> homes = hm.getAllHome();
                    p.sendMessage(Messages.home_List1);
                    p.sendMessage(Messages.home_List2.replace("%(n1)", String.valueOf(homes.size()))
                            .replace("%(n2)", String.valueOf(Config.home_maxNum - homes.size())));
                    for (String h:homes){
                        p.sendMessage(Messages.home_List3.replace("%(h)",h));
                    }
                    p.sendMessage(Messages.home_List1);
                    return true;
                }
                Location loc = hm.getHome(args[0]);
                if (loc == null) {
                    sender.sendMessage(Messages.home_404.replace("%(h)", args[0]));
                } else {
                    p.teleport(loc);
                    sender.sendMessage(Messages.home_TeleportSuccess.replace("%(h)", args[0]));
                }
                return true;
            }
        }
        return false;
    }
}
