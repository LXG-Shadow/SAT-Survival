package com.lxgshadow.survival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class hatCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){return true;}
        PlayerInventory inv =  ((Player) sender).getInventory();
        ItemStack item = inv.getHelmet();
        inv.setHelmet(inv.getItemInMainHand());
        inv.setItemInMainHand(item);
        return true;
    }
}