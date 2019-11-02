package com.lxgshadow.survival.models;

import com.lxgshadow.survival.Config;
import com.lxgshadow.survival.managers.TeamsManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;

public class SurvivalTeam {
    private HashSet<String> teammates = new HashSet<>();
    private String name;
    private String leader;
    private HashMap<String,Boolean> setting = new HashMap<>();

    public HashSet<String> joinRequests = new HashSet<>();
    public HashSet<String> inviteRequests = new HashSet<>();

    public SurvivalTeam(Player p) {
        this.leader = p.getName();
        // todo 改为数字
        this.name = p.getName() + "'s Team";
        this.add(p);
        defaultSetting();
    }

    public SurvivalTeam(Player p, String tn) {
        this.leader = p.getName();
        this.name = tn;
        this.add(p);
        defaultSetting();
    }

    private void defaultSetting(){
        this.setting.put("teammateDamage",false);
    }

    public HashMap<String,Boolean> getSetting(){
        return this.setting;
    }


    public HashSet<String>getMembers(){
        return this.teammates;
    }

    public void setName(String tn) {
        this.name = tn;
    }

    public String getName() {
        return this.name;
    }

    public int size() {
        return teammates.size();
    }


    public boolean containsTeammate(Player p) {
        return this.teammates.contains(p.getName());
    }
    public boolean containsTeammate(String p) {
        return this.teammates.contains(p);
    }

    public String getLeader() {
        return this.leader;
    }

    public int add(Player p) {
        if (this.size() > Config.team_maxSize) {
            return 1;
        }
        this.teammates.add(p.getName());
        return 0;
    }

    public int remove(Player p) {
        if (!this.containsTeammate(p)) {
            return 1;
        }
        this.teammates.remove(p.getName());
        if (this.size() == 0) {
            this.disband();
        }
        if (p.getName().equals(this.leader)){
            for (String a:teammates){
                this.leader = a;
                break;
            }
        }
        return 0;
    }

    public boolean isLeader(Player p) {
        return p.getName().equals(this.leader);
    }
    public boolean isLeader(String p) {
        return p.equals(this.leader);
    }

    public void disband() {
        TeamsManager.disband(this);
    }

}
