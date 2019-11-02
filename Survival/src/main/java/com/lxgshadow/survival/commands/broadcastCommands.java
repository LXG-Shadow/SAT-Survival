package com.lxgshadow.survival.commands;

import com.lxgshadow.survival.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class broadcastCommands implements CommandExecutor {
    private JavaPlugin plugin;
    public broadcastCommands(JavaPlugin p){this.plugin=p;}

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            if (!sender.isOp()){
                return true;
            }
        }
        if (args.length<1){
            return false;
        }
        StringBuilder msg = new StringBuilder(args[0]);
        for (int i=1;i<args.length;i++){
            msg.append(" ").append(args[i]);
        }
        plugin.getServer().getOnlinePlayers().forEach((tm)->tm.sendMessage(Messages.broadcase_Msg.replace("%(m)",msg.toString())));
        return true;
    }
}
