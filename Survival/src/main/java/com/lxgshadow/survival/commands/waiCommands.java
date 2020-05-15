package com.lxgshadow.survival.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class waiCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){return true;}
        Location loc = ((Player) sender).getLocation();
        ((Player) sender).chat("w - x:a, y:b, z:c,".replace("a",String.valueOf(loc.getBlockX()))
                .replace("b",String.valueOf(loc.getBlockY()))
                .replace("c",String.valueOf(loc.getBlockZ()))
                .replace("w",loc.getWorld().getName()));
        return true;
    }
}