package me.lakoba.pureSkyblockCore.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/skyblock", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveIsland(String player, String uuid) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO islands (player, uuid) VALUES (?, ?)")) {
            stmt.setString(1, player);
            stmt.setString(2, uuid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
