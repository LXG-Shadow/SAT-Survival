package com.lxgshadow.survival.commands;

import com.lxgshadow.survival.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class forcemsgCommands implements CommandExecutor {
    private JavaPlugin plugin;
    public forcemsgCommands(JavaPlugin p){this.plugin=p;}

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            if (!sender.isOp()){
                return true;
            }
        }
        if (args.length<2){
            return false;
        }
        String pn = args[0];
        Player player = this.plugin.getServer().getPlayer(pn);
        if (player == null){
            sender.sendMessage(Messages.forcemsg_Player404);
            return true;
        }
        StringBuilder msg = new StringBuilder(args[1]);
        for (int i=2;i<args.length;i++){
            msg.append(" ").append(args[i]);
        }
        player.chat(msg.toString());
        return true;
    }
}