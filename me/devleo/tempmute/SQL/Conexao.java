package me.devleo.tempmute.SQL;

import me.devleo.tempmute.Main;

import java.sql.*;

public class Conexao {

    private static final String host = Main.plugin.getConfig().getString("MySQL.host");
    private static final int porta = Main.plugin.getConfig().getInt("MySQL.porta");
    private static final String database = Main.plugin.getConfig().getString("MySQL.database");
    private static final String url = "jdbc:mysql://" + host + ":" + porta + "/" + database;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(url, Main.plugin.getConfig().getString("MySQL.usuario"), Main.plugin.getConfig().getString("MySQL.senha"));
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Ocorreu um erro na conexao do SQL: ", e);
        }
    }

    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro na conexao do SQL: ", e);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement st) {
        closeConnection(con);
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro na conexao do SQL: ", e);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement st, ResultSet rs) {
        closeConnection(con, st);
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro na conexao do SQL: ", e);
        }
    }
}
