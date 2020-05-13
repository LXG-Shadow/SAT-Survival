package com.lxgshadow.survival;

import com.lxgshadow.survival.commands.*;
import com.lxgshadow.survival.listeners.*;
import com.lxgshadow.survival.managers.PicvManager;
import com.lxgshadow.survival.managers.SurvivalSBManager;
import com.lxgshadow.survival.mysql.mysqlConnection;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;


    @Override
    public void onLoad() {
        getLogger().info("Survival Plugin Loading");
    }

    @Override
    public void onEnable() {
        Main.instance = this;
        Config.init();
        registerCommands();
        registerEvents();
        initializeManagers();
        mysqlConnection.initialize();
    }

    @Override
    public void onDisable() {
        mysqlConnection.close();
        ChairsListener.clear();
    }

    private void initializeManagers(){
        SurvivalSBManager.initialize();
        PicvManager.initialtize();
    }

    private void registerEvents(){
        this.getServer().getPluginManager().registerEvents(new SurvivalTeamListener(),this);
        this.getServer().getPluginManager().registerEvents(new AntiExplosionListener(),this);
        this.getServer().getPluginManager().registerEvents(new vanishListener(),this);
        this.getServer().getPluginManager().registerEvents(new scoreboardRegListener(this),this);
        this.getServer().getPluginManager().registerEvents(new ChairsListener(),this);
        this.getServer().getPluginManager().registerEvents(new deathMessageListener(),this);
    }

    private void registerCommands(){
        this.getCommand("suicide").setExecutor(new suicideCommands());
        this.getCommand("ec").setExecutor(new enderchestCommands());
        this.getCommand("hat").setExecutor(new hatCommands());
        this.getCommand("tpa").setExecutor(new tpaCommands(this));
        this.getCommand("team").setExecutor(new teamCommands(this));
        this.getCommand("home").setExecutor(new homeCommands(this));
        this.getCommand("sethome").setExecutor(new homeCommands(this));
        this.getCommand("delhome").setExecutor(new homeCommands(this));
        this.getCommand("homelist").setExecutor(new homeCommands(this));
        this.getCommand("broadcast").setExecutor(new broadcastCommands(this));
        this.getCommand("vanish").setExecutor(new vanishCommands(this));
        this.getCommand("unvanish").setExecutor(new vanishCommands(this));
        this.getCommand("forcemsg").setExecutor(new forcemsgCommands(this));
        this.getCommand("openinv").setExecutor(new openinvCommands(this));

    }


    public static Main getInstance() {
        return instance;
    }

    public static void disablePlugin(String reason){
        instance.getLogger().info("Due to ' "+reason+" ', this plugin is going to be disabled.");
        instance.getServer().getPluginManager().disablePlugin(instance);
    }
}
