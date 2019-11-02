package com.lxgshadow.advancedmob;

import com.lxgshadow.advancedmob.Modification.advancedZombie;
import com.lxgshadow.advancedmob.MonWeapon.TheStoneSword;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onLoad() {
        getLogger().info("Advanced Mob Loading");
    }

    @Override
    public void onEnable() {
        Main.instance = this;
        Config.init();
        registerWeapons();
        registerModification();
        getLogger().info("Advanced Mob Enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Advanced Mob Disabling");
    }

    private void registerWeapons(){
        TheStoneSword.create();
    }
    private void registerModification(){
        this.getServer().getPluginManager().registerEvents(new advancedZombie(),this);
    }


    public static Main getInstance() {
        return instance;
    }
}