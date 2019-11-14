package com.lxgshadow.customitem;

import com.lxgshadow.customitem.AdvancedTool.LumbererAxe;
import com.lxgshadow.customitem.AdvancedTool.MinerPickaxe;
import com.lxgshadow.customitem.AdvancedWeapon.BowOfBlackTea;
import com.lxgshadow.customitem.AdvancedWeapon.PotionWand;
import com.lxgshadow.customitem.ArkNightsCollection.ChiXiao;
import com.lxgshadow.customitem.ArkNightsCollection.EyjafjallaVolcano;
import com.lxgshadow.customitem.commands.energyCommand;
import com.lxgshadow.customitem.commands.getCICommand;
import com.lxgshadow.customitem.energySystem.energyDisplay;
import com.lxgshadow.customitem.UltimateWeapon.*;
import com.lxgshadow.customitem.managers.playerUtilManager;
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
        registerManagers();
        registerCommands();
        registerEvents();
        registerRecipes();
        getLogger().info("CustomItems Plugin Enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("CustomItems Plugin Disabling");
    }

    private void registerManagers(){
        playerUtilManager.initialize();
    }

    private void registerEvents(){
        this.getServer().getPluginManager().registerEvents(new energyDisplay(),this);
    }

    private void registerCommands(){
        this.getCommand("energy").setExecutor(new energyCommand(this));
        this.getCommand("getcustomitem").setExecutor(new getCICommand());
    }

    private void registerRecipes(){
        GodApple.createRecipe();
        WitherSword.createRecipe();
        LighteningWand.createRecipe();
        carBoat.createRecipe();
        AimbotBow.createRecipe();
        SoulSword.createRecipe();

        BowOfBlackTea.createRecipe();
        PotionWand.createRecipe();
        LumbererAxe.createRecipe();
        MinerPickaxe.createRecipe();

        EyjafjallaVolcano.createRecipe();
        ChiXiao.createRecipe();

    }

    public static Main getInstance() {
        return instance;
    }
}