package com.lxgshadow.easyduel.commands;

import com.lxgshadow.easyduel.arena.ArenaManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class newarean implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){return true;}
        if (!sender.isOp()){
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("newarena")){
            player.sendMessage("new");
            player.sendMessage(ArenaManager.create(new Player[]{player})+"");
            player.sendMessage(ArenaManager.getArenaId(player)+"");
            player.sendMessage(Arrays.toString(ArenaManager.getArena(ArenaManager.getArenaId(player))));
        }
        if (cmd.getName().equalsIgnoreCase("cancelarena")){
            player.sendMessage("cancel");
            player.sendMessage(ArenaManager.getArenaId(player)+"");
            player.sendMessage(Arrays.toString(ArenaManager.getArena(ArenaManager.getArenaId(player))) +"");
            ArenaManager.remove(ArenaManager.getArenaId(player));
        }
        return true;
    }
}
