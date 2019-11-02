package com.lxgshadow.survival.commands;

import com.lxgshadow.survival.managers.TeamsManager;
import com.lxgshadow.survival.models.SurvivalTeam;
import com.lxgshadow.survival.Config;
import com.lxgshadow.survival.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;


public class teamCommands implements CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;

    public teamCommands(JavaPlugin p) {
        this.plugin = p;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        switch (args.length) {
            case 1: {
                List<String> possibles = new ArrayList<>();
                possibles.add("create");
                possibles.add("accept");
                possibles.add("info");
                possibles.add("invite");
                possibles.add("quit");
                possibles.add("kick");
                possibles.add("disband");
                possibles.add("join");
                for (String s : possibles) {
                    if (StringUtil.startsWithIgnoreCase(s, args[0].toLowerCase())) {
                        completions.add(s);
                    }
                }
                return completions;
            }
            case 2: {
                if (args[0].equals("create") || args[0].equals("info") || args[0].equals("quit") || args[0].equals("disband")) {
                    return new ArrayList<>();
                }
                if (args[0].equals("kick") || args[0].equals("invite") || args[0].equals("accept")) {
                    plugin.getServer().getOnlinePlayers().forEach((p) -> {
                        if (StringUtil.startsWithIgnoreCase(p.getName(), args[1].toLowerCase())) {
                            completions.add(p.getName());
                        }
                    });
                    return completions;
                }
                if (args[0].equals("join")) {
                    for (SurvivalTeam t : TeamsManager.getAll()) {
                        if (StringUtil.startsWithIgnoreCase(t.getName(), args[1].toLowerCase())) {
                            completions.add(t.getName());
                        }
                    }
                    return completions;
                }
            }
        }
        return completions;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("team")) {
            switch (args[0]) {
                case "create": {
                    int rs;
                    if (args.length >= 2) {
                        rs = TeamsManager.create(player, args[1]);
                    } else {
                        rs = TeamsManager.create(player);
                    }
                    switch (rs) {
                        case 0: {
                            player.sendMessage(Messages.team_CreateSuccess.replace("%(t)", TeamsManager.get(player).getName()));
                            break;
                        }
                        case 1: {
                            player.sendMessage(Messages.team_AlreadyIn.replace("%(t)", TeamsManager.get(player).getName()));
                            break;
                        }
                        case 2:{
                            player.sendMessage(Messages.team_CreateSameName);
                            break;
                        }
                    }
                    return true;
                }
                case "quit": {
                    switch (TeamsManager.quit(player)) {
                        case 0: {
                            player.sendMessage(Messages.team_QuitSuccess);
                            break;
                        }
                        case 1: {
                            player.sendMessage(Messages.team_NotIn);
                            break;
                        }
                    }
                    return true;
                }
                case "disband": {
                    switch (TeamsManager.disband(player)) {
                        case 0: {
                            player.sendMessage(Messages.team_DisbandSuccess);
                            break;
                        }
                        case 1: {
                            player.sendMessage(Messages.team_DisbandNoPermission);
                            break;
                        }
                        case 2: {
                            player.sendMessage(Messages.team_NotIn);
                            break;
                        }
                    }
                    return true;
                }
                case "join": {
                    if (args.length < 2) {
                        return false;
                    }
                    String tn = args[1];
                    for (int i=2;i<args.length;i++){
                        tn = tn + " " + args[i];
                    }
                    if (TeamsManager.hasInviteRequest(tn,player)) {
                        switch (TeamsManager.join(player, tn)) {
                            case 0: {
                                player.sendMessage(Messages.team_JoinSuccess.replace("%(t)", tn));
                                break;
                            }
                            case 1: {
                                player.sendMessage(Messages.team_404);
                                break;
                            }
                            case 2: {
                                player.sendMessage(Messages.team_JoinNoSpace.replace("%(t)", tn));
                                break;
                            }
                            case 3: {
                                player.sendMessage(Messages.team_AlreadyIn.replace("%(t)", tn));
                                break;
                            }
                        }
                        TeamsManager.get(tn).inviteRequests.remove(player.getName());
                    } else {
                        if (TeamsManager.get(player) != null) {
                            player.sendMessage(Messages.team_AlreadyIn.replace("%(t)", TeamsManager.get(player).getName()));
                            return true;
                        }
                        SurvivalTeam t = TeamsManager.get(tn);
                        if (t == null) {
                            player.sendMessage(Messages.team_404);
                        } else {
                            if (t.joinRequests.contains(player.getName())) {
                                player.sendMessage(Messages.team_JoinRepeatRequest.replace("%(t)", tn));
                                return true;
                            }
                            t.joinRequests.add(player.getName());
                            player.sendMessage(Messages.team_JoinRequest.replace("%(t)", tn));
                            plugin.getServer().getPlayer(t.getLeader()).sendMessage(Messages.team_AcceptHint.replace("%(p)", player.getName()));
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    t.joinRequests.remove(player.getName());
                                    this.cancel();
                                }
                            }.runTaskLater(plugin, 60 * 20);
                        }
                    }
                    return true;
                }
                case "invite": {
                    if (args.length < 2) {
                        return false;
                    }
                    // todo: 放到TeamManager里去.
                    SurvivalTeam t = TeamsManager.get(player);
                    if (t == null) {
                        player.sendMessage(Messages.team_NotIn);
                        return true;
                    }
                    if (!t.isLeader(player)) {
                        player.sendMessage(Messages.team_InviteNoPermission);
                        return true;
                    }
                    Player targetPlayer = plugin.getServer().getPlayer(args[1]);
                    if (targetPlayer == null) {
                        player.sendMessage(Messages.team_Player404);
                        return true;
                    }
                    if (TeamsManager.get(targetPlayer) != null) {
                        player.sendMessage(Messages.team_InvitePlayerAlreadyIn);
                        return true;
                    }
                    if (t.inviteRequests.contains(targetPlayer.getName())) {
                        player.sendMessage(Messages.team_InviteRepeatRequest);
                        return true;
                    }
                    t.inviteRequests.add(targetPlayer.getName());
                    targetPlayer.sendMessage(Messages.team_InviteHint
                            .replace("%(t)", t.getName()).replace("%(p)", player.getName()));
                    t.getMembers().forEach((tm) -> plugin.getServer().getPlayer(tm).sendMessage(Messages.team_BroadcastInvite
                            .replace("%(p1)",t.getLeader()).replace("%(p2)",targetPlayer.getName())));
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            t.inviteRequests.remove(targetPlayer.getName());
                            this.cancel();
                        }
                    }.runTaskLater(plugin, 60 * 20);
                    return true;
                }
                case "kick": {
                    if (args.length < 2) {
                        return false;
                    }
                    switch (TeamsManager.kick(player, args[1])) {
                        case 0: {
                            player.sendMessage(Messages.team_KickSuccess.replace("%(p)", args[1]));
                            break;
                        }
                        case 1: {
                            player.sendMessage(Messages.team_KickPlayer404);
                            break;
                        }
                        case 2: {
                            player.sendMessage(Messages.team_KickNoPermission);
                            break;
                        }
                        case 3: {
                            player.sendMessage(Messages.team_NotIn);
                            break;
                        }
                    }
                    return true;

                }
                case "accept": {
                    if (args.length < 2) {
                        return false;
                    }
                    Player target = plugin.getServer().getPlayer(args[1]);
                    switch (TeamsManager.accept(player,target)){
                        case 0:{
                            break;
                        }
                        case 1:{
                            player.sendMessage(Messages.team_Player404);
                            break;
                        }
                        case 2:{
                            player.sendMessage(Messages.team_AcceptNoPermission);
                            break;
                        }
                        case 3:{
                            player.sendMessage(Messages.team_NotIn);
                            break;
                        }
                        case 4:{
                            player.sendMessage(Messages.team_AcceptNoRequest);
                            break;
                        }
                        case 5:{
                            player.sendMessage(Messages.team_AcceptNoSpace);
                            break;
                        }
                        case 6:{
                            player.sendMessage(Messages.team_AcceptPlayerAlreadyIn);
                            break;
                        }
                    }
                    return true;
                }
                case "info": {
                    SurvivalTeam t = TeamsManager.get(player);
                    if (t == null){
                        player.sendMessage(Messages.team_NotIn);
                        return true;
                    }
                    player.sendMessage(Messages.team_Info1);
                    player.sendMessage(Messages.team_Info2
                            .replace("%(t)",t.getName()).replace("%(n)",String.valueOf(t.size()))
                            .replace("%(mn)",String.valueOf(Config.team_maxSize)));
                    player.sendMessage(Messages.team_Info3);
                    Player p;
                    p = plugin.getServer().getPlayer(t.getLeader());
                    sendTeamMemberInfo(player,p);
                    player.sendMessage(Messages.team_Info4);
                    for (String pn:t.getMembers()){
                        if (t.isLeader(pn)){
                            continue;
                        }
                        sendTeamMemberInfo(player,plugin.getServer().getPlayer(pn));
                    }
                    player.sendMessage(Messages.team_Info1);
                    return true;
                }
            }
        }
        return false;
    }

    private void sendTeamMemberInfo(Player receiver, Player p){
        receiver.sendMessage(Messages.team_Info5.replace("%(p)",p.getName())
                .replace("%(h)",String.format("%.2f",p.getHealth()))
                .replace("%(x)",String.valueOf((int)p.getLocation().getX()))
                .replace("%(y)",String.valueOf((int)p.getLocation().getY()))
                .replace("%(z)",String.valueOf((int)p.getLocation().getZ()))
                .replace("%(w)",String.valueOf(p.getLocation().getWorld().getName())));
    }
}
