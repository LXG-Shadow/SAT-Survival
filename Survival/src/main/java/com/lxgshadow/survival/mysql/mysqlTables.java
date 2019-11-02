package com.lxgshadow.survival.mysql;

import com.lxgshadow.survival.managers.HomeManager;

import java.sql.SQLException;
import java.sql.Statement;

public class mysqlTables {
    public static void create() throws SQLException {
        Statement stmt = mysqlConnection.conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS "+ HomeManager.table_name +
                "(id int PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                "uuid text NOT NULL," +
                "name text, x double, y double, z double, yaw float, pitch float, world text)");
    }
}
