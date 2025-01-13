package com.example.dao;

import com.example.database.DatabaseManager;
import com.example.model.Reward;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RewardDAO {
    private DatabaseManager dbManager;

    public RewardDAO() {
        dbManager = new DatabaseManager();
    }

    public void addReward(Reward reward) {
        String sql = "INSERT INTO Reward (name, wheel_id) VALUES (?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, reward.getName());
            pstmt.setInt(2, reward.getWheelId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Reward> getRewardsByWheelId(int wheelId) {
        List<Reward> rewards = new ArrayList<>();
        String sql = "SELECT * FROM Reward WHERE wheel_id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, wheelId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    rewards.add(mapResultSetToReward(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rewards;
    }
    private Reward mapResultSetToReward(ResultSet rs) throws SQLException {
         int id = rs.getInt("id");
         String name = rs.getString("name");
         int wheelId = rs.getInt("wheel_id");
        return new Reward(id, name,wheelId);
    }


}