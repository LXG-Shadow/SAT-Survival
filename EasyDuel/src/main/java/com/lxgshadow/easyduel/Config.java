package com.lxgshadow.easyduel;

public class Config {
    public static void init() {
        setDefault();

    }

    private static void setDefault(){
        Main.getInstance().getConfig().options().copyDefaults(true);
        Main.getInstance().saveConfig();
    }

    private static double getDouble(String path){ return Main.getInstance().getConfig().getDouble(path); }
    private static int getInt(String path){ return Main.getInstance().getConfig().getInt(path); }
    private static float getFloat(String path){ return (float) Main.getInstance().getConfig().getDouble(path); }
    private static String getString(String path){ return Main.getInstance().getConfig().getString(path); }
}
