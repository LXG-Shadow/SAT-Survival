package com.lxgshadow.survival.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.HashMap;
import java.util.UUID;

public interface scoreboards {
    DisplaySlot getDisplaySlot();
    String getCriteria();
    String getName();
    String getTitle();
    UUID getPlayer();
    public HashMap<Integer, String> getTextList();
    public void setText(int index, String text);
    public void setTitle(String t);
}
