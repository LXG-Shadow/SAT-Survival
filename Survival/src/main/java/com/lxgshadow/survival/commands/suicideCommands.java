package com.lxgshadow.survival.commands;

import com.lxgshadow.survival.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class suicideCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("suicide")) {
            if (sender instanceof Player) {
                ((Player) sender).setHealth(0);
                sender.sendMessage(Messages.suicide_Msg);
            }
        }
        return true;
    }
}
