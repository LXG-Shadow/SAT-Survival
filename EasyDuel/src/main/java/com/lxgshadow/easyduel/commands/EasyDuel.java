package com.lxgshadow.easyduel.commands;

import com.lxgshadow.easyduel.Config;
import com.lxgshadow.easyduel.Main;
import com.lxgshadow.easyduel.arena.ArenaManager;
import com.lxgshadow.easyduel.arena.ArenaModes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class EasyDuel implements CommandExecutor {
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
        int mode = 1;
        for (int i=1;i<args.length;i++){
            if (args[i].startsWith("mode=") && args[i].length()>5){
                mode = Integer.parseInt(args[i].substring(5));
            }
        }
        int size = Config.arena_defaultsize;
        for (int i=1;i<args.length;i++){
            if (args[i].startsWith("size=") && args[i].length()>5){
                size = Integer.parseInt(args[i].substring(5));
            }
        }
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Integer> teams = new ArrayList<>();
        players.add(player);
        players.add(oppo);
        teams.add(1);
        teams.add(2);
        ArenaManager.create(players,teams,mode,size);
        return true;
    }
}
