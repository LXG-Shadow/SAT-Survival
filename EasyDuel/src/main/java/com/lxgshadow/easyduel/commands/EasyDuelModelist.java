package com.lxgshadow.easyduel.commands;

import com.lxgshadow.easyduel.Messages;
import com.lxgshadow.easyduel.mode.ArenaMode;
import com.lxgshadow.easyduel.mode.ArenaModeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EasyDuelModelist implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("duelmodelist")) {
            return false;
        }
        sender.sendMessage(Messages.modelist_start);
        for (ArenaMode am: ArenaModeManager.getAllMode()){
            sender.sendMessage(Messages.modelist_item.replace("%id",am.getId()).replace("%name",am.getDisplayname()));
        }
        return true;
    }
}
