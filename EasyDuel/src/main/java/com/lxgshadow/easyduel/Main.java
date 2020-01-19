package com.lxgshadow.easyduel;

import com.lxgshadow.easyduel.arena.ArenaManager;
import com.lxgshadow.easyduel.commands.duel;
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
    }

    private void registerEvents(){
    }

    private void registerCommands(){
        this.getCommand("duel").setExecutor(new duel());
    }


    public static Main getInstance() {
        return instance;
    }
}
