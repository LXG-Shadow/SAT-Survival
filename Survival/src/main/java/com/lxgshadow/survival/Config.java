package com.lxgshadow.survival;

public class Config {
    public static String mysql_address="127.0.0.1";
    public static String mysql_port="3306";
    public static String mysql_db="mcserver";
    public static String mysql_username="root";
    public static String mysql_password="root";

    public static int tpa_cooldown;
    public static int home_maxNum;
    public static int team_maxSize;

    public static void init() {
        setDefault();
        home_maxNum = getInt("home.maxNum");
        tpa_cooldown = getInt("tpa.cooldown");
        team_maxSize = getInt("team.maxSize");
        mysql_address = getString("mysql.address");
        mysql_port = getString("mysql.port");
        mysql_db = getString("mysql.db");
        mysql_username = getString("mysql.username");
        mysql_password = getString("mysql.password");

    }

    private static void setDefault(){
        Main.getInstance().getConfig().addDefault("home.maxNum",3);
        Main.getInstance().getConfig().addDefault("team.maxSize",3);
        Main.getInstance().getConfig().addDefault("tpa.cooldown",60);
        Main.getInstance().getConfig().options().copyDefaults(true);
        Main.getInstance().saveConfig();
    }

    private static double getDouble(String path){ return Main.getInstance().getConfig().getDouble(path); }
    private static int getInt(String path){ return Main.getInstance().getConfig().getInt(path); }
    private static float getFloat(String path){ return (float) Main.getInstance().getConfig().getDouble(path); }
    private static String getString(String path){ return Main.getInstance().getConfig().getString(path); }
}
