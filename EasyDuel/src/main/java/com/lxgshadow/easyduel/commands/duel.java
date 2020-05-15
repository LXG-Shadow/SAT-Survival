package com.lxgshadow.easyduel.commands;

import com.lxgshadow.easyduel.Main;
import com.lxgshadow.easyduel.Messages;
import com.lxgshadow.easyduel.arena.Arena;
import com.lxgshadow.easyduel.arena.ArenaManager;
import com.lxgshadow.easyduel.arena.ArenaProperties;
import com.lxgshadow.easyduel.arena.ArenaTeam;
import com.lxgshadow.easyduel.mode.ArenaMode;
import com.lxgshadow.easyduel.mode.ArenaModeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        if (oppo == null){sender.sendMessage(Messages.arena_error_Player404.replace("%p",args[0]));return true;}
        if (oppo.equals(player)){sender.sendMessage(Messages.arena_error_DuelSelf);return true;}

        ArenaMode mode = ArenaModeManager.getDefaultMode();
        for (int i=1;i<args.length;i++){
            if (args[i].startsWith("mode=") && args[i].length()>5){
                mode = ArenaModeManager.getArenaMode(args[i].substring(5));
            }
        }
        ArenaProperties ap = new ArenaProperties();
        ap.parseArgs(args);

        ArenaTeam team1 = new ArenaTeam(Collections.singletonList(player));
        ArenaTeam team2 = new ArenaTeam(Collections.singletonList(oppo));
        Arena arena = ArenaManager.create(Arrays.asList(team1,team2),ap,mode);
        if (arena.getErrormsg().size() > 0){
            for (String msg:arena.getErrormsg()){
                sender.sendMessage(msg);
            }
        }
        return true;
    }
}
