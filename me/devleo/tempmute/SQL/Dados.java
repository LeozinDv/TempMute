package me.devleo.tempmute.SQL;

import me.devleo.tempmute.Main;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dados {

    public static void load() {
        update("CREATE TABLE IF NOT EXISTS `mute_users` (uuid VARCHAR(64), time BIGINT)");
    }

    public static void delete(String uuid) {
        update("DELETE FROM mute_users WHERE uuid='" + uuid + "'");
    }

    private static void update(String statement) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
            Connection con = Conexao.getConnection();
            PreparedStatement st = null;
            try {
                st = con.prepareStatement(statement);
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                Conexao.closeConnection(con, st);
            }
        });
    }

    public static boolean contains(String uuid) {
        Connection con = Conexao.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement("SELECT * FROM mute_users WHERE uuid='" + uuid + "';");
            rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(con, st, rs);
        }
        return false;
    }

    public static void set(String uuid, long time) {
        Connection con = Conexao.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement("SELECT * FROM mute_users WHERE uuid='" + uuid + "';");
            rs = st.executeQuery();
            if (rs.next()) {
                update("UPDATE mute_users SET time='" + time + " WHERE uuid='" + uuid + "';");
            } else {
                update("INSERT INTO mute_users (uuid,time)VALUES('" + uuid + "', '" + time + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(con, st, rs);
        }
    }

    public static long get(String uuid) {
        Connection con = Conexao.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement("SELECT * FROM mute_users WHERE uuid='" + uuid + "';");
            rs = st.executeQuery();
            if (rs.next())
                return rs.getLong("time");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexao.closeConnection(con, st, rs);
        }
        return 0;
    }
}
