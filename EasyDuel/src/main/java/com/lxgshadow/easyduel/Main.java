package com.lxgshadow.easyduel;

import com.lxgshadow.easyduel.arena.ArenaManager;
import com.lxgshadow.easyduel.commands.EasyDuel;
import com.lxgshadow.easyduel.commands.duel;
import com.lxgshadow.easyduel.mode.ArenaModeManager;
import org.bukkit.plugin.java.JavaPlugin;

public class
Main extends JavaPlugin {
    private static Main instance;


    @Override
    public void onLoad() {
        getLogger().info("Easy Duel Loading");
    }

    @Override
    public void onEnable() {
        Main.instance = this;
        Config.init();
        registerCommands();
        registerEvents();
        initializeManagers();
    }

    @Override
    public void onDisable() {
    }

    private void initializeManagers(){
        ArenaManager.initialize();
        ArenaModeManager.initialize();
    }

    private void registerEvents(){
    }

    private void registerCommands(){
        this.getCommand("duel").setExecutor(new EasyDuel());
    }


    public static Main getInstance() {
        return instance;
    }
}
