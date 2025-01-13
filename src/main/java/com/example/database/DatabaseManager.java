package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/database.db";

    public DatabaseManager() {
        createTables();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    private void createTables() {
           String sqlWheel = "CREATE TABLE IF NOT EXISTS Wheel (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "create_date INTEGER NOT NULL" +
                ")";

        String sqlReward = "CREATE TABLE IF NOT EXISTS Reward (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
             "wheel_id INTEGER NOT NULL," +
            "FOREIGN KEY(wheel_id) REFERENCES Wheel(id) ON DELETE CASCADE" +
                ")";

         String sqlHistory = "CREATE TABLE IF NOT EXISTS History (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "spin_time INTEGER NOT NULL," +
            "wheel_id INTEGER NOT NULL," +
              "reward_name TEXT NOT NULL," +
            "FOREIGN KEY(wheel_id) REFERENCES Wheel(id) ON DELETE CASCADE" +
                ")";

           String sqlWheelReward = "CREATE TABLE IF NOT EXISTS WheelReward (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "wheel_id INTEGER NOT NULL," +
            "wheel_name TEXT NOT NULL," +
            "reward_name TEXT NOT NULL," +
             "FOREIGN KEY(wheel_id) REFERENCES Wheel(id) ON DELETE CASCADE" +
                ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlWheel);
            stmt.execute(sqlReward);
           stmt.execute(sqlHistory);
            stmt.execute(sqlWheelReward);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}