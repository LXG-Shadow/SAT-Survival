package com.lxgshadow.survival.managers;

import com.lxgshadow.survival.models.SurvivalTeam;
import com.lxgshadow.survival.Main;
import com.lxgshadow.survival.Messages;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class TeamsManager {
    private static HashSet<SurvivalTeam> teams = new HashSet<>();

    public static HashSet<SurvivalTeam> getAll(){
        return teams;
    }

    // 0 成功 1 在队伍里 2 同名存在
    public static int create(Player p) {
        return create(p, p.getName() + "'s Team");
    }

    public static int create(Player p, String tn) {
        if (get(tn) != null){
            return 2;
        }
        if (get(p) != null){
            return 1;
        }
        teams.add(new SurvivalTeam(p,tn));
        return 0;

    }

    public static SurvivalTeam get(String tn) {
        for (SurvivalTeam t : teams) {
            if (t.getName().equals(tn)) {
                return t;
            }
        }
        return null;
    }

    public static SurvivalTeam get(Player p) {
        for (SurvivalTeam t : teams) {
            if (t.containsTeammate(p)) {
                return t;
            }
        }
        return null;
    }

    public static boolean hasTeam(Player p){
        return null != get(p);
    }

    // join 1 没有该队伍 0 成功 2 人满了 3 在一个队伍里了

    public static int join(Player request, String tn) {
        return join(request,get(tn));
    }

    public static int join(Player request, Player target) {
        return join(request,get(target));
    }

    public static int join(Player request, SurvivalTeam t) {
        if (t == null) {
            return 1;
        }
        if (!teams.contains(t)) {
            return 1;
        }
        if (get(request) != null){
            return 3;
        }
        switch (t.add(request)) {
            case 0: {
                t.getMembers().forEach((tm) -> Main.getInstance().getServer().getPlayer(tm)
                        .sendMessage(Messages.team_BroadcastJoin.replace("%(p)", request.getName())));
                return 0;
            }
            case 1:{
                return 2;
            }
        }
        return -1;
    }

    // 0 成功 1 没队伍
    public static int quit(Player p){
        SurvivalTeam t = get(p);
        if (t == null){
            return 1;
        }
        t.remove(p);
        t.getMembers().forEach((tm) -> Main.getInstance().getServer().getPlayer(tm)
                .sendMessage(Messages.team_BroadcastQuit.replace("%(p)", p.getName())));
        return 0;
    }

    // 0 成功 1 没人 2 没权限 3 没队伍
    public static int kick(Player request, String target){
        Player p = Main.getInstance().getServer().getPlayer(target);
        if (p == null){
            return 1;
        }
        return kick(request,p);
    }

    public static int kick(Player request, Player target){
        SurvivalTeam t = get(request);
        if (t == null){
            return 3;
        }
        if (!t.getLeader().equals(request.getName())){
            return 2;
        }
        if (!t.containsTeammate(target)){
            return 1;
        }
        t.remove(target);
        target.sendMessage(Messages.team_KickVictimHint.replace("%(t)",t.getName()));
        t.getMembers().forEach((tm) -> Main.getInstance().getServer().getPlayer(tm)
                .sendMessage(Messages.team_BroadcastKick.replace("%(p)", target.getName())));
        return 0;
    }

    // 0 成功 1 没权限 2 没队伍
    public static int disband(Player request) {
        SurvivalTeam t = get(request);
        if (t == null){
            return 2;
        }
        if (!t.getLeader().equals(request.getName())){
            return 1;
        }
        disband(t);
        return 0;
    }

    public static void disband(SurvivalTeam t) {
        teams.remove(t);
        t.getMembers().forEach((tm) -> Main.getInstance().getServer().getPlayer(tm)
                .sendMessage(Messages.team_BroadcastDisband));
    }

    // 0 成功 1 没人 2 没权限 3 没队伍 4 没请求 5 人满 6 在别的队伍里
    public static int accept(Player request, Player target) {
        SurvivalTeam t = get(request);
        if (t == null){
            return 3;
        }
        if (!t.getLeader().equals(request.getName())){
            return 2;
        }
        if (target == null){
            return 1;
        }
        if (!hasJoinRequest(t,target)){
            return 4;
        }
        t.joinRequests.remove(target.getName());
        switch (join(target,t)){
            case 0:{
                return 0;
            }
            case 2:{
                return 5;
            }
            case 3:{
                return 6;
            }
        }
        return -1;
    }
    public static int accept(Player request, String target) {
        return accept(request, Main.getInstance().getServer().getPlayer(target));

    }

    public static boolean hasInviteRequest(String tn, Player p){
        return hasInviteRequest(get(tn),p);
    }

    public static boolean hasInviteRequest(SurvivalTeam t, Player p){
        if (t == null){
            return false;
        }
        return t.inviteRequests.contains(p.getName());
    }

    public static boolean hasJoinRequest(String tn, Player target){
        return hasJoinRequest(get(tn),target);
    }

    public static boolean hasJoinRequest(SurvivalTeam t, Player target){
        if (t == null){
            return false;
        }
        return t.joinRequests.contains(target.getName());
    }

    public static boolean inSameTeam(Player p1, Player p2){
        SurvivalTeam t = TeamsManager.get(p1);
        if (t != null){
            return t.containsTeammate(p2);
        }
        return false;
    }
}