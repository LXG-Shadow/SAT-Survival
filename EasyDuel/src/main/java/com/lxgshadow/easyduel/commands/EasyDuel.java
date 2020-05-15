package com.lxgshadow.easyduel.commands;

import com.lxgshadow.easyduel.Main;
import com.lxgshadow.easyduel.Messages;
import com.lxgshadow.easyduel.arena.Arena;
import com.lxgshadow.easyduel.arena.ArenaManager;
import com.lxgshadow.easyduel.arena.ArenaProperties;
import com.lxgshadow.easyduel.arena.ArenaTeam;
import com.lxgshadow.easyduel.events.EasyDuelRequestEvent;
import com.lxgshadow.easyduel.mode.ArenaMode;
import com.lxgshadow.easyduel.mode.ArenaModeManager;
import com.lxgshadow.easyduel.utils.playerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.StringUtil;

import java.util.*;

public class EasyDuel implements CommandExecutor, TabCompleter {
    private HashMap<String, String> requestList = new HashMap<>();
    private HashMap<String, Arena> requestedArena = new HashMap<>();
    private int expireTime = 60;

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch (args.length) {
            case 1: {
                List<String> completions = new ArrayList<>();
                List<String> possibles = new ArrayList<>();
                possibles.add("accept");
                Main.getInstance().getServer().getOnlinePlayers().forEach(p->possibles.add(p.getName()));
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
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("duel")) {
            return false;
        }
        if (!(sender instanceof Player)) {
            return true;
        }
        if (args.length == 0) {
            return false;
        }

        if (args[0].equals("accept")) {
            if (args.length < 2) {
                return false;
            }
            if (!requestList.containsKey(args[1])) {
                sender.sendMessage(Messages.arena_error_NoRequest.replace("%p", args[1]));
                return true;
            }
            // start duel
            if (requestList.get(args[1]).equals(sender.getName())) {
                Player requester = Main.getInstance().getServer().getPlayer(args[1]);
                if (requester == null) {
                    sender.sendMessage(Messages.arena_error_Player404.replace("%p", args[1]));
                    return true;
                }
                requestList.remove(requester.getName());
                Arena arena = requestedArena.remove(requester.getName());
                boolean b = ArenaManager.startArena(arena);
                if (!b){
                    for (String msg : arena.getErrormsg()) {
                        arena.broadcast(msg);
                    }
                }
                return true;
            }
            // end start duel
            else {
                sender.sendMessage(Messages.arena_error_NoRequest.replace("%p", args[1]));
                return true;
            }
        }

        //request a duel

        Player requester = (Player) sender;
        Player target = Main.getInstance().getServer().getPlayer(args[0]);
        if (target == null) {
            requester.sendMessage(Messages.arena_error_Player404.replace("%p", args[0]));
            return true;
        }
        if (target.equals(requester)) {
            requester.sendMessage(Messages.arena_error_DuelSelf);
            return true;
        }

        // raise request event
        EasyDuelRequestEvent event = new EasyDuelRequestEvent(requester, target);
        Main.getInstance().getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            requester.sendMessage(event.getMsg());
            return true;
        }

        ArenaMode mode = ArenaModeManager.getDefaultMode();
        for (int i = 1; i < args.length; i++) {
            if (args[i].startsWith("mode=") && args[i].length() > 5) {
                mode = ArenaModeManager.getArenaMode(args[i].substring(5));
            }
        }
        ArenaProperties ap = new ArenaProperties();
        ap.parseArgs(args);
        ArenaTeam team1 = new ArenaTeam(Collections.singletonList(requester));
        ArenaTeam team2 = new ArenaTeam(Collections.singletonList(target));
        Arena arena = ArenaManager.create(Arrays.asList(team1, team2), ap, mode);

        // 如果create出现错误
        if (arena.getErrormsg().size() > 0){
            for (String msg : arena.getErrormsg()) {
                arena.broadcast(msg);
            }
            return true;
        }

        requester.sendMessage(Messages.arena_SendingRequest.replace("%p",target.getDisplayName()));
        target.sendMessage(Messages.arena_AcceptHint.replace("%p",requester.getDisplayName()).replace("%m",arena.getMode().getDisplayname()));
        // if not cancel, add name to the request list.
        requestList.put(requester.getName(), target.getName());
        requestedArena.put(requester.getName(),arena);

        new BukkitRunnable() {
            int time = 0;
            @Override
            public void run() {
                if (time >= expireTime) {
                    requestList.remove(requester.getName());
                    requestedArena.remove(requester.getName());
                    this.cancel();
                }
                if (!requestList.containsKey(requester.getName())){
                    this.cancel();
                }
                time +=1;
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
        return true;
    }

}