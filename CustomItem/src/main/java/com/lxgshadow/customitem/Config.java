package com.lxgshadow.customitem;

public class Config {
    public static int potionwand_basic_duration;
    public static int potionwand_advanced_duration;
    public static int potionwand_basic_amplifier;
    public static int potionwand_advanced_amplifier;
    public static float withersword_skullspeed;
    public static float withersword_skullcost;
    public static float withersword_witherpotential;
    public static int withersword_realdamage;
    public static boolean withersword_skullbreak;
    public static int lighteningwand_maxdistance;
    public static int lighteningwand_realdamage;
    public static float carboat_speed;
    public static void init() {
        setDefault();
        potionwand_basic_duration = getInt("potionwand.basic.duration");
        potionwand_advanced_duration = getInt("potionwand.advanced.duration");
        potionwand_basic_amplifier = getInt("potionwand.basic.amplifier");
        potionwand_advanced_amplifier = getInt("potionwand.advanced.amplifier");
        withersword_skullspeed = getFloat("withersword.skullspeed");
        withersword_skullbreak = getBoolean("withersword.skullbreak");
        withersword_witherpotential = getFloat("withersword.witherpotential");
        withersword_realdamage = getInt("withersword.realdamage");
        lighteningwand_maxdistance = getInt("lighteningwand.maxdistance");
        lighteningwand_realdamage = getInt("lighteningwand.realdamage");
        carboat_speed = getFloat("carboat.speed");
    }

    private static void setDefault(){
        Main.getInstance().getConfig().addDefault("potionwand.basic.duration",23);
        Main.getInstance().getConfig().addDefault("potionwand.advanced.duration",23);
        Main.getInstance().getConfig().addDefault("potionwand.basic.amplifier",0);
        Main.getInstance().getConfig().addDefault("potionwand.advanced.amplifier",1);
        Main.getInstance().getConfig().addDefault("withersword.skullspeed",2);
        Main.getInstance().getConfig().addDefault("withersword.skullbreak",false);
        Main.getInstance().getConfig().addDefault("withersword.realdamage",10);
        Main.getInstance().getConfig().addDefault("withersword.witherpotential",0.3);
        Main.getInstance().getConfig().addDefault("lighteningwand.maxdistance",50);
        Main.getInstance().getConfig().addDefault("lighteningwand.realdamage",6);
        Main.getInstance().getConfig().addDefault("carboat.speed",50);
        Main.getInstance().getConfig().options().copyDefaults(true);
        Main.getInstance().saveConfig();
    }

    private static double getDouble(String path){ return Main.getInstance().getConfig().getDouble(path); }
    private static int getInt(String path){ return Main.getInstance().getConfig().getInt(path); }
    private static float getFloat(String path){ return (float) Main.getInstance().getConfig().getDouble(path); }
    private static String getString(String path){ return Main.getInstance().getConfig().getString(path); }
    private static boolean getBoolean(String path) {return Main.getInstance().getConfig().getBoolean(path);}
}
