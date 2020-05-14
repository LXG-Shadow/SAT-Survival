package com.lxgshadow.survival.mysql;

import com.lxgshadow.survival.Config;
import com.lxgshadow.survival.Main;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import sun.nio.cs.ext.MacArabic;

import java.sql.*;

public class mysqlConnection {
    private static boolean status;
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static BukkitRunnable re;
    public static Connection conn;

    public static void initialize(){
        status = false;
        String url = "jdbc:mysql://"+ Config.mysql_address+":"+Config.mysql_port+"/"+Config.mysql_db+"?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true";
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            Main.getInstance().getLogger().info("Connection to database...");
            conn = DriverManager.getConnection(url, Config.mysql_username,Config.mysql_password);
            Main.getInstance().getLogger().info("Database connected.");
            Main.getInstance().getLogger().info("Initialize Tables.");
            mysqlTables.create();
            Main.getInstance().getLogger().info("Tables initialized");
        }catch(Exception se){
            // 处理 JDBC 错误
            se.printStackTrace();
            Main.getInstance().getLogger().warning("Mysql connection error, some function may not working properly");
            //Main.disablePlugin("Mysql connection error");
            return;
        }
        // 重启conn
        re = new BukkitRunnable(){
            @Override
            public void run(){
                try {
                    Main.getInstance().getLogger().info("Restarting Mysql Connection");
                    if (conn!=null)conn.close();
                    conn = DriverManager.getConnection(url, Config.mysql_username,Config.mysql_password);
                    Main.getInstance().getLogger().info("Mysql Connection Established");
                } catch (SQLException e) {
                    e.printStackTrace();
                    status = false;
                    Main.disablePlugin("Mysql connection error");
                    re.cancel();
                }
            }
        };
        re.runTaskTimer(Main.getInstance(),0,60*20*60*2);
        status = true;
    }

    public static void close(){
        Main.getInstance().getLogger().info("Close mysql connection");
        if (re == null){
            return;
        }
        re.cancel();
        try {
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ResultSet select(String table,String[] cols,Object[] values){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        if (cols.length != values.length){
            return rs;
        }
        StringBuilder sql = new StringBuilder("SELECT * FROM ").append(table).append(" WHERE ");
        for (int i=0;i<cols.length;i++){
            if (i == values.length-1){
                sql.append(cols[i]).append("='").append(values[i]).append("';");
            }else {
                sql.append(cols[i]).append("='").append(values[i]).append("' AND ");
            }
        }
        try {
            stmt = conn.prepareStatement(sql.toString());
            rs = stmt.executeQuery(sql.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static ResultSet select(String table,String col,Object value){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder sql = new StringBuilder("SELECT * FROM ").append(table).append(" ");
        sql.append("WHERE ").append(col).append("='").append(value).append("'");
        try {
            stmt = conn.prepareStatement(sql.toString());
            rs = stmt.executeQuery(sql.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static Boolean delete(String table,String[] cols,Object[] values){
        PreparedStatement stmt = null;
        if (cols.length != values.length){
            return false;
        }
        StringBuilder sql = new StringBuilder("DELETE FROM ").append(table).append(" WHERE ");
        for (int i=0;i<cols.length;i++){
            if (i == values.length-1){
                sql.append(cols[i]).append("='").append(values[i]).append("';");
            }else {
                sql.append(cols[i]).append("='").append(values[i]).append("' AND ");
            }
        }
        try {
            stmt = conn.prepareStatement(sql.toString());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean insert(String table,String cols[],Object[] values){
        PreparedStatement stmt = null;
        if (cols.length != values.length){
            return false;
        }
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(table);
        sql.append(" (");
        for (int i=0;i<cols.length;i++){
            if (i == values.length-1){
                sql.append(cols[i]).append(") ");
            }else {
                sql.append(cols[i]).append(",");
            }
        }
        sql.append("VALUES (");
        for (int i=0;i<values.length;i++){
            if (i == values.length-1){
                sql.append("'").append(values[i]).append("'").append(") ");
            }else {
                sql.append("'").append(values[i]).append("'").append(",");
            }
        }
        try {
            stmt = conn.prepareStatement(sql.toString());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean getStatus(){
        return status;
    }
}
