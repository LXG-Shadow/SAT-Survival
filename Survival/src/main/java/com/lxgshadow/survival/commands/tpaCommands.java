package com.lxgshadow.survival.commands;

import com.lxgshadow.survival.Config;
import com.lxgshadow.survival.Messages;
import com.lxgshadow.survival.events.playerTpaEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class tpaCommands implements CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;
    private HashMap<String, String> tpaList = new HashMap<>();
    private HashMap<String, Long> tpaCoolDownList = new HashMap<>();
    private int expireTime = 60;

    public tpaCommands(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch (args.length) {
            case 1: {
                List<String> completions = new ArrayList<>();
                List<String> possibles = new ArrayList<>();
                possibles.add("send");
                possibles.add("accept");
                possibles.add("cancel");
                for (String s : possibles) {
                    if (StringUtil.startsWithIgnoreCase(s, args[0].toLowerCase())) {
                        completions.add(s);
                    }
                }
                return completions;
            }
            case 2: {
                List<String> completions = new ArrayList<>();
                Bukkit.getServer().getOnlinePlayers().forEach((p) -> {
                    if (StringUtil.startsWithIgnoreCase(p.getName(), args[1].toLowerCase())) {
                        completions.add(p.getName());
                    }
                });
                return completions;
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender,Command cmd, String label,String[] args) {
        if (cmd.getName().equalsIgnoreCase("tpa")) {
            if (args.length < 1) {
                return false;
            }
            if (!(sender instanceof Player)){
                return true;
            }
            if (args[0].equals("send")) {
                if (args.length < 2) {
                    return false;
                }
                Player tpdplayer = (Player) sender;
                Player targetPlayer = this.plugin.getServer().getPlayer(args[1]);
                if (targetPlayer == null) {
                    tpdplayer.sendMessage(Messages.tpa_Player404);
                    return true;
                }
                if (targetPlayer == tpdplayer) {
                    tpdplayer.sendMessage(Messages.tpa_PlayerSelf);
                    return true;
                }
                playerTpaEvent event = new playerTpaEvent(tpdplayer,targetPlayer);
                this.plugin.getServer().getPluginManager().callEvent(event);
                if (!event.isSkippable()){
                    if (tpaCoolDownList.containsKey(tpdplayer.getName())){
                        int cd = (int)(System.currentTimeMillis() - tpaCoolDownList.get(tpdplayer.getName())) / 1000;
                        if (cd < Config.tpa_cooldown){
                            tpdplayer.sendMessage(Messages.tpa_InCoolDown.replace("%(s)",String.valueOf(Config.tpa_cooldown-cd)));
                            return true;
                        }
                        else {
                            tpaCoolDownList.remove(tpdplayer.getName());
                        }
                    }
                }
                if (event.isCancelled()){
                    return true;
                }
                sendTpaRequest(tpdplayer,targetPlayer);
                return true;
            }
            if (args[0].equals("accept")) {
                if (args.length < 2){
                    return true;
                }
                String tpdPlayer = args[1];
                if (!tpaList.containsKey(tpdPlayer)){
                    sender.sendMessage(Messages.tpa_NoRequest.replace("%(p)",tpdPlayer));
                    return true;
                }
                if (tpaList.get(tpdPlayer).equals(sender.getName())){
                    tpPlayer(this.plugin.getServer().getPlayer(tpdPlayer),(Player)sender);
                    return true;
                }
                else {
                    sender.sendMessage(Messages.tpa_NoRequest.replace("%(p)",tpdPlayer));
                    return true;
                }
            }
            if (args[0].equals("cancel")) {
                if (tpaList.containsKey(sender.getName())){
                    tpaList.remove(sender.getName());
                    sender.sendMessage(Messages.tpa_CancelRequest);
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private void sendTpaRequest(Player sender, Player receiver) {
        sender.sendMessage(Messages.tpa_SendingRequest.replace("%(p)", receiver.getName()));
        receiver.sendMessage(Messages.tpa_AcceptHint.replace("%(p)", sender.getName()));
        tpaList.put(sender.getName(), receiver.getName());
        new BukkitRunnable() {
            int time = 0;

            @Override
            public void run() {
                if (time >= expireTime) {
                    tpaList.remove(sender.getName());
                    this.cancel();
                }
                if (!tpaList.containsKey(sender.getName())){
                    this.cancel();
                }
//                if (!tpaList.get(sender.getName()).equals(receiver.getName())) {
//                    this.cancel();
//                }
                time +=1;
            }
        }.runTaskTimer(this.plugin, 0, 20);
    }

    private void tpPlayer(Player tpdPlayer, Player targetPlayer) {
        if (tpdPlayer == null){
            targetPlayer.sendMessage(Messages.tpa_Player404);
            return;
        }

        tpaCoolDownList.put(tpdPlayer.getName(),System.currentTimeMillis());
        tpdPlayer.teleport(targetPlayer);
        tpaList.remove(tpdPlayer.getName());
    }
}

