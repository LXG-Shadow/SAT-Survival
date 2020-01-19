package com.lxgshadow.easyduel.commands;

import com.lxgshadow.easyduel.Main;
import com.lxgshadow.easyduel.arena.Arena;
import com.lxgshadow.easyduel.arena.ArenaManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class duel implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("duel")){return false;}
        if (!(sender instanceof Player)){return true;}
        Player player = (Player) sender;
        if (args.length == 0){
            return true;
        }
        Player oppo = Main.getInstance().getServer().getPlayer(args[0]);
        if (oppo == null){return true;}
        if (oppo.equals(player)){return true;}
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Integer> teams = new ArrayList<>();
        players.add(player);
        players.add(oppo);
        teams.add(1);
        teams.add(2);
        ArenaManager.create(players,teams,1,16);
        return true;
    }
}
