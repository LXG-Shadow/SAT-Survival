package com.lxgshadow.easyduel.mode;

import com.lxgshadow.easyduel.Main;
import com.lxgshadow.easyduel.arena.Arena;
import com.lxgshadow.easyduel.arena.ArenaManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class ArenaModeManager {
    private static HashMap<String,ArenaMode> modes;
    private static String defaultMode;

    public static void initialize(){
        modes = new HashMap<>();
        defaultMode = "common";
        ArenaModeManager.registerMode(new ArenaModeCommon());
        ArenaModeManager.registerMode(new ArenaModeFirstTouch());
    }

    public static void registerMode(ArenaMode mode){
        Main.getInstance().getServer().getPluginManager().registerEvents(mode,Main.getInstance());
        modes.put(mode.getId(),mode);
    }

    public static Set<String> getAllId(){
        return modes.keySet();
    }

    public static Collection<ArenaMode> getAllMode(){
        return modes.values();
    }

    public static String[] getAllDisplayname(){
        Collection<ArenaMode>mds = getAllMode();
        String[] dns = new String[mds.size()];
        int index =0;
        for (ArenaMode am:mds){
            dns[index] = am.getDisplayname();
            index++;
        }
        return dns;
    }

    public static ArenaMode getArenaMode(String id){
        return modes.get(id);
    }

    public static boolean hasArena(String id){
        return modes.containsKey(id);
    }

    public static void setDefaultMode(ArenaMode mode){
        defaultMode = mode.getId();
    }

    public static ArenaMode getDefaultMode(){
        return modes.get(defaultMode);
    }
}
