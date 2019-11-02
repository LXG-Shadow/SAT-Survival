package com.lxgshadow.customitem;

import com.lxgshadow.customitem.AdvancedWeapon.BowOfBlackTea;
import com.lxgshadow.customitem.potionwand.PotionWandCore;
import com.lxgshadow.customitem.UltimateWeapon.*;
import com.lxgshadow.customitem.vehicle.carBoat;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onLoad() {
        getLogger().info("CustomItems Plugin Loading");
    }

    @Override
    public void onEnable() {
        Main.instance = this;
        Config.init();
        registerCommands();
        registerEvents();
        registerRecipes();
        getLogger().info("CustomItems Plugin Enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("CustomItems Plugin Disabling");
    }

    private void registerEvents(){
    }

    private void registerCommands(){
    }

    private void registerRecipes(){
        GodApple.createRecipe();
        WitherSword.createRecipe();
        PotionWandCore.initialize();
        LighteningWand.createRecipe();
        carBoat.createRecipe();
        AimbotBow.createRecipe();
        SoulSword.createRecipe();
        BowOfBlackTea.createRecipe();
    }

    public static Main getInstance() {
        return instance;
    }
}