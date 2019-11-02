package com.lxgshadow.survival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class enderchestCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){return true;}
        if (cmd.getName().equalsIgnoreCase("ec") || cmd.getName().equalsIgnoreCase("enderchest")){
            ((Player) sender).openInventory(((Player) sender).getEnderChest());
        }
        return true;
    }
}
