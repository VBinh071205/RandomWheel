package com.example.dao;

import com.example.database.DatabaseManager;
import com.example.model.Wheel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WheelDAO {
    private DatabaseManager dbManager;

    public WheelDAO() {
        dbManager = new DatabaseManager();
    }

    public int addWheel(Wheel wheel) {
        String sql = "INSERT INTO Wheel (name, create_date) VALUES (?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, wheel.getName());
            pstmt.setLong(2, wheel.getCreateDate().getTime());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public Wheel getWheelById(int id) {
        String sql = "SELECT * FROM Wheel WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                   return  mapResultSetToWheel(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Wheel> getAllWheels() {
        List<Wheel> wheels = new ArrayList<>();
        String sql = "SELECT * FROM Wheel";
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                wheels.add(mapResultSetToWheel(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wheels;
    }


    private Wheel mapResultSetToWheel(ResultSet rs) throws SQLException {
       int id = rs.getInt("id");
       String name = rs.getString("name");
       long createDateMillis = rs.getLong("create_date");
       Date createDate = new Date(createDateMillis);
        Wheel wheel = new Wheel(id, name, createDate);
        RewardDAO rewardDAO = new RewardDAO();
        wheel.setRewards(rewardDAO.getRewardsByWheelId(id));
       return wheel;
    }
}