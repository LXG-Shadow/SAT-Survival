package com.lxgshadow.survival.commands;

import com.lxgshadow.survival.Messages;
import com.lxgshadow.survival.managers.PicvManager;
import com.lxgshadow.survival.models.PlayerInventoryChestViewer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class openinvCommands implements CommandExecutor {
    private JavaPlugin plugin;
    public openinvCommands(JavaPlugin p){this.plugin=p;}

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            if (!sender.isOp()){
                return true;
            }
        }else {
            return true;
        }
        if (args.length<1){
            return false;
        }
        String pn = args[0];
        Player player = this.plugin.getServer().getPlayer(pn);
        PlayerInventoryChestViewer viewer;
        if (player == null){
            viewer = PicvManager.createOffline(this.plugin.getServer().getOfflinePlayer(pn));

        }else {
            viewer = PicvManager.create(player);
        }
        if (viewer == null){
            sender.sendMessage(Messages.forcemsg_Player404);
            return true;
        }
        ((Player) sender).openInventory(viewer.getInventory());
        return true;
    }
}

