package com.lxgshadow.survival.managers;

import com.lxgshadow.survival.Config;
import com.lxgshadow.survival.Main;
import com.lxgshadow.survival.mysql.mysqlConnection;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HomeManager {
    public static String table_name = "homes";
    private UUID uuid;

    public HomeManager(Player p) {
        this.uuid = p.getUniqueId();
    }

    public int getHomeNum() {
        ResultSet queue = mysqlConnection.select(table_name, "uuid", this.uuid.toString());
        int num = 0;
        if (queue != null) {
            try {
                while (queue.next()) {
                    num += 1;
                }
                queue.close();
            } catch (SQLException ex) {
                Main.getInstance().getLogger().warning(ex.getLocalizedMessage());
                return 0;
            }
        }
        return num;
    }

    public Location getHome(String name) {
        name = sqlEscape(name);
        String[] qn = {"uuid", "name"};
        Object[] qv = {this.uuid, name};

        ResultSet queue = mysqlConnection.select(table_name, qn, qv);
        if (queue != null) {
            try {
                Location loc = null;
                while (queue.next()) {
                    loc = new Location(Main.getInstance().getServer().getWorld(queue.getString("world")),
                            queue.getDouble("x"), queue.getDouble("y"), queue.getDouble("z"),
                            queue.getFloat("yaw"), queue.getFloat("pitch"));
                }
                queue.close();
                return loc;
            } catch (SQLException ex) {
                Main.getInstance().getLogger().warning(ex.getLocalizedMessage());
                return null;
            }
        }
        return null;
    }

    public List<String> getAllHome() {
        ResultSet queue = mysqlConnection.select(table_name, "uuid", this.uuid);
        List<String> homes = new ArrayList<String>();
        if (queue != null) {
            try {
                while (queue.next()) {
                    homes.add(queue.getString("name"));
                }
                queue.close();
                return homes;
            } catch (SQLException ex) {
                Main.getInstance().getLogger().warning(ex.getLocalizedMessage());
                return homes;
            }
        }
        return homes;
    }

    // 1 超过上限 2 相同名字存在 0 成功
    public int setHome(Location loc, String name) {
        name = sqlEscape(name);
        if (getHomeNum() >= Config.home_maxNum) {
            return 1;
        }
        if (getHome(name) != null) {
            return 2;
        }
        String[] cols = {"uuid", "name", "x", "y", "z", "yaw", "pitch", "world"};
        Object[] vals = {uuid, name, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch(), loc.getWorld().getName()};
        mysqlConnection.insert(table_name, cols, vals);
        return 0;
    }

    // -1 出错 0 成功 1 没有
    public int delHome(String name) {
        if (getHome(name) == null) {
            return 1;
        }
        name = sqlEscape(name);
        String[] qn = {"uuid", "name"};
        Object[] qv = {this.uuid, name};
        ResultSet queue =mysqlConnection.select(table_name, qn, qv);
        if (queue != null) {
            try {
                while (queue.next()) {
                    mysqlConnection.delete(table_name, qn, qv);
                    break;
                }
                queue.close();
                return 0;
            } catch (SQLException ex) {
                Main.getInstance().getLogger().warning(ex.getLocalizedMessage());
                return -1;
            }
        }
        return 1;
    }

    private String sqlEscape(String s) {
        return StringEscapeUtils.escapeSql(s);
    }
}
