package com.lxgshadow.survival.scoreboard;

import com.lxgshadow.survival.Main;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class sideBar implements scoreboards{
    private DisplaySlot displaySlot = DisplaySlot.SIDEBAR;
    private UUID player;
    private String criteria;
    private String name;
    private String title;
    private HashMap<Integer,String> textList;

    public sideBar(Player p,String n,String c,String t) {
        this.player = p.getUniqueId();
        this.textList = new HashMap<>();
        this.title = t;
        this.name = n;
        this.criteria = c;
    }
    public sideBar(Player p,String n,String c,String t,HashMap<Integer,String> tl) {
        this.player = p.getUniqueId();
        this.textList = new HashMap<>();
        this.title = t;
        this.name = n;
        this.criteria = c;
        textList = tl;
    }

    public String getCriteria() {
        return criteria;
    }
    public String getName() {
        return name;
    }
    public String getText(int index){
        if (textList.containsKey(index)){return textList.get(index);}
        return null;
    }
    public String getTitle() {
        return title;
    }
    @Override
    public UUID getPlayer() {
        return player;
    }
    public HashMap<Integer, String> getTextList() {
        return textList;
    }
    public void setText(int index, String text) {
        scoreboardTextChangeEvent event = new scoreboardTextChangeEvent(this,index,getText(index),text);
        Main.getInstance().getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()){return;}
        textList.put(index,text);
    }
    public void setTitle(String t){
        scoreboardTitleChangeEvent event = new scoreboardTitleChangeEvent(this,this.title,t);
        Main.getInstance().getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()){return;}
        this.title = t;
    }
    public DisplaySlot getDisplaySlot(){
        return displaySlot;
    }
}
