package com.lxgshadow.customitem.interfaces;

import com.lxgshadow.customitem.AdvancedTool.LumbererAxe;
import com.lxgshadow.customitem.AdvancedTool.MinerPickaxe;
import com.lxgshadow.customitem.AdvancedWeapon.BowOfBlackTea;
import com.lxgshadow.customitem.AdvancedWeapon.PotionWand;
import com.lxgshadow.customitem.ArkNightsCollection.ArkNightsCollectionBox;
import com.lxgshadow.customitem.ArkNightsCollection.ChiXiao;
import com.lxgshadow.customitem.ArkNightsCollection.EyjafjallaVolcano;
import com.lxgshadow.customitem.UltimateWeapon.AimbotBow;
import com.lxgshadow.customitem.UltimateWeapon.LighteningWand;
import com.lxgshadow.customitem.UltimateWeapon.SoulSword;
import com.lxgshadow.customitem.UltimateWeapon.WitherSword;
import org.bukkit.entity.Wither;

import java.util.ArrayList;
import java.util.List;

public enum CustomItemList {
    ArkNightsCollectionBox(com.lxgshadow.customitem.ArkNightsCollection.ArkNightsCollectionBox.regName,com.lxgshadow.customitem.ArkNightsCollection.ArkNightsCollectionBox.class),
    EyjafjallaVolcano(com.lxgshadow.customitem.ArkNightsCollection.EyjafjallaVolcano.regName,com.lxgshadow.customitem.ArkNightsCollection.EyjafjallaVolcano.class),
    ChiXiao(com.lxgshadow.customitem.ArkNightsCollection.ChiXiao.regName, com.lxgshadow.customitem.ArkNightsCollection.ChiXiao.class),

    BowOfBlackTea(com.lxgshadow.customitem.AdvancedWeapon.BowOfBlackTea.regName,com.lxgshadow.customitem.AdvancedWeapon.BowOfBlackTea.class),
    PotionWand(com.lxgshadow.customitem.AdvancedWeapon.PotionWand.regName,com.lxgshadow.customitem.AdvancedWeapon.PotionWand.class),
    DullahanSword(com.lxgshadow.customitem.AdvancedWeapon.DullahanSword.regName,com.lxgshadow.customitem.AdvancedWeapon.DullahanSword.class),

    LumbererAxe(com.lxgshadow.customitem.AdvancedTool.LumbererAxe.regName,com.lxgshadow.customitem.AdvancedTool.LumbererAxe.class),
    MinerPickaxe(com.lxgshadow.customitem.AdvancedTool.MinerPickaxe.regName,com.lxgshadow.customitem.AdvancedTool.MinerPickaxe.class),


    AimbotBow(com.lxgshadow.customitem.UltimateWeapon.AimbotBow.regName,com.lxgshadow.customitem.UltimateWeapon.AimbotBow.class),
    LighteningWand(com.lxgshadow.customitem.UltimateWeapon.LighteningWand.regName,com.lxgshadow.customitem.UltimateWeapon.LighteningWand.class),
    SoulSword(com.lxgshadow.customitem.UltimateWeapon.SoulSword.regName,com.lxgshadow.customitem.UltimateWeapon.SoulSword.class),
    WitherSword(com.lxgshadow.customitem.UltimateWeapon.WitherSword.regName,com.lxgshadow.customitem.UltimateWeapon.WitherSword.class);


    public Class clazz;
    public String regName;
    private CustomItemList(String regName,Class clazz){
        this.clazz = clazz;
        this.regName = regName;
    }
}
