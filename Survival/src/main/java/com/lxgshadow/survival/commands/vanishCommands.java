package com.lxgshadow.survival.commands;

import com.lxgshadow.survival.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.UUID;

public class vanishCommands implements CommandExecutor {
    private final JavaPlugin plugin;
    private static HashSet<UUID> vanishList = new HashSet<>();

    public static HashSet<UUID> getVanishList(){return vanishList;}

    public vanishCommands(JavaPlugin p) {
        this.plugin = p;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("vanish")) {
            if (!(sender instanceof Player)) {
                return true;
            }
            if (!sender.isOp()){return true;}
            plugin.getServer().getOnlinePlayers().forEach((p)->p.hidePlayer(plugin,(Player) sender));
            vanishList.add(((Player) sender).getUniqueId());
            sender.sendMessage(Messages.vanish_msg1);
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("unvanish")) {
            if (!(sender instanceof Player)) {
                return true;
            }
            if (!sender.isOp()){return true;}
            plugin.getServer().getOnlinePlayers().forEach((p)->p.showPlayer(plugin,(Player) sender));
            vanishList.remove(((Player) sender).getUniqueId());
            sender.sendMessage(Messages.vanish_msg2);
            return true;
        }
        return true;
    }
}
