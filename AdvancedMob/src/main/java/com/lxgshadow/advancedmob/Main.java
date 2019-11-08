package com.lxgshadow.advancedmob;

import com.lxgshadow.advancedmob.Modification.advancedSkeleton;
import com.lxgshadow.advancedmob.Modification.advancedSpider;
import com.lxgshadow.advancedmob.Modification.advancedZombie;
import com.lxgshadow.advancedmob.Modification.commonMonster;
import com.lxgshadow.advancedmob.MonWeapon.TheBoneBow;
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
        TheBoneBow.create();
    }
    private void registerModification(){
        this.getServer().getPluginManager().registerEvents(new commonMonster(),this);
        this.getServer().getPluginManager().registerEvents(new advancedZombie(),this);
        this.getServer().getPluginManager().registerEvents(new advancedSkeleton(),this);
        this.getServer().getPluginManager().registerEvents(new advancedSpider(),this);
    }


    public static Main getInstance() {
        return instance;
    }
}