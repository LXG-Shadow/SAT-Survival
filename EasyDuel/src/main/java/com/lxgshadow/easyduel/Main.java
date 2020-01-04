package com.lxgshadow.easyduel;

import com.lxgshadow.easyduel.arena.ArenaManager;
import com.lxgshadow.easyduel.commands.newarean;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
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
        this.getCommand("newarena").setExecutor(new newarean());
        this.getCommand("cancelarena").setExecutor(new newarean());
    }


    public static Main getInstance() {
        return instance;
    }
}
